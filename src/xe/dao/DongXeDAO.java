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
import xe.entity.LoaiXe;
import xe.utils.XJdbc;

/**
 *
 * @author ADMIN
 */
public class DongXeDAO extends XeDAO<DongXe, String> {

    String INSERT_SQL = "INSERT INTO Dong_xe(TenDX, TenHX, Baohanh) VALUES(?,?,?)";
    String UPDATE_SQL = "UPDATE Dong_Xe SET TenDX=? ,TenHX=?, Baohanh=? WHERE MaDX=?";
    String UPDATE_SQL_TenHX = "update Xe_may set TenHX=? where MaDX=?";
    String UPDATE_SQL_TenHX_SKSM = "update Sokhung_Somay set TenHX=? where MaDX=?";
    String DELETE_SQL = "DELETE FROM Dong_xe WHERE MaDX=?";
    String DELETE_SQL_Xe = "DELETE FROM Xe_may WHERE MaDX=?";
    String DELETE_SQL_SKSMXe = "DELETE FROM Sokhung_Somay WHERE MaDX=?";
    String SELECT_ALL_SQL = "SELECT * FROM Dong_xe order by TenHX,TenDX";
    String SELECT_BY_ID_SQL = "SELECT * FROM Dong_Xe WHERE MaDX=?";
    String SELECT_BY_IDHX = "SELECT * FROM Dong_Xe WHERE TenHX=?";
    String SELECT_BY_DX = "SELECT TenDX FROM Dong_Xe WHERE TenHX=?";

    @Override
    public void insert(DongXe entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getTenDX(),
                entity.getTenHX(),
                entity.getBaohanh());
    }

    @Override
    public void update(DongXe entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getTenDX(),
                entity.getTenHX(),
                entity.getBaohanh(),
                entity.getMaDX());
        XJdbc.executeUpdate(UPDATE_SQL_TenHX,
                entity.getTenHX(),
                entity.getMaDX());
        XJdbc.executeUpdate(UPDATE_SQL_TenHX_SKSM,
                entity.getTenHX(),
                entity.getMaDX());
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL_SKSMXe, id);
        XJdbc.executeUpdate(DELETE_SQL_Xe, id);
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public DongXe selectById(String id) {
        List<DongXe> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public DongXe selectHangXe(String id) {
        List<DongXe> list = this.selectBySQL(SELECT_BY_IDHX, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public DongXe selectDX(String id) {
        List<DongXe> list = this.selectBySQL(SELECT_BY_DX, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<DongXe> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<DongXe> selectBySQL(String sql, Object... args) {
        List<DongXe> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                DongXe entity = new DongXe();
                entity.setMaDX(rs.getInt("MaDX"));
                entity.setTenDX(rs.getString("TenDX"));
                entity.setTenHX(rs.getString("TenHX"));
                entity.setBaohanh(rs.getInt("Baohanh"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DongXe> selectByDX(String Tendx) {
        String SQL = "SELECT MaDX FROM Dong_xe WHERE TenDX = ?";
        return this.selectByMaDX(SQL, Tendx);
    }

    protected List<DongXe> selectByMaDX(String sql, Object... args) {
        List<DongXe> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                DongXe entity = new DongXe();
                entity.setMaDX(rs.getInt("MaDX"));
//                entity.setMaLX(rs.getString("MaLX"));
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
