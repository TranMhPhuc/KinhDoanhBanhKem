package view.ingredient;

import control.ingredient.IngredientControllerInterface;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.ingredient.IngredientManageModelInterface;
import model.ingredient.IngredientModelInterface;
import model.ingredient.type.IngredientTypeModelInterface;
import model.product.ProductModelInterface;
import model.provider.ProviderModelInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import util.messages.Messages;
import util.swing.NumberRenderer;
import util.swing.UIControl;
import view.MessageShowing;
import view.product.ModifiedProductObserver;
import view.provider.InsertedProviderObserver;
import view.provider.ModifiedProviderObserver;
import view.provider.RemovedProviderObserver;

public class IngredientPanel extends javax.swing.JPanel implements ActionListener,
        MessageShowing, InsertedIngredientObserver, ModifiedIngredientObserver,
        RemovedIngredientObserver, InsertedIngredientTypeObserver, InsertedProviderObserver,
        ModifiedProviderObserver, RemovedProviderObserver, ModifiedProductObserver,
        SettingUpdateObserver {

    public static final int INGREDIENT_ID_COLUMN_INDEX = 0;
    public static final int INGREDIENT_NAME_COLUMN_INDEX = 1;
    public static final int INGREDIENT_TYPE_COLUMN_INDEX = 2;
    public static final int INGREDIENT_UNIT_COLUMN_INDEX = 3;
    public static final int INGREDIENT_PROVIDER_NAME_COLUMN_INDEX = 4;
    public static final int INGREDIENT_COST_COLUMN_INDEX = 5;
    public static final int INGREDIENT_AMOUNT_COLUMN_INDEX = 6;

    public enum EditState {
        ADD,
        MODIFY,
    }

    private JFrame mainFrame;

    private IngredientManageModelInterface ingredientManageModel;
    private IngredientControllerInterface ingredientController;

    private DefaultTableModel tableIngredientModel;

    private EditState editState;

    public IngredientPanel() {
        initComponents();
        this.tableIngredientModel = (DefaultTableModel) tableIngredient.getModel();
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

    public EditState getEditState() {
        return this.editState;
    }

    public void setIngredientManageModel(IngredientManageModelInterface model) {
        if (model == null) {
            throw new NullPointerException("Ingredient model is null object.");
        }
        this.ingredientManageModel = model;
        this.ingredientManageModel.registerInsertedIngredientObserver(this);
        this.ingredientManageModel.registerModifiedIngredientObserver(this);
        this.ingredientManageModel.registerRemovedIngredientObserver(this);
        this.ingredientManageModel.registerInsertedIngredientTypeObserver(this);

        loadIngredientTypeInput();
        loadProviderNameInput();
        loadIngredientUnitInput();
        resetIngredientList();
    }

    public void setIngredientController(IngredientControllerInterface controller) {
        if (controller == null) {
            throw new NullPointerException("Ingredient controller is null object.");
        }
        this.ingredientController = controller;
    }

    private void createView() {
        textfIngredientID.setEditable(false);
        resetIngredientInput();
        setIngredientInputEditable(false);
        UIControl.setDefaultTableHeader(tableIngredient);
        
         TableColumnModel m = tableIngredient.getColumnModel();
        m.getColumn(5).setCellRenderer(NumberRenderer.getCurrencyRenderer());
    }

    private void createControl() {
        btnCreateIngredientType.addActionListener(this);
        btnSearchClear.addActionListener(this);
        btnAdd.addActionListener(this);
        btnModify.addActionListener(this);
        btnRemove.addActionListener(this);
        btnExport.addActionListener(this);
        btnRequestImport.addActionListener(this);
        btnShowImportHistory.addActionListener(this);
        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);
        btnReset.addActionListener(this);

        tableIngredient.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ingredientController.requestShowIngredientInfo();
            }
        });

        textfSearchName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                String searchText = textfSearchName.getText().trim();
                showIngredientList(ingredientController.getIngredientBySearchName(searchText));
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                String searchText = textfSearchName.getText().trim();
                if (searchText.isEmpty()) {
                    resetIngredientList();
                } else {
                    showIngredientList(ingredientController.getIngredientBySearchName(searchText));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public int getTableIngredientRowCount() {
        return tableIngredientModel.getRowCount();
    }

    public JTable getTableIngredient() {
        return tableIngredient;
    }

    public String getProviderNameSelected() {
        Object ret = this.combProviderName.getSelectedItem();
        if (ret == null) {
            return null;
        }
        return (String) ret;
    }

    public void showIngredientInfo(IngredientModelInterface ingredient) {
        this.textfIngredientID.setText(ingredient.getIngredientIDText());
        this.textfIngredientName.setText(ingredient.getName());
        this.textfIngredientCost.setText(String.valueOf(ingredient.getCost()));

        String providerName = ingredient.getProviderName();
        for (int i = 0; i < this.combProviderName.getItemCount(); i++) {
            if (this.combProviderName.getItemAt(i).equals(providerName)) {
                this.combProviderName.setSelectedIndex(i);
                break;
            }
        }
        String ingredientTypeName = ingredient.getTypeName();
        for (int i = 0; i < this.combIngredientTypeName.getItemCount(); i++) {
            if (this.combIngredientTypeName.getItemAt(i).equals(ingredientTypeName)) {
                this.combIngredientTypeName.setSelectedIndex(i);
                break;
            }
        }
        String ingredientUnitName = ingredient.getUnitName();
        for (int i = 0; i < this.combIngredientUnitName.getItemCount(); i++) {
            if (this.combIngredientUnitName.getItemAt(i).equals(ingredientUnitName)) {
                this.combIngredientUnitName.setSelectedIndex(i);
                break;
            }
        }
    }

    public void loadIngredientTypeInput() {
        combIngredientTypeName.removeAllItems();
        List<String> ingredientTypeNames = this.ingredientManageModel.getAllIngredientTypeName();
        ingredientTypeNames.forEach(name -> {
            combIngredientTypeName.addItem(name);
        });
        if (combIngredientTypeName.getItemCount() != 0) {
            combIngredientTypeName.setSelectedIndex(0);
        }
    }

    public void loadProviderNameInput() {
        combProviderName.removeAllItems();
        List<String> providerNames = this.ingredientManageModel.getAllProviderName();
        providerNames.forEach(name -> {
            combProviderName.addItem(name);
        });
        if (combProviderName.getItemCount() != 0) {
            combProviderName.setSelectedIndex(0);
        }
    }

    public void loadIngredientUnitInput() {
        combIngredientUnitName.setSelectedIndex(0);
    }

    public void resetIngredientList() {
        Iterator<IngredientModelInterface> iterator = this.ingredientController.getAllIngredientData();
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

    private void addRowTableIngredient(IngredientModelInterface ingredient) {
        Object[] record = new Object[]{
            ingredient.getIngredientIDText(),
            ingredient.getName(),
            ingredient.getTypeName(),
            ingredient.getUnitName(),
            ingredient.getProviderName(),
            ingredient.getCost(),
            ingredient.getAmount()
        };
        this.tableIngredientModel.addRow(record);
    }

    private void updateRowTableIngredient(IngredientModelInterface ingredient) {
        for (int i = 0; i < this.tableIngredientModel.getRowCount(); i++) {
            String ingredientIDInTable = (String) this.tableIngredientModel.getValueAt(i, INGREDIENT_ID_COLUMN_INDEX);
            if (ingredientIDInTable.equals(ingredient.getIngredientIDText())) {
                Object[] record = new Object[]{
                    ingredient.getIngredientIDText(),
                    ingredient.getName(),
                    ingredient.getTypeName(),
                    ingredient.getUnitName(),
                    ingredient.getProviderName(),
                    ingredient.getCost(),
                    ingredient.getAmount()
                };
                for (int j = 0; j < record.length; j++) {
                    this.tableIngredientModel.setValueAt(record[j], i, j);
                }
                break;
            }
        }
    }

    private void removeRowTableIngredient(IngredientModelInterface ingredient) {
        if (this.ingredientController.deleteIngredientInSearchList(ingredient)) {
            String ingredientIDText = ingredient.getIngredientIDText();
            for (int i = 0; i < this.tableIngredientModel.getRowCount(); i++) {
                String ingredientIDInTable = (String) this.tableIngredientModel.getValueAt(i, INGREDIENT_ID_COLUMN_INDEX);
                if (ingredientIDInTable.equals(ingredientIDText)) {
                    this.tableIngredientModel.removeRow(i);
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
        return this.tableIngredient.getSelectedRow();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnCreateIngredientType) {
            this.ingredientController.requestCreateNewIngredientType();
        } else if (source == btnSearchClear) {
            if (!textfSearchName.getText().trim().isEmpty()) {
                setTextfSearch("");
                resetIngredientList();
                resetIngredientInput();
            }
        } else if (source == btnAdd) {
            resetIngredientInput();
            setIngredientInputEditable(true);
            btnRequestImport.setEnabled(false);
            btnShowImportHistory.setEnabled(false);
            if (btnAdd.getText().equals("Add")) {
                btnOK.setText("Add");
            } else {
                btnOK.setText("Thêm");
            }
            showCardOption();
            String nextIngredientIDText = this.ingredientManageModel.getNextIngredientIDText();
            this.textfIngredientID.setText(nextIngredientIDText);
            this.editState = EditState.ADD;
        } else if (source == btnModify) {
            String ingredientIDText = textfIngredientID.getText();
            if (ingredientIDText.isEmpty()) {
                showInfoMessage(Messages.getInstance().INGR_NO_INGR_CHOSEN);
            } else {
                setIngredientInputEditable(true);
                combIngredientUnitName.setEnabled(ingredientController.isUnitModifiable());
                btnRequestImport.setEnabled(false);
                btnShowImportHistory.setEnabled(false);
                if (btnModify.getText().equals("Modify")) {
                    btnOK.setText("Save");
                } else {
                    btnOK.setText("Lưu");
                }
                showCardOption();
                this.editState = EditState.MODIFY;
            }
        } else if (source == btnRemove) {
            String ingredientIDText = textfIngredientID.getText();
            if (ingredientIDText.isEmpty()) {
                showInfoMessage(Messages.getInstance().INGR_NO_INGR_CHOSEN);
            } else {
                int ret = JOptionPane.showConfirmDialog(getParent(),
                        Messages.getInstance().OTHERS_REMOVE_INGR, "BakeryMS", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/warning.png")));
                if (ret == JOptionPane.YES_OPTION) {
                    this.ingredientController.requestRemoveIngredient();
                }
            }
        } else if (source == btnExport) {
            this.ingredientController.requestExportExcel();
        } else if (source == btnRequestImport) {
            this.ingredientController.requestImportIngredient();
        } else if (source == btnShowImportHistory) {
            this.ingredientController.requestViewImportHistory();
        } else if (source == btnOK) {
            switch (editState) {
                case ADD: {
                    this.ingredientController.requestCreateIngredient();
                    break;
                }
                case MODIFY: {
                    this.ingredientController.requestUpdateIngredient();
                    break;
                }
            }
        } else if (source == btnCancel) {
            exitEditState();
        } else if (source == btnReset) {
            resetIngredientInput();
        }
    }

    public void exitEditState() {
        if (this.editState == EditState.ADD) {
            this.textfIngredientID.setText("");
            resetIngredientInput();
        }
        this.editState = null;
        setIngredientInputEditable(false);
        btnRequestImport.setEnabled(true);
        btnShowImportHistory.setEnabled(true);
        showCardFunction();
        ingredientController.requestShowIngredientInfo();
    }

    public String getIngredientNameInput() {
        return textfIngredientName.getText().trim();
    }

    public String getIngredientCostInput() {
        return textfIngredientCost.getText().trim();
    }

    public String getIngredientIDText() {
        return textfIngredientID.getText();
    }

    public String getIngredientTypeNameSelected() {
        Object ret = combIngredientTypeName.getSelectedItem();
        if (ret == null) {
            return null;
        }
        return (String) ret;
    }

    public void setIngredientTypeSelectIndex(String itemSelect) {
        if (this.editState == null) {
            return;
        }
        if (itemSelect.isEmpty()) {
            throw new IllegalArgumentException("Item content is empty.");
        }
        for (int i = 0; i < this.combIngredientTypeName.getItemCount(); i++) {
            String item = this.combIngredientTypeName.getItemAt(i);
            if (item.equals(itemSelect)) {
                this.combIngredientTypeName.setSelectedIndex(i);
                break;
            }
        }
    }

    public String getIngredientUnitNameSelected() {
        Object ret = combIngredientUnitName.getSelectedItem();
        if (ret == null) {
            return null;
        }
        return (String) ret;
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
    public void updateInsertedIngredientObserver(IngredientModelInterface ingredient) {
        String searchText = textfSearchName.getText().trim();
        if (this.ingredientController.isSearchMatching(searchText, ingredient)) {
            if (searchText.isEmpty()) {
                addRowTableIngredient(ingredient);
            } else {
                showIngredientList(ingredientController.getIngredientBySearchName(searchText));
            }
        }
        this.tableIngredient.repaint();
    }

    @Override
    public void updateModifiedIngredientObserver(IngredientModelInterface ingredient) {
        updateRowTableIngredient(ingredient);
    }

    @Override
    public void updateRemovedIngredient(IngredientModelInterface ingredient) {
        removeRowTableIngredient(ingredient);
    }

    @Override
    public void updateInsertedIngredientType(IngredientTypeModelInterface insertedIngredientType) {
        if (insertedIngredientType == null) {
            throw new NullPointerException("Ingredient type instance is null.");
        }
        this.combIngredientTypeName.addItem(insertedIngredientType.getName());
    }

    @Override
    public void updateInsertedProviderObserver(ProviderModelInterface insertedProvider) {
        if (insertedProvider == null) {
            throw new NullPointerException();
        }
        // insert item in combobox provider
        combProviderName.addItem(insertedProvider.getName());
    }

    @Override
    public void updateModifiedProviderObserver(ProviderModelInterface updatedProvider) {
        // Update combobox provider name
        int combNameSelectedIndex = combProviderName.getSelectedIndex();

        loadProviderNameInput();
        combProviderName.setSelectedIndex(combNameSelectedIndex);

        // Update provider name in table
        ingredientManageModel.updateProviderNameOfIngredientData();

        String searchText = textfSearchName.getText().trim();
        if (searchText.isEmpty()) {
            resetIngredientList();
        } else {
            showIngredientList(ingredientController.getIngredientBySearchName(searchText));
        }
    }

    @Override
    public void updateRemovedProviderObserver(ProviderModelInterface provider) {
        combProviderName.removeItem(provider.getName());
    }

    @Override
    public void updateModifiedProductObserver(ProductModelInterface updatedProduct) {
        // update amount in model
        ingredientManageModel.updateAmountOfIngredientData();

        String searchText = textfSearchName.getText().trim();
        if (searchText.isEmpty()) {
            resetIngredientList();
        } else {
            showIngredientList(ingredientController.getIngredientBySearchName(searchText));
        }
    }

    @Override
    public void updateSettingObserver() {
        AppSetting.Language language = AppSetting.getInstance().getAppLanguage();

        switch (language) {
            case ENGLISH: {
                labelName.setText("Name:");
                labelCost.setText("Cost:");
                labelProvider.setText("Provider:");
                labelType.setText("Type:");
                labelUnit.setText("Unit:");
                labelsearchProduct.setText("Search name:");
                btnCreateIngredientType.setText("New type");
                btnSearchClear.setText("Clear");
                btnAdd.setText("Add");
                btnModify.setText("Modify");
                btnRemove.setText("Remove");
                btnExport.setText("Export");
                btnRequestImport.setText("Import ingredient");
                btnShowImportHistory.setText("Show import history");
                btnCancel.setText("Cancel");
                btnReset.setText("Reset");

                TableColumnModel columnModel = tableIngredient.getColumnModel();

                columnModel.getColumn(INGREDIENT_NAME_COLUMN_INDEX).setHeaderValue("Name");
                columnModel.getColumn(INGREDIENT_TYPE_COLUMN_INDEX).setHeaderValue("Type");
                columnModel.getColumn(INGREDIENT_UNIT_COLUMN_INDEX).setHeaderValue("Unit");
                columnModel.getColumn(INGREDIENT_PROVIDER_NAME_COLUMN_INDEX).setHeaderValue("Provider");
                columnModel.getColumn(INGREDIENT_COST_COLUMN_INDEX).setHeaderValue("Cost");
                columnModel.getColumn(INGREDIENT_AMOUNT_COLUMN_INDEX).setHeaderValue("Amount");

                TitledBorder titledBorder = (TitledBorder) panelInfo.getBorder();
                titledBorder.setTitle("Ingredient information");
                break;
            }
            case VIETNAMESE: {
                labelName.setText("Tên:");
                labelCost.setText("Giá:");
                labelProvider.setText("Nhà cung cấp:");
                labelType.setText("Loại:");
                labelUnit.setText("Đơn vị:");
                labelsearchProduct.setText("Tìm theo tên:");
                btnCreateIngredientType.setText("Thêm loại");
                btnSearchClear.setText("Xóa");
                btnAdd.setText("Thêm");
                btnModify.setText("Chỉnh sửa");
                btnRemove.setText("Xóa");
                btnExport.setText("Xuất");
                btnRequestImport.setText("Nhập nguyên liệu");
                btnShowImportHistory.setText("Xem lịch sử nhập nguyên liệu");
                btnCancel.setText("Thoát");
                btnReset.setText("Làm mới");

                TableColumnModel columnModel = tableIngredient.getColumnModel();

                columnModel.getColumn(INGREDIENT_NAME_COLUMN_INDEX).setHeaderValue("Tên");
                columnModel.getColumn(INGREDIENT_TYPE_COLUMN_INDEX).setHeaderValue("Loại");
                columnModel.getColumn(INGREDIENT_UNIT_COLUMN_INDEX).setHeaderValue("Đơn vị");
                columnModel.getColumn(INGREDIENT_PROVIDER_NAME_COLUMN_INDEX).setHeaderValue("Nhà cung cấp");
                columnModel.getColumn(INGREDIENT_COST_COLUMN_INDEX).setHeaderValue("Giá");
                columnModel.getColumn(INGREDIENT_AMOUNT_COLUMN_INDEX).setHeaderValue("Số lượng");

                TitledBorder titledBorder = (TitledBorder) panelInfo.getBorder();
                titledBorder.setTitle("Thông tin nguyên liệu");
                break;
            }
        }

        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelInfo = new javax.swing.JPanel();
        label_ingreID = new javax.swing.JLabel();
        textfIngredientID = new javax.swing.JTextField();
        labelName = new javax.swing.JLabel();
        labelCost = new javax.swing.JLabel();
        labelType = new javax.swing.JLabel();
        combIngredientTypeName = new javax.swing.JComboBox<>();
        labelProvider = new javax.swing.JLabel();
        textfIngredientName = new javax.swing.JTextField();
        textfIngredientCost = new javax.swing.JTextField();
        combProviderName = new javax.swing.JComboBox<>();
        labelUnit = new javax.swing.JLabel();
        combIngredientUnitName = new javax.swing.JComboBox<>();
        btnCreateIngredientType = new javax.swing.JButton();
        scrpaneIngredient = new javax.swing.JScrollPane();
        tableIngredient = new javax.swing.JTable();
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
        labelsearchProduct = new javax.swing.JLabel();
        textfSearchName = new javax.swing.JTextField();
        btnSearchClear = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnRequestImport = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(100, 0), new java.awt.Dimension(100, 0), new java.awt.Dimension(100, 32767));
        btnShowImportHistory = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setName("Ingredient"); // NOI18N

        panelInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ingredient Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        label_ingreID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_ingreID.setText(" ID");

        textfIngredientID.setEditable(false);
        textfIngredientID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfIngredientID.setPreferredSize(new java.awt.Dimension(160, 30));

        labelName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelName.setText(" Name");

        labelCost.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelCost.setText("Cost");

        labelType.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelType.setText("Type");

        combIngredientTypeName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combIngredientTypeName.setPreferredSize(new java.awt.Dimension(160, 30));

        labelProvider.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelProvider.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelProvider.setText("Provider");

        textfIngredientName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfIngredientCost.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        combProviderName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        labelUnit.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelUnit.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelUnit.setText("Unit");

        combIngredientUnitName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combIngredientUnitName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kilôgam", "Lít", "Quả" }));
        combIngredientUnitName.setPreferredSize(new java.awt.Dimension(160, 30));

        btnCreateIngredientType.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnCreateIngredientType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Sugar_cubes_20px_1.png"))); // NOI18N
        btnCreateIngredientType.setText("New type");

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelName)
                    .addComponent(label_ingreID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textfIngredientID, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelProvider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textfIngredientCost)
                    .addComponent(combProviderName, 0, 264, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(labelType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combIngredientTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(labelUnit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combIngredientUnitName, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(btnCreateIngredientType)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label_ingreID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfIngredientID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(combIngredientTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelType)
                        .addComponent(labelCost, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfIngredientCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCreateIngredientType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combProviderName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(combIngredientUnitName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelUnit)))
                .addContainerGap())
        );

        tableIngredient.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tableIngredient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Type", "Unit", "Provider", "Cost", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.Float.class
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
        tableIngredient.setIntercellSpacing(new java.awt.Dimension(5, 5));
        tableIngredient.setSelectionBackground(new java.awt.Color(113, 168, 255));
        tableIngredient.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableIngredient.getTableHeader().setReorderingAllowed(false);
        scrpaneIngredient.setViewportView(tableIngredient);
        if (tableIngredient.getColumnModel().getColumnCount() > 0) {
            tableIngredient.getColumnModel().getColumn(0).setMinWidth(7);
            tableIngredient.getColumnModel().getColumn(0).setPreferredWidth(7);
            tableIngredient.getColumnModel().getColumn(1).setMinWidth(170);
            tableIngredient.getColumnModel().getColumn(1).setPreferredWidth(170);
            tableIngredient.getColumnModel().getColumn(4).setMinWidth(170);
            tableIngredient.getColumnModel().getColumn(4).setPreferredWidth(170);
            tableIngredient.getColumnModel().getColumn(5).setMinWidth(170);
            tableIngredient.getColumnModel().getColumn(5).setPreferredWidth(170);
        }

        panelCard.setLayout(new java.awt.CardLayout());

        panelBtnFunction.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnFunction.setName("Function"); // NOI18N
        panelBtnFunction.setPreferredSize(new java.awt.Dimension(580, 40));
        panelBtnFunction.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(51, 51, 51));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Add_30px.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setFocusPainted(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnFunction.add(btnAdd);

        btnModify.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnModify.setForeground(new java.awt.Color(51, 51, 51));
        btnModify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Edit_30px.png"))); // NOI18N
        btnModify.setText("Modify");
        btnModify.setFocusPainted(false);
        btnModify.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnFunction.add(btnModify);

        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnRemove.setForeground(new java.awt.Color(51, 51, 51));
        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Trash_30px.png"))); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.setFocusPainted(false);
        btnRemove.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtnFunction.add(btnRemove);

        btnExport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnExport.setForeground(new java.awt.Color(51, 51, 51));
        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Excel_30px.png"))); // NOI18N
        btnExport.setText("Export");
        btnExport.setFocusPainted(false);
        btnExport.setPreferredSize(new java.awt.Dimension(115, 40));
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

        btnReset.setFont(btnCancel.getFont());
        btnReset.setText("Reset");
        panelBtnOption.add(btnReset);

        panelCard.add(panelBtnOption, "Option");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 30));

        labelsearchProduct.setBackground(new java.awt.Color(255, 255, 255));
        labelsearchProduct.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelsearchProduct.setText("Search name:");
        labelsearchProduct.setOpaque(true);
        labelsearchProduct.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel3.add(labelsearchProduct);

        textfSearchName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        textfSearchName.setPreferredSize(new java.awt.Dimension(250, 30));
        jPanel3.add(textfSearchName);

        btnSearchClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSearchClear.setForeground(new java.awt.Color(51, 51, 51));
        btnSearchClear.setText("Clear");
        btnSearchClear.setFocusPainted(false);
        btnSearchClear.setPreferredSize(new java.awt.Dimension(90, 30));
        jPanel3.add(btnSearchClear);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnRequestImport.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnRequestImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Partially_shipped_30px.png"))); // NOI18N
        btnRequestImport.setText("Import Ingredient");
        btnRequestImport.setIconTextGap(10);
        btnRequestImport.setPreferredSize(new java.awt.Dimension(330, 50));
        jPanel1.add(btnRequestImport);
        jPanel1.add(filler1);
        jPanel1.add(filler2);

        btnShowImportHistory.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowImportHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Content_30px.png"))); // NOI18N
        btnShowImportHistory.setText("View import history");
        btnShowImportHistory.setIconTextGap(10);
        btnShowImportHistory.setPreferredSize(new java.awt.Dimension(330, 50));
        jPanel1.add(btnShowImportHistory);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrpaneIngredient)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1271, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrpaneIngredient)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCreateIngredientType;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRequestImport;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JButton btnShowImportHistory;
    private javax.swing.JComboBox<String> combIngredientTypeName;
    private javax.swing.JComboBox<String> combIngredientUnitName;
    private javax.swing.JComboBox<String> combProviderName;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelCost;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelProvider;
    private javax.swing.JLabel labelType;
    private javax.swing.JLabel labelUnit;
    private javax.swing.JLabel label_ingreID;
    private javax.swing.JLabel labelsearchProduct;
    private javax.swing.JPanel panelBtnFunction;
    private javax.swing.JPanel panelBtnOption;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JScrollPane scrpaneIngredient;
    private javax.swing.JTable tableIngredient;
    private javax.swing.JTextField textfIngredientCost;
    private javax.swing.JTextField textfIngredientID;
    private javax.swing.JTextField textfIngredientName;
    private javax.swing.JTextField textfSearchName;
    // End of variables declaration//GEN-END:variables
}
