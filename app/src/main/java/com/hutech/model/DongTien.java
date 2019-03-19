package com.hutech.model;

public class DongTien {
    private int maDong;
    private String tenDong;

    public DongTien(int maDong, String tenDong) {
        this.maDong = maDong;
        this.tenDong = tenDong;
    }

    public DongTien() {
    }

    public int getMaDong() {
        return maDong;
    }

    public void setMaDong(int maDong) {
        this.maDong = maDong;
    }

    public String getTenDong() {
        return tenDong;
    }

    public void setTenDong(String tenDong) {
        this.tenDong = tenDong;
    }
}
