package view.function;

import control.statistics.StatisticsControllerInterface;
import util.swing.UIControl;

public class StatisticsPanel extends javax.swing.JPanel {

    private volatile static StatisticsPanel uniqueInstance;

    private StatisticsControllerInterface controller;
    
    private StatisticsPanel(StatisticsControllerInterface controller) {
        initComponents();
        createView();
        createControl();
    }
    
    public static StatisticsPanel getInstance(StatisticsControllerInterface controller) {
        if (controller == null) {
            throw new NullPointerException("Statistic controller is null.");
        }
        if (uniqueInstance == null) {
            synchronized (StatisticsPanel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new StatisticsPanel(controller);
                }
            }
        } else {
            uniqueInstance.controller = controller;
        }
        return uniqueInstance;
    }
    
    public static StatisticsPanel getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException("Instance is null.");
        }
        return uniqueInstance;
    }

    private void createView() {
        UIControl.setDefaultTableHeader(tableInfo);
    }
    
    private void createControl() {
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrpaneStatistics = new javax.swing.JScrollPane();
        tableInfo = new javax.swing.JTable();
        panelStatisticsInfo = new javax.swing.JPanel();
        label_statisTo = new javax.swing.JLabel();
        label_statisFrom = new javax.swing.JLabel();
        dateChooserDateFrom = new com.toedter.calendar.JDateChooser();
        dateChooserDateTo = new com.toedter.calendar.JDateChooser();
        button_Apply = new javax.swing.JButton();
        panelButtons = new javax.swing.JPanel();
        btnIngredientCost = new javax.swing.JButton();
        btnRevenue = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        scrpaneStatistics.setPreferredSize(new java.awt.Dimension(500, 500));

        tableInfo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tableInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Month", "Year", "Total Sell Money", "Total Ingredient Money", "Total Month Revenue"
            }
        ));
        scrpaneStatistics.setViewportView(tableInfo);

        panelStatisticsInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelStatisticsInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Date filter", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 15), new java.awt.Color(200, 200, 200))); // NOI18N

        label_statisTo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_statisTo.setText("To");

        label_statisFrom.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_statisFrom.setText("From");

        dateChooserDateFrom.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        dateChooserDateTo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        button_Apply.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        button_Apply.setForeground(new java.awt.Color(51, 51, 51));
        button_Apply.setText("Apply");
        button_Apply.setFocusPainted(false);

        javax.swing.GroupLayout panelStatisticsInfoLayout = new javax.swing.GroupLayout(panelStatisticsInfo);
        panelStatisticsInfo.setLayout(panelStatisticsInfoLayout);
        panelStatisticsInfoLayout.setHorizontalGroup(
            panelStatisticsInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelStatisticsInfoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(label_statisFrom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateChooserDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(label_statisTo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateChooserDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_Apply, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        panelStatisticsInfoLayout.setVerticalGroup(
            panelStatisticsInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelStatisticsInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelStatisticsInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(label_statisFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateChooserDateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(label_statisTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateChooserDateTo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Apply, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(31, 31, 31))
        );

        panelButtons.setBackground(new java.awt.Color(255, 255, 255));
        panelButtons.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Review", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 15), new java.awt.Color(200, 200, 200))); // NOI18N

        btnIngredientCost.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnIngredientCost.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_ingredient-cost.png"))); // NOI18N
        btnIngredientCost.setContentAreaFilled(false);
        btnIngredientCost.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIngredientCost.setFocusPainted(false);
        btnIngredientCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngredientCostActionPerformed(evt);
            }
        });

        btnRevenue.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnRevenue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_revenue.png"))); // NOI18N
        btnRevenue.setContentAreaFilled(false);
        btnRevenue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRevenue.setFocusPainted(false);
        btnRevenue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRevenueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonsLayout = new javax.swing.GroupLayout(panelButtons);
        panelButtons.setLayout(panelButtonsLayout);
        panelButtonsLayout.setHorizontalGroup(
            panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonsLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnIngredientCost, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );
        panelButtonsLayout.setVerticalGroup(
            panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonsLayout.createSequentialGroup()
                .addComponent(btnIngredientCost, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrpaneStatistics, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelStatisticsInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(447, 447, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelStatisticsInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scrpaneStatistics, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    public void clearAll() {
        dateChooserDateFrom.setDate(null);
        dateChooserDateTo.setDate(null);
    }
    private void btnIngredientCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngredientCostActionPerformed

    }//GEN-LAST:event_btnIngredientCostActionPerformed

    private void btnRevenueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevenueActionPerformed

    }//GEN-LAST:event_btnRevenueActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngredientCost;
    private javax.swing.JButton btnRevenue;
    private javax.swing.JButton button_Apply;
    private com.toedter.calendar.JDateChooser dateChooserDateFrom;
    private com.toedter.calendar.JDateChooser dateChooserDateTo;
    private javax.swing.JLabel label_statisFrom;
    private javax.swing.JLabel label_statisTo;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelStatisticsInfo;
    private javax.swing.JScrollPane scrpaneStatistics;
    private javax.swing.JTable tableInfo;
    // End of variables declaration//GEN-END:variables
}
