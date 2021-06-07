package view.function.provider;

import control.provider.ProviderControllerInterface;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.provider.ProviderManageModelInterface;
import model.provider.ProviderModelInterface;
import util.swing.UIControl;
import view.MessageShowing;
import view.main.MainFrame;

public class ProviderPanel extends javax.swing.JPanel implements ActionListener,
        MessageShowing, InsertedProviderObserver, ModifiedProviderObserver {

    public static final int PROVIDER_ID_COLUMN_INDEX = 0;
    
    enum EditState {
        ADD,
        MODIFY,
    }

    private volatile static ProviderPanel uniqueInstance;

    private ProviderManageModelInterface model;
    private ProviderControllerInterface controller;

    private DefaultTableModel tableProviderModel;

    private EditState editState;

    private ProviderPanel(ProviderManageModelInterface model,
            ProviderControllerInterface controller) {
        if (model == null) {
            throw new IllegalArgumentException("Provider model is null object.");
        }
        if (controller == null) {
            throw new IllegalArgumentException("Provider controller is null object.");
        }

        initComponents();

        this.model = model;
        this.model.registerInsertedProviderObserver(this);
        this.model.registerModifiedProviderObserver(this);

        this.controller = controller;

        this.tableProviderModel = (DefaultTableModel) tableProvider.getModel();
        this.editState = null;

        createView();
        createControl();
    }

    public static ProviderPanel getInstance(ProviderManageModelInterface model,
            ProviderControllerInterface controller) {
        if (uniqueInstance == null) {
            synchronized (ProviderPanel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ProviderPanel(model, controller);
                }
            }
        }
        return uniqueInstance;
    }

    public static ProviderPanel getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

    private void createView() {
        textfProviderID.setEditable(false);
        resetProviderInput();
        setProviderInputEditable(false);
        UIControl.setDefaultTableHeader(tableProvider);
        resetProviderList();
    }

    private void createControl() {
        btnSearchClear.addActionListener(this);
        btnAdd.addActionListener(this);
        btnModify.addActionListener(this);
        btnRemove.addActionListener(this);
        btnMore.addActionListener(this);
        mnImport.addActionListener(this);
        mnExport.addActionListener(this);
        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);
        btnReset.addActionListener(this);

        tableProvider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.requestShowProviderInfo();
            }
        });

        textfSearchName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String searchText = textfSearchName.getText().trim();
                Iterator<ProviderModelInterface> resultIterator = controller.getProviderBySearchName(searchText);
                showProviderList(resultIterator);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String searchText = textfSearchName.getText().trim();
                if (searchText.isEmpty()) {
                    resetProviderList();
                } else {
                    Iterator<ProviderModelInterface> resultIterator = controller.getProviderBySearchName(searchText);
                    showProviderList(resultIterator);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
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
    
    private void replaceRowTableProvider(int rowID, ProviderModelInterface provider) {
        Object[] record = new Object[]{
            provider.getProviderIDText(),
            provider.getName(),
            provider.getEmail(),
            provider.getPhoneNum(),
            provider.getAddress()
        };
        for (int i = 0; i < record.length; i++) {
            this.tableProviderModel.setValueAt(record[i], rowID, i);
        }
        this.tableProvider.repaint();
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
        if (editState == EditState.ADD) {
            return;
        }

        this.textfProviderID.setText(provider.getProviderIDText());
        this.textfProviderName.setText(provider.getName());
        this.textfAddress.setText(provider.getAddress());
        this.textfEmail.setText(provider.getEmail());
        this.textfPhoneNum.setText(provider.getPhoneNum());
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
        Iterator<ProviderModelInterface> iterator = this.controller.getAllProviderData();
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
            btnOK.setText("Add");
            showCardOption();
            String nextProviderIDText = this.model.getNextProviderIDText();
            this.textfProviderID.setText(nextProviderIDText);
            this.editState = EditState.ADD;
        } else if (source == btnModify) {
            String providerIDText = textfProviderID.getText();
            if (providerIDText.isEmpty()) {
                showInfoMessage("You should choose one provider first.");
            } else {
                setProviderInputEditable(true);
                btnOK.setText("Save");
                showCardOption();
                this.editState = EditState.MODIFY;
            }
        } else if (source == btnRemove) {
            String providerIDText = textfProviderID.getText();
            if (providerIDText.isEmpty()) {
                showInfoMessage("You should choose one provider first.");
            } else {
                int ret = JOptionPane.showConfirmDialog(MainFrame.getInstance(),
                        "Remove provider?", "Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (ret == JOptionPane.YES_OPTION) {
                    this.controller.requestRemoveProvider();
                }
            }
        } else if (source == btnMore) {
            popupBtnMore.show(btnMore, 0, btnMore.getY() + btnMore.getHeight());
        } else if (source == mnImport) {
            // XXX

        } else if (source == mnExport) {
            if (this.tableProviderModel.getRowCount() == 0) {
                showErrorMessage("Table provider data is empty.");
            } else {
                // XXX

            }
        } else if (source == btnOK) {
            switch (editState) {
                case ADD: {
                    this.controller.requestCreateProvider();
                    break;
                }
                case MODIFY: {
                    this.controller.requestUpdateProvider();
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
        this.editState = null;
        setProviderInputEditable(false);
        showCardFunction();
        this.controller.requestShowProviderInfo();
    }

    public void setEditState(EditState editState) {
        this.editState = editState;
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
    public void updateInsertedProvider(ProviderModelInterface insertedProvider) {
        String searchText = textfSearchName.getText().trim();
        if (searchText.isEmpty()) {
            addRowTableProvider(insertedProvider);
        } else {
            // Check match and insert one row, insert to search buffer in controller
            if (this.controller.insertToSearchListByMatchingName(searchText, insertedProvider)) {
                addRowTableProvider(insertedProvider);
            }
        }
        this.tableProvider.repaint();
    }
    
    @Override
    public void updateModifiedProvider(ProviderModelInterface updatedProvider) {
        System.out.println("view.function.provider.ProviderPanel.updateModifiedProvider()");
        // Update record in table if exist
        for (int i = 0; i < this.tableProviderModel.getRowCount(); i++) {
            String providerIDInTable = (String) this.tableProviderModel.getValueAt(i, PROVIDER_ID_COLUMN_INDEX);
            if (providerIDInTable.equals(updatedProvider.getProviderIDText())) {
                replaceRowTableProvider(i, updatedProvider);
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupBtnMore = new javax.swing.JPopupMenu();
        mnImport = new javax.swing.JMenuItem();
        mnExport = new javax.swing.JMenuItem();
        panelProviderInfo = new javax.swing.JPanel();
        label_provID4 = new javax.swing.JLabel();
        textfProviderID = new javax.swing.JTextField();
        label_provName4 = new javax.swing.JLabel();
        label_provEmail4 = new javax.swing.JLabel();
        label_provMobile4 = new javax.swing.JLabel();
        textfPhoneNum = new javax.swing.JTextField();
        label_provAdd4 = new javax.swing.JLabel();
        textfProviderName = new javax.swing.JTextField();
        textfEmail = new javax.swing.JTextField();
        textfAddress = new javax.swing.JTextField();
        textfSearchName = new javax.swing.JTextField();
        scrpaneTable = new javax.swing.JScrollPane();
        tableProvider = new javax.swing.JTable();
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
        label_provName5 = new javax.swing.JLabel();

        mnImport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        mnImport.setText("Import");
        popupBtnMore.add(mnImport);

        mnExport.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        mnExport.setText("Export");
        popupBtnMore.add(mnExport);

        setBackground(new java.awt.Color(255, 255, 255));

        panelProviderInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelProviderInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Provider Information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        label_provID4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_provID4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_provID4.setText(" ID");

        textfProviderID.setEditable(false);
        textfProviderID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfProviderID.setEnabled(false);
        textfProviderID.setPreferredSize(new java.awt.Dimension(160, 30));

        label_provName4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_provName4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_provName4.setText("Name");

        label_provEmail4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_provEmail4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_provEmail4.setText("Email");

        label_provMobile4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_provMobile4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_provMobile4.setText("Mobile");

        textfPhoneNum.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfPhoneNum.setPreferredSize(new java.awt.Dimension(160, 30));

        label_provAdd4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_provAdd4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_provAdd4.setText("Address");

        textfProviderName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfEmail.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfAddress.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout panelProviderInfoLayout = new javax.swing.GroupLayout(panelProviderInfo);
        panelProviderInfo.setLayout(panelProviderInfoLayout);
        panelProviderInfoLayout.setHorizontalGroup(
            panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProviderInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label_provName4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_provID4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProviderInfoLayout.createSequentialGroup()
                        .addComponent(textfProviderID, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(label_provMobile4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textfPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textfProviderName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label_provAdd4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_provEmail4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(label_provMobile4)
                    .addComponent(textfPhoneNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_provEmail4)
                    .addComponent(textfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_provName4)
                    .addComponent(label_provAdd4)
                    .addComponent(textfProviderName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        textfSearchName.setPreferredSize(new java.awt.Dimension(190, 30));

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

        label_provName5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_provName5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_provName5.setText("Search name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrpaneTable, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelProviderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 406, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_provName5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textfSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProviderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_provName5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(scrpaneTable, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
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
    private javax.swing.JLabel label_provAdd4;
    private javax.swing.JLabel label_provEmail4;
    private javax.swing.JLabel label_provID4;
    private javax.swing.JLabel label_provMobile4;
    private javax.swing.JLabel label_provName4;
    private javax.swing.JLabel label_provName5;
    private javax.swing.JMenuItem mnExport;
    private javax.swing.JMenuItem mnImport;
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
