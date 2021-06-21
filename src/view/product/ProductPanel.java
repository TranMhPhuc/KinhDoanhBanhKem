package view.product;

import control.product.ProductControllerInterface;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.product.ProductManageModelInterface;
import model.product.ProductModelInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import util.messages.Messages;
import util.swing.UIControl;
import view.MessageShowing;

public class ProductPanel extends javax.swing.JPanel implements ActionListener,
        MessageShowing, InsertedProductObserver, ModifiedProductObserver,
        RemovedProductObserver, SettingUpdateObserver {

    public static final int PRODUCT_ID_COLUMN_INDEX = 0;
    public static final int PRODUCT_NAME_COLUMN_INDEX = 1;
    public static final int PRODUCT_SIZE_COLUMN_INDEX = 2;
    public static final int PRODUCT_COST_COLUMN_INDEX = 3;
    public static final int PRODUCT_PRICE_COLUMN_INDEX = 4;
    public static final int PRODUCT_AMOUNT_COLUMN_INDEX = 5;
    
    public static final int INGREDIENT_DETAIL_NAME_COLUMN_INDEX = 0;
    public static final int INGREDIENT_DETAIL_TYPE_COLUMN_INDEX = 1;
    public static final int INGREDIENT_DETAIL_UNIT_COLUMN_INDEX = 2;
    public static final int INGREDIENT_DETAIL_AMOUNT_COLUMN_INDEX = 3;

    public enum EditState {
        ADD,
        MODIFY,
    }

    private JFrame mainFrame;

    private ProductManageModelInterface productManageModel;
    private ProductControllerInterface productController;

    private DefaultTableModel tableProductModel;
    private DefaultTableModel tableIngredientDetailModel;

    private EditState editState;

    public ProductPanel() {
        initComponents();
        this.tableProductModel = (DefaultTableModel) tableProduct.getModel();
        this.tableIngredientDetailModel = (DefaultTableModel) tableIngredientDetail.getModel();
        this.editState = null;
        createView();
        createControl();
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JFrame getMainFrame() {
        return this.mainFrame;
    }

    public void setProductManageModel(ProductManageModelInterface productManageModel) {
        if (productManageModel == null) {
            throw new NullPointerException("Product model instance is null.");
        }
        this.productManageModel = productManageModel;
        this.productManageModel.registerInsertedProductObserver(this);
        this.productManageModel.registerModifiedProductObserver(this);
        this.productManageModel.registerRemovedProductObserver(this);
    }

    public void setProductController(ProductControllerInterface controller) {
        if (controller == null) {
            throw new NullPointerException("Product controller instance is null.");
        }
        this.productController = controller;
        resetProductList();
    }

    private void createView() {
        textfProductID.setEditable(false);
        resetProductInput();
        setProductInputEditable(false);
        UIControl.setDefaultTableHeader(tableProduct);
    }

    private void createControl() {
        btnEditIngredient.addActionListener(this);
        btnSearchClear.addActionListener(this);
        btnAdd.addActionListener(this);
        btnModify.addActionListener(this);
        btnRemove.addActionListener(this);
        btnExport.addActionListener(this);
        btnRequestProduce.addActionListener(this);
        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);
        btnReset.addActionListener(this);

        tableProduct.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                productController.requestShowProductInfo();
            }
        });

        textfSearchName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                String searchText = textfSearchName.getText().trim();
                showProductList(productController.getProductBySearchName(searchText));
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                String searchText = textfSearchName.getText().trim();
                if (searchText.isEmpty()) {
                    resetProductList();
                } else {
                    showProductList(productController.getProductBySearchName(searchText));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public void showProductInfo(ProductModelInterface product) {
        this.textfProductID.setText(product.getProductIDText());
        this.textfProductName.setText(product.getName());
        this.textfProductCost.setText(String.valueOf(product.getCost()));
        this.textfProductPrice.setText(String.valueOf(product.getPrice()));

        String productSize = product.getSize().name();
        for (int i = 0; i < this.combProductSize.getItemCount(); i++) {
            if (this.combProductSize.getItemAt(i).equals(productSize)) {
                this.combProductSize.setSelectedIndex(i);
                break;
            }
        }

        showIngredientDetailList();
    }

    public void showIngredientDetailList() {
        this.tableIngredientDetailModel.setRowCount(0);

        productManageModel.getBufferedIngredientDetailList().forEach(ingredientDetail -> {
            Object[] record = new Object[]{
                ingredientDetail.getIngredientName(),
                ingredientDetail.getIngredientTypeName(),
                ingredientDetail.getUnitName(),
                ingredientDetail.getAmount()
            };
            this.tableIngredientDetailModel.addRow(record);
        });
    }

    public void resetProductList() {
        if (this.productController == null) {
            return;
        }

        Iterator<ProductModelInterface> iterator = this.productController.getAllProductData();
        showProductList(iterator);
    }

    private void showProductList(Iterator<ProductModelInterface> iterator) {
        clearTableProduct();
        while (iterator.hasNext()) {
            addRowTableProduct(iterator.next());
        }
    }

    public void clearTableProduct() {
        this.tableProductModel.setRowCount(0);
    }

    private void addRowTableProduct(ProductModelInterface product) {
        Object[] record = new Object[]{
            product.getProductIDText(),
            product.getName(),
            product.getSize(),
            product.getCost(),
            product.getPrice(),
            product.getAmount()
        };
        this.tableProductModel.addRow(record);
    }

    private void updateRowTableProduct(ProductModelInterface product) {
        for (int i = 0; i < this.tableProductModel.getRowCount(); i++) {
            String productIDInTable = (String) this.tableProductModel.getValueAt(i, PRODUCT_ID_COLUMN_INDEX);
            if (productIDInTable.equals(product.getProductIDText())) {
                Object[] record = new Object[]{
                    product.getProductIDText(),
                    product.getName(),
                    product.getSize(),
                    product.getCost(),
                    product.getPrice(),
                    product.getAmount()
                };
                for (int j = 0; j < record.length; j++) {
                    this.tableProductModel.setValueAt(record[j], i, j);
                }
                break;
            }
        }
    }

    private void removeRowTableProduct(ProductModelInterface product) {
        if (this.productController.deleteProductInSearchList(product)) {
            String productIDText = product.getProductIDText();
            for (int i = 0; i < this.tableProductModel.getRowCount(); i++) {
                String productIDInTable = (String) this.tableProductModel.getValueAt(i, PRODUCT_ID_COLUMN_INDEX);
                if (productIDInTable.equals(productIDText)) {
                    this.tableProductModel.removeRow(i);
                    break;
                }
            }
        }
    }

    public void setTextfSearch(String text) {
        this.textfSearchName.setText(text);
    }

    public String getTextSearch() {
        return this.textfSearchName.getText().trim();
    }

    public int getSelectedRow() {
        return this.tableProduct.getSelectedRow();
    }

    public void setProductInputEditable(boolean editable) {
        textfProductCost.setEditable(editable);
        textfProductPrice.setEditable(editable);
        textfProductName.setEditable(editable);
        combProductSize.setEnabled(editable);
        btnEditIngredient.setEnabled(editable);
    }

    public void resetProductInput() {
        textfProductCost.setText("");
        textfProductPrice.setText("");
        textfProductName.setText("");
        combProductSize.setSelectedIndex(0);
        tableIngredientDetailModel.setRowCount(0);
        if (productManageModel != null) {
            productManageModel.getBufferedIngredientDetailList().clear();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnEditIngredient) {
            this.productController.requestEditIngredient();
        } else if (source == btnSearchClear) {
            if (!textfSearchName.getText().trim().isEmpty()) {
                setTextfSearch("");
                resetProductList();
                resetProductInput();
            }
        } else if (source == btnAdd) {
            resetProductInput();
            setProductInputEditable(true);
            btnRequestProduce.setEnabled(false);

            if (btnAdd.getText().equals("Add")) {
                btnOK.setText("Add");
            } else {
                btnOK.setText("Thêm");
            }

            showCardOption();
            String nextProductIDText = this.productManageModel.getNextProductIDText();
            this.textfProductID.setText(nextProductIDText);
            this.editState = EditState.ADD;
        } else if (source == btnModify) {
            String productIDText = textfProductID.getText();
            if (productIDText.isEmpty()) {
                showInfoMessage(Messages.getInstance().PRODUCT_NO_PRODUCT_CHOSEN);
            } else {
                setProductInputEditable(true);
                btnRequestProduce.setEnabled(false);
                if (btnModify.getText().equals("Modify")) {
                    btnOK.setText("Save");
                } else {
                    btnOK.setText("Lưu");
                }
                showCardOption();
                this.editState = EditState.MODIFY;
            }
        } else if (source == btnRemove) {
            String productIDText = textfProductID.getText();
            if (productIDText.isEmpty()) {
                showInfoMessage(Messages.getInstance().PRODUCT_NO_PRODUCT_CHOSEN);
            } else {
                int ret = JOptionPane.showConfirmDialog(mainFrame,
                        Messages.getInstance().OTHERS_REMOVE_PRODUCT, "BakeryMS", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/warning.png")));
                if (ret == JOptionPane.YES_OPTION) {
                    this.productController.requestRemoveProduct();
                }
            }
        } else if (source == btnExport) {
            this.productController.requestExportExcel();
        } else if (source == btnRequestProduce) {
            this.productController.requestProduceProduct();
        } else if (source == btnOK) {
            switch (editState) {
                case ADD: {
                    this.productController.requestCreateProduct();
                    break;
                }
                case MODIFY: {
                    this.productController.requestUpdateProduct();
                    break;
                }
            }
        } else if (source == btnCancel) {
            exitEditState();
        } else if (source == btnReset) {
            resetProductInput();
        }
    }

    public EditState getEditState() {
        return this.editState;
    }

    public void exitEditState() {
        if (this.editState == EditState.ADD) {
            this.textfProductID.setText("");
            resetProductInput();
        }
        this.editState = null;
        setProductInputEditable(false);
        btnRequestProduce.setEnabled(true);
        showCardFunction();
        productController.requestShowProductInfo();
    }

    public int getTableProductRowCount() {
        return tableProductModel.getRowCount();
    }

    public JTable getTableProduct() {
        return this.tableProduct;
    }

    public String getProductIDText() {
        return this.textfProductID.getText();
    }

    public String getProductName() {
        return this.textfProductName.getText().trim();
    }

    public String getProductSize() {
        return (String) this.combProductSize.getSelectedItem();
    }

    public String getProductCost() {
        return this.textfProductCost.getText().trim();
    }

    public String getProductPrice() {
        return this.textfProductPrice.getText().trim();
    }

    public void showCardOption() {
        CardLayout cardLayout = (CardLayout) panelCard.getLayout();
        cardLayout.show(panelCard, panelBtnOption.getName());
    }

    public void showCardFunction() {
        CardLayout cardLayout = (CardLayout) panelCard.getLayout();
        cardLayout.show(panelCard, panelBtnFunction.getName());
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

    @Override
    public void updateInsertedProductObserver(ProductModelInterface insertedProduct) {
        String searchText = textfSearchName.getText().trim();
        if (this.productController.isSearchMatching(searchText, insertedProduct)) {
            if (searchText.isEmpty()) {
                addRowTableProduct(insertedProduct);
            } else {
                showProductList(productController.getProductBySearchName(searchText));
            }
        }
        this.tableProduct.repaint();
    }

    @Override
    public void updateModifiedProductObserver(ProductModelInterface updatedProduct) {
        updateRowTableProduct(updatedProduct);
    }

    @Override
    public void updateRemovedProductObserver(ProductModelInterface removedProduct) {
        removeRowTableProduct(removedProduct);
    }

    @Override
    public void updateSettingObserver() {
        AppSetting.Language language = AppSetting.getInstance().getAppLanguage();

        switch (language) {
            case ENGLISH: {
                labelName.setText("Name:");
                labelSize.setText("Size:");
                labelCost.setText("Cost:");
                labelPrice.setText("Price:");
                labelSearchProduct.setText("Search name:");
                btnSearchClear.setText("Clear");
                btnAdd.setText("Add");
                btnModify.setText("Modify");
                btnRemove.setText("Remove");
                btnExport.setText("Export");
                btnReset.setText("Reset");
                btnCancel.setText("Cancel");
                btnRequestProduce.setText("Produce product");
                btnEditIngredient.setText("Edit");
                
                TableColumnModel tableProductColumnModel = tableProduct.getColumnModel();
                tableProductColumnModel.getColumn(PRODUCT_NAME_COLUMN_INDEX).setHeaderValue("Name");
                tableProductColumnModel.getColumn(PRODUCT_SIZE_COLUMN_INDEX).setHeaderValue("Size");
                tableProductColumnModel.getColumn(PRODUCT_COST_COLUMN_INDEX).setHeaderValue("Cost");
                tableProductColumnModel.getColumn(PRODUCT_PRICE_COLUMN_INDEX).setHeaderValue("Price");
                tableProductColumnModel.getColumn(PRODUCT_AMOUNT_COLUMN_INDEX).setHeaderValue("Amount");
                
                TableColumnModel tableIngredientDetailColumnModel = tableIngredientDetail.getColumnModel();
                tableIngredientDetailColumnModel.getColumn(INGREDIENT_DETAIL_NAME_COLUMN_INDEX)
                        .setHeaderValue("Name");
                tableIngredientDetailColumnModel.getColumn(INGREDIENT_DETAIL_TYPE_COLUMN_INDEX)
                        .setHeaderValue("Type");
                tableIngredientDetailColumnModel.getColumn(INGREDIENT_DETAIL_UNIT_COLUMN_INDEX)
                        .setHeaderValue("Unit");
                tableIngredientDetailColumnModel.getColumn(INGREDIENT_DETAIL_AMOUNT_COLUMN_INDEX)
                        .setHeaderValue("Amount");
                
                break;
            }
            case VIETNAMESE: {
                labelName.setText("Tên:");
                labelSize.setText("Kích thước:");
                labelCost.setText("Giá gốc:");
                labelPrice.setText("Giá bán:");
                labelSearchProduct.setText("Tìm theo tên:");
                btnSearchClear.setText("Xóa");
                btnAdd.setText("Thêm");
                btnModify.setText("Chỉnh sửa");
                btnRemove.setText("Xóa");
                btnExport.setText("Xuất");
                btnReset.setText("Làm mới");
                btnCancel.setText("Thoát");
                btnRequestProduce.setText("Tạo sản phẩm");
                btnEditIngredient.setText("Chỉnh sửa");
                
                TableColumnModel tableProductColumnModel = tableProduct.getColumnModel();
                tableProductColumnModel.getColumn(PRODUCT_NAME_COLUMN_INDEX).setHeaderValue("Tên");
                tableProductColumnModel.getColumn(PRODUCT_SIZE_COLUMN_INDEX).setHeaderValue("Kích thước");
                tableProductColumnModel.getColumn(PRODUCT_COST_COLUMN_INDEX).setHeaderValue("Giá gốc");
                tableProductColumnModel.getColumn(PRODUCT_PRICE_COLUMN_INDEX).setHeaderValue("Giá bán");
                tableProductColumnModel.getColumn(PRODUCT_AMOUNT_COLUMN_INDEX).setHeaderValue("Số lượng");
                
                TableColumnModel tableIngredientDetailColumnModel = tableIngredientDetail.getColumnModel();
                tableIngredientDetailColumnModel.getColumn(INGREDIENT_DETAIL_NAME_COLUMN_INDEX)
                        .setHeaderValue("Tên nguyên liệu");
                tableIngredientDetailColumnModel.getColumn(INGREDIENT_DETAIL_TYPE_COLUMN_INDEX)
                        .setHeaderValue("Loại");
                tableIngredientDetailColumnModel.getColumn(INGREDIENT_DETAIL_UNIT_COLUMN_INDEX)
                        .setHeaderValue("Đơn vị");
                tableIngredientDetailColumnModel.getColumn(INGREDIENT_DETAIL_AMOUNT_COLUMN_INDEX)
                        .setHeaderValue("Số lượng");
                break;
            }
        }

        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelProductInfo = new javax.swing.JPanel();
        label_productID = new javax.swing.JLabel();
        labelSize = new javax.swing.JLabel();
        textfProductID = new javax.swing.JTextField();
        combProductSize = new javax.swing.JComboBox<>();
        labelName = new javax.swing.JLabel();
        labelCost = new javax.swing.JLabel();
        textfProductName = new javax.swing.JTextField();
        textfProductCost = new javax.swing.JTextField();
        textfProductPrice = new javax.swing.JTextField();
        labelPrice = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        scrpaneIngredientSelected = new javax.swing.JScrollPane();
        tableIngredientDetail = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnEditIngredient = new javax.swing.JButton();
        scrpaneTable = new javax.swing.JScrollPane();
        tableProduct = new javax.swing.JTable();
        panelCard = new javax.swing.JPanel();
        panelBtnFunction = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        panelBtnOption = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        labelSearchProduct = new javax.swing.JLabel();
        textfSearchName = new javax.swing.JTextField();
        btnSearchClear = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnRequestProduce = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setName("Product"); // NOI18N

        panelProductInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelProductInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        label_productID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_productID.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_productID.setText(" ID");
        label_productID.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        labelSize.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelSize.setText("Size");

        textfProductID.setEditable(false);
        textfProductID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfProductID.setPreferredSize(new java.awt.Dimension(160, 30));

        combProductSize.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combProductSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S", "M", "L" }));
        combProductSize.setPreferredSize(new java.awt.Dimension(160, 30));

        labelName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelName.setText(" Name");

        labelCost.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelCost.setText("Cost");

        textfProductName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfProductCost.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfProductPrice.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        labelPrice.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelPrice.setText("Price");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingredient List", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(2147483647, 188));
        jPanel1.setMinimumSize(new java.awt.Dimension(549, 188));

        scrpaneIngredientSelected.setPreferredSize(new java.awt.Dimension(452, 350));

        tableIngredientDetail.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tableIngredientDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Type", "Unit", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        tableIngredientDetail.setIntercellSpacing(new java.awt.Dimension(3, 3));
        tableIngredientDetail.setRowHeight(20);
        tableIngredientDetail.setSelectionBackground(new java.awt.Color(113, 168, 255));
        tableIngredientDetail.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableIngredientDetail.getTableHeader().setReorderingAllowed(false);
        scrpaneIngredientSelected.setViewportView(tableIngredientDetail);

        jPanel2.setPreferredSize(new java.awt.Dimension(463, 40));

        btnEditIngredient.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnEditIngredient.setText("Edit");
        btnEditIngredient.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel2.add(btnEditIngredient);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrpaneIngredientSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(scrpaneIngredientSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panelProductInfoLayout = new javax.swing.GroupLayout(panelProductInfo);
        panelProductInfo.setLayout(panelProductInfoLayout);
        panelProductInfoLayout.setHorizontalGroup(
            panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductInfoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelName)
                    .addComponent(label_productID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelProductInfoLayout.createSequentialGroup()
                        .addComponent(textfProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labelSize)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combProductSize, 0, 1, Short.MAX_VALUE))
                    .addComponent(textfProductName, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCost, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelPrice, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textfProductCost, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelProductInfoLayout.setVerticalGroup(
            panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelProductInfoLayout.createSequentialGroup()
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_productID)
                            .addComponent(textfProductID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSize, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combProductSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCost, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textfProductCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelName)
                            .addComponent(textfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textfProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        tableProduct.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Size", "Cost", "Price", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.Long.class, java.lang.Integer.class
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
        tableProduct.setIntercellSpacing(new java.awt.Dimension(3, 3));
        tableProduct.setRowHeight(25);
        tableProduct.setSelectionBackground(new java.awt.Color(113, 168, 255));
        tableProduct.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableProduct.getTableHeader().setReorderingAllowed(false);
        scrpaneTable.setViewportView(tableProduct);
        tableProduct.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

        //table_proInfo.getTableHeader().setFont(new Font("Segoe UI",1,15));
        //DefaultTableCellRenderer DTCR2 = (DefaultTableCellRenderer) tableProduct.getTableHeader().getDefaultRenderer();
        //DTCR2.setHorizontalAlignment(0);
        //
        //
        //table_proInfo.setRowHeight(30);
        //DefaultTableCellRenderer center2 = new DefaultTableCellRenderer();
        //center2.setHorizontalAlignment(JLabel.CENTER);
        //table_proInfo.getColumnModel().getColumn(0).setCellRenderer(center);
        tableProduct.getAccessibleContext().setAccessibleDescription("");

        panelCard.setLayout(new java.awt.CardLayout());

        panelBtnFunction.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnFunction.setName("Function"); // NOI18N
        panelBtnFunction.setPreferredSize(new java.awt.Dimension(530, 30));
        panelBtnFunction.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(51, 51, 51));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Add_30px.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setFocusPainted(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(120, 30));
        panelBtnFunction.add(btnAdd);

        btnModify.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnModify.setForeground(new java.awt.Color(51, 51, 51));
        btnModify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Edit_30px.png"))); // NOI18N
        btnModify.setText("Modify");
        btnModify.setFocusPainted(false);
        btnModify.setPreferredSize(new java.awt.Dimension(120, 30));
        panelBtnFunction.add(btnModify);

        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnRemove.setForeground(new java.awt.Color(51, 51, 51));
        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Trash_30px.png"))); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.setFocusPainted(false);
        btnRemove.setPreferredSize(new java.awt.Dimension(120, 30));
        panelBtnFunction.add(btnRemove);

        btnExport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnExport.setForeground(new java.awt.Color(51, 51, 51));
        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Excel_30px.png"))); // NOI18N
        btnExport.setText("Export");
        btnExport.setFocusPainted(false);
        btnExport.setPreferredSize(new java.awt.Dimension(120, 30));
        panelBtnFunction.add(btnExport);

        panelCard.add(panelBtnFunction, "Function");

        panelBtnOption.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnOption.setName("Option"); // NOI18N
        panelBtnOption.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnOK.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnOK.setForeground(new java.awt.Color(51, 51, 51));
        btnOK.setText("OK");
        btnOK.setFocusPainted(false);
        btnOK.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnOption.add(btnOK);

        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(51, 51, 51));
        btnCancel.setText("Cancel");
        btnCancel.setFocusPainted(false);
        btnCancel.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnOption.add(btnCancel);

        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnReset.setForeground(new java.awt.Color(51, 51, 51));
        btnReset.setText("Reset");
        btnReset.setFocusPainted(false);
        btnReset.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnOption.add(btnReset);

        panelCard.add(panelBtnOption, "Option");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        labelSearchProduct.setBackground(new java.awt.Color(255, 255, 255));
        labelSearchProduct.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelSearchProduct.setText("Search name:");
        labelSearchProduct.setOpaque(true);
        labelSearchProduct.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel3.add(labelSearchProduct);

        textfSearchName.setPreferredSize(new java.awt.Dimension(250, 30));
        jPanel3.add(textfSearchName);

        btnSearchClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSearchClear.setForeground(new java.awt.Color(51, 51, 51));
        btnSearchClear.setText("Clear");
        btnSearchClear.setFocusPainted(false);
        btnSearchClear.setPreferredSize(new java.awt.Dimension(90, 30));
        jPanel3.add(btnSearchClear);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnRequestProduce.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnRequestProduce.setForeground(new java.awt.Color(51, 51, 51));
        btnRequestProduce.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Cooking_30px.png"))); // NOI18N
        btnRequestProduce.setText("Produce product");
        btnRequestProduce.setFocusPainted(false);
        btnRequestProduce.setIconTextGap(10);
        btnRequestProduce.setPreferredSize(new java.awt.Dimension(300, 50));
        jPanel4.add(btnRequestProduce);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelProductInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrpaneTable)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProductInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrpaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEditIngredient;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRequestProduce;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JComboBox<String> combProductSize;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel labelCost;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelPrice;
    private javax.swing.JLabel labelSearchProduct;
    private javax.swing.JLabel labelSize;
    private javax.swing.JLabel label_productID;
    private javax.swing.JPanel panelBtnFunction;
    private javax.swing.JPanel panelBtnOption;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel panelProductInfo;
    private javax.swing.JScrollPane scrpaneIngredientSelected;
    private javax.swing.JScrollPane scrpaneTable;
    private javax.swing.JTable tableIngredientDetail;
    private javax.swing.JTable tableProduct;
    private javax.swing.JTextField textfProductCost;
    private javax.swing.JTextField textfProductID;
    private javax.swing.JTextField textfProductName;
    private javax.swing.JTextField textfProductPrice;
    private javax.swing.JTextField textfSearchName;
    // End of variables declaration//GEN-END:variables
}
