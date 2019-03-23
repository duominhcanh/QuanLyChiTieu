package com.hutechlab.monaget;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hutechlab.model.Record;
import com.hutechlab.model.StringWithTag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditRecordActivity extends AppCompatActivity {
    String DATABASE_NAME="monaget.db";
    SQLiteDatabase database=null;

    Record r;
    EditText edtDescription, edtMoney;
    Spinner spnFlow, spnActivity;
    TextView txtDate;

    ArrayAdapter<StringWithTag> flowArrayAdapter, activityAdapter;

    StringWithTag selectedFlowObject= null;
    StringWithTag selectedActivityObject= null;

    SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");

    boolean inited= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        Intent i = getIntent();
        r= (Record) i.getSerializableExtra("record");

        loadControls();
        initProps();
        addEvents();
        linkAdapterToControls();


        if(selectedFlowObject != null){
            spnFlow.setSelection(flowArrayAdapter.getPosition(selectedFlowObject));
        }
        else{
            spnFlow.setSelection(1);
        }

        txtDate.setText("Thời gian: "+r.getTime());

        getSupportActionBar().setTitle(R.string.act_EditRecord);
    }

    private void linkAdapterToControls() {

        spnFlow.setAdapter(flowArrayAdapter);
        spnActivity.setAdapter(activityAdapter);
    }

    private void initProps() {
        flowArrayAdapter= new ArrayAdapter<StringWithTag>(EditRecordActivity.this, android.R.layout.simple_spinner_item);
        flowArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        loadFlow(flowArrayAdapter);
    }

    private void loadControls() {
        edtDescription= findViewById(R.id.edtDescription);
        edtMoney= findViewById(R.id.edtMoney);
        edtDescription.setText(r.getDescription());
        edtMoney.setText(""+r.getAmmount());
        spnFlow= findViewById(R.id.spnFlow);
        spnActivity= findViewById(R.id.spnActivity);
        txtDate= findViewById(R.id.txtDate);
    }

    public void btnCancelClicked(View view) {
        finish();
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
            if(flowId == r.getFlowId()){
                selectedFlowObject= flow;
            }
            toAdapter.add(flow);
        }
    }

    private void loadActivitySpinner(int forFlowId){
        activityAdapter=new ArrayAdapter<StringWithTag>(EditRecordActivity.this, android.R.layout.simple_spinner_item);
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
            String activityName=     cursor.getString(1);

            StringWithTag activity= new StringWithTag(activityName, activityId);
            if(activityId == r.getActivityId()){
                selectedActivityObject= activity;
            }
            activityAdapter.add(activity);
        }
        spnActivity.setAdapter(activityAdapter);

        if(selectedActivityObject != null){
            spnActivity.setSelection(activityAdapter.getPosition(selectedActivityObject));
        }
        else{
            spnActivity.setSelection(0);
        }
    }

    private void addEvents() {
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
        spnActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                r.setActivityId(activityAdapter.getItem(position).tag);
                r.setActivityName(activityAdapter.getItem(position).string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void btnDateClicked(View view) {
        final Calendar c = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        txtDate.setText("Thời gian: "+dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        if(inited){
                            r.setTime(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

        inited= true;
    }

    public void btnSaveClicked(View view) {
        database= openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        ContentValues values=new ContentValues();
        values.put("description", edtDescription.getText().toString());
        values.put("time",r.getTime());
        values.put("ammount", Integer.parseInt(edtMoney.getText().toString()));
        values.put("activityId",r.getActivityId());

        int kq=database.update("Record",values,"recordId=?",new String[]{""+r.getRecordId()});
        if(kq>0)
        {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(this, "Có lỗi phát sinh, vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
        }

    }
}
