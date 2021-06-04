package view.function.bill;

import util.swing.UIControl;
import static util.swing.UIControl.setLocationCenterForDialog;

public class BillCreatePanel extends javax.swing.JPanel {

    private volatile static BillCreatePanel uniqueInstance;
    
//    BillExportDialog exportConfirm = new BillExportDialog(null, true);

    private BillCreatePanel() {
        initComponents();
        UIControl.setDefaultTableHeader(tableProductOffered);
        UIControl.setDefaultTableHeader(tableProductSelected);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_prodChoose = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableProductSelected = new javax.swing.JTable();
        btnChoose = new javax.swing.JButton();
        panel_BillInfo = new javax.swing.JPanel();
        label_billID = new javax.swing.JLabel();
        label_totalMoney = new javax.swing.JLabel();
        btnExportBill = new javax.swing.JButton();
        textfBillID = new javax.swing.JTextField();
        textfTotalMoney = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableProductOffered = new javax.swing.JTable();
        panelBtnProductSelect = new javax.swing.JPanel();
        btnRemove = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        label_prodChoose1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        textfProductName = new javax.swing.JTextField();
        label_prodName = new javax.swing.JLabel();
        btnSearchClear = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        label_prodChoose.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        label_prodChoose.setText("Chosen products");

        tableProductSelected.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Size", "Amount", "Price"
            }
        ));
        jScrollPane8.setViewportView(tableProductSelected);

        btnChoose.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnChoose.setForeground(new java.awt.Color(51, 51, 51));
        btnChoose.setText("Choose");
        btnChoose.setFocusPainted(false);
        btnChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseActionPerformed(evt);
            }
        });

        panel_BillInfo.setBackground(new java.awt.Color(255, 255, 255));
        panel_BillInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bill information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        label_billID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_billID.setText("Bill ID");

        label_totalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_totalMoney.setText("Total Money");

        btnExportBill.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnExportBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_export.png"))); // NOI18N
        btnExportBill.setContentAreaFilled(false);
        btnExportBill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportBill.setFocusPainted(false);
        btnExportBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportBillActionPerformed(evt);
            }
        });

        textfBillID.setEditable(false);
        textfBillID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfTotalMoney.setEditable(false);
        textfTotalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout panel_BillInfoLayout = new javax.swing.GroupLayout(panel_BillInfo);
        panel_BillInfo.setLayout(panel_BillInfoLayout);
        panel_BillInfoLayout.setHorizontalGroup(
            panel_BillInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_BillInfoLayout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(btnExportBill, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                .addGap(41, 41, 41))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_BillInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label_billID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(label_totalMoney)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        panel_BillInfoLayout.setVerticalGroup(
            panel_BillInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_BillInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_BillInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_totalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_BillInfoLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_BillInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_billID, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnExportBill, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        tableProductOffered.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Size", "Unit Price"
            }
        ));
        jScrollPane7.setViewportView(tableProductOffered);

        panelBtnProductSelect.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnProductSelect.setLayout(new java.awt.GridLayout(1, 0, 35, 0));

        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnRemove.setForeground(new java.awt.Color(51, 51, 51));
        btnRemove.setText("Remove");
        btnRemove.setFocusPainted(false);
        panelBtnProductSelect.add(btnRemove);

        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnClear.setForeground(new java.awt.Color(51, 51, 51));
        btnClear.setText("Clear");
        btnClear.setFocusPainted(false);
        btnClear.setPreferredSize(new java.awt.Dimension(89, 29));
        panelBtnProductSelect.add(btnClear);

        label_prodChoose1.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        label_prodChoose1.setText("Product list");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        textfProductName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfProductName.setPreferredSize(new java.awt.Dimension(160, 30));

        label_prodName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_prodName.setText("Product Name");

        btnSearchClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSearchClear.setForeground(new java.awt.Color(51, 51, 51));
        btnSearchClear.setText("Clear");
        btnSearchClear.setFocusPainted(false);
        btnSearchClear.setPreferredSize(new java.awt.Dimension(89, 29));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBtnProductSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_prodChoose1)
                            .addComponent(label_prodName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel_BillInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(label_prodChoose)
                        .addGap(212, 212, 212))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(label_prodChoose1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(textfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_prodName)
                                    .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 924, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(panel_BillInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(label_prodChoose)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(panelBtnProductSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void clearAll() {
        textfProductName.setText(null);
        textfBillID.setText(null);
        textfTotalMoney.setText(null);
    }
    private void btnChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseActionPerformed

    }//GEN-LAST:event_btnChooseActionPerformed

    private void btnExportBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportBillActionPerformed
//        setLocationCenterForDialog(exportConfirm);
//        exportConfirm.setVisible(true);
    }//GEN-LAST:event_btnExportBillActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChoose;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnExportBill;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel label_billID;
    private javax.swing.JLabel label_prodChoose;
    private javax.swing.JLabel label_prodChoose1;
    private javax.swing.JLabel label_prodName;
    private javax.swing.JLabel label_totalMoney;
    private javax.swing.JPanel panelBtnProductSelect;
    private javax.swing.JPanel panel_BillInfo;
    private javax.swing.JTable tableProductOffered;
    private javax.swing.JTable tableProductSelected;
    private javax.swing.JTextField textfBillID;
    private javax.swing.JTextField textfProductName;
    private javax.swing.JTextField textfTotalMoney;
    // End of variables declaration//GEN-END:variables
}
