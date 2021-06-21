package view.ingredient;

import control.ingredient.IngredientControllerInterface;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.ingredient.IngredientManageModelInterface;
import view.MessageShowing;

public class IngredientImportDialog extends javax.swing.JDialog implements
        ActionListener, MessageShowing {

    public static final String DIALOG_TITLE = "Request import ingredient dialog";

    private IngredientControllerInterface ingredientController;

    public IngredientImportDialog(java.awt.Frame parent, boolean modal,
            IngredientControllerInterface ingredientController) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        setTitle(DIALOG_TITLE);

//        this.ingredientManageModel = ingredientManageModel;
        this.ingredientController = ingredientController;

        createView();
        createControl();
    }

    private void createView() {
    }

    private void createControl() {
        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);

        spinnerAmount.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                ingredientController.showTotalCostIngredientImport();
            }
        });
    }
    
    public void setImportDate(Date importDate) {
        dateChooserImportDate.setDate(importDate);
    }

    public int getImportAmountInput() {
        return (int) spinnerAmount.getValue();
    }

    public Date getImportDateInput() {
        return dateChooserImportDate.getDate();
    }

    public void setIngredientTotalCost(String totalCost) {
        this.textfTotalCost.setText(totalCost);
    }

    public void setIngredientIDText(String ingredientIDText) {
        textfIngredientID.setText(ingredientIDText);
    }

    public void setIngredientName(String ingredientName) {
        this.textfIngredientName.setText(ingredientName);
    }

    public void setLabelIngredientUnit(String ingredientUnitName) {
        this.labelUnit.setText(ingredientUnitName);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnOK) {
            ingredientController.importIngredient();
        } else if (source == btnCancel) {
            dispose();
        }
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, DIALOG_TITLE, JOptionPane.ERROR_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/error.png")));
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, DIALOG_TITLE, JOptionPane.INFORMATION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/infor.png")));
    }

    @Override
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, DIALOG_TITLE, JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/warning.png")));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        label_Import_Ingr_Title = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 32767));
        btnCancel = new javax.swing.JButton();
        panelInfo = new javax.swing.JPanel();
        label_SelectIngr = new javax.swing.JLabel();
        label_ImportDate = new javax.swing.JLabel();
        spinnerAmount = new javax.swing.JSpinner();
        label_Amount = new javax.swing.JLabel();
        label_Cost = new javax.swing.JLabel();
        textfTotalCost = new javax.swing.JTextField();
        dateChooserImportDate = new com.toedter.calendar.JDateChooser();
        textfIngredientID = new javax.swing.JTextField();
        labelUnit = new javax.swing.JLabel();
        textfIngredientName = new javax.swing.JTextField();
        label_SelectIngr1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        label_Import_Ingr_Title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        label_Import_Ingr_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Import_Ingr_Title.setText("Import Ingredient");
        label_Import_Ingr_Title.setPreferredSize(new java.awt.Dimension(206, 60));
        jPanel2.add(label_Import_Ingr_Title, java.awt.BorderLayout.NORTH);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(371, 95));

        btnOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_SaveImport.png"))); // NOI18N
        btnOK.setContentAreaFilled(false);
        btnOK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOK.setFocusPainted(false);
        jPanel1.add(btnOK);
        jPanel1.add(filler1);

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_cancel.png"))); // NOI18N
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setFocusPainted(false);
        jPanel1.add(btnCancel);

        jPanel2.add(jPanel1, java.awt.BorderLayout.SOUTH);

        panelInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelInfo.setForeground(new java.awt.Color(255, 255, 255));

        label_SelectIngr.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_SelectIngr.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label_SelectIngr.setText("Ingredient ID:");

        label_ImportDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_ImportDate.setText("Import date");

        spinnerAmount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        spinnerAmount.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        label_Amount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_Amount.setText("Amount");

        label_Cost.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_Cost.setText("Cost");

        textfTotalCost.setEditable(false);
        textfTotalCost.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        dateChooserImportDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        textfIngredientID.setEditable(false);
        textfIngredientID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        labelUnit.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelUnit.setText("Unit");
        labelUnit.setPreferredSize(new java.awt.Dimension(27, 30));

        textfIngredientName.setEditable(false);
        textfIngredientName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        label_SelectIngr1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_SelectIngr1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label_SelectIngr1.setText("Name:");

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(label_SelectIngr, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textfIngredientID, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_ImportDate)
                            .addComponent(label_SelectIngr1)
                            .addComponent(label_Amount)
                            .addComponent(label_Cost))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textfIngredientName, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                            .addGroup(panelInfoLayout.createSequentialGroup()
                                .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(dateChooserImportDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textfTotalCost))))
                .addGap(60, 60, 60))
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfIngredientID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_SelectIngr, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_SelectIngr1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(label_ImportDate, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_Amount, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(dateChooserImportDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_Cost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textfTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );

        jPanel2.add(panelInfo, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private com.toedter.calendar.JDateChooser dateChooserImportDate;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelUnit;
    private javax.swing.JLabel label_Amount;
    private javax.swing.JLabel label_Cost;
    private javax.swing.JLabel label_ImportDate;
    private javax.swing.JLabel label_Import_Ingr_Title;
    private javax.swing.JLabel label_SelectIngr;
    private javax.swing.JLabel label_SelectIngr1;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JSpinner spinnerAmount;
    private javax.swing.JTextField textfIngredientID;
    private javax.swing.JTextField textfIngredientName;
    private javax.swing.JTextField textfTotalCost;
    // End of variables declaration//GEN-END:variables

}
