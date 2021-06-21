package view.main.accountant;

import control.setting.AccountantSettingController;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import model.setting.AppSetting;

public class AccountantSettingsPanel extends javax.swing.JPanel {

    private AppSetting appSettingModel;
    private AccountantSettingController accountantSettingController;

    public AccountantSettingsPanel() {
        initComponents();
        createView();
        createControl();
    }

    public void setAppSettingModel(AppSetting appSettingModel) {
        if (appSettingModel == null) {
            throw new NullPointerException();
        }
        this.appSettingModel = appSettingModel;
        loadSettingData();
    }
    
    private void loadSettingData() {
        AppSetting.Language appLanguage = appSettingModel.getAppLanguage();
        combLanguage.setSelectedItem(appLanguage.toString());
        ckbConfirmSignOut.setSelected(appSettingModel.getConfirmSignOutFlag());
        ckbConfirmExit.setSelected(appSettingModel.getConfirmExitFlag());
    }

    public void setAccountantSettingController(AccountantSettingController accountantSettingController) {
        if (accountantSettingController == null) {
            throw new NullPointerException();
        }
        this.accountantSettingController = accountantSettingController;
    }

    private void createView() {
        labelHyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelHyperlink.setForeground(Color.BLUE.darker());
    }

    private void createControl() {
        labelHyperlink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/TranMhPhuc/KinhDoanhBanhKem"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        ckbConfirmSignOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountantSettingController.requestChangeConfirmSignOutFlag(ckbConfirmSignOut.isSelected());
            }
        });

        ckbConfirmExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountantSettingController.requestChangeConfirmExitFlag(ckbConfirmExit.isSelected());
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelOption = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        labelHyperlink = new javax.swing.JLabel();
        ckbConfirmSignOut = new javax.swing.JCheckBox();
        ckbConfirmExit = new javax.swing.JCheckBox();
        combLanguage = new javax.swing.JComboBox<>();
        labelLanguage = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setName("Settings"); // NOI18N

        labelOption.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelOption.setText("Options:");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "About", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 15), new java.awt.Color(135, 135, 135))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/shop70px.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Bakery Management System");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel5.setMinimumSize(new java.awt.Dimension(200, 37));
        jLabel5.setPreferredSize(new java.awt.Dimension(355, 30));
        jPanel2.add(jLabel5, java.awt.BorderLayout.NORTH);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("v1.0");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel6.setPreferredSize(new java.awt.Dimension(31, 20));
        jPanel2.add(jLabel6, java.awt.BorderLayout.CENTER);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Copyright © 2021 BMS Program");
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel7.setPreferredSize(new java.awt.Dimension(31, 20));
        jPanel2.add(jLabel7, java.awt.BorderLayout.SOUTH);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setText("Website: ");

        labelHyperlink.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelHyperlink.setForeground(new java.awt.Color(0, 0, 204));
        labelHyperlink.setText("https://github.com/TranMhPhuc/KinhDoanhBanhKem");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelHyperlink))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(28, 28, 28)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(626, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(labelHyperlink))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        ckbConfirmSignOut.setBackground(new java.awt.Color(255, 255, 255));
        ckbConfirmSignOut.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ckbConfirmSignOut.setText("Confirm when sign out");

        ckbConfirmExit.setBackground(new java.awt.Color(255, 255, 255));
        ckbConfirmExit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ckbConfirmExit.setText("Confirm when exit");

        combLanguage.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combLanguage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "English", "Vietnamese" }));

        labelLanguage.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelLanguage.setText("Language:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelLanguage)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(combLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelOption, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ckbConfirmExit)
                                    .addComponent(ckbConfirmSignOut))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelOption, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLanguage)
                    .addComponent(combLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ckbConfirmSignOut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ckbConfirmExit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 364, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ckbConfirmExit;
    private javax.swing.JCheckBox ckbConfirmSignOut;
    private javax.swing.JComboBox<String> combLanguage;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelHyperlink;
    private javax.swing.JLabel labelLanguage;
    private javax.swing.JLabel labelOption;
    // End of variables declaration//GEN-END:variables
}
