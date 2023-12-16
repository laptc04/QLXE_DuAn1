/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xe.entity;

/**
 *
 * @author BND6699
 */
import java.util.ArrayList;
import javax.swing.JOptionPane;
import xe.utils.XJdbc;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.*;
import org.jfree.data.jdbc.JDBCXYDataset;

public class baocaoXL {

    static String connectionUrl
            = "jdbc:sqlserver://LAPTC-PC06384:1433;"
            + "databaseName=QLXE;"
            + "user=sa;"
            + "password=123;"
            + "encrypt=false;"
            + "trustServerCertificate=true;"
            + "loginTimeout=30;";

    //lay danh sach Baocao
    public ArrayList<hoadonTT> getRecords(String strngaybd, String strngaytk) {

        ArrayList<hoadonTT> list = new ArrayList<hoadonTT>();

        try (Connection conn = DriverManager.getConnection(connectionUrl);) {
            System.out.println("Kết nối thành công");
//            String CauLenh = "SELECT DienThoai.*, NguonNhap.TenNguon "
//                    + "FROM DienThoai "
//                    + "JOIN  NguonNhap "
//                    + "ON  DienThoai.NguonGoc =  NguonNhap.MaNguon";

            Statement ChayLenh = conn.createStatement();
            ResultSet rs = ChayLenh.executeQuery("SELECT * FROM hoa_don WHERE Ngaytao between '" + strngaybd + "' and '" + strngaytk + "' ");
            while (rs.next()) {
                hoadonTT hd = new hoadonTT(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6), rs.getDate(7), rs.getLong(8));
                list.add(hd);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return list;
    }

    public ArrayList<tongtienTT> getTong(String strngaybd, String strngaytk) {
        ArrayList<tongtienTT> list = new ArrayList<tongtienTT>();

        try (Connection conn = DriverManager.getConnection(connectionUrl);) {
            System.out.println("Kết nối thành công");
//            String CauLenh = "SELECT DienThoai.*, NguonNhap.TenNguon "
//                    + "FROM DienThoai "
//                    + "JOIN  NguonNhap "
//                    + "ON  DienThoai.NguonGoc =  NguonNhap.MaNguon";

            Statement ChayLenh = conn.createStatement();
            ResultSet rs = ChayLenh.executeQuery("SELECT  'TongTien'=SUM(hoa_don.TongTien)"
                    + "FROM hoa_don "
                    + "WHERE Ngaytao between '" + strngaybd + "' and '" + strngaytk + "' and Trangthai=1");

            while (rs.next()) {
                tongtienTT hd = new tongtienTT(rs.getLong(1));
                list.add(hd);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return list;

    }
}
