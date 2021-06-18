package view.profile;

import control.app.MainFrameControllerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.MessageShowing;

public class PasswordChangeDialog extends javax.swing.JDialog implements MessageShowing {

    private MainFrameControllerInterface mainFrameController;
    
    public PasswordChangeDialog(java.awt.Frame parent, boolean modal,
            MainFrameControllerInterface mainFrameController) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        this.mainFrameController = mainFrameController;
        
        btnChangePass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrameController.checkPasswordUpdateInput();
            }
        });
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
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Password update dialog", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Password update dialog", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Password update dialog", JOptionPane.WARNING_MESSAGE);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        label_changePass = new javax.swing.JLabel();
        label_oldPass = new javax.swing.JLabel();
        label_newPass = new javax.swing.JLabel();
        btnChangePass = new javax.swing.JButton();
        label_verifyPass = new javax.swing.JLabel();
        passfOldPassword = new javax.swing.JPasswordField();
        passfNewPassword = new javax.swing.JPasswordField();
        passfVerifyPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        label_changePass.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        label_changePass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_changePass.setText("Change your password");

        label_oldPass.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_oldPass.setText("Old Password ");

        label_newPass.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_newPass.setText("New Password");

        btnChangePass.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnChangePass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttonIMG_updatePW.png"))); // NOI18N
        btnChangePass.setContentAreaFilled(false);
        btnChangePass.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChangePass.setFocusPainted(false);

        label_verifyPass.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_verifyPass.setText("Verify New Password");

        passfOldPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        passfNewPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        passfVerifyPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_changePass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passfVerifyPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_verifyPass)
                            .addComponent(passfNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_newPass)
                            .addComponent(passfOldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_oldPass))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE))
                    .addComponent(btnChangePass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_changePass)
                .addGap(22, 22, 22)
                .addComponent(label_oldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passfOldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_newPass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passfNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_verifyPass, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passfVerifyPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangePass;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label_changePass;
    private javax.swing.JLabel label_newPass;
    private javax.swing.JLabel label_oldPass;
    private javax.swing.JLabel label_verifyPass;
    private javax.swing.JPasswordField passfNewPassword;
    private javax.swing.JPasswordField passfOldPassword;
    private javax.swing.JPasswordField passfVerifyPassword;
    // End of variables declaration//GEN-END:variables
}
