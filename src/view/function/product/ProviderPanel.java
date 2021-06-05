package view.function.product;

import control.provider.ProviderControllerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.provider.ProviderModelInterface;
import util.swing.UIControl;

public class ProviderPanel extends javax.swing.JPanel implements ActionListener {

    private volatile static ProviderPanel uniqueInstance;

    private ProviderModelInterface model;
    private ProviderControllerInterface controller;

    private ProviderPanel(ProviderModelInterface model,
            ProviderControllerInterface controller) {
        if (model == null) {
            throw new IllegalArgumentException("Provider model is null object.");
        }
        if (controller == null) {
            throw new IllegalArgumentException("Provider controller is null object.");
        }

        initComponents();
        createView();
        createControl();
    }

    public static ProviderPanel getInstance(ProviderModelInterface model,
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
        UIControl.setDefaultTableHeader(tableProduct);
        setEditableForAll(false);
    }

    private void createControl() {

    }

    public void setEditableForAll(boolean editable) {
        textfID.setEditable(false);
        textfAddress.setEditable(editable);
        textfEmail.setEditable(editable);
        textfName.setEditable(editable);
        textfPhoneNum.setEditable(editable);
    }

    public void clearAll() {
        textfAddress.setText(null);
        textfEmail.setText(null);
        textfID.setText(null);
        textfName.setText(null);
        textfPhoneNum.setText(null);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        
        if (source == btnSearchClear) {
            
        } else if (source == btnAdd) {
            
        } else if (source == btnModify) {
            
        } else if (source == btnRemove) {
            
        } else if (source == btnMore) {
            popupBtnMore.show(btnMore, 0, btnMore.getY() + btnMore.getWidth());
        } else if (source == mnImport) {
            
        } else if (source == mnExport) {
            
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
        textfID = new javax.swing.JTextField();
        label_provName4 = new javax.swing.JLabel();
        label_provEmail4 = new javax.swing.JLabel();
        label_provMobile4 = new javax.swing.JLabel();
        textfPhoneNum = new javax.swing.JTextField();
        label_provAdd4 = new javax.swing.JLabel();
        textfName = new javax.swing.JTextField();
        textfEmail = new javax.swing.JTextField();
        textfAddress = new javax.swing.JTextField();
        textfSearch = new javax.swing.JTextField();
        panelBtn = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnMore = new javax.swing.JButton();
        scrpaneTable = new javax.swing.JScrollPane();
        tableProduct = new javax.swing.JTable();
        combSearchMode = new javax.swing.JComboBox<>();
        btnSearchClear = new javax.swing.JButton();

        mnImport.setText("Import");
        popupBtnMore.add(mnImport);

        mnExport.setText("Export");
        popupBtnMore.add(mnExport);

        setBackground(new java.awt.Color(255, 255, 255));

        panelProviderInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelProviderInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Provider Information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        label_provID4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_provID4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_provID4.setText(" ID");

        textfID.setEditable(false);
        textfID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        textfID.setEnabled(false);
        textfID.setPreferredSize(new java.awt.Dimension(160, 30));

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

        textfName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

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
                        .addComponent(textfID, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(label_provMobile4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textfPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textfName))
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
                    .addComponent(textfID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_provID4)
                    .addComponent(label_provMobile4)
                    .addComponent(textfPhoneNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_provEmail4)
                    .addComponent(textfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelProviderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_provName4)
                    .addComponent(label_provAdd4)
                    .addComponent(textfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        textfSearch.setPreferredSize(new java.awt.Dimension(190, 30));

        panelBtn.setBackground(new java.awt.Color(255, 255, 255));
        panelBtn.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(51, 51, 51));
        btnAdd.setText("Add");
        btnAdd.setFocusPainted(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtn.add(btnAdd);

        btnModify.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnModify.setForeground(new java.awt.Color(51, 51, 51));
        btnModify.setText("Modify");
        btnModify.setFocusPainted(false);
        btnModify.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtn.add(btnModify);

        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnRemove.setForeground(new java.awt.Color(51, 51, 51));
        btnRemove.setText("Remove");
        btnRemove.setFocusPainted(false);
        btnRemove.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtn.add(btnRemove);

        btnMore.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnMore.setForeground(new java.awt.Color(51, 51, 51));
        btnMore.setText("More ▼");
        btnMore.setFocusPainted(false);
        btnMore.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtn.add(btnMore);

        tableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Provider ID", "Name", "Email", "Mobile", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrpaneTable.setViewportView(tableProduct);

        combSearchMode.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combSearchMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Search name", "Search ID" }));

        btnSearchClear.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSearchClear.setForeground(new java.awt.Color(51, 51, 51));
        btnSearchClear.setText("Clear");
        btnSearchClear.setFocusPainted(false);
        btnSearchClear.setPreferredSize(new java.awt.Dimension(89, 29));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrpaneTable, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(combSearchMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(panelProviderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 406, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProviderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(combSearchMode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(scrpaneTable, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnMore;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JComboBox<String> combSearchMode;
    private javax.swing.JLabel label_provAdd4;
    private javax.swing.JLabel label_provEmail4;
    private javax.swing.JLabel label_provID4;
    private javax.swing.JLabel label_provMobile4;
    private javax.swing.JLabel label_provName4;
    private javax.swing.JMenuItem mnExport;
    private javax.swing.JMenuItem mnImport;
    private javax.swing.JPanel panelBtn;
    private javax.swing.JPanel panelProviderInfo;
    private javax.swing.JPopupMenu popupBtnMore;
    private javax.swing.JScrollPane scrpaneTable;
    private javax.swing.JTable tableProduct;
    private javax.swing.JTextField textfAddress;
    private javax.swing.JTextField textfEmail;
    private javax.swing.JTextField textfID;
    private javax.swing.JTextField textfName;
    private javax.swing.JTextField textfPhoneNum;
    private javax.swing.JTextField textfSearch;
    // End of variables declaration//GEN-END:variables

}
