package view.function.product;

import control.ingredient.IngredientControllerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.ingredient.IngredientModelInterface;
import util.swing.UIControl;

public class IngredientPanel extends javax.swing.JPanel implements ActionListener {

    private volatile static IngredientPanel uniqueInstance;
    
    private IngredientModelInterface model;
    private IngredientControllerInterface controller;

    private IngredientPanel(IngredientModelInterface model, 
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
        
        createView();
        createControl();
    }
    
    public static IngredientPanel getInstance(IngredientModelInterface model, 
            IngredientControllerInterface controller) {
        if (uniqueInstance == null) {
            synchronized(IngredientPanel.class) {
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
        setEditableForAll(false);
        UIControl.setDefaultTableHeader(tableIngredient);
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
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        
        if (source == btnCreateIngredientType) {
            
        } else if (source == btnSearchClear) {
            
        } else if (source == btnAdd) {
            
        } else if (source == btnModify) {
            
        } else if (source == btnRemove) {
            
        } else if (source == btnMore) {
            
        } else if (source == btnRequestImport) {
            
        } else if (source == btnShowImportHistory) {
            
        } else if (source == mnImport) {
            
        } else if (source == mnExport) {
            
        }
    }

    public void setEditableForAll(boolean editable) {
        textfIngredientID.setEditable(false);
        textfIngredientCost.setEditable(editable);
        textfIngredientName.setEditable(editable);
        textfProviderName.setEditable(editable);
        combIngredientTypeName.setSelectedIndex(0);

    }

    public void clearAll() {
        textfIngredientCost.setText(null);
        textfIngredientID.setText(null);
        textfIngredientName.setText(null);
        textfProviderName.setText(null);
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
        textfProviderName = new javax.swing.JTextField();
        label_Search = new javax.swing.JLabel();
        textfSearchName = new javax.swing.JTextField();
        scrpaneIngredient = new javax.swing.JScrollPane();
        tableIngredient = new javax.swing.JTable();
        panelBtn = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnMore = new javax.swing.JButton();
        btnRequestImport = new javax.swing.JButton();
        btnShowImportHistory = new javax.swing.JButton();
        btnSearchClear = new javax.swing.JButton();

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
        label_ingreType.setText("Ingredient Type");

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

        textfProviderName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

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
                        .addGap(461, 461, 461)
                        .addComponent(btnCreateIngredientType))
                    .addComponent(textfIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelIngredientInfoLayout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addGroup(panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelIngredientInfoLayout.createSequentialGroup()
                                .addComponent(label_ingreType)
                                .addGap(18, 18, 18)
                                .addComponent(combIngredientTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelIngredientInfoLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(label_IngredientProvider)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfProviderName, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(label_ingreCost)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textfIngredientCost, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelIngredientInfoLayout.setVerticalGroup(
            panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIngredientInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCreateIngredientType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_ingreID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfIngredientID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(combIngredientTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_ingreType)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelIngredientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_ingreName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_IngredientProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfProviderName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_ingreCost, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfIngredientCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        label_Search.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_Search.setText("Search name:");

        textfSearchName.setPreferredSize(new java.awt.Dimension(190, 30));

        tableIngredient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "ID", "Name", "Cost", "Type", "Total amount", "Unit", "Provider"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrpaneIngredient.setViewportView(tableIngredient);

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
                        .addGap(385, 385, 385)
                        .addComponent(panelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelIngredientInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(btnRequestImport)
                .addGap(138, 138, 138)
                .addComponent(btnShowImportHistory)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelIngredientInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSearchClear, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textfSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scrpaneIngredient, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRequestImport)
                    .addComponent(btnShowImportHistory))
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCreateIngredientType;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnMore;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRequestImport;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JButton btnShowImportHistory;
    private javax.swing.JComboBox<String> combIngredientTypeName;
    private javax.swing.JLabel label_IngredientProvider;
    private javax.swing.JLabel label_Search;
    private javax.swing.JLabel label_ingreCost;
    private javax.swing.JLabel label_ingreID;
    private javax.swing.JLabel label_ingreName;
    private javax.swing.JLabel label_ingreType;
    private javax.swing.JMenuItem mnExport;
    private javax.swing.JMenuItem mnImport;
    private javax.swing.JPanel panelBtn;
    private javax.swing.JPanel panelIngredientInfo;
    private javax.swing.JPopupMenu popupBtnMore;
    private javax.swing.JScrollPane scrpaneIngredient;
    private javax.swing.JTable tableIngredient;
    private javax.swing.JTextField textfIngredientCost;
    private javax.swing.JTextField textfIngredientID;
    private javax.swing.JTextField textfIngredientName;
    private javax.swing.JTextField textfProviderName;
    private javax.swing.JTextField textfSearchName;
    // End of variables declaration//GEN-END:variables

}
