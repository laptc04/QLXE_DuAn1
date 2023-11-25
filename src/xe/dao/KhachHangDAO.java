/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xe.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import xe.entity.KhachHang;
import xe.utils.XJdbc;

/**
 *
 * @author ADMIN
 */
public class KhachHangDAO extends XeDAO<KhachHang, String>{

    String INSERT_SQL = "INSERT INTO Khach_Hang(MaKH, TenKH, Diachi,Sodienthoai,Ngaytao) VALUES(?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE Khach_Hang SET TenKH=?, Sodienthoai = ?, Diachi=? , Ngaytao = ? WHERE MaKH=?";
    String DELETE_SQL = "DELETE FROM Khach_Hang WHERE MaKH=?";
    String SELECT_ALL_SQL = "SELECT * FROM Khach_Hang";
    String SELECT_BY_ID_SQL = "SELECT * FROM Khach_Hang WHERE MaKH=?";
    
    @Override
    public void insert(KhachHang entity) {
        XJdbc.executeUpdate(INSERT_SQL,
                entity.getMaKH(),
                entity.getTenKH(),
                entity.getDiachi(),
                entity.getSodienthoai(),
                entity.getNgaytao());
    }

    @Override
    public void update(KhachHang entity) {
        XJdbc.executeUpdate(UPDATE_SQL,
                entity.getTenKH(),
                entity.getSodienthoai(),
                entity.getDiachi(),
                entity.getNgaytao(),
                entity.getMaKH());
    }

    @Override
    public void delete(String id) {
        XJdbc.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public KhachHang selectById(String id) {
        List<KhachHang> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<KhachHang> selectBySQL(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKH(rs.getString("MaKH"));
                entity.setTenKH(rs.getString("TenKH"));
                entity.setDiachi(rs.getString("Diachi"));
                entity.setSodienthoai(rs.getString("Sodienthoai"));
                entity.setNgaytao(rs.getDate("Ngaytao"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
