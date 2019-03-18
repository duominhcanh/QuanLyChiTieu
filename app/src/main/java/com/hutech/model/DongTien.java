package com.hutech.model;

public class DongTien {
    private int maDongTien;
    private String tenDongTien;

    public int getMaDongTien() {
        return maDongTien;
    }

    public void setMaDongTien(int maDongTien) {
        this.maDongTien = maDongTien;
    }

    public String getTenDongTien() {
        return tenDongTien;
    }

    public void setTenDongTien(String tenDongTienngTien) {
        this.tenDongTien = tenDongTien;
    }

    public DongTien(int maDongTien, String tenDongTien) {
        this.maDongTien = maDongTien;
        this.tenDongTien = tenDongTien;
    }

    public DongTien() {
    }
}
