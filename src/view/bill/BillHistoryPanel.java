package view.bill;

import util.swing.UIControl;
import control.bill.history.BillHistoryControllerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import view.MessageShowing;
import model.bill.BillModelInterface;

public class BillHistoryPanel extends javax.swing.JPanel implements ActionListener, MessageShowing {

    private BillHistoryControllerInterface controller;

    private DefaultTableModel tableBillModel;

    public BillHistoryPanel() {
        initComponents();

        this.tableBillModel = (DefaultTableModel) this.tableBill.getModel();

//        createView();
//        createControl();
    }

    public void setBillHistoryController(BillHistoryControllerInterface controller) {
        if (controller == null) {
            throw new NullPointerException("Bill history controller is null object.");
        }
        this.controller = controller;
    }

    private void createView() {
        UIControl.setDefaultTableHeader(tableBill);
        datechooserDateFrom.setDate(Date.from(Instant.now()));
        datechooserDateTo.setDate(Date.from(Instant.now()));
        showTodayBill();
    }

    private void createControl() {
        btnSearchClear.addActionListener(this);
        btnApply.addActionListener(this);
        btnTodayBill.addActionListener(this);
        btnExport.addActionListener(this);
        btnDetail.addActionListener(this);

        textfSearchEmployeeName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                // XXX

            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                // XXX

            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public void setSearchText(String text) {
        this.textfSearchEmployeeName.setText(text);
    }

    public void showBills(List<BillModelInterface> bills) {
        clearBillTable();
        for (BillModelInterface bill : bills) {
            addRowTableBill(bill);
        }
    }

    private void showTodayBill() {
        List<BillModelInterface> bills = this.controller.getTodayBillList();
        clearBillTable();
        for (BillModelInterface bill : bills) {
            addRowTableBill(bill);
        }
    }

    private void addRowTableBill(BillModelInterface bill) {
        Object[] record = new Object[]{
            bill.getBillID(),
            bill.getDateTimeExport(),
            bill.getPayment(),
            bill.getGuestMoney(),
            bill.getChangeMoney(),
            bill.getEmployeeName()
        };

        tableBillModel.addRow(record);
    }

    public void clearBillTable() {
        this.tableBillModel.setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnSearchClear) {
            if (!textfSearchEmployeeName.getText().trim().isEmpty()) {
                this.controller.requestClearSearch();
            }
        } else if (source == btnApply) {
            showBills(this.controller.getBillList(datechooserDateFrom.getDate(),
                    datechooserDateTo.getDate()));
        } else if (source == btnTodayBill) {
            showTodayBill();
        } else if (source == btnExport) {
            // XXX

        } else if (source == btnDetail) {
            int id = tableBill.getSelectedRow();
            if (id == -1) {
                showErrorMessage("You should choose one bill first.");
            } else {
                this.controller.requestViewBillDetail(id);
            }
        }
    }

    public void clearAll() {
        datechooserDateFrom.setDate(null);
        datechooserDateTo.setDate(null);
        textfSearchEmployeeName.setText(null);
    }

    @Override
    public void showErrorMessage(String message) {
        ((MessageShowing) getParent()).showErrorMessage(message);
    }

    @Override
    public void showInfoMessage(String message) {
        ((MessageShowing) getParent()).showInfoMessage(message);
    }

    @Override
    public void showWarningMessage(String message) {
        ((MessageShowing) getParent()).showWarningMessage(message);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBillDateInfo = new javax.swing.JPanel();
        label_dayCreBillFrom = new javax.swing.JLabel();
        label_dayCreBillTo = new javax.swing.JLabel();
        datechooserDateFrom = new com.toedter.calendar.JDateChooser();
        datechooserDateTo = new com.toedter.calendar.JDateChooser();
        btnApply = new javax.swing.JButton();
        scrollPane_BillTable = new javax.swing.JScrollPane();
        tableBill = new javax.swing.JTable();
        btnDetail = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        label_searchProduct = new javax.swing.JLabel();
        textfSearchEmployeeName = new javax.swing.JTextField();
        btnSearchClear = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnExport = new javax.swing.JButton();
        btnTodayBill = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setName("BillHistory"); // NOI18N

        panelBillDateInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelBillDateInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Date Filter", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        label_dayCreBillFrom.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_dayCreBillFrom.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_dayCreBillFrom.setText("From");

        label_dayCreBillTo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_dayCreBillTo.setText("To");

        datechooserDateFrom.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        datechooserDateTo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        btnApply.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnApply.setForeground(new java.awt.Color(51, 51, 51));
        btnApply.setText("Apply");
        btnApply.setFocusPainted(false);

        javax.swing.GroupLayout panelBillDateInfoLayout = new javax.swing.GroupLayout(panelBillDateInfo);
        panelBillDateInfo.setLayout(panelBillDateInfoLayout);
        panelBillDateInfoLayout.setHorizontalGroup(
            panelBillDateInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBillDateInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_dayCreBillFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(datechooserDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(label_dayCreBillTo, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(datechooserDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnApply, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(545, Short.MAX_VALUE))
        );
        panelBillDateInfoLayout.setVerticalGroup(
            panelBillDateInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBillDateInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBillDateInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnApply, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBillDateInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelBillDateInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_dayCreBillTo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_dayCreBillFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(datechooserDateTo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(datechooserDateFrom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bill ID", "Day Created", "Bill Money", "Customer Payment", "Change Money", "Employee Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Long.class, java.lang.Long.class, java.lang.Long.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableBill.getTableHeader().setReorderingAllowed(false);
        scrollPane_BillTable.setViewportView(tableBill);

        btnDetail.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_bill-details.png"))); // NOI18N
        btnDetail.setBorderPainted(false);
        btnDetail.setContentAreaFilled(false);
        btnDetail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDetail.setFocusPainted(false);
        btnDetail.setPreferredSize(new java.awt.Dimension(115, 40));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 30));

        label_searchProduct.setBackground(new java.awt.Color(255, 255, 255));
        label_searchProduct.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_searchProduct.setText("Search employee name:");
        label_searchProduct.setOpaque(true);
        label_searchProduct.setPreferredSize(new java.awt.Dimension(160, 30));
        jPanel3.add(label_searchProduct);

        textfSearchEmployeeName.setPreferredSize(new java.awt.Dimension(250, 30));
        jPanel3.add(textfSearchEmployeeName);

        btnSearchClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSearchClear.setForeground(new java.awt.Color(51, 51, 51));
        btnSearchClear.setText("Clear");
        btnSearchClear.setFocusPainted(false);
        btnSearchClear.setPreferredSize(new java.awt.Dimension(90, 30));
        jPanel3.add(btnSearchClear);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnExport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnExport.setForeground(new java.awt.Color(51, 51, 51));
        btnExport.setText("Export");
        btnExport.setBorderPainted(false);
        btnExport.setPreferredSize(new java.awt.Dimension(115, 40));
        jPanel1.add(btnExport);

        btnTodayBill.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnTodayBill.setForeground(new java.awt.Color(51, 51, 51));
        btnTodayBill.setText("Today bills");
        btnTodayBill.setBorderPainted(false);
        btnTodayBill.setPreferredSize(new java.awt.Dimension(115, 40));
        jPanel1.add(btnTodayBill);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBillDateInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPane_BillTable, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBillDateInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane_BillTable, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JButton btnTodayBill;
    private com.toedter.calendar.JDateChooser datechooserDateFrom;
    private com.toedter.calendar.JDateChooser datechooserDateTo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel label_dayCreBillFrom;
    private javax.swing.JLabel label_dayCreBillTo;
    private javax.swing.JLabel label_searchProduct;
    private javax.swing.JPanel panelBillDateInfo;
    private javax.swing.JScrollPane scrollPane_BillTable;
    private javax.swing.JTable tableBill;
    private javax.swing.JTextField textfSearchEmployeeName;
    // End of variables declaration//GEN-END:variables
}
