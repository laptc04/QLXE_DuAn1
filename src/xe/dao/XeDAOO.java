/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import xe.entity.DongXe;
import xe.entity.NhanVien;
import xe.entity.Xe;
import xe.utils.XJdbc;

/**
 *
 * @author ADMIN
 */
public class XeDAOO extends XeDAO<Xe, String> {

    String INSERT_SQL = "INSERT INTO Xe_may(MaXe,TenXe,TenHX,MaDX,MaLX,MaMX,Sokhung,Somay,Dungtich,Gia,Hinh_anh,Soluong) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE Xe_may SET TenXe=?,TenHX=?,MaDX=?, MaLX=?,MaMX=?,Sokhung=?,Somay=?,Dungtich=?,Gia=?,Hinh_anh=?,soluong=? WHERE MaXe=?";
    String DELETE_SQL = "DELETE FROM Xe_may WHERE MaXe=?";
    String SELECT_ALL_XE = "select * from Xe_may";
    String SELECT_ALL_SQL = "select MaXe,TenXe,Dong_xe.TenHX,Dong_xe.TenDX,Loai_xe.TenLX,Mau_xe.TenMX,Sokhung,Somay,Dungtich,Gia,Hinh_anh,Soluong from Xe_may join Loai_xe on Xe_may.MaLX=Loai_xe.MaLX\n"
            + "join Mau_xe on Mau_xe.MaMX=Xe_may.MaMX\n"
            + "join Dong_xe on Dong_xe.MaDX=Xe_may.MaDX\n"
            + "join Hang_xe on Hang_xe.TenHX=Xe_may.TenHX order by Hang_xe.TenHX";
    String SELECT_BY_ID_SQL = "select MaXe,TenXe,Dong_xe.TenHX,Dong_xe.TenDX,Loai_xe.TenLX,Mau_xe.TenMX,Sokhung,Somay,Dungtich,Gia,Hinh_anh,Soluong from Xe_may join Loai_xe on Xe_may.MaLX=Loai_xe.MaLX\n"
            + "join Mau_xe on Mau_xe.MaMX=Xe_may.MaMX\n"
            + "join Dong_xe on Dong_xe.MaDX=Xe_may.MaDX\n"
            + "join Hang_xe on Hang_xe.TenHX=Xe_may.TenHX where MaXe =?";

    String SELECT_MX = "select MaMX from Mau_xe where TenMX=?";
    String SELECT_ML = "select MaLX from Loai_xe where TenLX=?";
    String SELECT_MD = "select MaDX from Dong_xe where TenDX=?";

    @Override
    public void insert(Xe entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaXe(),
                entity.getTenXe(),
                entity.getTenHX(),
                entity.getMaDX(),
                entity.getMaLX(),
                entity.getMaMX(),
                entity.getSokhung(),
                entity.getSomay(),
                entity.getDungtich(),
                entity.getGia(),
                entity.getHinh_anh(),
                entity.getSoluong());
    }

    @Override
    public void update(Xe entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getTenXe(),
                entity.getTenHX(),
                entity.getMaDX(),
                entity.getMaLX(),
                entity.getMaMX(),
                entity.getSokhung(),
                entity.getSomay(),
                entity.getDungtich(),
                entity.getGia(),
                entity.getHinh_anh(),
                entity.getSoluong(),
                entity.getMaXe());
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public Xe selectById(String id) {
        List<Xe> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Xe> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    public List<Xe> selectSQLXe() {
        return this.selectBySQLXe(SELECT_ALL_XE);
    }

//    public List<Xe> selectDongXe(String  id) {
//        return this.selectBySQL(SELECT_MD,id);
//    }
    @Override
    protected List<Xe> selectBySQL(String sql, Object... args) {
        List<Xe> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                Xe entity = new Xe();
                entity.setMaXe(rs.getString("MaXe"));
                entity.setTenXe(rs.getString("TenXe"));
                entity.setTenHX(rs.getString("TenHX"));
                entity.setMaDX(rs.getString("TenDX"));
                entity.setMaLX(rs.getString("TenLX"));
                entity.setMaMX(rs.getString("TenMX"));
                entity.setSokhung(rs.getString("SoKhung"));
                entity.setSomay(rs.getString("Somay"));
                entity.setDungtich(rs.getString("Dungtich"));
                entity.setGia(rs.getString("Gia"));
                entity.setHinh_anh(rs.getString("Hinh_anh"));
                entity.setSoluong(rs.getString("Soluong"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<Xe> selectBySQLXe(String sql, Object... args) {
        List<Xe> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                Xe entity = new Xe();
                entity.setMaXe(rs.getString("MaXe"));
                entity.setTenXe(rs.getString("TenXe"));
                entity.setTenHX(rs.getString("TenHX"));
                entity.setMaDX(rs.getString("MaDX"));
                entity.setMaLX(rs.getString("MaLX"));
                entity.setMaMX(rs.getString("MaMX"));
                entity.setSokhung(rs.getString("SoKhung"));
                entity.setSomay(rs.getString("Somay"));
                entity.setDungtich(rs.getString("Dungtich"));
                entity.setGia(rs.getString("Gia"));
                entity.setHinh_anh(rs.getString("Hinh_anh"));
                entity.setSoluong(rs.getString("Soluong"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
