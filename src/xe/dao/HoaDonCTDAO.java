/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xe.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import xe.entity.HoaDonCT;
import xe.entity.KhachHang;
import xe.utils.XJdbc;

/**
 *
 * @author ADMIN
 */
public class HoaDonCTDAO extends XeDAO<HoaDonCT, String> {

    String INSERT_SQL = "INSERT INTO Hoa_don_chi_tiet(MaHD, MaXe,TenXe,TenHX, TenDX,TenLX,TenMX,Dungtich,Sokhung,Somay,Gia,Soluong,Thanhtien) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE Hoa_don_chi_tiet SET TenKH=?, Sodienthoai = ?, Diachi=? , Ngaytao = ? WHERE MaKH=?";
    String DELETE_SQL = "DELETE FROM Hoa_don_chi_tiet WHERE MaHDCT=?";
    String SELECT_ALL_SQL = "SELECT * FROM Hoa_don_chi_tiet";
    String SELECT_BY_ID_SQL = "SELECT * FROM Hoa_don_chi_tiet WHERE MaHDCT=?";

    @Override
    public void insert(HoaDonCT entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaHD(),
                entity.getMaXe(),
                entity.getTenXe(),
                entity.getTenHang(),
                entity.getTenDX(),
                entity.getTenLX(),
                entity.getTenMX(),
                entity.getDungtich(),
                entity.getSokhung(),
                entity.getSomay(),
                entity.getGia(),
                entity.getSoluong(),
                entity.getThanhtien()
        );
    }

    @Override
    public void update(HoaDonCT entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public HoaDonCT selectById(String id) {
        List<HoaDonCT> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDonCT> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<HoaDonCT> selectBySQL(String sql, Object... args) {
        List<HoaDonCT> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                HoaDonCT entity = new HoaDonCT();
                entity.setHoaDonCT(rs.getInt("MaHDCT"));
                entity.setMaHD(rs.getInt("MaHD"));
                entity.setMaXe(rs.getInt("MaXe"));
                entity.setTenXe(rs.getString("TenXe"));
                entity.setTenHang(rs.getString("TenHX"));
                entity.setTenDX(rs.getString("TenDX"));
                entity.setTenLX(rs.getString("TenLX"));
                entity.setTenMX(rs.getString("TenMX"));
                entity.setDungtich(rs.getString("Dungtich"));
                entity.setSokhung(rs.getString("Sokhung"));
                entity.setSomay(rs.getString("Somay"));
                entity.setGia(rs.getInt("Gia"));
                entity.setSoluong(rs.getInt("Soluong"));
                entity.setThanhtien(rs.getInt("Thanhtien"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    

}
