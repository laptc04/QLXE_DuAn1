/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xe.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import xe.entity.NhanVien;
import xe.utils.XJdbc;

/**
 *
 * @author balis
 */
public class NhanVienDAO extends XeDAO<NhanVien, String> {

    String INSERT_SQL = "INSERT INTO Nhan_Vien(MaNV, TenNV, Matkhau,Sodienthoai,Diachi,Hinh_anh,Vaitro) VALUES(?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE Nhan_Vien SET TenNV=?, Sodienthoai = ?,Diachi = ?, Hinh_anh = ?,Vaitro=? WHERE MaNV=?";
    String UPDATE_SQL_CT = "UPDATE Nhan_Vien SET TenNV=?, Sodienthoai = ?,Diachi = ?, Hinh_anh = ? WHERE MaNV=?";
    String UPDATE_SQL_MK = "UPDATE Nhan_Vien SET Matkhau=? WHERE MaNV=?";
    String DELETE_SQL = "DELETE FROM Nhan_Vien WHERE MaNV=?";
    String SELECT_ALL_SQL = "SELECT * FROM Nhan_Vien order by Vaitro";
    String SELECT_BY_ID_SQL = "SELECT * FROM Nhan_Vien WHERE MaNV=?";

    @Override
    public void insert(NhanVien entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaNV(),
                entity.getTenNV(),
                entity.getMatKhau(),
                entity.getSDT(),
                entity.getDiaChi(),
                entity.getHinhAnh(),
                entity.isVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getTenNV(),
                entity.getSDT(),
                entity.getDiaChi(),
                entity.getHinhAnh(),
                entity.isVaiTro(),
                entity.getMaNV());
    }

    public void updateCT(NhanVien entity) {
        XJdbc.executeUpdate(UPDATE_SQL_CT,
                entity.getTenNV(),
                entity.getSDT(),
                entity.getDiaChi(),
                entity.getHinhAnh(),
                entity.getMaNV());
    }
    
    public void updateMK(NhanVien entity) {
        XJdbc.executeUpdate(UPDATE_SQL_MK,
                entity.getMatKhau(),
                entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<NhanVien> selectBySQL(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setTenNV(rs.getString("TenNV"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setSDT(rs.getString("Sodienthoai"));
                entity.setDiaChi(rs.getString("Diachi"));
                entity.setHinhAnh(rs.getString("Hinh_anh"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<NhanVien> selectByVaitro(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                entity.setMaNV(rs.getString("MaNV"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
