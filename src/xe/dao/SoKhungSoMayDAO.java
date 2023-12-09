/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xe.dao;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import xe.entity.SoKhungSoMay;
import xe.utils.XJdbc;

/**
 *
 * @author ADMIN
 */
public class SoKhungSoMayDAO extends XeDAO<SoKhungSoMay, String> {

    String INSERT_SQL = "INSERT INTO Sokhung_Somay(MaXe,TenHX,MaDX,Sokhung,Somay) VALUES(?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE Sokhung_Somay SET MaXe=?,TenHX=?,MaDX=?,Sokhung=?,Somay=? WHERE MaSKSM=?";
    String DELETE_SQL = "DELETE FROM Sokhung_Somay WHERE MaSKSM=?";
    String SELECT_ALL_SQL = "SELECT * FROM Sokhung_Somay";
    String SELECT_BY_ID_SQL = "SELECT * FROM Sokhung_Somay WHERE MaSKSM=?";
    String SELECT_BY_ID_SKSM = "SELECT * FROM Sokhung_Somay WHERE MaXe=?";

    @Override
    public void insert(SoKhungSoMay entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaXe(),
                entity.getTenHX(),
                entity.getMaDX(),
                entity.getSokhung(),
                entity.getSomay()
        );
    }

    @Override
    public void update(SoKhungSoMay entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getMaXe(),
                entity.getTenHX(),
                entity.getMaDX(),
                entity.getSokhung(),
                entity.getSomay(),
                entity.getMaSKSM());
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public SoKhungSoMay selectById(String id) {
        List<SoKhungSoMay> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public SoKhungSoMay selectBySKSM(String id) {
        List<SoKhungSoMay> list = this.selectBySQL(SELECT_BY_ID_SKSM, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<SoKhungSoMay> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<SoKhungSoMay> selectBySQL(String sql, Object... args) {
        List<SoKhungSoMay> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                SoKhungSoMay entity = new SoKhungSoMay();
                entity.setMaSKSM(rs.getInt("MaSKSM"));
                entity.setMaXe(rs.getInt("MaXe"));
                entity.setTenHX(rs.getString("TenHX"));
                entity.setMaDX(rs.getInt("MaDX"));
                entity.setSokhung(rs.getString("Sokhung"));
                entity.setSomay(rs.getString("Somay"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<SoKhungSoMay> selectBysm(String TenSK) {
        String SQL = "SELECT Somay FROM Sokhung_Somay WHERE Sokhung= ?";
        return this.selectBysm(SQL, TenSK);
    }

    protected List<SoKhungSoMay> selectBysm(String sql, Object... args) {
        List<SoKhungSoMay> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                SoKhungSoMay entity = new SoKhungSoMay();
                entity.setSomay(rs.getString("Somay"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    protected List<SoKhungSoMay> selectBysk(String maxe) {
        String SQL = "SELECT MaXe,Sokhung FROM Sokhung_Somay where Maxe=?";
        return this.selectBysk(SQL,maxe);
    }

    public List<SoKhungSoMay> selectBysk(String sql, Object... args) {
        List<SoKhungSoMay> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                SoKhungSoMay entity = new SoKhungSoMay();
                entity.setMaXe(rs.getInt("Maxe"));
                entity.setSokhung(rs.getString("Sokhung"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
