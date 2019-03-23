package com.hutechlab.monaget;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hutechlab.adapter.RecordAdapter;
import com.hutechlab.model.Flow;
import com.hutechlab.model.Record;
import com.hutechlab.model.StringWithTag;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SearchActivity extends AppCompatActivity {
    String DATABASE_NAME="monaget.db";
    SQLiteDatabase database=null;

    LinearLayout lotSearchArea;
    Spinner spnFlow, spnActivity;
    EditText edtDescript, edtMoneyMin, edtMoneyMax;
    ImageButton btnDateLeft, btnDateRight;
    TextView txtDateLeft, txtDateRight;
    ListView lvSearchResult;
    ArrayAdapter<StringWithTag> flowArrayAdapter, activityAdapter;
    SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
    RecordAdapter recordAdapter;

    Flow selected=null;
    String dateLeftString= "";
    String dateRightString= "";
    int selectedActId;

    boolean isLotSearchAreaVisible= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        loadControls();
        initProps();
        addEvents();
        linkAdapterToControls();


        getSupportActionBar().setTitle(R.string.act_Search);
    }

    private void loadFlow(ArrayAdapter toAdapter) {
        toAdapter.add(new StringWithTag("Tất cả", 0));

        database= openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor= database.rawQuery("SELECT f.flowId, f.flowName " +
                "from Flow f", null);
        while(cursor.moveToNext()){
            int    flowId=    cursor.getInt(0);
            String flowName=     cursor.getString(1);

            StringWithTag flow= new StringWithTag(flowName, flowId);
            toAdapter.add(flow);
        }
    }

    private void linkAdapterToControls() {

        spnFlow.setAdapter(flowArrayAdapter);
        spnActivity.setAdapter(activityAdapter);
        lvSearchResult.setAdapter(recordAdapter);
    }

    private void initProps() {
        flowArrayAdapter= new ArrayAdapter<StringWithTag>(SearchActivity.this, android.R.layout.simple_spinner_item);
        flowArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        loadFlow(flowArrayAdapter);

        activityAdapter=new ArrayAdapter<StringWithTag>(SearchActivity.this, android.R.layout.simple_spinner_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);

        recordAdapter= new RecordAdapter(SearchActivity.this, R.layout.list_item_record);
    }

    private void addEvents() {
        btnDateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDateClicked(v);
            }
        });
        btnDateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDateClicked(v);
            }
        });

        spnFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadActivitySpinner(flowArrayAdapter.getItem(position).tag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loadActivitySpinner(0);
            }
        });
        edtMoneyMax.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!edtMoneyMin.getText().toString().equals("")){
                        if(
                                Integer.parseInt(edtMoneyMin.getText().toString())>
                                        Integer.parseInt(edtMoneyMax.getText().toString())
                        ){
                            edtMoneyMax.setText("");
                            Toast.makeText(SearchActivity.this, "Điều kiện sai", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            }
        });
        edtMoneyMin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!edtMoneyMax.getText().toString().equals("")){
                    if(
                            Integer.parseInt(edtMoneyMin.getText().toString())>
                                    Integer.parseInt(edtMoneyMax.getText().toString())
                    ){
                        edtMoneyMin.setText("");
                        Toast.makeText(SearchActivity.this, "Điều kiện sai", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        spnActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedActId= activityAdapter.getItem(position).tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadActivitySpinner(int forFlowId){
        activityAdapter=new ArrayAdapter<StringWithTag>(SearchActivity.this, android.R.layout.simple_spinner_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        database= openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        activityAdapter.add(new StringWithTag("Tất cả", 0));
        Cursor cursor;
        if(forFlowId!= 0){
            cursor= database.rawQuery("select a.activityId, a.activityName "+
                    "from Activity a "+
                    "where a.flowId = "+forFlowId, null);
        }
        else{
            cursor= database.rawQuery("select a.activityId, a.activityName "+
                    "from Activity a ", null);
        }
        while(cursor.moveToNext()){
            int    activityId=    cursor.getInt(0);
            String activityname=     cursor.getString(1);

            StringWithTag flow= new StringWithTag(activityname, activityId);
            activityAdapter.add(flow);
        }
        spnActivity.setAdapter(activityAdapter);
    }

    private void btnDateClicked(final View button){
        // Get Current Date
        final Calendar c = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if(button.getId() == R.id.btnDateLeft){
                            if(dateRightString.isEmpty()){
                                txtDateLeft.setText("Từ ngày: "+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                dateLeftString= year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            }
                            else {
                                try {
                                    Date dateRight = dateFormat.parse(dateRightString);
                                    Date dateLeft = dateFormat.parse( dayOfMonth + "/" + (monthOfYear + 1) + "/" +year );
                                    if (dateLeft.compareTo(dateRight) == -1) {
                                        txtDateLeft.setText("Đến ngày: " + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                        dateLeftString =  dayOfMonth + "/" + (monthOfYear + 1) + "/" + year ;
                                    } else {
                                        Toast.makeText(SearchActivity.this,
                                                "Biên phải phải lớn hơn biên trái", Toast.LENGTH_SHORT).show();
                                        txtDateLeft.setText("Biên trái");
                                        dateLeftString= "";
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else if(button.getId() == R.id.btnDateRight){
                            if(dateLeftString.isEmpty()){
                                txtDateRight.setText("Đến ngày: "+dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                dateRightString= dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            }
                            else {
                                try {
                                    Date dateleft = dateFormat.parse(dateLeftString);
                                    Date dateRight = dateFormat.parse( dayOfMonth + "/" + (monthOfYear + 1) + "/" +year );
                                    if (dateleft.compareTo(dateRight) == -1) {
                                        txtDateRight.setText("Đến ngày: " + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                        dateRightString = year + "-" + (monthOfYear + 1) + "-" +dayOfMonth ;
                                    } else {
                                        Toast.makeText(SearchActivity.this,
                                                "Biên phải phải lớn hơn biên trái", Toast.LENGTH_SHORT).show();
                                        txtDateRight.setText("Biên phải");
                                        dateRightString= "";
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void loadControls() {
        lotSearchArea= findViewById(R.id.lotSearchArea);
        spnFlow= findViewById(R.id.spnFlow);
        spnActivity= findViewById(R.id.spnActivity);
        edtDescript= findViewById(R.id.edtDescript);
        edtMoneyMin= findViewById(R.id.edtMoneyMin);
        edtMoneyMax= findViewById(R.id.edtMoneyMax);
        btnDateLeft= findViewById(R.id.btnDateLeft);
        btnDateRight= findViewById(R.id.btnDateRight);
        txtDateLeft= findViewById(R.id.txtDateLeft);
        txtDateRight= findViewById(R.id.txtDateRight);
        lvSearchResult= findViewById(R.id.lvSearchResult);
    }

    public void btnSearchCollapseClicked(View view) {
        if(isLotSearchAreaVisible){
            lotSearchArea.setVisibility(View.GONE);
            isLotSearchAreaVisible= false;
            ((ImageButton)view).setImageResource(R.drawable.expand64);
        }
        else{
            lotSearchArea.setVisibility(View.VISIBLE);
            isLotSearchAreaVisible= true;
            ((ImageButton)view).setImageResource(R.drawable.collapse64);
        }
    }

    private void loadRecords(String sqlString){
        /**
         * Load danh sách các bản ghi vào [banGhiAdapter]
         */
        database= openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor= database.rawQuery(sqlString, null);
        while(cursor.moveToNext()){
            int    recordId=             cursor.getInt(0);
            String description=     cursor.getString(1);
            String time=    cursor.getString(2);
            int    ammount=         cursor.getInt(3);
            int    activityId=     cursor.getInt(4);
            String activityName= cursor.getString(5);
            int    flowId=     cursor.getInt(6);
            String flowName=     cursor.getString(7);

            Record record= new Record(recordId, description, time,
                    ammount, activityId, activityName, flowId, flowName);
            recordAdapter.add(record);
        }
        lvSearchResult.setAdapter(recordAdapter);
    }

    public void btnSearchClicked(View view) {
        switch(getControlsState()){
            case 0:
                loadRecords("select r.recordId, r.description,  r.time, r.ammount, a.activityId, a.activityName, f.flowId, f.flowName " +
                        "from Record r, Flow f, Activity a " +
                        "where r.activityId= a.activityId and a.flowId= f.flowId");
                break;
            case 99:
                loadRecords("select r.recordId, r.description,  r.time, r.ammount, a.activityId, a.activityName, f.flowId, f.flowName "+
                        "from Record r, Flow f, Activity a "+
                        "where r.activityId= a.activityId and a.flowId= f.flowId "+
                        "and unicode(r.description) = unicode('"+edtDescript.getText().toString()+"') "+
                        "and r.ammount > "+ edtMoneyMin.getText().toString()+
                        " and r.ammount < "+ edtMoneyMax.getText().toString()+
                        " and r.activityId= "+ selectedActId);
                break;
        }
    }

    private int getControlsState(){
        if(flowArrayAdapter.getItem(spnFlow.getSelectedItemPosition()).tag == 0 &&
                activityAdapter.getItem(spnActivity.getSelectedItemPosition()).tag == 0 &&
                edtDescript.getText().toString().equals("") &&
                edtMoneyMin.getText().toString().equals("") &&
                edtMoneyMax.getText().toString().equals("")&&
                dateRightString.equals("") &&
                dateLeftString.equals("")){
            //Tất cả đều trống
            return 0;
        }
        else if(flowArrayAdapter.getItem(spnFlow.getSelectedItemPosition()).tag != 0 &&
                activityAdapter.getItem(spnActivity.getSelectedItemPosition()).tag == 0 &&
                edtDescript.getText().toString().equals("") &&
                edtMoneyMin.getText().toString().equals("") &&
                edtMoneyMax.getText().toString().equals("")&&
                dateRightString.equals("") &&
                dateLeftString.equals("")){
            //Flow không trống
            return 1;
        }
        else if(flowArrayAdapter.getItem(spnFlow.getSelectedItemPosition()).tag == 0 &&
                activityAdapter.getItem(spnActivity.getSelectedItemPosition()).tag != 0 &&
                edtDescript.getText().toString().equals("") &&
                edtMoneyMin.getText().toString().equals("") &&
                edtMoneyMax.getText().toString().equals("")&&
                dateRightString.equals("") &&
                dateLeftString.equals("")){
            //Activity not null
            return 2;
        }
        else if(flowArrayAdapter.getItem(spnFlow.getSelectedItemPosition()).tag == 0 &&
                activityAdapter.getItem(spnActivity.getSelectedItemPosition()).tag == 0 &&
                !edtDescript.getText().toString().equals("") &&
                edtMoneyMin.getText().toString().equals("") &&
                edtMoneyMax.getText().toString().equals("")&&
                dateRightString.equals("") &&
                dateLeftString.equals("")){
            //Descript not null
            return 3;
        }
        else if(flowArrayAdapter.getItem(spnFlow.getSelectedItemPosition()).tag == 0 &&
                activityAdapter.getItem(spnActivity.getSelectedItemPosition()).tag == 0 &&
                edtDescript.getText().toString().equals("") &&
                !edtMoneyMin.getText().toString().equals("") &&
                edtMoneyMax.getText().toString().equals("")&&
                dateRightString.equals("") &&
                dateLeftString.equals("")){
            //MoneyMin not null
            return 4;
        }
        else if(flowArrayAdapter.getItem(spnFlow.getSelectedItemPosition()).tag == 0 &&
                activityAdapter.getItem(spnActivity.getSelectedItemPosition()).tag == 0 &&
                edtDescript.getText().toString().equals("") &&
                edtMoneyMin.getText().toString().equals("") &&
                !edtMoneyMax.getText().toString().equals("")&&
                dateRightString.equals("") &&
                dateLeftString.equals("")){
            //edtMoneyMax not null
            return 5;
        }
        else if(flowArrayAdapter.getItem(spnFlow.getSelectedItemPosition()).tag == 0 &&
                activityAdapter.getItem(spnActivity.getSelectedItemPosition()).tag == 0 &&
                edtDescript.getText().toString().equals("") &&
                edtMoneyMin.getText().toString().equals("") &&
                edtMoneyMax.getText().toString().equals("")&&
                !dateRightString.equals("") &&
                dateLeftString.equals("")){
            //dateRight not null
            return 6;
        }
        else if(flowArrayAdapter.getItem(spnFlow.getSelectedItemPosition()).tag == 0 &&
                activityAdapter.getItem(spnActivity.getSelectedItemPosition()).tag == 0 &&
                edtDescript.getText().toString().equals("") &&
                edtMoneyMin.getText().toString().equals("") &&
                edtMoneyMax.getText().toString().equals("")&&
                dateRightString.equals("") &&
                !dateLeftString.equals("")){
            //dateLeft not null
            return 7;
        }
        return 99;
    }
}
