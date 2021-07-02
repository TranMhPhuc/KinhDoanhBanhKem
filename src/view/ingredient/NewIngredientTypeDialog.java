package view.ingredient;

import control.ingredient.IngredientControllerInterface;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.ingredient.IngredientManageModelInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import view.MessageShowing;

public class NewIngredientTypeDialog extends javax.swing.JDialog implements
        ActionListener, MessageShowing, SettingUpdateObserver {

    private IngredientManageModelInterface ingredientManageModel;
    private IngredientControllerInterface ingredientController;

    public NewIngredientTypeDialog(java.awt.Frame parent, boolean modal,
            IngredientManageModelInterface model, IngredientControllerInterface controller) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);

        this.ingredientManageModel = model;
        this.ingredientController = controller;

        createView();
        createControl();
    }

    private void createView() {
        this.getContentPane().setBackground(Color.white);
        String nextIngredientTypeIDText = ingredientManageModel.getNextIngredientTypeIDText();
        this.textfTypeID.setText(nextIngredientTypeIDText);
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
            this.ingredientController.createNewIngredientType();
        } else if (source == btnCancel) {
            dispose();
        }
    }

    public String getIngredientTypeIDText() {
        return this.textfTypeID.getText();
    }

    public String getIngredientTypeName() {
        return this.textfTypeName.getText().trim();
    }

    @Override
    public void updateSettingObserver() {
        if (AppSetting.getInstance().getAppLanguage() == AppSetting.Language.ENGLISH) {
            setTitle("Create new ingredient type");

            labelMainTitle.setText("New Ingredient Type");
            labelTypeID.setText("Type ID:");
            labelTypeName.setText("Type name:");
            btnCreate.setText("Create");
            btnCancel.setText("Cancel");
        } else {
            setTitle("Hộp thoại tạo loại nguyên liệu mới");

            labelMainTitle.setText("Loại nguyên liệu mới");
            labelTypeID.setText("Mã loại:");
            labelTypeName.setText("Tên loại:");
            btnCreate.setText("Tạo");
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

        labelMainTitle = new javax.swing.JLabel();
        labelTypeID = new javax.swing.JLabel();
        labelTypeName = new javax.swing.JLabel();
        textfTypeID = new javax.swing.JTextField();
        textfTypeName = new javax.swing.JTextField();
        btnCreate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        labelMainTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        labelMainTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMainTitle.setText("New Ingredient Type");

        labelTypeID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTypeID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTypeID.setText(" ID");

        labelTypeName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTypeName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTypeName.setText(" Name");

        textfTypeID.setEditable(false);
        textfTypeID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfTypeName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        btnCreate.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Add_30px.png"))); // NOI18N
        btnCreate.setText("Create");
        btnCreate.setPreferredSize(new java.awt.Dimension(150, 40));

        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setPreferredSize(new java.awt.Dimension(150, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelMainTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelTypeID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTypeName, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textfTypeID, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelMainTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTypeID)
                    .addComponent(textfTypeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTypeName)
                    .addComponent(textfTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCreate;
    private javax.swing.JLabel labelMainTitle;
    private javax.swing.JLabel labelTypeID;
    private javax.swing.JLabel labelTypeName;
    private javax.swing.JTextField textfTypeID;
    private javax.swing.JTextField textfTypeName;
    // End of variables declaration//GEN-END:variables
}
