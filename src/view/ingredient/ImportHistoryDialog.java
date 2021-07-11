package view.ingredient;

import control.ingredient.IngredientControllerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.ingredient.IngredientManageModelInterface;
import model.ingredient.importDetail.IngredientImportDetailInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import util.constant.AppConstant;
import util.swing.UIControl;
import view.MessageShowing;

public class ImportHistoryDialog extends javax.swing.JDialog implements MessageShowing,
        SettingUpdateObserver {

    private static final int INGREDIENT_NAME_COLUMN_INDEX = 0;
    private static final int PROVIDER_NAME_COLUMN_INDEX = 1;
    private static final int IMPORT_DATE_COLUMN_INDEX = 2;
    private static final int TIME_COLUMN_INDEX = 3;
    private static final int AMOUNT_COLUMN_INDEX = 4;
    private static final int UNIT_COLUMN_INDEX = 5;
    private static final int COST_COLUMN_INDEX = 6;

    private IngredientControllerInterface ingredientController;
    private IngredientManageModelInterface ingredientManageModel;

    private DefaultTableModel tableImportHistoryModel;

    public ImportHistoryDialog(java.awt.Frame parent, boolean modal,
            IngredientControllerInterface ingredientController,
            IngredientManageModelInterface ingredientManageModel) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);

        this.ingredientController = ingredientController;
        this.ingredientManageModel = ingredientManageModel;

        this.tableImportHistoryModel = (DefaultTableModel) tableImportHistory.getModel();
        createView();
        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingredientController.viewImportHistory();
            }
        });
    }

    private void createView() {
        UIControl.setColumnWidth(tableImportHistory, 0, 200);
        UIControl.setDefaultTableHeader(tableImportHistory);
        UIControl.setColumnWidth(tableImportHistory, 1, 400);
        UIControl.setColumnWidth(tableImportHistory, 6, 125);
        UIControl.setColumnWidth(tableImportHistory, 4, 70);
        UIControl.setColumnWidth(tableImportHistory, 3, 75);
        UIControl.setColumnWidth(tableImportHistory, 5, 100);
        UIControl.setHorizontalAlignmentForColumn(tableImportHistory, 3, JLabel.CENTER);
        UIControl.setHorizontalAlignmentForColumn(tableImportHistory, 2, JLabel.CENTER);
        UIControl.setHorizontalAlignmentForColumn(tableImportHistory, 4, JLabel.CENTER);
        UIControl.setHorizontalAlignmentForColumn(tableImportHistory, 0, JLabel.LEFT);

    }

    public Date getDateFromInput() {
        return dateChooserdateFrom.getDate();
    }

    public void setDateFrom(Date dateFrom) {
        dateChooserdateFrom.setDate(dateFrom);
    }

    public Date getDateToInput() {
        return dateChooserdateTo.getDate();
    }

    public void setDateTo(Date dateTo) {
        dateChooserdateTo.setDate(dateTo);
    }

    public void showImportHistoryFromDateRange() {
        Date dateFrom = dateChooserdateFrom.getDate();

        Date dateTo = dateChooserdateTo.getDate();

        List<IngredientImportDetailInterface> ingredientImportDetails
                = this.ingredientManageModel
                        .getImportHistoryFromDateRange(dateFrom, dateTo);

        tableImportHistoryModel.setRowCount(0);

        LocalDate visualDate = null;
        LocalTime visualTime = null;

        for (IngredientImportDetailInterface ingredientImportDetail : ingredientImportDetails) {
            Timestamp importTimestamp = ingredientImportDetail.getDate();
            visualDate = importTimestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            visualTime = importTimestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

            Object[] record = new Object[]{
                ingredientImportDetail.getIngredientName(),
                ingredientImportDetail.getProviderName(),
                visualDate.format(AppConstant.GLOBAL_DATE_FORMATTER),
                visualTime.format(AppConstant.GLOBAL_TIME_FORMATTER),
                ingredientImportDetail.getAmount(),
                ingredientImportDetail.getImportUnitName(),
                AppConstant.GLOBAL_VIE_CURRENCY_FORMATTER.format(ingredientImportDetail.getTotalCost())
            };
            tableImportHistoryModel.addRow(record);
        }
    }

    @Override
    public void updateSettingObserver() {
        if (AppSetting.getInstance().getAppLanguage() == AppSetting.Language.ENGLISH) {
            setTitle("Ingredient import history");

            labelMainTitle.setText("Ingredient Import History");

            TitledBorder titledBorder = (TitledBorder) panelDateInput.getBorder();
            titledBorder.setTitle("Date filter");

            labelDateFrom.setText("Date from:");
            labelDateTo.setText("Date to:");

            btnApply.setText("Apply");

            TableColumnModel tableColumnModel = tableImportHistory.getColumnModel();
            tableColumnModel.getColumn(INGREDIENT_NAME_COLUMN_INDEX).setHeaderValue("Ingredient name");
            tableColumnModel.getColumn(PROVIDER_NAME_COLUMN_INDEX).setHeaderValue("Provider name");
            tableColumnModel.getColumn(IMPORT_DATE_COLUMN_INDEX).setHeaderValue("Import date");
            tableColumnModel.getColumn(TIME_COLUMN_INDEX).setHeaderValue("Time");
            tableColumnModel.getColumn(AMOUNT_COLUMN_INDEX).setHeaderValue("Amount");
            tableColumnModel.getColumn(UNIT_COLUMN_INDEX).setHeaderValue("Unit");
            tableColumnModel.getColumn(COST_COLUMN_INDEX).setHeaderValue("Cost");
        } else {
            setTitle("Hộp thoại xem lịch sử nhập nguyên liệu");

            labelMainTitle.setText("Lịch Sử Nhập Nguyên Liệu");

            TitledBorder titledBorder = (TitledBorder) panelDateInput.getBorder();
            titledBorder.setTitle("Lọc ngày");

            labelDateFrom.setText("Ngày bắt đầu:");
            labelDateTo.setText("Ngày kết thúc:");

            btnApply.setText("Lọc");

            TableColumnModel tableColumnModel = tableImportHistory.getColumnModel();
            tableColumnModel.getColumn(INGREDIENT_NAME_COLUMN_INDEX).setHeaderValue("Tên nguyên liệu");
            tableColumnModel.getColumn(PROVIDER_NAME_COLUMN_INDEX).setHeaderValue("Tên nhà cung cấp");
            tableColumnModel.getColumn(IMPORT_DATE_COLUMN_INDEX).setHeaderValue("Ngày nhập");
            tableColumnModel.getColumn(TIME_COLUMN_INDEX).setHeaderValue("Thời gian");
            tableColumnModel.getColumn(AMOUNT_COLUMN_INDEX).setHeaderValue("Số lượng");
            tableColumnModel.getColumn(UNIT_COLUMN_INDEX).setHeaderValue("Đơn vị");
            tableColumnModel.getColumn(COST_COLUMN_INDEX).setHeaderValue("Giá");
        }

        repaint();
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.ERROR_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/error.png")));
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.INFORMATION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/infor.png")));
    }

    @Override
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/warning.png")));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelMainTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableImportHistory = new javax.swing.JTable();
        panelDateInput = new javax.swing.JPanel();
        labelDateFrom = new javax.swing.JLabel();
        labelDateTo = new javax.swing.JLabel();
        dateChooserdateFrom = new com.toedter.calendar.JDateChooser();
        dateChooserdateTo = new com.toedter.calendar.JDateChooser();
        btnApply = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        labelMainTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        labelMainTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMainTitle.setText("Ingredient Import History");

        tableImportHistory.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tableImportHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ingredient name", "Provider", "Import date", "Time", "Amount", "Unit", "Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Long.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableImportHistory.setSelectionBackground(new java.awt.Color(113, 168, 255));
        tableImportHistory.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableImportHistory.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableImportHistory);

        panelDateInput.setBackground(new java.awt.Color(255, 255, 255));
        panelDateInput.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Date filter", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        labelDateFrom.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelDateFrom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelDateFrom.setText("Date from:");

        labelDateTo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelDateTo.setText("Date to:");

        dateChooserdateFrom.setDateFormatString("dd - MM - yyyy");
        dateChooserdateFrom.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        dateChooserdateTo.setDateFormatString("dd - MM - yyyy");
        dateChooserdateTo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        btnApply.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnApply.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tick3.png"))); // NOI18N
        btnApply.setText("Apply");

        javax.swing.GroupLayout panelDateInputLayout = new javax.swing.GroupLayout(panelDateInput);
        panelDateInput.setLayout(panelDateInputLayout);
        panelDateInputLayout.setHorizontalGroup(
            panelDateInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDateInputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelDateFrom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateChooserdateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(labelDateTo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateChooserdateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnApply, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelDateInputLayout.setVerticalGroup(
            panelDateInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDateInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDateInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnApply, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelDateInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(labelDateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dateChooserdateTo, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addComponent(dateChooserdateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelDateTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelMainTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1095, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panelDateInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelMainTitle)
                .addGap(18, 18, 18)
                .addComponent(panelDateInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private com.toedter.calendar.JDateChooser dateChooserdateFrom;
    private com.toedter.calendar.JDateChooser dateChooserdateTo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelDateFrom;
    private javax.swing.JLabel labelDateTo;
    private javax.swing.JLabel labelMainTitle;
    private javax.swing.JPanel panelDateInput;
    private javax.swing.JTable tableImportHistory;
    // End of variables declaration//GEN-END:variables
}
