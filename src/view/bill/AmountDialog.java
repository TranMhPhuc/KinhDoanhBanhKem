package view.bill;

import control.bill.create.BillCreateControllerInterface;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;

public class AmountDialog extends javax.swing.JDialog implements SettingUpdateObserver {

    private BillCreateControllerInterface billCreateController;

    public AmountDialog(java.awt.Frame parent, boolean modal,
            BillCreateControllerInterface billCreateController) {
        super(parent, modal);
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(parent);
        this.billCreateController = billCreateController;

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                billCreateController.validateAmountInputFromDialog();
            }
        });
    }

    public void setAmountInput(int amount) {
        spinnerAmount.setValue(amount);
    }

    public int getAmountInput() {
        return (int) spinnerAmount.getValue();
    }

    @Override
    public void updateSettingObserver() {
        if (AppSetting.getInstance().getAppLanguage() == AppSetting.Language.ENGLISH) {
            setTitle("Product amount input dialog");
            labelAmount.setText("Amount:");
        } else {
            setTitle("Nhập số lượng sản phẩm");
            labelAmount.setText("Số lượng:");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spinnerAmount = new javax.swing.JSpinner();
        labelAmount = new javax.swing.JLabel();
        btnOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        spinnerAmount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        spinnerAmount.setModel(new javax.swing.SpinnerNumberModel());

        labelAmount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelAmount.setText("Amount");

        btnOK.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tick3.png"))); // NOI18N
        btnOK.setText("OK");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(labelAmount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAmount)
                    .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(btnOK)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOK;
    private javax.swing.JLabel labelAmount;
    private javax.swing.JSpinner spinnerAmount;
    // End of variables declaration//GEN-END:variables
}
