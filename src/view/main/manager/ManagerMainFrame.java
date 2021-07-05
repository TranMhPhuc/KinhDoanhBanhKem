package view.main.manager;

import control.app.MainFrameControllerInterface;
import control.employee.EmployeeController;
import control.employee.EmployeeControllerInterface;
import control.ingredient.IngredientController;
import control.ingredient.IngredientControllerInterface;
import control.product.ProductController;
import control.product.ProductControllerInterface;
import control.provider.ProviderController;
import control.provider.ProviderControllerInterface;
import control.setting.ManagerSettingController;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.employee.EmployeeManageModel;
import model.employee.EmployeeManageModelInterface;
import model.ingredient.IngredientManageModel;
import model.ingredient.IngredientManageModelInterface;
import model.product.ProductManageModel;
import model.product.ProductManageModelInterface;
import model.provider.ProviderManageModel;
import model.provider.ProviderManageModelInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import model.user.UserModelInterface;
import util.constant.AppConstant;
import util.messages.Messages;
import view.MessageShowing;

public class ManagerMainFrame extends javax.swing.JFrame implements MessageShowing,
        SettingUpdateObserver {

    private UserModelInterface userModel;
    private MainFrameControllerInterface mainFrameController;

    private ProductManageModelInterface productManageModel;
    private ProductControllerInterface productController;

    private IngredientManageModelInterface ingredientManageModel;
    private IngredientControllerInterface ingredientController;

    private ProviderManageModelInterface providerManageModel;
    private ProviderControllerInterface providerController;

    private EmployeeManageModelInterface employeeManageModel;
    private EmployeeControllerInterface employeeController;

    private AppSetting appSettingModel;
    private ManagerSettingController managerSettingController;

    private JLabel choosedLabel;
    private CardLayout cardLayoutPanelCenter;

    public ManagerMainFrame(MainFrameControllerInterface mainFrameController,
            UserModelInterface userModel) {
        this.mainFrameController = mainFrameController;
        this.userModel = userModel;
        this.userModel.setMainFrame(this);

        initComponents();
        this.cardLayoutPanelCenter = (CardLayout) panelCenter.getLayout();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

        panelProduct.setMainFrame(this);
        panelIngredient.setMainFrame(this);
        panelProvider.setMainFrame(this);
        panelEmployee.setMainFrame(this);
        panelSettings.setMainFrame(this);
        
        this.setTitle(AppConstant.MAIN_FRAME_TITLE_MANAGER);
        ImageIcon img = new ImageIcon(getClass().getResource("/img/shop70px.png"));
        this.setIconImage(img.getImage());
        
        createView();
        createControl();
    }

    private void createView() {
        this.choosedLabel = labelHome;
        choosedLabel.setBackground(AppConstant.COLOR_MENU_MOUSE_PRESS);
        cardLayoutPanelCenter.show(panelCenter, panelHome.getName());

        mainFrameController.setProfilePanelView(panelProfile);
        userModel.setProfilePanelView(panelProfile);

        productManageModel = new ProductManageModel();
        productManageModel.registerInsertedProductObserver(panelHome);
        productManageModel.registerModifiedProductObserver(panelHome);
        productManageModel.registerModifiedProductObserver(panelIngredient);
        productManageModel.registerRemovedProductObserver(panelHome);

        productController = new ProductController(productManageModel);
        productController.setProductPanelView(panelProduct);

        ingredientManageModel = new IngredientManageModel();
        ingredientManageModel.registerInsertedIngredientObserver(productController);
        ingredientManageModel.registerModifiedIngredientObserver(productController);

        ingredientManageModel.registerInsertedIngredientObserver(panelHome);
        ingredientManageModel.registerModifiedIngredientObserver(panelHome);
        ingredientManageModel.registerRemovedIngredientObserver(panelHome);

        ingredientController = new IngredientController(ingredientManageModel);
        ingredientController.setIngredientPanelView(panelIngredient);

        providerManageModel = new ProviderManageModel();
        providerManageModel.registerInsertedProviderObserver(panelIngredient);
        providerManageModel.registerModifiedProviderObserver(panelIngredient);
        providerManageModel.registerRemovedProviderObserver(panelIngredient);

        providerManageModel.registerInsertedProviderObserver(panelHome);
        providerManageModel.registerModifiedProviderObserver(panelHome);
        providerManageModel.registerRemovedProviderObserver(panelHome);

        providerController = new ProviderController(providerManageModel);
        providerController.setProviderPanelView(panelProvider);

        employeeManageModel = new EmployeeManageModel();
        employeeController = new EmployeeController(employeeManageModel);
        employeeController.setEmployeePanelView(panelEmployee);

        employeeManageModel.registerInsertedEmployeeObserver(panelHome);
        employeeManageModel.registerModifiedEmployeeObserver(panelHome);

        appSettingModel = AppSetting.getInstance();
        managerSettingController = new ManagerSettingController(appSettingModel);
        managerSettingController.setManagerSettingPanel(panelSettings);

        appSettingModel.registerObserver(this);
        appSettingModel.registerObserver(panelProfile);
        appSettingModel.registerObserver(panelHome);
        appSettingModel.registerObserver(panelProduct);
        appSettingModel.registerObserver(panelIngredient);
        appSettingModel.registerObserver(panelProvider);
        appSettingModel.registerObserver(panelEmployee);
        appSettingModel.registerObserver(Messages.getInstance());
    }

    private void createControl() {
        JLabel[] labels = new JLabel[]{
            labelProfile, labelHome, labelProduct, labelIngredient, labelProvider, labelEmployee, labelSettings
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
                        if (choosedLabel == labelProfile) {
                            if (!mainFrameController.canCloseProfilePanel()) {
                                return;
                            }
                        } else if (choosedLabel == labelProduct) {
                            if (!productController.canCloseProductManagePanel()) {
                                return;
                            }
                        } else if (choosedLabel == labelIngredient) {
                            if (!ingredientController.canCloseIngredientManagePanel()) {
                                return;
                            }
                        } else if (choosedLabel == labelProvider) {
                            if (!providerController.canCloseProviderManagePanel()) {
                                return;
                            }
                        } else if (choosedLabel == labelEmployee) {
                            if (!employeeController.canCloseEmployeeManagePanel()) {
                                return;
                            }
                        }
                        choosedLabel.setBackground(AppConstant.COLOR_MENU_MOUSE_EXIT);
                        label.setBackground(AppConstant.COLOR_MENU_MOUSE_PRESS);
                        choosedLabel = label;
                        if (label == labelProfile) {
                            cardLayoutPanelCenter.show(panelCenter, panelProfile.getName());
                        } else if (label == labelHome) {
                            cardLayoutPanelCenter.show(panelCenter, panelHome.getName());
                        } else if (label == labelProduct) {
                            cardLayoutPanelCenter.show(panelCenter, panelProduct.getName());
                        } else if (label == labelIngredient) {
                            cardLayoutPanelCenter.show(panelCenter, panelIngredient.getName());
                        } else if (label == labelProvider) {
                            cardLayoutPanelCenter.show(panelCenter, panelProvider.getName());
                        } else if (label == labelEmployee) {
                            cardLayoutPanelCenter.show(panelCenter, panelEmployee.getName());
                        } else if (label == labelSettings) {
                            cardLayoutPanelCenter.show(panelCenter, panelSettings.getName());
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
        AppSetting.Language language = AppSetting.getInstance().getAppLanguage();

        switch (language) {
            case ENGLISH: {
                labelProfile.setText("Profile");
                labelProduct.setText("Product");
                labelIngredient.setText("Ingredient");
                labelProvider.setText("Provider");
                labelEmployee.setText("Employee");
                labelSettings.setText("Settings");
                labelSignOut.setText("Sign out");
                break;
            }
            case VIETNAMESE: {
                labelProfile.setText("Thông tin cá nhân");
                labelProduct.setText("Sản phẩm");
                labelIngredient.setText("Nguyên liệu");
                labelProvider.setText("Nhà cung cấp");
                labelEmployee.setText("Nhân viên");
                labelSettings.setText("Thiết lập");
                labelSignOut.setText("Thoát");
                break;
            }
        }

        panelSide.repaint();
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Application message" , JOptionPane.ERROR_MESSAGE, AppConstant.IMAGE_ICON_MESSAGE_DIALOG_ERROR);
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Application message" , JOptionPane.INFORMATION_MESSAGE, AppConstant.IMAGE_ICON_MESSAGE_DIALOG_INFO);
    }

    @Override
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Application message" , JOptionPane.WARNING_MESSAGE, AppConstant.IMAGE_ICON_MESSAGE_DIALOG_WARNING);
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
        labelHome = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        labelProduct = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        labelIngredient = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        labelProvider = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel10 = new javax.swing.JPanel();
        labelEmployee = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel11 = new javax.swing.JPanel();
        labelSettings = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        labelSignOut = new javax.swing.JLabel();
        panelCenter = new javax.swing.JPanel();
        panelProfile = new view.profile.ProfilePanel();
        panelHome = new view.main.manager.HomePanel();
        panelProduct = new view.product.ProductPanel();
        panelIngredient = new view.ingredient.IngredientPanel();
        panelProvider = new view.provider.ProviderPanel();
        panelEmployee = new view.employee.EmployeePanel();
        panelSettings = new view.main.manager.ManagerSettingsPanel();

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

        labelHome.setBackground(new java.awt.Color(113, 168, 255));
        labelHome.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelHome.setForeground(new java.awt.Color(255, 255, 255));
        labelHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Dashboard_50px.png"))); // NOI18N
        labelHome.setText("Home");
        labelHome.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelHome.setIconTextGap(10);
        labelHome.setOpaque(true);
        labelHome.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator2.setBackground(new java.awt.Color(235, 235, 235));
        jSeparator2.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelHome, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(labelHome, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.add(jPanel6);

        labelProduct.setBackground(new java.awt.Color(113, 168, 255));
        labelProduct.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelProduct.setForeground(new java.awt.Color(255, 255, 255));
        labelProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Product_50px.png"))); // NOI18N
        labelProduct.setText("Product");
        labelProduct.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelProduct.setIconTextGap(10);
        labelProduct.setOpaque(true);
        labelProduct.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator3.setBackground(new java.awt.Color(235, 235, 235));
        jSeparator3.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(labelProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.add(jPanel7);

        labelIngredient.setBackground(new java.awt.Color(113, 168, 255));
        labelIngredient.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelIngredient.setForeground(new java.awt.Color(255, 255, 255));
        labelIngredient.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelIngredient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Material_50px.png"))); // NOI18N
        labelIngredient.setText("Ingredient");
        labelIngredient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelIngredient.setIconTextGap(10);
        labelIngredient.setOpaque(true);
        labelIngredient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator4.setBackground(new java.awt.Color(235, 235, 235));
        jSeparator4.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelIngredient, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(labelIngredient, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.add(jPanel8);

        labelProvider.setBackground(new java.awt.Color(113, 168, 255));
        labelProvider.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelProvider.setForeground(new java.awt.Color(255, 255, 255));
        labelProvider.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelProvider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Provider_50px.png"))); // NOI18N
        labelProvider.setText("Provider");
        labelProvider.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelProvider.setIconTextGap(10);
        labelProvider.setOpaque(true);
        labelProvider.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator5.setBackground(new java.awt.Color(235, 235, 235));
        jSeparator5.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(labelProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.add(jPanel9);

        labelEmployee.setBackground(new java.awt.Color(113, 168, 255));
        labelEmployee.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelEmployee.setForeground(new java.awt.Color(255, 255, 255));
        labelEmployee.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Employee_50px.png"))); // NOI18N
        labelEmployee.setText("Employee");
        labelEmployee.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelEmployee.setIconTextGap(10);
        labelEmployee.setOpaque(true);
        labelEmployee.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator6.setBackground(new java.awt.Color(235, 235, 235));
        jSeparator6.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(labelEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.add(jPanel10);

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

        jSeparator7.setBackground(new java.awt.Color(235, 235, 235));
        jSeparator7.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(labelSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.add(jPanel11);

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
        panelCenter.add(panelProfile, "Profile");
        panelProfile.getAccessibleContext().setAccessibleParent(this);

        panelCenter.add(panelHome, "Home");
        panelHome.getAccessibleContext().setAccessibleParent(this);

        panelCenter.add(panelProduct, "Product");
        panelCenter.add(panelIngredient, "Ingredient");
        panelIngredient.getAccessibleContext().setAccessibleParent(this);

        panelCenter.add(panelProvider, "Provider");
        panelProvider.getAccessibleContext().setAccessibleParent(this);

        panelCenter.add(panelEmployee, "Employee");
        panelEmployee.getAccessibleContext().setAccessibleParent(this);

        panelCenter.add(panelSettings, "Settings");
        panelSettings.getAccessibleContext().setAccessibleParent(this);

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
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JLabel labelEmployee;
    private javax.swing.JLabel labelHome;
    private javax.swing.JLabel labelIngredient;
    private javax.swing.JLabel labelProduct;
    private javax.swing.JLabel labelProfile;
    private javax.swing.JLabel labelProvider;
    private javax.swing.JLabel labelSettings;
    private javax.swing.JLabel labelSignOut;
    private javax.swing.JPanel panelCenter;
    private view.employee.EmployeePanel panelEmployee;
    private view.main.manager.HomePanel panelHome;
    private view.ingredient.IngredientPanel panelIngredient;
    private view.product.ProductPanel panelProduct;
    private view.profile.ProfilePanel panelProfile;
    private view.provider.ProviderPanel panelProvider;
    private view.main.manager.ManagerSettingsPanel panelSettings;
    private javax.swing.JPanel panelSide;
    private javax.swing.JPanel panelTitle;
    // End of variables declaration//GEN-END:variables
}
