package com.hutech.model;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class DanhMuc {
    private  String ma;
    private  String ten;
    private ArrayList<BanGhi> banGhis = new ArrayList<>();

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public DanhMuc(String ma, String ten) {
        this.ma = ma;
        this.ten = ten;
    }

    public DanhMuc() {
    }

    @Override
    public String toString() {
        return this.ten;

    }
}
