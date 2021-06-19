package view.dialog;

import control.bill.create.BillCreateControllerInterface;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AmountDialog extends javax.swing.JDialog {

    public static String DIALOG_TITLE = "Product amount input dialog";
    
    private BillCreateControllerInterface billCreateController;
    
    public AmountDialog(java.awt.Frame parent, boolean modal,
            BillCreateControllerInterface billCreateController) {
        super(parent, modal);
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        setTitle(DIALOG_TITLE);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spinnerAmount = new javax.swing.JSpinner();
        label_AddAmount = new javax.swing.JLabel();
        btnOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        spinnerAmount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        spinnerAmount.setModel(new javax.swing.SpinnerNumberModel());

        label_AddAmount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_AddAmount.setText("Amount");

        btnOK.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnOK.setText("OK");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(label_AddAmount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_AddAmount)
                    .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(btnOK)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOK;
    private javax.swing.JLabel label_AddAmount;
    private javax.swing.JSpinner spinnerAmount;
    // End of variables declaration//GEN-END:variables
}
