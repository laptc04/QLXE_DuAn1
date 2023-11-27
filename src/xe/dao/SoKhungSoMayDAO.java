/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xe.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import xe.entity.KhachHang;
import xe.entity.SoKhungSoMay;
import xe.utils.XJdbc;

/**
 *
 * @author ADMIN
 */
public class SoKhungSoMayDAO extends XeDAO<SoKhungSoMay, String> {

    String INSERT_SQL = "INSERT INTO Sokhung_Somay(MaXe,Sokhung,Somay) VALUES(?,?,?)";
    String UPDATE_SQL = "UPDATE Sokhung_Somay SET MaXe=?,Sokhung=?,Somay=? WHERE MaSKSM=?";
    String DELETE_SQL = "DELETE FROM Sokhung_Somay WHERE MaSKSM=?";
    String SELECT_ALL_SQL = "SELECT * FROM Sokhung_Somay";
    String SELECT_BY_ID_SQL = "SELECT * FROM Sokhung_Somay WHERE MaSKSM=?";
    String SELECT_BY_ID_SKSM = "SELECT * FROM Sokhung_Somay WHERE MaXe=?";

    @Override
    public void insert(SoKhungSoMay entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaXe(),
                entity.getSokhung(),
                entity.getSomay()
        );
    }

    @Override
    public void update(SoKhungSoMay entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getMaXe(),
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

}