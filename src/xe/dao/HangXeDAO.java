/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import xe.entity.HangXe;
import xe.utils.XJdbc;

/**
 *
 * @author ADMIN
 */
public class HangXeDAO extends XeDAO<HangXe, String> {

    String INSERT_SQL = "INSERT INTO Hang_xe(TenHX) VALUES(?)";
//    String UPDATE_SQL = "UPDATE Hang_Xe SET TenHX=? WHERE TenHX=?";
//    String UPDATE_SQL_DongXe = "UPDATE Dong_Xe SET TenHX=? WHERE TenHX=?";
    String DELETE_SQL = "DELETE FROM Hang_xe WHERE TenHX=?";
    String DELETE_SQL_DongXe = "DELETE FROM Dong_xe WHERE TenHX=?";
    String DELETE_SQL_Xe = "DELETE FROM Xe_may WHERE TenHX=?";
    String SELECT_ALL_SQL = "SELECT * FROM Hang_xe";
    String SELECT_ALL_HX = "SELECT * FROM Hang_xe";
    String SELECT_BY_ID_SQL = "SELECT * FROM Hang_Xe WHERE TenHX=?";
    String SELECT_BY_ID = "SELECT TenHX FROM Hang_Xe WHERE TenHX=?";

    @Override
    public void insert(HangXe entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getTenHX());
    }

    @Override
    public void update(HangXe entity) {
//        XJdbc.executeUpdate(UPDATE_SQL_DongXe,
//                entity.getTenHX(),
//                entity.getTenHX());
//        XJdbc.executeUpdate(UPDATE_SQL,
//                entity.getTenHX(),
//                entity.getTenHX());

    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL_DongXe, id);
        XJdbc.executeUpdate(DELETE_SQL, id);
    }
    public void deleteAll(String id) {
        XJdbc.executeUpdate(DELETE_SQL_Xe, id);
        XJdbc.executeUpdate(DELETE_SQL_DongXe, id);
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public HangXe selectById(String id) {
        List<HangXe> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public HangXe selectTenHX(String id) {
        List<HangXe> list = this.selectBySQL(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HangXe> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }
    
    public List<HangXe> selectAllHX() {
        return this.selectBySQL(SELECT_ALL_HX);
    }

    @Override
    protected List<HangXe> selectBySQL(String sql, Object... args) {
        List<HangXe> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                HangXe entity = new HangXe();
                entity.setTenHX(rs.getString("TenHX"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
