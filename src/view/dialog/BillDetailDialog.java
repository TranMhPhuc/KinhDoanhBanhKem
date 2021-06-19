package view.dialog;

import control.bill.history.BillHistoryControllerInterface;
import java.awt.Color;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import util.swing.UIControl;
import model.bill.detail.ProductDetailModelInterface;
import model.bill.BillModelInterface;

public class BillDetailDialog extends javax.swing.JDialog {

    public static final String DIALOG_TITLE = "Bill detail dialog";
    
    private BillHistoryControllerInterface billHistoryController;
    
    private DefaultTableModel tableBillDetailModel;
    
    public BillDetailDialog(java.awt.Frame parent, boolean modal, 
            BillHistoryControllerInterface controller) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        setTitle(DIALOG_TITLE);
        
        this.billHistoryController = controller;
        
        this.tableBillDetailModel = (DefaultTableModel) tableProductDetail.getModel();
        
        setLocationRelativeTo(parent);
        createView();
    }
    
    private void createView() {
        UIControl.setDefaultTableHeader(tableProductDetail);
        this.getContentPane().setBackground(Color.WHITE);
    }
    
    public void setLabelBillID(String text) {
        this.labelBillID.setText(text);
    }
    
    public void setTableBillDetail(Iterator<ProductDetailModelInterface> iterator) {
        clearTableBillDetail();
        while (iterator.hasNext()) {
            addRowTableBillDetail(iterator.next());
        }
    }
    
    private void addRowTableBillDetail(ProductDetailModelInterface productDetail) {
        Object[] object = new Object[] {
            productDetail.getProduct().getName(),
            productDetail.getProduct().getSize().toString(),
            productDetail.getAmount(),
            productDetail.getPrice()
        };
        this.tableBillDetailModel.addRow(object);
    }
    
    public void clearTableBillDetail() {
        this.tableBillDetailModel.setRowCount(0);
    }
    
    public void setBillInfo(BillModelInterface bill) {
        if (bill == null) {
            throw new NullPointerException();
        }
        
        setLabelBillID(bill.getBillIDText());
        this.labelExportDate.setText(bill.getDateTimeExport().toString());
        this.labelTotalMoney.setText(String.valueOf(bill.getPayment()));
        this.labelGuestMoney.setText(String.valueOf(bill.getGuestMoney()));
        this.labelChangeMoney.setText(String.valueOf(bill.getChangeMoney()));
        this.labelEmployeeID.setText(bill.getEmployee().getEmployeeIDText());
        this.labelEmployeeName.setText(bill.getEmployee().getName());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_BillID = new javax.swing.JLabel();
        scrpane = new javax.swing.JScrollPane();
        tableProductDetail = new javax.swing.JTable();
        label_Title = new javax.swing.JLabel();
        labelBillID = new javax.swing.JLabel();
        labelTitleBillDate = new javax.swing.JLabel();
        labelTitlePayment = new javax.swing.JLabel();
        labelTitleGuestMoney = new javax.swing.JLabel();
        labelTitleChangeMoney = new javax.swing.JLabel();
        labelExportDate = new javax.swing.JLabel();
        labelTotalMoney = new javax.swing.JLabel();
        labelGuestMoney = new javax.swing.JLabel();
        labelChangeMoney = new javax.swing.JLabel();
        labelTitleChangeMoney1 = new javax.swing.JLabel();
        labelEmployeeID = new javax.swing.JLabel();
        labelTitleChangeMoney2 = new javax.swing.JLabel();
        labelEmployeeName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        label_BillID.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        label_BillID.setText("Bill ID: ");

        tableProductDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product name", "Product size", "Amount", "Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Long.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrpane.setViewportView(tableProductDetail);

        label_Title.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        label_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Title.setText("Bill Detail");

        labelBillID.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelBillID.setForeground(new java.awt.Color(255, 51, 51));
        labelBillID.setText("1");

        labelTitleBillDate.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        labelTitleBillDate.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitleBillDate.setText("Bill date:");

        labelTitlePayment.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        labelTitlePayment.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitlePayment.setText("Total money:");

        labelTitleGuestMoney.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        labelTitleGuestMoney.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitleGuestMoney.setText("Guest money:");

        labelTitleChangeMoney.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        labelTitleChangeMoney.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitleChangeMoney.setText("Change:");

        labelExportDate.setBackground(new java.awt.Color(153, 153, 153));
        labelExportDate.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelExportDate.setText("6/9/1969");

        labelTotalMoney.setBackground(new java.awt.Color(153, 153, 153));
        labelTotalMoney.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelTotalMoney.setText("499 000 VND");

        labelGuestMoney.setBackground(new java.awt.Color(153, 153, 153));
        labelGuestMoney.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelGuestMoney.setText("500 000 VND");

        labelChangeMoney.setBackground(new java.awt.Color(153, 153, 153));
        labelChangeMoney.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelChangeMoney.setText("1000 VND");

        labelTitleChangeMoney1.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        labelTitleChangeMoney1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitleChangeMoney1.setText("Employee ID:");

        labelEmployeeID.setBackground(new java.awt.Color(153, 153, 153));
        labelEmployeeID.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelEmployeeID.setText("15");

        labelTitleChangeMoney2.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        labelTitleChangeMoney2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitleChangeMoney2.setText("Employee name:");

        labelEmployeeName.setBackground(new java.awt.Color(153, 153, 153));
        labelEmployeeName.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelEmployeeName.setText("15");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_Title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(label_BillID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelTitlePayment, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrpane, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTitleChangeMoney2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelTitleBillDate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelExportDate, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelTitleChangeMoney1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelEmployeeID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelTitleGuestMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelTitleChangeMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelGuestMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelChangeMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(label_Title)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_BillID)
                        .addComponent(labelBillID))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelTotalMoney)
                        .addComponent(labelTitlePayment)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelTitleBillDate)
                        .addComponent(labelExportDate))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTitleGuestMoney)
                            .addComponent(labelGuestMoney))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTitleChangeMoney)
                            .addComponent(labelChangeMoney)
                            .addComponent(labelTitleChangeMoney1)
                            .addComponent(labelEmployeeID))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTitleChangeMoney2)
                    .addComponent(labelEmployeeName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrpane, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelBillID;
    private javax.swing.JLabel labelChangeMoney;
    private javax.swing.JLabel labelEmployeeID;
    private javax.swing.JLabel labelEmployeeName;
    private javax.swing.JLabel labelExportDate;
    private javax.swing.JLabel labelGuestMoney;
    private javax.swing.JLabel labelTitleBillDate;
    private javax.swing.JLabel labelTitleChangeMoney;
    private javax.swing.JLabel labelTitleChangeMoney1;
    private javax.swing.JLabel labelTitleChangeMoney2;
    private javax.swing.JLabel labelTitleGuestMoney;
    private javax.swing.JLabel labelTitlePayment;
    private javax.swing.JLabel labelTotalMoney;
    private javax.swing.JLabel label_BillID;
    private javax.swing.JLabel label_Title;
    private javax.swing.JScrollPane scrpane;
    private javax.swing.JTable tableProductDetail;
    // End of variables declaration//GEN-END:variables
}
