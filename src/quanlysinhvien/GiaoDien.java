package quanlysinhvien;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

public class GiaoDien extends javax.swing.JFrame {

    private ArrayList<SinhVien> list;
    DefaultTableModel model;
    private ArrayList<SinhVien> listSVN;
    DefaultTableModel modelSVN;
    private ArrayList<Nganh> listN;
    DefaultTableModel modelN;
    private ArrayList<Khoa> listK;
    DefaultTableModel modelK;
    private ArrayList<KetQua> listKQ;
    DefaultTableModel modelKQ;
    private ArrayList<HocPhan> listHP;
    DefaultTableModel modelHP;
    int temp;

    public GiaoDien() {
        initComponents();
        this.setLocationRelativeTo(null);
        list = new ConnectDB().getDanhSachSinhVien();
        model = (DefaultTableModel) tblDanhSachSV.getModel();
        model.setColumnIdentifiers(new Object[]{
            "MSSV", "Họ tên", "Giới tính", "Ngày sinh", "Quê quán", "Mã Khoa", "Mã Ngành", "Khóa học"
        });
        reloadTable();

        listSVN = new ConnectDB().getDanhSachSinhVien();
        modelSVN = (DefaultTableModel) tblDanhSachSVNganh.getModel();
        modelSVN.setColumnIdentifiers(new Object[]{
            "MSSV", "Họ tên", "Giới tính", "Ngày sinh", "Quê quán", "Mã Khoa", "Mã Ngành", "Khóa học"
        });
        reloadTableSVNganh();

        listN = new ConnectDB().getNganh();
        modelN = (DefaultTableModel) tblNganh.getModel();
        modelN.setColumnIdentifiers(new Object[]{
            "Mã Khoa", "Tên Khoa", "Mã Ngành", "Tên Ngành"
        });
        reloadNganhTable();

        listK = new ConnectDB().getSoSV();
        modelK = (DefaultTableModel) tblTongSoSVKhoa.getModel();
        modelK.setColumnIdentifiers(new Object[]{
            "Tên khoa", "Tổng sinh viên"
        });
        reloadTongSoSV();

        txtUpdateMaHP.setEditable(false);
        txtUpdateTenHP.setEditable(false);
        txtUpdateHocKy.setEditable(false);
        txtUpdateNamHoc.setEditable(false);
        txtShowHoTen.setEditable(false);
        txtShowMaKhoa.setEditable(false);
        txtShowMaNganh.setEditable(false);
        txtShowDiemTB.setEditable(false);

        listHP = new ConnectDB().getHP();
        modelHP = (DefaultTableModel) tblDSHP.getModel();
        modelHP.setColumnIdentifiers(new Object[]{
            "Mã Học Phần", "Tên Học Phần", "Số TC"
        });
        hocPhan();
        tblDanhSachSV.setComponentPopupMenu(jPopupMenu1);
    }

    public void reloadTable() {
        model = (DefaultTableModel) tblDanhSachSV.getModel();
        Object[] row = new Object[10];
        for (int g = 0; g < list.size(); g++) {
            row[0] = list.get(g).getMssv();
            row[1] = list.get(g).getHoTen();
            row[2] = list.get(g).getGioiTinh();
            row[3] = list.get(g).getNgaySinh();
            row[4] = list.get(g).getQueQuan();
            row[5] = list.get(g).getMaKhoa();
            row[6] = list.get(g).getMaNganh();
            row[7] = list.get(g).getKhoaHoc();
            model.addRow(row);
        }
    }

    public void reloadTableSVNganh() {
        modelSVN = (DefaultTableModel) tblDanhSachSVNganh.getModel();
        Object[] row = new Object[10];
        for (int g = 0; g < listSVN.size(); g++) {
            row[0] = listSVN.get(g).getMssv();
            row[1] = listSVN.get(g).getHoTen();
            row[2] = listSVN.get(g).getGioiTinh();
            row[3] = listSVN.get(g).getNgaySinh();
            row[4] = listSVN.get(g).getQueQuan();
            row[5] = listSVN.get(g).getMaKhoa();
            row[6] = listSVN.get(g).getMaNganh();
            row[7] = listSVN.get(g).getKhoaHoc();
            modelSVN.addRow(row);
        }
    }

    public void reloadNganhTable() {
        modelN = (DefaultTableModel) tblNganh.getModel();
        Object[] row = new Object[4];
        for (int g = 0; g < listN.size(); g++) {
            row[0] = listN.get(g).getMaKhoa();
            row[1] = listN.get(g).getTenKhoa();
            row[2] = listN.get(g).getMaNganh();
            row[3] = listN.get(g).getTenNganh();
            modelN.addRow(row);
        }
    }

    public void reloadDanhSachHP() {
        modelKQ = (DefaultTableModel) tblDanhSachHP.getModel();
        Object[] row = new Object[7];
        for (int g = 0; g < listKQ.size(); g++) {
            row[0] = listKQ.get(g).getMaHP();
            row[1] = listKQ.get(g).getTenHP();
            //System.out.println(listKQ.get(g).getTenHP());
            row[2] = listKQ.get(g).getSoTC();
            row[3] = listKQ.get(g).getHocKy();
            row[4] = listKQ.get(g).getNamHoc();
            row[5] = listKQ.get(g).getDiem();
            String diemChu;
            if (listKQ.get(g).getDiem() == -1) {
                diemChu = "";
            } else if (listKQ.get(g).getDiem() < 4) {
                diemChu = "F";
            } else if (listKQ.get(g).getDiem() < 5) {
                diemChu = "D";
            } else if (listKQ.get(g).getDiem() < 6) {
                diemChu = "C";
            } else if (listKQ.get(g).getDiem() < 8) {
                diemChu = "B";
            } else {
                diemChu = "A";
            }
            row[6] = diemChu;
            modelKQ.addRow(row);
        }
    }

    public void reloadDanhSachHPSV() {
        modelKQ = (DefaultTableModel) tblDanhSachHPSV.getModel();
        Object[] row = new Object[7];
        for (int g = 0; g < listKQ.size(); g++) {
            row[0] = listKQ.get(g).getMaHP();
            row[1] = listKQ.get(g).getTenHP();
            row[2] = listKQ.get(g).getSoTC();
            row[3] = listKQ.get(g).getHocKy();
            row[4] = listKQ.get(g).getNamHoc();
            row[5] = listKQ.get(g).getDiem();
            String diemChu;
            if (listKQ.get(g).getDiem() == -1) {
                diemChu = "";
            } else if (listKQ.get(g).getDiem() < 4) {
                diemChu = "F";
            } else if (listKQ.get(g).getDiem() < 5) {
                diemChu = "D";
            } else if (listKQ.get(g).getDiem() < 6) {
                diemChu = "C";
            } else if (listKQ.get(g).getDiem() < 8) {
                diemChu = "B";
            } else {
                diemChu = "A";
            }
            row[6] = diemChu;
            modelKQ.addRow(row);
        }
    }

    public void hocPhan() {
        modelHP = (DefaultTableModel) tblDSHP.getModel();
        Object[] row = new Object[3];
        for (int g = 0; g < listHP.size(); g++) {
            row[0] = listHP.get(g).getMaHP();
            row[1] = listHP.get(g).getTenHP();
            row[2] = listHP.get(g).getSoTC();
            modelHP.addRow(row);
        }
    }

    public float pointAvg() {

        int stc = 0, temp, diem;
        float point;
        float spoint = 0;
        for (int i = 0; i < listKQ.size(); i++) {
            temp = listKQ.get(i).getSoTC();
            diem = listKQ.get(i).getDiem();
            if (diem >= 0) {
                stc = stc + temp;
                spoint = spoint + diem * temp;
            }
        }
        point = (float) spoint / stc;
        point = (float) Math.round(point * 100) / 100;
        return point;
    }

    public void reloadTB() {
        String mssv = txtNhapMSSV.getText();
        txtShowDiemTB.setText("");
        temp = temp + 1;
        if (temp == 1) {
            int t = 0;
            //System.out.println(mssv);
            for (int i = 0; i < list.size(); i++) {
                if (mssv.equals(model.getValueAt(i, 0).toString())) {

                    txtShowHoTen.setText(tblDanhSachSV.getModel().getValueAt(i, 1).toString());
                    txtShowMaKhoa.setText(tblDanhSachSV.getModel().getValueAt(i, 5).toString());
                    txtShowMaNganh.setText(tblDanhSachSV.getModel().getValueAt(i, 6).toString());
                    listKQ = new ConnectDB().getKetQua(mssv);
                    modelKQ = (DefaultTableModel) tblDanhSachHP.getModel();
                    modelKQ.setColumnIdentifiers(new Object[]{
                        "Mã HP", "Tên HP", "Số Tín Chỉ", "Học Kỳ", "Năm Học", "Điểm Số", "Điểm Chữ"
                    });
                    modelKQ.fireTableDataChanged();
                    reloadDanhSachHP();
                    t = 1;
                    txtShowDiemTB.setText(Float.toString(pointAvg()));
                }
            }
            if (t == 0) {
                JOptionPane.showMessageDialog(rootPane, "Không tìm thấy sinh viên!");
                txtShowHoTen.setText("");
                txtShowMaKhoa.setText("");
                txtShowMaNganh.setText("");
                temp = 0;
                txtShowDiemTB.setText("");
            }
        } else {
            int t = 0;
            for (int g = 0; g < listKQ.size(); g++) {
                modelKQ.removeRow(0);
            }
            //System.out.println(mssv);
            for (int i = 0; i < list.size(); i++) {
                if (mssv.equals(model.getValueAt(i, 0).toString())) {

                    txtShowHoTen.setText(tblDanhSachSV.getModel().getValueAt(i, 1).toString());
                    txtShowMaKhoa.setText(tblDanhSachSV.getModel().getValueAt(i, 5).toString());
                    txtShowMaNganh.setText(tblDanhSachSV.getModel().getValueAt(i, 6).toString());
                    listKQ = new ConnectDB().getKetQua(mssv);
                    modelKQ = (DefaultTableModel) tblDanhSachHP.getModel();
                    modelKQ.setColumnIdentifiers(new Object[]{
                        "Mã HP", "Tên HP", "Số Tín Chỉ", "Học Kỳ", "Năm Học", "Điểm Số", "Điểm Chữ"
                    });
                    modelKQ.fireTableDataChanged();
                    reloadDanhSachHP();
                    t = 1;
                    txtShowDiemTB.setText(Float.toString(pointAvg()));
                }
            }
            if (t == 0) {
                JOptionPane.showMessageDialog(rootPane, "Không tìm thấy sinh viên!");
                txtShowHoTen.setText("");
                txtShowMaKhoa.setText("");
                txtShowMaNganh.setText("");
                temp = 0;
                txtShowDiemTB.setText("");
            }
        }
    }

    public void reloadTBSV(String s) {
        for (int i = 0; i < list.size(); i++) {

            //System.out.println(model.getValueAt(i, 0).toString());
            if (s.equals(model.getValueAt(i, 0).toString())) {
                txtMSSV.setText(tblDanhSachSV.getModel().getValueAt(i, 0).toString());
                txtMSSV.setEditable(false);
                txtHoTenSV.setText(tblDanhSachSV.getModel().getValueAt(i, 1).toString());
                txtHoTenSV.setEditable(false);
                txtNgaySinh.setText(tblDanhSachSV.getModel().getValueAt(i, 3).toString());
                txtNgaySinh.setEditable(false);
                txtMaKhoaSV.setText(tblDanhSachSV.getModel().getValueAt(i, 5).toString());
                txtMaKhoaSV.setEditable(false);
                txtMaNganhSV.setText(tblDanhSachSV.getModel().getValueAt(i, 6).toString());
                txtMaNganhSV.setEditable(false);
                txtQueQuan.setText(tblDanhSachSV.getModel().getValueAt(i, 4).toString());
                txtQueQuan.setEditable(false);
                txtKhoaHoc.setText(tblDanhSachSV.getModel().getValueAt(i, 7).toString());
                txtKhoaHoc.setEditable(false);
                listKQ = new ConnectDB().getKetQua(s);
                modelKQ = (DefaultTableModel) tblDanhSachHPSV.getModel();
                modelKQ.setColumnIdentifiers(new Object[]{
                    "Mã HP", "Tên HP", "Số Tín Chỉ", "Học Kỳ", "Năm Học", "Điểm Số", "Điểm Chữ"
                });
                modelKQ.fireTableDataChanged();
                reloadDanhSachHPSV();
                txtDiemTBSV.setText(Float.toString(pointAvg()));
            }
        }
    }

    public void reloadTongSoSV() {
        modelK = (DefaultTableModel) tblTongSoSVKhoa.getModel();
        Object[] row = new Object[2];
        for (int g = 0; g < listK.size(); g++) {
            row[0] = listK.get(g).getTenKhoa();
            row[1] = listK.get(g).getSoSV();
            modelK.addRow(row);
        }
    }

    public void ShowSinhVienPanel() {
        SinhVienFrame.setVisible(true);
        SinhVienFrame.setResizable(false);
        SinhVienFrame.setSize(1015, 629);
        SinhVienFrame.setLocationRelativeTo(null);
    }

    public void exportExcel(JTable table) {
        JFileChooser chooser = new JFileChooser();
        int i = chooser.showSaveDialog(chooser);
        if (i == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                FileWriter out = new FileWriter(file + ".xls");
                BufferedWriter bwrite = new BufferedWriter(out);
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                // ten Cot
                for (int j = 0; j < table.getColumnCount(); j++) {
                    bwrite.write(model.getColumnName(j) + "\t");
                }
                bwrite.write("\n");
                // Lay du lieu dong
                for (int j = 0; j < table.getRowCount(); j++) {
                    for (int k = 0; k < table.getColumnCount(); k++) {
                        bwrite.write(model.getValueAt(j, k) + "\t");
                    }
                    bwrite.write("\n");
                }
                bwrite.close();
                JOptionPane.showMessageDialog(null, "Lưu file thành công!");
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, "Lỗi khi lưu file!");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ThemSVDialog = new javax.swing.JDialog();
        lblThemMssv = new javax.swing.JLabel();
        lblThemHoTen = new javax.swing.JLabel();
        lblThemGioiTinh = new javax.swing.JLabel();
        lblThemNgaySinh = new javax.swing.JLabel();
        lblThemQueQuan = new javax.swing.JLabel();
        lblThemMaKhoa = new javax.swing.JLabel();
        lblThemMaNganh = new javax.swing.JLabel();
        lblThemKhoahoc = new javax.swing.JLabel();
        txtThemMssv = new javax.swing.JTextField();
        txtThemHoTen = new javax.swing.JTextField();
        txtThemNgaySinh = new javax.swing.JTextField();
        txtThemQueQuan = new javax.swing.JTextField();
        cbbThemKhoaHoc = new javax.swing.JComboBox<>();
        RadioThemNam = new javax.swing.JRadioButton();
        RadioThemNu = new javax.swing.JRadioButton();
        btnThemSV = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cbbThemMaKhoa = new javax.swing.JComboBox<>();
        cbbThemMaNganh = new javax.swing.JComboBox<>();
        CapNhapSVDialog = new javax.swing.JDialog();
        lblUpdateMssv = new javax.swing.JLabel();
        lblUpdateHoTen = new javax.swing.JLabel();
        lblUpdateGioiTinh = new javax.swing.JLabel();
        lblUpdateNgaySinh = new javax.swing.JLabel();
        lblUpdateQueQuan = new javax.swing.JLabel();
        lblUpdateMaKhoa = new javax.swing.JLabel();
        lblUpdateMaNganh = new javax.swing.JLabel();
        lblUpdateKhoaHoc = new javax.swing.JLabel();
        txtUpdateMssv = new javax.swing.JTextField();
        txtUpdateHoTen = new javax.swing.JTextField();
        txtUpdateNgaySinh = new javax.swing.JTextField();
        txtUpdateQueQuan = new javax.swing.JTextField();
        cbbUpdateKhoaHoc = new javax.swing.JComboBox<>();
        RadioUpdateNam = new javax.swing.JRadioButton();
        RadioUpdateNu = new javax.swing.JRadioButton();
        btnCapNhapSV = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cbbUpdateMaKhoa = new javax.swing.JComboBox<>();
        cbbUpdateMaNganh = new javax.swing.JComboBox<>();
        GioiTinhGroup = new javax.swing.ButtonGroup();
        SinhVienFrame = new javax.swing.JFrame();
        ShowThongTinSVPanel = new javax.swing.JPanel();
        lblMSSV = new javax.swing.JLabel();
        txtMSSV = new javax.swing.JTextField();
        lblHoTenSV = new javax.swing.JLabel();
        txtHoTenSV = new javax.swing.JTextField();
        lblMaKhoaSV = new javax.swing.JLabel();
        txtMaKhoaSV = new javax.swing.JTextField();
        lblMaNganhSV = new javax.swing.JLabel();
        txtMaNganhSV = new javax.swing.JTextField();
        lblNgaySinh = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        lblQueQuan = new javax.swing.JLabel();
        txtQueQuan = new javax.swing.JTextField();
        lblKhoaHoc = new javax.swing.JLabel();
        txtKhoaHoc = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblDanhSachHPSV = new javax.swing.JTable();
        ShowDiemTBSV = new javax.swing.JPanel();
        lblDiemTBSV = new javax.swing.JLabel();
        txtDiemTBSV = new javax.swing.JTextField();
        btnXoaHP = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblDSHP = new javax.swing.JTable();
        cbbHocKy = new javax.swing.JComboBox<>();
        cbbNamHoc = new javax.swing.JComboBox<>();
        btnDSHPThemHP = new javax.swing.JButton();
        btnDangXuat1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        delMenuItem = new javax.swing.JMenuItem();
        updateMenuItem = new javax.swing.JMenuItem();
        lblTieude = new javax.swing.JLabel();
        btnDangXuat = new javax.swing.JButton();
        TabPane = new javax.swing.JTabbedPane();
        ThongTinSV = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachSV = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnCapNhap = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        cbbSapXep = new javax.swing.JComboBox<>();
        btnSapXep = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnInDanhSach = new javax.swing.JButton();
        QuanLyKhoa = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDanhSachSVNganh = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblTongSoSVKhoa = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblNganh = new javax.swing.JTable();
        QuanLyDiem = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDanhSachHP = new javax.swing.JTable();
        txtNhapMSSV = new javax.swing.JTextField();
        lblNhapMSSV = new javax.swing.JLabel();
        btnTimMSSV = new javax.swing.JButton();
        ShowThongTin = new javax.swing.JPanel();
        txtShowHoTen = new javax.swing.JTextField();
        lblShowHoTen = new javax.swing.JLabel();
        lblShowMaNganh = new javax.swing.JLabel();
        txtShowMaNganh = new javax.swing.JTextField();
        lblShowMaKhoa = new javax.swing.JLabel();
        txtShowMaKhoa = new javax.swing.JTextField();
        ShowDiemTB = new javax.swing.JPanel();
        lblShowDiemTB = new javax.swing.JLabel();
        txtShowDiemTB = new javax.swing.JTextField();
        btnInBangDiem = new javax.swing.JButton();
        CapNhapHP = new javax.swing.JPanel();
        lblUpdateMaHP = new javax.swing.JLabel();
        lblUpdateTenHP = new javax.swing.JLabel();
        lblUpdateHocKy = new javax.swing.JLabel();
        lblUpdateNamHoc = new javax.swing.JLabel();
        lblUpdateDiem = new javax.swing.JLabel();
        txtUpdateMaHP = new javax.swing.JTextField();
        txtUpdateTenHP = new javax.swing.JTextField();
        txtUpdateHocKy = new javax.swing.JTextField();
        txtUpdateNamHoc = new javax.swing.JTextField();
        txtUpdateDiem = new javax.swing.JTextField();
        btnCapNhapHP = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        ThemSVDialog.setTitle("Thêm sinh viên");

        lblThemMssv.setText("Mã số sinh viên:");

        lblThemHoTen.setText("Họ tên:");

        lblThemGioiTinh.setText("Giới tính:");

        lblThemNgaySinh.setText("Ngày sinh:");

        lblThemQueQuan.setText("Quê quán:");

        lblThemMaKhoa.setText("Mã khoa:");

        lblThemMaNganh.setText("Mã ngành:");

        lblThemKhoahoc.setText("Khóa học");

        cbbThemKhoaHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "16", "15", "14", "13" }));

        GioiTinhGroup.add(RadioThemNam);
        RadioThemNam.setText("Nam");

        GioiTinhGroup.add(RadioThemNu);
        RadioThemNu.setText("Nữ");

        btnThemSV.setBackground(new java.awt.Color(0, 153, 255));
        btnThemSV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Them.png"))); // NOI18N
        btnThemSV.setText("Thêm");
        btnThemSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSVActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/ThemSv.png"))); // NOI18N
        jLabel5.setText("THÊM SINH VIÊN");

        cbbThemMaKhoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CNPM", "HTTT", "KHMT", "KTMT", "KTTT", "MMT&TT" }));
        cbbThemMaKhoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbThemMaKhoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ThemSVDialogLayout = new javax.swing.GroupLayout(ThemSVDialog.getContentPane());
        ThemSVDialog.getContentPane().setLayout(ThemSVDialogLayout);
        ThemSVDialogLayout.setHorizontalGroup(
            ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThemSVDialogLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblThemMssv)
                    .addComponent(lblThemHoTen)
                    .addComponent(lblThemGioiTinh)
                    .addComponent(lblThemNgaySinh)
                    .addComponent(lblThemQueQuan)
                    .addComponent(lblThemMaKhoa)
                    .addComponent(lblThemMaNganh)
                    .addComponent(lblThemKhoahoc))
                .addGap(49, 49, 49)
                .addGroup(ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ThemSVDialogLayout.createSequentialGroup()
                        .addGroup(ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtThemQueQuan)
                            .addComponent(txtThemHoTen)
                            .addComponent(txtThemNgaySinh)
                            .addGroup(ThemSVDialogLayout.createSequentialGroup()
                                .addGroup(ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbThemKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(ThemSVDialogLayout.createSequentialGroup()
                                        .addComponent(RadioThemNam)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(RadioThemNu))
                                    .addComponent(txtThemMssv, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(44, 44, 44))
                    .addGroup(ThemSVDialogLayout.createSequentialGroup()
                        .addGroup(ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbThemMaKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbThemMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ThemSVDialogLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(105, 105, 105))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ThemSVDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThemSV)
                .addGap(134, 134, 134))
        );
        ThemSVDialogLayout.setVerticalGroup(
            ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThemSVDialogLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel5)
                .addGap(20, 20, 20)
                .addGroup(ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblThemMssv)
                    .addComponent(txtThemMssv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblThemHoTen)
                    .addComponent(txtThemHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblThemGioiTinh)
                    .addComponent(RadioThemNam)
                    .addComponent(RadioThemNu))
                .addGap(18, 18, 18)
                .addGroup(ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblThemNgaySinh)
                    .addComponent(txtThemNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblThemQueQuan)
                    .addComponent(txtThemQueQuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblThemMaKhoa)
                    .addComponent(cbbThemMaKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblThemMaNganh)
                    .addComponent(cbbThemMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ThemSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblThemKhoahoc)
                    .addComponent(cbbThemKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(btnThemSV)
                .addGap(20, 20, 20))
        );

        lblUpdateMssv.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUpdateMssv.setText("Mã số sinh viên:");

        lblUpdateHoTen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUpdateHoTen.setText("Họ Tên:");

        lblUpdateGioiTinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUpdateGioiTinh.setText("Giới tính:");

        lblUpdateNgaySinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUpdateNgaySinh.setText("Ngày sinh:");

        lblUpdateQueQuan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUpdateQueQuan.setText("Quê quán:");

        lblUpdateMaKhoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUpdateMaKhoa.setText("Mã khoa:");

        lblUpdateMaNganh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUpdateMaNganh.setText("Mã ngành:");

        lblUpdateKhoaHoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUpdateKhoaHoc.setText("Khóa học:");

        cbbUpdateKhoaHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "16", "15", "14", "13" }));

        GioiTinhGroup.add(RadioUpdateNam);
        RadioUpdateNam.setText("Nam");

        GioiTinhGroup.add(RadioUpdateNu);
        RadioUpdateNu.setText("Nữ");

        btnCapNhapSV.setBackground(new java.awt.Color(0, 153, 255));
        btnCapNhapSV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhapSV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Update.png"))); // NOI18N
        btnCapNhapSV.setText("Cập nhật");
        btnCapNhapSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhapSVActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit24.png"))); // NOI18N
        jLabel4.setText("CẬP NHẬT SINH VIÊN");

        cbbUpdateMaKhoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CNPM", "HTTT", "KHMT", "KTMT", "KTTT", "MMT&TT" }));
        cbbUpdateMaKhoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbUpdateMaKhoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CapNhapSVDialogLayout = new javax.swing.GroupLayout(CapNhapSVDialog.getContentPane());
        CapNhapSVDialog.getContentPane().setLayout(CapNhapSVDialogLayout);
        CapNhapSVDialogLayout.setHorizontalGroup(
            CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhapSVDialogLayout.createSequentialGroup()
                .addGroup(CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CapNhapSVDialogLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUpdateMssv)
                            .addComponent(lblUpdateHoTen)
                            .addComponent(lblUpdateGioiTinh)
                            .addComponent(lblUpdateNgaySinh)
                            .addComponent(lblUpdateQueQuan)
                            .addComponent(lblUpdateMaKhoa)
                            .addComponent(lblUpdateMaNganh)
                            .addComponent(lblUpdateKhoaHoc))
                        .addGap(41, 41, 41)
                        .addGroup(CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUpdateQueQuan)
                            .addComponent(txtUpdateHoTen)
                            .addComponent(txtUpdateNgaySinh)
                            .addGroup(CapNhapSVDialogLayout.createSequentialGroup()
                                .addGroup(CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbUpdateKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(CapNhapSVDialogLayout.createSequentialGroup()
                                        .addComponent(RadioUpdateNam)
                                        .addGap(18, 18, 18)
                                        .addComponent(RadioUpdateNu))
                                    .addComponent(txtUpdateMssv, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbbUpdateMaKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbbUpdateMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(CapNhapSVDialogLayout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(btnCapNhapSV))
                    .addGroup(CapNhapSVDialogLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jLabel4)))
                .addGap(35, 35, 35))
        );
        CapNhapSVDialogLayout.setVerticalGroup(
            CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhapSVDialogLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
                .addGap(19, 19, 19)
                .addGroup(CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdateMssv)
                    .addComponent(txtUpdateMssv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdateHoTen)
                    .addComponent(txtUpdateHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdateGioiTinh)
                    .addComponent(RadioUpdateNam)
                    .addComponent(RadioUpdateNu))
                .addGap(18, 18, 18)
                .addGroup(CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUpdateNgaySinh)
                    .addComponent(txtUpdateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdateQueQuan)
                    .addComponent(txtUpdateQueQuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdateMaKhoa)
                    .addComponent(cbbUpdateMaKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdateMaNganh)
                    .addComponent(cbbUpdateMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CapNhapSVDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdateKhoaHoc)
                    .addComponent(cbbUpdateKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCapNhapSV)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        SinhVienFrame.setTitle("Sinh viên");
        SinhVienFrame.setResizable(false);

        lblMSSV.setText("MSSV:");

        txtMSSV.setEditable(false);

        lblHoTenSV.setText("Họ tên:");

        txtHoTenSV.setEditable(false);

        lblMaKhoaSV.setText("Mã khoa:");

        txtMaKhoaSV.setEditable(false);
        txtMaKhoaSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKhoaSVActionPerformed(evt);
            }
        });

        lblMaNganhSV.setText("Mã ngành:");

        txtMaNganhSV.setEditable(false);

        lblNgaySinh.setText("Ngày sinh:");

        lblQueQuan.setText("Quê quán:");

        lblKhoaHoc.setText("Khóa hoc:");

        txtKhoaHoc.setEditable(false);

        javax.swing.GroupLayout ShowThongTinSVPanelLayout = new javax.swing.GroupLayout(ShowThongTinSVPanel);
        ShowThongTinSVPanel.setLayout(ShowThongTinSVPanelLayout);
        ShowThongTinSVPanelLayout.setHorizontalGroup(
            ShowThongTinSVPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowThongTinSVPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMSSV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMSSV, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ShowThongTinSVPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ShowThongTinSVPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(ShowThongTinSVPanelLayout.createSequentialGroup()
                            .addComponent(lblMaKhoaSV)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaKhoaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(ShowThongTinSVPanelLayout.createSequentialGroup()
                            .addComponent(lblQueQuan)
                            .addGap(18, 18, 18)
                            .addComponent(txtQueQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ShowThongTinSVPanelLayout.createSequentialGroup()
                        .addComponent(lblHoTenSV)
                        .addGap(34, 34, 34)
                        .addComponent(txtHoTenSV, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(ShowThongTinSVPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ShowThongTinSVPanelLayout.createSequentialGroup()
                        .addComponent(lblKhoaHoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ShowThongTinSVPanelLayout.createSequentialGroup()
                        .addComponent(lblNgaySinh)
                        .addGap(52, 52, 52)
                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ShowThongTinSVPanelLayout.createSequentialGroup()
                        .addComponent(lblMaNganhSV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtMaNganhSV, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        ShowThongTinSVPanelLayout.setVerticalGroup(
            ShowThongTinSVPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowThongTinSVPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ShowThongTinSVPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMSSV)
                    .addComponent(txtMSSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHoTenSV)
                    .addComponent(txtHoTenSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNgaySinh)
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ShowThongTinSVPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaKhoaSV)
                    .addComponent(txtMaKhoaSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaNganhSV)
                    .addComponent(txtMaNganhSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ShowThongTinSVPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQueQuan)
                    .addComponent(txtQueQuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKhoaHoc)
                    .addComponent(txtKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblDanhSachHPSV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HP", "Tên HP", "Sô Tín Chỉ", "Học Kỳ", "Năm Học", "Điểm Số", "Điểm Chữ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tblDanhSachHPSV);

        lblDiemTBSV.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        lblDiemTBSV.setText("Điểm TB:");

        txtDiemTBSV.setEditable(false);

        javax.swing.GroupLayout ShowDiemTBSVLayout = new javax.swing.GroupLayout(ShowDiemTBSV);
        ShowDiemTBSV.setLayout(ShowDiemTBSVLayout);
        ShowDiemTBSVLayout.setHorizontalGroup(
            ShowDiemTBSVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowDiemTBSVLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDiemTBSV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiemTBSV, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        ShowDiemTBSVLayout.setVerticalGroup(
            ShowDiemTBSVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowDiemTBSVLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(ShowDiemTBSVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiemTBSV)
                    .addComponent(txtDiemTBSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        btnXoaHP.setBackground(new java.awt.Color(0, 153, 255));
        btnXoaHP.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        btnXoaHP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/xoahp.png"))); // NOI18N
        btnXoaHP.setText("Xóa học phần");
        btnXoaHP.setMaximumSize(new java.awt.Dimension(112, 22));
        btnXoaHP.setMinimumSize(new java.awt.Dimension(112, 22));
        btnXoaHP.setPreferredSize(new java.awt.Dimension(112, 22));
        btnXoaHP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHPActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("DANH SÁCH HỌC PHẦN ");

        tblDSHP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HP", "Tên HP", "Số tín chỉ"
            }
        ));
        jScrollPane8.setViewportView(tblDSHP);

        cbbHocKy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "Hè" }));
        cbbHocKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbHocKyActionPerformed(evt);
            }
        });

        cbbNamHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2022", "2023", "2024" }));

        btnDSHPThemHP.setBackground(new java.awt.Color(0, 153, 255));
        btnDSHPThemHP.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        btnDSHPThemHP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/themhocPhan.png"))); // NOI18N
        btnDSHPThemHP.setText("Thêm học phần");
        btnDSHPThemHP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDSHPThemHPActionPerformed(evt);
            }
        });

        btnDangXuat1.setBackground(new java.awt.Color(153, 153, 255));
        btnDangXuat1.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        btnDangXuat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout-icon-16.png"))); // NOI18N
        btnDangXuat1.setText("Đăng xuất");
        btnDangXuat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuat1ActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Edu.png"))); // NOI18N

        javax.swing.GroupLayout SinhVienFrameLayout = new javax.swing.GroupLayout(SinhVienFrame.getContentPane());
        SinhVienFrame.getContentPane().setLayout(SinhVienFrameLayout);
        SinhVienFrameLayout.setHorizontalGroup(
            SinhVienFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SinhVienFrameLayout.createSequentialGroup()
                .addGap(418, 418, 418)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDangXuat1)
                .addGap(79, 79, 79))
            .addGroup(SinhVienFrameLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(SinhVienFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SinhVienFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addComponent(jScrollPane6)
                        .addComponent(ShowThongTinSVPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SinhVienFrameLayout.createSequentialGroup()
                        .addComponent(ShowDiemTBSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaHP, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(SinhVienFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SinhVienFrameLayout.createSequentialGroup()
                        .addComponent(cbbHocKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbbNamHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDSHPThemHP)))
                .addGap(38, 38, 38))
        );
        SinhVienFrameLayout.setVerticalGroup(
            SinhVienFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SinhVienFrameLayout.createSequentialGroup()
                .addGroup(SinhVienFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SinhVienFrameLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btnDangXuat1))
                    .addGroup(SinhVienFrameLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel7))
                    .addGroup(SinhVienFrameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SinhVienFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(SinhVienFrameLayout.createSequentialGroup()
                        .addComponent(ShowThongTinSVPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(SinhVienFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ShowDiemTBSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SinhVienFrameLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnXoaHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SinhVienFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDSHPThemHP)
                        .addComponent(cbbNamHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbbHocKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        delMenuItem.setText("Xóa");
        delMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delMenuItemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(delMenuItem);

        updateMenuItem.setText("Sửa");
        updateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateMenuItemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(updateMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý sinh viên");
        setBackground(new java.awt.Color(102, 153, 255));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTieude.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTieude.setForeground(new java.awt.Color(255, 255, 255));
        lblTieude.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTieude.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Studen-16.png"))); // NOI18N
        lblTieude.setText("QUẢN LÝ SINH VIÊN");
        getContentPane().add(lblTieude, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 670, -1));

        btnDangXuat.setBackground(new java.awt.Color(153, 153, 255));
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout-icon-16.png"))); // NOI18N
        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        getContentPane().add(btnDangXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, -1, -1));

        TabPane.setBackground(new java.awt.Color(0, 153, 255));
        TabPane.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        tblDanhSachSV.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        tblDanhSachSV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MSSV", "Họ tên", "Giới tính", "Ngày sinh", "Quê quán", "Mã khoa", "Mã ngành", "Khóa học"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachSV.setSelectionBackground(new java.awt.Color(0, 153, 204));
        tblDanhSachSV.setShowGrid(true);
        jScrollPane1.setViewportView(tblDanhSachSV);
        tblDanhSachSV.getAccessibleContext().setAccessibleName("");

        btnThem.setBackground(new java.awt.Color(0, 153, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/ThemSv.png"))); // NOI18N
        btnThem.setText("Thêm ");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnCapNhap.setBackground(new java.awt.Color(0, 153, 255));
        btnCapNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Update.png"))); // NOI18N
        btnCapNhap.setText("Cập nhập");
        btnCapNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhapActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 153, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnTimKiem.setBackground(new java.awt.Color(0, 153, 255));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search-icon-16.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        cbbSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MSSV", "Họ Tên", "Ngày Sinh", "Quê Quán", "Mã Khoa", "Mã Ngành" }));
        cbbSapXep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSapXepActionPerformed(evt);
            }
        });

        btnSapXep.setBackground(new java.awt.Color(0, 153, 255));
        btnSapXep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/SapXep.png"))); // NOI18N
        btnSapXep.setText("Sắp xếp");
        btnSapXep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSapXepActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(0, 153, 255));
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnInDanhSach.setBackground(new java.awt.Color(0, 153, 255));
        btnInDanhSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/xls.png"))); // NOI18N
        btnInDanhSach.setText("In danh sách");
        btnInDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInDanhSachActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ThongTinSVLayout = new javax.swing.GroupLayout(ThongTinSV);
        ThongTinSV.setLayout(ThongTinSVLayout);
        ThongTinSVLayout.setHorizontalGroup(
            ThongTinSVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThongTinSVLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(ThongTinSVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(ThongTinSVLayout.createSequentialGroup()
                        .addGroup(ThongTinSVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ThongTinSVLayout.createSequentialGroup()
                                .addComponent(cbbSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSapXep)
                                .addGap(31, 31, 31)
                                .addComponent(btnRefresh)
                                .addGap(29, 29, 29)
                                .addComponent(btnInDanhSach))
                            .addGroup(ThongTinSVLayout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addGap(18, 18, 18)
                                .addComponent(btnCapNhap)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoa)
                                .addGap(168, 168, 168)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(btnTimKiem)))
                        .addGap(0, 135, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ThongTinSVLayout.setVerticalGroup(
            ThongTinSVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ThongTinSVLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(ThongTinSVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnCapNhap)
                    .addComponent(btnXoa)
                    .addComponent(btnTimKiem)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(ThongTinSVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSapXep)
                    .addComponent(btnRefresh)
                    .addComponent(btnInDanhSach))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TabPane.addTab("Thông tin sinh viên", new javax.swing.ImageIcon(getClass().getResource("/icon/graduating-student.png")), ThongTinSV); // NOI18N

        tblDanhSachSVNganh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MSSV", "Họ tên", "Giới tính", "Ngày sinh", "Quê quán", "Mã Khoa", "Mã Ngành", "Khóa học"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachSVNganh.setSelectionBackground(new java.awt.Color(0, 153, 204));
        jScrollPane3.setViewportView(tblDanhSachSVNganh);

        tblTongSoSVKhoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên khoa", "Tổng sinh viên"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTongSoSVKhoa.setSelectionBackground(new java.awt.Color(0, 153, 204));
        tblTongSoSVKhoa.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblTongSoSVKhoaPropertyChange(evt);
            }
        });
        jScrollPane4.setViewportView(tblTongSoSVKhoa);

        tblNganh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Khoa", "Tên Khoa", "Mã Ngành", "Tên Ngành"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNganh.setSelectionBackground(new java.awt.Color(0, 153, 204));
        tblNganh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNganhMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblNganh);

        javax.swing.GroupLayout QuanLyKhoaLayout = new javax.swing.GroupLayout(QuanLyKhoa);
        QuanLyKhoa.setLayout(QuanLyKhoaLayout);
        QuanLyKhoaLayout.setHorizontalGroup(
            QuanLyKhoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QuanLyKhoaLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(QuanLyKhoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(QuanLyKhoaLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)))
                .addContainerGap())
        );
        QuanLyKhoaLayout.setVerticalGroup(
            QuanLyKhoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, QuanLyKhoaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(QuanLyKhoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        TabPane.addTab("Quản lý Khoa", new javax.swing.ImageIcon(getClass().getResource("/icon/analysis.png")), QuanLyKhoa); // NOI18N

        QuanLyDiem.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        tblDanhSachHP.setBackground(new java.awt.Color(102, 102, 102));
        tblDanhSachHP.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        tblDanhSachHP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HP", "Tên HP", "Số Tín Chỉ", "Học Kỳ", "Năm Học", "Điểm Số", "Điểm Chữ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachHP.setSelectionBackground(new java.awt.Color(0, 153, 204));
        tblDanhSachHP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachHPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDanhSachHP);

        txtNhapMSSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNhapMSSVActionPerformed(evt);
            }
        });

        lblNhapMSSV.setText("Nhập mã số sinh viên");

        btnTimMSSV.setBackground(new java.awt.Color(0, 153, 255));
        btnTimMSSV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loupe(resize).png"))); // NOI18N
        btnTimMSSV.setText("Tìm");
        btnTimMSSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimMSSVActionPerformed(evt);
            }
        });

        lblShowHoTen.setText("Họ tên:");

        lblShowMaNganh.setText("Mã ngành:");

        lblShowMaKhoa.setText("Mã khoa:");

        javax.swing.GroupLayout ShowThongTinLayout = new javax.swing.GroupLayout(ShowThongTin);
        ShowThongTin.setLayout(ShowThongTinLayout);
        ShowThongTinLayout.setHorizontalGroup(
            ShowThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowThongTinLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblShowHoTen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtShowHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblShowMaNganh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtShowMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblShowMaKhoa)
                .addGap(18, 18, 18)
                .addComponent(txtShowMaKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ShowThongTinLayout.setVerticalGroup(
            ShowThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowThongTinLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(ShowThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtShowHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShowHoTen)
                    .addComponent(lblShowMaNganh)
                    .addComponent(txtShowMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShowMaKhoa)
                    .addComponent(txtShowMaKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        lblShowDiemTB.setText("Điểm TB:");

        txtShowDiemTB.setEditable(false);

        btnInBangDiem.setBackground(new java.awt.Color(0, 153, 255));
        btnInBangDiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/printer.png"))); // NOI18N
        btnInBangDiem.setText("In bảng điểm");
        btnInBangDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInBangDiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ShowDiemTBLayout = new javax.swing.GroupLayout(ShowDiemTB);
        ShowDiemTB.setLayout(ShowDiemTBLayout);
        ShowDiemTBLayout.setHorizontalGroup(
            ShowDiemTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowDiemTBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblShowDiemTB)
                .addGap(18, 18, 18)
                .addComponent(txtShowDiemTB, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(btnInBangDiem)
                .addGap(24, 24, 24))
        );
        ShowDiemTBLayout.setVerticalGroup(
            ShowDiemTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ShowDiemTBLayout.createSequentialGroup()
                .addGap(0, 27, Short.MAX_VALUE)
                .addGroup(ShowDiemTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnInBangDiem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(ShowDiemTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblShowDiemTB)
                        .addComponent(txtShowDiemTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        lblUpdateMaHP.setText("Mã học phần:");

        lblUpdateTenHP.setText("Tên học phần:");

        lblUpdateHocKy.setText("Học kỳ:");

        lblUpdateNamHoc.setText("Năm học:");

        lblUpdateDiem.setText("Điểm:");

        javax.swing.GroupLayout CapNhapHPLayout = new javax.swing.GroupLayout(CapNhapHP);
        CapNhapHP.setLayout(CapNhapHPLayout);
        CapNhapHPLayout.setHorizontalGroup(
            CapNhapHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhapHPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CapNhapHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CapNhapHPLayout.createSequentialGroup()
                        .addGroup(CapNhapHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUpdateNamHoc)
                            .addComponent(lblUpdateDiem))
                        .addGap(35, 35, 35)
                        .addGroup(CapNhapHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUpdateNamHoc, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(txtUpdateDiem)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CapNhapHPLayout.createSequentialGroup()
                        .addGroup(CapNhapHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUpdateMaHP)
                            .addComponent(lblUpdateTenHP)
                            .addComponent(lblUpdateHocKy))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(CapNhapHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUpdateHocKy, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(txtUpdateTenHP)
                            .addComponent(txtUpdateMaHP))))
                .addContainerGap())
        );
        CapNhapHPLayout.setVerticalGroup(
            CapNhapHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhapHPLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(CapNhapHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdateMaHP)
                    .addComponent(txtUpdateMaHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CapNhapHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdateTenHP)
                    .addComponent(txtUpdateTenHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CapNhapHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdateHocKy)
                    .addComponent(txtUpdateHocKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CapNhapHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdateNamHoc)
                    .addComponent(txtUpdateNamHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CapNhapHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdateDiem)
                    .addComponent(txtUpdateDiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        btnCapNhapHP.setBackground(new java.awt.Color(0, 153, 255));
        btnCapNhapHP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnCapNhapHP.setText("Cập nhập");
        btnCapNhapHP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhapHPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout QuanLyDiemLayout = new javax.swing.GroupLayout(QuanLyDiem);
        QuanLyDiem.setLayout(QuanLyDiemLayout);
        QuanLyDiemLayout.setHorizontalGroup(
            QuanLyDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QuanLyDiemLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(QuanLyDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(QuanLyDiemLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CapNhapHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(QuanLyDiemLayout.createSequentialGroup()
                        .addGroup(QuanLyDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(QuanLyDiemLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(txtNhapMSSV, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnTimMSSV))
                            .addComponent(ShowDiemTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ShowThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNhapMSSV))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                        .addComponent(btnCapNhapHP)
                        .addGap(65, 65, 65))))
        );
        QuanLyDiemLayout.setVerticalGroup(
            QuanLyDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, QuanLyDiemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNhapMSSV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(QuanLyDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNhapMSSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimMSSV))
                .addGap(30, 30, 30)
                .addGroup(QuanLyDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(QuanLyDiemLayout.createSequentialGroup()
                        .addComponent(CapNhapHP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCapNhapHP))
                    .addGroup(QuanLyDiemLayout.createSequentialGroup()
                        .addComponent(ShowThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ShowDiemTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(120, 120, 120))
        );

        TabPane.addTab("Quản lý điểm", new javax.swing.ImageIcon(getClass().getResource("/icon/exam.png")), QuanLyDiem); // NOI18N

        getContentPane().add(TabPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 870, 500));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/background.jpg"))); // NOI18N
        jLabel6.setText("jLabel6");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 590));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCapNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhapActionPerformed

        int row = tblDanhSachSV.getSelectedRow();
        if (row == -1)
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn 1 sinh viên!");
        else {
            txtUpdateMssv.setText(tblDanhSachSV.getModel().getValueAt(row, 0).toString());
            txtUpdateMssv.setEnabled(false);
            txtUpdateHoTen.setText(tblDanhSachSV.getModel().getValueAt(row, 1).toString());
            if (tblDanhSachSV.getModel().getValueAt(row, 2).toString().equals("Nam")) {
                RadioUpdateNam.setSelected(true);
            } else {
                RadioUpdateNu.setSelected(true);
            }

            txtUpdateNgaySinh.setText(tblDanhSachSV.getModel().getValueAt(row, 3).toString());
            txtUpdateQueQuan.setText(tblDanhSachSV.getModel().getValueAt(row, 4).toString());
            if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("HTTT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("HTTT")){
                cbbUpdateMaKhoa.setSelectedItem("HTTT");
                cbbUpdateMaNganh.setSelectedItem("HTTT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("HTTT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("TMDT")){
                cbbUpdateMaKhoa.setSelectedItem("HTTT");
                cbbUpdateMaNganh.setSelectedItem("TMDT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("MMT&TT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("ATTT")){
                cbbUpdateMaKhoa.setSelectedItem("MMT&TT");
                cbbUpdateMaNganh.setSelectedItem("ATTT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("MMT&TT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("MMTT")){
                cbbUpdateMaKhoa.setSelectedItem("MMT&TT");
                cbbUpdateMaNganh.setSelectedItem("MMTT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("KHMT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("KHMT")){
                cbbUpdateMaKhoa.setSelectedItem("KHMT");
                cbbUpdateMaNganh.setSelectedItem("KHMT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("KHMT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("KHNT")){
                cbbUpdateMaKhoa.setSelectedItem("KHMT");
                cbbUpdateMaNganh.setSelectedItem("KHNT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("KTMT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("KTMT")){
                cbbUpdateMaKhoa.setSelectedItem("KTMT");
                cbbUpdateMaNganh.setSelectedItem("KTMT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("CNPM")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("KTPM")){
                cbbUpdateMaKhoa.setSelectedItem("CNPM");
                cbbUpdateMaNganh.setSelectedItem("KTPM");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("KTTT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("CNTT")){
                cbbUpdateMaKhoa.setSelectedItem("KTTT");
                cbbUpdateMaNganh.setSelectedItem("CNTT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("KTTT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("KHDL")){
                cbbUpdateMaKhoa.setSelectedItem("KTTT");
                cbbUpdateMaNganh.setSelectedItem("KHDL");    
            }
            
            if (tblDanhSachSV.getModel().getValueAt(row, 7).toString().equals("16")) {
                cbbUpdateKhoaHoc.setSelectedIndex(0);
            } else if (tblDanhSachSV.getModel().getValueAt(row, 7).toString().equals("15")) {
                cbbUpdateKhoaHoc.setSelectedIndex(1);
            } else if (tblDanhSachSV.getModel().getValueAt(row, 7).toString().equals("14")) {
                cbbUpdateKhoaHoc.setSelectedIndex(2);
            } else if (tblDanhSachSV.getModel().getValueAt(row, 7).toString().equals("13")) {
                cbbUpdateKhoaHoc.setSelectedIndex(3);
            }
            CapNhapSVDialog.setVisible(true);
            CapNhapSVDialog.setSize(350,450);
            CapNhapSVDialog.setResizable(false);
            CapNhapSVDialog.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_btnCapNhapActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        ThemSVDialog.setVisible(true);
        ThemSVDialog.setSize(350,450);
        ThemSVDialog.setResizable(false);
        ThemSVDialog.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int row = tblDanhSachSV.getSelectedRow();
        if (row == -1)
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn 1 sinh viên!");
        else {
            String del = tblDanhSachSV.getModel().getValueAt(row, 0).toString();
            new ConnectDB().deleteAllHPSV(del);
            new ConnectDB().delete(del);
            new ConnectDB().deleteUser(del);
            model.removeRow(row);
            for (int g = 0; g < list.size() - 1; g++) {
                model.removeRow(0);
            }
            list = new ConnectDB().getDanhSachSinhVien();
            model = (DefaultTableModel) tblDanhSachSV.getModel();
            model.setColumnIdentifiers(new Object[]{
                "MSSV", "Họ tên", "Giới tính", "Ngày sinh", "Địa chỉ", "Mã khoa", "Mã ngành", "Khóa học"
            });
            reloadTable();
            JOptionPane.showMessageDialog(rootPane, "Xóa thành công!");
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSVActionPerformed
        // TODO add your handling code here:
        SinhVien s = new SinhVien();
        s.setMssv(txtThemMssv.getText());
        s.setHoTen(txtThemHoTen.getText());
        RadioThemNam.setActionCommand("Nam");
        RadioThemNu.setActionCommand("Nu");
        s.setGioiTinh(GioiTinhGroup.getSelection().getActionCommand());
        try {
            s.setNgaySinh(new SimpleDateFormat("dd/MM/yyyy").parse(txtThemNgaySinh.getText()));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        s.setQueQuan(txtThemQueQuan.getText());
        s.setMaKhoa(cbbThemMaKhoa.getSelectedItem().toString());
        s.setMaNganh(cbbThemMaNganh.getSelectedItem().toString());
        s.setKhoaHoc(Integer.parseInt(cbbThemKhoaHoc.getSelectedItem().toString()));
        if (new ConnectDB().addStudent(s) && new ConnectDB().createUser(s)) {
            JOptionPane.showMessageDialog(rootPane, "Thêm thành công!");
            list.add(s);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Đã có lỗi xảy ra!");
        }
        model = (DefaultTableModel) tblDanhSachSV.getModel();
        model.setRowCount(0);
        reloadTable();
    }//GEN-LAST:event_btnThemSVActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        String hoTen = txtTimKiem.getText();
        for (int g = 0; g < list.size(); g++) {
            model.removeRow(0);
        }
        list = new ConnectDB().tiemKiemSinhVien(hoTen);
        model = (DefaultTableModel) tblDanhSachSV.getModel();
        model.setColumnIdentifiers(new Object[]{
            "MSSV", "Họ tên", "Giới tính", "Ngày sinh", "Quê quán", "Mã khoa", "Mã ngành", "Khóa học"
        });
        reloadTable();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnInDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInDanhSachActionPerformed
        exportExcel(tblDanhSachSV);
    }//GEN-LAST:event_btnInDanhSachActionPerformed

    private void btnSapXepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSapXepActionPerformed
        for (int g = 0; g < list.size(); g++) {
            model.removeRow(0);
        }
        if (cbbSapXep.getSelectedItem().toString().equals("MSSV")) {
            cbbSapXep.setActionCommand("mssv");
        } else if (cbbSapXep.getSelectedItem().toString().equals("Họ Tên")) {
            cbbSapXep.setActionCommand("hoTen");
        } else if (cbbSapXep.getSelectedItem().toString().equals("Ngày Sinh")) {
            cbbSapXep.setActionCommand("ngaySinh");
        } else if (cbbSapXep.getSelectedItem().toString().equals("Quê Quán")) {
            cbbSapXep.setActionCommand("queQuan");
        } else if (cbbSapXep.getSelectedItem().toString().equals("Mã Khoa")) {
            cbbSapXep.setActionCommand("maKhoa");
        } else {
            cbbSapXep.setActionCommand("maNganh");
        }
        String t = cbbSapXep.getActionCommand();
        list = new ConnectDB().sapXepSV(t);
        model = (DefaultTableModel) tblDanhSachSV.getModel();
        model.setColumnIdentifiers(new Object[]{
            "MSSV", "Họ tên", "Giới tính", "Ngày sinh", "Quê quán", "Mã khoa", "Mã ngành", "Khóa học"
        });
        reloadTable();
    }//GEN-LAST:event_btnSapXepActionPerformed

    private void cbbSapXepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSapXepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbSapXepActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        for (int g = 0; g < list.size(); g++) {
            model.removeRow(0);
        }
        list = new ConnectDB().getDanhSachSinhVien();
        model = (DefaultTableModel) tblDanhSachSV.getModel();
        model.setColumnIdentifiers(new Object[]{
            "MSSV", "Họ tên", "Giới tính", "Ngày sinh", "Quê quán", "Mã khoa", "Mã ngành", "Khóa học"
        });
        reloadTable();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCapNhapSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhapSVActionPerformed
        SinhVien t = new SinhVien();
        t.setMssv(txtUpdateMssv.getText());
        t.setHoTen(txtUpdateHoTen.getText());
        RadioUpdateNam.setActionCommand("Nam");
        RadioUpdateNu.setActionCommand("Nu");
        t.setGioiTinh(GioiTinhGroup.getSelection().getActionCommand());
        try {
            t.setNgaySinh(new SimpleDateFormat("yyyy-MM-dd").parse(txtUpdateNgaySinh.getText()));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        t.setQueQuan(txtUpdateQueQuan.getText());
        t.setMaKhoa(cbbUpdateMaKhoa.getSelectedItem().toString());
        t.setMaNganh(cbbUpdateMaNganh.getSelectedItem().toString());
        t.setKhoaHoc(Integer.parseInt(cbbUpdateKhoaHoc.getSelectedItem().toString()));
        if (new ConnectDB().update(t)) {
            JOptionPane.showMessageDialog(rootPane, "Cập nhật thành công!");
            //list.set(row, t);
            CapNhapSVDialog.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Cập nhật thất bại!");
            CapNhapSVDialog.setVisible(false);
        }
        model = (DefaultTableModel) tblDanhSachSV.getModel();
        model.fireTableDataChanged();
        model.setRowCount(0);
        reloadTable();
    }//GEN-LAST:event_btnCapNhapSVActionPerformed

    private void tblNganhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNganhMouseClicked
        int row = tblNganh.getSelectedRow();
        for (int g = 0; g < listSVN.size(); g++) {
            modelSVN.removeRow(0);
        }
        String mk, mn;
        //System.out.println();
        mk = tblNganh.getModel().getValueAt(row, 0).toString();
        mn = tblNganh.getModel().getValueAt(row, 2).toString();
        listSVN = new ConnectDB().selectTableNganh(mk, mn);
        modelSVN = (DefaultTableModel) tblDanhSachSVNganh.getModel();
        modelSVN.setColumnIdentifiers(new Object[]{
            "MSSV", "Họ tên", "Giới tính", "Ngày sinh", "Quê quán", "Mã khoa", "Mã ngành", "Khóa học"
        });
        reloadTableSVNganh();
    }//GEN-LAST:event_tblNganhMouseClicked

    private void btnTimMSSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimMSSVActionPerformed
        for (int g = 0; g < list.size(); g++) {
            model.removeRow(0);
        }
        list = new ConnectDB().getDanhSachSinhVien();
        model = (DefaultTableModel) tblDanhSachSV.getModel();
        model.setColumnIdentifiers(new Object[]{
            "MSSV", "Họ tên", "Giới tính", "Ngày sinh", "Quê quán", "Mã khoa", "Mã ngành", "Khóa học"
        });
        reloadTable();
        reloadTB();
    }//GEN-LAST:event_btnTimMSSVActionPerformed

    private void tblDanhSachHPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachHPMouseClicked
        int row = tblDanhSachHP.getSelectedRow();
        txtUpdateMaHP.setText(tblDanhSachHP.getModel().getValueAt(row, 0).toString());
        txtUpdateMaHP.setEnabled(false);
        txtUpdateTenHP.setText(tblDanhSachHP.getModel().getValueAt(row, 1).toString());
        txtUpdateTenHP.setEnabled(false);
        txtUpdateHocKy.setText(tblDanhSachHP.getModel().getValueAt(row, 3).toString());
        txtUpdateHocKy.setEnabled(false);
        txtUpdateNamHoc.setText(tblDanhSachHP.getModel().getValueAt(row, 4).toString());
        txtUpdateNamHoc.setEnabled(false);
        txtUpdateDiem.setText(tblDanhSachHP.getModel().getValueAt(row, 5).toString());
    }//GEN-LAST:event_tblDanhSachHPMouseClicked

    private void btnCapNhapHPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhapHPActionPerformed
        if (Integer.parseInt(txtUpdateDiem.getText()) >= 0 && Integer.parseInt(txtUpdateDiem.getText()) <= 10) {
            int row = tblDanhSachHP.getSelectedRow();
            KetQua kq = new KetQua();
            kq.setMssv(txtNhapMSSV.getText());
            kq.setMaHP(txtUpdateMaHP.getText());
            kq.setTenHP(tblDanhSachHP.getModel().getValueAt(row, 2).toString());
            kq.setHocKy(Integer.parseInt(tblDanhSachHP.getModel().getValueAt(row, 3).toString()));
            kq.setNamHoc(Integer.parseInt(tblDanhSachHP.getModel().getValueAt(row, 4).toString()));
            kq.setDiem(Integer.parseInt(txtUpdateDiem.getText()));

            if (new ConnectDB().updateDiem(kq)) {
                JOptionPane.showMessageDialog(rootPane, "Cập nhật thành công!");
                reloadTB();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Cập nhật thất bại!");
            }
        } else
            JOptionPane.showMessageDialog(rootPane, "Nhập sai điểm!");
    }//GEN-LAST:event_btnCapNhapHPActionPerformed

    private void btnXoaHPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHPActionPerformed
        int row = tblDanhSachHPSV.getSelectedRow();
        if (row == -1)
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn 1 học phần để xóa!");
        else {
            String mssv = txtMSSV.getText();
            String maHP = tblDanhSachHPSV.getModel().getValueAt(row, 0).toString();
            new ConnectDB().deleteHP(mssv, maHP);
            modelKQ.removeRow(row);
            for (int g = 0; g < listKQ.size() - 1; g++) {
                modelKQ.removeRow(0);
            }
            reloadTBSV(mssv);
            JOptionPane.showMessageDialog(rootPane,"Xóa học phần thành công");
        }
    }//GEN-LAST:event_btnXoaHPActionPerformed

    private void tblTongSoSVKhoaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblTongSoSVKhoaPropertyChange
    }//GEN-LAST:event_tblTongSoSVKhoaPropertyChange

    private void txtMaKhoaSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKhoaSVActionPerformed
    }//GEN-LAST:event_txtMaKhoaSVActionPerformed

    private void btnInBangDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInBangDiemActionPerformed
        Document document = new Document(PageSize.A4);
        String filename = "Bang diem sinh vien";
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("reports/" + filename + ".pdf"));
            document.open();
            document.addAuthor("Dương Văn Nhật Long");
            document.addCreationDate();
            document.addCreator("QuanLySinhVien");
            document.addTitle("Bảng điểm sinh viên");
            document.addSubject("Bảng điểm sinh viên");
            File filefontTieude = new File("fonts/vuArialBold.ttf");
            BaseFont bfTieude = BaseFont.createFont(filefontTieude.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontTieuDe1 = new Font(bfTieude, 16);
            fontTieuDe1.setColor(BaseColor.BLUE);
            Font fontTieude2 = new Font(bfTieude, 13);
            fontTieude2.setColor(BaseColor.BLUE);
            Font fontTieude3 = new Font(bfTieude, 13);
            Font fontTieude4 = new Font(bfTieude, 12);

            File filefontNoiDung = new File("fonts/vuArial.ttf");
            BaseFont bfNoiDung = BaseFont.createFont(filefontNoiDung.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontNoiDung1 = new Font(bfNoiDung, 13);
            Font fontNoiDung2 = new Font(bfNoiDung, 12);
            Font fontNoiDung3 = new Font(bfNoiDung, 11);
            Image logo = Image.getInstance("images/logoUIT.png");
            logo.setAbsolutePosition(80, 750);
            logo.scaleAbsolute(50, 50);
            document.add(logo);
            Paragraph prgTenPK = new Paragraph("Trường Đại học Công Nghệ Thông Tin - ĐHQG.HCM", fontTieude2);
            prgTenPK.setIndentationLeft(100);
            document.add(prgTenPK);
            Paragraph prgDiaChiPK = new Paragraph("Khu phố 6,phường Linh Trung,Tp.Thủ Đức,Tp.Hồ Chí Minh", fontNoiDung2);
            prgDiaChiPK.setIndentationLeft(100);
            document.add(prgDiaChiPK);
            Paragraph prgSoDTPK = new Paragraph("Số Điện Thoại:02837252002", fontNoiDung2);
            prgSoDTPK.setIndentationLeft(100);
            document.add(prgSoDTPK);
            Paragraph prgTieuDe = new Paragraph("BẢNG ĐIỂM SINH VIÊN", fontTieuDe1);
            prgTieuDe.setAlignment(Element.ALIGN_CENTER);
            prgTieuDe.setSpacingBefore(10);
            prgTieuDe.setSpacingAfter(10);
            document.add(prgTieuDe);
            try {
                Connection con = new ConnectDB().createConn();
                String strSQL = "Select * from SINHVIEN WHERE MSSV=?";
                PreparedStatement pres = con.prepareStatement(strSQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                pres.setString(1, txtNhapMSSV.getText());
                ResultSet rs = pres.executeQuery();
                if (rs.first()) {
                    List listTTSV = new List(List.UNORDERED);
                    listTTSV.add(new ListItem("Họ tên sinh viên: " + rs.getString("HoTen"), fontNoiDung1));
                    listTTSV.add(new ListItem("Mã số sinh viên: " + rs.getString("MSSV"), fontNoiDung1));
                    listTTSV.add(new ListItem("Giới tính: " + rs.getString("GioiTinh"), fontNoiDung1));
                    String[] arrayNgaySinh = rs.getString("NgaySinh").split("-");
                    String ngay = arrayNgaySinh[2];
                    String thang = arrayNgaySinh[1];
                    String nam = arrayNgaySinh[0];
                    listTTSV.add(new ListItem("Ngày sinh: " + ngay + "/" + thang + "/" + nam, fontNoiDung1));
                    listTTSV.add(new ListItem("Quê quán: " + rs.getString("QueQuan"), fontNoiDung1));
                    listTTSV.add(new ListItem("Mã Khoa: " + rs.getString("MaKhoa"), fontNoiDung1));
                    listTTSV.add(new ListItem("Mã Ngành: " + rs.getString("MaNganh"), fontNoiDung1));
                    listTTSV.add(new ListItem("Khóa học: " + rs.getString("KhoaHoc"), fontNoiDung1));
                    document.add(listTTSV);
                }

            } catch (SQLException e) {
                System.out.println(e);
                System.out.println("Lỗi");
            }
            Paragraph prgDichVu = new Paragraph("Danh sách học phần:", fontTieude3);
            prgDichVu.setSpacingBefore(10);
            prgDichVu.setSpacingAfter(10);
            document.add(prgDichVu);
            PdfPTable tableKQ = new PdfPTable(6);
            tableKQ.setWidthPercentage(80);
            tableKQ.setSpacingBefore(10);
            tableKQ.setSpacingAfter(10);
            float[] tableKQ_columWidths = {80, 180, 80, 70, 80, 80};
            tableKQ.setWidths(tableKQ_columWidths);

            PdfPCell cellTDMaHP = new PdfPCell(new Paragraph("MaHP", fontTieude4));
            cellTDMaHP.setBorderColor(BaseColor.BLACK);
            cellTDMaHP.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTDMaHP.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellTDMaHP.setMinimumHeight(30);
            tableKQ.addCell(cellTDMaHP);

            PdfPCell cellTDTenHP = new PdfPCell(new Paragraph("Tên học phần", fontTieude4));
            cellTDTenHP.setBorderColor(BaseColor.BLACK);
            cellTDTenHP.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTDTenHP.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableKQ.addCell(cellTDTenHP);

            PdfPCell cellTDSoTC = new PdfPCell(new Paragraph("Số tín chỉ", fontTieude4));
            cellTDSoTC.setBorderColor(BaseColor.BLACK);
            cellTDSoTC.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTDSoTC.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableKQ.addCell(cellTDSoTC);

            PdfPCell cellTDHocKy = new PdfPCell(new Paragraph("Học kỳ", fontTieude4));
            cellTDHocKy.setBorderColor(BaseColor.BLACK);
            cellTDHocKy.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTDHocKy.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableKQ.addCell(cellTDHocKy);

            PdfPCell cellTDNamHoc = new PdfPCell(new Paragraph("Năm học", fontTieude4));
            cellTDNamHoc.setBorderColor(BaseColor.BLACK);
            cellTDNamHoc.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTDNamHoc.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableKQ.addCell(cellTDNamHoc);

            PdfPCell cellTDDiem = new PdfPCell(new Paragraph("Điểm", fontTieude4));
            cellTDDiem.setBorderColor(BaseColor.BLACK);
            cellTDDiem.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTDDiem.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableKQ.addCell(cellTDDiem);

            try {
                Connection con = new ConnectDB().createConn();
                String strSQL = "SELECT * FROM KETQUA,HOCPHAN WHERE KETQUA.MAHP=HOCPHAN.MAHP AND MSSV =?";
                PreparedStatement pres = con.prepareStatement(strSQL);
                pres.setString(1, txtNhapMSSV.getText());
                ResultSet rs = pres.executeQuery();
                while (rs.next()) {

                    PdfPCell cellMaHP = new PdfPCell(new Paragraph(rs.getString("KETQUA.MaHP"), fontNoiDung3));
                    cellMaHP.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellMaHP.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableKQ.addCell(cellMaHP);

                    PdfPCell cellTenHP = new PdfPCell(new Paragraph(rs.getString("TenHP"), fontNoiDung3));
                    cellTenHP.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellTenHP.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableKQ.addCell(cellTenHP);

                    PdfPCell cellSoTC = new PdfPCell(new Paragraph(rs.getString("SoTinChi"), fontNoiDung3));
                    cellSoTC.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellSoTC.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableKQ.addCell(cellSoTC);

                    PdfPCell cellHocKy = new PdfPCell(new Paragraph(rs.getString("HocKy"), fontNoiDung3));
                    cellHocKy.setPaddingLeft(10);
                    cellHocKy.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellHocKy.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableKQ.addCell(cellHocKy);

                    PdfPCell cellNamHoc = new PdfPCell(new Paragraph(rs.getString("NamHoc"), fontNoiDung3));
                    cellNamHoc.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellNamHoc.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableKQ.addCell(cellNamHoc);

                    PdfPCell cellDiem = new PdfPCell(new Paragraph(rs.getString("Diem"), fontNoiDung3));
                    cellDiem.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellDiem.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableKQ.addCell(cellDiem);
                }
                PdfPCell cellDiemTB = new PdfPCell(new Paragraph("Điểm trung bình", fontNoiDung3));
                cellDiemTB.setColspan(5);
                cellDiemTB.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDiemTB.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellDiemTB.setMinimumHeight(20);
                tableKQ.addCell(cellDiemTB);

                PdfPCell cellTB = new PdfPCell(new Paragraph(txtShowDiemTB.getText(), fontNoiDung3));
                cellTB.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTB.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableKQ.addCell(cellTB);

            } catch (SQLException e) {
                System.out.println(e);
                System.out.println("Lỗi");
            }
            document.add(tableKQ);

            PdfPTable tableTTNgIn = new PdfPTable(2);
            tableTTNgIn.setWidthPercentage(90);
            tableTTNgIn.setSpacingBefore(10);
            tableTTNgIn.setSpacingAfter(10);
            float[] tableTTBS_columWidths = {300, 200};
            tableTTNgIn.setWidths(tableTTBS_columWidths);

            PdfPCell cellGhiChu = new PdfPCell(new Paragraph("Ghi chú:", fontNoiDung3));
            cellGhiChu.setBorder(0);
            cellGhiChu.setRowspan(3);
            cellGhiChu.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellGhiChu.setVerticalAlignment(Element.ALIGN_TOP);
            tableTTNgIn.addCell(cellGhiChu);

            PdfPCell cellNgInDon = new PdfPCell(new Paragraph("Ký tên \n \n \n \n ", fontTieude4));
            cellNgInDon.setBorder(0);
            cellNgInDon.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellNgInDon.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableTTNgIn.addCell(cellNgInDon);

            document.add(tableTTNgIn);
            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file = new File("reports/" + filename + ".pdf");
            if (!Desktop.isDesktopSupported()) {
                System.out.println("not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if (file.exists()) {
                desktop.open(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnInBangDiemActionPerformed

    private void btnDSHPThemHPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDSHPThemHPActionPerformed
        int row = tblDSHP.getSelectedRow();
        if (row == -1)
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn 1 học phần để thêm!");
        else {
            String mahp = tblDSHP.getModel().getValueAt(row, 0).toString();
            String mssv = txtMSSV.getText();
            int namhoc = Integer.parseInt(cbbNamHoc.getSelectedItem().toString());
            if (cbbHocKy.getSelectedItem().toString().equals("1")) {
                cbbHocKy.setActionCommand("1");
            } else if (cbbHocKy.getSelectedItem().toString().equals("2")) {
                cbbHocKy.setActionCommand("2");
            } else if (cbbHocKy.getSelectedItem().toString().equals("Hè")) {
                cbbHocKy.setActionCommand("3");
            }
            int hocky = Integer.parseInt(cbbHocKy.getActionCommand());
            if (new ConnectDB().themHP(mahp, mssv, hocky, namhoc)) {
                JOptionPane.showMessageDialog(rootPane, "Thêm học phần thành công!");
                for (int g = 0; g < listKQ.size(); g++) {
                    modelKQ.removeRow(0);
                }
                reloadTBSV(mssv);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Lỗi! Môn học này đã học rồi!");
            }
        }
    }//GEN-LAST:event_btnDSHPThemHPActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        Login n = new Login();
        n.setVisible(true);
        this.setVisible(false);
        this.dispose();
        JOptionPane.showMessageDialog(rootPane, "Đăng xuất thành công !");
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnDangXuat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuat1ActionPerformed
        Login n = new Login();
        n.setVisible(true);
        SinhVienFrame.setVisible(false);
        SinhVienFrame.dispose();
        JOptionPane.showMessageDialog(rootPane, "Đăng xuất thành công !");
    }//GEN-LAST:event_btnDangXuat1ActionPerformed

    private void updateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateMenuItemActionPerformed
        int row = tblDanhSachSV.getSelectedRow();
        if (row == -1)
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn 1 sinh viên!");
        else {
            txtUpdateMssv.setText(tblDanhSachSV.getModel().getValueAt(row, 0).toString());
            txtUpdateMssv.setEnabled(false);
            txtUpdateHoTen.setText(tblDanhSachSV.getModel().getValueAt(row, 1).toString());
            if (tblDanhSachSV.getModel().getValueAt(row, 2).toString().equals("Nam")) {
                RadioUpdateNam.setSelected(true);
            } else {
                RadioUpdateNu.setSelected(true);
            }

            txtUpdateNgaySinh.setText(tblDanhSachSV.getModel().getValueAt(row, 3).toString());
            txtUpdateQueQuan.setText(tblDanhSachSV.getModel().getValueAt(row, 4).toString());       
            if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("HTTT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("HTTT")){
                cbbUpdateMaKhoa.setSelectedItem("HTTT");
                cbbUpdateMaNganh.setSelectedItem("HTTT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("HTTT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("TMDT")){
                cbbUpdateMaKhoa.setSelectedItem("HTTT");
                cbbUpdateMaNganh.setSelectedItem("TMDT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("MMT&TT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("ATTT")){
                cbbUpdateMaKhoa.setSelectedItem("MMT&TT");
                cbbUpdateMaNganh.setSelectedItem("ATTT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("MMT&TT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("MMTT")){
                cbbUpdateMaKhoa.setSelectedItem("MMT&TT");
                cbbUpdateMaNganh.setSelectedItem("MMTT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("KHMT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("KHMT")){
                cbbUpdateMaKhoa.setSelectedItem("KHMT");
                cbbUpdateMaNganh.setSelectedItem("KHMT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("KHMT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("KHNT")){
                cbbUpdateMaKhoa.setSelectedItem("KHMT");
                cbbUpdateMaNganh.setSelectedItem("KHNT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("KTMT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("KTMT")){
                cbbUpdateMaKhoa.setSelectedItem("KTMT");
                cbbUpdateMaNganh.setSelectedItem("KTMT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("CNPM")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("KTPM")){
                cbbUpdateMaKhoa.setSelectedItem("CNPM");
                cbbUpdateMaNganh.setSelectedItem("KTPM");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("KTTT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("CNTT")){
                cbbUpdateMaKhoa.setSelectedItem("KTTT");
                cbbUpdateMaNganh.setSelectedItem("CNTT");    
            }
            else if(tblDanhSachSV.getModel().getValueAt(row, 5).toString().equals("KTTT")
                    && tblDanhSachSV.getModel().getValueAt(row, 6).toString().equals("KHDL")){
                cbbUpdateMaKhoa.setSelectedItem("KTTT");
                cbbUpdateMaNganh.setSelectedItem("KHDL");    
            }
            
            if (tblDanhSachSV.getModel().getValueAt(row, 7).toString().equals("16")) {
                cbbUpdateKhoaHoc.setSelectedIndex(0);
            } else if (tblDanhSachSV.getModel().getValueAt(row, 7).toString().equals("15")) {
                cbbUpdateKhoaHoc.setSelectedIndex(1);
            } else if (tblDanhSachSV.getModel().getValueAt(row, 7).toString().equals("14")) {
                cbbUpdateKhoaHoc.setSelectedIndex(2);
            } else if (tblDanhSachSV.getModel().getValueAt(row, 7).toString().equals("13")) {
                cbbUpdateKhoaHoc.setSelectedIndex(3);
            }
            CapNhapSVDialog.setVisible(true);
            CapNhapSVDialog.setSize(350,450);
            CapNhapSVDialog.setResizable(false);
            CapNhapSVDialog.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_updateMenuItemActionPerformed

    private void delMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delMenuItemActionPerformed
        // TODO add your handling code here:
         int row = tblDanhSachSV.getSelectedRow();
        if (row == -1)
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn 1 sinh viên!");
        else {
            String del = tblDanhSachSV.getModel().getValueAt(row, 0).toString();
            new ConnectDB().delete(del);
            new ConnectDB().deleteUser(del);
            model.removeRow(row);
            for (int g = 0; g < list.size() - 1; g++) {
                model.removeRow(0);
            }
            list = new ConnectDB().getDanhSachSinhVien();
            model = (DefaultTableModel) tblDanhSachSV.getModel();
            model.setColumnIdentifiers(new Object[]{
                "MSSV", "Họ tên", "Giới tính", "Ngày sinh", "Địa chỉ", "Mã khoa", "Mã ngành", "Khóa học"
            });
            reloadTable();
        }
    }//GEN-LAST:event_delMenuItemActionPerformed

    private void txtNhapMSSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNhapMSSVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNhapMSSVActionPerformed

    private void cbbHocKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHocKyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbHocKyActionPerformed

    private void cbbThemMaKhoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbThemMaKhoaActionPerformed
        if (cbbThemMaKhoa.getSelectedItem().toString().equals("HTTT")) {
            cbbThemMaNganh.setModel(new DefaultComboBoxModel<>(new String[]{"HTTT", "TMDT"}));
        } else if (cbbThemMaKhoa.getSelectedItem().toString().equals("CNPM")) {
            cbbThemMaNganh.setModel(new DefaultComboBoxModel<>(new String[]{"KTPM"}));
        } else if (cbbThemMaKhoa.getSelectedItem().toString().equals("KHMT")) {
            cbbThemMaNganh.setModel(new DefaultComboBoxModel<>(new String[]{"KHMT", "KHNT"}));
        } else if (cbbThemMaKhoa.getSelectedItem().toString().equals("KTMT")) {
            cbbThemMaNganh.setModel(new DefaultComboBoxModel<>(new String[]{"KTMT"}));
        } else if (cbbThemMaKhoa.getSelectedItem().toString().equals("KTTT")) {
            cbbThemMaNganh.setModel(new DefaultComboBoxModel<>(new String[]{"CNTT", "KHDL"}));
        } else if (cbbThemMaKhoa.getSelectedItem().toString().equals("MMT&TT")) {
            cbbThemMaNganh.setModel(new DefaultComboBoxModel<>(new String[]{"ATTT", "MMTT"}));
        }
    }//GEN-LAST:event_cbbThemMaKhoaActionPerformed

    private void cbbUpdateMaKhoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbUpdateMaKhoaActionPerformed
        if (cbbUpdateMaKhoa.getSelectedItem().toString().equals("HTTT")) {
            cbbUpdateMaNganh.setModel(new DefaultComboBoxModel<>(new String[]{"HTTT", "TMDT"}));
        } else if (cbbUpdateMaKhoa.getSelectedItem().toString().equals("CNPM")) {
            cbbUpdateMaNganh.setModel(new DefaultComboBoxModel<>(new String[]{"KTPM"}));
        } else if (cbbUpdateMaKhoa.getSelectedItem().toString().equals("KHMT")) {
            cbbUpdateMaNganh.setModel(new DefaultComboBoxModel<>(new String[]{"KHMT", "KHNT"}));
        } else if (cbbUpdateMaKhoa.getSelectedItem().toString().equals("KTMT")) {
            cbbUpdateMaNganh.setModel(new DefaultComboBoxModel<>(new String[]{"KTMT"}));
        } else if (cbbUpdateMaKhoa.getSelectedItem().toString().equals("KTTT")) {
            cbbUpdateMaNganh.setModel(new DefaultComboBoxModel<>(new String[]{"CNTT", "KHDL"}));
        } else if (cbbUpdateMaKhoa.getSelectedItem().toString().equals("MMT&TT")) {
            cbbUpdateMaNganh.setModel(new DefaultComboBoxModel<>(new String[]{"ATTT", "MMTT"}));
        }
    }//GEN-LAST:event_cbbUpdateMaKhoaActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GiaoDien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CapNhapHP;
    private javax.swing.JDialog CapNhapSVDialog;
    private javax.swing.ButtonGroup GioiTinhGroup;
    private javax.swing.JPanel QuanLyDiem;
    private javax.swing.JPanel QuanLyKhoa;
    private javax.swing.JRadioButton RadioThemNam;
    private javax.swing.JRadioButton RadioThemNu;
    private javax.swing.JRadioButton RadioUpdateNam;
    private javax.swing.JRadioButton RadioUpdateNu;
    private javax.swing.JPanel ShowDiemTB;
    private javax.swing.JPanel ShowDiemTBSV;
    private javax.swing.JPanel ShowThongTin;
    private javax.swing.JPanel ShowThongTinSVPanel;
    private javax.swing.JFrame SinhVienFrame;
    private javax.swing.JTabbedPane TabPane;
    private javax.swing.JDialog ThemSVDialog;
    private javax.swing.JPanel ThongTinSV;
    private javax.swing.JButton btnCapNhap;
    private javax.swing.JButton btnCapNhapHP;
    private javax.swing.JButton btnCapNhapSV;
    private javax.swing.JButton btnDSHPThemHP;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnDangXuat1;
    private javax.swing.JButton btnInBangDiem;
    private javax.swing.JButton btnInDanhSach;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSapXep;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemSV;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTimMSSV;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaHP;
    private javax.swing.JComboBox<String> cbbHocKy;
    private javax.swing.JComboBox<String> cbbNamHoc;
    private javax.swing.JComboBox<String> cbbSapXep;
    private javax.swing.JComboBox<String> cbbThemKhoaHoc;
    private javax.swing.JComboBox<String> cbbThemMaKhoa;
    private javax.swing.JComboBox<String> cbbThemMaNganh;
    private javax.swing.JComboBox<String> cbbUpdateKhoaHoc;
    private javax.swing.JComboBox<String> cbbUpdateMaKhoa;
    private javax.swing.JComboBox<String> cbbUpdateMaNganh;
    private javax.swing.JMenuItem delMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lblDiemTBSV;
    private javax.swing.JLabel lblHoTenSV;
    private javax.swing.JLabel lblKhoaHoc;
    private javax.swing.JLabel lblMSSV;
    private javax.swing.JLabel lblMaKhoaSV;
    private javax.swing.JLabel lblMaNganhSV;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblNhapMSSV;
    private javax.swing.JLabel lblQueQuan;
    private javax.swing.JLabel lblShowDiemTB;
    private javax.swing.JLabel lblShowHoTen;
    private javax.swing.JLabel lblShowMaKhoa;
    private javax.swing.JLabel lblShowMaNganh;
    private javax.swing.JLabel lblThemGioiTinh;
    private javax.swing.JLabel lblThemHoTen;
    private javax.swing.JLabel lblThemKhoahoc;
    private javax.swing.JLabel lblThemMaKhoa;
    private javax.swing.JLabel lblThemMaNganh;
    private javax.swing.JLabel lblThemMssv;
    private javax.swing.JLabel lblThemNgaySinh;
    private javax.swing.JLabel lblThemQueQuan;
    private javax.swing.JLabel lblTieude;
    private javax.swing.JLabel lblUpdateDiem;
    private javax.swing.JLabel lblUpdateGioiTinh;
    private javax.swing.JLabel lblUpdateHoTen;
    private javax.swing.JLabel lblUpdateHocKy;
    private javax.swing.JLabel lblUpdateKhoaHoc;
    private javax.swing.JLabel lblUpdateMaHP;
    private javax.swing.JLabel lblUpdateMaKhoa;
    private javax.swing.JLabel lblUpdateMaNganh;
    private javax.swing.JLabel lblUpdateMssv;
    private javax.swing.JLabel lblUpdateNamHoc;
    private javax.swing.JLabel lblUpdateNgaySinh;
    private javax.swing.JLabel lblUpdateQueQuan;
    private javax.swing.JLabel lblUpdateTenHP;
    private javax.swing.JTable tblDSHP;
    private javax.swing.JTable tblDanhSachHP;
    private javax.swing.JTable tblDanhSachHPSV;
    private javax.swing.JTable tblDanhSachSV;
    private javax.swing.JTable tblDanhSachSVNganh;
    private javax.swing.JTable tblNganh;
    private javax.swing.JTable tblTongSoSVKhoa;
    private javax.swing.JTextField txtDiemTBSV;
    private javax.swing.JTextField txtHoTenSV;
    private javax.swing.JTextField txtKhoaHoc;
    private javax.swing.JTextField txtMSSV;
    private javax.swing.JTextField txtMaKhoaSV;
    private javax.swing.JTextField txtMaNganhSV;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtNhapMSSV;
    private javax.swing.JTextField txtQueQuan;
    private javax.swing.JTextField txtShowDiemTB;
    private javax.swing.JTextField txtShowHoTen;
    private javax.swing.JTextField txtShowMaKhoa;
    private javax.swing.JTextField txtShowMaNganh;
    private javax.swing.JTextField txtThemHoTen;
    private javax.swing.JTextField txtThemMssv;
    private javax.swing.JTextField txtThemNgaySinh;
    private javax.swing.JTextField txtThemQueQuan;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtUpdateDiem;
    private javax.swing.JTextField txtUpdateHoTen;
    private javax.swing.JTextField txtUpdateHocKy;
    private javax.swing.JTextField txtUpdateMaHP;
    private javax.swing.JTextField txtUpdateMssv;
    private javax.swing.JTextField txtUpdateNamHoc;
    private javax.swing.JTextField txtUpdateNgaySinh;
    private javax.swing.JTextField txtUpdateQueQuan;
    private javax.swing.JTextField txtUpdateTenHP;
    private javax.swing.JMenuItem updateMenuItem;
    // End of variables declaration//GEN-END:variables
}
