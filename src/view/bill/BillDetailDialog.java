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
        this.labelBillID.setText(text);
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

        this.labelExportDate.setText(dateTimeFormatter.format(bill.getDateTimeExport().toLocalDateTime()));
        this.labelTotalMoney.setText(AppConstant.GLOBAL_VIE_CURRENCY_FORMATTER.format(bill.getPayment()));
        this.labelGuestMoney.setText(AppConstant.GLOBAL_VIE_CURRENCY_FORMATTER.format(bill.getGuestMoney()));
        this.labelChangeMoney.setText(AppConstant.GLOBAL_VIE_CURRENCY_FORMATTER.format(bill.getChangeMoney()));
        this.labelEmployeeID.setText(bill.getEmployee().getEmployeeIDText());
        this.labelEmployeeName.setText(bill.getEmployee().getName());
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
        labelBillID = new javax.swing.JLabel();
        labelTitleBillDate = new javax.swing.JLabel();
        labelTitlePayment = new javax.swing.JLabel();
        labelTitleGuestMoney = new javax.swing.JLabel();
        labelTitleChangeMoney = new javax.swing.JLabel();
        labelExportDate = new javax.swing.JLabel();
        labelTotalMoney = new javax.swing.JLabel();
        labelGuestMoney = new javax.swing.JLabel();
        labelChangeMoney = new javax.swing.JLabel();
        labelTitleEmployeeID = new javax.swing.JLabel();
        labelEmployeeID = new javax.swing.JLabel();
        labelTitleEmployeeName = new javax.swing.JLabel();
        labelEmployeeName = new javax.swing.JLabel();

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

        labelBillID.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelBillID.setText("1");

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

        labelTitleEmployeeID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTitleEmployeeID.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitleEmployeeID.setText("Employee ID:");

        labelEmployeeID.setBackground(new java.awt.Color(153, 153, 153));
        labelEmployeeID.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelEmployeeID.setText("15");

        labelTitleEmployeeName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTitleEmployeeName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelTitleEmployeeName.setText("Employee name:");

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
                    .addComponent(labelMainTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrpane, javax.swing.GroupLayout.Alignment.TRAILING)
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
                                    .addComponent(labelExportDate, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                    .addComponent(labelBillID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelEmployeeID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(labelTitleGuestMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(labelTitleChangeMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelGuestMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelChangeMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(labelTitlePayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labelTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(labelBillID))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelTotalMoney)
                        .addComponent(labelTitlePayment)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTitleBillDate)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labelTitleGuestMoney)
                                .addComponent(labelGuestMoney))
                            .addComponent(labelExportDate, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTitleChangeMoney)
                            .addComponent(labelChangeMoney)
                            .addComponent(labelTitleEmployeeID)
                            .addComponent(labelEmployeeID))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTitleEmployeeName)
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
    private javax.swing.JLabel labelMainTitle;
    private javax.swing.JLabel labelTitleBillDate;
    private javax.swing.JLabel labelTitleBillID;
    private javax.swing.JLabel labelTitleChangeMoney;
    private javax.swing.JLabel labelTitleEmployeeID;
    private javax.swing.JLabel labelTitleEmployeeName;
    private javax.swing.JLabel labelTitleGuestMoney;
    private javax.swing.JLabel labelTitlePayment;
    private javax.swing.JLabel labelTotalMoney;
    private javax.swing.JScrollPane scrpane;
    private javax.swing.JTable tableProductDetail;
    // End of variables declaration//GEN-END:variables
}
