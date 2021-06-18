package view.provider;

import control.provider.ProviderControllerInterface;
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
import model.provider.ProviderManageModelInterface;
import model.provider.ProviderModelInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import util.swing.UIControl;
import view.MessageShowing;

public class ProviderPanel extends javax.swing.JPanel implements ActionListener,
        MessageShowing, InsertedProviderObserver, ModifiedProviderObserver,
        RemovedProviderObserver, SettingUpdateObserver {

    public static final int PROVIDER_ID_COLUMN_INDEX = 0;
    public static final int PROVIDER_NAME_COLUMN_INDEX = 1;
    public static final int PROVIDER_PHONE_NUM_COLUMN_INDEX = 2;
    public static final int PROVIDER_EMAIL_COLUMN_INDEX = 3;
    public static final int PROVIDER_ADDRESS_COLUMN_INDEX = 4;

    public enum EditState {
        ADD,
        MODIFY,
    }

    public JFrame mainFrame;

    private ProviderManageModelInterface providerManageModel;
    private ProviderControllerInterface providerController;

    private DefaultTableModel tableProviderModel;

    private EditState editState;

    public ProviderPanel() {
        initComponents();

        this.tableProviderModel = (DefaultTableModel) tableProvider.getModel();
        this.editState = null;

        createView();
        createControl();
    }

    public void setProviderManageModel(ProviderManageModelInterface model) {
        if (model == null) {
            throw new NullPointerException("Provider model is null object.");
        }
        this.providerManageModel = model;
        this.providerManageModel.registerInsertedProviderObserver(this);
        this.providerManageModel.registerModifiedProviderObserver(this);
        this.providerManageModel.registerRemovedProviderObserver(this);

        resetProviderList();
    }

    public void setProviderController(ProviderControllerInterface controller) {
        if (controller == null) {
            throw new NullPointerException("Provider controller is null object.");
        }
        this.providerController = controller;
    }

    private void createView() {
        textfProviderID.setEditable(false);
        resetProviderInput();
        setProviderInputEditable(false);
        UIControl.setDefaultTableHeader(tableProvider);
    }

    private void createControl() {
        btnSearchClear.addActionListener(this);
        btnAdd.addActionListener(this);
        btnModify.addActionListener(this);
        btnRemove.addActionListener(this);
        btnMore.addActionListener(this);
        mnImport.addActionListener(this);
        mnExport.addActionListener(this);
        mnTemplate.addActionListener(this);
        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);
        btnReset.addActionListener(this);

        tableProvider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                providerController.requestShowProviderInfo();
            }
        });

        textfSearchName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String searchText = textfSearchName.getText().trim();
                Iterator<ProviderModelInterface> resultIterator = providerController.getProviderBySearchName(searchText);
                showProviderList(resultIterator);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String searchText = textfSearchName.getText().trim();
                if (searchText.isEmpty()) {
                    resetProviderList();
                } else {
                    Iterator<ProviderModelInterface> resultIterator = providerController.getProviderBySearchName(searchText);
                    showProviderList(resultIterator);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public EditState getEditState() {
        return this.editState;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JTable getTableProvider() {
        return tableProvider;
    }

    private void showProviderList(Iterator<ProviderModelInterface> iterator) {
        clearTableProvider();
        while (iterator.hasNext()) {
            addRowTableProvider(iterator.next());
        }
    }

    public void clearTableProvider() {
        this.tableProviderModel.setRowCount(0);
    }

    private void addRowTableProvider(ProviderModelInterface provider) {
        Object[] record = new Object[]{
            provider.getProviderIDText(),
            provider.getName(),
            provider.getEmail(),
            provider.getPhoneNum(),
            provider.getAddress()
        };
        this.tableProviderModel.addRow(record);
    }

    private void updateRowTableProvider(ProviderModelInterface updatedProvider) {
        for (int i = 0; i < this.tableProviderModel.getRowCount(); i++) {
            String providerIDInTable = (String) this.tableProviderModel.getValueAt(i, PROVIDER_ID_COLUMN_INDEX);
            if (providerIDInTable.equals(updatedProvider.getProviderIDText())) {
                Object[] record = new Object[]{
                    updatedProvider.getProviderIDText(),
                    updatedProvider.getName(),
                    updatedProvider.getEmail(),
                    updatedProvider.getPhoneNum(),
                    updatedProvider.getAddress()
                };
                for (int j = 0; j < record.length; j++) {
                    this.tableProviderModel.setValueAt(record[j], i, j);
                }
                break;
            }
        }
    }

    private void removeRowTableProvider(ProviderModelInterface provider) {
        if (this.providerController.deleteProviderInSearchList(provider)) {
            String providerIDText = provider.getProviderIDText();
            for (int i = 0; i < this.tableProviderModel.getRowCount(); i++) {
                String providerIDInTable = (String) this.tableProviderModel.getValueAt(i, PROVIDER_ID_COLUMN_INDEX);
                if (providerIDInTable.equals(providerIDText)) {
                    this.tableProviderModel.removeRow(i);
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

    public void resetProviderInput() {
        textfProviderName.setText("");
        textfAddress.setText("");
        textfEmail.setText("");
        textfPhoneNum.setText("");
    }

    public void showProviderInfo(ProviderModelInterface provider) {
        this.textfProviderID.setText(provider.getProviderIDText());
        this.textfProviderName.setText(provider.getName());
        this.textfAddress.setText(provider.getAddress());
        this.textfEmail.setText(provider.getEmail());
        this.textfPhoneNum.setText(provider.getPhoneNum());
    }

    public int getTableProviderRowCount() {
        return tableProviderModel.getRowCount();
    }

    public String getProviderIDtext() {
        return this.textfProviderID.getText();
    }

    public String getProviderName() {
        return this.textfProviderName.getText().trim();
    }

    public String getProviderEmai() {
        return this.textfEmail.getText().trim();
    }

    public String getProviderAddress() {
        return this.textfAddress.getText().trim();
    }

    public String getProviderPhoneNum() {
        return this.textfPhoneNum.getText().trim();
    }

    public int getSelectedRow() {
        return this.tableProvider.getSelectedRow();
    }

    public void showCardOption() {
        CardLayout cardLayout = (CardLayout) panelCard.getLayout();
        cardLayout.show(panelCard, panelBtnOption.getName());
    }

    public void showCardFunction() {
        CardLayout cardLayout = (CardLayout) panelCard.getLayout();
        cardLayout.show(panelCard, panelBtnFunction.getName());
    }

    public void setProviderInputEditable(boolean editable) {
        textfAddress.setEditable(editable);
        textfEmail.setEditable(editable);
        textfProviderName.setEditable(editable);
        textfPhoneNum.setEditable(editable);
    }

    public void resetProviderList() {
        Iterator<ProviderModelInterface> iterator = this.providerController.getAllProviderData();
        showProviderList(iterator);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnSearchClear) {
            if (!this.textfSearchName.getText().isEmpty()) {
                setTextfSearch("");
                resetProviderList();
                resetProviderInput();
            }
        } else if (source == btnAdd) {
            resetProviderInput();
            setProviderInputEditable(true);
            if (btnAdd.getText().equals("Add")) {
                btnOK.setText("Add");
            } else {
                btnOK.setText("Thêm");
            }
            showCardOption();
            String nextProviderIDText = this.providerManageModel.getNextProviderIDText();
            this.textfProviderID.setText(nextProviderIDText);
            this.editState = EditState.ADD;
        } else if (source == btnModify) {
            String providerIDText = textfProviderID.getText();
            if (providerIDText.isEmpty()) {
                showInfoMessage("You should choose one provider first.");
            } else {
                setProviderInputEditable(true);
                if (btnModify.getText().equals("Modify")) {
                    btnOK.setText("Save");
                } else {
                    btnOK.setText("Lưu");
                }
                showCardOption();
                this.editState = EditState.MODIFY;
            }
        } else if (source == btnRemove) {
            String providerIDText = textfProviderID.getText();
            if (providerIDText.isEmpty()) {
                showInfoMessage("You should choose one provider first.");
            } else {
                int ret = JOptionPane.showConfirmDialog(mainFrame,
                        "Remove provider?", "Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (ret == JOptionPane.YES_OPTION) {
                    this.providerController.requestRemoveProvider();
                }
            }
        } else if (source == btnMore) {
            popupBtnMore.show(btnMore, 0, btnMore.getY() + btnMore.getHeight());
        } else if (source == mnImport) {
            this.providerController.requestImportExcel();
        } else if (source == mnExport) {
            this.providerController.requestExportExcel();
        } else if (source == mnTemplate) {
            this.providerController.requestCreateTemplateExcel();
        } else if (source == btnOK) {
            switch (editState) {
                case ADD: {
                    this.providerController.requestCreateProvider();
                    break;
                }
                case MODIFY: {
                    this.providerController.requestUpdateProvider();
                    break;
                }
            }
        } else if (source == btnCancel) {
            exitEditState();
        } else if (source == btnReset) {
            resetProviderInput();
        }
    }

    public void exitEditState() {
        if (this.editState == EditState.ADD) {
            this.textfProviderID.setText("");
            resetProviderInput();
        }
        this.editState = null;
        setProviderInputEditable(false);
        showCardFunction();
        this.providerController.requestShowProviderInfo();
    }

    public void setEditState(EditState editState) {
        this.editState = editState;
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
    public void updateInsertedProviderObserver(ProviderModelInterface insertedProvider) {
        String searchText = textfSearchName.getText().trim();
        if (this.providerController.isSearchMatching(searchText, insertedProvider)) {
            if (searchText.isEmpty()) {
                addRowTableProvider(insertedProvider);
            } else {
                showProviderList(providerController.getProviderBySearchName(searchText));
            }
        }
        this.tableProvider.repaint();
    }

    @Override
    public void updateModifiedProviderObserver(ProviderModelInterface updatedProvider) {
        // Update record in table if exist
        updateRowTableProvider(updatedProvider);
    }

    @Override
    public void updateRemovedProviderObserver(ProviderModelInterface provider) {
        removeRowTableProvider(provider);
    }

    @Override
    public void updateSettingObserver() {
        AppSetting.Language language = AppSetting.getInstance().getAppLanguage();

        switch (language) {
            case ENGLISH: {
                labelName.setText("Name:");
                labelPhoneNum.setText("Mobile:");
                labelAddress.setText("Address:");
                labelSearchProvider.setText("Search name:");
                btnSearchClear.setText("Clear");
                btnAdd.setText("Add");
                btnModify.setText("Modify");
                btnRemove.setText("Remove");
                btnMore.setText("More ▼");
                btnReset.setText("Reset");
                btnCancel.setText("Cancel");
                mnExport.setText("Export Excel flie");
                mnImport.setText("Import Excel file");
                mnTemplate.setText("Create template");

                TableColumnModel tableProviderColumnModel = tableProvider.getColumnModel();
                tableProviderColumnModel.getColumn(PROVIDER_NAME_COLUMN_INDEX).setHeaderValue("Name");
                tableProviderColumnModel.getColumn(PROVIDER_PHONE_NUM_COLUMN_INDEX).setHeaderValue("Mobile");
                tableProviderColumnModel.getColumn(PROVIDER_ADDRESS_COLUMN_INDEX).setHeaderValue("Address");

                break;
            }
            case VIETNAMESE: {
                labelName.setText("Ten6:");
                labelPhoneNum.setText("Số điện thoại:");
                labelAddress.setText("Địa chỉ:");
                labelSearchProvider.setText("Tìm theo tên:");
                btnSearchClear.setText("Xóa");
                btnAdd.setText("Thêm");
                btnModify.setText("Chỉnh sửa");
                btnRemove.setText("Xóa");
                btnMore.setText("Khác ▼");
                btnReset.setText("Làm mới");
                btnCancel.setText("Thoát");
                mnExport.setText("Xuất file Excel");
                mnImport.setText("Nhập file Excel");
                mnTemplate.setText("Tạo biểu mẫu");

                TableColumnModel tableProviderColumnModel = tableProvider.getColumnModel();
                tableProviderColumnModel.getColumn(PROVIDER_NAME_COLUMN_INDEX).setHeaderValue("Name");
                tableProviderColumnModel.getColumn(PROVIDER_PHONE_NUM_COLUMN_INDEX).setHeaderValue("Mobile");
                tableProviderColumnModel.getColumn(PROVIDER_ADDRESS_COLUMN_INDEX).setHeaderValue("Address");

                break;
            }
        }

        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupBtnMore = new javax.swing.JPopupMenu();
        mnImport = new javax.swing.JMenuItem();
        mnExport = new javax.swing.JMenuItem();
        mnTemplate = new javax.swing.JMenuItem();
        panelProviderInfo = new javax.swing.JPanel();
        label_provID4 = new javax.swing.JLabel();
        textfProviderID = new javax.swing.JTextField();
        labelName = new javax.swing.JLabel();
        labelEmail = new javax.swing.JLabel();
        labelPhoneNum = new javax.swing.JLabel();
        textfPhoneNum = new javax.swing.JTextField();
        labelAddress = new javax.swing.JLabel();
        textfProviderName = new javax.swing.JTextField();
        textfEmail = new javax.swing.JTextField();
        textfAddress = new javax.swing.JTextField();
        scrpaneTable = new javax.swing.JScrollPane();
        tableProvider = new javax.swing.JTable();
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
        jPanel3 = new javax.swing.JPanel();
        labelSearchProvider = new javax.swing.JLabel();
        textfSearchName = new javax.swing.JTextField();
        btnSearchClear = new javax.swing.JButton();

        mnImport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        mnImport.setText("Import");
        popupBtnMore.add(mnImport);

        mnExport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        mnExport.setText("Export");
        popupBtnMore.add(mnExport);

        mnTemplate.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        mnTemplate.setText("Template");
        popupBtnMore.add(mnTemplate);

        setBackground(new java.awt.Color(255, 255, 255));
        setName("Provider"); // NOI18N

        panelProviderInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelProviderInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Provider Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        label_provID4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_provID4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_provID4.setText(" ID");

        textfProviderID.setEditable(false);
        textfProviderID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfProviderID.setEnabled(false);
        textfProviderID.setPreferredSize(new java.awt.Dimension(160, 30));

        labelName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelName.setText("Name");

        labelEmail.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelEmail.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelEmail.setText("Email");

        labelPhoneNum.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelPhoneNum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelPhoneNum.setText("Mobile");

        textfPhoneNum.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfPhoneNum.setPreferredSize(new java.awt.Dimension(160, 30));

        labelAddress.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelAddress.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelAddress.setText("Address");

        textfProviderName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfEmail.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfAddress.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout panelProviderInfoLayout = new javax.swing.GroupLayout(panelProviderInfo);
        panelProviderInfo.setLayout(panelProviderInfoLayout);
        panelProviderInfoLayout.setHorizontalGroup(
            panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProviderInfoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_provID4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelProviderInfoLayout.createSequentialGroup()
                        .addComponent(textfProviderID, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(labelPhoneNum)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textfPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textfProviderName))
                .addGap(30, 30, 30)
                .addGroup(panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textfEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                    .addComponent(textfAddress))
                .addContainerGap())
        );
        panelProviderInfoLayout.setVerticalGroup(
            panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProviderInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfProviderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_provID4)
                    .addComponent(labelPhoneNum)
                    .addComponent(textfPhoneNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEmail)
                    .addComponent(textfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelName)
                    .addComponent(labelAddress)
                    .addComponent(textfProviderName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tableProvider.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Email", "Mobile", "Address"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        tableProvider.getTableHeader().setReorderingAllowed(false);
        scrpaneTable.setViewportView(tableProvider);

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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 30));

        labelSearchProvider.setBackground(new java.awt.Color(255, 255, 255));
        labelSearchProvider.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelSearchProvider.setText("Search name:");
        labelSearchProvider.setOpaque(true);
        labelSearchProvider.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel3.add(labelSearchProvider);

        textfSearchName.setPreferredSize(new java.awt.Dimension(250, 30));
        jPanel3.add(textfSearchName);

        btnSearchClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSearchClear.setForeground(new java.awt.Color(51, 51, 51));
        btnSearchClear.setText("Clear");
        btnSearchClear.setFocusPainted(false);
        btnSearchClear.setPreferredSize(new java.awt.Dimension(90, 30));
        jPanel3.add(btnSearchClear);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelProviderInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrpaneTable, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 270, Short.MAX_VALUE)
                        .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProviderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrpaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnMore;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelAddress;
    private javax.swing.JLabel labelEmail;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelPhoneNum;
    private javax.swing.JLabel labelSearchProvider;
    private javax.swing.JLabel label_provID4;
    private javax.swing.JMenuItem mnExport;
    private javax.swing.JMenuItem mnImport;
    private javax.swing.JMenuItem mnTemplate;
    private javax.swing.JPanel panelBtnFunction;
    private javax.swing.JPanel panelBtnOption;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel panelProviderInfo;
    private javax.swing.JPopupMenu popupBtnMore;
    private javax.swing.JScrollPane scrpaneTable;
    private javax.swing.JTable tableProvider;
    private javax.swing.JTextField textfAddress;
    private javax.swing.JTextField textfEmail;
    private javax.swing.JTextField textfPhoneNum;
    private javax.swing.JTextField textfProviderID;
    private javax.swing.JTextField textfProviderName;
    private javax.swing.JTextField textfSearchName;
    // End of variables declaration//GEN-END:variables

}
