package view.bill;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import control.bill.create.BillCreateControllerInterface;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bill.BillModelInterface;
import model.bill.detail.ProductDetailModelInterface;
import view.MessageShowing;
import model.bill.BillCreateModelInterface;
import util.swing.CurrencyTextField;

public class BillExportDialog extends javax.swing.JDialog implements ActionListener,
        BillUpdateObserver, MessageShowing {

    public static final String DIALOG_TITLE = "Export bill";

    private BillCreateModelInterface billManageModel;
    private BillCreateControllerInterface billCreateController;

    public BillExportDialog(java.awt.Frame parent, boolean modal,
            BillCreateModelInterface billManageModel,
            BillCreateControllerInterface billCreateController) {
        super(parent, modal);
        initComponents();
        setTitle(DIALOG_TITLE);
        setLocationRelativeTo(parent);

        this.billManageModel = billManageModel;
        billManageModel.registerBillUpdateObserver(this);
        this.billCreateController = billCreateController;

        createView();
        createControl();
    }

    private void createView() {
        this.getContentPane().setBackground(Color.WHITE);
        this.btnContinue.setEnabled(false);
        this.labelError.setText("");
    }

    private void createControl() {
        this.btnContinue.addActionListener(this);
        this.btnCancel.addActionListener(this);

        this.textfGuestMoney.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                try {
                    ((CurrencyTextField) textfGuestMoney).commitEdit();
                    billCreateController.validateGuestMoney();
                } catch (ParseException ex) {
                    Logger.getLogger(BillExportDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                billCreateController.validateGuestMoney();
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public void setBillIDText(String billIDText) {
        this.textfBillID.setText(billIDText);
    }

    public void setBillTotalMoney(String totalMoney) {
        if (totalMoney.equals("")) {
            this.textfTotalMoney.setValue(null);
        } else {
            this.textfTotalMoney.setValue(Integer.valueOf(totalMoney));
        }
    }

    public void setBtnContinueEnable(boolean enable) {
        this.btnContinue.setEnabled(enable);
    }

    public void setLabelErrorText(String text) {
        this.labelError.setText(text);
    }

    public void setGuestMoneyText(String guestMoney) {
        if (guestMoney.equals("")) {
            System.out.println("was here");
            textfGuestMoney.setValue(null);
        } else {
            this.textfGuestMoney.setValue(Integer.valueOf(guestMoney));
        }
    }

    public void setChangeMoneyText(String text) {
        if (text.equals("")) {
            this.textfChangeMoney.setValue(null);
        } else {
            this.textfChangeMoney.setValue(Integer.valueOf(text));
        }
    }

    public String getGuestMoneyInput() {
        return String.valueOf(textfGuestMoney.getValue());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnContinue) {
            this.billCreateController.exportBill();
        } else if (source == btnCancel) {
            dispose();
        }
    }

    @Override
    public void updateFromNewBillCreated(BillModelInterface bill) {
    }

    @Override
    public void updateFromBillState() {
        long changeMoney = billManageModel.getChangeMoney();
        textfChangeMoney.setValue(changeMoney);
    }

    @Override
    public void updateFromModifiedSelectedProduct(ProductDetailModelInterface productDetail) {
    }

    @Override
    public void updateFromInsertedSelectedProduct(ProductDetailModelInterface productDetail) {
    }

    @Override
    public void updateFromDeletedSelectedProduct(int rowID) {
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


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnContinue = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        labelError = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textfBillID = new javax.swing.JTextField();
        textfTotalMoney = new CurrencyTextField();
        textfGuestMoney = new CurrencyTextField();
        textfChangeMoney = new CurrencyTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Export Bill");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Guest money:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Change money:");

        btnContinue.setFont(jLabel2.getFont());
        btnContinue.setText("Continue");

        btnCancel.setFont(jLabel2.getFont());
        btnCancel.setText("Cancel");

        labelError.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelError.setForeground(new java.awt.Color(255, 51, 51));
        labelError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelError.setText("Error");
        labelError.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Total money:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Bill ID:");

        textfBillID.setEditable(false);

        textfTotalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfGuestMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfChangeMoney.setEditable(false);
        textfChangeMoney.setEnabled(false);
        textfChangeMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(159, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(159, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelError, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(20, 20, 20)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(textfChangeMoney, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                    .addComponent(textfGuestMoney))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnContinue)
                            .addGap(78, 78, 78)
                            .addComponent(btnCancel)
                            .addGap(52, 52, 52)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(18, 18, 18)
                            .addComponent(textfTotalMoney))))
                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textfGuestMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textfChangeMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(labelError)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContinue)
                    .addComponent(btnCancel))
                .addGap(44, 44, 44))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnContinue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel labelError;
    private javax.swing.JTextField textfBillID;
    private javax.swing.JFormattedTextField textfChangeMoney;
    private javax.swing.JFormattedTextField textfGuestMoney;
    private javax.swing.JFormattedTextField textfTotalMoney;
    // End of variables declaration//GEN-END:variables
}
