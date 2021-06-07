package view.dialog;

import control.ingredient.IngredientControllerInterface;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.ingredient.IngredientManageModelInterface;
import view.MessageShowing;

public class IngredientImportDialog extends javax.swing.JDialog implements
        ActionListener, MessageShowing {

    private IngredientManageModelInterface model;
    private IngredientControllerInterface controller;

    public IngredientImportDialog(java.awt.Frame parent, boolean modal,
            IngredientManageModelInterface model,
            IngredientControllerInterface controller) {
        super(parent, modal);
        initComponents();

        this.model = model;
        this.controller = controller;

        createView();
        createControl();
    }

    private void createView() {
        dateChooserImportDate.setDate(new Date());
        this.getContentPane().setBackground(Color.WHITE);
    }

    private void createControl() {
        btnSave.addActionListener(this);
        btnCancel.addActionListener(this);

        spinnerImportAmount.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                String value = (String) spinnerImportAmount.getModel().getValue();
                // XXX
            }
        });
    }

    public void setCostText(String text) {
        this.textfCost.setText(text);
    }

    public void setIngredientName(String text) {
        this.textIngredientName.setText(text);
    }

    public void setLabelIngredientUnit(String text) {
        this.labelUnit.setText(text);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnSave) {
            String value = (String) spinnerImportAmount.getModel().getValue();
            Date importDate = dateChooserImportDate.getDate();
            // XXX
        } else if (source == btnCancel) {

        }
    }

    @Override
    public void showErrorMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showInfoMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showWarningMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_Import_Ingr_Title = new javax.swing.JLabel();
        panelInfo = new javax.swing.JPanel();
        label_SelectIngr = new javax.swing.JLabel();
        label_ImportDate = new javax.swing.JLabel();
        spinnerImportAmount = new javax.swing.JSpinner();
        label_Amount = new javax.swing.JLabel();
        label_Cost = new javax.swing.JLabel();
        textfCost = new javax.swing.JTextField();
        dateChooserImportDate = new com.toedter.calendar.JDateChooser();
        textIngredientName = new javax.swing.JTextField();
        labelUnit = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        label_Import_Ingr_Title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        label_Import_Ingr_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Import_Ingr_Title.setText("Import Ingredient");

        panelInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelInfo.setForeground(new java.awt.Color(255, 255, 255));

        label_SelectIngr.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_SelectIngr.setText("Selected ingredient");

        label_ImportDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_ImportDate.setText("Import date");

        spinnerImportAmount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        spinnerImportAmount.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        label_Amount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_Amount.setText("Amount");

        label_Cost.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_Cost.setText("Cost");

        textfCost.setEditable(false);
        textfCost.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        dateChooserImportDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        textIngredientName.setEditable(false);
        textIngredientName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        labelUnit.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelUnit.setText("Unit");

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addContainerGap(93, Short.MAX_VALUE)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                        .addComponent(label_Amount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelInfoLayout.createSequentialGroup()
                                .addComponent(spinnerImportAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelUnit))
                            .addComponent(textIngredientName, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                            .addComponent(textfCost)))
                    .addComponent(dateChooserImportDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelInfoLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(label_ImportDate)
                        .addComponent(label_SelectIngr)
                        .addComponent(label_Cost))
                    .addContainerGap(222, Short.MAX_VALUE)))
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(textIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dateChooserImportDate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelUnit, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spinnerImportAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_Amount, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(textfCost, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelInfoLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(label_SelectIngr, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(label_ImportDate, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(61, 61, 61)
                    .addComponent(label_Cost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE)))
        );

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_SaveImport.png"))); // NOI18N
        btnSave.setContentAreaFilled(false);
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.setFocusPainted(false);

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_cancel.png"))); // NOI18N
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setFocusPainted(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label_Import_Ingr_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancel)))
                        .addGap(0, 56, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label_Import_Ingr_Title)
                .addGap(18, 18, 18)
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private com.toedter.calendar.JDateChooser dateChooserImportDate;
    private javax.swing.JLabel labelUnit;
    private javax.swing.JLabel label_Amount;
    private javax.swing.JLabel label_Cost;
    private javax.swing.JLabel label_ImportDate;
    private javax.swing.JLabel label_Import_Ingr_Title;
    private javax.swing.JLabel label_SelectIngr;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JSpinner spinnerImportAmount;
    private javax.swing.JTextField textIngredientName;
    private javax.swing.JTextField textfCost;
    // End of variables declaration//GEN-END:variables

}
