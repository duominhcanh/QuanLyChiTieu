package com.hutech.model;

public class BanGhi {
    private int ma;
    private String noiDung;
    private String thoiGian;
    private String soTien;
    private String maHoatDong;

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

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public String getMaHoatDong() {
        return maHoatDong;
    }

    public void setMaHoatDong(String maHoatDong) {
        this.maHoatDong = maHoatDong;
    }

    public BanGhi(int ma, String noiDung, String thoiGian, String soTien, String maHoatDong) {
        this.ma = ma;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.soTien = soTien;
        this.maHoatDong = maHoatDong;
    }

    public BanGhi() {
    }
}
