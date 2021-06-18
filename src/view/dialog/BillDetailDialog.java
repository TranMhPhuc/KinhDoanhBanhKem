package view.dialog;

import control.bill.history.BillHistoryControllerInterface;
import java.awt.Color;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.product.ProductModelInterface;
import util.swing.UIControl;
import model.bill.detail.ProductDetailModelInterface;
import model.bill.BillModelInterface;

public class BillDetailDialog extends javax.swing.JDialog {

    private BillHistoryControllerInterface controller;
    
    private DefaultTableModel tableBillDetailModel;
    
    public BillDetailDialog(java.awt.Frame parent, boolean modal, 
            BillHistoryControllerInterface controller) {
        super(parent, modal);
        initComponents();
        
        this.controller = controller;
        this.tableBillDetailModel = (DefaultTableModel) tableBillDetail.getModel();
        
        setLocationRelativeTo(parent);
        createView();
    }
    
    private void createView() {
        UIControl.setDefaultTableHeader(tableBillDetail);
        this.getContentPane().setBackground(Color.WHITE);
    }
    
    public void setLabelBillID(String text) {
        this.labelBillID.setText(text);
    }
    
    public void setTableBillDetail(List<ProductDetailModelInterface> productDetails) {
        clearTableBillDetail();
        for (ProductDetailModelInterface productDetail : productDetails) {
            addRowTableBillDetail(productDetail);
        }
    }
    
    private void addRowTableBillDetail(ProductDetailModelInterface productDetail) {
        Object[] object = new Object[] {
            productDetail.getProduct().getName(),
            productDetail.getAmount(),
            productDetail.getPrice()
        };
        this.tableBillDetailModel.addRow(object);
    }
    
    public void clearTableBillDetail() {
        this.tableBillDetailModel.setRowCount(0);
    }
    
    public void setBillInfo(BillModelInterface bill) {
        setLabelBillID(bill.getBillIDText());
        this.labelBillDate.setText(bill.getDateTimeExport().toString());
        this.labelPayment.setText(String.valueOf(bill.getPayment()));
        this.labelGuestMoney.setText(String.valueOf(bill.getGuestMoney()));
        this.labelChangeMoney.setText(String.valueOf(bill.getChangeMoney()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_BillID = new javax.swing.JLabel();
        scrpane = new javax.swing.JScrollPane();
        tableBillDetail = new javax.swing.JTable();
        label_Title = new javax.swing.JLabel();
        labelBillID = new javax.swing.JLabel();
        labelTitleBillDate = new javax.swing.JLabel();
        labelTitlePayment = new javax.swing.JLabel();
        labelTitleGuestMoney = new javax.swing.JLabel();
        labelTitleChangeMoney = new javax.swing.JLabel();
        labelBillDate = new javax.swing.JLabel();
        labelPayment = new javax.swing.JLabel();
        labelGuestMoney = new javax.swing.JLabel();
        labelChangeMoney = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        label_BillID.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        label_BillID.setText("Bill ID: ");

        tableBillDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product name", "Amount", "Cost"
            }
        ));
        scrpane.setViewportView(tableBillDetail);

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

        labelBillDate.setBackground(new java.awt.Color(153, 153, 153));
        labelBillDate.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelBillDate.setText("6/9/1969");

        labelPayment.setBackground(new java.awt.Color(153, 153, 153));
        labelPayment.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelPayment.setText("499 000 VND");

        labelGuestMoney.setBackground(new java.awt.Color(153, 153, 153));
        labelGuestMoney.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelGuestMoney.setText("500 000 VND");

        labelChangeMoney.setBackground(new java.awt.Color(153, 153, 153));
        labelChangeMoney.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelChangeMoney.setText("1000 VND");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_Title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrpane, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(labelTitleGuestMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelTitleChangeMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelTitlePayment, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelTitleBillDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelPayment)
                                    .addComponent(labelBillDate)
                                    .addComponent(labelGuestMoney)
                                    .addComponent(labelChangeMoney)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(label_BillID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(label_Title)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_BillID)
                    .addComponent(labelBillID))
                .addGap(18, 18, 18)
                .addComponent(scrpane, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTitleBillDate)
                    .addComponent(labelBillDate))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelPayment)
                    .addComponent(labelTitlePayment))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTitleGuestMoney)
                    .addComponent(labelGuestMoney))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTitleChangeMoney)
                    .addComponent(labelChangeMoney))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelBillDate;
    private javax.swing.JLabel labelBillID;
    private javax.swing.JLabel labelChangeMoney;
    private javax.swing.JLabel labelGuestMoney;
    private javax.swing.JLabel labelPayment;
    private javax.swing.JLabel labelTitleBillDate;
    private javax.swing.JLabel labelTitleChangeMoney;
    private javax.swing.JLabel labelTitleGuestMoney;
    private javax.swing.JLabel labelTitlePayment;
    private javax.swing.JLabel label_BillID;
    private javax.swing.JLabel label_Title;
    private javax.swing.JScrollPane scrpane;
    private javax.swing.JTable tableBillDetail;
    // End of variables declaration//GEN-END:variables
}
