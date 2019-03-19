package com.hutech.model;

import android.widget.ArrayAdapter;

public class BanGhi {
    private int ma;
    private String noiDung;
    private String thoiGian;
    private int soTien;
    private int maHoatDong;
    private String tenHoatDong;
    private int maDongTien;
    private String tenDongTien;

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
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

    public String getTenDongTien() {
        return tenDongTien;
    }

    public void setTenDongTien(String tenDongTien) {
        this.tenDongTien = tenDongTien;
    }

    public BanGhi(int ma, String noiDung, String thoiGian, int soTien, int maHoatDong, String tenHoatDong, int maDongTien, String tenDongTien) {
        this.ma = ma;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.soTien = soTien;
        this.maHoatDong = maHoatDong;
        this.tenHoatDong = tenHoatDong;
        this.maDongTien = maDongTien;
        this.tenDongTien = tenDongTien;
    }

    public BanGhi(int ma, String noiDung, String thoiGian, int soTien, int maHoatDong) {
        this.ma = ma;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.soTien = soTien;
        this.maHoatDong = maHoatDong;
    }

    public BanGhi() {
    }
}
