package com.hutechlab.monaget;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.hutechlab.adapter.RecordAdapter;
import com.hutechlab.model.Record;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    //Các biến liên quan đến database
    String DATABASE_NAME="monaget.db";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;

    //Khai báo các adapters
    RecordAdapter recordAdapter;

    //Các biến controls
    ListView lvRecord;
    TabHost host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Chạy hàm thêm database
        processCopy();

        //Khởi tạo các biến, ví dụ Adapter
        initProps();

        //Load controls từ file layout
        loadControls();

        addEvents();
        loadRecords();

        //Liên kết các adapter với controls
        linkAdapterToControls();

        getSupportActionBar().setTitle(R.string.app_name);
    }

    private void linkAdapterToControls() {
        lvRecord.setAdapter(recordAdapter);
    }

    private void initProps() {
        recordAdapter= new RecordAdapter(MainActivity.this, R.layout.list_item_record);
    }

    private void addEvents() {
        lvRecord.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // Create custom dialog object
                final Dialog dialog = new Dialog(MainActivity.this);
                // Include dialog.xml file
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.list_item_record_longclick);

                dialog.show();

                Button backButton = dialog.findViewById(R.id.btnBack);
                Button btnDelete= dialog.findViewById(R.id.btnDelete);
                Button btnEdit= dialog.findViewById(R.id.btnEdit);
                // if decline button is clicked, close the custom dialog
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteRecords(recordAdapter.getItem(position).getRecordId());
                        updateList();
                        dialog.cancel();
                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, EditRecordActivity.class);
                        i.putExtra("record", recordAdapter.getItem(position));
                        startActivity(i);
                        dialog.cancel();
                    }
                });

                return false;
            }
        });
    }

    private void updateList() {
        loadRecords();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords();
    }

    //Option menu Start <<<<<<<<<<<<<<<<<
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!= null){
            Bundle bdl= data.getExtras();
            Toast.makeText(this, ""+bdl.getInt("test"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.btnSearch:
                Intent searchIntent= new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.btnAddRecord:
                Intent addRecordIntent= new Intent(MainActivity.this, AddRecordActivity.class);
                startActivity(addRecordIntent);
                break;

        }
        return true;
    }

    //Option menu END <<<<<<<<<<<<<<<<<

    private void loadControls() {
        // Tab layout START <<<<<<<<<<<<<
        host = findViewById(R.id.mainTabHost);
        host.setup();
        TabHost.TabSpec spec = host.newTabSpec("Tab Record").setIndicator("",getResources().getDrawable(R.drawable.record64));
        spec.setContent(R.id.tabRecord);
        host.addTab(spec);
        //Tab 2
        spec = host.newTabSpec("Tab Two").setIndicator("",getResources().getDrawable(R.drawable.stat64));
        spec.setContent(R.id.tabStat);
        host.addTab(spec);
        //Tab 3
        spec = host.newTabSpec("Tab Three").setIndicator("",getResources().getDrawable(R.drawable.info64));
        spec.setContent(R.id.tabInfo);
        host.addTab(spec);
        // Tab layout END <<<<<<<<<<<<<

        lvRecord= findViewById(R.id.lvRecord);
    }

    //Database START <<<<<<<<<<<<<<<<<<<<
    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try
            {
                CopyDataBaseFromAsset();
                Toast.makeText(this,
                        "Copying sucess from Assets folder",
                        Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }

    public void CopyDataBaseFromAsset()
    {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Database END <<<<<<<<<<<<<<<<<<<<

    //Thao tác adapter : lấy dữ liệu

    private void loadRecords(){
        /**
         * Load danh sách các bản ghi vào [banGhiAdapter]
         */
        recordAdapter= null;
        lvRecord.setAdapter(null);
        recordAdapter= new RecordAdapter(MainActivity.this, R.layout.list_item_record);
        database= openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor= database.rawQuery(
                "select r.recordId, r.description,  r.time, r.ammount, a.activityId, a.activityName, f.flowId, f.flowName " +
                        "from Record r, Flow f, Activity a " +
                        "where r.activityId= a.activityId and a.flowId= f.flowId", null);
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
        recordAdapter.notifyDataSetChanged();
        lvRecord.setAdapter(recordAdapter);
    }

    private void deleteRecords(int recordId){
        database= openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        int kq=database.delete("Record","recordId=?",new String[]{""+recordId});
        if(kq>0)
        {
            Toast.makeText(this, "Xóa bản ghi Thành công", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Xóa bản ghi thất bại", Toast.LENGTH_SHORT).show();
        }
    }

}
