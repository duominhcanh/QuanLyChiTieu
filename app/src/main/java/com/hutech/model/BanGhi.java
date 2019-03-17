package com.hutech.model;

public class BanGhi {
    private int ma;
    private String noiDung;
    private String thoiGian;
    private String soTien;
    private String maDongTien;

    public BanGhi(int ma, String noiDung, String thoiGian, String soTien, String maDongTien) {
        this.ma = ma;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.soTien = soTien;
        this.maDongTien = maDongTien;
    }

    public BanGhi() {
    }

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

    public String getMaDongTien() {
        return maDongTien;
    }

    public void setMaDongTien(String maDongTien) {
        this.maDongTien = maDongTien;
    }
}
