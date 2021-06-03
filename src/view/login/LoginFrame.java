package view.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.user.UserModelInterface;
import view.MessageShowing;
import control.login.LoginControllerInterface;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LoginFrame extends javax.swing.JFrame implements MessageShowing, ActionListener {

    private UserModelInterface model;
    private LoginControllerInterface controller;

    public LoginFrame(UserModelInterface model, LoginControllerInterface controller) {
        initComponents();

        this.model = model;
        this.controller = controller;

        btnSignIn.addActionListener(this);
        btnForgotPassword.addActionListener(this);

        ckbShowPassword.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // TASK: show password text
                // XXX
            }
        });

        setAppLookAndFeel();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin = new javax.swing.JPanel();
        label_signIn = new javax.swing.JLabel();
        label_imgLogo = new javax.swing.JLabel();
        jLabel_dashLine = new javax.swing.JLabel();
        label_email = new javax.swing.JLabel();
        label_password = new javax.swing.JLabel();
        textfEmail = new javax.swing.JTextField();
        label_imgUser = new javax.swing.JLabel();
        label_imgPass = new javax.swing.JLabel();
        passfPassword = new javax.swing.JPasswordField();
        ckbShowPassword = new javax.swing.JCheckBox();
        btnSignIn = new javax.swing.JButton();
        btnForgotPassword = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sign In");

        setResizable(false);
        panelLogin.setBackground(new java.awt.Color(255, 255, 255));
        panelLogin.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        label_signIn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        label_signIn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_signIn.setText("Cake Shop Management");

        label_imgLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_imgLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Logo_60px.png"))); // NOI18N
        label_imgLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel_dashLine.setBackground(new java.awt.Color(0, 0, 0));
        jLabel_dashLine.setOpaque(true);

        label_email.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_email.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_email.setText("Email:");
        label_email.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        label_password.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_password.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_password.setText("Password:");
        label_password.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        textfEmail.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        label_imgUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_User.png"))); // NOI18N
        label_imgUser.setText("jLabel1");

        label_imgPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_Password.png"))); // NOI18N

        passfPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        ckbShowPassword.setBackground(new java.awt.Color(255, 255, 255));
        ckbShowPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        ckbShowPassword.setText("Show password");
        ckbShowPassword.setFocusPainted(false);

        btnSignIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttonIMG_signIn.png"))); // NOI18N
        btnSignIn.setContentAreaFilled(false);
        btnSignIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSignIn.setFocusPainted(false);

        btnForgotPassword.setBackground(new java.awt.Color(51, 102, 255));
        btnForgotPassword.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        btnForgotPassword.setForeground(new java.awt.Color(0, 123, 255));
        btnForgotPassword.setText("Forgot password?");
        btnForgotPassword.setContentAreaFilled(false);
        btnForgotPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnForgotPassword.setFocusPainted(false);
        btnForgotPassword.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_dashLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_signIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_imgLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_email)
                            .addComponent(label_password))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSignIn, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                            .addGroup(panelLoginLayout.createSequentialGroup()
                                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLoginLayout.createSequentialGroup()
                                        .addComponent(btnForgotPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ckbShowPassword))
                                    .addComponent(passfPassword)
                                    .addComponent(textfEmail, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_imgPass)
                                    .addComponent(label_imgUser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(23, 23, 23))))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addComponent(label_imgLogo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                        .addComponent(label_signIn)
                        .addGap(26, 26, 26)))
                .addComponent(jLabel_dashLine, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_email)
                .addGap(3, 3, 3)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_imgUser, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_password)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label_imgPass)
                    .addComponent(passfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckbShowPassword)
                    .addComponent(btnForgotPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(btnSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
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
//            this.controller.requestLogin(emailInput, passwordInput);
            this.controller.requestLogin("baohtp@gmail.com", "Nvbh123@");
        } else if (source == btnForgotPassword) {
            this.controller.requestRecoverPassword();
        }
    }

    public void setEmailText(String text) {
        this.textfEmail.setText(text);
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Login error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showWarningMessage(String message) {
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnForgotPassword;
    private javax.swing.JButton btnSignIn;
    private javax.swing.JCheckBox ckbShowPassword;
    private javax.swing.JLabel jLabel_dashLine;
    private javax.swing.JLabel label_email;
    private javax.swing.JLabel label_imgLogo;
    private javax.swing.JLabel label_imgPass;
    private javax.swing.JLabel label_imgUser;
    private javax.swing.JLabel label_password;
    private javax.swing.JLabel label_signIn;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPasswordField passfPassword;
    private javax.swing.JTextField textfEmail;
    // End of variables declaration//GEN-END:variables

}
