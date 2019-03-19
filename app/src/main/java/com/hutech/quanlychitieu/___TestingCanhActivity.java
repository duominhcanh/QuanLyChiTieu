package com.hutech.quanlychitieu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.hutech.adapter.BanGhiAdapter;
import com.hutech.model.BanGhi;

public class ___TestingCanhActivity extends AppCompatActivity {

    ListView lvTab1;
    BanGhiAdapter banGhiAdapter;

    String DATABASE_NAME="quanlychitieu.db";
    SQLiteDatabase database=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_____testing_canh);


        loadControls();
        loadData();
    }


    private void loadData() {
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

    private void loadControls() {
        lvTab1= findViewById(R.id.lvTab1);
        banGhiAdapter= new BanGhiAdapter(___TestingCanhActivity.this, R.layout.mainlistitem);
        lvTab1.setAdapter(banGhiAdapter);
    }
}
