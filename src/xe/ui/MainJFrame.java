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
import xe.dao.XeDAOO;
import xe.entity.DongXe;
import xe.entity.HangXe;
import xe.entity.KhachHang;
import xe.entity.LoaiXe;
import xe.entity.MauXe;
import xe.entity.NhanVien;
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

    private KhachHang getFormKhachHang() {
        KhachHang kh = new KhachHang();
        kh.setMaKH(txtMaKH.getText());
        kh.setTenKH(txtTenKH.getText());
        kh.setSodienthoai(txtSodienthoaiKH.getText());
        kh.setDiachi(txtDiaChiKH.getText());
        txtNgayTao.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
        return kh;
    }

    void setFormKhachHang(KhachHang kh) {
        txtMaKH.setText(kh.getMaKH());
        txtTenKH.setText(kh.getTenKH());
        txtSodienthoaiKH.setText(kh.getSodienthoai());
        txtNgayTao.setText(XDate.toString(kh.getNgaytao(), "dd-MM-yyyy"));
        txtDiaChiKH.setText(kh.getDiachi());
    }

    void clearFormKhachHang() {
        //txtCreator.setText("");
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtSodienthoaiKH.setText("0");
        txtDiaChiKH.setText("");
        txtNgayTao.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
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
        if (txtMaKH.getText().isBlank() || txtTenKH.getText().isBlank() || txtSodienthoaiKH.getText().isBlank() || txtNgayTao.getText().isBlank()) {
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
        if (txtMaKH.getText().isBlank() || txtTenKH.getText().isBlank() || txtSodienthoaiKH.getText().isBlank() || txtNgayTao.getText().isBlank()) {
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

    private LoaiXe getFormLoaiXe() {
        LoaiXe lx = new LoaiXe();
        lx.setMaLX(txtMaLX.getText());
        lx.setTenLX(txtTenLX.getText());
        return lx;
    }

    void setFormLoaiXe(LoaiXe lx) {
        txtMaLX.setText(lx.getMaLX());
        txtTenLX.setText(lx.getTenLX());
    }

    void clearFormLoaiXe() {
        //txtCreator.setText("");
        txtMaLX.setText("");
        txtTenLX.setText("");
        this.row = -1;
        this.updateStatusLoaiXe();
    }

    void updateStatusLoaiXe() {
        boolean edit = (this.row >= 0);
        txtMaLX.setEditable(!edit);
        txtMaLX.setEnabled(!edit);
        btnThemLX.setEnabled(!edit);
        btnSuaLX.setEnabled(edit);
        btnXoaLX.setEnabled(edit);
    }

    void editLoaiXe() {
        String malx = (String) tblLoaiXe.getValueAt(this.row, 0);
        LoaiXe lx = daolx.selectById(malx);
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
        LoaiXe lx = getFormLoaiXe();
        if (txtMaLX.getText().isBlank() || txtTenLX.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            for (LoaiXe lx1 : list) {
                if (lx1.getMaLX().equalsIgnoreCase(txtMaLX.getText())) {
                    MsgBox.alert(this, "Mã " + txtMaLX.getText() + " đã tồn tại!");
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
        if (txtMaLX.getText().isBlank() || txtTenLX.getText().isBlank()) {
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
            String malx = txtMaLX.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa loại xe này?\nMã loại xe: "
                    + txtMaLX.getText() + "\nTên loại xe: "
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

    private MauXe getFormMauXe() {
        MauXe mx = new MauXe();
        mx.setMaMX(txtMaMX.getText());
        mx.setTenMX(txtTenMX.getText());
        return mx;
    }

    void setFormMauXe(MauXe mx) {
        txtMaMX.setText(mx.getMaMX());
        txtTenMX.setText(mx.getTenMX());
    }

    void clearFormMauXe() {
        //txtCreator.setText("");
        txtMaMX.setText("");
        txtTenMX.setText("");
        this.row = -1;
        this.updateStatusMauXe();
    }

    void updateStatusMauXe() {
        boolean edit = (this.row >= 0);
        txtMaMX.setEditable(!edit);
        txtMaMX.setEnabled(!edit);
        btnThemMX.setEnabled(!edit);
        btnSuaMX.setEnabled(edit);
        btnXoaMX.setEnabled(edit);
    }

    void editMauXe() {
        String mamx = (String) tblMauXe.getValueAt(this.row, 0);
        MauXe mx = daomx.selectById(mamx);
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
        MauXe mx = getFormMauXe();
        if (txtMaMX.getText().isBlank() || txtTenMX.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            for (MauXe mx1 : list) {
                if (mx1.getMaMX().equalsIgnoreCase(txtMaMX.getText())) {
                    MsgBox.alert(this, "Mã " + txtMaMX.getText() + " đã tồn tại!");
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
        if (txtMaMX.getText().isBlank() || txtTenMX.getText().isBlank()) {
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
            String mamx = txtMaMX.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa màu xe này?\nMã màu: "
                    + txtMaMX.getText() + "\nTên màu: "
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

    private DongXe getFormDongXe() {
        DongXe dx = new DongXe();
        dx.setMaDX(txtMaDX.getText());
        dx.setTenDX(txtTenDX.getText());
        dx.setTenHX(cboTenHX.getSelectedItem().toString());
        dx.setBaohanh(txtBaoHanh.getDate());
        return dx;
    }

    void setFormDongXe(DongXe dx) {
        txtMaDX.setText(dx.getMaDX());
        txtTenDX.setText(dx.getTenDX());
        cboTenHX.setSelectedItem(daodx.selectHangXe(dx.getTenHX().toString()));
        cboTenHX.setToolTipText(String.valueOf(dx.getTenHX()));
//        cboTenHX.setSelectedIndex(0);
        cboTenHX.setSelectedItem(cboTenHX.getToolTipText());
        txtBaoHanh.setDate(dx.getBaohanh());
    }

    void clearFormDongXe() {
        //txtCreator.setText("");
        txtMaDX.setText("");
        txtTenDX.setText("");
        cboTenHX.setSelectedIndex(0);
        txtBaoHanh.setDate(new Date());
        this.row = -1;
        this.updateStatusDongXe();
    }

    void updateStatusDongXe() {
        boolean edit = (this.row >= 0);
        txtMaDX.setEditable(!edit);
        txtMaDX.setEnabled(!edit);
        btnThemDX.setEnabled(!edit);
        btnSuaDX.setEnabled(edit);
        btnXoaDX.setEnabled(edit);
    }

    void editDongXe() {
        String tendx = (String) tblDongXe.getValueAt(this.row, 0);
        DongXe dx = daodx.selectById(tendx);
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
                txtBaoHanh.setDate(new Date());
                txtMaDX.setEditable(true);
                txtMaDX.setEnabled(true);
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
            txtMaDX.setEditable(false);
            txtMaDX.setEnabled(false);
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
                Object[] row = {dx.getMaDX(), dx.getTenDX(), dx.getTenHX(), XDate.toString(dx.getBaohanh(), "dd-MMM-yyyy")};
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void insertDongXe() {
        DongXe dx = getFormDongXe();
        List<DongXe> list = daodx.selectAll();
        if (txtMaDX.getText().isBlank() || txtTenDX.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else {
            for (DongXe dx1 : list) {
                if (dx1.getMaDX().equalsIgnoreCase(txtMaDX.getText())) {
                    MsgBox.alert(this, "Mã " + txtMaDX.getText() + " đã tồn tại!");
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
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không được phép xóa!");
        } else {
            String madx = txtMaDX.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa dòng xe này?\nMã dòng: "
                    + txtMaDX.getText() + "\nTên dòng: "
                    + txtTenDX.getText())) {
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

    void fillTableXe() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSachXe.getModel();
        model.setRowCount(0);
        try {
            List<Xe> list = daoxe.selectAll();
            for (Xe x : list) {
                Object[] row = {x.getMaXe(), x.getTenXe(), x.getTenHX(), x.getMaDX(), x.getMaLX(), x.getMaMX(), x.getSokhung(), x.getSomay(), x.getDungtich(), x.getGia(), x.getSoluong()};
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

    private Xe getFormXe() {
        String dx = (String) cboDongXe.getSelectedItem();
        String lx = (String) cboLoaiXe.getSelectedItem();
        String mx = (String) cboMauXe.getSelectedItem();
        String madx = "", malx = "", mamx = "";;
        List<DongXe> listdx = daodx.selectByDX(dx);
        List<LoaiXe> listlx = daolx.selectByLX(lx);
        List<MauXe> listmx = daomx.selectByMX(mx);
        for (DongXe x : listdx) {
            madx = x.getMaDX();
            cboDongXe.setToolTipText(madx);
        }
        for (LoaiXe x : listlx) {
            malx = x.getMaLX();
            cboLoaiXe.setToolTipText(malx);
        }
        for (MauXe x : listmx) {
            mamx = x.getMaMX();
            cboMauXe.setToolTipText(mamx);
        }
//        System.out.println(cboHX.getSelectedItem().toString());
//        System.out.println(cboDongXe.getToolTipText());
//        System.out.println(cboMauXe.getToolTipText());
//        System.out.println(cboLoaiXe.getToolTipText());
        Xe x = new Xe();
        x.setMaXe(txtMaXe.getText());
        x.setTenXe(txtTenXe.getText());
        x.setTenHX(cboHX.getSelectedItem().toString());
        x.setMaDX(cboDongXe.getToolTipText());
        x.setMaMX(cboMauXe.getToolTipText());
        x.setMaLX(cboLoaiXe.getToolTipText());
        x.setSokhung(txtSokhung.getText());
        x.setSomay(txtSomay.getText());
        x.setDungtich(txtDunhtich.getText());
        x.setGia(txtGia.getText());
        x.setSoluong(txtSoLuong.getText());
        x.setHinh_anh(lblAnhXe.getToolTipText());
        return x;
    }

    void setFormXe(Xe x) {
        txtMaXe.setText(x.getMaXe());
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
        txtSokhung.setText(x.getSokhung());
        txtSomay.setText(x.getSomay());
        txtDunhtich.setText(x.getDungtich());
        txtGia.setText(x.getGia() + "");
        txtSoLuong.setText(x.getSoluong() + "");
        if (x.getHinh_anh() != null) {
            lblAnhXe.setToolTipText(x.getHinh_anh());
            lblAnhXe.setIcon(XImage.readAnhXe(x.getHinh_anh()));
        }
    }

    void clearFormXe() {
        txtMaXe.setText("");
        txtTenXe.setText("");
        cboHX.setSelectedIndex(0);
        cboDongXe.setSelectedIndex(0);
        cboMauXe.setSelectedIndex(0);
        cboLoaiXe.setSelectedIndex(0);
        txtSokhung.setText("");
        txtSomay.setText("");
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
        txtMaXe.setEditable(!edit);
        txtMaXe.setEnabled(!edit);
        btnThemXe.setEnabled(!edit);
        btnSuaXe.setEnabled(edit);
        btnXoaXe.setEnabled(edit);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnFirst.setEnabled(edit && !first);
        btnLast.setEnabled(edit && !last);
    }

    void editXe() {
        String tenxe = (String) tblDanhSachXe.getValueAt(this.row, 0);
        Xe dx = daoxe.selectById(tenxe);
        this.setFormXe(dx);
        XeMayJTabbedPane.setSelectedIndex(4);
        this.updateStatusXe();
    }

    void insertXe() {
        Xe dx = getFormXe();
        String dx1 = (String) cboDongXe.getSelectedItem();
        String lx = (String) cboLoaiXe.getSelectedItem();
        String mx = (String) cboMauXe.getSelectedItem();
        List<Xe> list = daoxe.selectAll();
        List<Xe> list1 = daoxe.selectAll();
        if (txtMaXe.getText().isBlank() || txtTenXe.getText().isBlank()
                || txtSokhung.getText().isBlank() || txtSomay.getText().isBlank() || txtDunhtich.getText().isBlank()) {
            MsgBox.alert(this, "Vui lòng nhập đủ thông tin!");
            return;
        } else if (lblAnhXe.getIcon() == null) {
            MsgBox.alert(this, "Vui lòng thêm ảnh xe!");
            return;
        } else {
            for (Xe x : list) {
                if (x.getMaXe().equalsIgnoreCase(txtMaXe.getText())) {
                    MsgBox.alert(this, "Mã " + txtMaXe.getText() + " đã tồn tại!");
                    return;
                }
            }
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
                ||txtSokhung.getText().isBlank()||txtSomay.getText().isBlank()||txtDunhtich.getText().isBlank()) {
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
            String maxe = txtMaXe.getText();
            if (MsgBox.confirm(this, "Bạn có muốn xóa xe này?\nMã xe: "
                    + txtMaXe.getText() + "\nTên xe: "
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
            txtBaoHanh.setDate(new Date());
            txtMaXe.setEnabled(true);
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
            txtSokhung.setEnabled(true);
            txtSomay.setEnabled(true);
            txtDunhtich.setEnabled(true);
        } else {
            //Khóa dòng xe
            txtMaDX.setEnabled(false);
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
            txtMaXe.setEnabled(false);
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
            txtSokhung.setEnabled(false);
            txtSomay.setEnabled(false);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGChucVu = new javax.swing.ButtonGroup();
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
        txtNgayTao = new javax.swing.JTextField();
        DanhSachKHJPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtTim1 = new javax.swing.JTextField();
        JPanelXeMay = new javax.swing.JPanel();
        XeMayJTabbedPane = new javax.swing.JTabbedPane();
        LoaiXeJPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblLoaiXe = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtMaLX = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTenLX = new javax.swing.JTextField();
        btnThemLX = new javax.swing.JButton();
        btnSuaLX = new javax.swing.JButton();
        btnXoaLX = new javax.swing.JButton();
        btnResetLX = new javax.swing.JButton();
        MauXeJPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblMauXe = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtMaMX = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTenMX = new javax.swing.JTextField();
        btnThemMX = new javax.swing.JButton();
        btnSuaMX = new javax.swing.JButton();
        btnXoaMX = new javax.swing.JButton();
        btnResetMX = new javax.swing.JButton();
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
        txtMaDX = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtTenDX = new javax.swing.JTextField();
        btnThemDX = new javax.swing.JButton();
        btnSuaDX = new javax.swing.JButton();
        btnXoaDX = new javax.swing.JButton();
        btnResetDX = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cboTenHX = new javax.swing.JComboBox<>();
        txtBaoHanh = new com.toedter.calendar.JDateChooser();
        QLXe = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        lblAnhXe = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtTenXe = new javax.swing.JTextField();
        txtMaXe = new javax.swing.JTextField();
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
        jLabel43 = new javax.swing.JLabel();
        txtSomay = new javax.swing.JTextField();
        txtSokhung = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtDunhtich = new javax.swing.JTextField();
        DanhSachXeJPanel = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblDanhSachXe = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        txtTim2 = new javax.swing.JTextField();
        JPanelHoaDon = new javax.swing.JPanel();
        HoaDonJTabbedPane = new javax.swing.JTabbedPane();
        HoaDonJPanel = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        HoaDonJTable = new javax.swing.JTable();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        txtTenKHHoaDon = new javax.swing.JTextField();
        txtMaKHHoaDon = new javax.swing.JTextField();
        txtMaNVHoaDon = new javax.swing.JTextField();
        txtTenNVHoaDon = new javax.swing.JTextField();
        txtNgayTaoHoaDon = new javax.swing.JTextField();

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
            .addComponent(lblTenNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(MeNuJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
                .addComponent(btnCaiDat, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
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
                .addGap(0, 710, Short.MAX_VALUE)
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        JPanelHomeLayout.setVerticalGroup(
            JPanelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPanelHomeLayout.createSequentialGroup()
                .addGap(0, 504, Short.MAX_VALUE)
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

        txtSodienthoai.setText("0");
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
                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(jLabel2)))
                        .addGap(13, 13, 13)
                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV)
                            .addComponent(txtTenNV)
                            .addComponent(txtSodienthoai)
                            .addComponent(txtMatKhau)
                            .addComponent(txtMatKhau2)
                            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                        .addComponent(rdoQuanLy)
                                        .addGap(13, 13, 13)
                                        .addComponent(rdoNhanVien)))
                                .addGap(0, 112, Short.MAX_VALUE))))
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnXoaAnhNhanVien)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel5)
                        .addGap(74, 74, 74)
                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                                .addComponent(btnThemNhanvien)
                                .addGap(18, 18, 18)
                                .addComponent(btnSuaNhanVien)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoaNhanVien)
                                .addGap(18, 18, 18)
                                .addComponent(btnResetNhanVien)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2))))
                .addGap(183, 183, 183))
        );
        CapNhatJPanelLayout.setVerticalGroup(
            CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2))
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(btnXoaAnhNhanVien)
                    .addGroup(CapNhatJPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))))
                .addGap(14, 14, 14)
                .addGroup(CapNhatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuaNhanVien)
                    .addComponent(btnThemNhanvien)
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
        );
        DanhSachJPanelLayout.setVerticalGroup(
            DanhSachJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DanhSachJPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(DanhSachJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtTimkiemNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE))
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

        txtSodienthoaiKH.setText("0");
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

        txtNgayTao.setEnabled(false);
        txtNgayTao.setText(XDate.toString(new Date(), "dd-MM-yyyy"));

        javax.swing.GroupLayout CapNhatKhachHangJPanelLayout = new javax.swing.GroupLayout(CapNhatKhachHangJPanel);
        CapNhatKhachHangJPanel.setLayout(CapNhatKhachHangJPanelLayout);
        CapNhatKhachHangJPanelLayout.setHorizontalGroup(
            CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhatKhachHangJPanelLayout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(167, 167, 167))
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
                .addComponent(btnThemKH, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(btnSuaKH, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(btnXoaKH, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(btnResetKH, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
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
                            .addComponent(txtNgayTao)
                            .addComponent(jScrollPane3)))
                    .addGroup(CapNhatKhachHangJPanelLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(41, 41, 41)
                        .addComponent(txtSodienthoaiKH)))
                .addGap(148, 148, 148))
        );
        CapNhatKhachHangJPanelLayout.setVerticalGroup(
            CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhatKhachHangJPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel21)
                .addGap(41, 41, 41)
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
                .addGap(15, 15, 15)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(CapNhatKhachHangJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jScrollPane3))
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

        javax.swing.GroupLayout DanhSachKHJPanelLayout = new javax.swing.GroupLayout(DanhSachKHJPanel);
        DanhSachKHJPanel.setLayout(DanhSachKHJPanelLayout);
        DanhSachKHJPanelLayout.setHorizontalGroup(
            DanhSachKHJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
            .addGroup(DanhSachKHJPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTim1, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
                .addGap(77, 77, 77))
        );
        DanhSachKHJPanelLayout.setVerticalGroup(
            DanhSachKHJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DanhSachKHJPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(DanhSachKHJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTim1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE))
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

        jLabel6.setText("Mã loại xe");

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
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addGap(62, 62, 62)
                .addGroup(LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoaiXeJPanelLayout.createSequentialGroup()
                        .addGroup(LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaLX)
                            .addComponent(txtTenLX)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(LoaiXeJPanelLayout.createSequentialGroup()
                        .addComponent(btnThemLX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSuaLX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaLX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetLX)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(72, 72, 72))
        );
        LoaiXeJPanelLayout.setVerticalGroup(
            LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
            .addGroup(LoaiXeJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addGap(32, 32, 32)
                .addGroup(LoaiXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtMaLX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

        jLabel9.setText("Mã màu xe");

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
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addGap(62, 62, 62)
                .addGroup(MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MauXeJPanelLayout.createSequentialGroup()
                        .addGroup(MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaMX)
                            .addComponent(txtTenMX)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(MauXeJPanelLayout.createSequentialGroup()
                        .addComponent(btnThemMX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSuaMX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaMX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetMX)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(72, 72, 72))
        );
        MauXeJPanelLayout.setVerticalGroup(
            MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
            .addGroup(MauXeJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addGap(32, 32, 32)
                .addGroup(MauXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtMaMX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
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
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
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

        jLabel28.setText("Mã dòng xe");

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

        javax.swing.GroupLayout DongXeJPanelLayout = new javax.swing.GroupLayout(DongXeJPanel);
        DongXeJPanel.setLayout(DongXeJPanelLayout);
        DongXeJPanelLayout.setHorizontalGroup(
            DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DongXeJPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DongXeJPanelLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DongXeJPanelLayout.createSequentialGroup()
                                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel29))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaDX)
                                    .addComponent(txtTenDX)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
                                .addGap(72, 72, 72))
                            .addGroup(DongXeJPanelLayout.createSequentialGroup()
                                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel31))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboTenHX, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtBaoHanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(73, 73, 73))))
                    .addGroup(DongXeJPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThemDX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSuaDX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaDX)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetDX)
                        .addGap(73, 73, 73))))
        );
        DongXeJPanelLayout.setVerticalGroup(
            DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
            .addGroup(DongXeJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addGap(32, 32, 32)
                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtMaDX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtTenDX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(cboTenHX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(txtBaoHanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(DongXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemDX)
                    .addComponent(btnSuaDX)
                    .addComponent(btnXoaDX)
                    .addComponent(btnResetDX))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jLabel35.setText("Mã xe");
        QLXe.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, -1, -1));

        jLabel36.setText("Tên xe");
        QLXe.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, -1, -1));

        jLabel37.setText("Loại xe");
        QLXe.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, -1, -1));

        jLabel38.setText("Dòng xe");
        QLXe.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, -1, -1));

        jLabel39.setText("Số lượng");
        QLXe.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 370, -1, -1));
        QLXe.add(txtTenXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, 310, -1));
        QLXe.add(txtMaXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 310, -1));

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
        QLXe.add(btnResetXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 420, -1, -1));

        btnThemXe.setText("Thêm");
        btnThemXe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemXeActionPerformed(evt);
            }
        });
        QLXe.add(btnThemXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 420, -1, -1));

        btnSuaXe.setText("Sửa");
        btnSuaXe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaXeActionPerformed(evt);
            }
        });
        QLXe.add(btnSuaXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 420, -1, -1));

        btnXoaXe.setText("Xóa");
        btnXoaXe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaXeActionPerformed(evt);
            }
        });
        QLXe.add(btnXoaXe, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 420, -1, -1));
        QLXe.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 370, 310, -1));

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
        QLXe.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 340, -1, -1));

        txtGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaKeyReleased(evt);
            }
        });
        QLXe.add(txtGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 340, 310, -1));

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
        QLXe.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 310, -1, -1));

        jLabel43.setText("Số khung");
        QLXe.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 250, -1, -1));
        QLXe.add(txtSomay, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 280, 310, -1));
        QLXe.add(txtSokhung, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, 310, -1));

        jLabel44.setText("Số máy");
        QLXe.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 280, -1, -1));
        QLXe.add(txtDunhtich, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 310, 310, -1));

        XeMayJTabbedPane.addTab("Quản Lý Thông Tin Xe", QLXe);

        tblDanhSachXe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã xe", "Tên xe", "Hãng", "Dòng", "Loại", "Màu", "Số khung", "Số máy", "Dung tích", "Giá", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
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

        javax.swing.GroupLayout DanhSachXeJPanelLayout = new javax.swing.GroupLayout(DanhSachXeJPanel);
        DanhSachXeJPanel.setLayout(DanhSachXeJPanelLayout);
        DanhSachXeJPanelLayout.setHorizontalGroup(
            DanhSachXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
            .addGroup(DanhSachXeJPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTim2, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DanhSachXeJPanelLayout.setVerticalGroup(
            DanhSachXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DanhSachXeJPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(DanhSachXeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTim2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        XeMayJTabbedPane.addTab("Danh Sách Xe", DanhSachXeJPanel);

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
                "Mã hóa đơn", "Mã khách hàng", "Tên khách hàng", "Mã nhân viên", "Tên nhân viên", "Ngày tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
        jLabel45.setText("HÓA ĐƠN");

        jLabel46.setText("Mã hóa đơn");

        jLabel47.setText("Mã khách hàng");

        jLabel48.setText("Tên khách hàng");

        jLabel49.setText("Mã nhân viên");

        jLabel50.setText("Tên nhân viên");

        jLabel51.setText("Ngày tạo");

        javax.swing.GroupLayout HoaDonJPanelLayout = new javax.swing.GroupLayout(HoaDonJPanel);
        HoaDonJPanel.setLayout(HoaDonJPanelLayout);
        HoaDonJPanelLayout.setHorizontalGroup(
            HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel47)
                                    .addComponent(jLabel46))
                                .addGap(18, 18, 18)
                                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaHD)
                                    .addComponent(txtMaKHHoaDon)))
                            .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel49)
                                    .addComponent(jLabel50)
                                    .addComponent(jLabel51))
                                .addGap(18, 18, 18)
                                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTenKHHoaDon)
                                    .addComponent(txtMaNVHoaDon)
                                    .addComponent(txtTenNVHoaDon)
                                    .addComponent(txtNgayTaoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(28, 28, 28))))
        );
        HoaDonJPanelLayout.setVerticalGroup(
            HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
            .addGroup(HoaDonJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addGap(18, 18, 18)
                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(txtMaKHHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txtTenKHHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(txtMaNVHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50)
                    .addComponent(txtTenNVHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(HoaDonJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51)
                    .addComponent(txtNgayTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        HoaDonJTabbedPane.addTab("Hóa Đơn", HoaDonJPanel);

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
            txtSodienthoai.setText("0");
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
            txtSodienthoaiKH.setText("0");
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
    }//GEN-LAST:event_btnThemXeActionPerformed

    private void btnResetXeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetXeActionPerformed
        // TODO add your handling code here:
        this.clearFormXe();
    }//GEN-LAST:event_btnResetXeActionPerformed

    private void btnSuaXeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaXeActionPerformed
        // TODO add your handling code here:
        this.updateXe();
    }//GEN-LAST:event_btnSuaXeActionPerformed

    private void btnXoaXeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaXeActionPerformed
        // TODO add your handling code here:
        this.deleteXe();
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
    private javax.swing.JTabbedPane XeMayJTabbedPane;
    private javax.swing.JButton btnCaiDat;
    private javax.swing.JButton btnFirst;
    private javax.swing.ButtonGroup btnGChucVu;
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
    private javax.swing.JButton btnResetXe;
    private javax.swing.JButton btnSuaDX;
    private javax.swing.JButton btnSuaKH;
    private javax.swing.JButton btnSuaLX;
    private javax.swing.JButton btnSuaMX;
    private javax.swing.JButton btnSuaNhanVien;
    private javax.swing.JButton btnSuaXe;
    private javax.swing.JButton btnThemDX;
    private javax.swing.JButton btnThemHX;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnThemLX;
    private javax.swing.JButton btnThemMX;
    private javax.swing.JButton btnThemNhanvien;
    private javax.swing.JButton btnThemXe;
    private javax.swing.JButton btnXoaAnhNhanVien;
    private javax.swing.JButton btnXoaDX;
    private javax.swing.JButton btnXoaHX;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.JButton btnXoaLX;
    private javax.swing.JButton btnXoaMX;
    private javax.swing.JButton btnXoaNhanVien;
    private javax.swing.JButton btnXoaXe;
    private javax.swing.JComboBox<String> cboDongXe;
    private javax.swing.JComboBox<String> cboHX;
    private javax.swing.JComboBox<String> cboLoaiXe;
    private javax.swing.JComboBox<String> cboMauXe;
    private javax.swing.JComboBox<String> cboTenHX;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
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
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblAnhXe;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblTime;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoQuanLy;
    private javax.swing.JTable tblDanhSachXe;
    private javax.swing.JTable tblDongXe;
    private javax.swing.JTable tblHangXe;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblLoaiXe;
    private javax.swing.JTable tblMauXe;
    private javax.swing.JTable tblNhanVien;
    private com.toedter.calendar.JDateChooser txtBaoHanh;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextArea txtDiaChiKH;
    private javax.swing.JTextField txtDunhtich;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaDX;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaKHHoaDon;
    private javax.swing.JTextField txtMaLX;
    private javax.swing.JTextField txtMaMX;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaNVHoaDon;
    private javax.swing.JTextField txtMaXe;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JPasswordField txtMatKhau2;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtNgayTaoHoaDon;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSodienthoai;
    private javax.swing.JTextField txtSodienthoaiKH;
    private javax.swing.JTextField txtSokhung;
    private javax.swing.JTextField txtSomay;
    private javax.swing.JTextField txtTenDX;
    private javax.swing.JTextField txtTenHX;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenKHHoaDon;
    private javax.swing.JTextField txtTenLX;
    private javax.swing.JTextField txtTenMX;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTenNVHoaDon;
    private javax.swing.JTextField txtTenXe;
    private javax.swing.JTextField txtTim1;
    private javax.swing.JTextField txtTim2;
    private javax.swing.JTextField txtTimkiemNV;
    // End of variables declaration//GEN-END:variables

}
