package quanlysinhvien;

import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public String getTaiKhoan() {
        return txtTaiKhoan.getText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTaiKhoan = new javax.swing.JLabel();
        lblMatKhau = new javax.swing.JLabel();
        txtTaiKhoan = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        passMatKhau = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Đăng Nhập");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTaiKhoan.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        lblTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        lblTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/userresixe.png"))); // NOI18N
        lblTaiKhoan.setText("Tài khoản:");
        getContentPane().add(lblTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, -1, -1));

        lblMatKhau.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        lblMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        lblMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/passwordresize.png"))); // NOI18N
        lblMatKhau.setText("Mật khẩu:");
        getContentPane().add(lblMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, -1, 20));

        txtTaiKhoan.setBackground(new java.awt.Color(204, 204, 204));
        txtTaiKhoan.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(txtTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 123, -1));

        btnLogin.setBackground(new java.awt.Color(0, 153, 255));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/log-in.png"))); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 110, -1));

        passMatKhau.setBackground(new java.awt.Color(204, 204, 204));
        passMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passMatKhauActionPerformed(evt);
            }
        });
        getContentPane().add(passMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, 123, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/login(resize).png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed

        String S = txtTaiKhoan.getText().toUpperCase(), W = String.valueOf(passMatKhau.getPassword());
        String loai = new ConnectDB().login(S, W);
        //System.out.println(loai);
        if (loai.equals("GV")) {
            GiaoDien n = new GiaoDien();
            n.setVisible(true);
            this.setVisible(false);
            this.dispose();
            JOptionPane.showMessageDialog(rootPane, "Đăng nhập thành công !");
        } else if (loai.equals("SV")) {
            GiaoDien n = new GiaoDien();
            n.ShowSinhVienPanel();
            this.setVisible(false);
            n.reloadTBSV(S);
            this.dispose();
            JOptionPane.showMessageDialog(rootPane, "Đăng nhập thành công !");
        } else
            JOptionPane.showMessageDialog(rootPane, "Thông tin không hợp lệ !");
    }//GEN-LAST:event_btnLoginActionPerformed

    private void passMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passMatKhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passMatKhauActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblTaiKhoan;
    private javax.swing.JPasswordField passMatKhau;
    private javax.swing.JTextField txtTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
