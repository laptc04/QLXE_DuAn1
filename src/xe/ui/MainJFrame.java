package xe.ui;

import java.awt.CardLayout;
import com.itextpdf.text.Anchor;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.jdi.connect.spi.Connection;
import java.beans.Statement;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.io.FileOutputStream;
import xe.dao.DongXeDAO;
import xe.dao.HangXeDAO;
import xe.dao.HoaDonCTDAO;
import xe.dao.HoaDonDAO;
import xe.dao.KhachHangDAO;
import xe.dao.LoaiXeDAO;
import xe.dao.MauXeDAO;
import xe.dao.NhanVienDAO;
import xe.dao.SoKhungSoMayDAO;
import xe.dao.XeDAOO;
import xe.entity.DongXe;
import xe.entity.HangXe;
import xe.entity.HoaDon;
import xe.entity.HoaDonCT;
import xe.entity.KhachHang;
import xe.entity.LoaiXe;
import xe.entity.MauXe;
import xe.entity.NhanVien;
import xe.entity.SoKhungSoMay;
import xe.entity.Xe;
import xe.utils.Auth;
import xe.utils.MsgBox;
import xe.utils.XDate;
import xe.utils.XImage;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import xe.entity.HoaDonCT;
import xe.utils.XJdbc;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import static java.lang.String.format;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import static java.text.MessageFormat.format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import org.codehaus.groovy.control.Phases;
import xe.entity.baocaoXL;
import xe.entity.hoadonTT;
import xe.entity.tongtienTT;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author ADMIN
 */
public class MainJFrame extends javax.swing.JFrame {

    SoKhungSoMayDAO daosksm = new SoKhungSoMayDAO();
    HoaDonCTDAO daohdct = new HoaDonCTDAO();
    HoaDonDAO daohd = new HoaDonDAO();
    XeDAOO daoxe = new XeDAOO();
    DongXeDAO daodx = new DongXeDAO();
    HangXeDAO daohx = new HangXeDAO();
    MauXeDAO daomx = new MauXeDAO();
    KhachHangDAO daokh = new KhachHangDAO();
    NhanVienDAO dao = new NhanVienDAO();
    LoaiXeDAO daolx = new LoaiXeDAO();
    int row = -1;
    int sl = -1;
    CardLayout card;
    JFileChooser fileChooser = new JFileChooser();
    hoadonTT selectedHoaDon = null;
    baocaoXL baocaoservices = new baocaoXL();
    String date1 = XDate.toString(new Date(), "dd-mm-YYYY");
    ArrayList<hoadonTT> dsHoaDon = new ArrayList<>();
    ArrayList<tongtienTT> dsTongTien = new ArrayList<>();

    /**
     * Creates new form Test
     */
    public MainJFrame() {
//        setLocationRelativeTo(null);

        //txtNgayTao.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
        //startClock();
        initComponents();
        card = (CardLayout) (JPanelCard.getLayout());
        this.displayUserInfo();
        this.fillTableNhanVien();
        //this.updateStatusNhanVien();
        this.fillTableKhachHang();
        this.updateStatusKhachHang();
        this.fillTableLoaiXe();
        this.updateStatusLoaiXe();
        this.fillTableMauXe();
        this.updateStatusMauXe();
        this.fillTableHangXe();
        this.updateStatusHangXe();
        this.fillComboBoxHangXe();
        this.fillTableDongXe();
        this.updateStatusDongXe();
        this.fillTableXe();
        this.updateStatusXe();
        this.fillComboBoxLoaiXe();
        this.fillComboBoxMauXe();
        this.fillComboBoxHX();
        this.fillTableSKSMXe();
        this.updateStatusSKSMXe();
        this.fillComboBoxXe();
        this.clearFormSKSMXe();
        this.checkHangXe();
        this.fillTableKH();
        this.fillTableHD();
        this.updateStatusKh();
        this.fillTableSP();
    }

//    void startClock() {
////        new Thread() {
////            @Override
////            public void run() {
////                while (true) {
////                    Calendar ca = new GregorianCalendar();
////                    int gio = ca.get(Calendar.HOUR);
////                    int phut = ca.get(Calendar.MINUTE);
////                    int giay = ca.get(Calendar.SECOND);
////                    int AM_PM = ca.get(Calendar.AM_PM);
////                    String day_night;
////                    if (AM_PM == 1) {
////                        day_night = "PM";
////                    } else {
////                        day_night = "AM";
////                    }
////                    lblTime.setText(gio + ":" + phut + ":" + giay + ":" + day_night);
////                }
////            }
////        }.start();
//        class TimeClock extends Thread {
//
//            @Override
//            public void run() {
//                while (true) {
//                    lblTime.setText(new SimpleDateFormat("hh:mm:ss a").format(Calendar.getInstance().getTime()));
//                }
//            }
//        }
//        TimeClock timeClock = new TimeClock();
//        timeClock.start();
//    }
    //---------------------------------------------------------thông tin người sử dụng--------------------------------
    void displayUserInfo() {
        String tenNV = Auth.user.getTenNV();
        String userID = Auth.user.getMaNV();
        String anhNV = Auth.user.getHinhAnh();
        String sdt = Auth.user.getSDT();
        String n = "Chưa thêm ảnh!";
        String role = Auth.user.isVaiTro() ? "Quản lý" : "Nhân viên";
        lblTenNV.setText("" + tenNV);
        lblMaNVCT.setText(userID);
        txtTenNVCT.setText(tenNV);
        txtSoDienThoaiCT.setText(sdt);
        txtMatKhauCT.setText(Auth.user.getMatKhau());
        lblVaiTroNV.setText(role);
        txtDiaChiNVCT.setText(Auth.user.getDiaChi());
        if (anhNV != null) {
            lblAnh.setToolTipText(anhNV);
            ImageIcon icon = XImage.readGiaoDien(anhNV);
            lblAnh.setIcon(icon);
            lblAnhNVCT.setIcon(icon);
        }
        if (anhNV.equals(n)) {
            ImageIcon icon = XImage.readGiaoDien("AvtTrang.png");
            lblAnh.setIcon(icon);
            lblAnhNVCT.setIcon(icon);
        }
    }

    void UserInfoCT() {
        String tenNV = Auth.user.getTenNV();
        String userID = Auth.user.getMaNV();
        String anhNV = Auth.user.getHinhAnh();
        String sdt = Auth.user.getSDT();
        String n = "Chưa thêm ảnh!";
        String role = Auth.user.isVaiTro() ? "Quản lý" : "Nhân viên";
        lblTenNV.setText("" + tenNV);
        lblMaNVCT.setText(userID);
        txtTenNVCT.setText(tenNV);
        txtSoDienThoaiCT.setText(sdt);
        txtMatKhauCT.setText(Auth.user.getMatKhau());
        lblVaiTroNV.setText(role);
        txtDiaChiNVCT.setText(Auth.user.getDiaChi());
        if (anhNV != null) {
            lblAnh.setToolTipText(anhNV);
            ImageIcon ico = XImage.readAnhNV(anhNV);
            lblAnhNVCT.setToolTipText(anhNV);
            lblAnhNVCT.setIcon(ico);
        }
        if (anhNV.equals(n)) {
            ImageIcon icon = XImage.readAnhNV("AvtTrang.png");
            lblAnh.setIcon(icon);
            lblAnhNVCT.setIcon(icon);
        }
    }

    //----------------------------------------------------------thêm ảnh------------------------------------------------
    void choosePicture() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            fileChooser.setDialogTitle("Chọn ảnh");
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName());
            lblHinhAnh.setToolTipText(file.getName());
            lblHinhAnh.setIcon(icon);
        }
    }

    void choosePictureXe() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            fileChooser.setDialogTitle("Chọn ảnh");
            File file = fileChooser.getSelectedFile();
            XImage.saveAnhXe(file);
            ImageIcon icon = XImage.readAnhXe(file.getName());
            lblAnhXe.setToolTipText(file.getName());
            lblAnhXe.setIcon(icon);
        }
    }

    void choosePictureNV() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            fileChooser.setDialogTitle("Chọn ảnh");
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.readAnhNV(file.getName());
            lblAnhNVCT.setToolTipText(file.getName());
            lblAnhNVCT.setIcon(icon);
        }
    }

    //-----------------------------------------------------------form nhân viên------------------------------------------
    private NhanVien getFormNhanVien() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNV.getText());
        nv.setTenNV(txtTenNV.getText());
        nv.setMatKhau(new String(txtMatKhau.getPassword()));
        nv.setSDT(txtSodienthoai.getText());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setHinhAnh(lblHinhAnh.getToolTipText());
        nv.setVaiTro(rdoQuanLy.isSelected());
        return nv;
    }

    //Cài đặt
    private NhanVien getFormNhanVienCT() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(lblMaNVCT.getText());
        nv.setTenNV(txtTenNVCT.getText());
        nv.setMatKhau(new String(txtMatKhauCT.getPassword()));
        nv.setSDT(txtSoDienThoaiCT.getText());
        nv.setDiaChi(txtDiaChiNVCT.getText());
        nv.setHinhAnh(lblAnhNVCT.getToolTipText());
        return nv;
    }

    void setFormNhanVien(NhanVien nv) {
        txtMaNV.setEditable(false);
        txtMaNV.setText(nv.getMaNV());
        txtTenNV.setText(nv.getTenNV());
        txtMatKhau.setEnabled(false);
        txtMatKhau2.setEnabled(false);
        txtMatKhau.setText("");
        txtMatKhau2.setText("");
        txtSodienthoai.setText(nv.getSDT());
        txtDiaChi.setText(nv.getDiaChi());
        if (nv.getHinhAnh() != null) {
            lblHinhAnh.setToolTipText(nv.getHinhAnh());
            lblHinhAnh.setIcon(XImage.read(nv.getHinhAnh()));
        } else {
            lblHinhAnh.setToolTipText("null");
            lblHinhAnh.setIcon(null);
        }
        rdoQuanLy.setSelected(nv.isVaiTro());
        rdoNhanVien.setSelected(!nv.isVaiTro());
        if (lblHinhAnh.getToolTipText().equalsIgnoreCase("Chưa thêm ảnh!")) {
            btnXoaAnhNhanVien.setEnabled(false);
        } else {
            btnXoaAnhNhanVien.setEnabled(true);
        }
        if (rdoQuanLy.isSelected()) {
            btnThemNhanvien.setEnabled(false);
            btnSuaNhanVien.setEnabled(false);
            btnXoaNhanVien.setEnabled(false);
            rdoQuanLy.setEnabled(false);
            rdoNhanVien.setEnabled(false);
            txtTenNV.setEditable(false);
            txtSodienthoai.setEditable(false);
            txtDiaChi.setEditable(false);
            btnXoaAnhNhanVien.setEnabled(false);
        } else {
            btnThemNhanvien.setEnabled(false);
            btnSuaNhanVien.setEnabled(true);
            btnXoaNhanVien.setEnabled(true);
            rdoQuanLy.setEnabled(true);
            rdoNhanVien.setEnabled(true);
            txtTenNV.setEditable(true);
            txtSodienthoai.setEditable(true);
            txtDiaChi.setEditable(true);
            if (lblHinhAnh.getToolTipText().equalsIgnoreCase("Chưa thêm ảnh!")) {
                btnXoaAnhNhanVien.setEnabled(false);
            } else {
                btnXoaAnhNhanVien.setEnabled(true);
            }
        }

    }

    void updateStatusNhanVien() {
        boolean edit = (this.row >= 0);
        txtMaNV.setEditable(!edit);
        btnThemNhanvien.setEnabled(!edit);
        btnSuaNhanVien.setEnabled(edit);
        btnXoaNhanVien.setEnabled(edit);
    }

    void editNhanVien() {
        String manv = (String) tblNhanVien.getValueAt(this.row, 0);
        NhanVien nv = dao.selectById(manv);
        //this.updateStatusNhanVien();
        this.setFormNhanVien(nv);
        NhanVienJTabbedPane.setSelectedIndex(0);

    }

    void clearFormNhanVien() {
        NhanVien nv = new NhanVien();
        this.setFormNhanVien(nv);
        txtMatKhau.setEnabled(true);
        txtMatKhau2.setEnabled(true);
        txtMatKhau.setText("");
        txtMatKhau2.setText("");
        btnXoaAnhNhanVien.setEnabled(false);
        lblHinhAnh.setIcon(null);
        lblHinhAnh.setToolTipText("Chưa thêm ảnh!");
        txtSodienthoai.setText("");
        this.row = -1;
        this.updateStatusNhanVien();
        btnGChucVu.clearSelection();
        txtMaNV.setEditable(true);
        txtMaNV.requestFocus();
    }

    void fillTableNhanVien() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = dao.selectAll();
            for (NhanVien nv : list) {
                if (nv.getDiaChi().isBlank()) {
                    Object[] rows = {nv.getMaNV(), nv.getTenNV(), nv.getSDT(), nv.getDiaChi() + "Chưa thêm địa chỉ!", nv.isVaiTro() ? "Quản lý" : "Nhân viên", nv.getHinhAnh() + ""};
                    model.addRow(rows);
                } else {
                    Object[] rows = {nv.getMaNV(), nv.getTenNV(), nv.getSDT(), nv.getDiaChi(), nv.isVaiTro() ? "Quản lý" : "Nhân viên", nv.getHinhAnh() + ""};
                    model.addRow(rows);
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi dữ liệu!");
        }
    }

    void insertNhanVien() {
        List<NhanVien> list = dao.selectAll();
        NhanVien nv = getFormNhanVien();
        String manv = txtMaNV.getText();
        String sdt = txtSodienthoai.getText();
        String mk2 = new String(txtMatKhau2.getPassword());
        if (txtMaNV.getText().isBlank() || txtMatKhau.getText().isBlank() || txtMatKhau2.getText().isBlank() || txtTenNV.getText().isBlank() || btnGChucVu.isSelected(null)
                || txtSodienthoai.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else if (txtSodienthoai.getText().length() > 10 || txtSodienthoai.getText().length() < 10) {
            MsgBox.alert(this, "Số điện thoại không hợp lệ!");
            return;
        } else {
            if (!mk2.equals(nv.getMatKhau())) {
                MsgBox.alert(this, "Mật khẩu nhập lại không chính xác!");
            } else {
                if (!Auth.isManager() && rdoQuanLy.isSelected()) {
                    MsgBox.alert(this, "Nhân viên không thể thêm!");
                } else {
                    for (NhanVien nv1 : list) {
                        if (nv1.getMaNV().equalsIgnoreCase(txtMaNV.getText())) {
                            MsgBox.alert(this, "Mã " + txtMaNV.getText() + " đã tồn tại!");
                            return;
                        } else if (nv1.getSDT().equalsIgnoreCase(sdt)) {
                            MsgBox.alert(this, "Số điện thoại đã tồn tại");
                            return;
                        }
                    }
                    try {
                        dao.insert(nv);
                        this.fillTableNhanVien();
                        this.clearFormNhanVien();
                        MsgBox.alert(this, "Thêm thành công");
                    } catch (Exception e) {
                        MsgBox.alert(this, "Thêm thất bại!");
                    }
                }
            }
        }
    }

    void updateNhanVien() {
        NhanVien nv = getFormNhanVien();
        String tennv = txtTenNV.getText();
        String manv = txtMaNV.getText();
        if (txtMaNV.getText().isBlank() || txtTenNV.getText().isBlank() || btnGChucVu.isSelected(null)
                || txtSodienthoai.getText().isBlank()) {
            MsgBox.alert(this, "Không để trống!");
            return;
        } else if (txtSodienthoai.getText().length() > 10 || txtSodienthoai.getText().length() < 10) {
            MsgBox.alert(this, "Số điện thoại không hợp lệ!");
            return;
        } else {
            try {
                dao.update(nv);
                this.fillTableNhanVien();
                this.displayUserInfo();
                MsgBox.alert(this, "Sửa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Sửa thất bại!");
            }
        }
    }

    //update cài đặt
    void updateNhanVienCT() {
        NhanVien nv = getFormNhanVienCT();
        String tennv = txtTenNVCT.getText();
        String manv = lblMaNVCT.getText();
        String diachi = txtDiaChiNVCT.getText();
        String sdt = txtSoDienThoaiCT.getText();
        if (tennv.isBlank() || txtSoDienThoaiCT.getText().isBlank()) {
            MsgBox.alert(this, "vui lòng Không để trống!");
            return;
        } else if (txtSoDienThoaiCT.getText().length() > 10 || txtSoDienThoaiCT.getText().length() < 10) {
            MsgBox.alert(this, "Số điện thoại không hợp lệ!");
            return;
        } else {
            try {
                dao.updateCT(nv);
                this.fillTableNhanVien();
                txtTenNVCT.setText(tennv);
                lblTenNV.setText(tennv);
                Auth.user.setTenNV(tennv);
                Auth.user.setHinhAnh(nv.getHinhAnh());
                Auth.user.setMatKhau(new String(txtMatKhauCT.getText()));
                Auth.user.setDiaChi(diachi);
                Auth.user.setSDT(sdt);
                lblAnhNVCT.setIcon(XImage.readAnhNV(nv.getHinhAnh()));
                lblAnh.setIcon(XImage.readGiaoDien(nv.getHinhAnh()));
                lblAnhNVCT.setToolTipText(nv.getHinhAnh());
                lblAnhNVCT.setToolTipText(nv.getHinhAnh());
                MsgBox.alert(this, "Đã cập nhật");
            } catch (Exception e) {
                MsgBox.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    void deleteNhanVien() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không được phép xóa!");
        } else {
            String manv = txtMaNV.getText();
            if (manv.equals(Auth.user.getMaNV())) {
                MsgBox.alert(this, "Bạn không thể xóa chính mình!");
            } else if (MsgBox.confirm(this, "Bạn có muốn xóa nhân viên này?\nTên NV: "
                    + txtTenNV.getText())) {
                try {
                    dao.delete(manv);
                    this.fillTableNhanVien();
                    this.clearFormNhanVien();
                    MsgBox.alert(this, "Xóa thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    //--------------------------------------------------------------form khách hàng--------------------------------------
    private KhachHang getFormKhachHang() {
        int i = 1;
        KhachHang kh = new KhachHang();
        List<KhachHang> list = daokh.selectAll();
        if (tblKhachHang.getRowCount() != 0) {
            for (KhachHang kh1 : list) {
                i += 1;
                kh.setMaKH("KH" + i);
                if (kh.getMaKH().equalsIgnoreCase(kh1.getMaKH())) {
                    i -= 1;
                    kh.setMaKH("KH" + i);
                    if (kh.getMaKH().equalsIgnoreCase(kh1.getMaKH())) {
                        i += 1;
                        kh.setMaKH("KH" + i);
                        break;
                    }
                    break;
                }
            }
        } else {
            kh.setMaKH("KH" + i);
        }
        kh.setTenKH(txtTenKH.getText());
        kh.setSodienthoai(txtSodienthoaiKH.getText());
        kh.setDiachi(txtDiaChiKH.getText());
        lblNgayTao.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
        return kh;
    }

    void setFormKhachHang(KhachHang kh) {
        lblTextMaKH.setText("Mã khách hàng");
        lblMaKH.setText(kh.getMaKH());
        txtTenKH.setText(kh.getTenKH());
        txtSodienthoaiKH.setText(kh.getSodienthoai());
        lblNgayTao.setText(XDate.toString(kh.getNgaytao(), "dd-MM-yyyy"));
        txtDiaChiKH.setText(kh.getDiachi());
    }

    void clearFormKhachHang() {
        lblTextMaKH.setText("");
        lblMaKH.setText("");
        txtTenKH.setText("");
        txtSodienthoaiKH.setText("");
        txtDiaChiKH.setText("");
        lblNgayTao.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
        this.row = -1;
        this.updateStatusKhachHang();
        txtTenKH.requestFocus();
    }

    void updateStatusKhachHang() {
        boolean edit = (this.row >= 0);
        //txtNgayTao.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
        btnThemKH.setEnabled(!edit);
        btnSuaKH.setEnabled(edit);
        btnXoaKH.setEnabled(edit);
    }

    void editKhachHang() {
        String makh = (String) tblKhachHang.getValueAt(this.row, 1);
        KhachHang kh = daokh.selectById(makh);
        this.setFormKhachHang(kh);
        KhachHangJTabbedPane.setSelectedIndex(0);
        this.updateStatusKhachHang();
    }

    void fillTableKhachHang() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        int i = 1;
        try {
            List<KhachHang> list = daokh.selectAll();
            for (KhachHang kh : list) {
                if (kh.getDiachi().isBlank()) {
                    Object[] row = {i++, kh.getMaKH(), kh.getTenKH(), kh.getSodienthoai(),
                        XDate.toString(kh.getNgaytao(), "dd-MM-yyyy"), kh.getDiachi() + "Chưa thêm địa chỉ"};
                    model.addRow(row);
                } else {
                    Object[] row = {i++, kh.getMaKH(), kh.getTenKH(), kh.getSodienthoai(),
                        XDate.toString(kh.getNgaytao(), "dd-MM-yyyy"), kh.getDiachi()};
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void insertKhachHang() {

        List<KhachHang> list = daokh.selectAll();
        KhachHang kh = getFormKhachHang();
        String sdt = txtSodienthoaiKH.getText();
        if (txtTenKH.getText().isBlank() || txtSodienthoaiKH.getText().isBlank() || lblNgayTao.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else if (txtSodienthoaiKH.getText().length() > 10 || txtSodienthoaiKH.getText().length() < 10) {
            MsgBox.alert(this, "Số điện thoại không hợp lệ!");
            return;
        } else {
            for (KhachHang kh1 : list) {
                if (kh1.getSodienthoai().equalsIgnoreCase(sdt)) {
                    MsgBox.alert(this, "Số điện thoại đã tồn tại");
                    return;
                }
            }
            try {
                daokh.insert(kh);
                this.fillTableKhachHang();
                this.clearFormKhachHang();
                MsgBox.alert(this, "Thêm thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm thất bại!");
            }
        }
    }

    void updateKhachHang() {
        KhachHang kh = getFormKhachHang();
        if (lblMaKH.getText().isBlank() || txtTenKH.getText().isBlank() || txtSodienthoaiKH.getText().isBlank() || lblNgayTao.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else if (txtSodienthoaiKH.getText().length() > 10 || txtSodienthoaiKH.getText().length() < 10) {
            MsgBox.alert(this, "Số điện thoại không hợp lệ!");
            return;
        } else {
            try {
                daokh.update(kh);
                this.fillTableKhachHang();
                this.clearFormKhachHang();
                MsgBox.alert(this, "Sửa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Sửa thất bại!");
            }
        }
    }

    void deleteKhachHang() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không được phép xóa!");
        } else {
            String makh = lblMaKH.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa khách hàng này?\nTên khách hàng: "
                    + txtTenKH.getText())) {
                try {
                    daokh.delete(makh);
                    this.fillTableKhachHang();
                    this.clearFormKhachHang();
                    MsgBox.alert(this, "Xóa thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    //------------------------------------------------------------form loại xe-------------------------------------------
    private LoaiXe getFormLoaiXe() {
        LoaiXe lx = new LoaiXe();
        lx.setMaLX(Integer.parseInt(lblMaLX.getText()));
        lx.setTenLX(txtTenLX.getText());
        return lx;
    }

    private LoaiXe getFormTenLoaiXe() {
        LoaiXe lx = new LoaiXe();
        lx.setTenLX(txtTenLX.getText());
        return lx;
    }

    void setFormLoaiXe(LoaiXe lx) {
        lblMaLoaiXe.setText("Mã loại xe");
        lblMaLX.setText(lx.getMaLX() + "");
        txtTenLX.setText(lx.getTenLX());
        btnLuuLX.setText("Lưu");
    }

    void clearFormLoaiXe() {
        lblMaLoaiXe.setText("");
        lblMaLX.setText("");
        txtTenLX.setText("");
        this.row = -1;
        this.updateStatusLoaiXe();
        txtTenLX.requestFocus();
        btnLuuLX.setText("Thêm");
    }

    void updateStatusLoaiXe() {
        boolean edit = (this.row >= 0);
        btnLuuLX.setEnabled(edit);
        btnXoaLX.setEnabled(edit);
    }

    void editLoaiXe() {
        Integer malx = (Integer) tblLoaiXe.getValueAt(this.row, 1);
        LoaiXe lx = daolx.selectById(malx.toString());
        this.setFormLoaiXe(lx);
        this.updateStatusLoaiXe();
    }

    void fillTableLoaiXe() {
        DefaultTableModel model = (DefaultTableModel) tblLoaiXe.getModel();
        model.setRowCount(0);
        int i = 1;
        try {
            List<LoaiXe> list = daolx.selectAll();
            for (LoaiXe lx : list) {
                Object[] row = {i++, lx.getMaLX(), lx.getTenLX()};
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void insertLoaiXe() {
        List<LoaiXe> list = daolx.selectAll();
        LoaiXe lx = getFormTenLoaiXe();
        if (txtTenLX.getText().isBlank() && lblMaLX.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            for (LoaiXe lx1 : list) {
                if (lx1.getTenLX().equalsIgnoreCase(txtTenLX.getText())) {
                    MsgBox.alert(this, "Tên " + txtTenLX.getText() + " đã tồn tại!");
                    return;
                }
            }
            if (lblMaLX.getText().isBlank()) {
                try {
                    daolx.insert(lx);
                    this.fillTableLoaiXe();
                    this.clearFormLoaiXe();
                    this.fillComboBoxLoaiXe();
                    this.fillTableXe();
//                    MsgBox.alert(this, "Thêm thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Thêm thất bại!");
                }
                return;
            } else {
                this.updateLoaiXe();
            }
        }
    }

    void updateLoaiXe() {
        LoaiXe lx = getFormLoaiXe();
        if (txtTenLX.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            try {
                daolx.update(lx);
                this.fillTableLoaiXe();
                this.clearFormLoaiXe();
                this.fillComboBoxLoaiXe();
                this.fillTableXe();
//                MsgBox.alert(this, "Sửa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Sửa thất bại!");
            }
        }
    }

    void deleteLoaiXe() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không được phép xóa!");
        } else {
            String malx = lblMaLX.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa loại xe này?\nMã loại xe: "
                    + lblMaLX.getText() + "\nTên loại xe: "
                    + txtTenLX.getText())) {
                try {
                    daolx.delete(malx);
                    this.fillTableLoaiXe();
                    this.clearFormLoaiXe();
                    this.fillComboBoxLoaiXe();
//                    MsgBox.alert(this, "Xóa thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    //-----------------------------------------------------------form màu xe----------------------------------------------
    private MauXe getFormMauXe() {
        MauXe mx = new MauXe();
        mx.setMaMX(Integer.parseInt(lblMaMX.getText()));
        mx.setTenMX(txtTenMX.getText());
        return mx;
    }

    private MauXe getFormTenMauXe() {
        MauXe mx = new MauXe();
        mx.setTenMX(txtTenMX.getText());
        return mx;
    }

    void setFormMauXe(MauXe mx) {
        lblMaMauXe.setText("Mã màu xe");
        lblMaMX.setText(mx.getMaMX() + "");
        txtTenMX.setText(mx.getTenMX());
        btnLuuMX.setText("Lưu");
    }

    void clearFormMauXe() {
        lblMaMauXe.setText("");
        lblMaMX.setText("");
        txtTenMX.setText("");
        this.row = -1;
        this.updateStatusMauXe();
        txtTenMX.requestFocus();
        btnLuuMX.setText("Thêm");
    }

    void updateStatusMauXe() {
        boolean edit = (this.row >= 0);
        btnLuuMX.setEnabled(edit);
        btnXoaMX.setEnabled(edit);
    }

    void editMauXe() {
        Integer mamx = (Integer) tblMauXe.getValueAt(this.row, 1);
        MauXe mx = daomx.selectById(mamx.toString());
        this.setFormMauXe(mx);
        this.updateStatusMauXe();
    }

    void fillTableMauXe() {
        DefaultTableModel model = (DefaultTableModel) tblMauXe.getModel();
        model.setRowCount(0);
        int i = 1;
        try {
            List<MauXe> list = daomx.selectAll();
            for (MauXe mx : list) {
                Object[] row = {i++, mx.getMaMX(), mx.getTenMX()};
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void insertMauXe() {
        List<MauXe> list = daomx.selectAll();
        MauXe mx = getFormTenMauXe();
        if (txtTenMX.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            for (MauXe mx1 : list) {
                if (mx1.getTenMX().equalsIgnoreCase(txtTenMX.getText())) {
                    MsgBox.alert(this, "Tên " + txtTenMX.getText() + " đã tồn tại!");
                    return;
                }
            }
            if (lblMaMX.getText().isBlank()) {
                try {
                    daomx.insert(mx);
                    this.fillTableMauXe();
                    this.clearFormMauXe();
                    this.fillComboBoxMauXe();
                    this.fillTableXe();
                    //MsgBox.alert(this, "Thêm thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Thêm thất bại!");
                }
            } else {
                this.updateMauXe();
            }
        }
    }

    void updateMauXe() {
        MauXe mx = getFormMauXe();
        if (txtTenMX.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            try {
                daomx.update(mx);
                this.fillTableMauXe();
                this.clearFormMauXe();
                this.fillComboBoxMauXe();
                this.fillTableXe();
                //MsgBox.alert(this, "Sửa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Sửa thất bại!");
            }
        }
    }

    void deleteMauXe() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không được phép xóa!");
        } else {
            String mamx = lblMaMX.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa màu xe này?\nMã màu: "
                    + lblMaMX.getText() + "\nTên màu: "
                    + txtTenMX.getText())) {
                try {
                    daomx.delete(mamx);
                    this.fillTableMauXe();
                    this.clearFormMauXe();
                    this.fillComboBoxMauXe();
                    //MsgBox.alert(this, "Xóa thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    //----------------------------------------------------------form hãng xe---------------------------------------------
    private HangXe getFormHangXe() {
        HangXe hx = new HangXe();
        hx.setTenHX(txtTenHX.getText());
        return hx;
    }

    void setFormHangXe(HangXe hx) {
        txtTenHX.setText(hx.getTenHX());
    }

    void clearFormHangXe() {
        //txtCreator.setText("");
        txtTenHX.setText("");
        this.row = -1;
        this.updateStatusHangXe();
        txtTenHX.requestFocus();
    }

    void updateStatusHangXe() {
        boolean edit = (this.row >= 0);
        btnThemHX.setEnabled(!edit);
        btnXoaHX.setEnabled(edit);
    }

    void editHangXe() {
        String tenhx = (String) tblHangXe.getValueAt(this.row, 1);
        HangXe hx = daohx.selectById(tenhx);
        this.setFormHangXe(hx);
        this.updateStatusHangXe();
    }

    void fillTableHangXe() {
        DefaultTableModel model = (DefaultTableModel) tblHangXe.getModel();
        model.setRowCount(0);
        int i = 1;
        try {
            List<HangXe> list = daohx.selectAll();
            for (HangXe hx : list) {
                Object[] row = {i++, hx.getTenHX()};
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.checkHangXe();
    }

    void insertHangXe() {
        List<HangXe> list = daohx.selectAll();
        HangXe hx = getFormHangXe();
        if (txtTenHX.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            for (HangXe hx1 : list) {
                if (hx1.getTenHX().equalsIgnoreCase(txtTenHX.getText())) {
                    MsgBox.alert(this, "Mã " + txtTenHX.getText() + " đã tồn tại!");
                    return;
                }
            }
            try {
                daohx.insert(hx);
                this.fillTableHangXe();
                this.fillComboBoxHangXe();
                this.fillComboBoxHX();
                this.clearFormHangXe();
                this.checkHangXe();
                MsgBox.alert(this, "Thêm thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm thất bại!");
            }
        }
    }

//    void updateHangXe() {
//        HangXe hx = getFormHangXe();
//        if (txtTenHX.getText().isBlank()) {
//            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
//            return;
//        } else {
//            try {
//                daohx.update(hx);
//                this.fillTableHangXe();
//                this.fillComboBoxHangXe();
//                this.clearFormHangXe();
//                MsgBox.alert(this, "Sửa thành công");
//            } catch (Exception e) {
//                MsgBox.alert(this, "Sửa thất bại!");
//            }
//        }
//    }
    void deleteHangXe() {
        List<SoKhungSoMay> list0 = daosksm.selectAll();
        List<Xe> list = daoxe.selectAll();
        List<DongXe> list1 = daodx.selectAll();
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không được phép xóa!");
        } else {
            String mahx = txtTenHX.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa hãng xe này?\nTên hãng: " + txtTenHX.getText())) {
                try {
                    for (SoKhungSoMay sksm : list0) {
                        if (sksm.getTenHX().equalsIgnoreCase(txtTenHX.getText())) {
                            if (MsgBox.confirm(this, "Chú ý!\n"
                                    + "Hãng xe[" + txtTenHX.getText() + "] đã có dữ liệu "
                                    + "nếu xóa sẽ xóa toàn bộ dữ liệu của hãng này!\n"
                                    + "Đồng ý xóa?")) {
                                daohx.deleteAll(mahx);
                                this.clearFormDongXe();
                                this.clearFormHangXe();
                                this.clearFormXe();
                                this.fillTableHangXe();
                                this.fillTableDongXe();
                                this.fillTableXe();
                                this.fillComboBoxHangXe();
                                this.fillComboBoxHX();
                                this.fillTableSKSMXe();
                                this.checkHangXe();
                                return;
                            } else {
                                return;
                            }
                        }
                    }
                    for (Xe hx1 : list) {
                        if (hx1.getTenHX().equalsIgnoreCase(txtTenHX.getText())) {
                            if (MsgBox.confirm(this, "Chú ý!\n"
                                    + "Hãng xe[" + txtTenHX.getText() + "] đã có dữ liệu "
                                    + "nếu xóa sẽ xóa toàn bộ dữ liệu của hãng này!\n"
                                    + "Đồng ý xóa?")) {
                                daohx.deleteAll(mahx);
                                this.clearFormDongXe();
                                this.clearFormHangXe();
                                this.clearFormXe();
                                this.fillTableHangXe();
                                this.fillTableDongXe();
                                this.fillTableXe();
                                this.fillComboBoxHangXe();
                                this.fillComboBoxHX();
                                this.checkHangXe();
                                return;
                            } else {
                                return;
                            }
                        }
                    }
                    for (DongXe hx1 : list1) {
                        if (hx1.getTenHX().equalsIgnoreCase(txtTenHX.getText())) {
                            if (MsgBox.confirm(this, "Chú ý!\n"
                                    + "Hãng xe[" + txtTenHX.getText() + "] đã có dữ liệu "
                                    + "nếu xóa sẽ xóa toàn bộ dữ liệu của hãng này!\n"
                                    + "Đồng ý xóa?")) {
                                daohx.delete(mahx);
                                this.clearFormDongXe();
                                this.clearFormHangXe();
                                this.clearFormXe();
                                this.fillTableHangXe();
                                this.fillTableDongXe();
                                this.fillTableXe();
                                this.fillComboBoxHangXe();
                                this.fillComboBoxHX();
                                this.checkHangXe();
                                return;
                            } else {
                                return;
                            }
                        }
                    }
                    daohx.deleteTH(mahx);
                    this.clearFormDongXe();
                    this.clearFormHangXe();
                    this.fillTableHangXe();
                    this.fillTableDongXe();
                    this.fillComboBoxHangXe();
                    this.fillComboBoxHX();
                    this.fillTableXe();
                    this.checkHangXe();
                    MsgBox.alert(this, "Xóa thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    //-----------------------------------------------------------form dòng xe--------------------------------------------
    private DongXe getFormDongXe() {
        DongXe dx = new DongXe();
        dx.setMaDX(Integer.parseInt(lblMaDX.getText()));
        dx.setTenDX(txtTenDX.getText());
        dx.setTenHX(cboTenHX.getSelectedItem().toString());
        dx.setBaohanh(Integer.parseInt(txtBaoHanh.getText()));
        return dx;
    }

    private DongXe getFormTenDongXe() {
        DongXe dx = new DongXe();
        dx.setTenDX(txtTenDX.getText());
        dx.setTenHX(cboTenHX.getSelectedItem().toString());
        if (!txtBaoHanh.getText().isBlank()) {
            dx.setBaohanh(Integer.parseInt(txtBaoHanh.getText()));
        }

        return dx;
    }

    void setFormDongXe(DongXe dx) {
        lblMaDX.setEnabled(true);
        lblMaDongXe.setText("Mã dòng xe");
        lblMaDX.setText(dx.getMaDX() + "");
        txtTenDX.setText(dx.getTenDX());
        cboTenHX.setSelectedItem(daodx.selectHangXe(dx.getTenHX().toString()));
        cboTenHX.setToolTipText(String.valueOf(dx.getTenHX()));
//        cboTenHX.setSelectedIndex(0);
        cboTenHX.setSelectedItem(cboTenHX.getToolTipText());
        txtBaoHanh.setText(dx.getBaohanh() + "");
    }

    void clearFormDongXe() {
        lblMaDongXe.setText("");
        lblMaDX.setText("");
        txtTenDX.setText("");
        cboTenHX.setSelectedIndex(0);
        txtBaoHanh.setText("");
        this.row = -1;
        this.updateStatusDongXe();
        txtTenDX.requestFocus();
    }

    void updateStatusDongXe() {
        boolean edit = (this.row >= 0);
        btnThemDX.setEnabled(!edit);
        btnSuaDX.setEnabled(edit);
        btnXoaDX.setEnabled(edit);
    }

    void editDongXe() {
        Integer tendx = (Integer) tblDongXe.getValueAt(this.row, 1);
        DongXe dx = daodx.selectById(tendx.toString());
        this.setFormDongXe(dx);
        this.updateStatusDongXe();
    }

    void fillComboBoxHangXe() {
        DefaultComboBoxModel model1 = (DefaultComboBoxModel) cboTenHX.getModel();
        model1.removeAllElements();
        List<HangXe> list = daohx.selectAll();
        if (tblHangXe.getRowCount() != 0) {
            for (HangXe hx : list) {
                model1.addElement(hx.getTenHX().toString());
                txtBaoHanh.setText("");
                txtTenDX.setEditable(true);
                txtTenDX.setEnabled(true);
                cboTenHX.setEditable(false);
                cboTenHX.setEnabled(true);
                cboTenHX.setBackground(Color.white);
                txtBaoHanh.setEnabled(true);
                btnThemDX.setEnabled(true);
                btnSuaDX.setEnabled(true);
                btnXoaDX.setEnabled(true);
                btnResetDX.setEnabled(true);
            }
        } else {
            txtTenDX.setEditable(false);
            txtTenDX.setEnabled(false);
            model1.removeAllElements();
            model1.addElement("Chưa có hãng xe không thể thêm dòng xe!");
            cboTenHX.setEditable(false);
            cboTenHX.setEnabled(false);
            cboTenHX.setBackground(Color.red);
            txtBaoHanh.setEnabled(false);
            btnThemDX.setEnabled(false);
            btnSuaDX.setEnabled(false);
            btnXoaDX.setEnabled(false);
            btnResetDX.setEnabled(false);
        }
    }

    void fillTableDongXe() {
        DefaultTableModel model = (DefaultTableModel) tblDongXe.getModel();
        model.setRowCount(0);
        int i = 1;
        try {
            List<DongXe> list = daodx.selectAll();
            for (DongXe dx : list) {
                Object[] row = {i++, dx.getMaDX(), dx.getTenDX(), dx.getTenHX(), dx.getBaohanh()};
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void insertDongXe() {
        DongXe dx = getFormTenDongXe();
        List<DongXe> list = daodx.selectAll();
        String tenhx = (String) cboTenHX.getSelectedItem();
        if (txtTenDX.getText().isBlank() || txtBaoHanh.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            for (DongXe dx1 : list) {
                if (dx1.getTenDX().equalsIgnoreCase(txtTenDX.getText()) && dx1.getTenHX().equalsIgnoreCase(tenhx)) {
                    MsgBox.alert(this, "Tên " + txtTenDX.getText() + " đã tồn tại!\n"
                            + "Hãng " + tenhx + " đã tồn tại!");
                    return;
                }
            }
            try {
                daodx.insert(dx);
                this.fillTableDongXe();
                this.fillComboBoxHangXe();
                this.clearFormDongXe();
                this.fillComboBoxDX();
                this.fillTableXe();
                MsgBox.alert(this, "Thêm thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm thất bại!");
            }
        }

    }

    void updateDongXe() {
        DongXe dx = getFormDongXe();
        if (txtTenDX.getText().isBlank() || txtBaoHanh.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            try {
                daodx.update(dx);
                this.fillTableDongXe();
                this.fillComboBoxHangXe();
                this.clearFormDongXe();
                //this.clearFormXe();
                this.fillComboBoxDX();
                this.fillTableXe();
                MsgBox.alert(this, "Sửa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Sửa thất bại!");
            }
        }
    }

    void deleteDongXe() {
        List<SoKhungSoMay> listsksm = daosksm.selectAll();
        List<Xe> list = daoxe.selectSQLXe();
        String tenhx = (String) cboTenHX.getSelectedItem();
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không được phép xóa!");
        } else {
            String madx = lblMaDX.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa dòng xe này?\nMã dòng: "
                    + lblMaDX.getText() + "\nTên dòng: " + txtTenDX.getText() + "\nTên hãng: " + tenhx)) {
                try {
                    for (SoKhungSoMay sksmxe : listsksm) {
                        if (madx.equalsIgnoreCase(sksmxe.getMaDX() + "")) {
                            if (MsgBox.confirm(this, "Chú ý!\n"
                                    + "Dòng xe[" + txtTenDX.getText() + "] đã có dữ liệu "
                                    + "nếu xóa sẽ xóa toàn bộ dữ liệu của dòng xe này!\n"
                                    + "Đồng ý xóa?")) {
                                daodx.delete(madx);
                                this.clearFormXe();
                                this.clearFormDongXe();
                                this.clearFormSKSMXe();
                                this.fillTableDongXe();
                                this.fillTableXe();
                                this.fillComboBoxDX();
                                this.fillComboBoxHX();
                                this.fillTableSKSMXe();
                                this.fillComboBoxXe();
                                return;
                            } else {
                                return;
                            }
                        }
                    }
                    for (Xe xe : list) {
                        if (xe.getMaDX().equalsIgnoreCase(madx)) {
                            if (MsgBox.confirm(this, "Chú ý!\n"
                                    + "Dòng xe[" + txtTenDX.getText() + "] đã có dữ liệu "
                                    + "nếu xóa sẽ xóa toàn bộ dữ liệu của dòng xe này!\n"
                                    + "Đồng ý xóa?")) {
                                daodx.delete(madx);
                                this.clearFormXe();
                                this.clearFormDongXe();
                                this.fillTableDongXe();
                                this.fillTableXe();
                                this.fillComboBoxDX();
                                this.fillComboBoxHX();
                                return;
                            } else {
                                return;
                            }
                        }
                    }
                    daodx.delete(madx);
                    this.fillTableDongXe();
                    this.clearFormDongXe();
                    this.fillComboBoxDX();
                    this.fillTableXe();
                    this.fillComboBoxHangXe();
                    MsgBox.alert(this, "Xóa thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    //-------------------------------------------------------form xe và danh sách xe--------------------------------------
    void fillTableXe() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachXe.getModel();
        model.setRowCount(0);
        int i = 1;
        NumberFormat fm = new DecimalFormat("#,###");
        try {
            List<Xe> list = daoxe.selectAll();
            for (Xe x : list) {
                Object[] row = {i++, x.getMaXe(), x.getTenXe(), x.getTenHX(), x.getMaDX(), x.getMaLX(), x.getMaMX(), x.getDungtich(), fm.format(x.getGia()), x.getSoluong()};
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void fillComboBoxLoaiXe() {
        DefaultComboBoxModel model1 = (DefaultComboBoxModel) cboLoaiXe.getModel();
        model1.removeAllElements();
        List<LoaiXe> list = daolx.selectAll();
        if (tblLoaiXe.getRowCount() != 0) {
            for (LoaiXe lx : list) {
                model1.addElement(lx.getTenLX().toString());
                cboLoaiXe.setEditable(false);
                cboLoaiXe.setEnabled(true);
                cboLoaiXe.setBackground(Color.white);
            }
        }
    }

    void fillComboBoxMauXe() {
        DefaultComboBoxModel model1 = (DefaultComboBoxModel) cboMauXe.getModel();
        model1.removeAllElements();
        List<MauXe> list = daomx.selectAll();
        if (tblMauXe.getRowCount() != 0) {
            for (MauXe mx : list) {
                model1.addElement(mx.getTenMX().toString());
                cboMauXe.setEditable(false);
                cboMauXe.setEnabled(true);
                cboMauXe.setBackground(Color.white);
            }
        }
    }

    void fillComboBoxHX() {
        DefaultComboBoxModel model1 = (DefaultComboBoxModel) cboHX.getModel();
        model1.removeAllElements();
        List<HangXe> list = daohx.selectAllHX();
        if (tblHangXe.getRowCount() != 0) {
            for (HangXe hx : list) {
                model1.addElement(hx.getTenHX().toString());
                cboHX.setEditable(false);
                cboHX.setEnabled(true);
                cboHX.setBackground(Color.white);
                this.fillComboBoxDX();
            }
        }
    }

    void fillComboBoxDX() {
        DefaultComboBoxModel model2 = (DefaultComboBoxModel) cboDongXe.getModel();
        model2.removeAllElements();
        List<DongXe> list = daodx.selectAll();
        if (tblDongXe.getRowCount() != 0) {
            for (DongXe dx : list) {
                if (dx.getTenHX().equalsIgnoreCase(cboHX.getToolTipText())) {
                    model2.addElement(dx.getTenDX().toString());
                    cboDongXe.setEditable(false);
                    cboDongXe.setEnabled(true);
                    cboDongXe.setBackground(Color.white);
                }
//                else if (model2.getSize()<0) {
//                    model2.removeAllElements();
//                    model2.addElement("Nhân viên chưa thêm dòng xe!");
//                    cboDongXe.setEditable(false);
//                    cboDongXe.setEnabled(false);
//                    cboDongXe.setBackground(Color.red);
//                }
            }
        }
    }

    //get form để update
    private Xe getFormXe() {
        String dx = (String) cboDongXe.getSelectedItem();
        String lx = (String) cboLoaiXe.getSelectedItem();
        String mx = (String) cboMauXe.getSelectedItem();
        String madx = "", malx = "", mamx = "";;
        List<DongXe> listdx = daodx.selectByDX(dx);
        List<LoaiXe> listlx = daolx.selectByLX(lx);
        List<MauXe> listmx = daomx.selectByMX(mx);
        for (DongXe x : listdx) {
            madx = x.getMaDX() + "";
            cboDongXe.setToolTipText(madx);
        }
        for (LoaiXe x : listlx) {
            malx = x.getMaLX() + "";
            cboLoaiXe.setToolTipText(malx);
        }
        for (MauXe x : listmx) {
            mamx = x.getMaMX() + "";
            cboMauXe.setToolTipText(mamx);
        }
//        System.out.println(cboHX.getSelectedItem().toString());
//        System.out.println(cboDongXe.getToolTipText());
//        System.out.println(cboMauXe.getToolTipText());
//        System.out.println(cboLoaiXe.getToolTipText());
        Xe x = new Xe();
        x.setMaXe(Integer.parseInt(lblMaXe.getText()));
        x.setTenXe(txtTenXe.getText());
        x.setTenHX(cboHX.getSelectedItem().toString());
        x.setMaDX(cboDongXe.getToolTipText());
        x.setMaMX(cboMauXe.getToolTipText());
        x.setMaLX(cboLoaiXe.getToolTipText());
        x.setDungtich(txtDunhtich.getText());
        if (!txtGia.getText().isBlank()) {
            String gia = txtGia.getText();
            NumberFormat format = new DecimalFormat("#,###");
            try {
                Number parsed = format.parse(gia);
                int result = parsed.intValue();
                //System.out.println(result);
                x.setGia(result);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        x.setSoluong(Integer.parseInt(txtSoLuong.getText()));
        x.setHinh_anh(lblAnhXe.getToolTipText());
        return x;
    }

    //get form để insert
    private Xe getFormTenXe() {
        int madx = cboDongXe.getSelectedIndex();
        if (madx > -1) {
            String a = cboDongXe.getItemAt(madx);
            List<DongXe> listdx = daodx.selectByDX(a);
            for (DongXe x : listdx) {
                a = x.getMaDX() + "";
                cboDongXe.setToolTipText(a);
            }
        }
        String lx = (String) cboLoaiXe.getSelectedItem();
        String mx = (String) cboMauXe.getSelectedItem();
        String malx = "", mamx = "";;
        List<LoaiXe> listlx = daolx.selectByLX(lx);
        List<MauXe> listmx = daomx.selectByMX(mx);
        for (LoaiXe x : listlx) {
            malx = x.getMaLX() + "";
            cboLoaiXe.setToolTipText(malx);
        }
        for (MauXe x : listmx) {
            mamx = x.getMaMX() + "";
            cboMauXe.setToolTipText(mamx);
        }
        Xe x = new Xe();
        //khóa chính tự sinh nên k cần phải thêm;
        x.setTenXe(txtTenXe.getText());
        x.setTenHX(cboHX.getSelectedItem().toString());
        x.setMaDX(cboDongXe.getToolTipText());
        x.setMaMX(cboMauXe.getToolTipText());
        x.setMaLX(cboLoaiXe.getToolTipText());
        x.setDungtich(txtDunhtich.getText());
        if (!txtGia.getText().isBlank()) {
            String gia = txtGia.getText();
            NumberFormat format = new DecimalFormat("#,###");
            try {
                Number parsed = format.parse(gia);
                int result = parsed.intValue();
                //System.out.println(result);
                x.setGia(result);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (!txtSoLuong.getText().isBlank()) {
            x.setSoluong(Integer.parseInt(txtSoLuong.getText()));
        }
        x.setHinh_anh(lblAnhXe.getToolTipText());
        return x;
    }

    void setFormXe(Xe x) {
        lblXe.setText("Mã xe");
        lblMaXe.setText(x.getMaXe() + "");
        txtTenXe.setText(x.getTenXe());
        cboHX.setSelectedItem(daodx.selectHangXe(x.getTenHX().toString()));
        cboHX.setToolTipText(String.valueOf(x.getTenHX()));
        cboHX.setSelectedItem(cboHX.getToolTipText());
        cboDongXe.setToolTipText(String.valueOf(x.getMaDX()));
        cboDongXe.setSelectedItem(cboDongXe.getToolTipText());
        cboMauXe.setToolTipText(String.valueOf(x.getMaMX()));
        cboMauXe.setSelectedItem(cboMauXe.getToolTipText());
        cboLoaiXe.setToolTipText(String.valueOf(x.getMaLX()));
        cboLoaiXe.setSelectedItem(cboLoaiXe.getToolTipText());
        txtDunhtich.setText(x.getDungtich());
        int gia = Integer.parseInt(x.getGia() + "");
        NumberFormat fm = new DecimalFormat("#,###");
        txtGia.setText(fm.format(gia));
        txtSoLuong.setText(x.getSoluong() + "");
        if (x.getHinh_anh() != null) {
            lblAnhXe.setToolTipText(x.getHinh_anh());
            lblAnhXe.setIcon(XImage.readAnhXe(x.getHinh_anh()));
        }
    }

    void clearFormXe() {
        lblXe.setText("");
        lblMaXe.setText("");
        txtTenXe.setText("");
        cboHX.setSelectedIndex(0);
//        cboDongXe.setSelectedIndex(0);
        cboMauXe.setSelectedIndex(0);
        cboLoaiXe.setSelectedIndex(0);
        txtDunhtich.setText("");
        txtGia.setText("");
        lblAnhXe.setIcon(null);
        lblAnhXe.setToolTipText(null);
        txtSoLuong.setText("");
        this.row = -1;
        txtTenXe.requestFocus();
        this.updateStatusXe();
    }

    void updateStatusXe() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblDanhSachXe.getRowCount() - 1);
        btnThemXe.setEnabled(!edit);
        btnSuaXe.setEnabled(edit);
        btnXoaXe.setEnabled(edit);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnFirst.setEnabled(edit && !first);
        btnLast.setEnabled(edit && !last);
    }

    void editXe() {
        Integer tenxe = (Integer) tblDanhSachXe.getValueAt(this.row, 1);
        Xe dx = daoxe.selectById(tenxe.toString());
        this.setFormXe(dx);
        XeMayJTabbedPane.setSelectedIndex(4);
        this.updateStatusXe();
    }

    void insertXe() {
        Xe dx = getFormTenXe();
        String dx1 = (String) cboDongXe.getSelectedItem();
        String lx = (String) cboLoaiXe.getSelectedItem();
        String mx = (String) cboMauXe.getSelectedItem();
        List<Xe> list1 = daoxe.selectAll();
        if (txtTenXe.getText().isBlank() || txtGia.getText().isBlank() || txtSoLuong.getText().isBlank()
                || txtDunhtich.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else if (lblAnhXe.getIcon() == null) {
            MsgBox.alert(this, "Vui lòng thêm ảnh xe!");
            return;
        } else {
            for (Xe x : list1) {
                if (x.getMaDX().equalsIgnoreCase(dx1) && x.getMaLX().equalsIgnoreCase(lx)
                        && x.getMaMX().equalsIgnoreCase(mx) && x.getTenHX().equalsIgnoreCase(cboHX.getSelectedItem().toString())) {
                    MsgBox.alert(this, "Thông tin xe này đã tồn tại!");
                    return;
                }
            }
            try {
                daoxe.insert(dx);
                this.fillTableXe();
                this.clearFormXe();
                this.fillTableSP();
                MsgBox.alert(this, "Thêm thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm thất bại!");
            }
        }

    }

    void updateXe() {
        Xe xe = getFormXe();
        String dx1 = (String) cboDongXe.getSelectedItem();
        String lx = (String) cboLoaiXe.getSelectedItem();
        String mx = (String) cboMauXe.getSelectedItem();
        List<Xe> list1 = daoxe.selectAll();
        if (txtTenXe.getText().isBlank() || txtGia.getText().isBlank() || txtSoLuong.getText().isBlank()
                || txtDunhtich.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
//            for (Xe x : list) {
//                if (x.getMaXe().equalsIgnoreCase(txtMaXe.getText())) {
//                    MsgBox.alert(this, "Mã " + txtMaXe.getText() + " đã tồn tại!");
//                    return;
//                }
//            }
//            for (Xe x : list1) {
//                if (x.getMaDX().equalsIgnoreCase(dx1)&&x.getMaLX().equalsIgnoreCase(lx)
//                        &&x.getMaMX().equalsIgnoreCase(mx)&&x.getTenHX().equalsIgnoreCase(cboHX.getSelectedItem().toString())) {
//                    MsgBox.alert(this, "Thông tin xe này đã tồn tại!");
//                    return;
//                }
//            }
            try {
                daoxe.update(xe);
                this.fillTableXe();
                this.clearFormXe();
                this.fillTableSP();
                MsgBox.alert(this, "Sửa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Sửa thất bại!");
            }
        }
    }

    void deleteXe() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không được phép xóa!");
        } else {
            String maxe = lblMaXe.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa xe này?\nMã xe: "
                    + lblMaXe.getText() + "\nTên xe: "
                    + txtTenXe.getText())) {
                try {
                    daoxe.delete(maxe);
                    this.fillTableXe();
                    this.clearFormXe();
                    this.fillTableSKSMXe();
                    this.fillTableSP();
                    MsgBox.alert(this, "Xóa thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    void checkHangXe() {
        if (tblHangXe.getRowCount() != 0) {
            txtBaoHanh.setText("");
            lblMaXe.setEnabled(true);
            txtTenXe.setEnabled(true);
            txtGia.setEnabled(true);
            txtSoLuong.setEnabled(true);
            cboHX.setEnabled(true);
            cboHX.setBackground(Color.white);
            cboDongXe.setEnabled(true);
            cboDongXe.setBackground(Color.white);
            cboMauXe.setEnabled(true);
            cboMauXe.setBackground(Color.white);
            cboLoaiXe.setEnabled(true);
            cboLoaiXe.setBackground(Color.white);
            btnThemXe.setEnabled(true);
            btnSuaXe.setEnabled(false);
            btnXoaXe.setEnabled(false);
            btnResetXe.setEnabled(true);
            btnXoaDX.setEnabled(false);
            btnSuaDX.setEnabled(false);
            lblAnhXe.setIcon(null);
            lblAnhXe.setToolTipText(null);
            txtDunhtich.setEnabled(true);
            this.fillComboBoxLoaiXe();
            this.fillComboBoxMauXe();
        } else {
            //Khóa dòng xe
            lblMaDX.setEnabled(false);
            txtTenDX.setEnabled(false);
            cboTenHX.setEnabled(false);
            cboTenHX.addItem("Hãy thêm hãng xe!");
            cboTenHX.setBackground(Color.red);
            txtBaoHanh.setEnabled(false);
            btnThemDX.setEnabled(false);
            btnXoaDX.setEnabled(false);
            btnSuaDX.setEnabled(false);
            btnResetDX.setEnabled(false);

            //khóa thông tin xe
            lblMaXe.setEnabled(false);
            txtTenXe.setEnabled(false);
            txtGia.setEnabled(false);
            txtSoLuong.setEnabled(false);
            cboHX.setEnabled(false);
            cboHX.setBackground(Color.red);
            cboHX.addItem("Hãy thêm hãng xe!");
            cboDongXe.setEnabled(false);
            cboDongXe.setBackground(Color.red);
            cboDongXe.addItem("Hãy thêm hãng xe!");
            cboMauXe.setEnabled(false);
            cboMauXe.setBackground(Color.red);
            cboMauXe.addItem("Hãy thêm hãng xe!");
            cboLoaiXe.setEnabled(false);
            cboLoaiXe.setBackground(Color.red);
            cboLoaiXe.addItem("Hãy thêm hãng xe!");
            btnThemXe.setEnabled(false);
            btnSuaXe.setEnabled(false);
            btnXoaXe.setEnabled(false);
            btnResetXe.setEnabled(false);
            btnFirst.setEnabled(false);
            btnPrev.setEnabled(false);
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
            txtDunhtich.setEnabled(false);
        }
    }

    void first() {
        this.row = 0;
        this.editXe();
    }

    void prev() {
        if (row > 0) {
            this.row--;
            this.editXe();
        }
    }

    void next() {
        if (row < tblDanhSachXe.getRowCount() - 1) {
            this.row++;
            this.editXe();
        }
    }

    void last() {
        this.row = tblDanhSachXe.getRowCount() - 1;
        this.editXe();
    }

    //----------------------------------------------------------form số khung số máy----------------------------------------
    void fillComboBoxXe() {
        DefaultComboBoxModel model1 = (DefaultComboBoxModel) cboMaXe.getModel();
        model1.removeAllElements();
        List<Xe> list = daoxe.selectAll();
        if (tblDanhSachXe.getRowCount() != 0) {
            for (Xe xe : list) {
                //cboMaXe.addItem(xe.getMaXe() + "-" +xe.getTenXe());
                model1.addElement(xe.getMaXe() + " - " + xe.getTenXe());
                txtSoKhung.setEnabled(true);
                txtSoMay.setEnabled(true);
                cboMaXe.setEditable(false);
                cboMaXe.setEnabled(true);
                cboMaXe.setBackground(Color.white);
                btnThemSKSM.setEnabled(true);
                btnSuaSKSM.setEnabled(true);
                btnXoaSKSM.setEnabled(true);
                btnResetSKSM.setEnabled(true);
            }
        } else {
            model1.removeAllElements();
            cboMaXe.addItem("Chưa có xe không thể thêm !");
            cboMaXe.setToolTipText("Chưa có xe không thể thêm !");
            cboMaXe.setEditable(false);
            cboMaXe.setEnabled(false);
            cboMaXe.setBackground(Color.red);
            txtSoKhung.setEnabled(false);
            txtSoMay.setEnabled(false);
            btnThemSKSM.setEnabled(false);
            btnSuaSKSM.setEnabled(false);
            btnXoaSKSM.setEnabled(false);
            btnResetSKSM.setEnabled(false);
            lblSKSM.setText("");
            lblMaSKSM.setText("");
            lblTenXe.setText("");
            lblHangXe.setText("");
            lblDongXe.setText("");
            lblLoaiXe.setText("");
            lblMau.setText("");
            lblDungTich.setText("");
            lblGia.setText("");
            lblSoLuong.setText("");
            txtSoKhung.setText("");
            txtSoMay.setText("");
        }
    }

    //lấy mã combobox lấy thông tin xe
    void setFormSKSMXe(Xe x) {
        lblTenXe.setText(x.getTenXe());
        lblHangXe.setText(x.getTenHX());
        lblDongXe.setText(x.getMaDX());
        lblLoaiXe.setText(x.getMaLX());
        lblMau.setText(x.getMaMX());
        lblDungTich.setText(x.getDungtich());
        int gia = Integer.parseInt(x.getGia() + "");
        NumberFormat fm = new DecimalFormat("#,###");
        lblGia.setText(fm.format(gia) + " VNĐ");
        lblSoLuong.setText(x.getSoluong() + "");
    }

    void fillTableSKSMXe() {
        DefaultTableModel model = (DefaultTableModel) tblSKSM.getModel();
        model.setRowCount(0);

        int maxe = cboMaXe.getSelectedIndex();
        if (maxe > -1) {
            String a = cboMaXe.getItemAt(maxe);
            String chuoi[] = a.split("\\s+", 2);
            cboMaXe.setToolTipText(chuoi[0]);
            try {
                List<SoKhungSoMay> list = daosksm.selectAll();
                int i = 1;
                for (SoKhungSoMay x : list) {
                    if (cboMaXe.getToolTipText().equalsIgnoreCase(x.getMaXe() + "")) {
                        Object[] row = {i++, x.getMaSKSM(), x.getMaXe(), x.getSokhung(), x.getSomay()};
                        model.addRow(row);
                    } else {

                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    //get form insert
    private SoKhungSoMay getFormSKSMXe() {

        SoKhungSoMay sksm = new SoKhungSoMay();
        //khóa chính tự sinh nên k cần phải thêm;
        int maxe = cboMaXe.getSelectedIndex();
        if (maxe > -1) {
            String a = cboMaXe.getItemAt(maxe);
            String chuoi[] = a.split("\\s+", 2);
            cboMaXe.setToolTipText(chuoi[0]);
        }
        sksm.setTenHX(lblHangXe.getText());
        String tendx = lblDongXe.getText();
        List<DongXe> listdx = daodx.selectByDX(tendx);
        for (DongXe dx : listdx) {
            String madx = dx.getMaDX() + "";
            sksm.setMaDX(Integer.parseInt(madx));
        }
        sksm.setMaXe(Integer.parseInt(cboMaXe.getToolTipText()));
        sksm.setSokhung(txtSoKhung.getText());
        sksm.setSomay(txtSoMay.getText());
        return sksm;
    }

    //get form  để  update
    private SoKhungSoMay getFormSKSMXeUP() {
        SoKhungSoMay sksm = new SoKhungSoMay();
        int maxe = cboMaXe.getSelectedIndex();
        if (maxe > -1) {
            String a = cboMaXe.getItemAt(maxe);
            String chuoi[] = a.split("\\s+", 2);
            cboMaXe.setToolTipText(chuoi[0]);
        }
        sksm.setTenHX(lblHangXe.getText());
        String tendx = lblDongXe.getText();
        List<DongXe> listdx = daodx.selectByDX(tendx);
        for (DongXe dx : listdx) {
            String madx = dx.getMaDX() + "";
            sksm.setMaDX(Integer.parseInt(madx));
        }
        sksm.setMaSKSM(Integer.parseInt(lblMaSKSM.getText()));
        sksm.setMaXe(Integer.parseInt(cboMaXe.getToolTipText()));
        sksm.setSokhung(txtSoKhung.getText());
        sksm.setSomay(txtSoMay.getText());
        return sksm;
    }

    void setFormSKSMXe(SoKhungSoMay x) {
        lblSKSM.setText("Mã SKSM");
        lblMaSKSM.setText(x.getMaSKSM() + "");
        cboMaXe.setSelectedItem(x.getMaXe() + "");
        txtSoKhung.setText(x.getSokhung());
        txtSoMay.setText(x.getSomay());
    }

    void editSKSMXe() {
        Integer tenxe = (Integer) tblSKSM.getValueAt(this.row, 1);
        SoKhungSoMay sksm = daosksm.selectById(tenxe.toString());
        this.setFormSKSMXe(sksm);
        this.updateStatusSKSMXe();
    }

    void insertSKSMXe() {
        SoKhungSoMay dx = getFormSKSMXe();
        List<SoKhungSoMay> list1 = daosksm.selectAll();
        List<HoaDonCT> list = daohdct.selectAll();
        if (txtSoKhung.getText().isBlank() || txtSoMay.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            if (dx.getSokhung().length() <= 17 && dx.getSomay().length() <= 17) {
                for (SoKhungSoMay x : list1) {
                    if (x.getSokhung().equalsIgnoreCase(txtSoKhung.getText()) && x.getSomay().equalsIgnoreCase(txtSoMay.getText())) {
                        MsgBox.alert(this, "Thông tin số khung số máy này đã tồn tại!");
                        return;
                    } else if (x.getSokhung().equalsIgnoreCase(txtSoKhung.getText())) {
                        MsgBox.alert(this, "Thông tin số khung này đã tồn tại!");
                        return;
                    } else if (x.getSomay().equalsIgnoreCase(txtSoMay.getText())) {
                        MsgBox.alert(this, "Thông tin số máy này đã tồn tại!");
                        return;
                    }
                }
                for (HoaDonCT hd : list) {
                    if (hd.getSokhung().equalsIgnoreCase(dx.getSokhung()) && hd.getSomay().equalsIgnoreCase(dx.getSomay())) {
                        MsgBox.alert(this, "Thông tin số khung số máy này đã từng tồn tại!");
                        return;
                    } else if (hd.getSomay().equalsIgnoreCase(dx.getSomay())) {
                        MsgBox.alert(this, "Thông tin số máy này đã từng tồn tại!");
                        return;
                    } else if (hd.getSokhung().equalsIgnoreCase(dx.getSokhung())) {
                        MsgBox.alert(this, "Thông tin số khung này đã từng tồn tại!");
                        return;
                    }
                }
                String sk = txtSoKhung.getText();
                for (int i = 0; i < sk.length(); i++) {
                    if (sk.charAt(i) == 'O') {
                        MsgBox.alert(this, "Số khung không chức các ký tự 'I', 'O', 'Q'");
                        return;
                    } else if (sk.charAt(i) == 'o') {
                        MsgBox.alert(this, "Số khung không chức các ký tự 'I', 'O', 'Q'");
                        return;
                    } else if (sk.charAt(i) == 'i') {
                        MsgBox.alert(this, "Số khung không chức các ký tự 'I', 'O', 'Q'");
                        return;
                    } else if (sk.charAt(i) == 'I') {
                        MsgBox.alert(this, "Số khung không chức các ký tự 'I', 'O', 'Q'");
                        return;
                    } else if (sk.charAt(i) == 'q') {
                        MsgBox.alert(this, "Số khung không chức các ký tự 'I', 'O', 'Q'");
                        return;
                    } else if (sk.charAt(i) == 'Q') {
                        MsgBox.alert(this, "Số khung không chức các ký tự 'I', 'O', 'Q'");
                        return;
                    }
                }
            } else {
                if (dx.getSokhung().length() >= 18) {
                    MsgBox.alert(this, "Số khung chỉ có tối đa là 17 ký tự\n"
                            + "Số khung bạn vừa nhập '" + dx.getSokhung() + "'\n"
                            + "" + dx.getSokhung().length() + " Ký tự");
                    return;
                } else if (dx.getSomay().length() >= 18) {
                    MsgBox.alert(this, "Số máy chỉ có tối đa là 17 ký tự\n"
                            + "Số máy bạn vừa nhập '" + dx.getSokhung() + "'\n"
                            + "" + dx.getSokhung().length() + " Ký tự");
                    return;
                }
            }
            try {
                daosksm.insert(dx);
                this.fillTableSKSMXe();
                this.clearFormSKSMXe();
                MsgBox.alert(this, "Thêm thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm thất bại!");
            }
        }
    }

    void updateSKSMXeUP() {
        SoKhungSoMay sksm = getFormSKSMXeUP();
        if (txtSoKhung.getText().isBlank() || txtSoMay.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            try {
                daosksm.update(sksm);
                this.fillTableSKSMXe();
                this.clearFormSKSMXe();
                MsgBox.alert(this, "Sửa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Sửa thất bại!");
            }
        }
    }

    void deleteSKSMXe() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không được phép xóa!");
        } else {
            String masksm = lblMaSKSM.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa số khung số máy này?\nSố khung: "
                    + txtSoKhung.getText() + "\nSố máy: "
                    + txtSoMay.getText())) {
                try {
                    daosksm.delete(masksm);
                    this.fillTableSKSMXe();
                    this.clearFormSKSMXe();
                    MsgBox.alert(this, "Xóa thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    void clearFormSKSMXe() {
        lblSKSM.setText("");
        lblMaSKSM.setText("");
        txtSoKhung.setText("");
        txtSoMay.setText("");
        btnThemSKSM.setEnabled(true);
        btnSuaSKSM.setEnabled(false);
        btnXoaSKSM.setEnabled(false);
    }

    void updateStatusSKSMXe() {
        boolean edit = (this.row >= 0);
        btnThemSKSM.setEnabled(!edit);
        btnSuaSKSM.setEnabled(edit);
        btnXoaSKSM.setEnabled(edit);
    }

    //------------------------------------------------------------------form Hóa đơn--------------------------------------
    void fillTableHD() {
        DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
        model.setRowCount(0);
        NumberFormat fm = new DecimalFormat("#,###");
        try {
            List<HoaDon> list = daohd.selectAll();
            int i = 1;
            for (HoaDon hd : list) {
                Object[] row = {i++, hd.getMaHD(), hd.getMaKH(), hd.getTenKH(), hd.getMaNV(), hd.getTenNV(),
                    XDate.toString(hd.getNgayTao(), "dd-MM-yyyy"), hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán", fm.format(hd.getTongTien())};
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void fillTableHDBYIDKH() {
        DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
        model.setRowCount(0);
        NumberFormat fm = new DecimalFormat("#,###");
        try {
            List<HoaDon> list = daohd.selectAll();
            int i = 1;
            for (HoaDon hd : list) {
                if (lblMaKHHD.getText().equalsIgnoreCase(hd.getMaKH())) {
                    Object[] row = {i++, hd.getMaHD(), hd.getMaKH(), hd.getTenKH(), hd.getMaNV(), hd.getTenNV(),
                        XDate.toString(hd.getNgayTao(), "dd-MM-yyyy"), hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán", fm.format(hd.getTongTien())};
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void setFormHD(HoaDon hd) {
        lblTitleMaHD.setText("Mã hóa đơn");
        lblMaHD.setText(hd.getMaHD() + "");
        lblMaKHHD.setText(hd.getMaKH());
        lblTenKHHD.setText(hd.getTenKH());
        lblMaNVHD.setText(hd.getMaNV());
        lblTenNVHD.setText(hd.getTenNV());
        lblNgayTaoHD.setText(XDate.toString(hd.getNgayTao(), "dd-MM-yyyy"));
        rdoDaThanhToan.setSelected(hd.isTrangThai());
        rdoChuaThanhToan.setSelected(!hd.isTrangThai());
        lblTongTien.setText(hd.getTongTien() + "");
        if (!lblTongTien.getText().equalsIgnoreCase("0") && rdoChuaThanhToan.isSelected()) {
            rdoChuaThanhToan.setEnabled(true);
            rdoDaThanhToan.setEnabled(true);
            btnLuuHD.setEnabled(true);
            btnInHD.setEnabled(false);
        } else {
            rdoDaThanhToan.setEnabled(false);
            rdoChuaThanhToan.setEnabled(false);
            btnLuuHD.setEnabled(false);
            btnXoaHD.setEnabled(true);
            btnInHD.setEnabled(false);
        }
        if (rdoDaThanhToan.isSelected()) {
            btnXoaHD.setEnabled(false);
            btnInHD.setEnabled(true);
        }
        if (!lblTongTien.getText().equalsIgnoreCase("0")) {
            btnXoaHD.setEnabled(false);
        }
    }

    //get form insert
    private HoaDon getFormHD() {
        HoaDon hd = new HoaDon();
        //khóa chính tự sinh nên k cần phải thêm;
        hd.setMaKH(lblMaKHHD.getText());
        hd.setTenKH(lblTenKHHD.getText());
        hd.setMaNV(lblMaNVHD.getText());
        hd.setTenNV(lblTenNVHD.getText());
        lblNgayTaoHD.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
        hd.setTrangThai(rdoDaThanhToan.isSelected());
        String gia = lblTongTien.getText();
        NumberFormat format = new DecimalFormat("#,###");
        try {
            Number parsed = format.parse(gia);
            int result = parsed.intValue();
            hd.setTongTien(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hd;

    }

    //get form để update
    private HoaDon getFormHDUP() {
        HoaDon hd = new HoaDon();
        hd.setMaHD(Integer.parseInt(lblMaHD.getText()));
        hd.setMaKH(lblMaKHHD.getText());
        hd.setTenKH(lblTenKHHD.getText());
        hd.setMaNV(lblMaNVHD.getText());
        hd.setTenNV(lblTenNVHD.getText());
        lblNgayTaoHD.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
        hd.setTrangThai(rdoDaThanhToan.isSelected());
        String gia = lblTongTien.getText();
        NumberFormat format = new DecimalFormat("#,###");
        try {
            Number parsed = format.parse(gia);
            int result = parsed.intValue();
            hd.setTongTien(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hd;
    }

    void editHD() {
        Integer mahd = (Integer) tblHD.getValueAt(this.row, 1);
        HoaDon HD = daohd.selectById(mahd + "");
        btnXoaHD.setEnabled(true);
        this.setFormHD(HD);
    }

    void clearFormHD() {
        String tenNV = Auth.user.getTenNV();
        String userID = Auth.user.getMaNV();
        lblMaNVHD.setText(userID);
        lblTenNVHD.setText(tenNV);
        lblTitleMaHD.setText("");
        lblMaHD.setText("");
        lblMaKHHD.setText("");
        lblTenKHHD.setText("");
        lblTongTien.setText("0");
        lblNgayTaoHD.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
        btnGThanhToan.clearSelection();
        btnLuuHD.setEnabled(false);
        btnLuuHD.setText("Tạo HĐ");
        btnInHD.setEnabled(false);
        btnXoaHD.setEnabled(false);
        rdoChuaThanhToan.setEnabled(false);
        rdoDaThanhToan.setEnabled(false);
        lblMaHD1.setText("");
        lblTextThanhToanHD.setText("");
        this.fillTableHD();
        this.fillTableKH();
        this.fillTableHDCT();
        txtTimKHHD.setText("");
        buttonGroup1.clearSelection();
    }

    void insertHD() {
        HoaDon hd = getFormHD();
        if (lblMaHD.getText().isBlank()) {
            try {
                daohd.insert(hd);
                this.clearFormHD();
                this.fillTableHDBYIDKH();
                MsgBox.alert(this, "Tạo thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Lưu thất bại!");
            }
        } else {
            this.updateHDUP();
        }
    }

    void updateHDUP() {
        HoaDon hd = getFormHDUP();
        try {
            daohd.update(hd);
            this.fillTableHDBYIDKH();
            if (rdoDaThanhToan.isSelected()) {
                rdoDaThanhToan.setEnabled(false);
                rdoChuaThanhToan.setEnabled(false);
                btnLuuHD.setEnabled(false);
                btnXoaHD.setEnabled(false);
                btnInHD.setEnabled(true);
            }
            MsgBox.alert(this, "Đã lưu");
        } catch (Exception e) {
            MsgBox.alert(this, "Lưu thất bại!");
        }
    }

    void deleteHD() {
        int sl = 1;
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không được phép xóa!");
        } else {
            String hd = lblMaHD.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa Hóa đơn này?\nMaHD: "
                    + lblMaHD.getText() + "\nTên KH: "
                    + lblTenKHHD.getText())) {
                try {
                    daohd.delete(hd);
                    this.fillTableHDBYIDKH();
                    this.fillTableHDCT();
                    lblTitleMaHD.setText("");
                    lblMaHD.setText("");
                    btnXoaHD.setEnabled(false);
                    btnLuuHD.setEnabled(true);
                    //this.clearFormHD();
                    MsgBox.alert(this, "Đã xóa");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    //--------------------------------khách hàng-----------------------------KH
    void fillTableKH() {
        DefaultTableModel model = (DefaultTableModel) tblKHHD.getModel();
        model.setRowCount(0);
        try {
            List<KhachHang> list = daokh.selectAll();
            int i = 1;
            for (KhachHang kh : list) {
                if (kh.getDiachi().isBlank()) {
                    Object[] row = {i++, kh.getMaKH(), kh.getTenKH(), kh.getSodienthoai(),
                        XDate.toString(kh.getNgaytao(), "dd-MM-yyyy"), kh.getDiachi() + "Chưa thêm địa chỉ"};
                    model.addRow(row);
                } else {
                    Object[] row = {i++, kh.getMaKH(), kh.getTenKH(), kh.getSodienthoai(),
                        XDate.toString(kh.getNgaytao(), "dd-MM-yyyy"), kh.getDiachi()};
                    model.addRow(row);
                };
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void setFormKh(KhachHang kh) {
        lblMaKHHD.setText(kh.getMaKH());
        lblTenKHHD.setText(kh.getTenKH());
    }

    void updateStatusKh() {
        boolean edit = (this.row >= 0);
        btnLuuHD.setEnabled(edit);
        btnInHD.setEnabled(false);
        btnXoaHD.setEnabled(false);
    }

    void editKh() {
        String makh = (String) tblKHHD.getValueAt(this.row, 1);
        KhachHang kh = daokh.selectById(makh);
        this.setFormKh(kh);
        this.fillTableHDBYIDKH();
        this.updateStatusKh();
    }
    //-----------------------------------------------------------------------KH

    //----------------------------------------------------form hóa đơn chi tiết--------------------------------------
    void fillTableHDCT() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDonCT.getModel();
        model.setRowCount(0);
        int tt = 0;
        NumberFormat fm = new DecimalFormat("#,###");
        try {
            List<HoaDonCT> list = daohdct.selectAll();
            int i = 1;
            for (HoaDonCT hd : list) {
                if (lblMaHD1.getText().equalsIgnoreCase(hd.getMaHD() + "")) {
                    Object[] row = {i++, hd.getHoaDonCT(), hd.getMaHD(), hd.getMaXe(), hd.getTenXe(), hd.getTenHang(), hd.getTenDX(), hd.getTenLX(),
                        hd.getTenMX(), hd.getDungtich(), hd.getSokhung(), hd.getSomay(), hd.getSoluong(), fm.format(hd.getGia()), fm.format(hd.getThanhtien())};
                    model.addRow(row);
                    tt += hd.getThanhtien();
                }
            }
            //Thành tiền hóa đơn
            HoaDon hd = new HoaDon();
            if (!lblMaHD.getText().isBlank()) {
                hd.setMaHD(Integer.parseInt(lblMaHD.getText()));
            }
            hd.setTongTien(tt);
            daohd.updateTT(hd);
            int gia = Integer.parseInt(tt + "");
            lblTongTien.setText(fm.format(gia));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //set form click từ table hdct
    void setFormHDCT(HoaDonCT hd) {
        lblMaHDCT.setText(hd.getHoaDonCT() + "");
        lblMaHD1.setText(hd.getMaHD() + "");
        lblMaXe1.setText(hd.getMaXe() + "");
        lblTenXe1.setText(hd.getTenXe());
        lblTenHang1.setText(hd.getTenHang());
        lblTenDong1.setText(hd.getTenDX());
        lblTenLoai1.setText(hd.getTenLX());
        lblTenMau1.setText(hd.getTenMX());
        lblDungTich1.setText(hd.getDungtich());
        cbosksm.removeAllItems();
        cbosksm.addItem(hd.getSokhung());
        lblSoMay1.setText(hd.getSomay());
        lblSoLuong1.setText(hd.getSoluong() + "");
        int gia = Integer.parseInt(hd.getGia() + "");
        NumberFormat fm = new DecimalFormat("#,###");
        lblGia1.setText(fm.format(gia));
        lblThanhTien.setText(fm.format(gia));
    }

    //get form insert
    private HoaDonCT getFormHDCT() {
        String sokhung = (String) cbosksm.getSelectedItem();
        List<HoaDonCT> list1 = daohdct.selectBysk();
        HoaDonCT hd = new HoaDonCT();
        //khóa chính tự sinh nên k cần phải thêm;
        hd.setMaHD(Integer.parseInt(lblMaHD1.getText()));
        hd.setMaXe(Integer.parseInt(lblMaXe1.getText()));
        hd.setTenXe(lblTenXe1.getText());
        hd.setTenHang(lblTenHang1.getText());
        hd.setTenDX(lblTenDong1.getText());
        hd.setTenLX(lblTenLoai1.getText());
        hd.setTenMX(lblTenMau1.getText());
        hd.setDungtich(lblDungTich1.getText());
        hd.setSokhung(sokhung);
        hd.setSomay(lblSoMay1.getText());
        hd.setSoluong(Integer.parseInt(lblSoLuong1.getText()));
        String gia = lblGia1.getText();
        NumberFormat format = new DecimalFormat("#,###");
        try {
            Number parsed = format.parse(gia);
            int result = parsed.intValue();
            //System.out.println(result);
            hd.setGia(result);
            hd.setThanhtien(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hd;
    }

    //khi xóa sản phẩm sẽ thêm só khung số máy lại
    private SoKhungSoMay getFormInsertHD() {
        SoKhungSoMay sksm = new SoKhungSoMay();
        sksm.setMaXe(Integer.parseInt(lblMaXe1.getText()));
        sksm.setTenHX(lblTenHang1.getText());
        String tendx = lblTenDong1.getText();
        List<DongXe> listdx = daodx.selectByDX(tendx);
        for (DongXe dx : listdx) {
            String madx = dx.getMaDX() + "";
            sksm.setMaDX(Integer.parseInt(madx));
        }
        String sk = (String) cbosksm.getSelectedItem();
        sksm.setSokhung(sk);
        sksm.setSomay(lblSoMay1.getText());
        return sksm;
    }

    void insertHDCT() {
        int sl = 0;
        String somay = lblSoMay1.getText();
        HoaDonCT hd = getFormHDCT();
        if (lblMaHDCT.getText().isBlank()) {
            try {
                List<Xe> list = daoxe.selectAll();
                for (Xe xe : list) {
                    if (lblMaXe1.getText().equalsIgnoreCase(xe.getMaXe() + "")) {
                        sl = xe.getSoluong() - 1;
                    }
                }
                Xe x = new Xe();
                if (!lblMaXe1.getText().isBlank()) {
                    x.setMaXe(Integer.parseInt(lblMaXe1.getText()));
                }
                x.setSoluong(sl);
                daoxe.updateSL(x);
                this.fillTableSP();
                this.fillTableXe();
//                if (x.getSoluong() == 0) {
//                    daoxe.deleteXE(lblMaXe1.getText());
//                }
                daosksm.deleteSKSM(somay);
                daohdct.insert(hd);
                this.fillTableHDCT();
                this.fillComboBoxSKSM();
                this.fillTableHD();
                this.fillTableSKSMXe();
                //MsgBox.alert(this, "Lưu thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Lưu thất bại!");
            }
        } else {
            //this.updateHDUP();
        }
    }

    void deleteHDCT() {
        int sl = 0;
        String hd = lblMaHDCT.getText();
        if (hd.isBlank()) {
            MsgBox.alert(this, "Vui lòng chọn sản phẩm cần xóa");
            return;
        } else {
            try {
                List<Xe> list = daoxe.selectAll();
                for (Xe xe : list) {
                    if (lblMaXe1.getText().equalsIgnoreCase(xe.getMaXe() + "")) {
                        sl = xe.getSoluong() + 1;
                    }
                }
                Xe x = new Xe();
                if (!lblMaXe1.getText().isBlank()) {
                    x.setMaXe(Integer.parseInt(lblMaXe1.getText()));
                }
                x.setSoluong(sl);
                daoxe.updateSL(x);
                this.fillTableSP();
                this.fillTableXe();

                //xóa sản phẩm sẽ  insert lại sksm 
                SoKhungSoMay sksm = getFormInsertHD();
                daosksm.insert(sksm);
                daohdct.delete(hd);
                this.fillTableHDCT();
                this.fillTableHD();
                this.fillComboBoxSKSM();
                this.fillTableSKSMXe();
                lblMaHDCT.setText("");
                btnLuuHDCT.setEnabled(true);
                btnXoaSP.setEnabled(false);
                tblHoaDonCT.requestFocus();
                //MsgBox.alert(this, "Đã xóa");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại!");
            }
        }
    }

    void clearFormHDCT() {
        lblTextThanhToanHD.setText("");
        lblMaHDCT.setText("");
        lblMaHD1.setText("");
        lblMaXe1.setText("");
        lblTenXe1.setText("");
        lblTenHang1.setText("");
        lblTenDong1.setText("");
        lblTenLoai1.setText("");
        lblTenMau1.setText("");
        lblDungTich1.setText("");
        lblSoMay1.setText("");
        lblGia1.setText("");
        lblThanhTien.setText("");
        this.fillComboBoxSKSM();
        this.fillTableHDCT();
        btnLuuHDCT.setEnabled(false);
        btnXoaSP.setEnabled(false);
        tblSanPham.setEnabled(true);
        txtTimSP.setText("");
    }

    void cleareFormGetclick() {
        lblMaHDCT.setText("");
        lblMaXe1.setText("");
        lblTenXe1.setText("");
        lblTenHang1.setText("");
        lblTenDong1.setText("");
        lblTenLoai1.setText("");
        lblTenMau1.setText("");
        lblDungTich1.setText("");
        lblSoMay1.setText("");
        lblGia1.setText("");
        lblThanhTien.setText("");
        btnLuuHDCT.setEnabled(false);
        btnXoaSP.setEnabled(false);
        tblSanPham.setEnabled(true);
    }

    void editHDCT() {
        Integer mahdct = (Integer) tblHoaDonCT.getValueAt(this.row, 1);
        HoaDonCT hd = daohdct.selectById(mahdct + "");
        this.setFormHDCT(hd);
        btnXoaSP.setEnabled(true);
    }

    //------------------------filltable sản phẩm-----------------------------
    void fillTableSP() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        int i = 1;
        int sl = 0;
        try {
            List<Xe> list = daoxe.selectAll();
            for (Xe x : list) {
                if (lblTextThanhToanHD.getText().isBlank()) {
                    if (x.getSoluong() != 0) {
                        Object[] row = {i++, x.getMaXe(), x.getTenXe(), x.getTenHX(), x.getMaDX(), x.getMaLX(), x.getMaMX(), x.getDungtich(), x.getSoluong(), x.getGia()};
                        model.addRow(row);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //fillcombox số khung số máy
    void fillComboBoxSKSM() {
        DefaultComboBoxModel model1 = (DefaultComboBoxModel) cbosksm.getModel();
        model1.removeAllElements();
        String maxe = lblMaXe1.getText();
        List<SoKhungSoMay> list = daosksm.selectAll();
        if (tblDanhSachXe.getRowCount() != 0) {
            for (SoKhungSoMay sksm : list) {
                if (maxe.equalsIgnoreCase(sksm.getMaXe() + "")) {
                    cbosksm.addItem(sksm.getSokhung());
                }
            }
            String sksm1 = (String) cbosksm.getSelectedItem();
            if (!lblMaXe1.getText().isBlank()) {
                if (sksm1 != null) {
//                    btnLuuHDCT.setEnabled(true);
//                    return;
                } else {
                    cbosksm.addItem("Vui lòng thêm số khung");
                    lblSoMay1.setText("");
                    btnLuuHDCT.setEnabled(false);
                }
            }
        } else {
            model1.removeAllElements();
            cbosksm.addItem("Chưa có số khung số máy không thể thêm!");
        }

    }

    //click tblsp  sét lên from
    void setFormSP(Xe x) {
        lblMaXe1.setText(x.getMaXe() + "");
        this.fillComboBoxSKSM();
        if (!lblMaHD1.getText().isBlank() && !lblMaXe1.getText().isBlank()) {
            btnLuuHDCT.setEnabled(true);
            btnXoaSP.setEnabled(false);
        }
        String sksm1 = (String) cbosksm.getSelectedItem();
        if (!lblMaXe1.getText().isBlank()) {
            if (!lblSoMay1.getText().isBlank()) {
//                    btnLuuHDCT.setEnabled(true);
//                    return;
            } else {
                cbosksm.addItem("Vui lòng thêm số khung");
                lblSoMay1.setText("");
                btnLuuHDCT.setEnabled(false);
            }
        }
        lblTenXe1.setText(x.getTenXe());
        lblTenHang1.setText(x.getTenHX());
        lblTenDong1.setText(x.getMaDX());
        lblTenLoai1.setText(x.getMaLX());
        lblTenMau1.setText(x.getMaMX());
        lblDungTich1.setText(x.getDungtich());
        int gia = Integer.parseInt(x.getGia() + "");
        Locale locale = new Locale("vi", "VN");
        NumberFormat fm = new DecimalFormat("#,###");
        lblGia1.setText(fm.format(gia));
        lblThanhTien.setText(fm.format(gia));
    }

    void editSP() {
        Integer maxe = (Integer) tblSanPham.getValueAt(this.row, 1);
        Xe xe = daoxe.selectById(maxe + "");
        this.setFormSP(xe);

    }

//    protected List<HoaDonCT> selectBySQL(String sql, Object... args) {
//        List<HoaDonCT> list = new ArrayList<>();
//        try {
//            ResultSet rs = XJdbc.executeQuery(sql, args);
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream("D:\\du_lieu.pdf"));
//            document.open();
//            while (rs.next()) {
//                HoaDonCT entity = new HoaDonCT();
////                entity.setHoaDonCT(rs.getInt("MaHDCT"));
//                String hdct = rs.getString("MaHDCT");
////                entity.setMaHD(rs.getInt("MaHD"));
////                entity.setMaXe(rs.getInt("MaXe"));
////                entity.setTenXe(rs.getString("TenXe"));
////                entity.setTenHang(rs.getString("TenHX"));
////                entity.setTenDX(rs.getString("TenDX"));
////                entity.setTenLX(rs.getString("TenLX"));
////                entity.setTenMX(rs.getString("TenMX"));
////                entity.setDungtich(rs.getString("Dungtich"));
////                entity.setSokhung(rs.getString("Sokhung"));
////                entity.setSomay(rs.getString("Somay"));
////                entity.setGia(rs.getInt("Gia"));
////                entity.setSoluong(rs.getInt("Soluong"));
////                entity.setThanhtien(rs.getInt("Thanhtien"));
////                list.add(entity);
//                document.add(new Paragraph(hdct));
//            }
//            rs.getStatement().getConnection().close();
//            document.close();
//            return list;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
    public void showDataList(String strngaybd, String strngaytk) {

        DefaultTableModel model = (DefaultTableModel) this.jTable1.getModel();
        model.setRowCount(0);

        //load data
        ArrayList<hoadonTT> list = baocaoservices.getRecords(strngaybd, strngaytk);
        dsHoaDon = list;

        Locale localeEN = new Locale("vi", "VN");
        NumberFormat en = NumberFormat.getInstance(localeEN);

        //SimpleDateFormat formatter2 = new SimpleDateFormat("dd-mm-yyyy");
        Object[] row = new Object[8];
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isTrangthai() == true) {
                row[0] = list.get(i).getMaHoaDon();
                row[1] = list.get(i).getMakhachhang();
                row[2] = list.get(i).getTenkh();
                row[3] = list.get(i).getUserID();
                row[4] = list.get(i).getNameID();
                row[5] = list.get(i).isTrangthai() ? "Đã thanh toán" : "Chưa thanh toán";
                row[6] = XDate.toString(list.get(i).getNgay(), "dd-mm-yyyy");
                row[7] = en.format(list.get(i).getTongTien());
                model.addRow(row);
            }
        }
    }

    public void showTong(String strngaybd, String strngaytk) {
        //load data
        ArrayList<tongtienTT> list = baocaoservices.getTong(strngaybd, strngaytk);
        dsTongTien = list;

        Locale localeEN = new Locale("vi", "VN");
        NumberFormat en = NumberFormat.getInstance(localeEN);

        for (int i = 0; i < list.size(); i++) {
            txt_tongtien.setText((en.format(list.get(i).getTongTien())) + " VNĐ");
            System.out.println(list.get(i).getTongTien());
        }
    }

    public void setNgay(String tungay, String denngay) {
        this.txt_tu.setText(tungay);
        this.txt_den.setText(denngay);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGChucVu = new javax.swing.ButtonGroup();
        btnGThanhToan = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jSplitPane1 = new javax.swing.JSplitPane();
        MeNuJPanel = new javax.swing.JPanel();
        btnNhanVien = new javax.swing.JButton();
        btnXeMay = new javax.swing.JButton();
        btnKhachHang = new javax.swing.JButton();
        btnHoaDon = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        btnAboutYou = new javax.swing.JButton();
        btnCaiDat = new javax.swing.JButton();
        lblAnh = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnHome = new javax.swing.JButton();
        JPanelCard = new javax.swing.JPanel();
        JPanelHome = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        JPanelNhanVien = new javax.swing.JPanel();
        NhanVienJTabbedPane = new javax.swing.JTabbedPane();
        CapNhatJPanel = new javax.swing.JPanel();
        btnSuaNhanVien = new javax.swing.JButton();
        btnThemNhanvien = new javax.swing.JButton();
        txtTenNV = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JPasswordField();
        jLabel12 = new javax.swing.JLabel();
        txtMatKhau2 = new javax.swing.JPasswordField();
        txtMaNV = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnXoaNhanVien = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSodienthoai = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        btnResetNhanVien = new javax.swing.JButton();
        lblHinhAnh = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        rdoQuanLy = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        btnXoaAnhNhanVien = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        DanhSachJPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtTimkiemNV = new javax.swing.JTextField();
        JPanelKhachHang = new javax.swing.JPanel();
        KhachHangJTabbedPane = new javax.swing.JTabbedPane();
        CapNhatKhachHangJPanel = new javax.swing.JPanel();
        lblTextMaKH = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtSodienthoaiKH = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDiaChiKH = new javax.swing.JTextArea();
        btnThemKH = new javax.swing.JButton();
        btnSuaKH = new javax.swing.JButton();
        btnXoaKH = new javax.swing.JButton();
        btnResetKH = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        lblMaKH = new javax.swing.JLabel();
        DanhSachKHJPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtTimKiemKH = new javax.swing.JTextField();
        JPanelXeMay = new javax.swing.JPanel();
        XeMayJTabbedPane = new javax.swing.JTabbedPane();
        LoaiXeJPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblLoaiXe = new javax.swing.JTable();
        lblMaLoaiXe = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTenLX = new javax.swing.JTextField();
        btnLuuLX = new javax.swing.JButton();
        btnXoaLX = new javax.swing.JButton();
        btnResetLX = new javax.swing.JButton();
        lblMaLX = new javax.swing.JLabel();
        MauXeJPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblMauXe = new javax.swing.JTable();
        lblMaMauXe = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTenMX = new javax.swing.JTextField();
        btnLuuMX = new javax.swing.JButton();
        btnXoaMX = new javax.swing.JButton();
        btnResetMX = new javax.swing.JButton();
        lblMaMX = new javax.swing.JLabel();
        HangXeJPanel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblHangXe = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtTenHX = new javax.swing.JTextField();
        btnThemHX = new javax.swing.JButton();
        btnXoaHX = new javax.swing.JButton();
        btnResetHX = new javax.swing.JButton();
        DongXeJPanel = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblDongXe = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        lblMaDongXe = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtTenDX = new javax.swing.JTextField();
        btnThemDX = new javax.swing.JButton();
        btnSuaDX = new javax.swing.JButton();
        btnXoaDX = new javax.swing.JButton();
        btnResetDX = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cboTenHX = new javax.swing.JComboBox<>();
        jLabel52 = new javax.swing.JLabel();
        txtBaoHanh = new javax.swing.JTextField();
        lblMaDX = new javax.swing.JLabel();
        QLXe = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        lblAnhXe = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        lblXe = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtTenXe = new javax.swing.JTextField();
        cboLoaiXe = new javax.swing.JComboBox<>();
        cboMauXe = new javax.swing.JComboBox<>();
        cboDongXe = new javax.swing.JComboBox<>();
        btnResetXe = new javax.swing.JButton();
        btnThemXe = new javax.swing.JButton();
        btnSuaXe = new javax.swing.JButton();
        btnXoaXe = new javax.swing.JButton();
        txtSoLuong = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        cboHX = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        btnFirst = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        txtDunhtich = new javax.swing.JTextField();
        lblMaXe = new javax.swing.JLabel();
        DanhSachXeJPanel = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblDanhSachXe = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        txtTimKiemXe = new javax.swing.JTextField();
        SKSMJPanel = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblSKSM = new javax.swing.JTable();
        jLabel43 = new javax.swing.JLabel();
        lblSKSM = new javax.swing.JLabel();
        lblMaSKSM = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        txtSoKhung = new javax.swing.JTextField();
        txtSoMay = new javax.swing.JTextField();
        btnThemSKSM = new javax.swing.JButton();
        btnSuaSKSM = new javax.swing.JButton();
        btnXoaSKSM = new javax.swing.JButton();
        btnResetSKSM = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblMau = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        lblGia = new javax.swing.JLabel();
        cboMaXe = new javax.swing.JComboBox<>();
        lblDungTich = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        lblTenXe = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        lblLoaiXe = new javax.swing.JLabel();
        lblHangXe = new javax.swing.JLabel();
        lblSoLuong = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        lblDongXe = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        JPanelHoaDon = new javax.swing.JPanel();
        HoaDonJTabbedPane = new javax.swing.JTabbedPane();
        HoaDonJPanel = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        jLabel45 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblTitleMaHD = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnLuuHD = new javax.swing.JButton();
        btnInHD = new javax.swing.JButton();
        btnXoaHD = new javax.swing.JButton();
        btnResSetHD = new javax.swing.JButton();
        lblMaKHHD = new javax.swing.JLabel();
        lblTenKHHD = new javax.swing.JLabel();
        lblMaNVHD = new javax.swing.JLabel();
        lblTenNVHD = new javax.swing.JLabel();
        lblNgayTaoHD = new javax.swing.JLabel();
        rdoDaThanhToan = new javax.swing.JRadioButton();
        rdoChuaThanhToan = new javax.swing.JRadioButton();
        lblMaHD = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblKHHD = new javax.swing.JTable();
        txtTimKHHD = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        rdoDTT = new javax.swing.JRadioButton();
        rdoCTT = new javax.swing.JRadioButton();
        rdoALL = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tblHoaDonCT = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblMaHD1 = new javax.swing.JLabel();
        lblTenXe1 = new javax.swing.JLabel();
        lblTenHang1 = new javax.swing.JLabel();
        lblTenDong1 = new javax.swing.JLabel();
        lblTenLoai1 = new javax.swing.JLabel();
        lblMaHDCT = new javax.swing.JLabel();
        lblTenMau1 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        lblMaXe1 = new javax.swing.JLabel();
        lblDungTich1 = new javax.swing.JLabel();
        cbosksm = new javax.swing.JComboBox<>();
        jLabel74 = new javax.swing.JLabel();
        lblSoLuong1 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        lblGia1 = new javax.swing.JLabel();
        lblThanhTien = new javax.swing.JLabel();
        btnLuuHDCT = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        btnXoaSP = new javax.swing.JButton();
        lblSoMay1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        txtTimSP = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        lblTextThanhToanHD = new javax.swing.JLabel();
        JPanelThongKe = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btn_in = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        txt_tongtien = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        txt_tu = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        txt_den = new javax.swing.JLabel();
        ngaybatdau = new com.toedter.calendar.JDateChooser();
        ngaykethuc = new com.toedter.calendar.JDateChooser();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        btn_doanhso = new javax.swing.JButton();
        jLabel94 = new javax.swing.JLabel();
        cb_DSB = new javax.swing.JRadioButton();
        JPanelCaiDat = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblAnhNVCT = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        lblMaNVCT = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        txtDiaChiNVCT = new javax.swing.JTextArea();
        txtMatKhauCT = new javax.swing.JPasswordField();
        lblVaiTroNV = new javax.swing.JLabel();
        btnCapNhatTT = new javax.swing.JButton();
        btnDoiMatKhau = new javax.swing.JButton();
        txtTenNVCT = new javax.swing.JTextField();
        txtSoDienThoaiCT = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        JPanelAboutYou = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Xe");

        jSplitPane1.setDividerSize(0);

        MeNuJPanel.setBackground(new java.awt.Color(51, 51, 51));

        btnNhanVien.setBackground(new java.awt.Color(51, 51, 51));
        btnNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnNhanVien.setText("Nhân viên");
        btnNhanVien.setBorder(null);
        btnNhanVien.setBorderPainted(false);
        btnNhanVien.setFocusPainted(false);
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });

        btnXeMay.setBackground(new java.awt.Color(51, 51, 51));
        btnXeMay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXeMay.setForeground(new java.awt.Color(255, 255, 255));
        btnXeMay.setText("Xe máy");
        btnXeMay.setBorder(null);
        btnXeMay.setBorderPainted(false);
        btnXeMay.setFocusPainted(false);
        btnXeMay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXeMayActionPerformed(evt);
            }
        });

        btnKhachHang.setBackground(new java.awt.Color(51, 51, 51));
        btnKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnKhachHang.setText("Khách hàng");
        btnKhachHang.setBorder(null);
        btnKhachHang.setBorderPainted(false);
        btnKhachHang.setFocusPainted(false);
        btnKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangActionPerformed(evt);
            }
        });

        btnHoaDon.setBackground(new java.awt.Color(51, 51, 51));
        btnHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnHoaDon.setText("Hóa đơn");
        btnHoaDon.setBorder(null);
        btnHoaDon.setBorderPainted(false);
        btnHoaDon.setFocusPainted(false);
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });

        btnThongKe.setBackground(new java.awt.Color(51, 51, 51));
        btnThongKe.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThongKe.setForeground(new java.awt.Color(255, 255, 255));
        btnThongKe.setText("Thống kê");
        btnThongKe.setBorder(null);
        btnThongKe.setBorderPainted(false);
        btnThongKe.setFocusPainted(false);
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        btnAboutYou.setBackground(new java.awt.Color(51, 51, 51));
        btnAboutYou.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAboutYou.setForeground(new java.awt.Color(255, 255, 255));
        btnAboutYou.setText("About you");
        btnAboutYou.setBorder(null);
        btnAboutYou.setBorderPainted(false);
        btnAboutYou.setFocusPainted(false);
        btnAboutYou.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutYouActionPerformed(evt);
            }
        });

        btnCaiDat.setBackground(new java.awt.Color(51, 51, 51));
        btnCaiDat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCaiDat.setForeground(new java.awt.Color(255, 255, 255));
        btnCaiDat.setText("Cài đặt");
        btnCaiDat.setBorder(null);
        btnCaiDat.setBorderPainted(false);
        btnCaiDat.setFocusPainted(false);
        btnCaiDat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaiDatActionPerformed(evt);
            }
        });

        lblAnh.setBackground(new java.awt.Color(255, 255, 255));
        lblAnh.setForeground(new java.awt.Color(255, 255, 255));
        lblAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });

        lblTenNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTenNV.setForeground(new java.awt.Color(255, 255, 255));
        lblTenNV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenNV.setText("Tên nhân viên");

        btnHome.setBackground(new java.awt.Color(51, 51, 51));
        btnHome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnHome.setText("Home");
        btnHome.setBorder(null);
        btnHome.setBorderPainted(false);
        btnHome.setFocusPainted(false);
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MeNuJPanelLayout = new javax.swing.GroupLayout(MeNuJPanel);
        MeNuJPanel.setLayout(MeNuJPanelLayout);
        MeNuJPanelLayout.setHorizontalGroup(
            MeNuJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MeNuJPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(MeNuJPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(lblTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnXeMay, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnCaiDat, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnAboutYou, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        MeNuJPanelLayout.setVerticalGroup(
            MeNuJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MeNuJPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lblTenNV)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(btnNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(btnKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(btnXeMay, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(btnHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(btnThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(btnCaiDat, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(btnAboutYou, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(MeNuJPanel);

        JPanelCard.setLayout(new java.awt.CardLayout());

        JPanelHome.setBackground(new java.awt.Color(255, 255, 255));

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/xe/icon/xe-may.png"))); // NOI18N

        javax.swing.GroupLayout JPanelHomeLayout = new javax.swing.GroupLayout(JPanelHome);
        JPanelHome.setLayout(JPanelHomeLayout);
        JPanelHomeLayout.setHorizontalGroup(
            JPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
        );
        JPanelHomeLayout.setVerticalGroup(
            JPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
        );

        JPanelCard.add(JPanelHome, "JPanelHome");

        JPanelNhanVien.setBackground(new java.awt.Color(255, 255, 255));

        NhanVienJTabbedPane.setBackground(new java.awt.Color(255, 255, 255));

        CapNhatJPanel.setBackground(new java.awt.Color(204, 204, 204));

        btnSuaNhanVien.setText("Sửa");
        btnSuaNhanVien.setEnabled(false);
        btnSuaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaNhanVienActionPerformed(evt);
            }
        });

        btnThemNhanvien.setText("Thêm");
        btnThemNhanvien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhanvienActionPerformed(evt);
            }
        });

        jLabel12.setText("Mã nhân viên");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel13.setText("QUẢN LÝ NHÂN VIÊN");

        jLabel14.setText("Tên nhân viên");

        btnXoaNhanVien.setText("Xóa");
        btnXoaNhanVien.setEnabled(false);
        btnXoaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNhanVienActionPerformed(evt);
            }
        });

        jLabel15.setText("Mật khẩu");

        jLabel16.setText("Nhập lại mật khẩu");

        jLabel4.setText("Số điện thoại");

        txtSodienthoai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSodienthoaiKeyReleased(evt);
            }
        });

        jLabel5.setText("Địa chỉ");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane2.setViewportView(txtDiaChi);

        btnResetNhanVien.setText("Reset");
        btnResetNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetNhanVienActionPerformed(evt);
            }
        });

        lblHinhAnh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhAnhMouseClicked(evt);
            }
        });

        jLabel1.setText("Vai trò");

        rdoQuanLy.setBackground(new java.awt.Color(204, 204, 204));
        btnGChucVu.add(rdoQuanLy);
        rdoQuanLy.setText("Quản lý");
        rdoQuanLy.setBorder(null);
        rdoQuanLy.setFocusPainted(false);

        rdoNhanVien.setBackground(new java.awt.Color(204, 204, 204));
        btnGChucVu.add(rdoNhanVien);
        rdoNhanVien.setText("Nhân viên");
        rdoNhanVien.setBorder(null);
        rdoNhanVien.setFocusPainted(false);

        btnXoaAnhNhanVien.setText("Xóa ảnh");
        btnXoaAnhNhanVien.setEnabled(false);
        btnXoaAnhNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaAnhNhanVienActionPerformed(evt);
            }
        });

        jLabel2.setText("Hình ảnh");

        javax.swing.GroupLayout CapNhatJPanelLayout = new javax.swing.GroupLayout(CapNhatJPanel);
        CapNhatJPanel.setLayout(CapNhatJPanelLayout);
        CapNhatJPanelLayout.setHorizontalGroup(
            CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel2)
                .addGap(171, 171, 171)
                .addComponent(jLabel13)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CapNhatJPanelLayout.createSequentialGroup()
                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CapNhatJPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16)))
                            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5))
                            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(13, 13, 13)
                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV)
                            .addComponent(txtTenNV, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSodienthoai, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMatKhau2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                .addComponent(rdoQuanLy)
                                .addGap(13, 13, 13)
                                .addComponent(rdoNhanVien)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CapNhatJPanelLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnXoaAnhNhanVien)
                        .addGap(158, 158, 158)
                        .addComponent(jScrollPane2)))
                .addGap(183, 183, 183))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CapNhatJPanelLayout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addComponent(btnThemNhanvien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnSuaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnXoaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnResetNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(193, 193, 193))
        );
        CapNhatJPanelLayout.setVerticalGroup(
            CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel2))
                    .addComponent(jLabel13))
                .addGap(6, 6, 6)
                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel14)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel4)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel15)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel16))
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSodienthoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMatKhau2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rdoQuanLy)
                                .addComponent(jLabel1))
                            .addComponent(rdoNhanVien))))
                .addGap(19, 19, 19)
                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnXoaAnhNhanVien)
                        .addComponent(jLabel5))
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaNhanVien)
                    .addComponent(btnResetNhanVien)
                    .addComponent(btnSuaNhanVien)
                    .addComponent(btnThemNhanvien))
                .addGap(23, 23, 23))
        );

        NhanVienJTabbedPane.addTab("Cập nhật", CapNhatJPanel);

        DanhSachJPanel.setBackground(new java.awt.Color(204, 204, 204));

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NV", "Tên NV", "Số điện thoại", "Địa chỉ", "Vai trò", "Hình ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setFocusable(false);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Tìm kiếm");

        txtTimkiemNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimkiemNVKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout DanhSachJPanelLayout = new javax.swing.GroupLayout(DanhSachJPanel);
        DanhSachJPanel.setLayout(DanhSachJPanelLayout);
        DanhSachJPanelLayout.setHorizontalGroup(
            DanhSachJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DanhSachJPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addGap(8, 8, 8)
                .addComponent(txtTimkiemNV)
                .addGap(77, 77, 77))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
        );
        DanhSachJPanelLayout.setVerticalGroup(
            DanhSachJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DanhSachJPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(DanhSachJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtTimkiemNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
        );

        NhanVienJTabbedPane.addTab("Danh sách", DanhSachJPanel);

        javax.swing.GroupLayout JPanelNhanVienLayout = new javax.swing.GroupLayout(JPanelNhanVien);
        JPanelNhanVien.setLayout(JPanelNhanVienLayout);
        JPanelNhanVienLayout.setHorizontalGroup(
            JPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NhanVienJTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        JPanelNhanVienLayout.setVerticalGroup(
            JPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NhanVienJTabbedPane)
        );

        JPanelCard.add(JPanelNhanVien, "JPanelNhanVien");

        JPanelKhachHang.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setText("Tên khách hàng");

        txtTenKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenKHKeyReleased(evt);
            }
        });

        jLabel19.setText("Số điện thoại");

        txtSodienthoaiKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSodienthoaiKHKeyReleased(evt);
            }
        });

        jLabel20.setText("Ngày tạo");

        jLabel22.setText("Địa chỉ");

        txtDiaChiKH.setColumns(20);
        txtDiaChiKH.setRows(5);
        txtDiaChiKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiaChiKHKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(txtDiaChiKH);

        btnThemKH.setText("Thêm");
        btnThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKHActionPerformed(evt);
            }
        });

        btnSuaKH.setText("Sửa");
        btnSuaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKHActionPerformed(evt);
            }
        });

        btnXoaKH.setText("Xóa");
        btnXoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKHActionPerformed(evt);
            }
        });

        btnResetKH.setText("Reset");
        btnResetKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetKHActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel21.setText("QUẢN LÝ KHÁCH HÀNG");

        lblNgayTao.setText(XDate.toString(new Date(), "dd-MM-yyyy"));

        javax.swing.GroupLayout CapNhatKhachHangJPanelLayout = new javax.swing.GroupLayout(CapNhatKhachHangJPanel);
        CapNhatKhachHangJPanel.setLayout(CapNhatKhachHangJPanelLayout);
        CapNhatKhachHangJPanelLayout.setHorizontalGroup(
            CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhatKhachHangJPanelLayout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(btnThemKH, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(btnSuaKH, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(btnXoaKH, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(btnResetKH, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addGap(143, 143, 143))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CapNhatKhachHangJPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addGap(259, 259, 259))
            .addGroup(CapNhatKhachHangJPanelLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel22)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19)
                    .addComponent(lblTextMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(txtTenKH)
                    .addComponent(txtSodienthoaiKH)
                    .addComponent(lblNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(148, 148, 148))
        );
        CapNhatKhachHangJPanelLayout.setVerticalGroup(
            CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhatKhachHangJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addGap(39, 39, 39)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTextMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtSodienthoaiKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(lblNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemKH)
                    .addComponent(btnSuaKH)
                    .addComponent(btnXoaKH)
                    .addComponent(btnResetKH))
                .addGap(61, 61, 61))
        );

        KhachHangJTabbedPane.addTab("Cập nhật", CapNhatKhachHangJPanel);

        DanhSachKHJPanel.setBackground(new java.awt.Color(204, 204, 204));

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã KH", "Tên KH", "Số điện thoại", "Ngày tạo", "Địa chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.setFocusable(false);
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblKhachHang);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Tìm kiếm");

        txtTimKiemKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKHKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout DanhSachKHJPanelLayout = new javax.swing.GroupLayout(DanhSachKHJPanel);
        DanhSachKHJPanel.setLayout(DanhSachKHJPanelLayout);
        DanhSachKHJPanelLayout.setHorizontalGroup(
            DanhSachKHJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
            .addGroup(DanhSachKHJPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiemKH, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                .addGap(77, 77, 77))
        );
        DanhSachKHJPanelLayout.setVerticalGroup(
            DanhSachKHJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DanhSachKHJPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(DanhSachKHJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
        );

        KhachHangJTabbedPane.addTab("Danh sách", DanhSachKHJPanel);

        javax.swing.GroupLayout JPanelKhachHangLayout = new javax.swing.GroupLayout(JPanelKhachHang);
        JPanelKhachHang.setLayout(JPanelKhachHangLayout);
        JPanelKhachHangLayout.setHorizontalGroup(
            JPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(KhachHangJTabbedPane)
        );
        JPanelKhachHangLayout.setVerticalGroup(
            JPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(KhachHangJTabbedPane)
        );

        JPanelCard.add(JPanelKhachHang, "JPanelKhachHang");

        tblLoaiXe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã loại xe", "Tên loại xe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLoaiXe.setFocusable(false);
        tblLoaiXe.setShowGrid(false);
        tblLoaiXe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiXeMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblLoaiXe);

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel23.setText("QUẢN LÝ LOẠI XE");

        jLabel8.setText("Tên loại xe");

        txtTenLX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenLXKeyReleased(evt);
            }
        });

        btnLuuLX.setText("Thêm");
        btnLuuLX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuLXActionPerformed(evt);
            }
        });

        btnXoaLX.setText("Xóa");
        btnXoaLX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaLXActionPerformed(evt);
            }
        });

        btnResetLX.setText("Reset");
        btnResetLX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetLXActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LoaiXeJPanelLayout = new javax.swing.GroupLayout(LoaiXeJPanel);
        LoaiXeJPanel.setLayout(LoaiXeJPanelLayout);
        LoaiXeJPanelLayout.setHorizontalGroup(
            LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoaiXeJPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addGap(62, 62, 62)
                .addGroup(LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoaiXeJPanelLayout.createSequentialGroup()
                        .addGroup(LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMaLoaiXe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenLX)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMaLX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(LoaiXeJPanelLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(btnLuuLX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaLX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetLX)
                        .addGap(0, 24, Short.MAX_VALUE)))
                .addGap(72, 72, 72))
        );
        LoaiXeJPanelLayout.setVerticalGroup(
            LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
            .addGroup(LoaiXeJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addGap(35, 35, 35)
                .addGroup(LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMaLoaiXe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaLX, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTenLX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLuuLX)
                    .addComponent(btnXoaLX)
                    .addComponent(btnResetLX))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        XeMayJTabbedPane.addTab("Quản Lý Loại Xe", LoaiXeJPanel);

        tblMauXe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã màu xe", "Tên màu xe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMauXe.setFocusable(false);
        tblMauXe.setShowGrid(false);
        tblMauXe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMauXeMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblMauXe);

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel24.setText("QUẢN LÝ MÀU XE");

        jLabel10.setText("Tên màu xe");

        txtTenMX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenMXKeyReleased(evt);
            }
        });

        btnLuuMX.setText("Thêm");
        btnLuuMX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuMXActionPerformed(evt);
            }
        });

        btnXoaMX.setText("Xóa");
        btnXoaMX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaMXActionPerformed(evt);
            }
        });

        btnResetMX.setText("Reset");
        btnResetMX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetMXActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MauXeJPanelLayout = new javax.swing.GroupLayout(MauXeJPanel);
        MauXeJPanel.setLayout(MauXeJPanelLayout);
        MauXeJPanelLayout.setHorizontalGroup(
            MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MauXeJPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addGap(62, 62, 62)
                .addGroup(MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MauXeJPanelLayout.createSequentialGroup()
                        .addGroup(MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMaMauXe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenMX)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMaMX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(MauXeJPanelLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(btnLuuMX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaMX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetMX)
                        .addGap(0, 24, Short.MAX_VALUE)))
                .addGap(72, 72, 72))
        );
        MauXeJPanelLayout.setVerticalGroup(
            MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
            .addGroup(MauXeJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addGap(35, 35, 35)
                .addGroup(MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMaMauXe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaMX, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTenMX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLuuMX)
                    .addComponent(btnXoaMX)
                    .addComponent(btnResetMX))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        XeMayJTabbedPane.addTab("Quản Lý Màu Xe", MauXeJPanel);

        tblHangXe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên hãng xe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHangXe.setFocusable(false);
        tblHangXe.setGridColor(new java.awt.Color(255, 255, 255));
        tblHangXe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHangXeMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblHangXe);

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel25.setText("QUẢN LÝ HÃNG XE");

        jLabel26.setText("Tên hãng xe");

        btnThemHX.setText("Thêm");
        btnThemHX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHXActionPerformed(evt);
            }
        });

        btnXoaHX.setText("Xóa");
        btnXoaHX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHXActionPerformed(evt);
            }
        });

        btnResetHX.setText("Reset");
        btnResetHX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetHXActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HangXeJPanelLayout = new javax.swing.GroupLayout(HangXeJPanel);
        HangXeJPanel.setLayout(HangXeJPanelLayout);
        HangXeJPanelLayout.setHorizontalGroup(
            HangXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HangXeJPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addGap(62, 62, 62)
                .addGroup(HangXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HangXeJPanelLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(HangXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenHX)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(HangXeJPanelLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(btnThemHX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaHX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetHX)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(72, 72, 72))
        );
        HangXeJPanelLayout.setVerticalGroup(
            HangXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
            .addGroup(HangXeJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addGap(66, 66, 66)
                .addGroup(HangXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtTenHX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(HangXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemHX)
                    .addComponent(btnXoaHX)
                    .addComponent(btnResetHX))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        XeMayJTabbedPane.addTab("Quản Lý Hãng Xe", HangXeJPanel);

        tblDongXe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã dòng xe", "Tên dòng xe", "Tên hãng xe", "Bảo hành"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDongXe.setFocusable(false);
        tblDongXe.setGridColor(new java.awt.Color(255, 255, 255));
        tblDongXe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDongXeMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblDongXe);

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel27.setText("QUẢN LÝ DÒNG XE");

        jLabel29.setText("Tên dòng xe");

        btnThemDX.setText("Thêm");
        btnThemDX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDXActionPerformed(evt);
            }
        });

        btnSuaDX.setText("Sửa");
        btnSuaDX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaDXActionPerformed(evt);
            }
        });

        btnXoaDX.setText("Xóa");
        btnXoaDX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDXActionPerformed(evt);
            }
        });

        btnResetDX.setText("Reset");
        btnResetDX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetDXActionPerformed(evt);
            }
        });

        jLabel30.setText("Tên hãng xe");

        jLabel31.setText("Bảo hành");

        cboTenHX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));
        cboTenHX.setSelectedIndex(1);

        jLabel52.setText("Năm");

        txtBaoHanh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBaoHanhKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout DongXeJPanelLayout = new javax.swing.GroupLayout(DongXeJPanel);
        DongXeJPanel.setLayout(DongXeJPanelLayout);
        DongXeJPanelLayout.setHorizontalGroup(
            DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DongXeJPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DongXeJPanelLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DongXeJPanelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnThemDX)
                        .addGap(6, 6, 6)
                        .addComponent(btnSuaDX)
                        .addGap(6, 6, 6)
                        .addComponent(btnXoaDX)
                        .addGap(6, 6, 6)
                        .addComponent(btnResetDX))
                    .addGroup(DongXeJPanelLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(DongXeJPanelLayout.createSequentialGroup()
                                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel31))
                                .addGap(22, 22, 22)
                                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboTenHX, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(DongXeJPanelLayout.createSequentialGroup()
                                        .addComponent(txtBaoHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel52))))
                            .addGroup(DongXeJPanelLayout.createSequentialGroup()
                                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29)
                                    .addComponent(lblMaDongXe, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMaDX, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenDX, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        DongXeJPanelLayout.setVerticalGroup(
            DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
            .addGroup(DongXeJPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel27)
                .addGap(35, 35, 35)
                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMaDongXe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaDX, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DongXeJPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel29))
                    .addComponent(txtTenDX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTenHX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(8, 8, 8)
                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBaoHanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemDX)
                    .addComponent(btnSuaDX)
                    .addComponent(btnXoaDX)
                    .addComponent(btnResetDX)))
        );

        XeMayJTabbedPane.addTab("Quản Lý Dòng Xe", DongXeJPanel);

        QLXe.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel32.setText("QUẢN LÝ THÔNG TIN XE");
        QLXe.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        lblAnhXe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnhXe.setText("Ảnh");
        lblAnhXe.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblAnhXe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhXeMouseClicked(evt);
            }
        });
        QLXe.add(lblAnhXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 330, 340));

        jLabel33.setText("Hình ảnh");
        QLXe.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, -1, -1));

        jLabel34.setText("Màu xe");
        QLXe.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 190, -1, -1));
        QLXe.add(lblXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, 50, 20));

        jLabel36.setText("Tên xe");
        QLXe.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, -1, -1));

        jLabel37.setText("Loại xe");
        QLXe.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, -1, -1));

        jLabel38.setText("Dòng xe");
        QLXe.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, -1, -1));

        jLabel39.setText("Số lượng");
        QLXe.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 310, -1, -1));
        QLXe.add(txtTenXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, 310, -1));

        cboLoaiXe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoaiXe.setToolTipText("");
        QLXe.add(cboLoaiXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 220, 310, -1));

        cboMauXe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        QLXe.add(cboMauXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 190, 310, -1));

        cboDongXe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboDongXe.setSelectedIndex(1);
        QLXe.add(cboDongXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, 310, -1));

        btnResetXe.setText("Reset");
        btnResetXe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetXeActionPerformed(evt);
            }
        });
        QLXe.add(btnResetXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 360, -1, -1));

        btnThemXe.setText("Thêm");
        btnThemXe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemXeActionPerformed(evt);
            }
        });
        QLXe.add(btnThemXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 360, -1, -1));

        btnSuaXe.setText("Sửa");
        btnSuaXe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaXeActionPerformed(evt);
            }
        });
        QLXe.add(btnSuaXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 360, -1, -1));

        btnXoaXe.setText("Xóa");
        btnXoaXe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaXeActionPerformed(evt);
            }
        });
        QLXe.add(btnXoaXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 360, -1, -1));

        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyReleased(evt);
            }
        });
        QLXe.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 310, 310, -1));

        jLabel40.setText("Hãng xe");
        QLXe.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, -1, -1));

        cboHX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));
        cboHX.setSelectedIndex(1);
        cboHX.setToolTipText("");
        cboHX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHXActionPerformed(evt);
            }
        });
        QLXe.add(cboHX, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, 310, -1));

        jLabel11.setText("Giá");
        QLXe.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 280, -1, -1));

        txtGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaKeyReleased(evt);
            }
        });
        QLXe.add(txtGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 280, 310, -1));

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        QLXe.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 50, -1));

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        QLXe.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 420, 50, -1));

        btnPrev.setText("<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        QLXe.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 420, 50, -1));

        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        QLXe.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, 50, -1));

        jLabel42.setText("Dung tích");
        QLXe.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 250, -1, -1));
        QLXe.add(txtDunhtich, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, 310, -1));
        QLXe.add(lblMaXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 310, 20));

        XeMayJTabbedPane.addTab("Quản Lý Thông Tin Xe", QLXe);

        tblDanhSachXe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã xe", "Tên xe", "Hãng", "Dòng", "Loại", "Màu", "Dung tích", "Giá", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachXe.setFocusable(false);
        tblDanhSachXe.setGridColor(new java.awt.Color(255, 255, 255));
        tblDanhSachXe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachXeMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tblDanhSachXe);

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel41.setText("Tìm kiếm");

        txtTimKiemXe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemXeKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout DanhSachXeJPanelLayout = new javax.swing.GroupLayout(DanhSachXeJPanel);
        DanhSachXeJPanel.setLayout(DanhSachXeJPanelLayout);
        DanhSachXeJPanelLayout.setHorizontalGroup(
            DanhSachXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
            .addGroup(DanhSachXeJPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiemXe, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DanhSachXeJPanelLayout.setVerticalGroup(
            DanhSachXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DanhSachXeJPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(DanhSachXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemXe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
        );

        XeMayJTabbedPane.addTab("Danh Sách Xe", DanhSachXeJPanel);

        tblSKSM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SKSM", "Mã xe", "Số khung", "Số máy"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSKSM.setFocusable(false);
        tblSKSM.setGridColor(new java.awt.Color(255, 255, 255));
        tblSKSM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSKSMMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tblSKSM);

        jLabel43.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel43.setText("QUẢN LÝ SỐ KHUNG SỐ MÁY");

        jLabel54.setText("Số khung");

        jLabel55.setText("Số máy");

        btnThemSKSM.setText("Thêm");
        btnThemSKSM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSKSMActionPerformed(evt);
            }
        });

        btnSuaSKSM.setText("Sửa");
        btnSuaSKSM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSKSMActionPerformed(evt);
            }
        });

        btnXoaSKSM.setText("Xóa");
        btnXoaSKSM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSKSMActionPerformed(evt);
            }
        });

        btnResetSKSM.setText("Reset");
        btnResetSKSM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetSKSMActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Thông tin xe"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(lblMau, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 250, 16));

        jLabel59.setText("Loại");
        jPanel1.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jLabel58.setText("Dòng");
        jPanel1.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel62.setText("Số lượng");
        jPanel1.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        lblGia.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.add(lblGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 270, 16));

        cboMaXe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));
        cboMaXe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaXeActionPerformed(evt);
            }
        });
        jPanel1.add(cboMaXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 250, -1));
        jPanel1.add(lblDungTich, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 250, 16));

        jLabel63.setText("Màu");
        jPanel1.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        jLabel56.setText("Tên xe");
        jPanel1.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));
        jPanel1.add(lblTenXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 250, 16));

        jLabel57.setText("Hãng");
        jPanel1.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));
        jPanel1.add(lblLoaiXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 250, 16));
        jPanel1.add(lblHangXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 250, 16));
        jPanel1.add(lblSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 250, 16));

        jLabel60.setText("Dung tích");
        jPanel1.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));
        jPanel1.add(lblDongXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 250, 16));

        jLabel53.setText("Mã xe");
        jPanel1.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel95.setText("Giá");
        jPanel1.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

        javax.swing.GroupLayout SKSMJPanelLayout = new javax.swing.GroupLayout(SKSMJPanel);
        SKSMJPanel.setLayout(SKSMJPanelLayout);
        SKSMJPanelLayout.setHorizontalGroup(
            SKSMJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SKSMJPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(SKSMJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(SKSMJPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblSKSM, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMaSKSM, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SKSMJPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(SKSMJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(SKSMJPanelLayout.createSequentialGroup()
                                .addComponent(btnThemSKSM)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSuaSKSM)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnXoaSKSM)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnResetSKSM))
                            .addGroup(SKSMJPanelLayout.createSequentialGroup()
                                .addGroup(SKSMJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(SKSMJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSoMay, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSoKhung, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SKSMJPanelLayout.setVerticalGroup(
            SKSMJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SKSMJPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(SKSMJPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel43)
                .addGap(32, 32, 32)
                .addGroup(SKSMJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSKSM, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaSKSM, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SKSMJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SKSMJPanelLayout.createSequentialGroup()
                        .addComponent(txtSoKhung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSoMay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SKSMJPanelLayout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel55)))
                .addGap(18, 18, 18)
                .addGroup(SKSMJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaSKSM)
                    .addComponent(btnResetSKSM)
                    .addComponent(btnSuaSKSM)
                    .addComponent(btnThemSKSM))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        XeMayJTabbedPane.addTab("Số Khung Số Máy", SKSMJPanel);

        javax.swing.GroupLayout JPanelXeMayLayout = new javax.swing.GroupLayout(JPanelXeMay);
        JPanelXeMay.setLayout(JPanelXeMayLayout);
        JPanelXeMayLayout.setHorizontalGroup(
            JPanelXeMayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(XeMayJTabbedPane)
        );
        JPanelXeMayLayout.setVerticalGroup(
            JPanelXeMayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(XeMayJTabbedPane)
        );

        JPanelCard.add(JPanelXeMay, "JPanelXeMay");

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã hóa đơn", "Mã khách hàng", "Tên khách hàng", "Mã nhân viên", "Tên nhân viên", "Ngày tạo", "Trạng thái", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHD.setFocusable(false);
        tblHD.setShowGrid(false);
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tblHD);

        jLabel45.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("HÓA ĐƠN");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel47.setText("Mã khách hàng");

        jLabel48.setText("Tên khách hàng");

        jLabel49.setText("Mã nhân viên");

        jLabel50.setText("Tên nhân viên");

        jLabel51.setText("Ngày tạo HĐ");

        jLabel6.setText("Trạng thái");

        btnLuuHD.setText("Tạo HĐ");
        btnLuuHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuHDActionPerformed(evt);
            }
        });

        btnInHD.setText("In .PDF");
        btnInHD.setEnabled(false);
        btnInHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHDActionPerformed(evt);
            }
        });

        btnXoaHD.setText("Xóa");
        btnXoaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHDActionPerformed(evt);
            }
        });

        btnResSetHD.setText("Reset");
        btnResSetHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResSetHDActionPerformed(evt);
            }
        });

        lblNgayTaoHD.setText(XDate.toString(new Date(), "dd-MM-yyyy"));

        btnGThanhToan.add(rdoDaThanhToan);
        rdoDaThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoDaThanhToan.setForeground(new java.awt.Color(0, 204, 0));
        rdoDaThanhToan.setText("Đã thanh toán");
        rdoDaThanhToan.setEnabled(false);
        rdoDaThanhToan.setFocusPainted(false);

        btnGThanhToan.add(rdoChuaThanhToan);
        rdoChuaThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rdoChuaThanhToan.setForeground(new java.awt.Color(204, 0, 0));
        rdoChuaThanhToan.setText("Chưa thanh toán");
        rdoChuaThanhToan.setEnabled(false);
        rdoChuaThanhToan.setFocusPainted(false);

        jLabel35.setText("Tổng tiền");

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(255, 0, 0));
        lblTongTien.setText("0");
        lblTongTien.setName(""); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addGap(6, 6, 6)
                        .addComponent(lblTenKHHD, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addGap(18, 18, 18)
                        .addComponent(lblMaNVHD, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addGap(16, 16, 16)
                        .addComponent(lblTenNVHD, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addGap(22, 22, 22)
                        .addComponent(lblNgayTaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(32, 32, 32)
                        .addComponent(rdoDaThanhToan)
                        .addGap(6, 6, 6)
                        .addComponent(rdoChuaThanhToan))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(40, 40, 40)
                        .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblTitleMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaKHHD, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMaHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLuuHD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInHD)
                .addGap(6, 6, 6)
                .addComponent(btnXoaHD)
                .addGap(6, 6, 6)
                .addComponent(btnResSetHD)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitleMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addComponent(lblMaKHHD, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48)
                    .addComponent(lblTenKHHD, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49)
                    .addComponent(lblMaNVHD, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50)
                    .addComponent(lblTenNVHD, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51)
                    .addComponent(lblNgayTaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel6))
                    .addComponent(rdoDaThanhToan)
                    .addComponent(rdoChuaThanhToan))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(lblTongTien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnInHD)
                        .addComponent(btnLuuHD))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnResSetHD)
                        .addComponent(btnXoaHD)))
                .addGap(14, 14, 14))
        );

        tblKHHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã KH", "Tên KH", "SĐT", "Ngày tạo", "Địa chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKHHD.setFocusable(false);
        tblKHHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKHHDMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tblKHHD);

        txtTimKHHD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKHHDKeyReleased(evt);
            }
        });

        jLabel9.setText("Tìm");

        jLabel64.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("KHÁCH HÀNG");

        buttonGroup1.add(rdoDTT);
        rdoDTT.setForeground(new java.awt.Color(0, 255, 51));
        rdoDTT.setText("Đã thanh toán");
        rdoDTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDTTActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoCTT);
        rdoCTT.setForeground(new java.awt.Color(255, 0, 0));
        rdoCTT.setText("Chưa thanh toán");
        rdoCTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoCTTActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoALL);
        rdoALL.setText("ALL");
        rdoALL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoALLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HoaDonJPanelLayout = new javax.swing.GroupLayout(HoaDonJPanel);
        HoaDonJPanel.setLayout(HoaDonJPanelLayout);
        HoaDonJPanelLayout.setHorizontalGroup(
            HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel45)
                        .addGap(140, 140, 140)
                        .addComponent(rdoALL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoDTT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoCTT))
                    .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE))
                            .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTimKHHD))
                                    .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        HoaDonJPanelLayout.setVerticalGroup(
            HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(rdoDTT)
                    .addComponent(rdoCTT)
                    .addComponent(rdoALL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                        .addComponent(jLabel64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtTimKHHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        HoaDonJTabbedPane.addTab("Hóa Đơn", HoaDonJPanel);

        jLabel65.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setText("HÓA ĐƠN CHI TIẾT");

        tblHoaDonCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã HDCT", "Mã HĐ", "Mã xe", "Xe", "Hãng", "Dòng", "Loại", "Màu", "Dung tích xilanh", "Số khung", "Số máy", "SL", "Giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonCT.setFocusable(false);
        tblHoaDonCT.setShowGrid(false);
        tblHoaDonCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonCTMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tblHoaDonCT);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel66.setText("Mã HĐCT");

        jLabel67.setText("Mã HĐ:");

        jLabel68.setText("Tên xe:");

        jLabel69.setText("Tên hãng:");

        jLabel70.setText("Tên dòng:");

        jLabel71.setText("Tên loại:");

        jLabel28.setText("Màu:");

        jLabel44.setText("Dung tích:");

        jLabel72.setText("Số khung");

        jLabel73.setText("Mã xe:");

        cbosksm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1" }));
        cbosksm.setToolTipText("");
        cbosksm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbosksmActionPerformed(evt);
            }
        });

        jLabel74.setText("Số máy:");

        lblSoLuong1.setText("1");

        jLabel75.setText("Số lượng");

        jLabel76.setText("Giá");

        jLabel77.setText("Thành tiền");

        lblThanhTien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblThanhTien.setForeground(new java.awt.Color(255, 0, 0));

        btnLuuHDCT.setText("Thêm SP");
        btnLuuHDCT.setEnabled(false);
        btnLuuHDCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuHDCTActionPerformed(evt);
            }
        });

        jButton5.setText("Reset");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        btnXoaSP.setText("Xóa SP");
        btnXoaSP.setEnabled(false);
        btnXoaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSPActionPerformed(evt);
            }
        });

        jLabel17.setText("VNĐ");

        jLabel88.setForeground(new java.awt.Color(255, 0, 0));
        jLabel88.setText("VNĐ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addGap(17, 17, 17)
                        .addComponent(lblMaHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel67)
                        .addGap(28, 28, 28)
                        .addComponent(lblMaHD1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lblMaXe1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addGap(30, 30, 30)
                        .addComponent(lblTenXe1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel69)
                        .addGap(16, 16, 16)
                        .addComponent(lblTenHang1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel70)
                        .addGap(15, 15, 15)
                        .addComponent(lblTenDong1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel71)
                        .addGap(24, 24, 24)
                        .addComponent(lblTenLoai1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(41, 41, 41)
                        .addComponent(lblTenMau1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addGap(13, 13, 13)
                        .addComponent(lblDungTich1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel72)
                        .addGap(18, 18, 18)
                        .addComponent(cbosksm, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel74)
                        .addGap(26, 26, 26)
                        .addComponent(lblSoMay1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel75)
                        .addGap(21, 21, 21)
                        .addComponent(lblSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnLuuHDCT)
                        .addGap(6, 6, 6)
                        .addComponent(btnXoaSP)
                        .addGap(6, 6, 6)
                        .addComponent(jButton5))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel77)
                                .addGap(12, 12, 12)
                                .addComponent(lblThanhTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel76)
                                .addGap(51, 51, 51)
                                .addComponent(lblGia1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel88))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel66)
                    .addComponent(lblMaHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel67)
                    .addComponent(lblMaHD1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaXe1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel68)
                    .addComponent(lblTenXe1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel69)
                    .addComponent(lblTenHang1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel70)
                    .addComponent(lblTenDong1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel71)
                    .addComponent(lblTenLoai1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(lblTenMau1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addComponent(lblDungTich1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel72)
                    .addComponent(cbosksm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel74)
                    .addComponent(lblSoMay1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel75)
                    .addComponent(lblSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel76))
                            .addComponent(lblGia1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel77))
                    .addComponent(lblThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel88))
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLuuHDCT)
                    .addComponent(btnXoaSP)
                    .addComponent(jButton5)))
        );

        jLabel78.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel78.setText("SẢN PHẨM");

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã xe", "Xe", "Hãng", "Dòng", "Loại", "Màu", "Dung tích xilanh", "SL", "Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.setFocusable(false);
        tblSanPham.setShowGrid(false);
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane16.setViewportView(tblSanPham);

        txtTimSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimSPKeyReleased(evt);
            }
        });

        jLabel79.setText("Tìm");

        lblTextThanhToanHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTextThanhToanHD.setForeground(new java.awt.Color(255, 0, 0));
        lblTextThanhToanHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(lblTextThanhToanHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel79)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimSP)
                                .addGap(19, 19, 19))
                            .addComponent(jLabel78, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                            .addComponent(jScrollPane14)))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel65)
                    .addComponent(lblTextThanhToanHD, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel78)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel79))
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        HoaDonJTabbedPane.addTab("Hóa Đơn Chi Tiết", jPanel3);

        javax.swing.GroupLayout JPanelHoaDonLayout = new javax.swing.GroupLayout(JPanelHoaDon);
        JPanelHoaDon.setLayout(JPanelHoaDonLayout);
        JPanelHoaDonLayout.setHorizontalGroup(
            JPanelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HoaDonJTabbedPane)
        );
        JPanelHoaDonLayout.setVerticalGroup(
            JPanelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HoaDonJTabbedPane)
        );

        JPanelCard.add(JPanelHoaDon, "JPanelHoaDon");
        JPanelHoaDon.getAccessibleContext().setAccessibleDescription("");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HĐ", "Mã KH", "Tên KH", "Mã NV", "Tên NV", "Trạng thái", "Ngày tạo", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane17.setViewportView(jTable1);

        btn_in.setText("In ra");
        btn_in.setEnabled(false);
        btn_in.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_tongtien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_tongtien.setForeground(new java.awt.Color(255, 51, 0));
        txt_tongtien.setText("0");

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel81.setText("Tổng tiền đã bán được:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel81)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_tongtien)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(txt_tongtien))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel89.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel89.setText("Từ:");

        txt_tu.setForeground(new java.awt.Color(255, 51, 0));
        txt_tu.setText("00/00/0000");

        jLabel91.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel91.setText("Đến:");

        txt_den.setForeground(new java.awt.Color(255, 51, 0));
        txt_den.setText("00/00/0000");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel89)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_tu)
                .addGap(38, 38, 38)
                .addComponent(jLabel91)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_den)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel89)
                    .addComponent(txt_tu)
                    .addComponent(jLabel91)
                    .addComponent(txt_den))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ngaybatdau.setDateFormatString("dd/MM/yyyy");

        ngaykethuc.setDateFormatString("dd/MM/yyyy");

        jLabel92.setText("Từ ngày");

        jLabel93.setText("Đến ngày");

        btn_doanhso.setText("Xem doanh  thu");
        btn_doanhso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_doanhsoActionPerformed(evt);
            }
        });

        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel94.setText("thống kê");

        cb_DSB.setText("Danh số bán");
        cb_DSB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_DSBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JPanelThongKeLayout = new javax.swing.GroupLayout(JPanelThongKe);
        JPanelThongKe.setLayout(JPanelThongKeLayout);
        JPanelThongKeLayout.setHorizontalGroup(
            JPanelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanelThongKeLayout.createSequentialGroup()
                .addGroup(JPanelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPanelThongKeLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(JPanelThongKeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(JPanelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPanelThongKeLayout.createSequentialGroup()
                                .addComponent(jLabel92)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ngaybatdau, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanelThongKeLayout.createSequentialGroup()
                                .addComponent(jLabel93)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addGroup(JPanelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cb_DSB)
                                    .addComponent(ngaykethuc, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(JPanelThongKeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_doanhso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(8, 8, 8)
                .addGroup(JPanelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_in, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE))
                .addContainerGap())
        );
        JPanelThongKeLayout.setVerticalGroup(
            JPanelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanelThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPanelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel94))
                .addGroup(JPanelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPanelThongKeLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JPanelThongKeLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(JPanelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel92)
                            .addComponent(ngaybatdau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(JPanelThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel93)
                            .addComponent(ngaykethuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addComponent(cb_DSB)
                        .addGap(18, 18, 18)
                        .addComponent(btn_doanhso, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_in, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        JPanelCard.add(JPanelThongKe, "JPanelThongKe");

        lblAnhNVCT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnhNVCT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblAnhNVCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhNVCTMouseClicked(evt);
            }
        });

        jLabel80.setText("Mã NV");

        jLabel82.setText("Tên NV");

        jLabel83.setText("Số điện thoại");

        jLabel84.setText("Mật khẩu");

        jLabel85.setText("Địa chỉ");

        jLabel86.setText("Vai trò");

        txtDiaChiNVCT.setColumns(20);
        txtDiaChiNVCT.setRows(5);
        jScrollPane13.setViewportView(txtDiaChiNVCT);

        txtMatKhauCT.setEditable(false);

        lblVaiTroNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblVaiTroNV.setForeground(new java.awt.Color(255, 0, 0));

        btnCapNhatTT.setText("Cập nhật thông tin");
        btnCapNhatTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatTTActionPerformed(evt);
            }
        });

        btnDoiMatKhau.setText("Đổi mật khẩu");
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });

        jLabel90.setText("jLabel90");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel86))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnCapNhatTT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                                .addComponent(btnDoiMatKhau))
                            .addComponent(lblMaNVCT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane13)
                            .addComponent(txtMatKhauCT)
                            .addComponent(txtTenNVCT)
                            .addComponent(txtSoDienThoaiCT)
                            .addComponent(lblVaiTroNV, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblAnhNVCT, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97)))
                .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblAnhNVCT, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaNVCT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel80))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel82)
                                    .addComponent(txtTenNVCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel83)
                                    .addComponent(txtSoDienThoaiCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel84)
                                    .addComponent(txtMatKhauCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel86)
                                    .addComponent(lblVaiTroNV, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel85)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(186, 186, 186)
                                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(10, 10, 10)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCapNhatTT)
                            .addComponent(btnDoiMatKhau))))
                .addContainerGap())
        );

        javax.swing.GroupLayout JPanelCaiDatLayout = new javax.swing.GroupLayout(JPanelCaiDat);
        JPanelCaiDat.setLayout(JPanelCaiDatLayout);
        JPanelCaiDatLayout.setHorizontalGroup(
            JPanelCaiDatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JPanelCaiDatLayout.setVerticalGroup(
            JPanelCaiDatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        JPanelCard.add(JPanelCaiDat, "JPanelCaiDat");

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel87.setText("Cửa Hàng Xe Máy");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane15.setViewportView(jTextArea2);

        javax.swing.GroupLayout JPanelAboutYouLayout = new javax.swing.GroupLayout(JPanelAboutYou);
        JPanelAboutYou.setLayout(JPanelAboutYouLayout);
        JPanelAboutYouLayout.setHorizontalGroup(
            JPanelAboutYouLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanelAboutYouLayout.createSequentialGroup()
                .addGroup(JPanelAboutYouLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel87, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE))
                .addContainerGap())
        );
        JPanelAboutYouLayout.setVerticalGroup(
            JPanelAboutYouLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanelAboutYouLayout.createSequentialGroup()
                .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
        );

        JPanelCard.add(JPanelAboutYou, "JPanelAboutYou");

        jSplitPane1.setRightComponent(JPanelCard);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSplitPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSplitPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHangActionPerformed
        // TODO add your handling code here:
        card.show(JPanelCard, "JPanelKhachHang");
    }//GEN-LAST:event_btnKhachHangActionPerformed

    private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienActionPerformed
        // TODO add your handling code here:
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Chức năng này chỉ dành cho quản lý!");
            return;
        } else {
            card.show(JPanelCard, "JPanelNhanVien");
        }
        txtMaNV.requestFocus();
    }//GEN-LAST:event_btnNhanVienActionPerformed

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        // TODO add your handling code here:
        card.show(JPanelCard, "JPanelCaiDat");
        this.UserInfoCT();
    }//GEN-LAST:event_lblAnhMouseClicked

    private void lblHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMouseClicked
        // TODO add your handling code here:
        choosePicture();
        if (lblHinhAnh.getIcon() != null) {
            btnXoaAnhNhanVien.setEnabled(true);
        }
    }//GEN-LAST:event_lblHinhAnhMouseClicked

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblNhanVien.getSelectedRow();
            if (this.row >= 0) {
                this.editNhanVien();
            }
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnSuaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaNhanVienActionPerformed
        // TODO add your handling code here:
        this.updateNhanVien();
    }//GEN-LAST:event_btnSuaNhanVienActionPerformed

    private void btnXoaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNhanVienActionPerformed
        // TODO add your handling code here:
        this.deleteNhanVien();
    }//GEN-LAST:event_btnXoaNhanVienActionPerformed

    private void btnResetNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetNhanVienActionPerformed
        // TODO add your handling code here:
        this.clearFormNhanVien();

    }//GEN-LAST:event_btnResetNhanVienActionPerformed

    private void btnThemNhanvienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhanvienActionPerformed
        // TODO add your handling code here:
        lblHinhAnh.setToolTipText("Chưa thêm ảnh!");
        this.insertNhanVien();
    }//GEN-LAST:event_btnThemNhanvienActionPerformed

    private void txtSodienthoaiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSodienthoaiKeyReleased
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            txtSodienthoai.setText("");
        }
    }//GEN-LAST:event_txtSodienthoaiKeyReleased

    private void btnXoaAnhNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaAnhNhanVienActionPerformed
        // TODO add your handling code here:
        btnXoaAnhNhanVien.setEnabled(false);
        lblHinhAnh.setIcon(null);
        lblHinhAnh.setToolTipText("Chưa thêm ảnh!");
    }//GEN-LAST:event_btnXoaAnhNhanVienActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblKhachHang.getSelectedRow();
            if (this.row >= 0) {
                this.editKhachHang();
            }
        }
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnResetKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetKHActionPerformed
        // TODO add your handling code here:
        this.clearFormKhachHang();

    }//GEN-LAST:event_btnResetKHActionPerformed

    private void btnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKHActionPerformed
        // TODO add your handling code here:
        this.insertKhachHang();
    }//GEN-LAST:event_btnThemKHActionPerformed

    private void txtSodienthoaiKHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSodienthoaiKHKeyReleased
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            txtSodienthoaiKH.setText("");
        }
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txtDiaChiKH.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txtTenKH.requestFocus();
        }
    }//GEN-LAST:event_txtSodienthoaiKHKeyReleased

    private void btnSuaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKHActionPerformed
        // TODO add your handling code here:
        this.updateKhachHang();
    }//GEN-LAST:event_btnSuaKHActionPerformed

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
        // TODO add your handling code here:
        this.deleteKhachHang();
    }//GEN-LAST:event_btnXoaKHActionPerformed

    private void btnXeMayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXeMayActionPerformed
        // TODO add your handling code here:
        card.show(JPanelCard, "JPanelXeMay");
        if (XeMayJTabbedPane.getSelectedIndex() == 0) {
            txtTenLX.requestFocus();
        }
    }//GEN-LAST:event_btnXeMayActionPerformed

    private void tblLoaiXeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiXeMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tblLoaiXe.getSelectedRow();
            if (this.row >= 0) {
                this.editLoaiXe();
                List<LoaiXe> list = daolx.selectAll();
                for (LoaiXe lx1 : list) {
                    if (lx1.getTenLX().equalsIgnoreCase(txtTenLX.getText())) {
                        btnLuuLX.setEnabled(false);
                        btnXoaLX.setEnabled(true);
                        return;
                    } else {
                        btnLuuLX.setEnabled(true);
                        btnXoaLX.setEnabled(false);
                    }
                }
            }
        }
    }//GEN-LAST:event_tblLoaiXeMouseClicked

    private void btnResetLXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetLXActionPerformed
        // TODO add your handling code here:
        this.clearFormLoaiXe();
    }//GEN-LAST:event_btnResetLXActionPerformed

    private void btnLuuLXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuLXActionPerformed
        // TODO add your handling code here:
        this.insertLoaiXe();
    }//GEN-LAST:event_btnLuuLXActionPerformed

    private void btnXoaLXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLXActionPerformed
        // TODO add your handling code here:
        this.deleteLoaiXe();
    }//GEN-LAST:event_btnXoaLXActionPerformed

    private void tblMauXeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMauXeMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tblMauXe.getSelectedRow();
            if (this.row >= 0) {
                this.editMauXe();
                List<MauXe> list = daomx.selectAll();
                for (MauXe mx1 : list) {
                    if (mx1.getTenMX().equalsIgnoreCase(txtTenMX.getText())) {
                        btnLuuMX.setEnabled(false);
                        btnXoaMX.setEnabled(true);
                        return;
                    } else {
                        btnLuuMX.setEnabled(true);
                        btnXoaMX.setEnabled(false);
                    }
                }
            }
        }
    }//GEN-LAST:event_tblMauXeMouseClicked

    private void btnLuuMXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuMXActionPerformed
        // TODO add your handling code here:
        this.insertMauXe();
    }//GEN-LAST:event_btnLuuMXActionPerformed

    private void btnXoaMXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaMXActionPerformed
        // TODO add your handling code here:
        this.deleteMauXe();
    }//GEN-LAST:event_btnXoaMXActionPerformed

    private void btnResetMXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetMXActionPerformed
        // TODO add your handling code here:
        this.clearFormMauXe();
    }//GEN-LAST:event_btnResetMXActionPerformed

    private void tblHangXeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHangXeMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tblHangXe.getSelectedRow();
            if (this.row >= 0) {
                this.editHangXe();
            }
        }
    }//GEN-LAST:event_tblHangXeMouseClicked

    private void btnThemHXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHXActionPerformed
        // TODO add your handling code here:
        this.insertHangXe();
    }//GEN-LAST:event_btnThemHXActionPerformed

    private void btnXoaHXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHXActionPerformed
        // TODO add your handling code here:
        this.deleteHangXe();
        this.fillComboBoxXe();
    }//GEN-LAST:event_btnXoaHXActionPerformed

    private void btnResetHXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetHXActionPerformed
        // TODO add your handling code here:
        this.clearFormHangXe();
    }//GEN-LAST:event_btnResetHXActionPerformed

    private void tblDongXeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDongXeMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tblDongXe.getSelectedRow();
            if (this.row >= 0) {
                this.editDongXe();
            }
        }
    }//GEN-LAST:event_tblDongXeMouseClicked

    private void btnThemDXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDXActionPerformed
        // TODO add your handling code here:
        this.insertDongXe();
    }//GEN-LAST:event_btnThemDXActionPerformed

    private void btnSuaDXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaDXActionPerformed
        // TODO add your handling code here:
        this.updateDongXe();
    }//GEN-LAST:event_btnSuaDXActionPerformed

    private void btnXoaDXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDXActionPerformed
        // TODO add your handling code here:
        this.deleteDongXe();
        this.fillComboBoxXe();
    }//GEN-LAST:event_btnXoaDXActionPerformed

    private void btnResetDXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetDXActionPerformed
        // TODO add your handling code here:
        this.clearFormDongXe();
    }//GEN-LAST:event_btnResetDXActionPerformed

    private void cboHXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHXActionPerformed
        // TODO add your handling code here:
        String dl = (String) cboHX.getSelectedItem();
        cboHX.setToolTipText(dl);
        this.fillComboBoxDX();
    }//GEN-LAST:event_cboHXActionPerformed

    private void tblDanhSachXeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachXeMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblDanhSachXe.getSelectedRow();
            if (this.row >= 0) {
                this.editXe();
            }
        }
    }//GEN-LAST:event_tblDanhSachXeMouseClicked

    private void btnThemXeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemXeActionPerformed
        // TODO add your handling code here:
        this.insertXe();
        this.fillComboBoxXe();
    }//GEN-LAST:event_btnThemXeActionPerformed

    private void btnResetXeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetXeActionPerformed
        // TODO add your handling code here:
        this.clearFormXe();
    }//GEN-LAST:event_btnResetXeActionPerformed

    private void btnSuaXeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaXeActionPerformed
        // TODO add your handling code here:
        this.updateXe();
        this.fillComboBoxXe();
    }//GEN-LAST:event_btnSuaXeActionPerformed

    private void btnXoaXeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaXeActionPerformed
        // TODO add your handling code here:
        this.deleteXe();
        this.fillComboBoxXe();
    }//GEN-LAST:event_btnXoaXeActionPerformed

    private void lblAnhXeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhXeMouseClicked
        // TODO add your handling code here:
        choosePictureXe();
    }//GEN-LAST:event_lblAnhXeMouseClicked

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void txtGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaKeyReleased
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            txtGia.setText("");
        }
    }//GEN-LAST:event_txtGiaKeyReleased

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed
        // TODO add your handling code here:
        card.show(JPanelCard, "JPanelHoaDon");
        String tenNV = Auth.user.getTenNV();
        String userID = Auth.user.getMaNV();
        lblMaNVHD.setText(userID);
        lblTenNVHD.setText(tenNV);
    }//GEN-LAST:event_btnHoaDonActionPerformed

    private void txtTimkiemNVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimkiemNVKeyReleased
        // TODO add your handling code here:
        DefaultTableModel objNV = (DefaultTableModel) tblNhanVien.getModel();
        TableRowSorter<DefaultTableModel> objNV1 = new TableRowSorter<>(objNV);
        tblNhanVien.setRowSorter(objNV1);
        objNV1.setRowFilter(RowFilter.regexFilter(txtTimkiemNV.getText()));
    }//GEN-LAST:event_txtTimkiemNVKeyReleased

    private void btnThemSKSMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSKSMActionPerformed
        // TODO add your handling code here:
        this.insertSKSMXe();
    }//GEN-LAST:event_btnThemSKSMActionPerformed

    private void btnSuaSKSMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSKSMActionPerformed
        // TODO add your handling code here:
        this.updateSKSMXeUP();
    }//GEN-LAST:event_btnSuaSKSMActionPerformed

    private void btnXoaSKSMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSKSMActionPerformed
        // TODO add your handling code here:
        this.deleteSKSMXe();
    }//GEN-LAST:event_btnXoaSKSMActionPerformed

    private void btnResetSKSMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSKSMActionPerformed
        // TODO add your handling code here:
        this.clearFormSKSMXe();
    }//GEN-LAST:event_btnResetSKSMActionPerformed

    private void tblSKSMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSKSMMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tblSKSM.getSelectedRow();
            if (this.row >= 0) {
                this.editSKSMXe();
            }
        }
    }//GEN-LAST:event_tblSKSMMouseClicked

    private void cboMaXeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaXeActionPerformed
        // TODO add your handling code here:
        int maxe = cboMaXe.getSelectedIndex();
        if (tblDanhSachXe.getRowCount() != 0) {
            if (maxe > -1) {
                String a = cboMaXe.getItemAt(maxe);
                String[] mangChuoi = a.split("\\s+", 2);
                Xe xe = daoxe.selectById(mangChuoi[0]);
                this.setFormSKSMXe(xe);
            }
            this.fillTableSKSMXe();
        } else {
            return;
        }

        //cboMaXe.setToolTipText(maxe+"");
    }//GEN-LAST:event_cboMaXeActionPerformed

    private void txtTimKiemKHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKHKeyReleased
        // TODO add your handling code here:
        DefaultTableModel objKH = (DefaultTableModel) tblKhachHang.getModel();
        TableRowSorter<DefaultTableModel> objKH1 = new TableRowSorter<>(objKH);
        tblKhachHang.setRowSorter(objKH1);
        objKH1.setRowFilter(RowFilter.regexFilter(txtTimKiemKH.getText()));
    }//GEN-LAST:event_txtTimKiemKHKeyReleased

    private void txtTimKiemXeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemXeKeyReleased
        // TODO add your handling code here:
        DefaultTableModel objXM = (DefaultTableModel) tblDanhSachXe.getModel();
        TableRowSorter<DefaultTableModel> objXM1 = new TableRowSorter<>(objXM);
        tblDanhSachXe.setRowSorter(objXM1);
        objXM1.setRowFilter(RowFilter.regexFilter(txtTimKiemXe.getText()));
    }//GEN-LAST:event_txtTimKiemXeKeyReleased

    private void txtBaoHanhKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBaoHanhKeyReleased
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            txtBaoHanh.setText("");
        }
    }//GEN-LAST:event_txtBaoHanhKeyReleased

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        card.show(JPanelCard, "JPanelHome");
    }//GEN-LAST:event_btnHomeActionPerformed

    private void txtSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyReleased
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            txtSoLuong.setText("");
        }
    }//GEN-LAST:event_txtSoLuongKeyReleased

    private void txtTenKHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenKHKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            txtSodienthoaiKH.requestFocus();
        }
    }//GEN-LAST:event_txtTenKHKeyReleased

    private void txtDiaChiKHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiaChiKHKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            txtSodienthoaiKH.requestFocus();
        }
    }//GEN-LAST:event_txtDiaChiKHKeyReleased

    private void txtTenLXKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenLXKeyReleased
        // TODO add your handling code here:
        List<LoaiXe> list = daolx.selectAll();
        for (LoaiXe lx1 : list) {
            if (txtTenLX.getText().isBlank()) {
                btnLuuLX.setEnabled(false);
                return;
            } else {
                if (lblMaLX.getText().isBlank()) {
                    if (lx1.getTenLX().equalsIgnoreCase(txtTenLX.getText())) {
                        btnLuuLX.setEnabled(false);
                        return;
                    } else {
                        btnLuuLX.setEnabled(true);
                    }
                } else {
                    if (txtTenLX.getText().equalsIgnoreCase(lx1.getTenLX()) && lblMaLX.getText().equalsIgnoreCase(lx1.getMaLX() + "")) {
                        btnLuuLX.setEnabled(false);
                        btnXoaLX.setEnabled(true);
                        return;
                    } else if (txtTenLX.getText().equalsIgnoreCase(lx1.getTenLX())) {
                        btnLuuLX.setEnabled(false);
                        return;
                    } else {
                        btnLuuLX.setEnabled(true);
                        btnXoaLX.setEnabled(false);
                    }
                }
            }
        }
    }//GEN-LAST:event_txtTenLXKeyReleased

    private void txtTenMXKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenMXKeyReleased
        // TODO add your handling code here:
        List<MauXe> list = daomx.selectAll();
        for (MauXe mx1 : list) {
            if (txtTenMX.getText().isBlank()) {
                btnLuuMX.setEnabled(false);
                return;
            } else {
                if (lblMaMX.getText().isBlank()) {
                    if (mx1.getTenMX().equalsIgnoreCase(txtTenMX.getText())) {
                        btnLuuMX.setEnabled(false);
                        return;
                    } else {
                        btnLuuMX.setEnabled(true);
                    }
                } else {
                    if (mx1.getTenMX().equalsIgnoreCase(txtTenMX.getText()) && lblMaMX.getText().equalsIgnoreCase(mx1.getMaMX() + "")) {
                        btnLuuMX.setEnabled(false);
                        btnXoaMX.setEnabled(true);
                        return;
                    } else if (mx1.getTenMX().equalsIgnoreCase(txtTenMX.getText())) {
                        btnLuuMX.setEnabled(false);
                        return;
                    } else {
                        btnLuuMX.setEnabled(true);
                        btnXoaMX.setEnabled(false);
                    }
                }
            }
        }
    }//GEN-LAST:event_txtTenMXKeyReleased

    private void tblKHHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKHHDMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tblKHHD.getSelectedRow();
            if (this.row >= 0) {
                this.editKh();
                lblTitleMaHD.setText("");
                lblMaHD.setText("");
                lblNgayTaoHD.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
                rdoChuaThanhToan.setSelected(true);
                lblTongTien.setText("0");
                btnLuuHD.setText("Tạo HD");
            }
        }
    }//GEN-LAST:event_tblKHHDMouseClicked

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tblHD.getSelectedRow();
            if (this.row >= 0) {
                this.editHD();
                btnLuuHD.setText("Lưu");
                lblMaHD1.setText(lblMaHD.getText());
                this.fillTableHDCT();
            }
        }
        if (evt.getClickCount() == 2) {
            this.row = tblHD.getSelectedRow();
            if (this.row >= 0) {
                lblMaHD1.setText(lblMaHD.getText());
                this.cleareFormGetclick();
                if (rdoDaThanhToan.isSelected()) {
                    btnXoaHD.setEnabled(false);
                    lblTextThanhToanHD.setText("ĐÃ THANH TOÁN");
                } else {
                    lblTextThanhToanHD.setText("");
                }
                HoaDonJTabbedPane.setSelectedIndex(1);
                this.fillTableHDCT();
                this.fillTableSP();
                if (!lblMaHD1.getText().isBlank() && !lblMaXe1.getText().isBlank()) {
                    btnLuuHDCT.setEnabled(true);
                    btnXoaSP.setEnabled(false);
                }
            }
        }

    }//GEN-LAST:event_tblHDMouseClicked

    private void btnResSetHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResSetHDActionPerformed
        // TODO add your handling code here:
        this.clearFormHD();

    }//GEN-LAST:event_btnResSetHDActionPerformed

    private void btnLuuHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuHDActionPerformed
        // TODO add your handling code here:
        this.insertHD();
    }//GEN-LAST:event_btnLuuHDActionPerformed

    private void btnXoaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHDActionPerformed
        // TODO add your handling code here:
        this.deleteHD();
    }//GEN-LAST:event_btnXoaHDActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        String sksm = (String) cbosksm.getSelectedItem();
        if (evt.getClickCount() == 1) {
            this.row = tblSanPham.getSelectedRow();
            if (this.row >= 0) {
                this.editSP();
//                if (sksm.equalsIgnoreCase("Vui lòng thêm số khung")) {
//                    btnLuuHDCT.setEnabled(false);
//                }
                if (lblTextThanhToanHD.getText().isBlank()) {
                    lblMaHDCT.setText("");
                } else {
                    tblSanPham.setEnabled(false);
                    btnXoaSP.setEnabled(false);
                    btnLuuHDCT.setEnabled(false);
                }

            }
        }

    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void cbosksmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbosksmActionPerformed
        // TODO add your handling code here:
        String sksm = (String) cbosksm.getSelectedItem();
        if (sksm != null) {
            List<SoKhungSoMay> sksm1 = daosksm.selectBysm(sksm);
            for (SoKhungSoMay m : sksm1) {
                lblSoMay1.setText(m.getSomay() + "");
            }
        } else {
            btnLuuHDCT.setEnabled(false);
            return;
        }

    }//GEN-LAST:event_cbosksmActionPerformed

    private void tblHoaDonCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonCTMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tblHoaDonCT.getSelectedRow();
            if (this.row >= 0) {
                this.editHDCT();
                if (lblTextThanhToanHD.getText().isBlank()) {
                    btnXoaSP.setEnabled(true);
                    btnLuuHDCT.setEnabled(false);
                } else {
                    btnXoaSP.setEnabled(false);
                    btnLuuHDCT.setEnabled(false);
                }
            }
        }
    }//GEN-LAST:event_tblHoaDonCTMouseClicked

    private void btnLuuHDCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuHDCTActionPerformed
        // TODO add your handling code here:
        this.insertHDCT();
    }//GEN-LAST:event_btnLuuHDCTActionPerformed

    private void btnXoaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSPActionPerformed
        // TODO add your handling code here:
        this.deleteHDCT();
    }//GEN-LAST:event_btnXoaSPActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.clearFormHDCT();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        // TODO add your handling code here:
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Chức năng này chỉ dành cho quản lý!");
            return;
        } else {
            card.show(JPanelCard, "JPanelThongKe");
        }
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void btnCaiDatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaiDatActionPerformed
        // TODO add your handling code here:
        card.show(JPanelCard, "JPanelCaiDat");
        this.UserInfoCT();
    }//GEN-LAST:event_btnCaiDatActionPerformed

    private void btnAboutYouActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutYouActionPerformed
        // TODO add your handling code here:
        card.show(JPanelCard, "JPanelAboutYou");
    }//GEN-LAST:event_btnAboutYouActionPerformed

    private void btnInHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHDActionPerformed
        // TODO add your handling code here:
        try {
            Document document = new Document();
            String tenkh = lblTenKHHD.getText();
            String makh = lblMaKHHD.getText();
            String mahd = lblMaHD.getText();
            String manv = lblMaNVHD.getText();
            String tennv = lblTenNVHD.getText();
            int gia = Integer.parseInt(lblGia.getText());
            Locale locale = new Locale("vi", "VN");
            NumberFormat format = NumberFormat.getInstance(locale);
            String tongtien = format.format(gia);
            String date = XDate.toString(new Date(), "dd-mm-YYYY");
            PdfWriter.getInstance(document, new FileOutputStream("\\QLXE\\HĐ\\" + mahd + " - " + makh + " - " + tenkh + ".pdf"));
            //Paragraph.getInstance(document, new FileOutputStream("D:\\Test.pdf"));
            document.open();
            PdfPTable pdfTable = new PdfPTable(tblHoaDonCT.getColumnCount());
            pdfTable.setWidthPercentage(110);
//            float[] columnWidths = new float[]{10f, 15f, 15f, 15f, 40f, 40f, 40f, 40f, 20f, 30f, 50f, 50f, 20f, 50f, 50f}; // Thiết lập kích thước cột
//            pdfTable.setWidths(columnWidths);
            // Add table header
            for (int i = 0; i < tblHoaDonCT.getColumnCount(); i++) {
                pdfTable.addCell(new Phrase(tblHoaDonCT.getColumnName(i), FontFactory.getFont(FontFactory.TIMES, 8)));
            }
            // Add table data
            TableModel model = tblHoaDonCT.getModel();
            for (int rows = 0; rows < model.getRowCount(); rows++) {
                for (int cols = 0; cols < model.getColumnCount(); cols++) {
                    pdfTable.addCell(new Phrase(model.getValueAt(rows, cols).toString(), FontFactory.getFont(FontFactory.TIMES, 8)));
                }
            }
            Paragraph title = new Paragraph("Hóa ĐĐơơn" + "\n\n\n", FontFactory.getFont(FontFactory.TIMES, 18, new CMYKColor(0, 255, 255, 17)));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("Mã KH: " + makh + "\n"));
            document.add(new Paragraph("Tên KH: " + tenkh + "\n"));
            document.add(new Paragraph("Mã NV: " + manv + "\n"));
            document.add(new Paragraph("Tên NV: " + tennv + "\n"));
            document.add(new Paragraph("Ngày xuất hóa đơn: " + date + "\n"));

            document.add(new Paragraph("Thông tin hóa đơn" + "\n\n"));
            document.add(pdfTable);
            document.add(new Paragraph("\nTổng tiền: " + tongtien + "VND", FontFactory.getFont(FontFactory.TIMES, 15, new CMYKColor(0, 255, 255, 17))));
            document.close();
            MsgBox.alert(this, "In hóa đơn thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnInHDActionPerformed

    private void txtTimKHHDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKHHDKeyReleased
        // TODO add your handling code here:
        DefaultTableModel objKH = (DefaultTableModel) tblKHHD.getModel();
        TableRowSorter<DefaultTableModel> objKH1 = new TableRowSorter<>(objKH);
        tblKHHD.setRowSorter(objKH1);
        objKH1.setRowFilter(RowFilter.regexFilter(txtTimKHHD.getText()));
    }//GEN-LAST:event_txtTimKHHDKeyReleased

    private void txtTimSPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimSPKeyReleased
        // TODO add your handling code here:
        DefaultTableModel objSP = (DefaultTableModel) tblSanPham.getModel();
        TableRowSorter<DefaultTableModel> objSP1 = new TableRowSorter<>(objSP);
        tblSanPham.setRowSorter(objSP1);
        objSP1.setRowFilter(RowFilter.regexFilter(txtTimSP.getText()));
    }//GEN-LAST:event_txtTimSPKeyReleased

    private void lblAnhNVCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhNVCTMouseClicked
        // TODO add your handling code here:
        choosePictureNV();
    }//GEN-LAST:event_lblAnhNVCTMouseClicked

    private void btnCapNhatTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatTTActionPerformed
        // TODO add your handling code here:
        this.updateNhanVienCT();
    }//GEN-LAST:event_btnCapNhatTTActionPerformed

    private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhauActionPerformed
        // TODO add your handling code here:
        new DoiMatKhauJDialog(this, true).setVisible(true);
        this.UserInfoCT();
    }//GEN-LAST:event_btnDoiMatKhauActionPerformed

    private void btn_inActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inActionPerformed

        try {
            Document document = new Document();
            String tenkh = lblTenKHHD.getText();
            String makh = lblMaKHHD.getText();
            String mahd = lblMaHD.getText();
            PdfWriter.getInstance(document, new FileOutputStream("D:\\QLXE\\DoanhThu\\" + date1 + ".pdf"));
            document.open();
            PdfPTable pdfTable = new PdfPTable(jTable1.getColumnCount());
            pdfTable.setWidthPercentage(110);
            for (int i = 0; i < jTable1.getColumnCount(); i++) {
                pdfTable.addCell(new Phrase(jTable1.getColumnName(i), FontFactory.getFont(FontFactory.TIMES, 8)));
            }
            // Add table data
            TableModel model = jTable1.getModel();
            for (int rows = 0; rows < model.getRowCount(); rows++) {
                for (int cols = 0; cols < model.getColumnCount(); cols++) {
                    pdfTable.addCell(new Phrase(model.getValueAt(rows, cols).toString(), FontFactory.getFont(FontFactory.TIMES, 8)));
                }
            }
            Paragraph title = new Paragraph("Thống Kê Doanh Thu" + "\n\n\n", FontFactory.getFont(FontFactory.TIMES, 18, new CMYKColor(0, 255, 255, 17)));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("Từ ngày: " + txt_tu.getText() + "\n"));
            document.add(new Paragraph("Đến ngày: " + txt_den.getText() + "\n"));
            document.add(new Paragraph("Ngày xuất hóa đơn: " + date1 + "\n"));

            document.add(new Paragraph("Thông tin hóa đơn" + "\n\n"));
            document.add(pdfTable);
            document.add(new Paragraph("\nTổng tiền: " + txt_tongtien.getText(), FontFactory.getFont(FontFactory.TIMES, 15, new CMYKColor(0, 255, 255, 17))));
            document.close();
            MsgBox.alert(this, "In hóa đơn thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_inActionPerformed

    private void btn_doanhsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_doanhsoActionPerformed

        Date startDate = ngaybatdau.getDate();
        Date endDate = ngaykethuc.getDate();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");

            if (startDate == null | endDate == null) {
                JOptionPane.showMessageDialog(null, "Hãy chọn ngày bắt đầu và kết thúc");
            } else if (cb_DSB.isSelected() == true) { //Xem doanh số

                String strngaybd = formatter.format(startDate);
                String strngaytk = formatter.format(endDate);

                String tungay = formatter2.format(startDate);
                String denngay = formatter2.format(endDate);

                this.showDataList(strngaybd, strngaytk);
                this.showTong(strngaybd, strngaytk);
                this.setNgay(tungay, denngay);
            } else if (cb_DSB.isSelected() == false) {
                MsgBox.alert(this, "Vui lòng chọn kiểu thống kê");
                return;
            }
            if (jTable1.getRowCount() != 0) {
                btn_in.setEnabled(true);
                return;
            } else {
                btn_in.setEnabled(false);
                return;
            }
        } catch (Exception e) {
        }

        //        else if(cb_CTDSB.isSelected() == true)// Xem chi tiết doanh số
        //        {
        //            String strngaybd = formatter.format(startDate);
        //            String strngaytk = formatter.format(endDate);
        //
        //            String tungay = formatter2.format(startDate);
        //            String denngay = formatter2.format(endDate);
        //
        //            CT_Baocao ctbaocao = new CT_Baocao();
        //            ctbaocao.showDataList(strngaybd, strngaytk);
        //            ctbaocao.showTong(strngaybd, strngaytk);
        //            ctbaocao.showGiam(strngaybd, strngaytk);
        //            ctbaocao.setNgay(tungay, denngay);
        //
        //            //vi tri giua man hinh va maximize
        //            ctbaocao.pack();
        //            ctbaocao.setLocationRelativeTo(null);
        //            ctbaocao.setVisible(true);
        //        }
        //        else
        //        {
        //           JOptionPane.showMessageDialog(null, "Hãy chọn 1 trong 2 cái để xem");
        //        }
    }//GEN-LAST:event_btn_doanhsoActionPerformed

    private void cb_DSBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_DSBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_DSBActionPerformed

    private void rdoDTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDTTActionPerformed
        // TODO add your handling code here:
        if (rdoDTT.isSelected()) {
            if (!lblMaKHHD.getText().isBlank()) {
                DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
                model.setRowCount(0);
                NumberFormat fm = new DecimalFormat("#,###");
                try {
                    List<HoaDon> list = daohd.selectAll();
                    int i = 1;
                    for (HoaDon hd : list) {
                        if (hd.getMaKH().equalsIgnoreCase(lblMaKHHD.getText())) {
                            if (hd.isTrangThai() == true) {
                                Object[] row = {i++, hd.getMaHD(), hd.getMaKH(), hd.getTenKH(), hd.getMaNV(), hd.getTenNV(),
                                    XDate.toString(hd.getNgayTao(), "dd-MM-yyyy"), hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán", fm.format(hd.getTongTien())};
                                model.addRow(row);
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
                model.setRowCount(0);
                NumberFormat fm = new DecimalFormat("#,###");
                try {
                    List<HoaDon> list = daohd.selectAll();
                    int i = 1;
                    for (HoaDon hd : list) {
                        if (hd.isTrangThai() == true) {
                            Object[] row = {i++, hd.getMaHD(), hd.getMaKH(), hd.getTenKH(), hd.getMaNV(), hd.getTenNV(),
                                XDate.toString(hd.getNgayTao(), "dd-MM-yyyy"), hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán", fm.format(hd.getTongTien())};
                            model.addRow(row);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }//GEN-LAST:event_rdoDTTActionPerformed

    private void rdoCTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoCTTActionPerformed
        // TODO add your handling code here:
        if (rdoCTT.isSelected()) {
            if (!lblMaKHHD.getText().isBlank()) {
                DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
                model.setRowCount(0);
                NumberFormat fm = new DecimalFormat("#,###");
                try {
                    List<HoaDon> list = daohd.selectAll();
                    int i = 1;
                    for (HoaDon hd : list) {
                        if (lblMaKHHD.getText().equalsIgnoreCase(hd.getMaKH())) {
                            if (hd.isTrangThai() == false) {
                                Object[] row = {i++, hd.getMaHD(), hd.getMaKH(), hd.getTenKH(), hd.getMaNV(), hd.getTenNV(),
                                    XDate.toString(hd.getNgayTao(), "dd-MM-yyyy"), hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán", fm.format(hd.getTongTien())};
                                model.addRow(row);
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
                model.setRowCount(0);
                NumberFormat fm = new DecimalFormat("#,###");
                try {
                    List<HoaDon> list = daohd.selectAll();
                    int i = 1;
                    for (HoaDon hd : list) {
                        if (hd.isTrangThai() == false) {
                            Object[] row = {i++, hd.getMaHD(), hd.getMaKH(), hd.getTenKH(), hd.getMaNV(), hd.getTenNV(),
                                XDate.toString(hd.getNgayTao(), "dd-MM-yyyy"), hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán", fm.format(hd.getTongTien())};
                            model.addRow(row);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }//GEN-LAST:event_rdoCTTActionPerformed

    private void rdoALLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoALLActionPerformed
        // TODO add your handling code here:
        if (rdoALL.isSelected()) {
            if (!lblMaKHHD.getText().isBlank()) {
                DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
                model.setRowCount(0);
                NumberFormat fm = new DecimalFormat("#,###");
                try {
                    List<HoaDon> list = daohd.selectAll();
                    int i = 1;
                    for (HoaDon hd : list) {
                        if (lblMaKHHD.getText().equalsIgnoreCase(hd.getMaKH())) {
                            Object[] row = {i++, hd.getMaHD(), hd.getMaKH(), hd.getTenKH(), hd.getMaNV(), hd.getTenNV(),
                                XDate.toString(hd.getNgayTao(), "dd-MM-yyyy"), hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán", fm.format(hd.getTongTien())};
                            model.addRow(row);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                DefaultTableModel model = (DefaultTableModel) tblHD.getModel();
                model.setRowCount(0);
                NumberFormat fm = new DecimalFormat("#,###");
                try {
                    List<HoaDon> list = daohd.selectAll();
                    int i = 1;
                    for (HoaDon hd : list) {
                        Object[] row = {i++, hd.getMaHD(), hd.getMaKH(), hd.getTenKH(), hd.getMaNV(), hd.getTenNV(),
                            XDate.toString(hd.getNgayTao(), "dd-MM-yyyy"), hd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán", fm.format(hd.getTongTien())};
                        model.addRow(row);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }//GEN-LAST:event_rdoALLActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CapNhatJPanel;
    private javax.swing.JPanel CapNhatKhachHangJPanel;
    private javax.swing.JPanel DanhSachJPanel;
    private javax.swing.JPanel DanhSachKHJPanel;
    private javax.swing.JPanel DanhSachXeJPanel;
    private javax.swing.JPanel DongXeJPanel;
    private javax.swing.JPanel HangXeJPanel;
    private javax.swing.JPanel HoaDonJPanel;
    private javax.swing.JTabbedPane HoaDonJTabbedPane;
    private javax.swing.JPanel JPanelAboutYou;
    private javax.swing.JPanel JPanelCaiDat;
    private javax.swing.JPanel JPanelCard;
    private javax.swing.JPanel JPanelHoaDon;
    private javax.swing.JPanel JPanelHome;
    private javax.swing.JPanel JPanelKhachHang;
    private javax.swing.JPanel JPanelNhanVien;
    private javax.swing.JPanel JPanelThongKe;
    private javax.swing.JPanel JPanelXeMay;
    private javax.swing.JTabbedPane KhachHangJTabbedPane;
    private javax.swing.JPanel LoaiXeJPanel;
    private javax.swing.JPanel MauXeJPanel;
    private javax.swing.JPanel MeNuJPanel;
    private javax.swing.JTabbedPane NhanVienJTabbedPane;
    private javax.swing.JPanel QLXe;
    private javax.swing.JPanel SKSMJPanel;
    private javax.swing.JTabbedPane XeMayJTabbedPane;
    private javax.swing.JButton btnAboutYou;
    private javax.swing.JButton btnCaiDat;
    private javax.swing.JButton btnCapNhatTT;
    private javax.swing.JButton btnDoiMatKhau;
    private javax.swing.JButton btnFirst;
    private javax.swing.ButtonGroup btnGChucVu;
    private javax.swing.ButtonGroup btnGThanhToan;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnInHD;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLuuHD;
    private javax.swing.JButton btnLuuHDCT;
    private javax.swing.JButton btnLuuLX;
    private javax.swing.JButton btnLuuMX;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnResSetHD;
    private javax.swing.JButton btnResetDX;
    private javax.swing.JButton btnResetHX;
    private javax.swing.JButton btnResetKH;
    private javax.swing.JButton btnResetLX;
    private javax.swing.JButton btnResetMX;
    private javax.swing.JButton btnResetNhanVien;
    private javax.swing.JButton btnResetSKSM;
    private javax.swing.JButton btnResetXe;
    private javax.swing.JButton btnSuaDX;
    private javax.swing.JButton btnSuaKH;
    private javax.swing.JButton btnSuaNhanVien;
    private javax.swing.JButton btnSuaSKSM;
    private javax.swing.JButton btnSuaXe;
    private javax.swing.JButton btnThemDX;
    private javax.swing.JButton btnThemHX;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnThemNhanvien;
    private javax.swing.JButton btnThemSKSM;
    private javax.swing.JButton btnThemXe;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnXeMay;
    private javax.swing.JButton btnXoaAnhNhanVien;
    private javax.swing.JButton btnXoaDX;
    private javax.swing.JButton btnXoaHD;
    private javax.swing.JButton btnXoaHX;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.JButton btnXoaLX;
    private javax.swing.JButton btnXoaMX;
    private javax.swing.JButton btnXoaNhanVien;
    private javax.swing.JButton btnXoaSKSM;
    private javax.swing.JButton btnXoaSP;
    private javax.swing.JButton btnXoaXe;
    private javax.swing.JButton btn_doanhso;
    private javax.swing.JButton btn_in;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton cb_DSB;
    private javax.swing.JComboBox<String> cboDongXe;
    private javax.swing.JComboBox<String> cboHX;
    private javax.swing.JComboBox<String> cboLoaiXe;
    private javax.swing.JComboBox<String> cboMaXe;
    private javax.swing.JComboBox<String> cboMauXe;
    private javax.swing.JComboBox<String> cboTenHX;
    private javax.swing.JComboBox<String> cbosksm;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblAnhNVCT;
    private javax.swing.JLabel lblAnhXe;
    private javax.swing.JLabel lblDongXe;
    private javax.swing.JLabel lblDungTich;
    private javax.swing.JLabel lblDungTich1;
    private javax.swing.JLabel lblGia;
    private javax.swing.JLabel lblGia1;
    private javax.swing.JLabel lblHangXe;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JLabel lblLoaiXe;
    private javax.swing.JLabel lblMaDX;
    private javax.swing.JLabel lblMaDongXe;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblMaHD1;
    private javax.swing.JLabel lblMaHDCT;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblMaKHHD;
    private javax.swing.JLabel lblMaLX;
    private javax.swing.JLabel lblMaLoaiXe;
    private javax.swing.JLabel lblMaMX;
    private javax.swing.JLabel lblMaMauXe;
    private javax.swing.JLabel lblMaNVCT;
    private javax.swing.JLabel lblMaNVHD;
    private javax.swing.JLabel lblMaSKSM;
    private javax.swing.JLabel lblMaXe;
    private javax.swing.JLabel lblMaXe1;
    private javax.swing.JLabel lblMau;
    private static javax.swing.JLabel lblNgayTao;
    private static javax.swing.JLabel lblNgayTaoHD;
    private javax.swing.JLabel lblSKSM;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblSoLuong1;
    private javax.swing.JLabel lblSoMay1;
    private javax.swing.JLabel lblTenDong1;
    private javax.swing.JLabel lblTenHang1;
    private javax.swing.JLabel lblTenKHHD;
    private javax.swing.JLabel lblTenLoai1;
    private javax.swing.JLabel lblTenMau1;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblTenNVHD;
    private javax.swing.JLabel lblTenXe;
    private javax.swing.JLabel lblTenXe1;
    private javax.swing.JLabel lblTextMaKH;
    private javax.swing.JLabel lblTextThanhToanHD;
    private javax.swing.JLabel lblThanhTien;
    private javax.swing.JLabel lblTitleMaHD;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblVaiTroNV;
    private javax.swing.JLabel lblXe;
    private com.toedter.calendar.JDateChooser ngaybatdau;
    private com.toedter.calendar.JDateChooser ngaykethuc;
    private javax.swing.JRadioButton rdoALL;
    private javax.swing.JRadioButton rdoCTT;
    private javax.swing.JRadioButton rdoChuaThanhToan;
    private javax.swing.JRadioButton rdoDTT;
    private javax.swing.JRadioButton rdoDaThanhToan;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoQuanLy;
    private javax.swing.JTable tblDanhSachXe;
    private javax.swing.JTable tblDongXe;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblHangXe;
    private javax.swing.JTable tblHoaDonCT;
    private javax.swing.JTable tblKHHD;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblLoaiXe;
    private javax.swing.JTable tblMauXe;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblSKSM;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtBaoHanh;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextArea txtDiaChiKH;
    private javax.swing.JTextArea txtDiaChiNVCT;
    private javax.swing.JTextField txtDunhtich;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JPasswordField txtMatKhau2;
    private javax.swing.JPasswordField txtMatKhauCT;
    private javax.swing.JTextField txtSoDienThoaiCT;
    private javax.swing.JTextField txtSoKhung;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoMay;
    private javax.swing.JTextField txtSodienthoai;
    private javax.swing.JTextField txtSodienthoaiKH;
    private javax.swing.JTextField txtTenDX;
    private javax.swing.JTextField txtTenHX;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenLX;
    private javax.swing.JTextField txtTenMX;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTenNVCT;
    private javax.swing.JTextField txtTenXe;
    private javax.swing.JTextField txtTimKHHD;
    private javax.swing.JTextField txtTimKiemKH;
    private javax.swing.JTextField txtTimKiemXe;
    private javax.swing.JTextField txtTimSP;
    private javax.swing.JTextField txtTimkiemNV;
    private javax.swing.JLabel txt_den;
    private javax.swing.JLabel txt_tongtien;
    private javax.swing.JLabel txt_tu;
    // End of variables declaration//GEN-END:variables

}
