package view.ingredient;

import control.ingredient.IngredientControllerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import util.swing.CurrencyTextField;
import view.MessageShowing;

public class IngredientImportDialog extends javax.swing.JDialog implements
        ActionListener, MessageShowing, SettingUpdateObserver {

    private IngredientControllerInterface ingredientController;

    public IngredientImportDialog(java.awt.Frame parent, boolean modal,
            IngredientControllerInterface ingredientController) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        
        this.ingredientController = ingredientController;

        createControl();
    }

    private void createControl() {
        btnContinue.addActionListener(this);
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
        if(totalCost.equals("")){
            this.textfTotalCost.setValue(null);
        }else{
            this.textfTotalCost.setValue(Integer.parseInt(totalCost));
        }
        
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

        if (source == btnContinue) {
            ingredientController.importIngredient();
        } else if (source == btnCancel) {
            dispose();
        }
    }

    @Override
    public void updateSettingObserver() {
        if (AppSetting.getInstance().getAppLanguage() == AppSetting.Language.ENGLISH) {
            setTitle("Import ingredient dialog");
            
            labelMainTitle.setText("Import Ingredient");
            labelIngredientID.setText("Ingredient ID:");
            labelName.setText("Name:");
            labelImportDate.setText("Import date:");
            labelAmount.setText("Amount:");
            labelCost.setText("Total Cost:");
            
            btnContinue.setText("Continue");
            btnCancel.setText("Cancel");
            
        } else {
            setTitle("Hộp thoại nhập nguyên liệu");
            labelMainTitle.setText("Nhập Nguyên Liệu");
            labelIngredientID.setText("Mã nguyên liệu:");
            labelName.setText("Tên:");
            labelImportDate.setText("Ngày nhập:");
            labelAmount.setText("Số lượng:");
            labelCost.setText("Tổng chi phí:");
            
            btnContinue.setText("Tiếp tục");
            btnCancel.setText("Thoát");
        }
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.ERROR_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/error.png")));
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.INFORMATION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/infor.png")));
    }

    @Override
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/warning.png")));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        labelMainTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnContinue = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 32767));
        btnCancel = new javax.swing.JButton();
        panelInfo = new javax.swing.JPanel();
        labelIngredientID = new javax.swing.JLabel();
        labelImportDate = new javax.swing.JLabel();
        spinnerAmount = new javax.swing.JSpinner();
        labelAmount = new javax.swing.JLabel();
        labelCost = new javax.swing.JLabel();
        dateChooserImportDate = new com.toedter.calendar.JDateChooser();
        textfIngredientID = new javax.swing.JTextField();
        labelUnit = new javax.swing.JLabel();
        textfIngredientName = new javax.swing.JTextField();
        labelName = new javax.swing.JLabel();
        textfTotalCost = new CurrencyTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        labelMainTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        labelMainTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMainTitle.setText("Import Ingredient");
        labelMainTitle.setPreferredSize(new java.awt.Dimension(206, 60));
        jPanel2.add(labelMainTitle, java.awt.BorderLayout.NORTH);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(371, 60));

        btnContinue.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnContinue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/continue.png"))); // NOI18N
        btnContinue.setText("Continue");
        btnContinue.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel1.add(btnContinue);
        jPanel1.add(filler1);

        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel1.add(btnCancel);

        jPanel2.add(jPanel1, java.awt.BorderLayout.SOUTH);

        panelInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelInfo.setForeground(new java.awt.Color(255, 255, 255));

        labelIngredientID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelIngredientID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelIngredientID.setText("Ingredient ID:");

        labelImportDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelImportDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelImportDate.setText("Import date");

        spinnerAmount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        spinnerAmount.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        labelAmount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelAmount.setText("Amount");

        labelCost.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelCost.setText("Cost");

        dateChooserImportDate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        textfIngredientID.setEditable(false);
        textfIngredientID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        labelUnit.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelUnit.setText("Unit");
        labelUnit.setPreferredSize(new java.awt.Dimension(27, 30));

        textfIngredientName.setEditable(false);
        textfIngredientName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        labelName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelName.setText("Name:");

        textfTotalCost.setEditable(false);
        textfTotalCost.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("VND");

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelImportDate, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                            .addComponent(labelAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textfIngredientName, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                            .addGroup(panelInfoLayout.createSequentialGroup()
                                .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(dateChooserImportDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textfTotalCost)))
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(labelIngredientID, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textfIngredientID, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfIngredientID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelIngredientID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(labelImportDate, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(dateChooserImportDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(16, 16, 16))
        );

        jPanel2.add(panelInfo, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnContinue;
    private com.toedter.calendar.JDateChooser dateChooserImportDate;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelAmount;
    private javax.swing.JLabel labelCost;
    private javax.swing.JLabel labelImportDate;
    private javax.swing.JLabel labelIngredientID;
    private javax.swing.JLabel labelMainTitle;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelUnit;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JSpinner spinnerAmount;
    private javax.swing.JTextField textfIngredientID;
    private javax.swing.JTextField textfIngredientName;
    private javax.swing.JFormattedTextField textfTotalCost;
    // End of variables declaration//GEN-END:variables

}
