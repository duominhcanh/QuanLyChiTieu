package com.hutech.quanlychitieu;

import android.app.DatePickerDialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.hutech.adapter.BanGhiAdapter;
import com.hutech.model.BanGhi;
import com.hutech.model.DongTien;
import com.hutech.model.StringWithTag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class timKiemActivity extends AppCompatActivity {
    String DATABASE_NAME="quanlychitieu.db";
    SQLiteDatabase database=null;

    Spinner spinnerDanhMuc;
    ArrayAdapter<DongTien> dongTienAdapter;
    ArrayAdapter<StringWithTag> spinnerAdapter;

    ListView lvKetQuaTim;
    BanGhiAdapter banGhiAdapter;

    DongTien selected=null;

    Button btnNgayBD;
    Calendar calendar= Calendar.getInstance();
    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");

    Button btnNgayKT;
    Calendar calendar1= Calendar.getInstance();

    ImageView imgX;
    EditText edtMieuTa, edtSoTienMuonTim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        addControls();
        addEvent();

        loadData();

    }

    private void addEvent() {
        spinnerDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected=dongTienAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnNgayBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moManHinhDatePickerDialog();
            }
        });
        btnNgayKT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moManHinhDatePickerDialog1();
            }
        });

        imgX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaThongTinDaNhap();
            }
        });
    }

    private void xoaThongTinDaNhap() {
        edtSoTienMuonTim.setText(" ");
        edtMieuTa.setText(" ");
        edtMieuTa.setHint("Nhập mô tả khoản thu/chi");
        edtSoTienMuonTim.setHint("Nhập số tiền bạn muốn tìm");
    }

    private void moManHinhDatePickerDialog1() {
        DatePickerDialog.OnDateSetListener callBack= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                btnNgayKT.setText(simpleDateFormat.format(calendar1.getTime()));

            }
        };
        DatePickerDialog dialog=new DatePickerDialog(
                timKiemActivity.this,
                callBack,
                calendar1.get(Calendar.YEAR),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();

    }

    private void moManHinhDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callBack= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                btnNgayBD.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        DatePickerDialog dialog=new DatePickerDialog(
                timKiemActivity.this,
                callBack,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();

    }
    private void loadData() {
        database= openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor= database.rawQuery(
                "SELECT *" +
                        "from DongTien", null);
        while(cursor.moveToNext()){
            int    maDong=    cursor.getInt(0);
            String tenDong=     cursor.getString(1);

            DongTien dongTien= new DongTien(maDong,tenDong);
            dongTienAdapter.add(dongTien);

            spinnerAdapter.add(new StringWithTag(tenDong,maDong));
        }
    }
    private void addControls() {
        spinnerDanhMuc = findViewById(R.id.spinerDanhMuc);
        dongTienAdapter = new ArrayAdapter(timKiemActivity.this,
                android.R.layout.simple_list_item_1);
        spinnerAdapter = new ArrayAdapter(timKiemActivity.this,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinnerDanhMuc.setAdapter(spinnerAdapter);


        lvKetQuaTim= findViewById(R.id.lvKetQuaTim);
        banGhiAdapter= new BanGhiAdapter(timKiemActivity.this,
                R.layout.mainlistitem);
        lvKetQuaTim.setAdapter(banGhiAdapter);


        btnNgayBD=findViewById(R.id.btnNgayBD);
        btnNgayKT=findViewById(R.id.btnNgayKT);

        edtMieuTa=findViewById(R.id.edtMieuTa);
        edtSoTienMuonTim=findViewById(R.id.edtSoTienMuonTim);
        imgX= findViewById(R.id.imgX);
    }

    public void xuLyTim(View view) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date date1 = format.parse("20/3/2018");
        Date date2 = format.parse("20/2/2018");



        database= openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        String tenMieuTa= edtMieuTa.getText().toString();
        int soTienTim;
        try{
            soTienTim=Integer.parseInt(edtSoTienMuonTim.getText().toString());
        }
        catch (Exception e){
            soTienTim=0;
            //Toast.makeText(this, "Nhập số tiền muốn tìm!", Toast.LENGTH_SHORT).show();
        }


        Cursor cursor= database.rawQuery(
                "select  b.ma, b.noiDung,  b.thoiGian, b.soTien, b.maHoatDong, h.tenHoatDong, h.maDongTien, d.tenDong  "+
                        "from BanGhi b, DongTien d, HoatDong h "+
                        "where b.maHoatDong= h.maHoatDong and h.maDongTien= d.maDong and b.soTien >="+ soTienTim
                , null);
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

    public void xuLythoat(View view) {
        finish();
    }
}
