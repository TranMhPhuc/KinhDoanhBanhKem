package view.profile;

import control.app.MainFrameControllerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import listener.PasswordListener;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import view.MessageShowing;

public class PasswordChangeDialog extends javax.swing.JDialog implements MessageShowing,
        SettingUpdateObserver {

    private boolean showOldPW = false, showNewPW = false, showConfirmNewPW = false;
    private MainFrameControllerInterface mainFrameController;

    public PasswordChangeDialog(java.awt.Frame parent, boolean modal,
            MainFrameControllerInterface mainFrameController) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        this.mainFrameController = mainFrameController;

        btnChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrameController.checkPasswordUpdateInput();
            }
        });
        iconOldPWShow.addMouseListener(new PasswordListener(iconOldPWShow, passfOldPassword,showOldPW));
        iconNewPWShow.addMouseListener(new PasswordListener(iconNewPWShow, passfNewPassword,showNewPW));
        iconConfirmPWShow.addMouseListener(new PasswordListener(iconConfirmPWShow, passfVerifyPassword,showConfirmNewPW));
    }

    public String getOldPasswordInput() {
        return String.valueOf(this.passfOldPassword.getPassword());
    }

    public String getNewPasswordInput() {
        return String.valueOf(this.passfNewPassword.getPassword());
    }

    public String getVerifyPasswordInput() {
        return String.valueOf(this.passfVerifyPassword.getPassword());
    }

    @Override
    public void setVisible(boolean b) {
        this.passfOldPassword.setText("");
        this.passfNewPassword.setText("");
        this.passfVerifyPassword.setText("");

        super.setVisible(b);
    }

    @Override
    public void updateSettingObserver() {
        if (AppSetting.getInstance().getAppLanguage() == AppSetting.Language.ENGLISH) {
            setTitle("Change password dialog");
            labelMainTitle.setText("Change Your Password");
            labelOldPassword.setText("Old password");
            labelNewPassword.setText("New password");
            labelVerifyPassword.setText("Verify new password");
            btnChangePassword.setText("Update password");
        } else {
            setTitle("Hộp thoại thay đổi mật khẩu");
            labelMainTitle.setText("Thay Đổi Mật Khẩu");
            labelOldPassword.setText("Mật khẩu cũ");
            labelNewPassword.setText("Mật khẩu mới");
            labelVerifyPassword.setText("Xác thực mật khẩu mới");
            btnChangePassword.setText("Cập nhật mật khẩu");
        }
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.ERROR_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/error.png")));
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.INFORMATION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/infor.png")));
    }

    @Override
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/warning.png")));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelMainTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnChangePassword = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        passfNewPassword = new javax.swing.JPasswordField();
        labelNewPassword = new javax.swing.JLabel();
        labelOldPassword = new javax.swing.JLabel();
        passfOldPassword = new javax.swing.JPasswordField();
        passfVerifyPassword = new javax.swing.JPasswordField();
        labelVerifyPassword = new javax.swing.JLabel();
        iconOldPWShow = new javax.swing.JLabel();
        iconNewPWShow = new javax.swing.JLabel();
        iconConfirmPWShow = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        labelMainTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        labelMainTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMainTitle.setText("Change your password");
        labelMainTitle.setPreferredSize(new java.awt.Dimension(214, 50));
        jPanel1.add(labelMainTitle, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(155, 60));

        btnChangePassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnChangePassword.setText("Update password");
        btnChangePassword.setPreferredSize(new java.awt.Dimension(160, 40));
        jPanel2.add(btnChangePassword);

        jPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        passfNewPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        labelNewPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelNewPassword.setText("New Password");

        labelOldPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelOldPassword.setText("Old Password ");

        passfOldPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        passfVerifyPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        labelVerifyPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelVerifyPassword.setText("Verify New Password");

        iconOldPWShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hidePW_24px.png"))); // NOI18N

        iconNewPWShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hidePW_24px.png"))); // NOI18N

        iconConfirmPWShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hidePW_24px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(passfVerifyPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(iconConfirmPWShow))
                    .addComponent(labelVerifyPassword)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(passfNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(iconNewPWShow))
                    .addComponent(labelNewPassword)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(passfOldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(iconOldPWShow))
                    .addComponent(labelOldPassword))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelOldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passfOldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconOldPWShow))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelNewPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passfNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconNewPWShow))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(labelVerifyPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passfVerifyPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(iconConfirmPWShow))
                .addContainerGap())
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangePassword;
    private javax.swing.JLabel iconConfirmPWShow;
    private javax.swing.JLabel iconNewPWShow;
    private javax.swing.JLabel iconOldPWShow;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelMainTitle;
    private javax.swing.JLabel labelNewPassword;
    private javax.swing.JLabel labelOldPassword;
    private javax.swing.JLabel labelVerifyPassword;
    private javax.swing.JPasswordField passfNewPassword;
    private javax.swing.JPasswordField passfOldPassword;
    private javax.swing.JPasswordField passfVerifyPassword;
    // End of variables declaration//GEN-END:variables
}
