package view.bill;

import control.bill.create.BillCreateControllerInterface;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
        addQuantitiesSpinnerKeyListener();
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
            setTitle("Product amount input");
            labelAmount.setText("Amount:");
        } else {
            setTitle("Nhập số lượng sản phẩm");
            labelAmount.setText("Số lượng:");
        }
    }

    @Override
    public void dispose() {
        spinnerAmount.getModel().setValue(1);
        super.dispose();
    }

    private void addQuantitiesSpinnerKeyListener() {
        spinnerAmount.getEditor().getComponent(0).addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar()) == false && e.getKeyChar() != 8 && e.getKeyChar() != 127) {
                    e.consume();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar()) == false && e.getKeyChar() != 8 && e.getKeyChar() != 127) {
                    e.consume();
                }
            }
        });
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
        spinnerAmount.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        labelAmount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelAmount.setText("Amount");

        btnOK.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tick3.png"))); // NOI18N
        btnOK.setText("OK");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(labelAmount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAmount)
                    .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOK)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOK;
    private javax.swing.JLabel labelAmount;
    private javax.swing.JSpinner spinnerAmount;
    // End of variables declaration//GEN-END:variables
}
