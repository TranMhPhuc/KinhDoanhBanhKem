package view.function.product;

import control.product.ProductControllerInterface;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.ingredient.IngredientManageModel;
import model.ingredient.IngredientManageModelInterface;
import model.ingredient.IngredientModelInterface;
import model.ingredientOfProduct.IngredientDetailOfProductInterface;
import model.product.ProductManageModelInterface;
import model.product.ProductModelInterface;
import util.swing.UIControl;
import view.MessageShowing;
import view.function.ingredient.InsertedIngredientObserver;
import view.function.ingredient.ModifiedIngredientObserver;
import view.function.ingredient.RemovedIngredientObserver;
import view.main.MainFrame;

public class ProductPanel extends javax.swing.JPanel implements ActionListener,
        MessageShowing, InsertedProductObserver, ModifiedProductObserver, RemovedProductObserver,
        InsertedIngredientObserver, ModifiedIngredientObserver, RemovedIngredientObserver {

    public static final int PRODUCT_ID_COLUMN_INDEX = 0;
    public static final int PRODUCT_AMOUNT_COLUMN_INDEX = 4;

    enum EditState {
        ADD,
        MODIFY,
    }

    private volatile static ProductPanel uniqueInstance;

    private ProductManageModelInterface model;
    private ProductControllerInterface controller;

    private DefaultTableModel tableProductModel;
    private DefaultListModel listIngredientModel;

    private EditState editState;

    private ProductPanel(ProductManageModelInterface model,
            ProductControllerInterface controller) {
        if (model == null) {
            throw new IllegalArgumentException("Product model is null object.");
        }
        if (controller == null) {
            throw new IllegalArgumentException("Product controller is null object.");
        }

        initComponents();

        this.model = model;
        this.controller = controller;

        IngredientManageModelInterface ingredientManageModel = IngredientManageModel.getInstance();
        ingredientManageModel.registerInsertedIngredientObserver(this);
        ingredientManageModel.registerModifiedIngredientObserver(this);
        ingredientManageModel.registerRemovedIngredientObserver(this);

        this.tableProductModel = (DefaultTableModel) tableProduct.getModel();
        this.listIngredientModel = (DefaultListModel) listIngredient.getModel();

        this.editState = null;

        createView();
        createControl();
    }

    public static ProductPanel getInstance(ProductManageModelInterface model,
            ProductControllerInterface controller) {
        if (uniqueInstance == null) {
            synchronized (ProductPanel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ProductPanel(model, controller);
                }
            }
        }
        return uniqueInstance;
    }

    public static ProductPanel getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

    private void createView() {
        textfProductID.setEditable(false);
        resetProductInput();
        setProductInputEditable(false);
        UIControl.setDefaultTableHeader(tableProduct);
        resetProductList();
    }

    private void createControl() {
        btnEditIngredient.addActionListener(this);
        btnSearchClear.addActionListener(this);
        btnAdd.addActionListener(this);
        btnModify.addActionListener(this);
        btnRemove.addActionListener(this);
        btnMore.addActionListener(this);
        mnImport.addActionListener(this);
        mnExport.addActionListener(this);
        btnRequestProduce.addActionListener(this);
        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);
        btnReset.addActionListener(this);

        tableProduct.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.requestShowProductInfo();
            }
        });
        
        textfSearchName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                String searchText = textfSearchName.getText().trim();
                showProductList(controller.getProductBySearchName(searchText));
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                String searchText = textfSearchName.getText().trim();
                if (searchText.isEmpty()) {
                    resetProductList();
                } else {
                    showProductList(controller.getProductBySearchName(searchText));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public void showProductInfo(ProductModelInterface product) {
        if (editState == EditState.ADD) {
            return;
        }

        this.textfProductID.setText(product.getProductIDText());
        this.textfProductName.setText(product.getName());
        this.textfProductCost.setText(String.valueOf(product.getCost()));
        this.textfProductPrice.setText(String.valueOf(product.getPrice()));

        String productSize = product.getSize();
        for (int i = 0; i < this.combProductSize.getItemCount(); i++) {
            if (this.combProductSize.getItemAt(i).equals(productSize)) {
                this.combProductSize.setSelectedIndex(i);
                break;
            }
        }

        this.spinnerProductAmount.setValue(product.getAmount());

        showIngredientDetailList(product.getAllIngredient());
    }

    private void showIngredientDetailList(Iterator<IngredientDetailOfProductInterface> iterator) {
        this.listIngredientModel.clear();
        while (iterator.hasNext()) {
            IngredientDetailOfProductInterface ingredientDetail = iterator.next();
            this.listIngredientModel.addElement(ingredientDetail.getIngredient().getName());
        }
    }

    public void resetProductList() {
        Iterator<ProductModelInterface> iterator = this.controller.getAllProductData();
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
        if (this.controller.deleteProductInSearchList(product)) {
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
        spinnerProductAmount.setEnabled(editable);
        listIngredient.setEnabled(editable);
    }

    public void resetProductInput() {
        textfProductCost.setText("");
        textfProductPrice.setText("");
        textfProductName.setText("");
        combProductSize.setSelectedIndex(0);

        // ???
        listIngredient.clearSelection();

        spinnerProductAmount.setValue((int) 0);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnEditIngredient) {
            this.controller.requestEditIngredientOfProduct();
        } else if (source == btnSearchClear) {
            if (!textfSearchName.getText().trim().isEmpty()) {
                setTextfSearch("");
                resetProductList();
                resetProductInput();
            }
        } else if (source == btnAdd) {
            resetProductInput();
            setProductInputEditable(true);
            btnOK.setText("Add");
            showCardOption();
            String nextProductIDText = this.model.getNextProductIDText();
            this.textfProductID.setText(nextProductIDText);
            this.editState = EditState.ADD;
        } else if (source == btnModify) {
            String productIDText = textfProductID.getText();
            if (productIDText.isEmpty()) {
                showInfoMessage("You should choose one product first.");
            } else {
                setProductInputEditable(true);
                btnOK.setText("Save");
                showCardOption();
                this.editState = EditState.MODIFY;
            }
        } else if (source == btnRemove) {
            String productIDText = textfProductID.getText();
            if (productIDText.isEmpty()) {
                showInfoMessage("You should choose one product first.");
            } else {
                int ret = JOptionPane.showConfirmDialog(MainFrame.getInstance(),
                        "Remove product?", "Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (ret == JOptionPane.YES_OPTION) {
                    this.controller.requestRemoveProduct();
                }
            }
        } else if (source == btnMore) {
            this.popupBtnMore.show(btnMore, 0, btnMore.getY() + btnMore.getHeight());
        } else if (source == mnImport) {
            this.controller.requestImportExcel();
        } else if (source == mnExport) {
            if (this.tableProductModel.getRowCount() == 0) {
                showErrorMessage("Table product data is empty.");
            } else {

            }
        } else if (source == btnRequestProduce) {
            this.controller.requestProduceProduct();
        } else if (source == btnOK) {
            switch (editState) {
                case ADD: {
                    this.controller.requestCreateProduct();
                    break;
                }
                case MODIFY: {
                    this.controller.requestUpdateProduct();
                    break;
                }
            }
        } else if (source == btnCancel) {
            if (this.editState == EditState.ADD) {
                this.textfProductID.setText("");
                resetProductInput();
            }
            exitEditState();
        } else if (source == btnReset) {
            resetProductInput();
        }
    }

    public void exitEditState() {
        this.editState = null;
        setProductInputEditable(true);
        showCardFunction();
        controller.requestShowProductInfo();
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

    public int getProductAmount() {
        return (int) this.spinnerProductAmount.getValue();
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

    @Override
    public void updateInsertedProductObserver(ProductModelInterface insertedProduct) {
        String searchText = textfSearchName.getText().trim();
        if (this.controller.insertToSearchListByMatchingName(searchText, insertedProduct)) {
            addRowTableProduct(insertedProduct);
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
    public void updateInsertedIngredientObserver(IngredientModelInterface ingredient) {

    }

    @Override
    public void updateModifiedIngredientObserver(IngredientModelInterface ingredient) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateRemovedIngredient(IngredientModelInterface ingredient) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupBtnMore = new javax.swing.JPopupMenu();
        mnImport = new javax.swing.JMenuItem();
        mnExport = new javax.swing.JMenuItem();
        panelProductInfo = new javax.swing.JPanel();
        label_productID = new javax.swing.JLabel();
        label_size = new javax.swing.JLabel();
        label_amount = new javax.swing.JLabel();
        textfProductID = new javax.swing.JTextField();
        combProductSize = new javax.swing.JComboBox<>();
        spinnerProductAmount = new javax.swing.JSpinner();
        label_name = new javax.swing.JLabel();
        label_cost = new javax.swing.JLabel();
        label_ingredient = new javax.swing.JLabel();
        btnEditIngredient = new javax.swing.JButton();
        scrpaneList = new javax.swing.JScrollPane();
        listIngredient = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        textfProductName = new javax.swing.JTextField();
        textfProductCost = new javax.swing.JTextField();
        textfProductPrice = new javax.swing.JTextField();
        label_price = new javax.swing.JLabel();
        label_searchProduct = new javax.swing.JLabel();
        textfSearchName = new javax.swing.JTextField();
        scrpaneTable = new javax.swing.JScrollPane();
        tableProduct = new javax.swing.JTable();
        btnRequestProduce = new javax.swing.JButton();
        btnSearchClear = new javax.swing.JButton();
        panelCard = new javax.swing.JPanel();
        panelBtnFunction = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnMore = new javax.swing.JButton();
        panelBtnOption = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        mnImport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        mnImport.setText("Import");
        popupBtnMore.add(mnImport);

        mnExport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        mnExport.setText("Export");
        popupBtnMore.add(mnExport);

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));

        panelProductInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelProductInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        label_productID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_productID.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_productID.setText(" ID");
        label_productID.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        label_size.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_size.setText("Size");

        label_amount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_amount.setText("Amount");

        textfProductID.setEditable(false);
        textfProductID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfProductID.setPreferredSize(new java.awt.Dimension(160, 30));

        combProductSize.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combProductSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S", "M", "L" }));
        combProductSize.setPreferredSize(new java.awt.Dimension(160, 30));

        spinnerProductAmount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        spinnerProductAmount.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        spinnerProductAmount.setPreferredSize(new java.awt.Dimension(160, 30));

        label_name.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_name.setText(" Name");

        label_cost.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_cost.setText("Cost");

        label_ingredient.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_ingredient.setText("Ingredient List");

        btnEditIngredient.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnEditIngredient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_select.png"))); // NOI18N
        btnEditIngredient.setContentAreaFilled(false);
        btnEditIngredient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditIngredient.setFocusPainted(false);

        listIngredient.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        listIngredient.setModel(new DefaultListModel());
        listIngredient.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrpaneList.setViewportView(listIngredient);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("Unit");

        textfProductName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfProductCost.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfProductPrice.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        label_price.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_price.setText("Price");

        javax.swing.GroupLayout panelProductInfoLayout = new javax.swing.GroupLayout(panelProductInfo);
        panelProductInfo.setLayout(panelProductInfoLayout);
        panelProductInfoLayout.setHorizontalGroup(
            panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductInfoLayout.createSequentialGroup()
                .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label_amount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerProductAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(panelProductInfoLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_name)
                            .addComponent(label_productID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelProductInfoLayout.createSequentialGroup()
                                .addComponent(textfProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(label_size)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(combProductSize, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_cost, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_price, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textfProductCost, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textfProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28)
                .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_ingredient)
                    .addGroup(panelProductInfoLayout.createSequentialGroup()
                        .addComponent(scrpaneList, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditIngredient, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelProductInfoLayout.setVerticalGroup(
            panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductInfoLayout.createSequentialGroup()
                .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductInfoLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_productID)
                            .addComponent(textfProductID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_size, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combProductSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_cost, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textfProductCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_name)
                            .addComponent(textfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textfProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_price, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_amount)
                            .addComponent(spinnerProductAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelProductInfoLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(btnEditIngredient))
                    .addGroup(panelProductInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label_ingredient)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrpaneList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        label_searchProduct.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_searchProduct.setText("Search name:");

        textfSearchName.setPreferredSize(new java.awt.Dimension(190, 30));

        tableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Size", "Cost", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.Integer.class
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

        btnRequestProduce.setBackground(new java.awt.Color(255, 255, 255));
        btnRequestProduce.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_import-selected-product.png"))); // NOI18N
        btnRequestProduce.setContentAreaFilled(false);
        btnRequestProduce.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRequestProduce.setFocusPainted(false);

        btnSearchClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSearchClear.setForeground(new java.awt.Color(51, 51, 51));
        btnSearchClear.setText("Clear");
        btnSearchClear.setFocusPainted(false);
        btnSearchClear.setPreferredSize(new java.awt.Dimension(89, 29));

        panelCard.setLayout(new java.awt.CardLayout());

        panelBtnFunction.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnFunction.setName("Function"); // NOI18N
        panelBtnFunction.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(51, 51, 51));
        btnAdd.setText("Add");
        btnAdd.setFocusPainted(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnFunction.add(btnAdd);

        btnModify.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnModify.setForeground(new java.awt.Color(51, 51, 51));
        btnModify.setText("Modify");
        btnModify.setFocusPainted(false);
        btnModify.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnFunction.add(btnModify);

        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnRemove.setForeground(new java.awt.Color(51, 51, 51));
        btnRemove.setText("Remove");
        btnRemove.setFocusPainted(false);
        btnRemove.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnFunction.add(btnRemove);

        btnMore.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnMore.setForeground(new java.awt.Color(51, 51, 51));
        btnMore.setText("More ▼");
        btnMore.setFocusPainted(false);
        btnMore.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnFunction.add(btnMore);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelProductInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrpaneTable)
                    .addComponent(btnRequestProduce, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_searchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textfSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
                        .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProductInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_searchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scrpaneTable, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRequestProduce)
                .addGap(30, 30, 30))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEditIngredient;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnMore;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRequestProduce;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JComboBox<String> combProductSize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel label_amount;
    private javax.swing.JLabel label_cost;
    private javax.swing.JLabel label_ingredient;
    private javax.swing.JLabel label_name;
    private javax.swing.JLabel label_price;
    private javax.swing.JLabel label_productID;
    private javax.swing.JLabel label_searchProduct;
    private javax.swing.JLabel label_size;
    private javax.swing.JList<String> listIngredient;
    private javax.swing.JMenuItem mnExport;
    private javax.swing.JMenuItem mnImport;
    private javax.swing.JPanel panelBtnFunction;
    private javax.swing.JPanel panelBtnOption;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel panelProductInfo;
    private javax.swing.JPopupMenu popupBtnMore;
    private javax.swing.JScrollPane scrpaneList;
    private javax.swing.JScrollPane scrpaneTable;
    private javax.swing.JSpinner spinnerProductAmount;
    private javax.swing.JTable tableProduct;
    private javax.swing.JTextField textfProductCost;
    private javax.swing.JTextField textfProductID;
    private javax.swing.JTextField textfProductName;
    private javax.swing.JTextField textfProductPrice;
    private javax.swing.JTextField textfSearchName;
    // End of variables declaration//GEN-END:variables
}
