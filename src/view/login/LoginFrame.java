package view.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.user.UserModelInterface;
import view.MessageShowing;
import control.login.LoginControllerInterface;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.UIManager;

public class LoginFrame extends javax.swing.JFrame implements MessageShowing, ActionListener {

    private UserModelInterface model;
    private LoginControllerInterface controller;

    public LoginFrame(UserModelInterface model, LoginControllerInterface controller) {
        initComponents();

        this.model = model;
        this.model.setLoginFrame(this);
        this.controller = controller;

        btnSignIn.addActionListener(this);
        btnForgotPassword.addActionListener(this);

        ckbShowPassword.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    passfPassword.setEchoChar('\u0000');
                } else {
                    passfPassword.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
                }
            }
        });
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
//            this.controller.requestLogin(emailInput, passwordInput);
//            this.controller.requestLogin("baohtp@gmail.com", "Nvbh123@");
//            this.controller.requestLogin("nhantd@gmail.com", "Nvbh345@");
            this.controller.requestLogin("dv@gmail.com", "Nvbh234@");
        } else if (source == btnForgotPassword) {
            this.controller.requestRecoverPassword();
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
        this.ckbShowPassword.setSelected(false);
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
        jLabel_dashLine = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        label_email = new javax.swing.JLabel();
        ckbShowPassword = new javax.swing.JCheckBox();
        btnSignIn = new javax.swing.JButton();
        label_password = new javax.swing.JLabel();
        btnForgotPassword = new javax.swing.JButton();
        passfPassword = new javax.swing.JPasswordField();
        textfEmail = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sign In");
        setResizable(false);

        setResizable(false);
        panelLogin.setBackground(new java.awt.Color(255, 255, 255));
        panelLogin.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        panelLogin.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        label_signIn.setBackground(new java.awt.Color(54, 129, 203));
        label_signIn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        label_signIn.setForeground(new java.awt.Color(255, 255, 255));
        label_signIn.setText("Bakery Management System");
        label_signIn.setOpaque(true);
        label_signIn.setPreferredSize(new java.awt.Dimension(250, 32));
        jPanel1.add(label_signIn, java.awt.BorderLayout.CENTER);

        jLabel1.setBackground(new java.awt.Color(54, 129, 203));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/shop70px.png"))); // NOI18N
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 70));
        jPanel1.add(jLabel1, java.awt.BorderLayout.WEST);

        jLabel_dashLine.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_dashLine.setOpaque(true);
        jLabel_dashLine.setPreferredSize(new java.awt.Dimension(0, 5));
        jPanel1.add(jLabel_dashLine, java.awt.BorderLayout.SOUTH);

        panelLogin.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        label_email.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_email.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_email.setText("Email:");
        label_email.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        ckbShowPassword.setBackground(new java.awt.Color(255, 255, 255));
        ckbShowPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        ckbShowPassword.setText("Show password");
        ckbShowPassword.setFocusPainted(false);

        btnSignIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttonIMG_signIn.png"))); // NOI18N
        btnSignIn.setBorderPainted(false);
        btnSignIn.setContentAreaFilled(false);
        btnSignIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSignIn.setFocusPainted(false);

        label_password.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_password.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_password.setText("Password:");
        label_password.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnForgotPassword.setBackground(new java.awt.Color(51, 102, 255));
        btnForgotPassword.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        btnForgotPassword.setForeground(new java.awt.Color(0, 123, 255));
        btnForgotPassword.setText("Forgot password?");
        btnForgotPassword.setBorderPainted(false);
        btnForgotPassword.setContentAreaFilled(false);
        btnForgotPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnForgotPassword.setFocusPainted(false);
        btnForgotPassword.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);

        passfPassword.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfEmail.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_email)
                            .addComponent(label_password))
                        .addGap(331, 331, 331))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSignIn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnForgotPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ckbShowPassword))
                            .addComponent(passfPassword, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textfEmail))
                        .addGap(30, 30, 30))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_email)
                .addGap(3, 3, 3)
                .addComponent(textfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_password)
                .addGap(12, 12, 12)
                .addComponent(passfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckbShowPassword)
                    .addComponent(btnForgotPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelLogin.add(jPanel2, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnForgotPassword;
    private javax.swing.JButton btnSignIn;
    private javax.swing.JCheckBox ckbShowPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_dashLine;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label_email;
    private javax.swing.JLabel label_password;
    private javax.swing.JLabel label_signIn;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPasswordField passfPassword;
    private javax.swing.JTextField textfEmail;
    // End of variables declaration//GEN-END:variables

}
