package view.main;

import control.app.AppControllerInterface;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import model.user.UserModelInterface;
import view.SideMenuPanel;
import view.TitleMainFrame;
import view.function.EmployeeManagePanel;
import view.function.HomePanel;
import view.function.StatisticsPanel;
import view.function.bill.BillManagePanel;
import view.function.product.ProductManagePanel;

public class MainFrame extends javax.swing.JFrame {
    
    private volatile static MainFrame uniqueInstance;
    
    private UserModelInterface model;
    private AppControllerInterface controller;

    private MainFrame(AppControllerInterface controller, UserModelInterface model) {
        initComponents();

        this.model = model;
        this.controller = controller;

        createView();
    }
    
    public static MainFrame getInstance(AppControllerInterface controller, 
            UserModelInterface model) {
        if (uniqueInstance == null) {
            synchronized (MainFrame.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new MainFrame(controller, model);
                }
            }
        }
        return uniqueInstance;
    }

    private void createView() {
        panelSideMenu.setLabelUserNameText(this.model.getUserName());
        this.getContentPane().setBackground(new Color(225, 229, 234));
        showFirstCardEnable();
    }

    public void showFirstCardEnable() {
        CardLayout cardLayout = (CardLayout) panelManage.getLayout();

        JPanel[] panels = new JPanel[]{
            panelHome, panelBill, panelProduct, panelStatistics, panelManage, panelEmployeeManage
        };

        for (int i = 0; i < panels.length; i++) {
            if (panels[i].isEnabled()) {
                cardLayout.show(panelManage, panels[i].getName());
            }
        }
    }

    public BillManagePanel getPanelBill() {
        return panelBill;
    }

    public EmployeeManagePanel getPanelEmployeeManage() {
        return panelEmployeeManage;
    }

    public HomePanel getPanelHome() {
        return panelHome;
    }

    public JPanel getPanelManage() {
        return panelManage;
    }

    public ProductManagePanel getPanelProduct() {
        return panelProduct;
    }

    public SideMenuPanel getPanelSideMenu() {
        return panelSideMenu;
    }

    public StatisticsPanel getPanelStatistics() {
        return panelStatistics;
    }

    public TitleMainFrame getPanelTitle() {
        return panelTitle;
    }

    public void setHomeFunctionState(boolean flag) {
        this.panelSideMenu.setHomeFunctionState(flag);
        this.panelHome.setEnabled(flag);
    }

    public void setSellFunctionState(boolean flag) {
        this.panelSideMenu.setSellButtonState(flag);
        this.panelBill.setEnabled(flag);
    }

    public void setStatisticFunctionState(boolean flag) {
        this.panelSideMenu.setStatisticButtonState(flag);
        this.panelStatistics.setEnabled(flag);
    }

    public void setProductManageFunctionState(boolean flag) {
        this.panelSideMenu.setProductManageButtonState(flag);
        this.panelProduct.setEnabled(flag);
    }

    public void setEmployeeFunctionState(boolean flag) {
        this.panelSideMenu.setEmployeeButtonState(flag);
        this.panelEmployeeManage.setEnabled(flag);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        panelTitle = new view.TitleMainFrame();
        panelManage = new javax.swing.JPanel();
        panelEmployeeManage = view.function.EmployeeManagePanel.getInstance();
        panelHome = view.function.HomePanel.getInstance(new ImageIcon(getClass().getResource("/img/homeBackground.png")).getImage());
        panelStatistics = view.function.StatisticsPanel().getInstance();
        panelProduct = new view.function.product.ProductManagePanel();
        panelBill = new view.function.bill.BillManagePanel();
        panelSideMenu = new view.SideMenuPanel(panelTitle.getLabel_title(), panelManage);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLayeredPane1.setBackground(new java.awt.Color(225, 229, 234));
        jLayeredPane1.setForeground(new java.awt.Color(225, 229, 234));

        panelManage.setLayout(new java.awt.CardLayout());

        panelEmployeeManage.setName("Employee"); // NOI18N
        panelManage.add(panelEmployeeManage, "Employee");

        panelHome.setName("Home"); // NOI18N
        panelManage.add(panelHome, "Home");

        panelStatistics.setName("Statistics"); // NOI18N
        panelManage.add(panelStatistics, "Statistics");

        panelProduct.setName("Product"); // NOI18N
        panelManage.add(panelProduct, "Product");

        panelBill.setName("Selling"); // NOI18N
        panelManage.add(panelBill, "Selling");

        jLayeredPane1.setLayer(panelTitle, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(panelManage, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(panelSideMenu, javax.swing.JLayeredPane.DRAG_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSideMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelManage, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(panelManage, javax.swing.GroupLayout.PREFERRED_SIZE, 844, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(panelSideMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jLayeredPane1;
    private view.function.bill.BillManagePanel panelBill;
    private view.function.EmployeeManagePanel panelEmployeeManage;
    private view.function.HomePanel panelHome;
    private javax.swing.JPanel panelManage;
    private view.function.product.ProductManagePanel panelProduct;
    private view.SideMenuPanel panelSideMenu;
    private view.function.StatisticsPanel panelStatistics;
    private view.TitleMainFrame panelTitle;
    // End of variables declaration//GEN-END:variables

}
