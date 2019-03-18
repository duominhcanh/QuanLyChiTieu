package com.hutech.model;

import java.util.ArrayList;

public class DanhMuc {
    private int ma;
    private String noiDung;
    private String thoiGian;
    private String soTien;

    private ArrayList<BanGhi> banGhis;

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

    public DanhMuc(int ma, String noiDung, String thoiGian, String soTien) {
        this.ma = ma;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.soTien = soTien;
    }

    public DanhMuc() {
    }

    @Override
    public String toString() {
        return "DanhMuc{" +
                "ma=" + this.ma +
                ", noiDung='" + this.noiDung + '\'' +
                ", thoiGian='" + this.thoiGian + '\'' +
                ", soTien='" + this.soTien + '\'' +
                '}';
    }
}
