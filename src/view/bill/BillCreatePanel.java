package view.bill;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.bill.BillManageModelInterface;
import model.product.ProductModelInterface;
import org.apache.commons.lang3.tuple.Pair;
import util.swing.UIControl;
import view.MessageShowing;
import control.bill.create.BillCreateControllerInterface;
import javax.swing.JFrame;

public class BillCreatePanel extends javax.swing.JPanel implements ActionListener,
        MessageShowing {

    public static final int PRODUCT_OFFER_ID_COLUMN_INDEX = 0;
    public static final int PRODUCT_OFFER_LEFT_AMOUNT_COLUMN_INDEX = 4;

    public static final int PRODUCT_SELECT_ID_COLUMN_INDEX = 0;
    public static final int PRODUCT_SELECT_AMOUNT_COLUMN_INDEX = 3;
    public static final int PRODUCT_SELECT_PRICE_COLUMN_INDEX = 4;

    private JFrame mainFrame;

    private BillManageModelInterface billManageModel;
    private BillCreateControllerInterface billCreateController;

    private DefaultTableModel tableProductOfferModel;
    private DefaultTableModel tableProductSelectModel;

    public BillCreatePanel() {
        initComponents();
        tableProductOfferModel = (DefaultTableModel) tableProductOffer.getModel();
        tableProductSelectModel = (DefaultTableModel) tableProductSelect.getModel();
        createView();
        createControl();
    }

    public void setMaiFrame(JFrame maiFrame) {
        this.mainFrame = maiFrame;
    }

    public JFrame getMaiFrame() {
        return mainFrame;
    }

    public void setBillManageModel(BillManageModelInterface model) {
        if (model == null) {
            throw new NullPointerException("Bill model is null object.");
        }
        this.billManageModel = model;
        resetAll();
    }

    public void setBillCreateController(BillCreateControllerInterface controller) {
        if (controller == null) {
            throw new NullPointerException("Bill controller is null object.");
        }
        this.billCreateController = controller;
    }

    private void createView() {
        UIControl.setDefaultTableHeader(tableProductOffer);
        UIControl.setDefaultTableHeader(tableProductSelect);
    }

    private void createControl() {
        btnSearchClear.addActionListener(this);
        btnExportBill.addActionListener(this);
        btnSelect.addActionListener(this);
        btnRemove.addActionListener(this);
        btnClear.addActionListener(this);

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
                this.billCreateController.requestClearSearch();
            }
        } else if (source == btnExportBill) {
            if (tableProductSelectModel.getRowCount() == 0) {
                showInfoMessage("Bill has no product!");
            } else {
                this.billCreateController.requestExportBill();
            }
        } else if (source == btnSelect) {
            int rowID = tableProductOffer.getSelectedRow();
            if (rowID == -1) {
                showInfoMessage("You should choose one product first.");
            } else {
                this.billCreateController.requestChooseProduct(rowID);
            }
        } else if (source == btnRemove) {
            int rowID = tableProductSelect.getSelectedRow();
            if (rowID == -1) {
                showInfoMessage("You should choose one product first.");
            } else {
                this.billCreateController.requestRemoveSelectedProduct(rowID);
            }
        } else if (source == btnClear) {
            this.billCreateController.requestClearTableSelect();
        }
    }

    public int getLeftAmountProductOffer(int rowID) {
        int leftAmount = (int) tableProductOfferModel.getValueAt(rowID, PRODUCT_OFFER_LEFT_AMOUNT_COLUMN_INDEX);
        return leftAmount;
    }

    public String getIDProductOffer(int rowID) {
        String productID = (String) tableProductOffer.getValueAt(rowID, PRODUCT_OFFER_ID_COLUMN_INDEX);
        return productID;
    }

    private void resetProductList() {
        updateProductList(billCreateController.getRemainProduct());
    }

    public void updateProductList(List<Pair<ProductModelInterface, Integer>> products) {
        clearTableOffer();
        for (Pair<ProductModelInterface, Integer> element : products) {
            addRowTableOffer(element.getKey(), element.getValue());
        }
    }

    public void resetAll() {
        String newBillID = this.billCreateController.getNewBillID();
        textfBillID.setText(newBillID);

        resetProductList();

        textfTotalMoney.setText("0");
        clearTableSelect();
    }

    public void clearAll() {
        textfSearchProductName.setText(null);
        textfBillID.setText(null);
        textfTotalMoney.setText(null);
    }

    public void setBillID(String billIDText) {
        this.textfBillID.setText(billIDText);
    }

    public void setSearchProductText(String text) {
        this.textfSearchProductName.setText(text);
    }

    public void setTotalMoneyText(String text) {
        this.textfTotalMoney.setText(text);
    }

    public void setTableOfferRecords(List<String[]> records) {
        clearTableOffer();

        for (String[] record : records) {
            tableProductOfferModel.addRow(record);
        }
    }

    public void updateLeftAmountProductOffer(int rowID, int amount) {
        tableProductOfferModel.setValueAt(amount, rowID, PRODUCT_OFFER_LEFT_AMOUNT_COLUMN_INDEX);
        tableProductOffer.repaint();
    }

    public void updateAmountProductSelect(int rowID, int amount) {
        tableProductSelectModel.setValueAt(amount, rowID, PRODUCT_SELECT_AMOUNT_COLUMN_INDEX);
    }

    public void updatePriceProductSelect(int rowID, long price) {
        tableProductSelectModel.setValueAt(price, rowID, PRODUCT_SELECT_PRICE_COLUMN_INDEX);
    }

    public void clearTableOffer() {
        tableProductOfferModel.setRowCount(0);
    }

    public void clearTableSelect() {
        tableProductSelectModel.setRowCount(0);
    }

    public void addRowTableOffer(ProductModelInterface product, int productAmount) {
        Object[] record = new Object[]{
            product.getProductIDText(),
            product.getName(),
            product.getSize(),
            product.getPrice(),
            productAmount
        };
        tableProductOfferModel.addRow(record);
    }

    public void addRowTableSelect(ProductModelInterface product) {
        Object[] record = new Object[]{
            product.getProductIDText(),
            product.getName(),
            product.getSize(),
            1,
            product.getPrice()
        };
        tableProductSelectModel.addRow(record);
    }

    public int getRowIndexTableOffer(String productIDText) {
        for (int i = 0; i < tableProductOfferModel.getRowCount(); i++) {
            if (((String) tableProductOfferModel.getValueAt(i, PRODUCT_OFFER_ID_COLUMN_INDEX))
                    .equals(productIDText)) {
                return i;
            }
        }
        return -1;
    }

    public void removeRowTableSelect(int rowID) {
        tableProductSelectModel.removeRow(rowID);
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
        tableProductOffer = new javax.swing.JTable() {

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
        tableProductSelect = new javax.swing.JTable();
        labelTotalMoney = new javax.swing.JLabel();
        btnExportBill = new javax.swing.JButton();
        label_prodChoose = new javax.swing.JLabel();
        textfTotalMoney = new javax.swing.JTextField();
        textfBillID = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnClear = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(100, 0), new java.awt.Dimension(100, 0), new java.awt.Dimension(100, 32767));
        btnRemove = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setName("BillCreate"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(600, 639));
        jPanel1.setName("BillCreate"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 639));

        tableProductOffer.setModel(new javax.swing.table.DefaultTableModel(
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
        tableProductOffer.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tableProductOffer);

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
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, Short.MAX_VALUE)
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

        tableProductSelect.setModel(new javax.swing.table.DefaultTableModel(
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
        tableProductSelect.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tableProductSelect);

        labelTotalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTotalMoney.setText("Total Money");

        btnExportBill.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnExportBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_export.png"))); // NOI18N
        btnExportBill.setContentAreaFilled(false);
        btnExportBill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportBill.setFocusPainted(false);

        label_prodChoose.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        label_prodChoose.setText("Chosen products");
        label_prodChoose.setPreferredSize(new java.awt.Dimension(133, 30));

        textfTotalMoney.setEditable(false);
        textfTotalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfBillID.setEditable(false);
        textfBillID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnClear.setForeground(new java.awt.Color(51, 51, 51));
        btnClear.setText("Clear");
        btnClear.setFocusPainted(false);
        btnClear.setPreferredSize(new java.awt.Dimension(89, 29));
        jPanel4.add(btnClear);
        jPanel4.add(filler1);

        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnRemove.setForeground(new java.awt.Color(51, 51, 51));
        btnRemove.setText("Remove");
        btnRemove.setFocusPainted(false);
        jPanel4.add(btnRemove);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(labelBillID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelTotalMoney)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(label_prodChoose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExportBill, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(label_prodChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnExportBill, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel2);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnExportBill;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JButton btnSelect;
    private javax.swing.Box.Filler filler1;
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
    private javax.swing.JTable tableProductOffer;
    private javax.swing.JTable tableProductSelect;
    private javax.swing.JTextField textfBillID;
    private javax.swing.JTextField textfSearchProductName;
    private javax.swing.JTextField textfTotalMoney;
    // End of variables declaration//GEN-END:variables
}
