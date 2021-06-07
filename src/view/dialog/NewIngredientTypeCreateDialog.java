package view.dialog;

import control.ingredient.IngredientControllerInterface;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.ingredient.IngredientManageModelInterface;
import view.MessageShowing;
import view.main.MainFrame;

public class NewIngredientTypeCreateDialog extends javax.swing.JDialog implements
        ActionListener, MessageShowing {

    private IngredientManageModelInterface model;
    private IngredientControllerInterface controller;

    public NewIngredientTypeCreateDialog(java.awt.Frame parent, boolean modal,
            IngredientManageModelInterface model, IngredientControllerInterface controller) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);

        this.model = model;
        this.controller = controller;

        createView();
        createControl();
    }

    private void createView() {
        this.getContentPane().setBackground(Color.white);
    }

    private void createControl() {
        btnCreate.addActionListener(this);
        btnCancel.addActionListener(this);
    }

    public void setIngredientTypeID(String text) {
        this.textfTypeID.setText(text);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnCreate) {
            this.controller.checkNewIngredientTypeInput();
        } else if (source == btnCancel) {
            dispose();
        }
    }

    public String getIngredientTypeName() {
        return this.textfTypeName.getText().trim();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_addIngre_title = new javax.swing.JLabel();
        label_ingredientID = new javax.swing.JLabel();
        label_ingredientName = new javax.swing.JLabel();
        textfTypeID = new javax.swing.JTextField();
        textfTypeName = new javax.swing.JTextField();
        btnCreate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        label_addIngre_title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        label_addIngre_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_addIngre_title.setText("New Ingredient Type");

        label_ingredientID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_ingredientID.setText(" ID");

        label_ingredientName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_ingredientName.setText(" Name");

        textfTypeID.setEditable(false);
        textfTypeID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfTypeName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        btnCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_create.png"))); // NOI18N
        btnCreate.setContentAreaFilled(false);
        btnCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreate.setFocusPainted(false);

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_cancel_40.png"))); // NOI18N
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setFocusPainted(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_addIngre_title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label_ingredientName)
                    .addComponent(label_ingredientID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textfTypeID, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCreate)
                .addGap(34, 34, 34)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_addIngre_title)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_ingredientID)
                    .addComponent(textfTypeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_ingredientName)
                    .addComponent(textfTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCreate;
    private javax.swing.JLabel label_addIngre_title;
    private javax.swing.JLabel label_ingredientID;
    private javax.swing.JLabel label_ingredientName;
    private javax.swing.JTextField textfTypeID;
    private javax.swing.JTextField textfTypeName;
    // End of variables declaration//GEN-END:variables
}
