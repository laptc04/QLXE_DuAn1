/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xe.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import xe.entity.HoaDon;
import xe.entity.KhachHang;
import xe.utils.XJdbc;

/**
 *
 * @author ADMIN
 */
public class HoaDonDAO extends XeDAO<HoaDon, String> {

    String INSERT_SQL = "INSERT INTO Hoa_don(MaKH, TenKH, MaNV,TenNV,Trangthai,Ngaytao,Tongtien) VALUES(?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE Hoa_don SET MaKH=?, TenKH=?, MaNV = ?, TenNV=? ,Trangthai=?, Ngaytao = ?,Tongtien=? WHERE MaHD=?";
    String DELETE_SQL = "DELETE FROM Hoa_don WHERE MaHD=?";
    String DELETE_SQL_HDCT = "DELETE FROM Hoa_don_chi_tiet WHERE MaHD=?";
    String SELECT_ALL_SQL = "SELECT * FROM Hoa_don order by MaKH,Trangthai";
    String SELECT_BY_ID_SQL = "SELECT * FROM Hoa_don WHERE MaHD=?";

    @Override
    public void insert(HoaDon entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaKH(),
                entity.getTenKH(),
                entity.getMaNV(),
                entity.getTenNV(),
                entity.isTrangThai(),
                entity.getNgayTao(),
                entity.getTongTien());
    }

    @Override
    public void update(HoaDon entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getMaKH(),
                entity.getTenKH(),
                entity.getMaNV(),
                entity.getTenNV(),
                entity.isTrangThai(),
                entity.getNgayTao(),
                entity.getTongTien(),
                entity.getMaHD());
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL_HDCT, id);
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public HoaDon selectById(String id) {
        List<HoaDon> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDon> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<HoaDon> selectBySQL(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHD(rs.getInt("MaHD"));
                entity.setMaKH(rs.getString("MaKH"));
                entity.setTenKH(rs.getString("TenKH"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setTenNV(rs.getString("TenNV"));
                entity.setTrangThai(rs.getBoolean("Trangthai"));
                entity.setNgayTao(rs.getDate("Ngaytao"));
                entity.setTongTien(rs.getInt("Tongtien"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
