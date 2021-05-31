/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Minh Tu
 */
 enum ButtonMode{
    HOME, SELLING, STATISTICS, PRODUCT, EMPLOYEE  
}

 class imageButtonIcon{
        public JButton iconButton, menuButton;
        public String unselectedIconImage, unselectedButtonImage;
        public String selectedIconImage, selectedButtonImage;
        public imageButtonIcon(JButton iconButton, JButton menuButton, String unselectedIconImage, String unselectedButtonImage,
                String selectedIconImage, String selectedButtonImage){
            this.iconButton = iconButton;
            this.menuButton = menuButton;
            this.unselectedButtonImage = unselectedButtonImage;
            this.unselectedIconImage = unselectedIconImage;
            this.selectedButtonImage = selectedButtonImage;
            this.selectedIconImage = selectedIconImage;      
    }
}
public class SideMenuPanel extends javax.swing.JPanel {

    
    imageButtonIcon[] buttonList;
    JLabel title;
    JPanel functionPanel;
    public static final int SIDE_MENU_CLOSED_WIDTH = 72;
    public static final int SIDE_MENU_OPENED_WIDTH = 282;
 //   public static final int getHeight() = 959;
    /**
     * Creates new form SideMenu
     */
   public SideMenuPanel(){
        initComponents();
        this.setPreferredSize(new Dimension(SIDE_MENU_CLOSED_WIDTH, getHeight()));
       
        customButtonInit();
        setALForButtons();
        this.addMouseListener(new CustomOpenCloseMenu());
        this.setPreferredSize(new Dimension(SIDE_MENU_CLOSED_WIDTH, getHeight()));
   }
   public SideMenuPanel(JLabel title, JPanel functionPanel){
        initComponents();
        this.title = title;
        this.functionPanel = functionPanel;
        
        customButtonInit();
        setALForButtons();
        this.addMouseListener(new CustomOpenCloseMenu());
        this.setPreferredSize(new Dimension(SIDE_MENU_CLOSED_WIDTH, getHeight()));
        
    }
   
   
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_longButton = new javax.swing.JPanel();
        button_Home = new javax.swing.JButton();
        button_Selling = new javax.swing.JButton();
        button_Statistics = new javax.swing.JButton();
        button_Management = new javax.swing.JButton();
        button_Employee = new javax.swing.JButton();
        panel_IconButton = new javax.swing.JPanel();
        button_IconHome = new javax.swing.JButton();
        button_IconSelling = new javax.swing.JButton();
        button_IconStatistics = new javax.swing.JButton();
        button_IconManagement = new javax.swing.JButton();
        button_IconEmployee = new javax.swing.JButton();
        button_signOut = new javax.swing.JButton();
        label_EmpName = new javax.swing.JLabel();
        label_GreetingsAndPos = new javax.swing.JLabel();
        label_Menu = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));
        setPreferredSize(new java.awt.Dimension(282, 970));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });

        panel_longButton.setBackground(new java.awt.Color(204, 204, 204));
        panel_longButton.setForeground(new java.awt.Color(204, 204, 204));
        panel_longButton.setLayout(new java.awt.GridLayout(0, 1, 0, 60));

        button_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Selected_Home.png"))); // NOI18N
        button_Home.setBorder(null);
        button_Home.setBorderPainted(false);
        button_Home.setContentAreaFilled(false);
        button_Home.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_Home.setFocusPainted(false);
        button_Home.setName("HOME_MENU"); // NOI18N
        panel_longButton.add(button_Home);

        button_Selling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Unselected_Selling.png"))); // NOI18N
        button_Selling.setBorder(null);
        button_Selling.setBorderPainted(false);
        button_Selling.setContentAreaFilled(false);
        button_Selling.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_Selling.setFocusPainted(false);
        button_Selling.setName("SELLING_MENU"); // NOI18N
        button_Selling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_SellingActionPerformed(evt);
            }
        });
        panel_longButton.add(button_Selling);

        button_Statistics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Unselected_Statistics.png"))); // NOI18N
        button_Statistics.setBorder(null);
        button_Statistics.setBorderPainted(false);
        button_Statistics.setContentAreaFilled(false);
        button_Statistics.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_Statistics.setFocusPainted(false);
        button_Statistics.setName("STATISTICS_MENU"); // NOI18N
        panel_longButton.add(button_Statistics);

        button_Management.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Unselected_Management.png"))); // NOI18N
        button_Management.setBorder(null);
        button_Management.setBorderPainted(false);
        button_Management.setContentAreaFilled(false);
        button_Management.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_Management.setFocusPainted(false);
        button_Management.setName("PRODUCT_MENU"); // NOI18N
        panel_longButton.add(button_Management);

        button_Employee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Unselected_Employee.png"))); // NOI18N
        button_Employee.setBorder(null);
        button_Employee.setBorderPainted(false);
        button_Employee.setContentAreaFilled(false);
        button_Employee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_Employee.setFocusPainted(false);
        button_Employee.setName("EMPLOYEE_MENU"); // NOI18N
        panel_longButton.add(button_Employee);

        panel_IconButton.setBackground(new java.awt.Color(204, 204, 204));
        panel_IconButton.setForeground(new java.awt.Color(204, 204, 204));
        panel_IconButton.setLayout(new java.awt.GridLayout(0, 1, 0, 60));

        button_IconHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/selectedHome_45px.png"))); // NOI18N
        button_IconHome.setContentAreaFilled(false);
        button_IconHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_IconHome.setFocusPainted(false);
        button_IconHome.setName("HOME_ICON"); // NOI18N
        panel_IconButton.add(button_IconHome);

        button_IconSelling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/unselectedSelling_45px.png"))); // NOI18N
        button_IconSelling.setContentAreaFilled(false);
        button_IconSelling.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_IconSelling.setFocusPainted(false);
        button_IconSelling.setName("SELLING_ICON"); // NOI18N
        panel_IconButton.add(button_IconSelling);

        button_IconStatistics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/unselectedStatistics_45px.png"))); // NOI18N
        button_IconStatistics.setContentAreaFilled(false);
        button_IconStatistics.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_IconStatistics.setFocusPainted(false);
        button_IconStatistics.setName("STATISTICS_ICON"); // NOI18N
        panel_IconButton.add(button_IconStatistics);

        button_IconManagement.setBackground(new java.awt.Color(204, 204, 204));
        button_IconManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/unselectedManagement_45px.png"))); // NOI18N
        button_IconManagement.setContentAreaFilled(false);
        button_IconManagement.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_IconManagement.setFocusPainted(false);
        button_IconManagement.setName("PRODUCT_ICON"); // NOI18N
        panel_IconButton.add(button_IconManagement);

        button_IconEmployee.setBackground(new java.awt.Color(204, 204, 204));
        button_IconEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/unselectedEmployee_45px.png"))); // NOI18N
        button_IconEmployee.setContentAreaFilled(false);
        button_IconEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_IconEmployee.setFocusPainted(false);
        button_IconEmployee.setName("EMPLOYEE_ICON"); // NOI18N
        panel_IconButton.add(button_IconEmployee);

        button_signOut.setBackground(new java.awt.Color(255, 255, 255));
        button_signOut.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        button_signOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buttonIMG_signOut.png"))); // NOI18N
        button_signOut.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        button_signOut.setBorderPainted(false);
        button_signOut.setContentAreaFilled(false);
        button_signOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_signOut.setFocusPainted(false);
        button_signOut.setPreferredSize(new java.awt.Dimension(75, 25));
        button_signOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_signOutActionPerformed(evt);
            }
        });

        label_EmpName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_EmpName.setForeground(new java.awt.Color(0, 102, 255));
        label_EmpName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_EmpName.setText("Nguyễn Ngọc Minh Tú");
        label_EmpName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Font font = label_EmpName.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        label_EmpName.setFont(font.deriveFont(attributes));
        label_EmpName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                label_EmpNameMousePressed(evt);
            }
        });

        label_GreetingsAndPos.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        label_GreetingsAndPos.setForeground(new java.awt.Color(102, 102, 102));
        label_GreetingsAndPos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_GreetingsAndPos.setText("Hello, manager");

        label_Menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/unselectedMenu_45px.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_IconButton, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel_longButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_Menu)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_EmpName, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_GreetingsAndPos, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_signOut, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(label_GreetingsAndPos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_EmpName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label_Menu)))
                .addGap(76, 76, 76)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_IconButton, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_longButton, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(button_signOut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void customButtonInit(){
        imageButtonIcon set1 = new imageButtonIcon(button_IconHome, button_Home, "/img/unselectedHome_45px.png",
            "/img/Unselected_Home.png", "/img/selectedHome_45px.png", "/img/Selected_Home.png");
        imageButtonIcon set2 = new imageButtonIcon(button_IconSelling, button_Selling, "/img/unselectedSelling_45px.png",
            "/img/Unselected_Selling.png", "/img/selectedSelling_45px.png", "/img/Selected_Selling.png");
        imageButtonIcon set3 = new imageButtonIcon(button_IconStatistics, button_Statistics, "/img/unselectedStatistics_45px.png",
            "/img/Unselected_Statistics.png", "/img/selectedStatistics_45px.png", "/img/Selected_Statistics.png");
        imageButtonIcon set4 = new imageButtonIcon(button_IconManagement, button_Management, "/img/unselectedManagement_45px.png",
            "/img/Unselected_Management.png", "/img/selectedManagement_45px.png", "/img/Selected_Management.png");
        imageButtonIcon set5 = new imageButtonIcon(button_IconEmployee, button_Employee, "/img/unselectedEmployee_45px.png",
            "/img/Unselected_Employee.png", "/img/selectedEmployee_45px.png", "/img/Selected_Employee.png");
        
        buttonList = new imageButtonIcon[]{set1, set2, set3, set4, set5};
    }
    
    private class action_MenuButtonClicked implements ActionListener{
        imageButtonIcon[] buttonList;
        ButtonMode mode;
        boolean isIconButton;
        public action_MenuButtonClicked(imageButtonIcon[] buttonList, ButtonMode selectedMode, boolean isIconButton){
            this.mode = selectedMode;
            this.buttonList = buttonList;
            this.isIconButton = isIconButton;
        }
        //new javax.swing.ImageIcon(getClass().getResource("/img/Unselected_Selling.png"))
        @Override
        public void actionPerformed(ActionEvent event){
            for ( imageButtonIcon element : buttonList){
                if (element.iconButton.getName().substring(0, 3).equals(mode.name().substring(0,3))){
                    element.iconButton.setIcon(new ImageIcon(getClass().getResource(element.selectedIconImage)));
                    element.menuButton.setIcon(new ImageIcon(getClass().getResource(element.selectedButtonImage)));   
                    
                    title.setText(mode.name());
                    String cardName = String.valueOf(mode.name().charAt(0)).toUpperCase() + mode.name()
                            .toLowerCase().substring(1,mode.name().length());
                    showCardMenu(cardName);
                    if(!isIconButton){
                        setPreferredSize(new Dimension(SIDE_MENU_OPENED_WIDTH, getHeight()));
                    }
                    else {
                        setPreferredSize(new Dimension(SIDE_MENU_CLOSED_WIDTH, getHeight()));                          
                    } 
                }else{
                    element.iconButton.setIcon(new ImageIcon(getClass().getResource(element.unselectedIconImage)));
                    element.menuButton.setIcon(new ImageIcon(getClass().getResource(element.unselectedButtonImage)));    
                }
            }
        }
    }
    
    private void setALForButtons(){
        button_IconHome.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.HOME, true));
        button_Home.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.HOME, false));
        
        button_IconSelling.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.SELLING, true));
        button_Selling.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.SELLING, false));
        
        button_IconStatistics.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.STATISTICS, true));
        button_Statistics.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.STATISTICS, false));
        
        button_IconManagement.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.PRODUCT, true));
        button_Management.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.PRODUCT, false));
        
        button_IconEmployee.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.EMPLOYEE, true));
        button_Employee.addActionListener(new action_MenuButtonClicked(buttonList, ButtonMode.EMPLOYEE, false));
        
        
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
                            OpenMenu openM = new OpenMenu();
                            openM.start();
                        }
                    }

                }
            }, 400);
        }

        @Override
        public void mouseExited(MouseEvent evt) {
            longEnough = false;
            if (controlMenuIsOpened() && (evt.getX() > SIDE_MENU_OPENED_WIDTH - 1 || evt.getX() < 1)) {

                CloseMenu closeM = new CloseMenu();
                closeM.start();
            }
            setPreferredSize(new Dimension(SIDE_MENU_CLOSED_WIDTH, getHeight()));
        }
    }
    private void button_signOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_signOutActionPerformed
     
    }//GEN-LAST:event_button_signOutActionPerformed

    private void label_EmpNameMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_EmpNameMousePressed
       
    }//GEN-LAST:event_label_EmpNameMousePressed

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseEntered

    private void button_SellingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_SellingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_SellingActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_Employee;
    private javax.swing.JButton button_Home;
    private javax.swing.JButton button_IconEmployee;
    private javax.swing.JButton button_IconHome;
    private javax.swing.JButton button_IconManagement;
    private javax.swing.JButton button_IconSelling;
    private javax.swing.JButton button_IconStatistics;
    private javax.swing.JButton button_Management;
    private javax.swing.JButton button_Selling;
    private javax.swing.JButton button_Statistics;
    private javax.swing.JButton button_signOut;
    private javax.swing.JLabel label_EmpName;
    private javax.swing.JLabel label_GreetingsAndPos;
    private javax.swing.JLabel label_Menu;
    private javax.swing.JPanel panel_IconButton;
    private javax.swing.JPanel panel_longButton;
    // End of variables declaration//GEN-END:variables
}
