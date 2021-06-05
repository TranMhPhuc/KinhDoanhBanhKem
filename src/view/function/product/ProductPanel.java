package view.function.product;

import control.product.ProductControllerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import model.product.ProductModelInterface;
import util.swing.UIControl;

public class ProductPanel extends javax.swing.JPanel implements ActionListener {

    private volatile static ProductPanel uniqueInstance;

    private ProductModelInterface model;
    private ProductControllerInterface controller;

    private ProductPanel(ProductModelInterface model, 
            ProductControllerInterface controller) {
        if (model == null) {
            throw new IllegalArgumentException("Product model is null object.");
        }
        if (controller == null) {
            throw new IllegalArgumentException("Product controller is null object.");
        }
        
        initComponents();
        createView();
        createControl();
    }

    public static ProductPanel getInstance(ProductModelInterface model, 
            ProductControllerInterface controller) {
        if (uniqueInstance == null) {
            synchronized(ProductPanel.class) {
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
        UIControl.setDefaultTableHeader(tableProduct);
        setEditableForAll(false);
    }

    private void createControl() {
        btnSelectIngredient.addActionListener(this);
        btnSearchClear.addActionListener(this);
        btnAdd.addActionListener(this);
        btnModify.addActionListener(this);
        btnRemove.addActionListener(this);
        btnMore.addActionListener(this);
        mnImport.addActionListener(this);
        mnExport.addActionListener(this);
        btnRequestImport.addActionListener(this);
    }

    public void setEditableForAll(boolean editable) {
        textfProductID.setEditable(false);
        textfProductCost.setEditable(editable);
        textfProductName.setEditable(editable);
        combProductSize.setEnabled(editable);
        spinnerProductAmount.setEnabled(editable);
        listIngredient.setEnabled(editable);

    }

    public void clearAll() {
        textfProductID.setText(null);
        textfProductCost.setText(null);
        textfProductName.setText(null);
        textfSearch.setText(null);
        combProductSize.setSelectedIndex(0);
        listIngredient.clearSelection();
        spinnerProductAmount.setValue((int) 0);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        
        if (source == btnSelectIngredient) {
            
        } else if (source == btnSearchClear) {
            
        } else if (source == btnAdd) {
            
        } else if (source == btnModify) {
            
        } else if (source == btnRemove) {
            
        } else if (source == btnMore) {
            
        } else if (source == mnImport) {
            
        } else if (source == mnExport) {
            
        } else if (source == btnRequestImport) {
            
        }
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
        btnSelectIngredient = new javax.swing.JButton();
        scrpaneList = new javax.swing.JScrollPane();
        listIngredient = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        textfProductName = new javax.swing.JTextField();
        textfProductCost = new javax.swing.JTextField();
        label_searchProduct = new javax.swing.JLabel();
        textfSearch = new javax.swing.JTextField();
        scrpaneTable = new javax.swing.JScrollPane();
        tableProduct = new javax.swing.JTable();
        panelBtn = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnMore = new javax.swing.JButton();
        btnRequestImport = new javax.swing.JButton();
        btnSearchClear = new javax.swing.JButton();

        mnImport.setText("Import");
        popupBtnMore.add(mnImport);

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
        label_ingredient.setText("Selected Ingredients:");

        btnSelectIngredient.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnSelectIngredient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_select.png"))); // NOI18N
        btnSelectIngredient.setContentAreaFilled(false);
        btnSelectIngredient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSelectIngredient.setFocusPainted(false);

        listIngredient.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        scrpaneList.setViewportView(listIngredient);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("Unit");

        textfProductName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfProductCost.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout panelProductInfoLayout = new javax.swing.GroupLayout(panelProductInfo);
        panelProductInfo.setLayout(panelProductInfoLayout);
        panelProductInfoLayout.setHorizontalGroup(
            panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductInfoLayout.createSequentialGroup()
                .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductInfoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelProductInfoLayout.createSequentialGroup()
                                .addComponent(label_productID, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(label_size)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(combProductSize, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelProductInfoLayout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(label_cost)
                                    .addComponent(label_name))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textfProductCost)
                                    .addComponent(textfProductName))))
                        .addGap(37, 37, 37))
                    .addGroup(panelProductInfoLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(label_amount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerProductAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductInfoLayout.createSequentialGroup()
                        .addComponent(scrpaneList, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSelectIngredient, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelProductInfoLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(label_ingredient)))
                .addGap(211, 211, 211))
        );
        panelProductInfoLayout.setVerticalGroup(
            panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductInfoLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductInfoLayout.createSequentialGroup()
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textfProductID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_productID)
                            .addComponent(label_size, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combProductSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_name)
                            .addComponent(textfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_cost, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textfProductCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(label_amount)
                                .addComponent(spinnerProductAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(23, 23, 23))
                    .addGroup(panelProductInfoLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(label_ingredient)
                        .addGroup(panelProductInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelProductInfoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(scrpaneList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductInfoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSelectIngredient)
                                .addGap(58, 58, 58))))))
        );

        label_searchProduct.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_searchProduct.setText("Search name:");

        textfSearch.setPreferredSize(new java.awt.Dimension(190, 30));

        tableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Prod ID", "Name", "Size", "Cost", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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

        btnRequestImport.setBackground(new java.awt.Color(255, 255, 255));
        btnRequestImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_import-selected-product.png"))); // NOI18N
        btnRequestImport.setContentAreaFilled(false);
        btnRequestImport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRequestImport.setFocusPainted(false);

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
                    .addComponent(scrpaneTable)
                    .addComponent(btnRequestImport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelProductInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_searchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 459, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelProductInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSearchClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_searchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scrpaneTable, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRequestImport)
                .addGap(63, 63, 63))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnMore;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRequestImport;
    private javax.swing.JButton btnSearchClear;
    private javax.swing.JButton btnSelectIngredient;
    private javax.swing.JComboBox<String> combProductSize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel label_amount;
    private javax.swing.JLabel label_cost;
    private javax.swing.JLabel label_ingredient;
    private javax.swing.JLabel label_name;
    private javax.swing.JLabel label_productID;
    private javax.swing.JLabel label_searchProduct;
    private javax.swing.JLabel label_size;
    private javax.swing.JList<String> listIngredient;
    private javax.swing.JMenuItem mnExport;
    private javax.swing.JMenuItem mnImport;
    private javax.swing.JPanel panelBtn;
    private javax.swing.JPanel panelProductInfo;
    private javax.swing.JPopupMenu popupBtnMore;
    private javax.swing.JScrollPane scrpaneList;
    private javax.swing.JScrollPane scrpaneTable;
    private javax.swing.JSpinner spinnerProductAmount;
    private javax.swing.JTable tableProduct;
    private javax.swing.JTextField textfProductCost;
    private javax.swing.JTextField textfProductID;
    private javax.swing.JTextField textfProductName;
    private javax.swing.JTextField textfSearch;
    // End of variables declaration//GEN-END:variables
}
