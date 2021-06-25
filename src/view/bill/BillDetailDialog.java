package view.bill;

import control.bill.history.BillHistoryControllerInterface;
import java.awt.Color;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import util.swing.UIControl;
import model.bill.detail.ProductDetailModelInterface;
import model.bill.BillModelInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import util.constant.AppConstant;
import util.swing.CurrencyTextField;
import util.swing.NumberRenderer;

public class BillDetailDialog extends javax.swing.JDialog implements SettingUpdateObserver {

    private static final int PRODUCT_ID_COLUMN_INDEX = 0;
    private static final int PRODUCT_NAME_COLUMN_INDEX = 1;
    private static final int PRODUCT_SIZE_COLUMN_INDEX = 2;
    private static final int PRODUCT_AMOUNT_COLUMN_INDEX = 3;
    private static final int PRODUCT_COST_COLUMN_INDEX = 4;

    private BillHistoryControllerInterface billHistoryController;

    private DefaultTableModel tableBillDetailModel;

    public BillDetailDialog(java.awt.Frame parent, boolean modal,
            BillHistoryControllerInterface controller) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        this.billHistoryController = controller;

        this.tableBillDetailModel = (DefaultTableModel) tableProductDetail.getModel();
        tableProductDetail.getColumnModel().getColumn(PRODUCT_COST_COLUMN_INDEX)
                .setCellRenderer(NumberRenderer.getCurrencyRenderer());
        setLocationRelativeTo(parent);
        createView();
    }

    private void createView() {
        UIControl.setDefaultTableHeader(tableProductDetail);
        this.getContentPane().setBackground(Color.WHITE);
    }

    public void setLabelBillID(String text) {
        this.textfBillID.setText(text);
    }

    public void setTableBillDetail(Iterator<ProductDetailModelInterface> iterator) {
        clearTableBillDetail();
        while (iterator.hasNext()) {
            addRowTableBillDetail(iterator.next());
        }
    }

    private void addRowTableBillDetail(ProductDetailModelInterface productDetail) {
        Object[] object = new Object[]{
            productDetail.getProduct().getProductIDText(),
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

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        this.textfExportDate.setText(dateTimeFormatter.format(bill.getDateTimeExport().toLocalDateTime()));
        this.textfTotalMoney.setText(AppConstant.GLOBAL_VIE_CURRENCY_FORMATTER.format(bill.getPayment()));
        this.textfGuestMoney.setText(AppConstant.GLOBAL_VIE_CURRENCY_FORMATTER.format(bill.getGuestMoney()));
        this.textfChangeMoney.setText(AppConstant.GLOBAL_VIE_CURRENCY_FORMATTER.format(bill.getChangeMoney()));
        this.textfEmployeeID.setText(bill.getEmployee().getEmployeeIDText());
        this.textfEmployeeName.setText(bill.getEmployee().getName());
    }

    @Override
    public void updateSettingObserver() {
        AppSetting.Language language = AppSetting.getInstance().getAppLanguage();

        switch (language) {
            case ENGLISH: {
                setTitle("Bill detail dialog");
                
                labelMainTitle.setText("Bill Detail");

                labelTitleBillID.setText("Bill ID:");
                labelTitleBillDate.setText("Date:");
                labelTitleEmployeeID.setText("Employee ID:");
                labelTitleEmployeeName.setText("Employee name:");
                labelTitlePayment.setText("Total money:");
                labelTitleGuestMoney.setText("Guest money:");
                labelTitleChangeMoney.setText("Change money:");

                TableColumnModel tableColumnModel = tableProductDetail.getColumnModel();
                tableColumnModel.getColumn(PRODUCT_ID_COLUMN_INDEX).setHeaderValue("Product ID");
                tableColumnModel.getColumn(PRODUCT_NAME_COLUMN_INDEX).setHeaderValue("Product name");
                tableColumnModel.getColumn(PRODUCT_SIZE_COLUMN_INDEX).setHeaderValue("Product size");
                tableColumnModel.getColumn(PRODUCT_AMOUNT_COLUMN_INDEX).setHeaderValue("Amount");
                tableColumnModel.getColumn(PRODUCT_COST_COLUMN_INDEX).setHeaderValue("Cost");

                break;
            }
            case VIETNAMESE: {
                setTitle("Hộp thoại chi tiết hóa đơn");

                labelMainTitle.setText("Chi Tiết Hóa Đơn");
                
                labelTitleBillID.setText("Số hóa đơn:");
                labelTitleBillDate.setText("Ngày:");
                labelTitleEmployeeID.setText("Mã nhân viên:");
                labelTitleEmployeeName.setText("Tên nhân viên:");
                labelTitlePayment.setText("Tổng tiền:");
                labelTitleGuestMoney.setText("Tiền khách:");
                labelTitleChangeMoney.setText("Tiền thối:");

                TableColumnModel tableColumnModel = tableProductDetail.getColumnModel();
                tableColumnModel.getColumn(PRODUCT_ID_COLUMN_INDEX).setHeaderValue("Mã sản phẩm");
                tableColumnModel.getColumn(PRODUCT_NAME_COLUMN_INDEX).setHeaderValue("Tên sản phẩm");
                tableColumnModel.getColumn(PRODUCT_SIZE_COLUMN_INDEX).setHeaderValue("Kích thước");
                tableColumnModel.getColumn(PRODUCT_AMOUNT_COLUMN_INDEX).setHeaderValue("Số lượng");
                tableColumnModel.getColumn(PRODUCT_COST_COLUMN_INDEX).setHeaderValue("Giá");

                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTitleBillID = new javax.swing.JLabel();
        scrpane = new javax.swing.JScrollPane();
        tableProductDetail = new javax.swing.JTable();
        labelMainTitle = new javax.swing.JLabel();
        labelTitleBillDate = new javax.swing.JLabel();
        labelTitlePayment = new javax.swing.JLabel();
        labelTitleGuestMoney = new javax.swing.JLabel();
        labelTitleChangeMoney = new javax.swing.JLabel();
        labelTitleEmployeeID = new javax.swing.JLabel();
        labelTitleEmployeeName = new javax.swing.JLabel();
        textfTotalMoney = new CurrencyTextField();
        textfGuestMoney = new CurrencyTextField();
        textfChangeMoney = new CurrencyTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textfBillID = new javax.swing.JTextField();
        textfEmployeeID = new javax.swing.JTextField();
        textfEmployeeName = new javax.swing.JTextField();
        textfExportDate = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        labelTitleBillID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTitleBillID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTitleBillID.setText("Bill ID: ");

        tableProductDetail.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tableProductDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product name", "Product size", "Amount", "Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Long.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableProductDetail.setSelectionBackground(new java.awt.Color(113, 168, 255));
        tableProductDetail.getTableHeader().setReorderingAllowed(false);
        scrpane.setViewportView(tableProductDetail);

        labelMainTitle.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        labelMainTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMainTitle.setText("Bill Detail");

        labelTitleBillDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTitleBillDate.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitleBillDate.setText("Bill date:");

        labelTitlePayment.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTitlePayment.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitlePayment.setText("Total money:");

        labelTitleGuestMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTitleGuestMoney.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitleGuestMoney.setText("Guest money:");

        labelTitleChangeMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTitleChangeMoney.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitleChangeMoney.setText("Change:");

        labelTitleEmployeeID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTitleEmployeeID.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitleEmployeeID.setText("Employee ID:");

        labelTitleEmployeeName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTitleEmployeeName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitleEmployeeName.setText("Employee name:");

        textfTotalMoney.setEditable(false);
        textfTotalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfGuestMoney.setEditable(false);
        textfGuestMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfChangeMoney.setEditable(false);
        textfChangeMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("VND");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setText("VND");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setText("VND");

        textfBillID.setEditable(false);
        textfBillID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfEmployeeID.setEditable(false);
        textfEmployeeID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfEmployeeName.setEditable(false);
        textfEmployeeName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfExportDate.setEditable(false);
        textfExportDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelMainTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrpane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelTitleEmployeeName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelTitleEmployeeID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelTitleBillID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelTitleBillDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(textfBillID, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                        .addComponent(textfEmployeeID))
                                    .addComponent(textfExportDate, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(labelTitleGuestMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(labelTitleChangeMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(textfGuestMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textfChangeMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(labelTitlePayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textfEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(labelMainTitle)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelTitleBillID)
                        .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelTitlePayment)
                        .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelTitleBillDate)
                        .addComponent(textfExportDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTitleGuestMoney)
                            .addComponent(textfGuestMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTitleChangeMoney)
                            .addComponent(labelTitleEmployeeID)
                            .addComponent(textfChangeMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(textfEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTitleEmployeeName)
                    .addComponent(textfEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrpane, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel labelMainTitle;
    private javax.swing.JLabel labelTitleBillDate;
    private javax.swing.JLabel labelTitleBillID;
    private javax.swing.JLabel labelTitleChangeMoney;
    private javax.swing.JLabel labelTitleEmployeeID;
    private javax.swing.JLabel labelTitleEmployeeName;
    private javax.swing.JLabel labelTitleGuestMoney;
    private javax.swing.JLabel labelTitlePayment;
    private javax.swing.JScrollPane scrpane;
    private javax.swing.JTable tableProductDetail;
    private javax.swing.JTextField textfBillID;
    private javax.swing.JFormattedTextField textfChangeMoney;
    private javax.swing.JTextField textfEmployeeID;
    private javax.swing.JTextField textfEmployeeName;
    private javax.swing.JTextField textfExportDate;
    private javax.swing.JFormattedTextField textfGuestMoney;
    private javax.swing.JFormattedTextField textfTotalMoney;
    // End of variables declaration//GEN-END:variables
}
