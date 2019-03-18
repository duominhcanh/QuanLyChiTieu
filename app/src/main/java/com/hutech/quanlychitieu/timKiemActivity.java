package com.hutech.quanlychitieu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class timKiemActivity extends AppCompatActivity {
    Spinner spinerDanhMuc;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        addControls();
    }

    private void addControls() {
        spinerDanhMuc= findViewById(R.id.spinerDanhMuc);
        adapter= new ArrayAdapter<String>(timKiemActivity.this,
                android.R.layout.simple_spinner_item);
        adapter.add("Tất cả danh mục");
        adapter.add("Thu");
        adapter.add("Chi");
        adapter.add("Cho vay");
        adapter.add("Nợ");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerDanhMuc.setAdapter(adapter);
    }
}
