package view.function.bill;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.bill.BillManageModelInterface;
import model.product.ProductModelInterface;
import org.apache.commons.lang3.tuple.Pair;
import util.swing.UIControl;
import view.MessageShowing;
import view.main.MainFrame;
import control.bill.create.BillCreateControllerInterface;

public class BillCreatePanel extends javax.swing.JPanel implements ActionListener,
        MessageShowing {

    public static final int PRODUCT_OFFER_ID_COLUMN_INDEX = 0;
    public static final int PRODUCT_OFFER_LEFT_AMOUNT_COLUMN_INDEX = 4;

    public static final int PRODUCT_SELECT_ID_COLUMN_INDEX = 0;
    public static final int PRODUCT_SELECT_AMOUNT_COLUMN_INDEX = 3;
    public static final int PRODUCT_SELECT_PRICE_COLUMN_INDEX = 4;

    private volatile static BillCreatePanel uniqueInstance;

    private BillManageModelInterface model;
    private BillCreateControllerInterface controller;

    private DefaultTableModel tableOfferModel;
    private DefaultTableModel tableSelectModel;

    private BillCreatePanel(BillManageModelInterface model, BillCreateControllerInterface controller) {
        if (model == null) {
            throw new IllegalArgumentException("Bill model is null object.");
        }
        if (controller == null) {
            throw new IllegalArgumentException("Bill controller is null object.");
        }

        initComponents();

        this.model = model;
        this.controller = controller;

        tableOfferModel = (DefaultTableModel) tableOffer.getModel();
        tableSelectModel = (DefaultTableModel) tableSelect.getModel();

        createView();
        createControl();
    }

    public static BillCreatePanel getInstance(BillManageModelInterface model,
            BillCreateControllerInterface controller) {
        if (uniqueInstance == null) {
            synchronized (BillCreatePanel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new BillCreatePanel(model, controller);
                }
            }
        }
        return uniqueInstance;
    }

    public static BillCreatePanel getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

    private void createView() {
        UIControl.setDefaultTableHeader(tableOffer);
        UIControl.setDefaultTableHeader(tableSelect);

        resetAll();
    }

    private void createControl() {
        btnSearchClear.addActionListener(this);
        btnExportBill.addActionListener(this);
        btnChoose.addActionListener(this);
        btnRemove.addActionListener(this);
        btnClear.addActionListener(this);

        textfSearchProductName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                String searchText = textfSearchProductName.getText().trim();
                updateProductList(controller.getProductSearch(searchText));
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                String searchText = textfSearchProductName.getText().trim();
                if (searchText.isEmpty()) {
                    resetProductList();
                } else {
                    updateProductList(controller.getProductSearch(searchText));
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
                this.controller.requestClearSearch();
            }
        } else if (source == btnExportBill) {
            if (tableSelectModel.getRowCount() == 0) {
                showInfoMessage("Bill has no product!");
            } else {
                this.controller.requestExportBill();
            }
        } else if (source == btnChoose) {
            int rowID = tableOffer.getSelectedRow();
            if (rowID == -1) {
                showInfoMessage("You should choose one product first.");
            } else {
                this.controller.requestChooseProduct(rowID);
            }
        } else if (source == btnRemove) {
            int rowID = tableSelect.getSelectedRow();
            if (rowID == -1) {
                showInfoMessage("You should choose one product first.");
            } else {
                this.controller.requestRemoveSelectedProduct(rowID);
            }
        } else if (source == btnClear) {
            this.controller.requestClearTableSelect();
        }
    }

    public int getLeftAmountProductOffer(int rowID) {
        int leftAmount = (int) tableOfferModel.getValueAt(rowID, PRODUCT_OFFER_LEFT_AMOUNT_COLUMN_INDEX);
        return leftAmount;
    }

    public String getIDProductOffer(int rowID) {
        String productID = (String) tableOffer.getValueAt(rowID, PRODUCT_OFFER_ID_COLUMN_INDEX);
        return productID;
    }

    private void resetProductList() {
        updateProductList(controller.getRemainProduct());
    }

    public void updateProductList(List<Pair<ProductModelInterface, Integer>> products) {
        clearTableOffer();
        for (Pair<ProductModelInterface, Integer> element : products) {
            addRowTableOffer(element.getKey(), element.getValue());
        }
    }

    public void resetAll() {
        String newBillID = this.controller.getNewBillID();
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
            tableOfferModel.addRow(record);
        }
    }

    public void updateLeftAmountProductOffer(int rowID, int amount) {
        tableOfferModel.setValueAt(amount, rowID, PRODUCT_OFFER_LEFT_AMOUNT_COLUMN_INDEX);
        tableOffer.repaint();
    }

    public void updateAmountProductSelect(int rowID, int amount) {
        tableSelectModel.setValueAt(amount, rowID, PRODUCT_SELECT_AMOUNT_COLUMN_INDEX);
    }

    public void updatePriceProductSelect(int rowID, int price) {
        tableSelectModel.setValueAt(price, rowID, PRODUCT_SELECT_PRICE_COLUMN_INDEX);
    }

    public void clearTableOffer() {
        tableOfferModel.setRowCount(0);
    }

    public void clearTableSelect() {
        tableSelectModel.setRowCount(0);
    }

    public void addRowTableOffer(ProductModelInterface product, int productAmount) {
        Object[] record = new Object[]{
            product.getProductIDText(),
            product.getName(),
            product.getSize(),
            product.getPrice(),
            productAmount
        };
        tableOfferModel.addRow(record);
    }

    public void addRowTableSelect(ProductModelInterface product) {
        Object[] record = new Object[]{
            product.getProductIDText(),
            product.getName(),
            product.getSize(),
            1,
            product.getPrice()
        };
        tableSelectModel.addRow(record);
    }

    public int getRowIndexTableOffer(String productIDText) {
        for (int i = 0; i < tableOfferModel.getRowCount(); i++) {
            if (((String) tableOfferModel.getValueAt(i, PRODUCT_OFFER_ID_COLUMN_INDEX))
                    .equals(productIDText)) {
                return i;
            }
        }
        return -1;
    }

    public void removeRowTableSelect(int rowID) {
        tableSelectModel.removeRow(rowID);
    }

    @Override
    public void showErrorMessage(String message) {
        MainFrame.getInstance().showErrorMessage(message);
    }

    @Override
    public void showInfoMessage(String message) {
        MainFrame.getInstance().showInfoMessage(message);
    }

    @Override
    public void showWarningMessage(String message) {
        MainFrame.getInstance().showWarningMessage(message);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_prodChoose = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableSelect = new javax.swing.JTable();
        btnChoose = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableOffer = new javax.swing.JTable() {

        };
        panelBtnProductSelect = new javax.swing.JPanel();
        btnRemove = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        textfSearchProductName = new javax.swing.JTextField();
        label_prodName = new javax.swing.JLabel();
        btnSearchClear = new javax.swing.JButton();
        btnExportBill = new javax.swing.JButton();
        textfBillID = new javax.swing.JTextField();
        label_billID = new javax.swing.JLabel();
        label_totalMoney = new javax.swing.JLabel();
        textfTotalMoney = new javax.swing.JTextField();
        label_prodChoose2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        label_prodChoose.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        label_prodChoose.setText("Chosen products");

        tableSelect.setModel(new javax.swing.table.DefaultTableModel(
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
        tableSelect.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tableSelect);

        btnChoose.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnChoose.setForeground(new java.awt.Color(51, 51, 51));
        btnChoose.setText("Choose");
        btnChoose.setFocusPainted(false);

        tableOffer.setModel(new javax.swing.table.DefaultTableModel(
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
        tableOffer.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tableOffer);

        panelBtnProductSelect.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnProductSelect.setLayout(new java.awt.GridLayout(1, 0, 35, 0));

        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnRemove.setForeground(new java.awt.Color(51, 51, 51));
        btnRemove.setText("Remove");
        btnRemove.setFocusPainted(false);
        panelBtnProductSelect.add(btnRemove);

        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnClear.setForeground(new java.awt.Color(51, 51, 51));
        btnClear.setText("Clear");
        btnClear.setFocusPainted(false);
        btnClear.setPreferredSize(new java.awt.Dimension(89, 29));
        panelBtnProductSelect.add(btnClear);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        textfSearchProductName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfSearchProductName.setPreferredSize(new java.awt.Dimension(160, 30));

        label_prodName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_prodName.setText("Product Name");

        btnSearchClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSearchClear.setForeground(new java.awt.Color(51, 51, 51));
        btnSearchClear.setText("Clear");
        btnSearchClear.setFocusPainted(false);
        btnSearchClear.setPreferredSize(new java.awt.Dimension(89, 29));

        btnExportBill.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnExportBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_export.png"))); // NOI18N
        btnExportBill.setContentAreaFilled(false);
        btnExportBill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportBill.setFocusPainted(false);

        textfBillID.setEditable(false);
        textfBillID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        label_billID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_billID.setText("Bill ID");

        label_totalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_totalMoney.setText("Total Money");

        textfTotalMoney.setEditable(false);
        textfTotalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        label_prodChoose2.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        label_prodChoose2.setText("Product list");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBtnProductSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_prodName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textfSearchProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(label_prodChoose2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_prodChoose)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_billID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(label_totalMoney)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnExportBill, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_prodChoose)
                    .addComponent(label_prodChoose2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textfSearchProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_prodName)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnExportBill, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(label_totalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_billID, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1118, Short.MAX_VALUE)
                    .addComponent(jScrollPane8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBtnProductSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChoose;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnExportBill;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel label_billID;
    private javax.swing.JLabel label_prodChoose;
    private javax.swing.JLabel label_prodChoose2;
    private javax.swing.JLabel label_prodName;
    private javax.swing.JLabel label_totalMoney;
    private javax.swing.JPanel panelBtnProductSelect;
    private javax.swing.JTable tableOffer;
    private javax.swing.JTable tableSelect;
    private javax.swing.JTextField textfBillID;
    private javax.swing.JTextField textfSearchProductName;
    private javax.swing.JTextField textfTotalMoney;
    // End of variables declaration//GEN-END:variables
}
