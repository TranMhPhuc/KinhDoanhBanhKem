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
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.provider.ProviderManageModelInterface;
import model.provider.ProviderModelInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import util.constant.AppConstant;
import util.messages.Messages;
import util.swing.UIControl;
import util.validator.PhoneValidator;
import view.MessageShowing;

public class ProviderPanel extends javax.swing.JPanel implements ActionListener,
        MessageShowing, InsertedProviderObserver, ModifiedProviderObserver,
        RemovedProviderObserver, SettingUpdateObserver {

    public static final int PROVIDER_ID_COLUMN_INDEX = 0;
    public static final int PROVIDER_NAME_COLUMN_INDEX = 1;
    public static final int PROVIDER_PHONE_NUM_COLUMN_INDEX = 3;
    public static final int PROVIDER_EMAIL_COLUMN_INDEX = 2;
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
        addPhoneNumConstraint();
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
        UIControl.setColumnWidth(tableProvider, 0, 110);
    }

    private void createControl() {
        btnSearchClear.addActionListener(this);
        btnAdd.addActionListener(this);
        btnModify.addActionListener(this);
        btnRemove.addActionListener(this);
        btnExport.addActionListener(this);
        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);
        btnReset.addActionListener(this);

        tableProvider.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
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

    private void addPhoneNumConstraint() {
        labelSettingPhoneNum.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (labelSettingPhoneNum.isEnabled()) {
                    providerController.requestChangePhoneNumConstraint();
                }
            }
        });
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
//        try {
//            textfPhoneNum.commitEdit();
//        } catch (ParseException ex) {
//            Logger.getLogger(EmployeePanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        String phoneNum = String.valueOf(this.textfPhoneNum.getValue());
//        return phoneNum.replaceAll("-", "");
        return textfPhoneNum.getText().trim();
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
        labelSettingPhoneNum.setEnabled(editable);
    }

    public void resetProviderList() {
        Iterator<ProviderModelInterface> iterator = this.providerController.getAllProviderData();
        showProviderList(iterator);
    }

    public void setProviderIDInput(String providerIDText) {
        textfProviderID.setText(providerIDText);
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
                showInfoMessage(Messages.getInstance().PROVIDER_NO_PROVIDER_CHOSEN);
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
                showInfoMessage(Messages.getInstance().PROVIDER_NO_PROVIDER_CHOSEN);
            } else {
                int ret = JOptionPane.showConfirmDialog(mainFrame,
                        Messages.getInstance().OTHERS_REMOVE_PROVIDER, "BakeryMS", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/warning.png")));
                if (ret == JOptionPane.YES_OPTION) {
                    this.providerController.requestRemoveProvider();
                }
            }
        } else if (source == btnExport) {
            this.providerController.requestExportExcel();
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
        PhoneValidator.setValidDigitNum(10);
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
        if (insertedProvider == null) {
            throw new NullPointerException();
        }
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
                btnExport.setText("Export");
                btnReset.setText("Reset");
                btnCancel.setText("Cancel");

                TableColumnModel tableProviderColumnModel = tableProvider.getColumnModel();
                tableProviderColumnModel.getColumn(PROVIDER_NAME_COLUMN_INDEX).setHeaderValue("Name");
                tableProviderColumnModel.getColumn(PROVIDER_PHONE_NUM_COLUMN_INDEX).setHeaderValue("Mobile");
                tableProviderColumnModel.getColumn(PROVIDER_ADDRESS_COLUMN_INDEX).setHeaderValue("Address");

                TitledBorder titledBorder = (TitledBorder) panelInfo.getBorder();
                titledBorder.setTitle("Provider information");

                break;
            }
            case VIETNAMESE: {
                labelName.setText("Tên:");
                labelPhoneNum.setText("Số điện thoại:");
                labelAddress.setText("Địa chỉ:");
                labelSearchProvider.setText("Tìm theo tên:");
                btnSearchClear.setText("Xóa");
                btnAdd.setText("Thêm");
                btnModify.setText("Chỉnh sửa");
                btnRemove.setText("Xóa");
                btnExport.setText("Xuất");
                btnReset.setText("Làm mới");
                btnCancel.setText("Thoát");

                TableColumnModel tableProviderColumnModel = tableProvider.getColumnModel();
                tableProviderColumnModel.getColumn(PROVIDER_NAME_COLUMN_INDEX).setHeaderValue("Name");
                tableProviderColumnModel.getColumn(PROVIDER_PHONE_NUM_COLUMN_INDEX).setHeaderValue("Mobile");
                tableProviderColumnModel.getColumn(PROVIDER_ADDRESS_COLUMN_INDEX).setHeaderValue("Address");

                TitledBorder titledBorder = (TitledBorder) panelInfo.getBorder();
                titledBorder.setTitle("Thông tin nhà cung cấp");

                break;
            }
        }

        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelInfo = new javax.swing.JPanel();
        label_provID4 = new javax.swing.JLabel();
        textfProviderID = new javax.swing.JTextField();
        labelName = new javax.swing.JLabel();
        labelEmail = new javax.swing.JLabel();
        labelPhoneNum = new javax.swing.JLabel();
        labelAddress = new javax.swing.JLabel();
        textfProviderName = new javax.swing.JTextField();
        textfEmail = new javax.swing.JTextField();
        textfAddress = new javax.swing.JTextField();
        textfPhoneNum = new javax.swing.JTextField();
        labelSettingPhoneNum = new javax.swing.JLabel();
        scrpaneTable = new javax.swing.JScrollPane();
        tableProvider = new javax.swing.JTable();
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
        labelSearchProvider = new javax.swing.JLabel();
        textfSearchName = new javax.swing.JTextField();
        btnSearchClear = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setName("Provider"); // NOI18N

        panelInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Provider Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(153, 153, 153))); // NOI18N

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

        labelAddress.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelAddress.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelAddress.setText("Address");

        textfProviderName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfEmail.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfAddress.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfPhoneNum.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        labelSettingPhoneNum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Slider_20px_1.png"))); // NOI18N
        labelSettingPhoneNum.setEnabled(false);

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_provID4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(textfProviderID, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(labelPhoneNum)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textfPhoneNum))
                    .addComponent(textfProviderName, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelSettingPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textfEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                    .addComponent(textfAddress))
                .addContainerGap())
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textfPhoneNum, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelSettingPhoneNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textfProviderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_provID4)
                        .addComponent(labelPhoneNum)
                        .addComponent(labelEmail)
                        .addComponent(textfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelName)
                    .addComponent(labelAddress)
                    .addComponent(textfProviderName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tableProvider.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
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
        tableProvider.setSelectionBackground(new java.awt.Color(113, 168, 255));
        tableProvider.getTableHeader().setReorderingAllowed(false);
        scrpaneTable.setViewportView(tableProvider);
        if (tableProvider.getColumnModel().getColumnCount() > 0) {
            tableProvider.getColumnModel().getColumn(0).setMinWidth(7);
            tableProvider.getColumnModel().getColumn(0).setPreferredWidth(7);
            tableProvider.getColumnModel().getColumn(1).setMinWidth(170);
            tableProvider.getColumnModel().getColumn(1).setPreferredWidth(170);
            tableProvider.getColumnModel().getColumn(2).setMinWidth(170);
            tableProvider.getColumnModel().getColumn(2).setPreferredWidth(170);
            tableProvider.getColumnModel().getColumn(4).setMinWidth(170);
            tableProvider.getColumnModel().getColumn(4).setPreferredWidth(170);
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
                    .addComponent(panelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrpaneTable, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                        .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
                .addComponent(scrpaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnModify;
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
    private javax.swing.JLabel labelSettingPhoneNum;
    private javax.swing.JLabel label_provID4;
    private javax.swing.JPanel panelBtnFunction;
    private javax.swing.JPanel panelBtnOption;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel panelInfo;
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
