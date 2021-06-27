package view.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.user.UserModelInterface;
import view.MessageShowing;
import control.login.LoginControllerInterface;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class LoginFrame extends javax.swing.JFrame implements MessageShowing,
        ActionListener {

    private UserModelInterface userModel;
    private LoginControllerInterface loginController;
    private static Color SELECTED = new Color(107, 162, 249);
    private static Color UNSELECTED = new Color(173, 173, 173);
    private boolean showPW = false;

    public LoginFrame(UserModelInterface model, LoginControllerInterface controller) {
        initComponents();

        this.userModel = model;
        this.userModel.setLoginFrame(this);
        this.loginController = controller;

        btnSignIn.addActionListener(this);
        btnForgotPassword.addActionListener(this);

        setAppLookAndFeel();
    }

    private void setAppLookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnSignIn) {
            String emailInput = textfEmail.getText().trim();
            String passwordInput = String.valueOf(passfPassword.getPassword());
//            this.loginController.requestLogin(emailInput, passwordInput);
            this.loginController.requestLogin("baohtp@gmail.com", "Nvbh123@");
//            this.loginController.requestLogin("nhantd@gmail.com", "Nvbh345@");
//            this.loginController.requestLogin("ngocnhu@gmail.com", "Nvbh456@");
        } else if (source == btnForgotPassword) {
            this.loginController.requestRecoverPassword();
        }
    }

    public void setEmailText(String text) {
        this.textfEmail.setText(text);
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Login error", JOptionPane.ERROR_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/error.png")));
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/infor.png")));
    }

    @Override
    public void showWarningMessage(String message) {
    }

    public void resetLoginInput() {
        this.textfEmail.setText("");
        this.passfPassword.setText("");
    }

    @Override
    public void setVisible(boolean b) {
        resetLoginInput();
        super.setVisible(b);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        label_signIn = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnSignIn = new javax.swing.JButton();
        panelLoginInfo = new javax.swing.JPanel();
        labelEmail = new javax.swing.JLabel();
        textfEmail = new javax.swing.JTextField();
        labelPassword = new javax.swing.JLabel();
        passfPassword = new javax.swing.JPasswordField();
        passwordShow = new javax.swing.JLabel();
        btnForgotPassword = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sign In");
        setResizable(false);

        setResizable(false);
        panelLogin.setBackground(new java.awt.Color(255, 255, 255));
        panelLogin.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(54, 129, 203));
        jPanel1.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 75));

        label_signIn.setBackground(new java.awt.Color(54, 129, 203));
        label_signIn.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        label_signIn.setForeground(new java.awt.Color(255, 255, 255));
        label_signIn.setText("Bakery Management System");
        label_signIn.setOpaque(true);
        label_signIn.setPreferredSize(new java.awt.Dimension(250, 32));

        jLabel1.setBackground(new java.awt.Color(54, 129, 203));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/shop70px.png"))); // NOI18N
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 70));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(label_signIn, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_signIn, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSignIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_sign-in.png"))); // NOI18N
        btnSignIn.setBorderPainted(false);
        btnSignIn.setContentAreaFilled(false);
        btnSignIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSignIn.setFocusPainted(false);

        panelLoginInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelLoginInfo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        labelEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelEmail.setText("Email:");
        labelEmail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelLoginInfo.add(labelEmail);

        textfEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        textfEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(173, 173, 173)));
        textfEmail.setPreferredSize(new java.awt.Dimension(400, 40));
        textfEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textfEmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textfEmailFocusLost(evt);
            }
        });
        panelLoginInfo.add(textfEmail);

        labelPassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPassword.setText("Password:");
        labelPassword.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelLoginInfo.add(labelPassword);

        passfPassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        passfPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(173, 173, 173)));
        passfPassword.setPreferredSize(new java.awt.Dimension(400, 40));
        passfPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passfPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passfPasswordFocusLost(evt);
            }
        });
        panelLoginInfo.add(passfPassword);

        passwordShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hidePW_24px.png"))); // NOI18N
        passwordShow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        passwordShow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                passwordShowMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                passwordShowMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                passwordShowMouseExited(evt);
            }
        });
        panelLoginInfo.add(passwordShow);

        btnForgotPassword.setBackground(new java.awt.Color(51, 102, 255));
        btnForgotPassword.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnForgotPassword.setForeground(new java.awt.Color(113, 168, 255));
        btnForgotPassword.setText("Forgot password?");
        btnForgotPassword.setAutoscrolls(true);
        btnForgotPassword.setBorderPainted(false);
        btnForgotPassword.setContentAreaFilled(false);
        btnForgotPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnForgotPassword.setFocusPainted(false);
        btnForgotPassword.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelLoginInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(btnForgotPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelLoginInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnForgotPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textfEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textfEmailFocusGained
        textfEmail.setBorder(BorderFactory.createLineBorder(SELECTED, 2));
    }//GEN-LAST:event_textfEmailFocusGained

    private void textfEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textfEmailFocusLost
        textfEmail.setBorder(BorderFactory.createLineBorder(UNSELECTED, 1));
    }//GEN-LAST:event_textfEmailFocusLost

    private void passfPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passfPasswordFocusGained
        passfPassword.setBorder(BorderFactory.createLineBorder(SELECTED, 2));
    }//GEN-LAST:event_passfPasswordFocusGained

    private void passfPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passfPasswordFocusLost
        passfPassword.setBorder(BorderFactory.createLineBorder(UNSELECTED, 1));
    }//GEN-LAST:event_passfPasswordFocusLost

    private void passwordShowMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passwordShowMouseEntered
        if (showPW) {
            passwordShow.setIcon(new ImageIcon(getClass().getResource("/img/selected_showPW_24px.png")));
        } else {
            passwordShow.setIcon(new ImageIcon(getClass().getResource("/img/selected_hidePW_24px.png")));
        }
    }//GEN-LAST:event_passwordShowMouseEntered

    private void passwordShowMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passwordShowMouseExited
        if (showPW) {
            passwordShow.setIcon(new ImageIcon(getClass().getResource("/img/showPW_24px.png")));
        } else {
            passwordShow.setIcon(new ImageIcon(getClass().getResource("/img/hidePW_24px.png")));
        }
    }//GEN-LAST:event_passwordShowMouseExited

    private void passwordShowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passwordShowMouseClicked
        if (showPW) {
            showPW = false;
            passwordShow.setIcon(new ImageIcon(getClass().getResource("/img/selected_hidePW_24px.png")));
            passfPassword.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
        } else {
            showPW = true;
            passwordShow.setIcon(new ImageIcon(getClass().getResource("/img/selected_showPW_24px.png")));
            passfPassword.setEchoChar('\u0000');
        }


    }//GEN-LAST:event_passwordShowMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnForgotPassword;
    private javax.swing.JButton btnSignIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelEmail;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel label_signIn;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelLoginInfo;
    private javax.swing.JPasswordField passfPassword;
    private javax.swing.JLabel passwordShow;
    private javax.swing.JTextField textfEmail;
    // End of variables declaration//GEN-END:variables

}
