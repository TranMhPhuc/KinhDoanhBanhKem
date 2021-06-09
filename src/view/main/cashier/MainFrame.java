package view.main.cashier;

import view.main.accountant.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainFrame extends javax.swing.JFrame {

    private JLabel choosedLabel;

    public MainFrame() {
        initComponents();
        this.choosedLabel = null;
        createControl();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    private void createControl() {
        JLabel[] labels = new JLabel[]{
            labelProfile, labelCreateBill, labelViewBill, labelSettings
        };

        for (JLabel label : labels) {
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (label != choosedLabel) {
                        label.setBackground(new Color(107, 162, 249));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (label != choosedLabel) {
                        label.setBackground(new Color(113, 168, 255));
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (label != choosedLabel) {
                        if (choosedLabel != null) {
                            choosedLabel.setBackground(new Color(113, 168, 255));
                        }
                        label.setBackground(new Color(77, 128, 216));
                        choosedLabel = label;
                    }
                }
            });
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTitle = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panelSide = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        labelProfile = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        labelCreateBill = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        labelViewBill = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        labelSettings = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        panelCenter = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 800));

        panelTitle.setBackground(new java.awt.Color(62, 120, 207));
        panelTitle.setPreferredSize(new java.awt.Dimension(1194, 100));
        panelTitle.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/shop70px.png"))); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(150, 70));
        panelTitle.add(jLabel1, java.awt.BorderLayout.WEST);

        jPanel2.setBackground(new java.awt.Color(62, 120, 207));
        jPanel2.setMinimumSize(new java.awt.Dimension(200, 60));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Bakery Management System");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel2.setMinimumSize(new java.awt.Dimension(200, 37));
        jLabel2.setPreferredSize(new java.awt.Dimension(355, 20));
        jPanel2.add(jLabel2, java.awt.BorderLayout.CENTER);

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("v1.0");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel3.setPreferredSize(new java.awt.Dimension(31, 30));
        jPanel2.add(jLabel3, java.awt.BorderLayout.SOUTH);

        panelTitle.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(62, 120, 207));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Develop by Phuc Team");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel5.setPreferredSize(new java.awt.Dimension(165, 30));
        jPanel3.add(jLabel5, java.awt.BorderLayout.SOUTH);

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Copyright © 2021 BMS System");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel4.setPreferredSize(new java.awt.Dimension(230, 23));
        jPanel3.add(jLabel4, java.awt.BorderLayout.CENTER);

        panelTitle.add(jPanel3, java.awt.BorderLayout.EAST);

        getContentPane().add(panelTitle, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.BorderLayout());

        panelSide.setBackground(new java.awt.Color(113, 168, 255));
        panelSide.setPreferredSize(new java.awt.Dimension(150, 0));
        panelSide.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.GridLayout(0, 1));

        labelProfile.setBackground(new java.awt.Color(113, 168, 255));
        labelProfile.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        labelProfile.setForeground(new java.awt.Color(255, 255, 255));
        labelProfile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Profile_50px.png"))); // NOI18N
        labelProfile.setText("Profile");
        labelProfile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelProfile.setIconTextGap(10);
        labelProfile.setOpaque(true);
        labelProfile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator1.setBackground(new java.awt.Color(235, 235, 235));
        jSeparator1.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(labelProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.add(jPanel4);

        labelCreateBill.setBackground(new java.awt.Color(113, 168, 255));
        labelCreateBill.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelCreateBill.setForeground(new java.awt.Color(255, 255, 255));
        labelCreateBill.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCreateBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Bill_50px.png"))); // NOI18N
        labelCreateBill.setText("Create Bill");
        labelCreateBill.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelCreateBill.setIconTextGap(10);
        labelCreateBill.setOpaque(true);
        labelCreateBill.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator2.setBackground(new java.awt.Color(235, 235, 235));
        jSeparator2.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCreateBill, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(labelCreateBill, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.add(jPanel6);

        labelViewBill.setBackground(new java.awt.Color(113, 168, 255));
        labelViewBill.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelViewBill.setForeground(new java.awt.Color(255, 255, 255));
        labelViewBill.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelViewBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BillHistory_50px.png"))); // NOI18N
        labelViewBill.setText("View Bill");
        labelViewBill.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelViewBill.setIconTextGap(10);
        labelViewBill.setOpaque(true);
        labelViewBill.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator3.setBackground(new java.awt.Color(235, 235, 235));
        jSeparator3.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelViewBill, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(labelViewBill, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.add(jPanel7);

        labelSettings.setBackground(new java.awt.Color(113, 168, 255));
        labelSettings.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        labelSettings.setForeground(new java.awt.Color(255, 255, 255));
        labelSettings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/settings_50px.png"))); // NOI18N
        labelSettings.setText("Settings");
        labelSettings.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelSettings.setIconTextGap(10);
        labelSettings.setOpaque(true);
        labelSettings.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator5.setBackground(new java.awt.Color(235, 235, 235));
        jSeparator5.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(labelSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.add(jPanel9);

        panelSide.add(jPanel5, java.awt.BorderLayout.NORTH);

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Exit_20px.png"))); // NOI18N
        jLabel6.setText("Sign out");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jLabel6.setIconTextGap(10);
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 50));
        panelSide.add(jLabel6, java.awt.BorderLayout.SOUTH);

        jPanel1.add(panelSide, java.awt.BorderLayout.WEST);

        panelCenter.setBackground(new java.awt.Color(255, 255, 255));
        panelCenter.setPreferredSize(new java.awt.Dimension(100, 100));
        panelCenter.setLayout(new java.awt.CardLayout());
        jPanel1.add(panelCenter, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel labelCreateBill;
    private javax.swing.JLabel labelProfile;
    private javax.swing.JLabel labelSettings;
    private javax.swing.JLabel labelViewBill;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JPanel panelSide;
    private javax.swing.JPanel panelTitle;
    // End of variables declaration//GEN-END:variables
}