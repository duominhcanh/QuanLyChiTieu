package com.hutech.quanlychitieu;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hutech.adapter.BanGhiAdapter;
import com.hutech.model.BanGhi;
import com.hutech.model.DanhMuc;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;

public class timKiemActivity extends AppCompatActivity {
   Spinner spinnerDanhMuc;
   ArrayAdapter<DanhMuc> danhMucAdapter;

    ListView lvKetQuaTim;
    BanGhiAdapter banGhiAdapter;

    DanhMuc selected=null;

    Button btnNgayBD;
    Calendar calendar= Calendar.getInstance();
    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");

    Button btnNgayKT;
    Calendar calendar1= Calendar.getInstance();

    ImageView imgX;
    EditText edtMoTa, edtSoTienTim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        addControls();
        addEvent();
    }

    private void addEvent() {
        spinnerDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected=danhMucAdapter.getItem(position);
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
        edtSoTienTim.setText(" ");
        edtMoTa.setText(" ");
        edtSoTienTim.setHint("Nhập mô tả khoản thu/chi");
        edtSoTienTim.setHint("Nhập số tiền bạn muốn tìm");
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

    private void addControls() {
        spinnerDanhMuc = findViewById(R.id.spinerDanhMuc);
        danhMucAdapter= new ArrayAdapter<DanhMuc>(timKiemActivity.this,
                android.R.layout.simple_spinner_item);
        danhMucAdapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinnerDanhMuc.setAdapter(danhMucAdapter);

        lvKetQuaTim= findViewById(R.id.lvKetQuaTim);
        banGhiAdapter= new BanGhiAdapter(timKiemActivity.this,
                R.layout.mainlistitem);

        danhMucAdapter.add(new DanhMuc("tatca","Hiển thị tất cả"));
        danhMucAdapter.add(new DanhMuc("thu","Thu"));
        danhMucAdapter.add(new DanhMuc("chi","Chi"));
        danhMucAdapter.add(new DanhMuc("no","Nợ"));

        btnNgayBD=findViewById(R.id.btnNgayBD);
        btnNgayKT=findViewById(R.id.btnNgayKT);

        edtMoTa=findViewById(R.id.edtMieuTa);
        edtSoTienTim=findViewById(R.id.edtSoTienMuonTim);
        imgX= findViewById(R.id.imgX);
    }



}
