package view.function.home;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import model.bill.BillManageModel;
import model.bill.BillManageModelInterface;
import model.employee.EmployeeDataStorage;
import model.employee.EmployeeDataStorageInterface;
import model.product.ProductDataStorage;
import model.product.ProductDataStorageInterface;
import view.function.bill.BillUpdateObserver;
import view.function.employee.EmployeeUpdateObserver;
import view.function.product.ProductUpdateObserver;

public class HomePanel extends javax.swing.JPanel implements EmployeeUpdateObserver,
        BillUpdateObserver, ProductUpdateObserver {

    private volatile static HomePanel uniqueInstance;

    private Image img;
    private static EmployeeDataStorageInterface employeeDataStorage;
    private static BillManageModelInterface billManageModel;
    private static ProductDataStorageInterface productDataStorage;

    static {
        employeeDataStorage = EmployeeDataStorage.getInstance();
        billManageModel = new BillManageModel();
        productDataStorage = ProductDataStorage.getInstance();
    }

    private HomePanel(Image img) {
        initComponents();
        this.img = img;
        employeeDataStorage.registerObserver(this);
        billManageModel.registerObserver(this);
        productDataStorage.registerObserver(this);
    }

    public static HomePanel getInstance(Image img) {
        if (img == null) {
            throw new IllegalArgumentException("Image object is null.");
        }
        if (uniqueInstance == null) {
            synchronized (HomePanel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new HomePanel(img);
                }
            }
        } else {
            uniqueInstance.img = img;
        }
        return uniqueInstance;
    }

    public static HomePanel getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

    public void setLabelRevenue(int revenue) {
        this.labelRevenue.setText(String.valueOf(revenue) + "K");
    }

    public void setLabelBill(int billNumber) {
        this.labelBillNumber.setText(String.valueOf(billNumber));
    }

    public void setLabelProfit(int profit) {
        this.labelProfit.setText(String.valueOf(profit) + "K");
    }

    public void setLabelProduct(int productNumber) {
        this.labelProductNumber.setText(String.valueOf(productNumber));
    }

    public void setLabelEmployee(int employeeNumber) {
        this.labelEmployeeNumber.setText(String.valueOf(employeeNumber));
    }

    @Override
    public void updateEmployeeNumber(int employeeNumber) {
        setLabelEmployee(employeeNumber);
    }

    @Override
    public void updateBillNumber(int billNumber) {
        setLabelBill(billNumber);
    }

    @Override
    public void updateProductNumber(int productNumber) {
        setLabelProduct(productNumber);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_Today = new javax.swing.JLabel();
        panelRevenue = new javax.swing.JPanel();
        label_Title_Rev = new javax.swing.JLabel();
        labelRevenue = new javax.swing.JLabel();
        background_Rev = new javax.swing.JLabel();
        panelProfit = new javax.swing.JPanel();
        label_Title_Profit = new javax.swing.JLabel();
        labelProfit = new javax.swing.JLabel();
        background_Profit = new javax.swing.JLabel();
        panelBills = new javax.swing.JPanel();
        label_Title_Bills = new javax.swing.JLabel();
        labelBillNumber = new javax.swing.JLabel();
        background_Bills = new javax.swing.JLabel();
        label_Seperator = new javax.swing.JLabel();
        label_Numbers = new javax.swing.JLabel();
        panelEmployee = new javax.swing.JPanel();
        label_Title_Employee = new javax.swing.JLabel();
        labelEmployeeNumber = new javax.swing.JLabel();
        background_Employee = new javax.swing.JLabel();
        panelCakeType = new javax.swing.JPanel();
        labelTitleProduct = new javax.swing.JLabel();
        labelProductNumber = new javax.swing.JLabel();
        background_CakeType = new javax.swing.JLabel();

        label_Today.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Today.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_today.png"))); // NOI18N

        panelRevenue.setOpaque(false);
        panelRevenue.setLayout(null);

        label_Title_Rev.setFont(new java.awt.Font("Segoe Print", 2, 28)); // NOI18N
        label_Title_Rev.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Title_Rev.setText("Revenue");
        panelRevenue.add(label_Title_Rev);
        label_Title_Rev.setBounds(50, 40, 230, 70);

        labelRevenue.setFont(new java.awt.Font("Segoe Print", 3, 32)); // NOI18N
        labelRevenue.setForeground(new java.awt.Color(253, 94, 83));
        labelRevenue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelRevenue.setText("2000K");
        panelRevenue.add(labelRevenue);
        labelRevenue.setBounds(30, 80, 260, 90);

        background_Rev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/blue_25.png"))); // NOI18N
        background_Rev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                background_RevMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                background_RevMouseExited(evt);
            }
        });
        panelRevenue.add(background_Rev);
        background_Rev.setBounds(30, 20, 270, 190);

        panelProfit.setOpaque(false);
        panelProfit.setLayout(null);

        label_Title_Profit.setFont(new java.awt.Font("Segoe Print", 2, 28)); // NOI18N
        label_Title_Profit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Title_Profit.setText("Profit");
        panelProfit.add(label_Title_Profit);
        label_Title_Profit.setBounds(50, 40, 230, 70);

        labelProfit.setFont(new java.awt.Font("Segoe Print", 3, 32)); // NOI18N
        labelProfit.setForeground(new java.awt.Color(253, 94, 83));
        labelProfit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelProfit.setText("500K");
        panelProfit.add(labelProfit);
        labelProfit.setBounds(30, 80, 260, 90);

        background_Profit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/blue_25.png"))); // NOI18N
        background_Profit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                background_ProfitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                background_ProfitMouseExited(evt);
            }
        });
        panelProfit.add(background_Profit);
        background_Profit.setBounds(30, 20, 270, 190);

        panelBills.setOpaque(false);
        panelBills.setLayout(null);

        label_Title_Bills.setFont(new java.awt.Font("Segoe Print", 2, 28)); // NOI18N
        label_Title_Bills.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Title_Bills.setText("Bills");
        panelBills.add(label_Title_Bills);
        label_Title_Bills.setBounds(50, 40, 230, 70);

        labelBillNumber.setFont(new java.awt.Font("Segoe Print", 3, 32)); // NOI18N
        labelBillNumber.setForeground(new java.awt.Color(253, 94, 83));
        labelBillNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBillNumber.setText("23");
        panelBills.add(labelBillNumber);
        labelBillNumber.setBounds(30, 80, 260, 90);

        background_Bills.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/blue_25.png"))); // NOI18N
        background_Bills.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                background_BillsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                background_BillsMouseExited(evt);
            }
        });
        panelBills.add(background_Bills);
        background_Bills.setBounds(30, 20, 270, 190);

        label_Seperator.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Seperator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/seperator.png"))); // NOI18N

        label_Numbers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Numbers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_numbers.png"))); // NOI18N

        panelEmployee.setOpaque(false);
        panelEmployee.setLayout(null);

        label_Title_Employee.setFont(new java.awt.Font("Segoe Print", 2, 28)); // NOI18N
        label_Title_Employee.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Title_Employee.setText("Employee");
        panelEmployee.add(label_Title_Employee);
        label_Title_Employee.setBounds(50, 40, 230, 70);

        labelEmployeeNumber.setFont(new java.awt.Font("Segoe Print", 3, 32)); // NOI18N
        labelEmployeeNumber.setForeground(new java.awt.Color(253, 94, 83));
        labelEmployeeNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelEmployeeNumber.setText("9");
        panelEmployee.add(labelEmployeeNumber);
        labelEmployeeNumber.setBounds(30, 80, 260, 90);

        background_Employee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/blue_25.png"))); // NOI18N
        background_Employee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                background_EmployeeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                background_EmployeeMouseExited(evt);
            }
        });
        panelEmployee.add(background_Employee);
        background_Employee.setBounds(30, 10, 270, 190);

        panelCakeType.setOpaque(false);
        panelCakeType.setLayout(null);

        labelTitleProduct.setFont(new java.awt.Font("Segoe Print", 2, 28)); // NOI18N
        labelTitleProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitleProduct.setText("Product");
        panelCakeType.add(labelTitleProduct);
        labelTitleProduct.setBounds(50, 40, 230, 70);

        labelProductNumber.setFont(new java.awt.Font("Segoe Print", 3, 32)); // NOI18N
        labelProductNumber.setForeground(new java.awt.Color(253, 94, 83));
        labelProductNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelProductNumber.setText("13");
        panelCakeType.add(labelProductNumber);
        labelProductNumber.setBounds(30, 80, 260, 90);

        background_CakeType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/blue_25.png"))); // NOI18N
        background_CakeType.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                background_CakeTypeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                background_CakeTypeMouseExited(evt);
            }
        });
        panelCakeType.add(background_CakeType);
        background_CakeType.setBounds(30, 20, 270, 190);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label_Today, javax.swing.GroupLayout.DEFAULT_SIZE, 1211, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_Seperator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panelRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(94, 94, 94)
                                .addComponent(panelBills, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panelProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(label_Numbers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(panelCakeType, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(label_Today, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(panelBills, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_Seperator, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(label_Numbers, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCakeType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelEmployee, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );
    }// </editor-fold>//GEN-END:initComponents


    private void background_RevMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_background_RevMouseEntered
        background_Rev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/red_25.png")));
        labelRevenue.setForeground(new Color(186, 232, 232));
        label_Title_Rev.setForeground(Color.WHITE);
    }//GEN-LAST:event_background_RevMouseEntered

    private void background_RevMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_background_RevMouseExited
        background_Rev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/blue_25.png")));
        labelRevenue.setForeground(new Color(253, 94, 83));
        label_Title_Rev.setForeground(Color.BLACK);
    }//GEN-LAST:event_background_RevMouseExited

    private void background_ProfitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_background_ProfitMouseEntered
        background_Profit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/red_25.png")));
        labelProfit.setForeground(new Color(186, 232, 232));
        label_Title_Profit.setForeground(Color.WHITE);
    }//GEN-LAST:event_background_ProfitMouseEntered

    private void background_ProfitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_background_ProfitMouseExited
        background_Profit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/blue_25.png")));
        labelProfit.setForeground(new Color(253, 94, 83));
        label_Title_Profit.setForeground(Color.BLACK);
    }//GEN-LAST:event_background_ProfitMouseExited

    private void background_BillsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_background_BillsMouseEntered
        background_Bills.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/red_25.png")));
        labelBillNumber.setForeground(new Color(186, 232, 232));
        label_Title_Bills.setForeground(Color.WHITE);
    }//GEN-LAST:event_background_BillsMouseEntered

    private void background_BillsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_background_BillsMouseExited
        background_Bills.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/blue_25.png")));
        labelBillNumber.setForeground(new Color(253, 94, 83));
        label_Title_Bills.setForeground(Color.BLACK);
    }//GEN-LAST:event_background_BillsMouseExited

    private void background_EmployeeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_background_EmployeeMouseEntered
        background_Employee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/red_25.png")));
        labelEmployeeNumber.setForeground(new Color(186, 232, 232));
        label_Title_Employee.setForeground(Color.WHITE);
    }//GEN-LAST:event_background_EmployeeMouseEntered

    private void background_EmployeeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_background_EmployeeMouseExited
        background_Employee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/blue_25.png")));
        labelEmployeeNumber.setForeground(new Color(253, 94, 83));
        label_Title_Employee.setForeground(Color.BLACK);
    }//GEN-LAST:event_background_EmployeeMouseExited

    private void background_CakeTypeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_background_CakeTypeMouseEntered
        background_CakeType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/red_25.png")));
        labelProductNumber.setForeground(new Color(186, 232, 232));
        labelTitleProduct.setForeground(Color.WHITE);
    }//GEN-LAST:event_background_CakeTypeMouseEntered

    private void background_CakeTypeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_background_CakeTypeMouseExited
        background_CakeType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/blue_25.png")));
        labelProductNumber.setForeground(new Color(253, 94, 83));
        labelTitleProduct.setForeground(Color.BLACK);
    }//GEN-LAST:event_background_CakeTypeMouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background_Bills;
    private javax.swing.JLabel background_CakeType;
    private javax.swing.JLabel background_Employee;
    private javax.swing.JLabel background_Profit;
    private javax.swing.JLabel background_Rev;
    private javax.swing.JLabel labelBillNumber;
    private javax.swing.JLabel labelEmployeeNumber;
    private javax.swing.JLabel labelProductNumber;
    private javax.swing.JLabel labelProfit;
    private javax.swing.JLabel labelRevenue;
    private javax.swing.JLabel labelTitleProduct;
    private javax.swing.JLabel label_Numbers;
    private javax.swing.JLabel label_Seperator;
    private javax.swing.JLabel label_Title_Bills;
    private javax.swing.JLabel label_Title_Employee;
    private javax.swing.JLabel label_Title_Profit;
    private javax.swing.JLabel label_Title_Rev;
    private javax.swing.JLabel label_Today;
    private javax.swing.JPanel panelBills;
    private javax.swing.JPanel panelCakeType;
    private javax.swing.JPanel panelEmployee;
    private javax.swing.JPanel panelProfit;
    private javax.swing.JPanel panelRevenue;
    // End of variables declaration//GEN-END:variables

}
