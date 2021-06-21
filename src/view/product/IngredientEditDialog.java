package view.product;

import control.product.ProductControllerInterface;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import model.product.ProductManageModelInterface;
import model.product.ingredientDetail.IngredientDetailModelInterface;
import util.swing.UIControl;
import view.MessageShowing;

public class IngredientEditDialog extends javax.swing.JDialog implements ActionListener,
        MessageShowing {

    public static final String DIALOG_TITLE = "Ingredient product editing dialog";

    private static final int INGREDIENT_DETAIL_NAME_COLUMN_INDEX = 0;
    private static final int INGREDIENT_DETAIL_UNIT_COLUMN_INDEX = 2;
    private static final int INGREDIENT_DETAIL_AMOUNT_COLUMN_INDEX = 3;

    private ProductControllerInterface productController;
    private ProductManageModelInterface productManageModel;

    private DefaultTableModel tableIngredientModel;
    private SpinnerNumberModel spinnerAmountFloatModel;

    public IngredientEditDialog(java.awt.Frame parent, boolean modal,
            ProductManageModelInterface productManageModel,
            ProductControllerInterface controller) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        setTitle(DIALOG_TITLE);
        this.productManageModel = productManageModel;
        this.productController = controller;
        this.tableIngredientModel = (DefaultTableModel) tableIngredient.getModel();
        this.spinnerAmountFloatModel = (SpinnerNumberModel) spinnerAmount.getModel();
        createView();
        createControl();
    }

    @Override
    public void setVisible(boolean b) {
        List<IngredientDetailModelInterface> ingredientDetails = this.productManageModel
                .getBufferedIngredientDetailList();
        clearTableIngredient();
        ingredientDetails.forEach(e -> {
            addRowTableIngredient(e);
        });
        this.productManageModel.setBufferListModifiedFlag(false);
        super.setVisible(b);
    }

    private void createView() {
        UIControl.setDefaultTableHeader(tableIngredient);
        this.getContentPane().setBackground(Color.WHITE);
        reloadIngredientInputData();
    }

    public void reloadIngredientInputData() {
        this.combName.removeAllItems();
        List<String> ingredientNames = this.productManageModel.getAllIngredientName();
        ingredientNames.forEach(ingredientName -> {
            combName.addItem(ingredientName);
        });
        if (ingredientNames.size() > 0) {
            combName.setSelectedIndex(0);
        }
        setUnitCanConvert();
    }

    private void createControl() {
        btnRemove.addActionListener(this);
        btnClear.addActionListener(this);
        btnSaveChange.addActionListener(this);
        btnAdd.addActionListener(this);
        btnCancel.addActionListener(this);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                productController.requestCancelEditIngredientDetail();
            }
        });

        combName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUnitCanConvert();
            }
        });
    }

    public void setUnitCanConvert() {
        String ingredientName = getSelectedIngredientName();
        if (ingredientName == null) {
            return;
        }
        List<String> unitNames = productManageModel
                .getUnitNamePossibleOfIngredient(ingredientName);
        combUnit.removeAllItems();
        unitNames.forEach(unitName -> {
            combUnit.addItem(unitName);
        });
    }

    public void addRowTableIngredient(IngredientDetailModelInterface ingredientDetail) {
        Object[] record = new Object[]{
            ingredientDetail.getIngredientName(),
            ingredientDetail.getIngredientTypeName(),
            ingredientDetail.getUnitName(),
            ingredientDetail.getAmount()
        };
        tableIngredientModel.addRow(record);
    }

    public void updateRowTableIngredient(int rowID,
            IngredientDetailModelInterface ingredientDetail) {
        tableIngredientModel.setValueAt(ingredientDetail.getUnitName(), rowID,
                INGREDIENT_DETAIL_UNIT_COLUMN_INDEX);
        tableIngredientModel.setValueAt(ingredientDetail.getAmount(), rowID,
                INGREDIENT_DETAIL_AMOUNT_COLUMN_INDEX);
    }

    public void removeRowTableIngredient(int rowID) {
        if (rowID < 0 || rowID >= tableIngredientModel.getRowCount()) {
            throw new IndexOutOfBoundsException();
        }
        tableIngredientModel.removeRow(rowID);
    }

    public void clearTableIngredient() {
        tableIngredientModel.setRowCount(0);
    }

    public String getSelectedIngredientName() {
        Object ret = this.combName.getSelectedItem();
        if (ret == null) {
            return null;
        }
        return (String) ret;
    }

    public String getSelectedUnitName() {
        Object ret = this.combUnit.getSelectedItem();
        if (ret == null) {
            return null;
        }
        return (String) ret;
    }

    public float getAmountInput() {
        return (float) spinnerAmountFloatModel.getValue();
    }

    public int getSelectedRowTableIngredient() {
        return tableIngredient.getSelectedRow();
    }

    public int getTableIngredientRowCount() {
        return tableIngredientModel.getRowCount();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnAdd) {
            this.productController.requestAddIngredientDetailBuffer();
        } else if (source == btnRemove) {
            this.productController.requestRemoveIngredientDetailBuffer();
        } else if (source == btnClear) {
            this.productController.requestClearIngredientDetailBuffer();
        } else if (source == btnSaveChange) {
            this.productController.requestSaveIngredientDetailBuffer();
        } else if (source == btnCancel) {
            this.productController.requestCancelEditIngredientDetail();
        }
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "BakeryMS",
                JOptionPane.ERROR_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/error.png")));
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "BakeryMS",
                JOptionPane.INFORMATION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/infor.png")));
    }

    @Override
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "BakeryMS",
                JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/warning.png")));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        scrpaneIngredientSelected = new javax.swing.JScrollPane();
        tableIngredient = new javax.swing.JTable();
        label_SelectedIngredients = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnSaveChange = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        btnCancel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnRemove = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        combUnit = new javax.swing.JComboBox<>();
        label_SelectedIngredients2 = new javax.swing.JLabel();
        label_SelectedIngredients1 = new javax.swing.JLabel();
        label_SelectedIngredients3 = new javax.swing.JLabel();
        combName = new javax.swing.JComboBox<>();
        spinnerAmount = new javax.swing.JSpinner();
        btnAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tableIngredient.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tableIngredient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Type", "Unit", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
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
        tableIngredient.setSelectionBackground(new java.awt.Color(113, 168, 255));
        tableIngredient.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableIngredient.getTableHeader().setReorderingAllowed(false);
        scrpaneIngredientSelected.setViewportView(tableIngredient);

        label_SelectedIngredients.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_SelectedIngredients.setText("Ingredients of product");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnSaveChange.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_save-all-changes.png"))); // NOI18N
        btnSaveChange.setContentAreaFilled(false);
        btnSaveChange.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSaveChange.setFocusPainted(false);
        jPanel3.add(btnSaveChange);
        jPanel3.add(filler2);

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_cancel_50.png"))); // NOI18N
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setFocusPainted(false);
        jPanel3.add(btnCancel);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnRemove.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnRemove.setText("Remove");
        jPanel2.add(btnRemove);

        btnClear.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnClear.setText("Clear");
        jPanel2.add(btnClear);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select ingredient", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        combUnit.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        label_SelectedIngredients2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_SelectedIngredients2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_SelectedIngredients2.setText("Unit:");

        label_SelectedIngredients1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_SelectedIngredients1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_SelectedIngredients1.setText("Name:");

        label_SelectedIngredients3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_SelectedIngredients3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_SelectedIngredients3.setText("Amount:");

        combName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        spinnerAmount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        spinnerAmount.setModel(new javax.swing.SpinnerNumberModel(1.0f, 1.0f, null, 1.0f));

        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnAdd.setText("Add");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(label_SelectedIngredients1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combName, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(label_SelectedIngredients2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(label_SelectedIngredients3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_SelectedIngredients2)
                        .addComponent(combUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_SelectedIngredients1)
                        .addComponent(label_SelectedIngredients3)
                        .addComponent(combName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(btnAdd)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrpaneIngredientSelected)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(label_SelectedIngredients, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_SelectedIngredients)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scrpaneIngredientSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSaveChange;
    private javax.swing.JComboBox<String> combName;
    private javax.swing.JComboBox<String> combUnit;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel label_SelectedIngredients;
    private javax.swing.JLabel label_SelectedIngredients1;
    private javax.swing.JLabel label_SelectedIngredients2;
    private javax.swing.JLabel label_SelectedIngredients3;
    private javax.swing.JScrollPane scrpaneIngredientSelected;
    private javax.swing.JSpinner spinnerAmount;
    private javax.swing.JTable tableIngredient;
    // End of variables declaration//GEN-END:variables
}
