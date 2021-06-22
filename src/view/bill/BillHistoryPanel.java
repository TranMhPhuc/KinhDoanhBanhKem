package view.bill;

import util.swing.UIControl;
import control.bill.history.BillHistoryControllerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.bill.BillHistoryModelInterface;
import view.MessageShowing;
import model.bill.BillModelInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import util.constant.AppConstant;
import util.swing.NumberRenderer;

public class BillHistoryPanel extends javax.swing.JPanel implements ActionListener,
        MessageShowing, SettingUpdateObserver {

    private static final int BILL_ID_COLUMN_INDEX = 0;
    private static final int BILL_DATE_COLUMN_INDEX = 1;
    private static final int BILL_TIME_COLUMN_INDEX = 2;
    private static final int BILL_TOTAL_MONEY_COLUMN_INDEX = 3;
    private static final int BILL_CUSTOMER_MONEY_COLUMN_INDEX = 4;
    private static final int BILL_CHANGE_MONEY_COLUMN_INDEX = 5;
    private static final int BILL_EMPLOYEE_ID_COLUMN_INDEX = 6;
    private static final int BILL_EMPLOYEE_NAME_COLUMN_INDEX = 7;

    private JFrame mainFrame;

    private BillHistoryModelInterface billHistoryModel;
    private BillHistoryControllerInterface billHistoryController;

    private DefaultTableModel tableBillInfoModel;

    public BillHistoryPanel() {
        initComponents();
        this.tableBillInfoModel = (DefaultTableModel) this.tableBill.getModel();
        createView();
        createControl();
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void setBillHistoryController(BillHistoryControllerInterface controller) {
        if (controller == null) {
            throw new NullPointerException("Bill history controller instance is null.");
        }
        this.billHistoryController = controller;

        // Show today bill
        datechooserDateFrom.setDate(Date.from(Instant.now()));
        datechooserDateTo.setDate(Date.from(Instant.now()));
        Iterator<BillModelInterface> iterator = billHistoryController.getTodayBillData();
        updateBillList(iterator);
    }

    public void setBillHistoryModel(BillHistoryModelInterface billHistoryModel) {
        if (billHistoryModel == null) {
            throw new NullPointerException();
        }
        this.billHistoryModel = billHistoryModel;
        billHistoryModel.setBillHistoryPanel(this);
    }

    private void createView() {
        UIControl.setDefaultTableHeader(tableBill);
        tableBill.getColumnModel().getColumn(BILL_TOTAL_MONEY_COLUMN_INDEX)
                .setCellRenderer(NumberRenderer.getCurrencyRenderer());
        tableBill.getColumnModel().getColumn(BILL_CUSTOMER_MONEY_COLUMN_INDEX)
                .setCellRenderer(NumberRenderer.getCurrencyRenderer());
        tableBill.getColumnModel().getColumn(BILL_CHANGE_MONEY_COLUMN_INDEX)
                .setCellRenderer(NumberRenderer.getCurrencyRenderer());
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
                Iterator<BillModelInterface> searchResult = billHistoryController
                        .getBillSearchByEmployeeName();
                updateBillList(searchResult);
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                if (textfSearchEmployeeName.getText().trim().isEmpty()) {
                    resetBillList();
                } else {
                    Iterator<BillModelInterface> searchResult = billHistoryController
                            .getBillSearchByEmployeeName();
                    updateBillList(searchResult);
                }
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

    public String getSearchText() {
        return textfSearchEmployeeName.getText().trim();
    }

    public void setDateFromInput(LocalDate dateFrom) {
        datechooserDateFrom.setDate(Date.from(dateFrom.atStartOfDay(ZoneId
                .systemDefault()).toInstant()));
    }

    public void setDateToInput(LocalDate dateTo) {
        datechooserDateTo.setDate(Date.from(dateTo.atStartOfDay(ZoneId
                .systemDefault()).toInstant()));
    }

    public void updateBillList(Iterator<BillModelInterface> iterator) {
        clearBillTable();
        while (iterator.hasNext()) {
            BillModelInterface bill = iterator.next();
            addRecordTableBill(bill);
        }
    }

    public void resetBillList() {
        clearBillTable();
        Iterator<BillModelInterface> iterator = billHistoryController
                .getBillDataFromDateRange();
        while (iterator.hasNext()) {
            BillModelInterface bill = iterator.next();
            addRecordTableBill(bill);
        }
    }

    private void addRecordTableBill(BillModelInterface bill) {
        if (bill == null) {
            throw new NullPointerException();
        }

        Timestamp exportDate = bill.getDateTimeExport();

        LocalDate visualDate = exportDate.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalTime visualTime = exportDate.toInstant().atZone(ZoneId.systemDefault())
                .toLocalTime();

        Object[] record = new Object[]{
            bill.getBillID(),
            visualDate.format(AppConstant.GLOBAL_DATE_FORMATTER),
            visualTime.format(AppConstant.GLOBAL_TIME_FORMATTER),
            bill.getPayment(),
            bill.getGuestMoney(),
            bill.getChangeMoney(),
            bill.getEmployee().getEmployeeIDText(),
            bill.getEmployee().getName()
        };
        tableBillInfoModel.addRow(record);
    }

    public void clearBillTable() {
        this.tableBillInfoModel.setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnSearchClear) {
            if (!textfSearchEmployeeName.getText().trim().isEmpty()) {
                textfSearchEmployeeName.setText("");
                resetBillList();
            }
        } else if (source == btnApply) {
            billHistoryController.requestShowBillFromDateRange();
        } else if (source == btnTodayBill) {
            billHistoryController.requestShowTodayBill();
        } else if (source == btnExport) {
            billHistoryController.exportBillToExcel();
        } else if (source == btnDetail) {
            billHistoryController.requestShowBillDetail();
        }
    }

    public JTable getTableBillInfo() {
        return tableBill;
    }

    public LocalDate getDateFromInput() {
        return datechooserDateFrom.getDate().toInstant().atZone(ZoneId
                .systemDefault()).toLocalDate();
    }

    public LocalDate getDateToInput() {
        return datechooserDateTo.getDate().toInstant().atZone(ZoneId
                .systemDefault()).toLocalDate();
    }

    public int getTableBillSelectedRowIndex() {
        return tableBill.getSelectedRow();
    }

    @Override
    public void showErrorMessage(String message) {
        ((MessageShowing) mainFrame).showErrorMessage(message);
    }

    @Override
    public void showInfoMessage(String message) {
        ((MessageShowing) mainFrame).showInfoMessage(message);
    }

    @Override
    public void showWarningMessage(String message) {
        ((MessageShowing) mainFrame).showWarningMessage(message);
    }

    public void updateBillHistoryObserver() {
        if (textfSearchEmployeeName.getText().trim().isEmpty()) {
            resetBillList();
        } else {
            Iterator<BillModelInterface> searchResult = billHistoryController
                    .getBillSearchByEmployeeName();
            updateBillList(searchResult);
        }
    }

    @Override
    public void updateSettingObserver() {
        AppSetting.Language appLanguage = AppSetting.getInstance().getAppLanguage();

        switch (appLanguage) {
            case ENGLISH: {
                TitledBorder titledBorder = (TitledBorder) panelDateInput.getBorder();
                titledBorder.setTitle("Date filter");

                labelDateFrom.setText("From:");
                labelDateTo.setText("To:");
                labelSearchEmployeeName.setText("Search employee name:");

                btnApply.setText("Apply");
                btnSearchClear.setText("Clear");
                btnExport.setText("Export");
                btnTodayBill.setText("Today bills");
                btnDetail.setText("View bill detail");

                TableColumnModel tableColumnModel = tableBill.getColumnModel();
                tableColumnModel.getColumn(BILL_ID_COLUMN_INDEX).setHeaderValue("Bill ID");
                tableColumnModel.getColumn(BILL_DATE_COLUMN_INDEX).setHeaderValue("Date");
                tableColumnModel.getColumn(BILL_TIME_COLUMN_INDEX).setHeaderValue("Time");
                tableColumnModel.getColumn(BILL_TOTAL_MONEY_COLUMN_INDEX).setHeaderValue("Total money");
                tableColumnModel.getColumn(BILL_CUSTOMER_MONEY_COLUMN_INDEX).setHeaderValue("Customer's money");
                tableColumnModel.getColumn(BILL_CHANGE_MONEY_COLUMN_INDEX).setHeaderValue("Change money");
                tableColumnModel.getColumn(BILL_EMPLOYEE_ID_COLUMN_INDEX).setHeaderValue("Employee ID");
                tableColumnModel.getColumn(BILL_EMPLOYEE_NAME_COLUMN_INDEX).setHeaderValue("Employee name");

                break;
            }
            case VIETNAMESE: {
                TitledBorder titledBorder = (TitledBorder) panelDateInput.getBorder();
                titledBorder.setTitle("Lọc ngày");

                labelDateFrom.setText("Từ:");
                labelDateTo.setText("Đến:");
                labelSearchEmployeeName.setText("Tìm theo tên nhân viên:");

                btnApply.setText("Lọc");
                btnSearchClear.setText("Xóa");
                btnExport.setText("Xuất");
                btnTodayBill.setText("Hóa đơn hôm nay");
                btnDetail.setText("Xem chi tiết hóa đơn");

                TableColumnModel tableColumnModel = tableBill.getColumnModel();
                tableColumnModel.getColumn(BILL_ID_COLUMN_INDEX).setHeaderValue("Số hóa đơn");
                tableColumnModel.getColumn(BILL_DATE_COLUMN_INDEX).setHeaderValue("Ngày");
                tableColumnModel.getColumn(BILL_TIME_COLUMN_INDEX).setHeaderValue("Giờ");
                tableColumnModel.getColumn(BILL_TOTAL_MONEY_COLUMN_INDEX).setHeaderValue("Tổng tiền");
                tableColumnModel.getColumn(BILL_CUSTOMER_MONEY_COLUMN_INDEX).setHeaderValue("Tiền khách");
                tableColumnModel.getColumn(BILL_CHANGE_MONEY_COLUMN_INDEX).setHeaderValue("Tiền thối");
                tableColumnModel.getColumn(BILL_EMPLOYEE_ID_COLUMN_INDEX).setHeaderValue("Nhân viên ID");
                tableColumnModel.getColumn(BILL_EMPLOYEE_NAME_COLUMN_INDEX).setHeaderValue("Tên nhân viên");

                break;
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDateInput = new javax.swing.JPanel();
        labelDateFrom = new javax.swing.JLabel();
        labelDateTo = new javax.swing.JLabel();
        datechooserDateFrom = new com.toedter.calendar.JDateChooser();
        datechooserDateTo = new com.toedter.calendar.JDateChooser();
        btnApply = new javax.swing.JButton();
        scrollPane_BillTable = new javax.swing.JScrollPane();
        tableBill = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        labelSearchEmployeeName = new javax.swing.JLabel();
        textfSearchEmployeeName = new javax.swing.JTextField();
        btnSearchClear = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnExport = new javax.swing.JButton();
        btnTodayBill = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnDetail = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setName("BillHistory"); // NOI18N

        panelDateInput.setBackground(new java.awt.Color(255, 255, 255));
        panelDateInput.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Date Filter", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        labelDateFrom.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelDateFrom.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelDateFrom.setText("From");

        labelDateTo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelDateTo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelDateTo.setText("To");

        datechooserDateFrom.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        datechooserDateTo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        btnApply.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnApply.setForeground(new java.awt.Color(51, 51, 51));
        btnApply.setText("Apply");
        btnApply.setFocusPainted(false);

        javax.swing.GroupLayout panelDateInputLayout = new javax.swing.GroupLayout(panelDateInput);
        panelDateInput.setLayout(panelDateInputLayout);
        panelDateInputLayout.setHorizontalGroup(
            panelDateInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDateInputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(datechooserDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(labelDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(datechooserDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnApply, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(517, Short.MAX_VALUE))
        );
        panelDateInputLayout.setVerticalGroup(
            panelDateInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDateInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDateInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDateInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnApply, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelDateInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDateInputLayout.createSequentialGroup()
                                .addComponent(labelDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1))
                            .addComponent(datechooserDateTo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(datechooserDateFrom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(labelDateTo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tableBill.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tableBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bill ID", "Date", "Time", "Total money", "Customer's money", "Change", "Employee ID", "Employee name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.Long.class, java.lang.Long.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 30));

        labelSearchEmployeeName.setBackground(new java.awt.Color(255, 255, 255));
        labelSearchEmployeeName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelSearchEmployeeName.setText("Search employee name:");
        labelSearchEmployeeName.setOpaque(true);
        labelSearchEmployeeName.setPreferredSize(new java.awt.Dimension(160, 30));
        jPanel3.add(labelSearchEmployeeName);

        textfSearchEmployeeName.setPreferredSize(new java.awt.Dimension(250, 30));
        jPanel3.add(textfSearchEmployeeName);

        btnSearchClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSearchClear.setForeground(new java.awt.Color(51, 51, 51));
        btnSearchClear.setText("Clear");
        btnSearchClear.setFocusPainted(false);
        btnSearchClear.setPreferredSize(new java.awt.Dimension(90, 30));
        jPanel3.add(btnSearchClear);

        jPanel1.setPreferredSize(new java.awt.Dimension(280, 40));

        btnExport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnExport.setForeground(new java.awt.Color(51, 51, 51));
        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Excel_30px.png"))); // NOI18N
        btnExport.setText("Export");
        btnExport.setBorderPainted(false);
        btnExport.setPreferredSize(new java.awt.Dimension(115, 40));

        btnTodayBill.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnTodayBill.setForeground(new java.awt.Color(51, 51, 51));
        btnTodayBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Today_30px.png"))); // NOI18N
        btnTodayBill.setText("Today bills");
        btnTodayBill.setBorderPainted(false);
        btnTodayBill.setPreferredSize(new java.awt.Dimension(115, 40));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnTodayBill, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnTodayBill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnDetail.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Content_30px.png"))); // NOI18N
        btnDetail.setText("View bill detail");
        btnDetail.setPreferredSize(new java.awt.Dimension(260, 50));
        jPanel2.add(btnDetail);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDateInput, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPane_BillTable)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDateInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane_BillTable, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelDateFrom;
    private javax.swing.JLabel labelDateTo;
    private javax.swing.JLabel labelSearchEmployeeName;
    private javax.swing.JPanel panelDateInput;
    private javax.swing.JScrollPane scrollPane_BillTable;
    private javax.swing.JTable tableBill;
    private javax.swing.JTextField textfSearchEmployeeName;
    // End of variables declaration//GEN-END:variables
}
