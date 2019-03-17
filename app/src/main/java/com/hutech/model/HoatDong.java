package com.hutech.model;

public class HoatDong {
    private int maHoatDong;
    private String tenHoatDong;
    private int maDongTien;
    private int tienMacDinh;

    public HoatDong(int maHoatDong, String tenHoatDong, int maDongTien, int tienMacDinh) {
        this.maHoatDong = maHoatDong;
        this.tenHoatDong = tenHoatDong;
        this.maDongTien = maDongTien;
        this.tienMacDinh = tienMacDinh;
    }

    public HoatDong() {
    }

    public int getMaHoatDong() {
        return maHoatDong;
    }

    public void setMaHoatDong(int maHoatDong) {
        this.maHoatDong = maHoatDong;
    }

    public String getTenHoatDong() {
        return tenHoatDong;
    }

    public void setTenHoatDong(String tenHoatDong) {
        this.tenHoatDong = tenHoatDong;
    }

    public int getMaDongTien() {
        return maDongTien;
    }

    public void setMaDongTien(int maDongTien) {
        this.maDongTien = maDongTien;
    }

    public int getTienMacDinh() {
        return tienMacDinh;
    }

    public void setTienMacDinh(int tienMacDinh) {
        this.tienMacDinh = tienMacDinh;
    }
}
