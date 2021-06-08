package view.dialog;

import java.awt.Color;
import util.swing.UIControl;

public class IngredientSelectDialog extends javax.swing.JDialog {

    public IngredientSelectDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        UIControl.setDefaultTableHeader(tableIngredientOffered);
        UIControl.setDefaultTableHeader(tableIngredientSelected);
        this.getContentPane().setBackground(Color.WHITE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_titleSelectIngre = new javax.swing.JLabel();
        scrpaneIngredientOffered = new javax.swing.JScrollPane();
        tableIngredientOffered = new javax.swing.JTable();
        scrpaneIngredientSelected = new javax.swing.JScrollPane();
        tableIngredientSelected = new javax.swing.JTable();
        label_IngredientList = new javax.swing.JLabel();
        label_SelectedIngredients = new javax.swing.JLabel();
        btnChoose = new javax.swing.JButton();
        panel_Buttons = new javax.swing.JPanel();
        btnRemove = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnSaveChange = new javax.swing.JButton();
        label_ProductName = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        label_titleSelectIngre.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        label_titleSelectIngre.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        label_titleSelectIngre.setText("Edit Ingredient of Product");

        tableIngredientOffered.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Type", "Unit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableIngredientOffered.getTableHeader().setReorderingAllowed(false);
        scrpaneIngredientOffered.setViewportView(tableIngredientOffered);

        tableIngredientSelected.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
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
        tableIngredientSelected.getTableHeader().setReorderingAllowed(false);
        scrpaneIngredientSelected.setViewportView(tableIngredientSelected);

        label_IngredientList.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        label_IngredientList.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_IngredientList.setText("Ingredient list");

        label_SelectedIngredients.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        label_SelectedIngredients.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_SelectedIngredients.setText("Selected ingredients");

        btnChoose.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnChoose.setText("Choose");

        panel_Buttons.setBackground(new java.awt.Color(255, 255, 255));
        panel_Buttons.setLayout(new java.awt.GridLayout(1, 0, 40, 0));

        btnRemove.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnRemove.setText("Remove");
        panel_Buttons.add(btnRemove);

        btnClear.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnClear.setText("Clear");
        panel_Buttons.add(btnClear);

        btnSaveChange.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_save-all-changes.png"))); // NOI18N
        btnSaveChange.setContentAreaFilled(false);
        btnSaveChange.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSaveChange.setFocusPainted(false);

        label_ProductName.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        label_ProductName.setForeground(new java.awt.Color(255, 51, 0));
        label_ProductName.setText("Name");

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_cancel_50.png"))); // NOI18N
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setFocusPainted(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 119, Short.MAX_VALUE)
                        .addComponent(label_titleSelectIngre, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(label_ProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(163, 163, 163))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrpaneIngredientOffered, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(scrpaneIngredientSelected)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_Buttons, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_IngredientList, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(label_SelectedIngredients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addComponent(btnSaveChange, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_titleSelectIngre)
                    .addComponent(label_ProductName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_IngredientList)
                    .addComponent(label_SelectedIngredients))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrpaneIngredientOffered, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(scrpaneIngredientSelected, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_Buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChoose))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaveChange))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnChoose;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSaveChange;
    private javax.swing.JLabel label_IngredientList;
    private javax.swing.JLabel label_ProductName;
    private javax.swing.JLabel label_SelectedIngredients;
    private javax.swing.JLabel label_titleSelectIngre;
    private javax.swing.JPanel panel_Buttons;
    private javax.swing.JScrollPane scrpaneIngredientOffered;
    private javax.swing.JScrollPane scrpaneIngredientSelected;
    private javax.swing.JTable tableIngredientOffered;
    private javax.swing.JTable tableIngredientSelected;
    // End of variables declaration//GEN-END:variables
}
