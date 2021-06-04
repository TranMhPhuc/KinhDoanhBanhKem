package view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.user.UserModelInterface;
import util.swing.UIControl;
import view.dialog.ConfirmSigningOutDialog;
import view.dialog.PersonalProfileDialog;
import control.login.LoginControllerInterface;

enum ButtonMode {
    HOME, SELLING, STATISTICS, PRODUCT, EMPLOYEE
}

class ImageButtonIcon {

    public JButton iconButton, menuButton;
    public String unselectedIconImage, unselectedButtonImage;
    public String selectedIconImage, selectedButtonImage;

    public ImageButtonIcon(JButton iconButton, JButton menuButton,
            String unselectedIconImage, String unselectedButtonImage,
            String selectedIconImage, String selectedButtonImage) {
        this.iconButton = iconButton;
        this.menuButton = menuButton;
        this.unselectedButtonImage = unselectedButtonImage;
        this.unselectedIconImage = unselectedIconImage;
        this.selectedButtonImage = selectedButtonImage;
        this.selectedIconImage = selectedIconImage;
    }
}

public class SideMenuPanel extends javax.swing.JPanel {

    public static final int SIDE_MENU_CLOSED_WIDTH = 72;
    public static final int SIDE_MENU_OPENED_WIDTH = 282;

    private ImageButtonIcon[] buttonList;
    private JLabel title;
    private JPanel functionPanel;
    private PersonalProfileDialog profile = new PersonalProfileDialog(null, true);
    private ConfirmSigningOutDialog signOut = new ConfirmSigningOutDialog(null, true);

    private UserModelInterface model;
    private LoginControllerInterface controller;

    /**
     * Creates new form SideMenu
     */
    public SideMenuPanel() {
        initComponents();
        this.setPreferredSize(new Dimension(SIDE_MENU_CLOSED_WIDTH, getHeight()));

        customButtonInit();
        setALForButtons();
        this.addMouseListener(new CustomOpenCloseMenu());
        this.setPreferredSize(new Dimension(SIDE_MENU_CLOSED_WIDTH, getHeight()));
    }

    public SideMenuPanel(JLabel title, JPanel functionPanel) {
        initComponents();
        this.title = title;
        this.functionPanel = functionPanel;

        customButtonInit();
        setALForButtons();

        this.addMouseListener(new CustomOpenCloseMenu());
        this.setPreferredSize(new Dimension(SIDE_MENU_CLOSED_WIDTH, getHeight()));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLongButton = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        btnSelling = new javax.swing.JButton();
        btnStatistics = new javax.swing.JButton();
        btnProductManagement = new javax.swing.JButton();
        btnEmployee = new javax.swing.JButton();
        panelIconButton = new javax.swing.JPanel();
        btnIconHome = new javax.swing.JButton();
        btnIconSelling = new javax.swing.JButton();
        btnIconStatistics = new javax.swing.JButton();
        btnIconProductManagement = new javax.swing.JButton();
        btnIconEmployee = new javax.swing.JButton();
        btnSignOut = new javax.swing.JButton();
        labelUserName = new javax.swing.JLabel();
        labelGreeting = new javax.swing.JLabel();
        label_Menu = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));
        setPreferredSize(new java.awt.Dimension(282, 970));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });

        panelLongButton.setBackground(new java.awt.Color(204, 204, 204));
        panelLongButton.setForeground(new java.awt.Color(204, 204, 204));
        panelLongButton.setLayout(new java.awt.GridLayout(0, 1, 0, 60));

        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Selected_Home.png"))); // NOI18N
        btnHome.setBorder(null);
        btnHome.setBorderPainted(false);
        btnHome.setContentAreaFilled(false);
        btnHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHome.setFocusPainted(false);
        btnHome.setName("HOME_MENU"); // NOI18N
        panelLongButton.add(btnHome);

        btnSelling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Unselected_Selling.png"))); // NOI18N
        btnSelling.setBorder(null);
        btnSelling.setBorderPainted(false);
        btnSelling.setContentAreaFilled(false);
        btnSelling.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSelling.setFocusPainted(false);
        btnSelling.setName("SELLING_MENU"); // NOI18N
        btnSelling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSellingActionPerformed(evt);
            }
        });
        panelLongButton.add(btnSelling);

        btnStatistics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Unselected_Statistics.png"))); // NOI18N
        btnStatistics.setBorder(null);
        btnStatistics.setBorderPainted(false);
        btnStatistics.setContentAreaFilled(false);
        btnStatistics.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStatistics.setFocusPainted(false);
        btnStatistics.setName("STATISTICS_MENU"); // NOI18N
        panelLongButton.add(btnStatistics);

        btnProductManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Unselected_Management.png"))); // NOI18N
        btnProductManagement.setBorder(null);
        btnProductManagement.setBorderPainted(false);
        btnProductManagement.setContentAreaFilled(false);
        btnProductManagement.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProductManagement.setFocusPainted(false);
        btnProductManagement.setName("PRODUCT_MENU"); // NOI18N
        panelLongButton.add(btnProductManagement);

        btnEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Unselected_Employee.png"))); // NOI18N
        btnEmployee.setBorder(null);
        btnEmployee.setBorderPainted(false);
        btnEmployee.setContentAreaFilled(false);
        btnEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEmployee.setFocusPainted(false);
        btnEmployee.setName("EMPLOYEE_MENU"); // NOI18N
        panelLongButton.add(btnEmployee);

        panelIconButton.setBackground(new java.awt.Color(204, 204, 204));
        panelIconButton.setForeground(new java.awt.Color(204, 204, 204));
        panelIconButton.setLayout(new java.awt.GridLayout(0, 1, 0, 60));

        btnIconHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/selectedHome_45px.png"))); // NOI18N
        btnIconHome.setContentAreaFilled(false);
        btnIconHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIconHome.setFocusPainted(false);
        btnIconHome.setName("HOME_ICON"); // NOI18N
        panelIconButton.add(btnIconHome);

        btnIconSelling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/unselectedSelling_45px.png"))); // NOI18N
        btnIconSelling.setContentAreaFilled(false);
        btnIconSelling.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIconSelling.setFocusPainted(false);
        btnIconSelling.setName("SELLING_ICON"); // NOI18N
        panelIconButton.add(btnIconSelling);

        btnIconStatistics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/unselectedStatistics_45px.png"))); // NOI18N
        btnIconStatistics.setContentAreaFilled(false);
        btnIconStatistics.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIconStatistics.setFocusPainted(false);
        btnIconStatistics.setName("STATISTICS_ICON"); // NOI18N
        panelIconButton.add(btnIconStatistics);

        btnIconProductManagement.setBackground(new java.awt.Color(204, 204, 204));
        btnIconProductManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/unselectedManagement_45px.png"))); // NOI18N
        btnIconProductManagement.setContentAreaFilled(false);
        btnIconProductManagement.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIconProductManagement.setFocusPainted(false);
        btnIconProductManagement.setName("PRODUCT_ICON"); // NOI18N
        panelIconButton.add(btnIconProductManagement);

        btnIconEmployee.setBackground(new java.awt.Color(204, 204, 204));
        btnIconEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/unselectedEmployee_45px.png"))); // NOI18N
        btnIconEmployee.setContentAreaFilled(false);
        btnIconEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIconEmployee.setFocusPainted(false);
        btnIconEmployee.setName("EMPLOYEE_ICON"); // NOI18N
        panelIconButton.add(btnIconEmployee);

        btnSignOut.setBackground(new java.awt.Color(255, 255, 255));
        btnSignOut.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnSignOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttonIMG_signOut.png"))); // NOI18N
        btnSignOut.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSignOut.setBorderPainted(false);
        btnSignOut.setContentAreaFilled(false);
        btnSignOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSignOut.setFocusPainted(false);
        btnSignOut.setPreferredSize(new java.awt.Dimension(75, 25));
        btnSignOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignOutActionPerformed(evt);
            }
        });

        labelUserName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelUserName.setForeground(new java.awt.Color(0, 102, 255));
        labelUserName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelUserName.setText("Nguyễn Ngọc Minh Tú");
        labelUserName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Font font = labelUserName.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        labelUserName.setFont(font.deriveFont(attributes));
        labelUserName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                labelUserNameMousePressed(evt);
            }
        });

        labelGreeting.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        labelGreeting.setForeground(new java.awt.Color(102, 102, 102));
        labelGreeting.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGreeting.setText("Hello, manager");

        label_Menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/unselectedMenu_45px.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelIconButton, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelLongButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_Menu)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelGreeting, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSignOut, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(labelGreeting)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label_Menu)))
                .addGap(76, 76, 76)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelIconButton, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelLongButton, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(btnSignOut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void customButtonInit() {
        ImageButtonIcon set1 = new ImageButtonIcon(btnIconHome, btnHome, "/img/unselectedHome_45px.png",
                "/img/Unselected_Home.png", "/img/selectedHome_45px.png", "/img/Selected_Home.png");
        ImageButtonIcon set2 = new ImageButtonIcon(btnIconSelling, btnSelling, "/img/unselectedSelling_45px.png",
                "/img/Unselected_Selling.png", "/img/selectedSelling_45px.png", "/img/Selected_Selling.png");
        ImageButtonIcon set3 = new ImageButtonIcon(btnIconStatistics, btnStatistics, "/img/unselectedStatistics_45px.png",
                "/img/Unselected_Statistics.png", "/img/selectedStatistics_45px.png", "/img/Selected_Statistics.png");
        ImageButtonIcon set4 = new ImageButtonIcon(btnIconProductManagement, btnProductManagement, "/img/unselectedManagement_45px.png",
                "/img/Unselected_Management.png", "/img/selectedManagement_45px.png", "/img/Selected_Management.png");
        ImageButtonIcon set5 = new ImageButtonIcon(btnIconEmployee, btnEmployee, "/img/unselectedEmployee_45px.png",
                "/img/Unselected_Employee.png", "/img/selectedEmployee_45px.png", "/img/Selected_Employee.png");

        buttonList = new ImageButtonIcon[]{set1, set2, set3, set4, set5};
    }

    public void setHomeFunctionState(boolean flag) {
        btnHome.setEnabled(flag);
        btnIconHome.setEnabled(flag);
    }

    public void setSellButtonState(boolean flag) {
        btnSelling.setEnabled(flag);
        btnIconSelling.setEnabled(flag);
    }

    public void setStatisticButtonState(boolean flag) {
        btnStatistics.setEnabled(flag);
        btnIconStatistics.setEnabled(flag);
    }

    public void setProductManageButtonState(boolean flag) {
        btnProductManagement.setEnabled(flag);
        btnIconProductManagement.setEnabled(flag);
    }

    public void setEmployeeButtonState(boolean flag) {
        btnEmployee.setEnabled(flag);
        btnIconEmployee.setEnabled(flag);
    }

    public void setLabelGreetingText(String text) {
        labelGreeting.setText(text);
    }

    public void setLabelUserNameText(String userName) {
        labelUserName.setText(userName);
    }

    private class action_MenuButtonClicked implements ActionListener {

        ImageButtonIcon[] buttonList;
        ButtonMode mode;
        boolean isIconButton;

        public action_MenuButtonClicked(ImageButtonIcon[] buttonList, ButtonMode selectedMode, boolean isIconButton) {
            this.mode = selectedMode;
            this.buttonList = buttonList;
            this.isIconButton = isIconButton;
        }

        //new javax.swing.ImageIcon(getClass().getResource("/img/Unselected_Selling.png"))
        @Override
        public void actionPerformed(ActionEvent event) {
            for (ImageButtonIcon element : buttonList) {
                if (element.iconButton.getName().substring(0, 3).equals(mode.name().substring(0, 3))) {
                    element.iconButton.setIcon(new ImageIcon(getClass().getResource(element.selectedIconImage)));
                    element.menuButton.setIcon(new ImageIcon(getClass().getResource(element.selectedButtonImage)));

                    title.setText(mode.name());
                    String cardName = String.valueOf(mode.name().charAt(0)).toUpperCase() + mode.name()
                            .toLowerCase().substring(1, mode.name().length());
                    showCardMenu(cardName);
                    if (!isIconButton) {
                        setPreferredSize(new Dimension(SIDE_MENU_OPENED_WIDTH, getHeight()));
                    } else {
                        setPreferredSize(new Dimension(SIDE_MENU_CLOSED_WIDTH, getHeight()));
                    }
                } else {
                    element.iconButton.setIcon(new ImageIcon(getClass().getResource(element.unselectedIconImage)));
                    element.menuButton.setIcon(new ImageIcon(getClass().getResource(element.unselectedButtonImage)));
                }
            }
        }
    }

    private void setALForButtons() {
        btnIconHome.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.HOME, true));
        btnHome.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.HOME, false));

        btnIconSelling.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.SELLING, true));
        btnSelling.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.SELLING, false));

        btnIconStatistics.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.STATISTICS, true));
        btnStatistics.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.STATISTICS, false));

        btnIconProductManagement.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.PRODUCT, true));
        btnProductManagement.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.PRODUCT, false));

        btnIconEmployee.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.EMPLOYEE, true));
        btnEmployee.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.EMPLOYEE, false));

    }

    public void showCardMenu(String cardName) {
        CardLayout layout = (CardLayout) (functionPanel.getLayout());
        layout.show(functionPanel, cardName);
        functionPanel.repaint();
    }

    class OpenMenu extends Thread {

        Dimension size = new Dimension(SIDE_MENU_CLOSED_WIDTH, getHeight());

        @Override
        public void run() {
            try {
                for (int i = 0; i < 21; i++) {
                    size.setSize(size.width + 10, size.height);
                    setSize(size);
                    if (i != 20) {
                        Thread.sleep(7);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    class CloseMenu extends Thread {

        Dimension size = new Dimension(SIDE_MENU_OPENED_WIDTH, getHeight());

        @Override
        public void run() {
            try {
                for (int i = 0; i < 21; i++) {
                    size.setSize(size.width - 10, size.height);
                    setSize(size);
                    if (i != 20) {
                        Thread.sleep(7);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    private boolean controlMenuIsOpened() {
        return (this.getWidth() == SIDE_MENU_OPENED_WIDTH);
    }

    class CustomOpenCloseMenu extends MouseAdapter {

        private java.util.Timer delay;
        private boolean longEnough = false;

        @Override
        public void mouseEntered(MouseEvent evt) {
            longEnough = true;
            if (delay == null) {
                delay = new java.util.Timer();
            }
            delay.schedule(new TimerTask() {
                public void run() {
                    if (longEnough) {
                        if (!controlMenuIsOpened()) {
                            OpenMenu openMenu = new OpenMenu();
                            openMenu.start();
                        }
                    }

                }
            }, 400);
        }

        @Override
        public void mouseExited(MouseEvent evt) {
            longEnough = false;
            if (controlMenuIsOpened() && (evt.getX() > SIDE_MENU_OPENED_WIDTH - 1 || evt.getX() < 1)) {

                CloseMenu closeMenu = new CloseMenu();
                closeMenu.start();
            }
            setPreferredSize(new Dimension(SIDE_MENU_CLOSED_WIDTH, getHeight()));
        }
    }
    private void btnSignOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignOutActionPerformed
        UIControl.setLocationCenterForDialog(signOut);
        signOut.setVisible(true);
    }//GEN-LAST:event_btnSignOutActionPerformed

    private void labelUserNameMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelUserNameMousePressed
        UIControl.setLocationCenterForDialog(profile);
        profile.setVisible(true);
    }//GEN-LAST:event_labelUserNameMousePressed

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseEntered

    private void btnSellingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSellingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSellingActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmployee;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnIconEmployee;
    private javax.swing.JButton btnIconHome;
    private javax.swing.JButton btnIconProductManagement;
    private javax.swing.JButton btnIconSelling;
    private javax.swing.JButton btnIconStatistics;
    private javax.swing.JButton btnProductManagement;
    private javax.swing.JButton btnSelling;
    private javax.swing.JButton btnSignOut;
    private javax.swing.JButton btnStatistics;
    private javax.swing.JLabel labelGreeting;
    private javax.swing.JLabel labelUserName;
    private javax.swing.JLabel label_Menu;
    private javax.swing.JPanel panelIconButton;
    private javax.swing.JPanel panelLongButton;
    // End of variables declaration//GEN-END:variables
}
