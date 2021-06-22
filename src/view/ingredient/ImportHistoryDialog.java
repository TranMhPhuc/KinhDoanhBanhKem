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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ingredient.IngredientManageModelInterface;
import model.ingredient.importDetail.IngredientImportDetailInterface;
import util.constant.AppConstant;
import util.swing.UIControl;
import view.MessageShowing;

public class ImportHistoryDialog extends javax.swing.JDialog implements MessageShowing {

    private static final String DIALOG_TITLE = "BakeryMS";

    private IngredientControllerInterface ingredientController;
    private IngredientManageModelInterface ingredientManageModel;

    private DefaultTableModel tableImportHistoryModel;

    public ImportHistoryDialog(java.awt.Frame parent, boolean modal,
            IngredientControllerInterface ingredientController,
            IngredientManageModelInterface ingredientManageModel) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        setTitle(DIALOG_TITLE);
        UIControl.setDefaultTableHeader(tableImportHistory);

        this.ingredientController = ingredientController;
        this.ingredientManageModel = ingredientManageModel;

        this.tableImportHistoryModel = (DefaultTableModel) tableImportHistory.getModel();

        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingredientController.viewImportHistory();
            }
        });
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
                visualDate.format(AppConstant.GLOBAL_DATE_FORMATTER),
                visualTime.format(AppConstant.GLOBAL_TIME_FORMATTER),
                ingredientImportDetail.getAmount(),
                ingredientImportDetail.getImportUnitName(),
                ingredientImportDetail.getTotalCost()
            };
            tableImportHistoryModel.addRow(record);
        }
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, DIALOG_TITLE, JOptionPane.ERROR_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/error.png")));
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, DIALOG_TITLE, JOptionPane.INFORMATION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/infor.png")));
    }

    @Override
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, DIALOG_TITLE, JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/warning.png")));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        label_ImportHistory = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableImportHistory = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dateChooserdateFrom = new com.toedter.calendar.JDateChooser();
        dateChooserdateTo = new com.toedter.calendar.JDateChooser();
        btnApply = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        label_ImportHistory.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        label_ImportHistory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_ImportHistory.setText("Ingredient Import History");

        tableImportHistory.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tableImportHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ingredient name", "Import date", "Time", "Amount", "Unit", "Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Long.class
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
        tableImportHistory.setSelectionBackground(new java.awt.Color(113, 168, 255));
        tableImportHistory.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tableImportHistory);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Date filter", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Date from:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setText("Date to:");

        btnApply.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnApply.setText("Apply");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateChooserdateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateChooserdateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnApply, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnApply, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dateChooserdateTo, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addComponent(dateChooserdateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_ImportHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 843, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_ImportHistory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private com.toedter.calendar.JDateChooser dateChooserdateFrom;
    private com.toedter.calendar.JDateChooser dateChooserdateTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_ImportHistory;
    private javax.swing.JTable tableImportHistory;
    // End of variables declaration//GEN-END:variables
}
