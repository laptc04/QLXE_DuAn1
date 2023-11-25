/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xe.dao;

import java.util.ArrayList;
import java.util.List;
import xe.entity.MauXe;
import xe.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class MauXeDAO extends XeDAO<MauXe, String> {

    String INSERT_SQL = "INSERT INTO Mau_xe(MaMX, TenMX) VALUES(?,?)";
    String UPDATE_SQL = "UPDATE Mau_Xe SET TenMX=? WHERE MaMX=?";
    String DELETE_SQL = "DELETE FROM Mau_xe WHERE MaMX=?";
    String SELECT_ALL_SQL = "SELECT * FROM Mau_xe";
    String SELECT_BY_ID_SQL = "SELECT * FROM Mau_Xe WHERE MaMX=?";
    String SELECT_BY_MX = "select TenMX from Mau_xe where TenMX=?";

    @Override
    public void insert(MauXe entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaMX(),
                entity.getTenMX());
    }

    @Override
    public void update(MauXe entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getTenMX(),
                entity.getMaMX());
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public MauXe selectById(String id) {
        List<MauXe> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<MauXe> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<MauXe> selectBySQL(String sql, Object... args) {
        List<MauXe> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                MauXe entity = new MauXe();
                entity.setMaMX(rs.getString("MaMX"));
                entity.setTenMX(rs.getString("TenMX"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<MauXe> selectByMX(String Tenmx) {
        String SQL = "SELECT MaMX FROM Mau_xe WHERE TenMX = ?";
        return this.selectByMaMX(SQL, Tenmx);
    }
    
    protected List<MauXe> selectByMaMX(String sql, Object... args) {
        List<MauXe> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                MauXe entity = new MauXe();
                entity.setMaMX(rs.getString("MaMX"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
