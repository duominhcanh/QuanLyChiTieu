package com.hutech.quanlychitieu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.hutech.adapter.BanGhiAdapter;
import com.hutech.model.BanGhi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    //Các biến liên quan đến database
    String DATABASE_NAME="quanlychitieu.db";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;

    //Khai báo các adapters
    BanGhiAdapter banGhiAdapter;

    //Các biến controls
    Button btnAddRecord;

    ListView lvTab1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Chạy hàm thêm database
        processCopy();

        //Khởi tạo các biến, ví dụ Adapter
        initProps();

        //Load controls từ file layout
        addControls();

        //Liên kết các adapter với controls
        linkAdapterToControls();
    }

    private void linkAdapterToControls() {
        /**
         * Liên kết các adapter với controls
         */
        lvTab1.setAdapter(banGhiAdapter);
    }

    private void initProps() {
        /**
         * Khởi tạo các thuộc tính trong class
         * Đã làm: [banGhiAdapter]
         */
        banGhiAdapter= new BanGhiAdapter(MainActivity.this, R.layout.mainlistitem);
        loadBanGhi();
    }

    private void addControls() {
        // Tab layout START <<<<<<<<<<<<<
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();
        TabHost.TabSpec spec = host.newTabSpec("Tab One").setIndicator("",getResources().getDrawable(R.drawable.record));
        spec.setContent(R.id.tab1);
        spec.setIndicator("Tab One");
        spec.setIndicator("",getResources().getDrawable(R.drawable.record));
        host.addTab(spec);
        //Tab 2
        spec = host.newTabSpec("Tab Two").setIndicator("",getResources().getDrawable(R.drawable.stat));
        spec.setContent(R.id.tab2);
        spec.setIndicator("Tab Two");
        spec.setIndicator("",getResources().getDrawable(R.drawable.stat));
        host.addTab(spec);
        //Tab 3
        spec = host.newTabSpec("Tab Three").setIndicator("",getResources().getDrawable(R.drawable.category));
        spec.setContent(R.id.tab3);
        spec.setIndicator("Tab Three");
        spec.setIndicator("",getResources().getDrawable(R.drawable.category));
        host.addTab(spec);
        //Tab 4
        spec = host.newTabSpec("Tab Four").setIndicator("",getResources().getDrawable(R.drawable.info));
        spec.setContent(R.id.tab4);
        spec.setIndicator("Tab Four");
        spec.setIndicator("",getResources().getDrawable(R.drawable.info));
        host.addTab(spec);

        // Tab layout END <<<<<<<<<<<<<

        // Load controls trên tab 1
        //btnAddRecord= findViewById(R.id.btnAddRecord);
        lvTab1= findViewById(R.id.lvTab1);

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
        inflater.inflate(R.menu.maintabmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.menuSettings:
                menuSettingLanguageClicked();
                break;

        }
        return true;
    }

    private void menuSettingLanguageClicked() {
        Intent i= new Intent(this, MainSettingActivity.class);
        final int result=1;
        startActivityForResult(i, result);
    }

    //Option menu END <<<<<<<<<<<<<<<<<

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

    private void loadBanGhi(){
        /**
         * Load danh sách các bản ghi vào [banGhiAdapter]
         */
        database= openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor= database.rawQuery(
                "select b.ma, b.noiDung,  b.thoiGian, b.soTien, b.maHoatDong, h.tenHoatDong, h.maDongTien, d.tenDong " +
                        "from BanGhi b, DongTien d, HoatDong h " +
                        "where b.maHoatDong= h.maHoatDong and h.maDongTien= d.maDong", null);
        while(cursor.moveToNext()){
            int    ma=             cursor.getInt(0);
            String noiDung=     cursor.getString(1);
            String thoiGian=    cursor.getString(2);
            int    soTien=         cursor.getInt(3);
            int    maHoatDong=     cursor.getInt(4);
            String tenHoatDong= cursor.getString(5);
            int    maDongTien=     cursor.getInt(6);
            String tenDong=     cursor.getString(7);

            BanGhi banGhi= new BanGhi(ma, noiDung, thoiGian,
                    soTien, maHoatDong, tenHoatDong, maDongTien, tenDong);
            banGhiAdapter.add(banGhi);
        }
    }
}
