/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import xe.entity.LoaiXe;
import xe.utils.XJdbc;

/**
 *
 * @author ADMIN
 */
public class LoaiXeDAO extends XeDAO<LoaiXe, String> {

    String INSERT_SQL = "INSERT INTO Loai_xe(MaLX, TenLX) VALUES(?,?)";
    String UPDATE_SQL = "UPDATE Loai_xe SET TenLX=? WHERE MaLX=?";
    String DELETE_SQL = "DELETE FROM Loai_xe WHERE MaLX=?";
    String SELECT_ALL_SQL = "SELECT * FROM Loai_xe";
    String SELECT_BY_ID_SQL = "SELECT * FROM Loai_xe WHERE MaLX=?";

    @Override
    public void insert(LoaiXe entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaLX(),
                entity.getTenLX());
    }

    @Override
    public void update(LoaiXe entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getTenLX(),
                entity.getMaLX());
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public LoaiXe selectById(String id) {
        List<LoaiXe> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LoaiXe> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<LoaiXe> selectBySQL(String sql, Object... args) {
        List<LoaiXe> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                LoaiXe entity = new LoaiXe();
                entity.setMaLX(rs.getString("MaLX"));
                entity.setTenLX(rs.getString("TenLX"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<LoaiXe> selectByLX(String TenLx) {
        String SQL = "SELECT MaLX FROM Loai_xe WHERE TenLX = ?";
        return this.selectByMaLX(SQL, TenLx);
    }

    protected List<LoaiXe> selectByMaLX(String sql, Object... args) {
        List<LoaiXe> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                LoaiXe entity = new LoaiXe();
                entity.setMaLX(rs.getString("MaLX"));
//                entity.setMaMX(rs.getString("MaMX"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
