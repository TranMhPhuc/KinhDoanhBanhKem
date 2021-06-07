package view.function.ingredient;

import control.ingredient.IngredientControllerInterface;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.junit.Assert;
import model.ingredient.IngredientManageModelInterface;
import model.ingredient.IngredientModelInterface;
import model.ingredient.type.IngredientTypeDataStorageInterface;
import model.ingredient.type.IngredientTypeModelInterface;
import model.ingredient.unit.IngredientUnitModelInterface;
import model.provider.ProviderModelInterface;
import util.swing.UIControl;
import view.MessageShowing;
import view.main.MainFrame;

public class IngredientPanel extends javax.swing.JPanel implements ActionListener, MessageShowing {

    enum EditState {
        ADD,
        MODIFY,
    }

    private volatile static IngredientPanel uniqueInstance;

    private static IngredientTypeDataStorageInterface ingredientTypeDataStorage;

    private IngredientManageModelInterface model;
    private IngredientControllerInterface controller;

    private DefaultTableModel tableIngredientModel;

    private EditState editState;

    private IngredientPanel(IngredientManageModelInterface model,
            IngredientControllerInterface controller) {
        if (model == null) {
            throw new IllegalArgumentException("Ingredient model is null object.");
        }
        if (controller == null) {
            throw new IllegalArgumentException("Ingredient controller is null object.");
        }

        initComponents();

        this.model = model;
        this.controller = controller;
        this.tableIngredientModel = (DefaultTableModel) tableIngredient.getModel();
        this.editState = null;

        createView();
        createControl();
    }

    public static IngredientPanel getInstance(IngredientManageModelInterface model,
            IngredientControllerInterface controller) {
        if (uniqueInstance == null) {
            synchronized (IngredientPanel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new IngredientPanel(model, controller);
                }
            }
        }
        return uniqueInstance;
    }

    public static IngredientPanel getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

    private void createView() {
        textfIngredientID.setEditable(false);
        resetIngredientInput();
        setIngredientInputEditable(false);
        UIControl.setDefaultTableHeader(tableIngredient);
        loadIngredientTypeInput();
        loadProviderInput();
        loadIngredientUnitInput();
        resetIngredientList();
    }

    private void createControl() {
        btnCreateIngredientType.addActionListener(this);
        btnSearchClear.addActionListener(this);
        btnAdd.addActionListener(this);
        btnModify.addActionListener(this);
        btnRemove.addActionListener(this);
        btnMore.addActionListener(this);
        btnRequestImport.addActionListener(this);
        btnShowImportHistory.addActionListener(this);
        mnImport.addActionListener(this);
        mnExport.addActionListener(this);
        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);
        btnReset.addActionListener(this);

        tableIngredient.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int rowID = tableIngredient.getSelectedRow();
                if (rowID != -1) {
                    controller.selectRowTableIngredient(rowID);
                }
            }
        });

        textfSearchName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                String searchText = textfSearchName.getText().trim();
                showIngredientList(controller.getIngredientSearchByName(searchText));
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                String searchText = textfSearchName.getText().trim();
                if (searchText.isEmpty()) {
                    resetIngredientList();
                } else {
                    showIngredientList(controller.getIngredientSearchByName(searchText));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public void showIngredientInfo(IngredientModelInterface ingredient) {
        if (editState != null) {
            return;
        }

        this.textfIngredientID.setText(ingredient.getIngredientIDText());
        this.textfIngredientName.setText(ingredient.getName());
        this.textfIngredientCost.setText(String.valueOf(ingredient.getCost()));
        String providerName = ingredient.getProvider().getName();
        for (int i = 0; i < this.combProviderName.getItemCount(); i++) {
            if (this.combProviderName.getItemAt(i).equals(providerName)) {
                this.combProviderName.setSelectedIndex(i);
                break;
            }
        }
        String ingredientTypeName = ingredient.getIngredientType().getName();
        for (int i = 0; i < this.combIngredientTypeName.getItemCount(); i++) {
            if (this.combIngredientTypeName.getItemAt(i).equals(ingredientTypeName)) {
                this.combIngredientTypeName.setSelectedIndex(i);
                break;
            }
        }
    }

    public void loadIngredientTypeInput() {
        Iterator<IngredientTypeModelInterface> iterator = this.model.getAllIngredientType();
        combIngredientTypeName.removeAllItems();
        while (iterator.hasNext()) {
            IngredientTypeModelInterface ingredientType = iterator.next();
            combIngredientTypeName.addItem(ingredientType.getName());
        }
        if (combIngredientTypeName.getItemCount() != 0) {
            combIngredientTypeName.setSelectedIndex(0);
        }
    }

    public void loadProviderInput() {
        Iterator<ProviderModelInterface> iterator = this.model.getAllProvider();
        combProviderName.removeAllItems();
        while (iterator.hasNext()) {
            ProviderModelInterface provider = iterator.next();
            combProviderName.addItem(provider.getName());
        }
        if (combProviderName.getItemCount() != 0) {
            combProviderName.setSelectedIndex(0);
        }
    }
    
    public void loadIngredientUnitInput() {
        Iterator<IngredientUnitModelInterface> iterator = this.model.getAllIngredientUnit();
        combIngredientUnitName.removeAllItems();
        while (iterator.hasNext()) {
            IngredientUnitModelInterface ingredientUnit = iterator.next();
            combIngredientUnitName.addItem(ingredientUnit.getName());
        }
        if (combIngredientUnitName.getItemCount() != 0) {
            combIngredientUnitName.setSelectedIndex(0);
        }
    }

    public void resetIngredientList() {
        Iterator<IngredientModelInterface> iterator = this.controller.getAllIngredient();
        showIngredientList(iterator);
    }

    private void showIngredientList(Iterator<IngredientModelInterface> iterator) {
        clearTableIngredient();
        while (iterator.hasNext()) {
            addRowTableIngredient(iterator.next());
        }
    }

    public void clearTableIngredient() {
        this.tableIngredientModel.setRowCount(0);
    }

    public void addRowTableIngredient(IngredientModelInterface ingredient) {
        Object[] record = new Object[]{
            ingredient.getIngredientIDText(),
            ingredient.getName(),
            ingredient.getCost(),
            ingredient.getIngredientType().getName(),
            ingredient.getAmount(),
            ingredient.getIngredientUnit().getName(),
            ingredient.getProvider().getName()
        };
        this.tableIngredientModel.addRow(record);
    }

    public void setTextfSearch(String text) {
        this.textfSearchName.setText(text);
    }
    
    public String getTextSearch() {
        return this.textfSearchName.getText();
    }

    private void addIngredientAction() {
        resetIngredientInput();
        setIngredientInputEditable(true);
        showCardOption();

        String nextIngredientIDText = this.model.getNextIngredientID();
        this.textfIngredientID.setText(nextIngredientIDText);

        this.editState = EditState.ADD;
    }

    private void modifyIngredientAction() {
        String ingredientIDText = textfIngredientID.getText();
        if (ingredientIDText.isEmpty()) {
            showInfoMessage("You should choose one ingredient first.");
        } else {
            setIngredientInputEditable(true);
            showCardOption();
        }
        this.editState = EditState.MODIFY;
    }

    private void removeIngredientAction() {
        String ingredientIDText = textfIngredientID.getText();
        if (ingredientIDText.isEmpty()) {
            showInfoMessage("You should choose one ingredient first.");
        } else {
            int ret = JOptionPane.showConfirmDialog(MainFrame.getInstance(),
                    "Remove ingredient?", "Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (ret == JOptionPane.YES_OPTION) {
                this.controller.requestRemoveIngreident();
            }
        }
    }

    private void requestImportAction() {
        int rowID = tableIngredient.getSelectedRow();
        if (rowID == -1) {
            showInfoMessage("You should choose one ingredient first.");
        } else {
            this.controller.requestImportIngredient(rowID);
        }
    }

    private void requestViewImportHistoryAction() {
        int rowID = tableIngredient.getSelectedRow();
        if (rowID == -1) {
            showInfoMessage("You should choose one ingredient first.");
        } else {
            this.controller.viewIngredientImportHistory(rowID);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnCreateIngredientType) {
            this.controller.requestCreateNewIngredientType();
        } else if (source == btnSearchClear) {
            if (!textfSearchName.getText().trim().isEmpty()) {
                setTextfSearch("");
                resetIngredientList();
                resetIngredientInput();
            }
        } else if (source == btnAdd) {
            addIngredientAction();
        } else if (source == btnModify) {
            modifyIngredientAction();
        } else if (source == btnRemove) {
            removeIngredientAction();
        } else if (source == btnMore) {
            this.popupBtnMore.show(btnMore, 0, btnMore.getY() + btnMore.getHeight());
        } else if (source == btnRequestImport) {
            requestImportAction();
        } else if (source == btnShowImportHistory) {
            requestViewImportHistoryAction();
        } else if (source == mnImport) {
            // XXX

        } else if (source == mnExport) {
            // XXX

        } else if (source == btnOk) {
            switch (editState) {
                case ADD: {
                    this.controller.requestCreateIngredient();
                    break;
                }
                case MODIFY: {
                    int rowID = tableIngredient.getSelectedRow();
                    break;
                }
            }

        } else if (source == btnCancel) {
            this.editState = null;
            setIngredientInputEditable(false);
            showCardFunction();
            int rowID = tableIngredient.getSelectedRow();
            if (rowID != -1) {
                controller.selectRowTableIngredient(rowID);
            }
        } else if (source == btnReset) {
            resetIngredientInput();
        } else if (source == btnCreateIngredientType) {
            this.controller.requestCreateNewIngredientType();
        }
    }

    public String getIngredientNameInput() {
        return textfIngredientName.getText().trim();
    }

    public String getIngredientCostInput() {
        return textfIngredientCost.getText().trim();
    }
    
    public String getIngredientID() {
        return textfIngredientID.getText();
    }
    
    public int getProviderSelectIndex() {
        return combProviderName.getSelectedIndex();
    }
    
    public int getIngredientTypeSelectIndex() {
        return combIngredientTypeName.getSelectedIndex();
    }
    
    public int getIngredientUnitSelectIndex() {
        return combIngredientUnitName.getSelectedIndex();
    }

    public void showCardOption() {
        CardLayout cardLayout = (CardLayout) panelCard.getLayout();
        cardLayout.show(panelCard, panelBtnOption.getName());
    }

    public void showCardFunction() {
        CardLayout cardLayout = (CardLayout) panelCard.getLayout();
        cardLayout.show(panelCard, panelBtnFunction.getName());
    }

    public void setIngredientInputEditable(boolean editable) {
        textfIngredientCost.setEditable(editable);
        textfIngredientName.setEditable(editable);
        combProviderName.setEnabled(editable);
        combIngredientTypeName.setEnabled(editable);
        combIngredientUnitName.setEnabled(editable);
    }

    public void resetIngredientInput() {
        textfIngredientCost.setText("");
        textfIngredientID.setText("");
        textfIngredientName.setText("");
        if (combProviderName.getItemCount() != 0) {
            combProviderName.setSelectedIndex(0);
        }
        if (combIngredientTypeName.getItemCount() != 0) {
            combIngredientTypeName.setSelectedIndex(0);
        }
        if (combIngredientUnitName.getItemCount() != 0) {
            combIngredientUnitName.setSelectedIndex(0);
        }
    }

    @Override
    public void showErrorMessage(String message) {
        MainFrame.getInstance().showErrorMessage(message);
    }

    @Override
    public void showInfoMessage(String message) {
        MainFrame.getInstance().showErrorMessage(message);
    }

    @Override
    public void showWarningMessage(String message) {
        MainFrame.getInstance().showErrorMessage(message);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupBtnMore = new javax.swing.JPopupMenu();
        mnImport = new javax.swing.JMenuItem();
        mnExport = new javax.swing.JMenuItem();
        panelIngredientInfo = new javax.swing.JPanel();
        label_ingreID = new javax.swing.JLabel();
        textfIngredientID = new javax.swing.JTextField();
        label_ingreName = new javax.swing.JLabel();
        label_ingreCost = new javax.swing.JLabel();
        label_ingreType = new javax.swing.JLabel();
        combIngredientTypeName = new javax.swing.JComboBox<>();
        btnCreateIngredientType = new javax.swing.JButton();
        label_IngredientProvider = new javax.swing.JLabel();
        textfIngredientName = new javax.swing.JTextField();
        textfIngredientCost = new javax.swing.JTextField();
        combProviderName = new javax.swing.JComboBox<>();
        label_ingreUnit = new javax.swing.JLabel();
        combIngredientUnitName = new javax.swing.JComboBox<>();
        label_Search = new javax.swing.JLabel();
        textfSearchName = new javax.swing.JTextField();
        scrpaneIngredient = new javax.swing.JScrollPane();
        tableIngredient = new javax.swing.JTable();
        btnRequestImport = new javax.swing.JButton();
        btnShowImportHistory = new javax.swing.JButton();
        btnSearchClear = new javax.swing.JButton();
        panelCard = new javax.swing.JPanel();
        panelBtnFunction = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnMore = new javax.swing.JButton();
        panelBtnOption = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        mnImport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        mnImport.setText("Import");
        popupBtnMore.add(mnImport);

        mnExport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        mnExport.setText("Export");
        mnExport.setToolTipText("");
        popupBtnMore.add(mnExport);

        setBackground(new java.awt.Color(255, 255, 255));

        panelIngredientInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelIngredientInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingredient Information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        label_ingreID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_ingreID.setText(" ID");

        textfIngredientID.setEditable(false);
        textfIngredientID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfIngredientID.setPreferredSize(new java.awt.Dimension(160, 30));

        label_ingreName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_ingreName.setText(" Name");

        label_ingreCost.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_ingreCost.setText("Cost");

        label_ingreType.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_ingreType.setText("Type");

        combIngredientTypeName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combIngredientTypeName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combIngredientTypeName.setPreferredSize(new java.awt.Dimension(160, 30));

        btnCreateIngredientType.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnCreateIngredientType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_new-type.png"))); // NOI18N
        btnCreateIngredientType.setContentAreaFilled(false);
        btnCreateIngredientType.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreateIngredientType.setFocusPainted(false);

        label_IngredientProvider.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_IngredientProvider.setText("Provider");

        textfIngredientName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfIngredientCost.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        combProviderName.setFont(label_ingreID.getFont());
        combProviderName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        label_ingreUnit.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_ingreUnit.setText("Unit");

        combIngredientUnitName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combIngredientUnitName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combIngredientUnitName.setPreferredSize(new java.awt.Dimension(160, 30));

        javax.swing.GroupLayout panelIngredientInfoLayout = new javax.swing.GroupLayout(panelIngredientInfo);
        panelIngredientInfo.setLayout(panelIngredientInfoLayout);
        panelIngredientInfoLayout.setHorizontalGroup(
            panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIngredientInfoLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label_ingreName)
                    .addComponent(label_ingreID))
                .addGap(18, 18, 18)
                .addGroup(panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelIngredientInfoLayout.createSequentialGroup()
                        .addComponent(textfIngredientID, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelIngredientInfoLayout.createSequentialGroup()
                                .addGap(199, 199, 199)
                                .addComponent(label_IngredientProvider))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIngredientInfoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label_ingreCost))))
                    .addComponent(textfIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textfIngredientCost)
                    .addComponent(combProviderName, 0, 264, Short.MAX_VALUE))
                .addGap(66, 66, 66)
                .addGroup(panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelIngredientInfoLayout.createSequentialGroup()
                        .addComponent(label_ingreType)
                        .addGap(18, 18, 18)
                        .addComponent(combIngredientTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelIngredientInfoLayout.createSequentialGroup()
                        .addComponent(label_ingreUnit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(combIngredientUnitName, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCreateIngredientType)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelIngredientInfoLayout.setVerticalGroup(
            panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIngredientInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCreateIngredientType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelIngredientInfoLayout.createSequentialGroup()
                        .addGroup(panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_ingreID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textfIngredientID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(combIngredientTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label_ingreType)
                                .addComponent(label_ingreCost, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textfIngredientCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_ingreName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_IngredientProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combProviderName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label_ingreUnit)
                    .addComponent(combIngredientUnitName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        label_Search.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_Search.setText("Search name:");

        textfSearchName.setPreferredSize(new java.awt.Dimension(190, 30));

        tableIngredient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Cost", "Type", "Amount", "Unit", "Provider"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Long.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
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
        tableIngredient.getTableHeader().setReorderingAllowed(false);
        scrpaneIngredient.setViewportView(tableIngredient);

        btnRequestImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_import-selected-ingredient.png"))); // NOI18N
        btnRequestImport.setContentAreaFilled(false);
        btnRequestImport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRequestImport.setFocusPainted(false);

        btnShowImportHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_show-import-history.png"))); // NOI18N
        btnShowImportHistory.setContentAreaFilled(false);
        btnShowImportHistory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnShowImportHistory.setFocusPainted(false);

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

        btnOk.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnOk.setForeground(new java.awt.Color(51, 51, 51));
        btnOk.setText("OK");
        btnOk.setFocusPainted(false);
        btnOk.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnOption.add(btnOk);

        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(51, 51, 51));
        btnCancel.setText("Cancel");
        btnCancel.setFocusPainted(false);
        btnCancel.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnOption.add(btnCancel);

        btnReset.setFont(btnCancel.getFont());
        btnReset.setText("Reset");
        panelBtnOption.add(btnReset);

        panelCard.add(panelBtnOption, "Option");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrpaneIngredient)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_Search)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textfSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addComponent(panelIngredientInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(btnRequestImport)
                .addGap(138, 138, 138)
                .addComponent(btnShowImportHistory)
                .addContainerGap(238, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelIngredientInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textfSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 16, Short.MAX_VALUE)
                .addComponent(scrpaneIngredient, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRequestImport)
                    .addComponent(btnShowImportHistory))
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCreateIngredientType;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnMore;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRequestImport;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JButton btnShowImportHistory;
    private javax.swing.JComboBox<String> combIngredientTypeName;
    private javax.swing.JComboBox<String> combIngredientUnitName;
    private javax.swing.JComboBox<String> combProviderName;
    private javax.swing.JLabel label_IngredientProvider;
    private javax.swing.JLabel label_Search;
    private javax.swing.JLabel label_ingreCost;
    private javax.swing.JLabel label_ingreID;
    private javax.swing.JLabel label_ingreName;
    private javax.swing.JLabel label_ingreType;
    private javax.swing.JLabel label_ingreUnit;
    private javax.swing.JMenuItem mnExport;
    private javax.swing.JMenuItem mnImport;
    private javax.swing.JPanel panelBtnFunction;
    private javax.swing.JPanel panelBtnOption;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel panelIngredientInfo;
    private javax.swing.JPopupMenu popupBtnMore;
    private javax.swing.JScrollPane scrpaneIngredient;
    private javax.swing.JTable tableIngredient;
    private javax.swing.JTextField textfIngredientCost;
    private javax.swing.JTextField textfIngredientID;
    private javax.swing.JTextField textfIngredientName;
    private javax.swing.JTextField textfSearchName;
    // End of variables declaration//GEN-END:variables
}
