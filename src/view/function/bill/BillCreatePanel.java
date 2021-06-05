package view.function.bill;

import control.bill.BillControllerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.bill.BillModelInterface;
import util.swing.UIControl;

public class BillCreatePanel extends javax.swing.JPanel implements ActionListener {

    private volatile static BillCreatePanel uniqueInstance;

    private BillModelInterface model;
    private BillControllerInterface controller;

    private BillCreatePanel(BillModelInterface model, BillControllerInterface controller) {
        if (model == null) {
            throw new IllegalArgumentException("Bill model is null object.");
        }
        if (controller == null) {
            throw new IllegalArgumentException("Bill controller is null object.");
        }

        initComponents();

        this.model = model;
        this.controller = controller;

        createView();
        createControl();
    }

    public static BillCreatePanel getInstance(BillModelInterface model,
            BillControllerInterface controller) {
        if (uniqueInstance == null) {
            synchronized (BillCreatePanel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new BillCreatePanel(model, controller);
                }
            }
        }
        return uniqueInstance;
    }

    public static BillCreatePanel getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

    private void createView() {
        UIControl.setDefaultTableHeader(tableProductOffered);
        UIControl.setDefaultTableHeader(tableProductSelected);
    }

    private void createControl() {
        btnSearchClear.addActionListener(this);
        btnExportBill.addActionListener(this);
        btnChoose.addActionListener(this);
        btnRemove.addActionListener(this);
        btnClear.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
    }

    public void clearAll() {
        textfProductName.setText(null);
        textfBillID.setText(null);
        textfTotalMoney.setText(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_prodChoose = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableProductSelected = new javax.swing.JTable();
        btnChoose = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableProductOffered = new javax.swing.JTable();
        panelBtnProductSelect = new javax.swing.JPanel();
        btnRemove = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        textfProductName = new javax.swing.JTextField();
        label_prodName = new javax.swing.JLabel();
        btnSearchClear = new javax.swing.JButton();
        btnExportBill = new javax.swing.JButton();
        textfBillID = new javax.swing.JTextField();
        label_billID = new javax.swing.JLabel();
        label_totalMoney = new javax.swing.JLabel();
        textfTotalMoney = new javax.swing.JTextField();
        label_prodChoose2 = new javax.swing.JLabel();

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

        btnExportBill.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnExportBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_export.png"))); // NOI18N
        btnExportBill.setContentAreaFilled(false);
        btnExportBill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportBill.setFocusPainted(false);

        textfBillID.setEditable(false);
        textfBillID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        label_billID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_billID.setText("Bill ID");

        label_totalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_totalMoney.setText("Total Money");

        textfTotalMoney.setEditable(false);
        textfTotalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        label_prodChoose2.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        label_prodChoose2.setText("Product list");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBtnProductSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_prodName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(label_prodChoose2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_prodChoose)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_billID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(label_totalMoney)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnExportBill, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_prodChoose)
                    .addComponent(label_prodChoose2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_prodName)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnExportBill, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(label_totalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_billID, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1118, Short.MAX_VALUE)
                    .addComponent(jScrollPane8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBtnProductSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.JLabel label_prodChoose2;
    private javax.swing.JLabel label_prodName;
    private javax.swing.JLabel label_totalMoney;
    private javax.swing.JPanel panelBtnProductSelect;
    private javax.swing.JTable tableProductOffered;
    private javax.swing.JTable tableProductSelected;
    private javax.swing.JTextField textfBillID;
    private javax.swing.JTextField textfProductName;
    private javax.swing.JTextField textfTotalMoney;
    // End of variables declaration//GEN-END:variables

}
