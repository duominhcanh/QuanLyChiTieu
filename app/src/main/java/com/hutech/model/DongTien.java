package com.hutech.model;

public class DongTien {
    private int maDongTien;
    private String tenDOngTien;

    public int getMaDongTien() {
        return maDongTien;
    }

    public void setMaDongTien(int maDongTien) {
        this.maDongTien = maDongTien;
    }

    public String getTenDOngTien() {
        return tenDOngTien;
    }

    public void setTenDOngTien(String tenDOngTien) {
        this.tenDOngTien = tenDOngTien;
    }

    public DongTien(int maDongTien, String tenDOngTien) {
        this.maDongTien = maDongTien;
        this.tenDOngTien = tenDOngTien;
    }

    public DongTien() {
    }
}
