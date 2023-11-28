package xe.ui;

import java.awt.CardLayout;
import com.formdev.flatlaf.FlatLightLaf;
import com.orsonpdf.Pattern;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.print.DocFlavor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import xe.dao.DongXeDAO;
import xe.dao.HangXeDAO;
import xe.dao.KhachHangDAO;
import xe.dao.LoaiXeDAO;
import xe.dao.MauXeDAO;
import xe.dao.NhanVienDAO;
import xe.dao.SoKhungSoMayDAO;
import xe.dao.XeDAOO;
import xe.entity.DongXe;
import xe.entity.HangXe;
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
    XeDAOO daoxe = new XeDAOO();
    DongXeDAO daodx = new DongXeDAO();
    HangXeDAO daohx = new HangXeDAO();
    MauXeDAO daomx = new MauXeDAO();
    KhachHangDAO daokh = new KhachHangDAO();
    NhanVienDAO dao = new NhanVienDAO();
    LoaiXeDAO daolx = new LoaiXeDAO();
    int row = -1;
    CardLayout card;
    JFileChooser fileChooser = new JFileChooser();
    ArrayList<Xe> listXE = new ArrayList<>();

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
        this.updateStatusNhanVien();
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
        this.fillComboBoxXe();
        this.fillTableSKSMXe();
        this.updateStatusSKSMXe();
    }

    void startClock() {
//        new Thread() {
//            @Override
//            public void run() {
//                while (true) {
//                    Calendar ca = new GregorianCalendar();
//                    int gio = ca.get(Calendar.HOUR);
//                    int phut = ca.get(Calendar.MINUTE);
//                    int giay = ca.get(Calendar.SECOND);
//                    int AM_PM = ca.get(Calendar.AM_PM);
//                    String day_night;
//                    if (AM_PM == 1) {
//                        day_night = "PM";
//                    } else {
//                        day_night = "AM";
//                    }
//                    lblTime.setText(gio + ":" + phut + ":" + giay + ":" + day_night);
//                }
//            }
//        }.start();
        class TimeClock extends Thread {

            @Override
            public void run() {
                while (true) {
                    lblTime.setText(new SimpleDateFormat("hh:mm:ss a").format(Calendar.getInstance().getTime()));
                }
            }
        }
        TimeClock timeClock = new TimeClock();
        timeClock.start();
    }

    //---------------------------------------------------------thông tin người sử dụng--------------------------------
    void displayUserInfo() {
        String tenNV = Auth.user.getTenNV();
        String userID = Auth.user.getMaNV();
        String anhNV = Auth.user.getHinhAnh();
        String n = "Chưa thêm ảnh!";
        String role = Auth.user.isVaiTro() ? "Quản lý" : "Nhân viên";
        lblTenNV.setText("" + tenNV);
        if (anhNV != null) {
            lblAnh.setToolTipText(anhNV);
            ImageIcon icon = XImage.readGiaoDien(anhNV);
            lblAnh.setIcon(icon);
        }
        if (anhNV.equals(n)) {
            ImageIcon icon = XImage.readGiaoDien("AvtTrang.png");
            lblAnh.setIcon(icon);
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

    void setFormNhanVien(NhanVien nv) {
        txtMaNV.setText(nv.getMaNV());
        txtTenNV.setText(nv.getTenNV());
        txtMatKhau.setText(nv.getMatKhau());
        txtMatKhau2.setText(nv.getMatKhau());
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
    }

    void updateStatusNhanVien() {
        boolean edit = (this.row >= 0);
//        boolean first = (this.row == 0);
//        boolean last = (this.row == tblEmployee.getRowCount() - 1);
        //Form state
        txtMaNV.setEnabled(!edit);
        txtMaNV.setEditable(!edit);
        btnThemNhanvien.setEnabled(!edit);
        btnSuaNhanVien.setEnabled(edit);
        btnXoaNhanVien.setEnabled(edit);
        //Directional state
//        btnFirst.setEnabled(edit && !first);
//        btnPrev.setEnabled(edit && !first);
//        btnNext.setEnabled(edit && !last);
//        btnLast.setEnabled(edit && !last);
    }

    void editNhanVien() {
        String manv = (String) tblNhanVien.getValueAt(this.row, 0);
        NhanVien nv = dao.selectById(manv);
        this.setFormNhanVien(nv);
        NhanVienJTabbedPane.setSelectedIndex(0);
        this.updateStatusNhanVien();
    }

    void clearFormNhanVien() {
        NhanVien nv = new NhanVien();
        this.setFormNhanVien(nv);
        lblHinhAnh.setIcon(null);
        lblHinhAnh.setToolTipText("Chưa thêm ảnh!");
        txtSodienthoai.setText("0");
        this.row = -1;
        this.updateStatusNhanVien();
        btnGChucVu.clearSelection();
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
        String mk2 = new String(txtMatKhau2.getPassword());
        if (txtMaNV.getText().isBlank() || txtMatKhau.getText().isBlank() || txtMatKhau2.getText().isBlank() || txtTenNV.getText().isBlank() || btnGChucVu.isSelected(null)
                || txtSodienthoai.getText().isBlank()) {
            MsgBox.alert(this, "Không để trống!");
            return;
        } else if (txtSodienthoai.getText().length() > 10 || txtSodienthoai.getText().length() < 10) {
            MsgBox.alert(this, "Số điện thoại không hợp lệ!");
            return;
        } else {
            if (!mk2.equals(nv.getMatKhau())) {
                MsgBox.alert(this, "Mật khẩu nhập lại không chính xác!");
            } else {
                try {
                    dao.update(nv);
                    this.fillTableNhanVien();
                    MsgBox.alert(this, "Sửa thành công");
                } catch (Exception e) {
                    MsgBox.alert(this, "Sửa thất bại!");
                }
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
        KhachHang kh = new KhachHang();
        kh.setMaKH(txtMaKH.getText());
        kh.setTenKH(txtTenKH.getText());
        kh.setSodienthoai(txtSodienthoaiKH.getText());
        kh.setDiachi(txtDiaChiKH.getText());
        lblNgayTao.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
        return kh;
    }

    void setFormKhachHang(KhachHang kh) {
        txtMaKH.setText(kh.getMaKH());
        txtTenKH.setText(kh.getTenKH());
        txtSodienthoaiKH.setText(kh.getSodienthoai());
        lblNgayTao.setText(XDate.toString(kh.getNgaytao(), "dd-MM-yyyy"));
        txtDiaChiKH.setText(kh.getDiachi());
    }

    void clearFormKhachHang() {
        //txtCreator.setText("");
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtSodienthoaiKH.setText("");
        txtDiaChiKH.setText("");
        lblNgayTao.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
        this.row = -1;
        this.updateStatusKhachHang();
    }

    void updateStatusKhachHang() {
        boolean edit = (this.row >= 0);
        txtMaKH.setEditable(!edit);
        txtMaKH.setEnabled(!edit);
        //txtNgayTao.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
        btnThemKH.setEnabled(!edit);
        btnSuaKH.setEnabled(edit);
        btnXoaKH.setEnabled(edit);
    }

    void editKhachHang() {
        String makh = (String) tblKhachHang.getValueAt(this.row, 0);
        KhachHang kh = daokh.selectById(makh);
        this.setFormKhachHang(kh);
        KhachHangJTabbedPane.setSelectedIndex(0);
        this.updateStatusKhachHang();
    }

    void fillTableKhachHang() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        try {
            List<KhachHang> list = daokh.selectAll();
            for (KhachHang kh : list) {
                if (kh.getDiachi().isBlank()) {
                    Object[] row = {kh.getMaKH(), kh.getTenKH(), kh.getSodienthoai(),
                        XDate.toString(kh.getNgaytao(), "dd-MM-yyyy"), kh.getDiachi() + "Chưa thêm địa chỉ"};
                    model.addRow(row);
                } else {
                    Object[] row = {kh.getMaKH(), kh.getTenKH(), kh.getSodienthoai(),
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
        if (txtMaKH.getText().isBlank() || txtTenKH.getText().isBlank() || txtSodienthoaiKH.getText().isBlank() || lblNgayTao.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else if (txtSodienthoaiKH.getText().length() > 10 || txtSodienthoaiKH.getText().length() < 10) {
            MsgBox.alert(this, "Số điện thoại không hợp lệ!");
            return;
        } else {
            for (KhachHang kh1 : list) {
                if (kh1.getMaKH().equalsIgnoreCase(txtMaKH.getText())) {
                    MsgBox.alert(this, "Mã " + txtMaKH.getText() + " đã tồn tại!");
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
        if (txtMaKH.getText().isBlank() || txtTenKH.getText().isBlank() || txtSodienthoaiKH.getText().isBlank() || lblNgayTao.getText().isBlank()) {
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
            String makh = txtMaKH.getText();
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
        lblMaLX.setText(lx.getMaLX()+"");
        txtTenLX.setText(lx.getTenLX());
    }

    void clearFormLoaiXe() {
        lblMaLoaiXe.setText("");
        lblMaLX.setText("");
        txtTenLX.setText("");
        this.row = -1;
        this.updateStatusLoaiXe();
    }

    void updateStatusLoaiXe() {
        boolean edit = (this.row >= 0);
        btnThemLX.setEnabled(!edit);
        btnSuaLX.setEnabled(edit);
        btnXoaLX.setEnabled(edit);
    }

    void editLoaiXe() {
        Integer malx = (Integer) tblLoaiXe.getValueAt(this.row, 0);
        LoaiXe lx = daolx.selectById(malx.toString());
        this.setFormLoaiXe(lx);
        this.updateStatusLoaiXe();
    }

    void fillTableLoaiXe() {
        DefaultTableModel model = (DefaultTableModel) tblLoaiXe.getModel();
        model.setRowCount(0);
        try {
            List<LoaiXe> list = daolx.selectAll();
            for (LoaiXe lx : list) {
                Object[] row = {lx.getMaLX(), lx.getTenLX()};
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void insertLoaiXe() {
        List<LoaiXe> list = daolx.selectAll();
        LoaiXe lx = getFormTenLoaiXe();
        if (txtTenLX.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            for (LoaiXe lx1 : list) {
                if (lx1.getTenLX().equalsIgnoreCase(txtTenLX.getText())) {
                    MsgBox.alert(this, "Tên " + txtTenLX.getText() + " đã tồn tại!");
                    return;
                }
            }
            try {
                daolx.insert(lx);
                this.fillTableLoaiXe();
                this.clearFormLoaiXe();
                this.fillComboBoxLoaiXe();
                this.fillTableXe();
                MsgBox.alert(this, "Thêm thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm thất bại!");
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
                MsgBox.alert(this, "Sửa thành công");
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
                    MsgBox.alert(this, "Xóa thành công!");
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
    }

    void clearFormMauXe() {
        lblMaMauXe.setText("");
        lblMaMX.setText("");
        txtTenMX.setText("");
        this.row = -1;
        this.updateStatusMauXe();
    }

    void updateStatusMauXe() {
        boolean edit = (this.row >= 0);
        btnThemMX.setEnabled(!edit);
        btnSuaMX.setEnabled(edit);
        btnXoaMX.setEnabled(edit);
    }

    void editMauXe() {
        Integer mamx = (Integer) tblMauXe.getValueAt(this.row, 0);
        MauXe mx = daomx.selectById(mamx.toString());
        this.setFormMauXe(mx);
        this.updateStatusMauXe();
    }

    void fillTableMauXe() {
        DefaultTableModel model = (DefaultTableModel) tblMauXe.getModel();
        model.setRowCount(0);
        try {
            List<MauXe> list = daomx.selectAll();
            for (MauXe mx : list) {
                Object[] row = {mx.getMaMX(), mx.getTenMX()};
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
            try {
                daomx.insert(mx);
                this.fillTableMauXe();
                this.clearFormMauXe();
                this.fillComboBoxMauXe();
                this.fillTableXe();
                MsgBox.alert(this, "Thêm thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm thất bại!");
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
                MsgBox.alert(this, "Sửa thành công");
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
                    MsgBox.alert(this, "Xóa thành công!");
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
    }

    void updateStatusHangXe() {
        boolean edit = (this.row >= 0);
        btnThemHX.setEnabled(!edit);
        btnXoaHX.setEnabled(edit);
    }

    void editHangXe() {
        String tenhx = (String) tblHangXe.getValueAt(this.row, 0);
        HangXe hx = daohx.selectById(tenhx);
        this.setFormHangXe(hx);
        this.updateStatusHangXe();
    }

    void fillTableHangXe() {
        DefaultTableModel model = (DefaultTableModel) tblHangXe.getModel();
        model.setRowCount(0);
        try {
            List<HangXe> list = daohx.selectAll();
            for (HangXe hx : list) {
                Object[] row = {hx.getTenHX()};
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
        List<Xe> list = daoxe.selectAll();
        List<DongXe> list1 = daodx.selectAll();
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không được phép xóa!");
        } else {
            String mahx = txtTenHX.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa hãng xe này?\nTên hãng: " + txtTenHX.getText())) {
                try {
                    for (Xe hx1 : list) {
                        if (hx1.getTenHX().equalsIgnoreCase(txtTenHX.getText())) {
                            if (MsgBox.confirm(this, "Chú ý!\n"
                                    + "Hãng xe[" + txtTenHX.getText() + "] đã có dữ liệu "
                                    + "nếu xóa sẽ xóa toàn bộ dữ liệu của hãng này!\n"
                                    + "Đồng ý xóa?")) {
                                daohx.deleteAll(mahx);
                                this.clearFormDongXe();
                                this.clearFormHangXe();
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
                    daohx.delete(mahx);
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
        dx.setBaohanh(Integer.parseInt(txtBaoHanh.getText()));
        return dx;
    }

    void setFormDongXe(DongXe dx) {
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
    }

    void updateStatusDongXe() {
        boolean edit = (this.row >= 0);
        btnThemDX.setEnabled(!edit);
        btnSuaDX.setEnabled(edit);
        btnXoaDX.setEnabled(edit);
    }

    void editDongXe() {
        Integer tendx = (Integer) tblDongXe.getValueAt(this.row, 0);
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
        try {
            List<DongXe> list = daodx.selectAll();
            for (DongXe dx : list) {
                Object[] row = {dx.getMaDX(), dx.getTenDX(), dx.getTenHX(), dx.getBaohanh()};
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
        if (txtTenDX.getText().isBlank()) {
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
        if (txtTenDX.getText().isBlank()) {
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
        List<Xe> list = daoxe.selectSQLXe();
        String tenhx = (String) cboTenHX.getSelectedItem();
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không được phép xóa!");
        } else {
            String madx = lblMaDX.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa dòng xe này?\nMã dòng: "
                    + lblMaDX.getText() + "\nTên dòng: " + txtTenDX.getText() + "\nTên hãng: " + tenhx)) {
                try {
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
        try {
            List<Xe> list = daoxe.selectAll();
            for (Xe x : list) {
                Object[] row = {x.getMaXe(), x.getTenXe(), x.getTenHX(), x.getMaDX(), x.getMaLX(), x.getMaMX(), x.getDungtich(), x.getGia(), x.getSoluong()};
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
//                else if (model2.getSelectedItem()==null) {
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
        x.setGia(Integer.parseInt(txtGia.getText()));
        x.setSoluong(Integer.parseInt(txtSoLuong.getText()));
        x.setHinh_anh(lblAnhXe.getToolTipText());
        return x;
    }

    //get form để insert
    private Xe getFormTenXe() {
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
        Xe x = new Xe();
        //khóa chính tự sinh nên k cần phải thêm;
        x.setTenXe(txtTenXe.getText());
        x.setTenHX(cboHX.getSelectedItem().toString());
        x.setMaDX(cboDongXe.getToolTipText());
        x.setMaMX(cboMauXe.getToolTipText());
        x.setMaLX(cboLoaiXe.getToolTipText());
        x.setDungtich(txtDunhtich.getText());
        x.setGia(Integer.parseInt(txtGia.getText()));
        x.setSoluong(Integer.parseInt(txtSoLuong.getText()));
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
        txtGia.setText(x.getGia() + "");
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
        cboDongXe.setSelectedIndex(0);
        cboMauXe.setSelectedIndex(0);
        cboLoaiXe.setSelectedIndex(0);
        txtDunhtich.setText("");
        txtGia.setText("");
        lblAnhXe.setIcon(null);
        lblAnhXe.setToolTipText(null);
        txtSoLuong.setText("");
        this.row = -1;
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
        Integer tenxe = (Integer) tblDanhSachXe.getValueAt(this.row, 0);
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
                cboMaXe.addItem(xe.getMaXe() + "");
                //model1.addElement(xe.getMaXe());
                txtSoKhung.setEditable(true);
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
            model1.addElement("Chưa có xe không thể thêm !");
            cboMaXe.setEditable(false);
            cboMaXe.setEnabled(false);
            cboMaXe.setBackground(Color.red);
            txtSoKhung.setEnabled(false);
            txtSoMay.setEnabled(false);
            btnThemSKSM.setEnabled(false);
            btnSuaSKSM.setEnabled(false);
            btnXoaSKSM.setEnabled(false);
            btnResetSKSM.setEnabled(false);
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
        lblGia.setText(x.getGia() + "");
        lblSoLuong.setText(x.getSoluong() + "");
    }

    void fillTableSKSMXe() {
        DefaultTableModel model = (DefaultTableModel) tblSKSM.getModel();
        model.setRowCount(0);
        int maxe = cboMaXe.getSelectedIndex();
        if (maxe > -1) {
            String a = cboMaXe.getItemAt(maxe);
            cboMaXe.setToolTipText(a);
            try {
                List<SoKhungSoMay> list = daosksm.selectAll();
                for (SoKhungSoMay x : list) {
                    if (cboMaXe.getToolTipText().equalsIgnoreCase(x.getMaXe() + "")) {
                        Object[] row = {x.getMaSKSM(), x.getMaXe(), x.getSokhung(), x.getSomay()};
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
            cboMaXe.setToolTipText(a);
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
            cboMaXe.setToolTipText(a);
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
        Integer tenxe = (Integer) tblSKSM.getValueAt(this.row, 0);
        SoKhungSoMay sksm = daosksm.selectById(tenxe.toString());
        this.setFormSKSMXe(sksm);
        this.updateStatusSKSMXe();
    }

    void insertSKSMXe() {
        SoKhungSoMay dx = getFormSKSMXe();
        List<SoKhungSoMay> list1 = daosksm.selectAll();
        if (txtSoKhung.getText().isBlank() || txtSoMay.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            for (SoKhungSoMay x : list1) {
                if (x.getSokhung().equalsIgnoreCase(txtSoKhung.getText()) && x.getSomay().equalsIgnoreCase(txtSoMay.getText())) {
                    MsgBox.alert(this, "Thông tin số khung số máy này đã tồn tại!");
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
        jSplitPane1 = new javax.swing.JSplitPane();
        MeNuJPanel = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        btnNhanVien = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        btnKhachHang = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnCaiDat = new javax.swing.JButton();
        lblAnh = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        JPanelCard = new javax.swing.JPanel();
        JPanelHome = new javax.swing.JPanel();
        lblTime = new javax.swing.JLabel();
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
        jLabel17 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
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
        btnThemLX = new javax.swing.JButton();
        btnSuaLX = new javax.swing.JButton();
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
        btnThemMX = new javax.swing.JButton();
        btnSuaMX = new javax.swing.JButton();
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
        jLabel61 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        lblTenXe = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        lblLoaiXe = new javax.swing.JLabel();
        lblHangXe = new javax.swing.JLabel();
        lblSoLuong = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        lblDongXe = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        JPanelHoaDon = new javax.swing.JPanel();
        HoaDonJTabbedPane = new javax.swing.JTabbedPane();
        HoaDonJPanel = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        HoaDonJTable = new javax.swing.JTable();
        jLabel45 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        lblMaKHHD = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        lblTenKHHD = new javax.swing.JLabel();
        lblMaNVHD = new javax.swing.JLabel();
        lblTenNVHD = new javax.swing.JLabel();
        lblNgayTaoHD = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Xe");

        jSplitPane1.setDividerSize(0);

        MeNuJPanel.setBackground(new java.awt.Color(51, 51, 51));

        btnHome.setBackground(new java.awt.Color(51, 51, 51));
        btnHome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnHome.setText("Home");
        btnHome.setAlignmentY(0.0F);
        btnHome.setBorder(null);
        btnHome.setBorderPainted(false);
        btnHome.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnHome.setFocusPainted(false);
        btnHome.setMargin(new java.awt.Insets(0, 14, 3, 14));
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

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

        jButton5.setBackground(new java.awt.Color(51, 51, 51));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Xe máy");
        jButton5.setBorder(null);
        jButton5.setBorderPainted(false);
        jButton5.setFocusPainted(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
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

        jButton9.setBackground(new java.awt.Color(51, 51, 51));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Hóa đơn");
        jButton9.setBorder(null);
        jButton9.setBorderPainted(false);
        jButton9.setFocusPainted(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(51, 51, 51));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Thống kê");
        jButton6.setBorder(null);
        jButton6.setBorderPainted(false);
        jButton6.setFocusPainted(false);

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("About you");
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setFocusPainted(false);

        btnCaiDat.setBackground(new java.awt.Color(51, 51, 51));
        btnCaiDat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCaiDat.setForeground(new java.awt.Color(255, 255, 255));
        btnCaiDat.setText("Cài đặt");
        btnCaiDat.setBorder(null);
        btnCaiDat.setBorderPainted(false);
        btnCaiDat.setFocusPainted(false);

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

        javax.swing.GroupLayout MeNuJPanelLayout = new javax.swing.GroupLayout(MeNuJPanel);
        MeNuJPanel.setLayout(MeNuJPanelLayout);
        MeNuJPanelLayout.setHorizontalGroup(
            MeNuJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCaiDat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblTenNV, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(MeNuJPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MeNuJPanelLayout.setVerticalGroup(
            MeNuJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MeNuJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenNV)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(btnNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(btnKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(btnCaiDat, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        jSplitPane1.setLeftComponent(MeNuJPanel);

        JPanelCard.setLayout(new java.awt.CardLayout());

        JPanelHome.setBackground(new java.awt.Color(255, 255, 255));

        lblTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/xe/icon/Clock.png"))); // NOI18N

        javax.swing.GroupLayout JPanelHomeLayout = new javax.swing.GroupLayout(JPanelHome);
        JPanelHome.setLayout(JPanelHomeLayout);
        JPanelHomeLayout.setHorizontalGroup(
            JPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanelHomeLayout.createSequentialGroup()
                .addGap(0, 716, Short.MAX_VALUE)
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        JPanelHomeLayout.setVerticalGroup(
            JPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanelHomeLayout.createSequentialGroup()
                .addContainerGap(505, Short.MAX_VALUE)
                .addComponent(lblTime))
        );

        JPanelCard.add(JPanelHome, "JPanelHome");

        JPanelNhanVien.setBackground(new java.awt.Color(255, 255, 255));

        NhanVienJTabbedPane.setBackground(new java.awt.Color(255, 255, 255));

        CapNhatJPanel.setBackground(new java.awt.Color(204, 204, 204));

        btnSuaNhanVien.setText("Sửa");
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
                .addComponent(jLabel13))
            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addComponent(btnThemNhanvien)
                .addGap(18, 18, 18)
                .addComponent(btnSuaNhanVien)
                .addGap(18, 18, 18)
                .addComponent(btnXoaNhanVien)
                .addGap(18, 18, 18)
                .addComponent(btnResetNhanVien))
            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
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
                                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))))
                        .addGap(13, 13, 13)
                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV)
                            .addComponent(txtTenNV)
                            .addComponent(txtSodienthoai)
                            .addComponent(txtMatKhau)
                            .addComponent(txtMatKhau2)
                            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                .addComponent(rdoQuanLy)
                                .addGap(13, 13, 13)
                                .addComponent(rdoNhanVien))))
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnXoaAnhNhanVien)
                        .addGap(158, 158, 158)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)))
                .addGap(183, 183, 183))
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
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
                .addGap(14, 14, 14)
                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemNhanvien)
                    .addComponent(btnSuaNhanVien)
                    .addComponent(btnXoaNhanVien)
                    .addComponent(btnResetNhanVien))
                .addGap(21, 21, 21))
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
        );
        DanhSachJPanelLayout.setVerticalGroup(
            DanhSachJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DanhSachJPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(DanhSachJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtTimkiemNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE))
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

        jLabel17.setText("Mã khách hàng");

        jLabel18.setText("Tên khách hàng");

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
                .addGap(110, 110, 110)
                .addComponent(jLabel17)
                .addGap(28, 28, 28)
                .addComponent(txtMaKH)
                .addGap(148, 148, 148))
            .addGroup(CapNhatKhachHangJPanelLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jLabel18)
                .addGap(26, 26, 26)
                .addComponent(txtTenKH)
                .addGap(148, 148, 148))
            .addGroup(CapNhatKhachHangJPanelLayout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(btnThemKH, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(btnSuaKH, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(btnXoaKH, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(btnResetKH, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addGap(143, 143, 143))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CapNhatKhachHangJPanelLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CapNhatKhachHangJPanelLayout.createSequentialGroup()
                        .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel20))
                        .addGap(62, 62, 62)
                        .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addComponent(lblNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(CapNhatKhachHangJPanelLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(41, 41, 41)
                        .addComponent(txtSodienthoaiKH)))
                .addGap(148, 148, 148))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CapNhatKhachHangJPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addGap(259, 259, 259))
        );
        CapNhatKhachHangJPanelLayout.setVerticalGroup(
            CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhatKhachHangJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addGap(45, 45, 45)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(txtSodienthoaiKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(CapNhatKhachHangJPanelLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(24, 24, 24))
                    .addGroup(CapNhatKhachHangJPanelLayout.createSequentialGroup()
                        .addComponent(lblNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)))
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
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
                "Ma KH", "Tên KH", "Số điện thoại", "Ngày tạo", "Địa chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
            .addGroup(DanhSachKHJPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiemKH, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
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
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE))
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
                "Mã loại xe", "Tên loại xe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
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

        btnThemLX.setText("Thêm");
        btnThemLX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLXActionPerformed(evt);
            }
        });

        btnSuaLX.setText("Sửa");
        btnSuaLX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaLXActionPerformed(evt);
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
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
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
                        .addComponent(btnThemLX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSuaLX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaLX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetLX)
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addGap(72, 72, 72))
        );
        LoaiXeJPanelLayout.setVerticalGroup(
            LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
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
                    .addComponent(btnThemLX)
                    .addComponent(btnSuaLX)
                    .addComponent(btnXoaLX)
                    .addComponent(btnResetLX))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        XeMayJTabbedPane.addTab("Quản Lý Loại Xe", LoaiXeJPanel);

        tblMauXe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã màu xe", "Tên màu xe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
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

        btnThemMX.setText("Thêm");
        btnThemMX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMXActionPerformed(evt);
            }
        });

        btnSuaMX.setText("Sửa");
        btnSuaMX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaMXActionPerformed(evt);
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
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
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
                        .addComponent(btnThemMX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSuaMX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaMX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetMX)
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addGap(72, 72, 72))
        );
        MauXeJPanelLayout.setVerticalGroup(
            MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
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
                    .addComponent(btnThemMX)
                    .addComponent(btnSuaMX)
                    .addComponent(btnXoaMX)
                    .addComponent(btnResetMX))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        XeMayJTabbedPane.addTab("Quản Lý Màu Xe", MauXeJPanel);

        tblHangXe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên hãng xe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
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
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
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
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
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
                "Mã dòng xe", "Tên dòng xe", "Tên hãng xe", "Bảo hành"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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

        javax.swing.GroupLayout DongXeJPanelLayout = new javax.swing.GroupLayout(DongXeJPanel);
        DongXeJPanel.setLayout(DongXeJPanelLayout);
        DongXeJPanelLayout.setHorizontalGroup(
            DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DongXeJPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DongXeJPanelLayout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DongXeJPanelLayout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(btnThemDX)
                        .addGap(6, 6, 6)
                        .addComponent(btnSuaDX)
                        .addGap(6, 6, 6)
                        .addComponent(btnXoaDX)
                        .addGap(6, 6, 6)
                        .addComponent(btnResetDX))
                    .addGroup(DongXeJPanelLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
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
                .addContainerGap(78, Short.MAX_VALUE))
        );
        DongXeJPanelLayout.setVerticalGroup(
            DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
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
                "Mã xe", "Tên xe", "Hãng", "Dòng", "Loại", "Màu", "Dung tích", "Giá", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
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
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        XeMayJTabbedPane.addTab("Danh Sách Xe", DanhSachXeJPanel);

        tblSKSM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SKSM", "Mã xe", "Số khung", "Số máy"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        jPanel1.add(lblGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 250, 16));

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

        jLabel61.setText("Giá");
        jPanel1.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

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

        HoaDonJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã khách hàng", "Tên khách hàng", "Mã nhân viên", "Tên nhân viên", "Trạng thái", "Ngày tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        HoaDonJTable.setFocusable(false);
        HoaDonJTable.setShowGrid(false);
        jScrollPane10.setViewportView(HoaDonJTable);

        jLabel45.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("TẠO HÓA ĐƠN");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel46.setText("Mã hóa đơn");

        jLabel47.setText("Mã khách hàng");

        jLabel48.setText("Tên khách hàng");

        jLabel49.setText("Mã nhân viên");

        jLabel50.setText("Tên nhân viên");

        jLabel51.setText("Ngày tạo");

        jLabel6.setText("Trạng thái");

        jButton1.setText("Thêm");

        jButton2.setText("Sửa");

        jButton4.setText("Xóa");

        jButton7.setText("Reset");

        btnGThanhToan.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(0, 204, 0));
        jRadioButton1.setText("Đã thanh toán");

        btnGThanhToan.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(204, 0, 0));
        jRadioButton2.setText("Chưa thanh toán");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(32, 32, 32)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51)
                            .addComponent(jLabel47)
                            .addComponent(jLabel46)
                            .addComponent(jLabel48)
                            .addComponent(jLabel49))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMaKHHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTenKHHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMaNVHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTenNVHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNgayTaoHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaKHHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTenKHHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaNVHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTenNVHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNgayTaoHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton4)
                    .addComponent(jButton7))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã KH", "Tên KH", "SĐT", "Ngày tạo", "Địa chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane12.setViewportView(jTable1);

        jLabel9.setText("Tìm");

        jLabel64.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("KHÁCH HÀNG");

        javax.swing.GroupLayout HoaDonJPanelLayout = new javax.swing.GroupLayout(HoaDonJPanel);
        HoaDonJPanel.setLayout(HoaDonJPanelLayout);
        HoaDonJPanelLayout.setHorizontalGroup(
            HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                                .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                            .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1)
                                .addGap(14, 14, 14))))
                    .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        HoaDonJPanelLayout.setVerticalGroup(
            HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        HoaDonJTabbedPane.addTab("Tạo Hóa Đơn", HoaDonJPanel);

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
    }//GEN-LAST:event_btnNhanVienActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        card.show(JPanelCard, "JPanelHome");
    }//GEN-LAST:event_btnHomeActionPerformed

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        // TODO add your handling code here:
        //choosePicture();
    }//GEN-LAST:event_lblAnhMouseClicked

    private void lblHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMouseClicked
        // TODO add your handling code here:
        choosePicture();
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
    }//GEN-LAST:event_txtSodienthoaiKHKeyReleased

    private void btnSuaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKHActionPerformed
        // TODO add your handling code here:
        this.updateKhachHang();
    }//GEN-LAST:event_btnSuaKHActionPerformed

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
        // TODO add your handling code here:
        this.deleteKhachHang();
    }//GEN-LAST:event_btnXoaKHActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        card.show(JPanelCard, "JPanelXeMay");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tblLoaiXeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiXeMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tblLoaiXe.getSelectedRow();
            if (this.row >= 0) {
                this.editLoaiXe();
            }
        }
    }//GEN-LAST:event_tblLoaiXeMouseClicked

    private void btnResetLXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetLXActionPerformed
        // TODO add your handling code here:
        this.clearFormLoaiXe();
    }//GEN-LAST:event_btnResetLXActionPerformed

    private void btnThemLXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLXActionPerformed
        // TODO add your handling code here:
        this.insertLoaiXe();
    }//GEN-LAST:event_btnThemLXActionPerformed

    private void btnSuaLXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaLXActionPerformed
        // TODO add your handling code here:
        this.updateLoaiXe();
    }//GEN-LAST:event_btnSuaLXActionPerformed

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
            }
        }
    }//GEN-LAST:event_tblMauXeMouseClicked

    private void btnThemMXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMXActionPerformed
        // TODO add your handling code here:
        this.insertMauXe();
    }//GEN-LAST:event_btnThemMXActionPerformed

    private void btnSuaMXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaMXActionPerformed
        // TODO add your handling code here:
        this.updateMauXe();
    }//GEN-LAST:event_btnSuaMXActionPerformed

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

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        card.show(JPanelCard, "JPanelHoaDon");
    }//GEN-LAST:event_jButton9ActionPerformed

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
        if (maxe > -1) {
            String a = cboMaXe.getItemAt(maxe);
            Xe xe = daoxe.selectById(a);
            this.setFormSKSMXe(xe);
            //System.out.println(a);
        }
        this.fillTableSKSMXe();
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
    private javax.swing.JTable HoaDonJTable;
    private javax.swing.JPanel JPanelCard;
    private javax.swing.JPanel JPanelHoaDon;
    private javax.swing.JPanel JPanelHome;
    private javax.swing.JPanel JPanelKhachHang;
    private javax.swing.JPanel JPanelNhanVien;
    private javax.swing.JPanel JPanelXeMay;
    private javax.swing.JTabbedPane KhachHangJTabbedPane;
    private javax.swing.JPanel LoaiXeJPanel;
    private javax.swing.JPanel MauXeJPanel;
    private javax.swing.JPanel MeNuJPanel;
    private javax.swing.JTabbedPane NhanVienJTabbedPane;
    private javax.swing.JPanel QLXe;
    private javax.swing.JPanel SKSMJPanel;
    private javax.swing.JTabbedPane XeMayJTabbedPane;
    private javax.swing.JButton btnCaiDat;
    private javax.swing.JButton btnFirst;
    private javax.swing.ButtonGroup btnGChucVu;
    private javax.swing.ButtonGroup btnGThanhToan;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnPrev;
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
    private javax.swing.JButton btnSuaLX;
    private javax.swing.JButton btnSuaMX;
    private javax.swing.JButton btnSuaNhanVien;
    private javax.swing.JButton btnSuaSKSM;
    private javax.swing.JButton btnSuaXe;
    private javax.swing.JButton btnThemDX;
    private javax.swing.JButton btnThemHX;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnThemLX;
    private javax.swing.JButton btnThemMX;
    private javax.swing.JButton btnThemNhanvien;
    private javax.swing.JButton btnThemSKSM;
    private javax.swing.JButton btnThemXe;
    private javax.swing.JButton btnXoaAnhNhanVien;
    private javax.swing.JButton btnXoaDX;
    private javax.swing.JButton btnXoaHX;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.JButton btnXoaLX;
    private javax.swing.JButton btnXoaMX;
    private javax.swing.JButton btnXoaNhanVien;
    private javax.swing.JButton btnXoaSKSM;
    private javax.swing.JButton btnXoaXe;
    private javax.swing.JComboBox<String> cboDongXe;
    private javax.swing.JComboBox<String> cboHX;
    private javax.swing.JComboBox<String> cboLoaiXe;
    private javax.swing.JComboBox<String> cboMaXe;
    private javax.swing.JComboBox<String> cboMauXe;
    private javax.swing.JComboBox<String> cboTenHX;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
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
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblAnhXe;
    private javax.swing.JLabel lblDongXe;
    private javax.swing.JLabel lblDungTich;
    private javax.swing.JLabel lblGia;
    private javax.swing.JLabel lblHangXe;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JLabel lblLoaiXe;
    private javax.swing.JLabel lblMaDX;
    private javax.swing.JLabel lblMaDongXe;
    private javax.swing.JLabel lblMaKHHD;
    private javax.swing.JLabel lblMaLX;
    private javax.swing.JLabel lblMaLoaiXe;
    private javax.swing.JLabel lblMaMX;
    private javax.swing.JLabel lblMaMauXe;
    private javax.swing.JLabel lblMaNVHD;
    private javax.swing.JLabel lblMaSKSM;
    private javax.swing.JLabel lblMaXe;
    private javax.swing.JLabel lblMau;
    private static javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblNgayTaoHD;
    private javax.swing.JLabel lblSKSM;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenKHHD;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblTenNVHD;
    private javax.swing.JLabel lblTenXe;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblXe;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoQuanLy;
    private javax.swing.JTable tblDanhSachXe;
    private javax.swing.JTable tblDongXe;
    private javax.swing.JTable tblHangXe;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblLoaiXe;
    private javax.swing.JTable tblMauXe;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblSKSM;
    private javax.swing.JTextField txtBaoHanh;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextArea txtDiaChiKH;
    private javax.swing.JTextField txtDunhtich;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JPasswordField txtMatKhau2;
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
    private javax.swing.JTextField txtTenXe;
    private javax.swing.JTextField txtTimKiemKH;
    private javax.swing.JTextField txtTimKiemXe;
    private javax.swing.JTextField txtTimkiemNV;
    // End of variables declaration//GEN-END:variables

}
