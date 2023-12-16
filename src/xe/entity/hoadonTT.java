/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xe.entity;

import java.util.Date;



/**
 *
 * @author BND6699
 */
public class hoadonTT {

     String MaHoaDon;
    String Makhachhang;
    String tenkh;
    String UserID;
    String nameID;
    boolean trangthai;
    Date Ngay;
    long TongTien;

    public hoadonTT() {
    }

    public hoadonTT(String MaHoaDon, String Makhachhang, String tenkh, String UserID, String nameID, boolean trangthai, Date Ngay, long TongTien) {
        this.MaHoaDon = MaHoaDon;
        this.Makhachhang = Makhachhang;
        this.tenkh = tenkh;
        this.UserID = UserID;
        this.nameID = nameID;
        this.trangthai = trangthai;
        this.Ngay = Ngay;
        this.TongTien = TongTien;
    }

    public String getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(String MaHoaDon) {
        this.MaHoaDon = MaHoaDon;
    }

    public String getMakhachhang() {
        return Makhachhang;
    }

    public void setMakhachhang(String Makhachhang) {
        this.Makhachhang = Makhachhang;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public boolean isTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }

    public Date getNgay() {
        return Ngay;
    }

    public void setNgay(Date Ngay) {
        this.Ngay = Ngay;
    }

    public long getTongTien() {
        return TongTien;
    }

    public void setTongTien(long TongTien) {
        this.TongTien = TongTien;
    }
    
}
