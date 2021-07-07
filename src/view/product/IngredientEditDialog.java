package view.product;

import control.product.ProductControllerInterface;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.product.ProductManageModelInterface;
import model.product.ingredientDetail.IngredientDetailModelInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import util.constant.AppConstant;
import util.swing.UIControl;
import view.MessageShowing;

public class IngredientEditDialog extends javax.swing.JDialog implements ActionListener,
        MessageShowing, SettingUpdateObserver {

    private static final int INGREDIENT_DETAIL_NAME_COLUMN_INDEX = 0;
    private static final int INGREDIENT_DETAIL_TYPE_COLUMN_INDEX = 1;
    private static final int INGREDIENT_DETAIL_UNIT_COLUMN_INDEX = 2;
    private static final int INGREDIENT_DETAIL_AMOUNT_COLUMN_INDEX = 3;

    private ProductControllerInterface productController;
    private ProductManageModelInterface productManageModel;

    private DefaultTableModel tableIngredientModel;
    private SpinnerNumberModel spinnerAmountFloatModel;
    private String productNameText;
    private String productSizeText;
    private boolean isNewProduct;

    public IngredientEditDialog(java.awt.Frame parent, boolean modal,
            ProductManageModelInterface productManageModel,
            ProductControllerInterface controller) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        this.productManageModel = productManageModel;
        this.productController = controller;
        this.tableIngredientModel = (DefaultTableModel) tableIngredient.getModel();
        this.spinnerAmountFloatModel = (SpinnerNumberModel) spinnerAmount.getModel();
        createView();
        createControl();
        addQuantitiesSpinnerKeyListener();
        AutoCompleteDecorator.decorate(combName);
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

        String labelName = " " + "New name" + " - " + "New size" + " ";
        if (!isNewProduct) {
            labelName = " " + this.productNameText + " - " + this.productSizeText + " ";
        }
        labelProductName.setText(labelName);
        super.setVisible(b);
    }

    private void createView() {
        UIControl.setDefaultTableHeader2(tableIngredient);
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
        btnSave.addActionListener(this);
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
        } else if (source == btnSave) {
            this.productController.requestSaveIngredientDetailBuffer();
        } else if (source == btnCancel) {
            this.productController.requestCancelEditIngredientDetail();
        }
    }

    @Override
    public void updateSettingObserver() {
        if (AppSetting.getInstance().getAppLanguage() == AppSetting.Language.ENGLISH) {
            setTitle("Edit ingredient detail of product");

            TitledBorder titledBorder = (TitledBorder) panelSelectIngredient.getBorder();
            titledBorder.setTitle("Select ingredient");
            labelIngredientName.setText("Name:");
            labelIngredientUnit.setText("Unit:");
            labelIngredientAmount.setText("Amount:");
            btnAdd.setText("Add");
            labelIngredientOfProduct.setText("Ingredients of product");
            btnRemove.setText("Remove");
            btnClear.setText("Clear");

            TableColumnModel tableColumnModel = tableIngredient.getColumnModel();
            tableColumnModel.getColumn(INGREDIENT_DETAIL_NAME_COLUMN_INDEX).setHeaderValue("Name");
            tableColumnModel.getColumn(INGREDIENT_DETAIL_TYPE_COLUMN_INDEX).setHeaderValue("Type");
            tableColumnModel.getColumn(INGREDIENT_DETAIL_UNIT_COLUMN_INDEX).setHeaderValue("Unit");
            tableColumnModel.getColumn(INGREDIENT_DETAIL_AMOUNT_COLUMN_INDEX).setHeaderValue("Amount");

            btnSave.setText("Save");
            btnCancel.setText("Cancel");
        } else {
            setTitle("Hộp thoại điều chỉnh thành phần nguyên liệu của sản phẩm");

            TitledBorder titledBorder = (TitledBorder) panelSelectIngredient.getBorder();
            titledBorder.setTitle("Chọn nguyên liệu");
            labelIngredientName.setText("Tên:");
            labelIngredientUnit.setText("Đơn vị:");
            labelIngredientAmount.setText("Số lượng:");
            btnAdd.setText("Thêm");
            labelIngredientOfProduct.setText("Các thành phần nguyên liệu");
            btnRemove.setText("Bỏ thành phần");
            btnClear.setText("Xóa danh sách");

            TableColumnModel tableColumnModel = tableIngredient.getColumnModel();
            tableColumnModel.getColumn(INGREDIENT_DETAIL_NAME_COLUMN_INDEX).setHeaderValue("Tên");
            tableColumnModel.getColumn(INGREDIENT_DETAIL_TYPE_COLUMN_INDEX).setHeaderValue("Loại");
            tableColumnModel.getColumn(INGREDIENT_DETAIL_UNIT_COLUMN_INDEX).setHeaderValue("Đơn vị");
            tableColumnModel.getColumn(INGREDIENT_DETAIL_AMOUNT_COLUMN_INDEX).setHeaderValue("Số lượng");

            btnSave.setText("Lưu");
            btnCancel.setText("Thoát");
        }
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(),
                JOptionPane.ERROR_MESSAGE, AppConstant.IMAGE_ICON_MESSAGE_DIALOG_ERROR);
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(),
                JOptionPane.INFORMATION_MESSAGE, AppConstant.IMAGE_ICON_MESSAGE_DIALOG_INFO);
    }

    @Override
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(),
                JOptionPane.WARNING_MESSAGE, AppConstant.IMAGE_ICON_MESSAGE_DIALOG_WARNING);
    }

    public void setNameAndSizeText(String productName, String size, boolean isNewProduct) {
        this.productNameText = productName;
        this.productSizeText = size;
        this.isNewProduct = isNewProduct;
    }

    @Override
    public void dispose() {
        spinnerAmount.getModel().setValue(1.0f);
        super.dispose();
    }
    private void addQuantitiesSpinnerKeyListener() {
        spinnerAmount.getEditor().getComponent(0).addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar()) == false && e.getKeyChar() != 8 && e.getKeyChar() != 127) {
                    e.consume();
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar()) == false && e.getKeyChar() != 8 && e.getKeyChar() != 127) {
                    e.consume();
                }
            }
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        scrpaneIngredientSelected = new javax.swing.JScrollPane();
        tableIngredient = new javax.swing.JTable();
        labelIngredientOfProduct = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        btnCancel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnRemove = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        panelSelectIngredient = new javax.swing.JPanel();
        labelIngredientName = new javax.swing.JLabel();
        combName = new javax.swing.JComboBox<>();
        labelIngredientUnit = new javax.swing.JLabel();
        combUnit = new javax.swing.JComboBox<>();
        labelIngredientAmount = new javax.swing.JLabel();
        spinnerAmount = new javax.swing.JSpinner();
        btnAdd = new javax.swing.JButton();
        labelProductName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tableIngredient.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
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

        labelIngredientOfProduct.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelIngredientOfProduct.setText("Ingredients of product");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel3.add(btnSave);
        jPanel3.add(filler2);

        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel3.add(btnCancel);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnRemove.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Trash_30px.png"))); // NOI18N
        btnRemove.setText("Remove");
        jPanel2.add(btnRemove);

        btnClear.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clear.png"))); // NOI18N
        btnClear.setText("Clear");
        jPanel2.add(btnClear);

        panelSelectIngredient.setBackground(new java.awt.Color(255, 255, 255));
        panelSelectIngredient.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select ingredient", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        labelIngredientName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelIngredientName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelIngredientName.setText("Name:");

        combName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combName.setPreferredSize(new java.awt.Dimension(200, 31));

        labelIngredientUnit.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelIngredientUnit.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelIngredientUnit.setText("Unit:");

        combUnit.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combUnit.setPreferredSize(new java.awt.Dimension(105, 31));

        labelIngredientAmount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelIngredientAmount.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelIngredientAmount.setText("Amount:");

        spinnerAmount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        spinnerAmount.setModel(new javax.swing.SpinnerNumberModel(1.0f, 1.0f, null, 1.0f));

        javax.swing.GroupLayout panelSelectIngredientLayout = new javax.swing.GroupLayout(panelSelectIngredient);
        panelSelectIngredient.setLayout(panelSelectIngredientLayout);
        panelSelectIngredientLayout.setHorizontalGroup(
            panelSelectIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelectIngredientLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelIngredientName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(combName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelIngredientUnit)
                .addGap(5, 5, 5)
                .addComponent(combUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelIngredientAmount)
                .addGap(5, 5, 5)
                .addComponent(spinnerAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelSelectIngredientLayout.setVerticalGroup(
            panelSelectIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelectIngredientLayout.createSequentialGroup()
                .addGroup(panelSelectIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSelectIngredientLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelSelectIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelIngredientName)))
                    .addGroup(panelSelectIngredientLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(labelIngredientUnit))
                    .addGroup(panelSelectIngredientLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelSelectIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSelectIngredientLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(labelIngredientAmount))
                            .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(8, 8, 8))
        );

        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Add_30px.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setPreferredSize(new java.awt.Dimension(100, 46));

        labelProductName.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        labelProductName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelProductName.setText(" Product Name - M ");
        labelProductName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));
        labelProductName.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrpaneIngredientSelected, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panelSelectIngredient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(labelProductName))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelIngredientOfProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelProductName)
                            .addComponent(panelSelectIngredient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelIngredientOfProduct)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> combName;
    private javax.swing.JComboBox<String> combUnit;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelIngredientAmount;
    private javax.swing.JLabel labelIngredientName;
    private javax.swing.JLabel labelIngredientOfProduct;
    private javax.swing.JLabel labelIngredientUnit;
    private javax.swing.JLabel labelProductName;
    private javax.swing.JPanel panelSelectIngredient;
    private javax.swing.JScrollPane scrpaneIngredientSelected;
    private javax.swing.JSpinner spinnerAmount;
    private javax.swing.JTable tableIngredient;
    // End of variables declaration//GEN-END:variables
}
