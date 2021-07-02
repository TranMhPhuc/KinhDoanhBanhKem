package view.main.cashier;

import control.app.MainFrameControllerInterface;
import control.bill.create.BillCreateController;
import control.bill.create.BillCreateControllerInterface;
import control.bill.history.BillHistoryController;
import control.bill.history.BillHistoryControllerInterface;
import control.setting.CashierSettingController;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.bill.BillCreateModel;
import model.user.UserModelInterface;
import util.constant.AppConstant;
import view.MessageShowing;
import model.bill.BillCreateModelInterface;
import model.bill.BillHistoryModel;
import model.bill.BillHistoryModelInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import util.messages.Messages;

public class CashierMainFrame extends javax.swing.JFrame implements MessageShowing,
        SettingUpdateObserver {

    private UserModelInterface userModel;
    private MainFrameControllerInterface mainFrameController;

    private BillCreateModelInterface billCreateModel;
    private BillCreateControllerInterface billCreateController;

    private BillHistoryModelInterface billHistoryModel;
    private BillHistoryControllerInterface billHistoryController;

    private AppSetting appSettingModel;
    private CashierSettingController cashierSettingController;

    private JLabel choosedLabel;
    private CardLayout cardLayoutPanelCenter;

    public CashierMainFrame(MainFrameControllerInterface mainFrameController,
            UserModelInterface userModel) {
        initComponents();
        this.mainFrameController = mainFrameController;
        this.userModel = userModel;
        this.userModel.setMainFrame(this);

        this.cardLayoutPanelCenter = (CardLayout) panelCenter.getLayout();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

        panelBillCreate.setMaiFrame(this);
        panelBillHistory.setMainFrame(this);
        
        this.setTitle(AppConstant.MAIN_FRAME_TITLE_CASHIER);
        ImageIcon img = new ImageIcon(getClass().getResource("/img/shop70px.png"));
        this.setIconImage(img.getImage());
        
        createView();
        createControl();
    }

    private void createView() {
        this.choosedLabel = labelBillCreate;
        choosedLabel.setBackground(AppConstant.COLOR_MENU_MOUSE_PRESS);
        cardLayoutPanelCenter.show(panelCenter, panelBillCreate.getName());

        this.panelProfile.setUserModel(userModel);
        this.panelProfile.setMainFrameController(mainFrameController);
        mainFrameController.setProfilePanelView(panelProfile);
        userModel.setProfilePanelView(panelProfile);

        billCreateModel = new BillCreateModel();

        billCreateController = new BillCreateController(billCreateModel);
        billCreateController.setBillCreatePanel(panelBillCreate);

        billHistoryModel = new BillHistoryModel();
        billHistoryController = new BillHistoryController(billHistoryModel);
        billHistoryController.setBillHistoryPanel(panelBillHistory);

        billCreateModel.registerInsertedBillObserver(billHistoryController);

        appSettingModel = AppSetting.getInstance();
        cashierSettingController = new CashierSettingController(appSettingModel);
        cashierSettingController.setCashierSettingsPanel(panelSetting);

        appSettingModel.registerObserver(this);
        appSettingModel.registerObserver(panelProfile);
        appSettingModel.registerObserver(panelBillCreate);
        appSettingModel.registerObserver(panelBillHistory);
        appSettingModel.registerObserver(Messages.getInstance());
    }

    private void createControl() {
        JLabel[] labels = new JLabel[]{
            labelProfile, labelBillCreate, labelBillHistory, labelSettings
        };

        for (JLabel label : labels) {
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (label != choosedLabel) {
                        label.setBackground(AppConstant.COLOR_MENU_MOUSE_ENTER);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (label != choosedLabel) {
                        label.setBackground(AppConstant.COLOR_MENU_MOUSE_EXIT);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (label != choosedLabel) {
                        if (choosedLabel != null) {
                            choosedLabel.setBackground(AppConstant.COLOR_MENU_MOUSE_EXIT);
                        }
                        label.setBackground(AppConstant.COLOR_MENU_MOUSE_PRESS);
                        choosedLabel = label;
                        if (label == labelProfile) {
                            cardLayoutPanelCenter.show(panelCenter, panelProfile.getName());
                        } else if (label == labelBillCreate) {
                            cardLayoutPanelCenter.show(panelCenter, panelBillCreate.getName());
                        } else if (label == labelBillHistory) {
                            cardLayoutPanelCenter.show(panelCenter, panelBillHistory.getName());
                        } else if (label == labelSettings) {
                            cardLayoutPanelCenter.show(panelCenter, panelSetting.getName());
                        }
                    }
                }
            });
        }
        labelSignOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                labelSignOut.setBackground(AppConstant.COLOR_MENU_MOUSE_ENTER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelSignOut.setBackground(AppConstant.COLOR_MENU_MOUSE_EXIT);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mainFrameController.requestSignOut();
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrameController.requestCloseProgram();
            }
        });
    }

    @Override
    public void updateSettingObserver() {
        AppSetting.Language appLanguage = AppSetting.getInstance().getAppLanguage();

        switch (appLanguage) {
            case ENGLISH: {
                labelProfile.setText("Profile");
                labelBillCreate.setText("Create bill");
                labelBillHistory.setText("View bill");
                labelSettings.setText("Settings");
                labelSignOut.setText("Sign out");
                break;
            }
            case VIETNAMESE: {
                labelProfile.setText("Thông tin cá nhân");
                labelBillCreate.setText("Tạo hóa đơn");
                labelBillHistory.setText("Lịch sử hóa đơn");
                labelSettings.setText("Thiết lập");
                labelSignOut.setText("Thoát");
                break;
            }
        }
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Application message" , JOptionPane.ERROR_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/error.png")));
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Application message" , JOptionPane.INFORMATION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/infor.png")));
    }

    @Override
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Application message" , JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/warning.png")));
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
        labelBillCreate = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        labelBillHistory = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        labelSettings = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        labelSignOut = new javax.swing.JLabel();
        panelCenter = new javax.swing.JPanel();
        panelBillHistory = new view.bill.BillHistoryPanel();
        panelBillCreate = new view.bill.BillCreatePanel();
        panelProfile = new view.profile.ProfilePanel();
        panelSetting = new view.main.cashier.CashierSettingsPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1600, 1020));

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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Bakery Management System");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel2.setMinimumSize(new java.awt.Dimension(200, 37));
        jLabel2.setPreferredSize(new java.awt.Dimension(355, 20));
        jPanel2.add(jLabel2, java.awt.BorderLayout.CENTER);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("v1.0");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel3.setPreferredSize(new java.awt.Dimension(31, 30));
        jPanel2.add(jLabel3, java.awt.BorderLayout.SOUTH);

        panelTitle.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(62, 120, 207));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Develop by Phuc Team");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel5.setPreferredSize(new java.awt.Dimension(165, 30));
        jPanel3.add(jLabel5, java.awt.BorderLayout.SOUTH);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Copyright © 2021 BMS Program");
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
        labelProfile.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
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

        labelBillCreate.setBackground(new java.awt.Color(113, 168, 255));
        labelBillCreate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelBillCreate.setForeground(new java.awt.Color(255, 255, 255));
        labelBillCreate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBillCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Bill_50px.png"))); // NOI18N
        labelBillCreate.setText("Create Bill");
        labelBillCreate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelBillCreate.setIconTextGap(10);
        labelBillCreate.setOpaque(true);
        labelBillCreate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator2.setBackground(new java.awt.Color(235, 235, 235));
        jSeparator2.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelBillCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(labelBillCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.add(jPanel6);

        labelBillHistory.setBackground(new java.awt.Color(113, 168, 255));
        labelBillHistory.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelBillHistory.setForeground(new java.awt.Color(255, 255, 255));
        labelBillHistory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBillHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/BillHistory_50px.png"))); // NOI18N
        labelBillHistory.setText("View Bill");
        labelBillHistory.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelBillHistory.setIconTextGap(10);
        labelBillHistory.setOpaque(true);
        labelBillHistory.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator3.setBackground(new java.awt.Color(235, 235, 235));
        jSeparator3.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelBillHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(labelBillHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.add(jPanel7);

        labelSettings.setBackground(new java.awt.Color(113, 168, 255));
        labelSettings.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
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

        labelSignOut.setBackground(new java.awt.Color(113, 168, 255));
        labelSignOut.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelSignOut.setForeground(new java.awt.Color(255, 255, 255));
        labelSignOut.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSignOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Exit_20px.png"))); // NOI18N
        labelSignOut.setText("Sign out");
        labelSignOut.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        labelSignOut.setIconTextGap(10);
        labelSignOut.setOpaque(true);
        labelSignOut.setPreferredSize(new java.awt.Dimension(80, 50));
        panelSide.add(labelSignOut, java.awt.BorderLayout.SOUTH);

        jPanel1.add(panelSide, java.awt.BorderLayout.WEST);

        panelCenter.setBackground(new java.awt.Color(255, 255, 255));
        panelCenter.setPreferredSize(new java.awt.Dimension(100, 100));
        panelCenter.setLayout(new java.awt.CardLayout());
        panelCenter.add(panelBillHistory, "BillHistory");
        panelCenter.add(panelBillCreate, "BillCreate");
        panelCenter.add(panelProfile, "Profile");

        panelSetting.setName("Setting"); // NOI18N
        panelCenter.add(panelSetting, "Setting");

        jPanel1.add(panelCenter, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JLabel labelBillCreate;
    private javax.swing.JLabel labelBillHistory;
    private javax.swing.JLabel labelProfile;
    private javax.swing.JLabel labelSettings;
    private javax.swing.JLabel labelSignOut;
    private view.bill.BillCreatePanel panelBillCreate;
    private view.bill.BillHistoryPanel panelBillHistory;
    private javax.swing.JPanel panelCenter;
    private view.profile.ProfilePanel panelProfile;
    private view.main.cashier.CashierSettingsPanel panelSetting;
    private javax.swing.JPanel panelSide;
    private javax.swing.JPanel panelTitle;
    // End of variables declaration//GEN-END:variables
}
