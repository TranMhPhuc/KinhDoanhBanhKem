package view.bill;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import util.swing.UIControl;
import view.MessageShowing;
import control.bill.create.BillCreateControllerInterface;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JLabel;
import model.bill.BillModelInterface;
import model.bill.detail.ProductDetailModelInterface;
import model.product.ProductSimpleModelInterface;
import model.bill.BillCreateModelInterface;
import util.swing.CurrencyTextField;
import util.swing.NumberRenderer;

public class BillCreatePanel extends javax.swing.JPanel implements ActionListener,
        MessageShowing, BillUpdateObserver {

    public static final int PRODUCT_OFFER_ID_COLUMN_INDEX = 0;
    public static final int PRODUCT_OFFER_LEFT_AMOUNT_COLUMN_INDEX = 4;

    public static final int PRODUCT_SELECT_ID_COLUMN_INDEX = 0;
    public static final int PRODUCT_SELECT_AMOUNT_COLUMN_INDEX = 3;
    public static final int PRODUCT_SELECT_PRICE_COLUMN_INDEX = 4;

    private JFrame mainFrame;

    private BillCreateModelInterface billManageModel;
    private BillCreateControllerInterface billCreateController;

    private DefaultTableModel tableOfferedProductModel;
    private DefaultTableModel tableSelectedProductModel;

    public BillCreatePanel() {
        initComponents();
        tableOfferedProductModel = (DefaultTableModel) tableOfferedProduct.getModel();
        tableSelectedProductModel = (DefaultTableModel) tableSelectedProduct.getModel();
        createView();
        createControl();
    }

    public void setMaiFrame(JFrame maiFrame) {
        this.mainFrame = maiFrame;
    }

    public JFrame getMaiFrame() {
        return mainFrame;
    }

    public void setBillManageModel(BillCreateModelInterface billManageModel) {
        if (billManageModel == null) {
            throw new NullPointerException("Bill manage model instance is null.");
        }
        this.billManageModel = billManageModel;
        billManageModel.registerBillUpdateObserver(this);

        String newBillID = billManageModel.getNextBillIDText();
        textfBillID.setText(newBillID);
    }

    public void setBillCreateController(BillCreateControllerInterface controller) {
        if (controller == null) {
            throw new NullPointerException("Bill controller instance is null.");
        }
        this.billCreateController = controller;
        resetProductList();
    }

    private void createView() {
        UIControl.setDefaultTableHeader(tableOfferedProduct);
        UIControl.setDefaultTableHeader(tableSelectedProduct);
        textfTotalMoney.setValue(0);
        textfSearchProductName.setText("");
        clearTableProductSelect();

        tableOfferedProduct.getColumnModel().getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        tableSelectedProduct.getColumnModel().getColumn(4).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        UIControl.setHorizontalAlignmentForColumn(tableOfferedProduct, 2, JLabel.CENTER);
        UIControl.setHorizontalAlignmentForColumn(tableSelectedProduct, 2, JLabel.CENTER);
    }

    private void createControl() {
        btnSearchClear.addActionListener(this);
        btnExport.addActionListener(this);
        btnSelect.addActionListener(this);
        btnRemove.addActionListener(this);
        btnClear.addActionListener(this);
        btnEditAmount.addActionListener(this);

        textfSearchProductName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                String searchText = textfSearchProductName.getText().trim();
                updateProductList(billCreateController.getProductSearch(searchText));
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                String searchText = textfSearchProductName.getText().trim();
                if (searchText.isEmpty()) {
                    resetProductList();
                } else {
                    updateProductList(billCreateController.getProductSearch(searchText));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnSearchClear) {
            if (!textfSearchProductName.getText().isEmpty()) {
                this.billCreateController.requestClearProductSearch();
            }
        } else if (source == btnExport) {
            this.billCreateController.requestExportBill();
        } else if (source == btnSelect) {
            billCreateController.requestChooseOfferedProduct();
        } else if (source == btnRemove) {
            billCreateController.requestRemoveSelectedProduct();
        } else if (source == btnClear) {
            this.billCreateController.requestClearTableSelectedProduct();
        } else if (source == btnEditAmount) {
            this.billCreateController.requestEditSelectedProductAmount();
        }
    }

    public int getOfferedProductTableSelectedRowIndex() {
        return tableOfferedProduct.getSelectedRow();
    }

    public int getSelectedProductTableSelectedRowIndex() {
        return tableSelectedProduct.getSelectedRow();
    }

    public int getLeftAmountProductOffer(int rowID) {
        int leftAmount = (int) tableOfferedProductModel.getValueAt(rowID, PRODUCT_OFFER_LEFT_AMOUNT_COLUMN_INDEX);
        return leftAmount;
    }

    public String getIDProductOffer(int rowID) {
        String productID = (String) tableOfferedProduct.getValueAt(rowID, PRODUCT_OFFER_ID_COLUMN_INDEX);
        return productID;
    }

    private void resetProductList() {
        updateProductList(billCreateController.getAllProduct());
    }

    public void updateProductList(Iterator<ProductSimpleModelInterface> iterator) {
        clearOfferedProductTable();
        while (iterator.hasNext()) {
            addRowOfferedProductTable(iterator.next());
        }
    }

    public void updateOfferedProductInfo(int rowID, ProductSimpleModelInterface updatedOfferedProduct) {
        if (rowID < 0 || rowID >= tableOfferedProductModel.getRowCount()) {
            throw new IndexOutOfBoundsException();
        }
        tableOfferedProductModel.setValueAt(updatedOfferedProduct.getAmount(), rowID, PRODUCT_OFFER_LEFT_AMOUNT_COLUMN_INDEX);
    }

    @Override
    public void updateFromInsertedSelectedProduct(ProductDetailModelInterface productDetail) {
        Object[] record = new Object[]{
            productDetail.getProduct().getProductIDText(),
            productDetail.getProduct().getName(),
            productDetail.getProduct().getSize().toString(),
            productDetail.getAmount(),
            productDetail.getPrice()
        };
        tableSelectedProductModel.addRow(record);
    }

    @Override
    public void updateFromDeletedSelectedProduct(int rowID) {
        tableSelectedProductModel.removeRow(rowID);
    }

    public void setSearchProductText(String text) {
        this.textfSearchProductName.setText(text);
    }

    public void clearOfferedProductTable() {
        tableOfferedProductModel.setRowCount(0);
    }

    public void clearTableProductSelect() {
        tableSelectedProductModel.setRowCount(0);
    }

    public void addRowOfferedProductTable(ProductSimpleModelInterface product) {
        Object[] record = new Object[]{
            product.getProductIDText(),
            product.getName(),
            product.getSize(),
            product.getPrice(),
            product.getAmount()
        };
        tableOfferedProductModel.addRow(record);
    }

    public void removeRowTableSelect(int rowID) {
        tableSelectedProductModel.removeRow(rowID);
    }

    @Override
    public void updateFromNewBillCreated(BillModelInterface bill) {
        // New bill created, reset input
        resetProductList();
        clearTableProductSelect();
        textfBillID.setText(this.billManageModel.getNextBillIDText());
        textfTotalMoney.setValue(null);
    }

    @Override
    public void updateFromBillState() {
        long totalMoney = billManageModel.getTotalMoney();
        textfTotalMoney.setValue(totalMoney);
        if (billManageModel.isBillHavingNoProduct()) {
            clearTableProductSelect();
        }
    }

    @Override
    public void updateFromModifiedSelectedProduct(ProductDetailModelInterface modifiedSelectedProduct) {
        for (int i = 0; i < tableSelectedProductModel.getRowCount(); i++) {
            if (tableSelectedProductModel.getValueAt(i, PRODUCT_SELECT_ID_COLUMN_INDEX)
                    .equals(modifiedSelectedProduct.getProduct().getProductIDText())) {
                int updatedAmount = modifiedSelectedProduct.getAmount();
                tableSelectedProductModel.setValueAt(updatedAmount, i, PRODUCT_SELECT_AMOUNT_COLUMN_INDEX);
                long updatedPrice = modifiedSelectedProduct.getPrice();
                tableSelectedProductModel.setValueAt(updatedPrice, i, PRODUCT_SELECT_PRICE_COLUMN_INDEX);
            }
        }
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableOfferedProduct = new javax.swing.JTable() {

        };
        labelProductList = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        labelSearchProduct = new javax.swing.JLabel();
        textfSearchProductName = new javax.swing.JTextField();
        btnSearchClear = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnSelect = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        labelBillID = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableSelectedProduct = new javax.swing.JTable();
        labelTotalMoney = new javax.swing.JLabel();
        label_prodChoose = new javax.swing.JLabel();
        textfBillID = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnEditAmount = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        textfTotalMoney = new CurrencyTextField();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setName("BillCreate"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(600, 639));
        jPanel1.setName("BillCreate"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 639));

        tableOfferedProduct.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tableOfferedProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Size", "Unit price", "Left amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
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
        tableOfferedProduct.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tableOfferedProduct);

        labelProductList.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        labelProductList.setText("Product list");
        labelProductList.setPreferredSize(new java.awt.Dimension(92, 30));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 30));

        labelSearchProduct.setBackground(new java.awt.Color(255, 255, 255));
        labelSearchProduct.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelSearchProduct.setText("Search name:");
        labelSearchProduct.setOpaque(true);
        labelSearchProduct.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel3.add(labelSearchProduct);

        textfSearchProductName.setPreferredSize(new java.awt.Dimension(250, 30));
        jPanel3.add(textfSearchProductName);

        btnSearchClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSearchClear.setForeground(new java.awt.Color(51, 51, 51));
        btnSearchClear.setText("Clear");
        btnSearchClear.setFocusPainted(false);
        btnSearchClear.setPreferredSize(new java.awt.Dimension(90, 30));
        jPanel3.add(btnSearchClear);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        btnSelect.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSelect.setForeground(new java.awt.Color(51, 51, 51));
        btnSelect.setText("Select");
        btnSelect.setFocusPainted(false);
        btnSelect.setPreferredSize(new java.awt.Dimension(120, 30));
        jPanel5.add(btnSelect);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelProductList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(labelProductList, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(700, 639));
        jPanel2.setPreferredSize(new java.awt.Dimension(720, 639));

        labelBillID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelBillID.setText("Bill ID");

        tableSelectedProduct.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tableSelectedProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Size", "Amount", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
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
        tableSelectedProduct.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tableSelectedProduct);

        labelTotalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTotalMoney.setText("Total Money");

        label_prodChoose.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        label_prodChoose.setText("Chosen products");
        label_prodChoose.setPreferredSize(new java.awt.Dimension(133, 30));

        textfBillID.setEditable(false);
        textfBillID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));

        btnEditAmount.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnEditAmount.setForeground(new java.awt.Color(51, 51, 51));
        btnEditAmount.setText("Edit amount");
        btnEditAmount.setFocusPainted(false);
        btnEditAmount.setPreferredSize(new java.awt.Dimension(120, 30));
        jPanel4.add(btnEditAmount);

        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnClear.setForeground(new java.awt.Color(51, 51, 51));
        btnClear.setText("Clear");
        btnClear.setFocusPainted(false);
        btnClear.setPreferredSize(new java.awt.Dimension(120, 30));
        jPanel4.add(btnClear);

        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnRemove.setForeground(new java.awt.Color(51, 51, 51));
        btnRemove.setText("Remove");
        btnRemove.setFocusPainted(false);
        btnRemove.setPreferredSize(new java.awt.Dimension(120, 30));
        jPanel4.add(btnRemove);

        btnExport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnExport.setText("Export");
        btnExport.setPreferredSize(new java.awt.Dimension(100, 30));

        textfTotalMoney.setEditable(false);
        textfTotalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("VNĐ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(label_prodChoose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(labelBillID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labelTotalMoney)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(label_prodChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel2);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnEditAmount;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JButton btnSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel labelBillID;
    private javax.swing.JLabel labelProductList;
    private javax.swing.JLabel labelSearchProduct;
    private javax.swing.JLabel labelTotalMoney;
    private javax.swing.JLabel label_prodChoose;
    private javax.swing.JTable tableOfferedProduct;
    private javax.swing.JTable tableSelectedProduct;
    private javax.swing.JTextField textfBillID;
    private javax.swing.JTextField textfSearchProductName;
    private javax.swing.JFormattedTextField textfTotalMoney;
    // End of variables declaration//GEN-END:variables
}
