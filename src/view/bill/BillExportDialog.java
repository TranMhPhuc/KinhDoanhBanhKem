package view.bill;

import control.bill.create.BillCreateControllerInterface;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.bill.BillCreateModelInterface;
import model.bill.BillModelInterface;
import model.bill.detail.ProductDetailModelInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import util.constant.AppConstant;
import util.swing.CurrencyTextField;
import view.MessageShowing;

public class BillExportDialog extends javax.swing.JDialog implements ActionListener,
        BillUpdateObserver, MessageShowing, SettingUpdateObserver {

    private BillCreateModelInterface billManageModel;
    private BillCreateControllerInterface billCreateController;

    public BillExportDialog(java.awt.Frame parent, boolean modal,
            BillCreateModelInterface billManageModel,
            BillCreateControllerInterface billCreateController) {
        super(parent, modal);
        initComponents();
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
            this.textfTotalMoney.setValue(Integer.parseInt(totalMoney));
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
            this.textfGuestMoney.setValue(null);
        } else {
            this.textfGuestMoney.setValue(Integer.parseInt(guestMoney));
        }
    }

    public void setChangeMoneyText(String text) {
        if (text.equals("")) {
            textfChangeMoney.setValue(null);
        } else {
            this.textfChangeMoney.setValue(text);
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
    public void updateSettingObserver() {
        if (AppSetting.getInstance().getAppLanguage() == AppSetting.Language.ENGLISH) {
            setTitle("Export bill");

            labelMainTitle.setText("Export Bill");
            labelBillID.setText("Bill ID:");
            labelTotalMoney.setText("Total money:");
            labelGuestMoney.setText("Guest money:");
            labelChangeMoney.setText("Change money:");

            btnContinue.setText("Continue");
            btnCancel.setText("Cancel");

        } else {
            setTitle("Hộp thoại xuất hóa đơn");

            labelMainTitle.setText("Xuất Hóa Đơn");
            labelBillID.setText("Mã hóa đơn:");
            labelTotalMoney.setText("Tổng tiền:");
            labelGuestMoney.setText("Tiền khách:");
            labelChangeMoney.setText("Tiền thối:");

            btnContinue.setText("Tiếp tục");
            btnCancel.setText("Thoát");
        }
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.ERROR_MESSAGE, AppConstant.IMAGE_ICON_MESSAGE_DIALOG_ERROR);
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.INFORMATION_MESSAGE, AppConstant.IMAGE_ICON_MESSAGE_DIALOG_INFO);
    }

    @Override
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.WARNING_MESSAGE, AppConstant.IMAGE_ICON_MESSAGE_DIALOG_WARNING);
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelMainTitle = new javax.swing.JLabel();
        labelGuestMoney = new javax.swing.JLabel();
        labelChangeMoney = new javax.swing.JLabel();
        btnContinue = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        labelError = new javax.swing.JLabel();
        labelTotalMoney = new javax.swing.JLabel();
        labelBillID = new javax.swing.JLabel();
        textfBillID = new javax.swing.JTextField();
        textfTotalMoney = new CurrencyTextField();
        textfGuestMoney = new CurrencyTextField();
        textfChangeMoney = new CurrencyTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        labelMainTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        labelMainTitle.setText("Export Bill");

        labelGuestMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelGuestMoney.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelGuestMoney.setText("Guest money:");

        labelChangeMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelChangeMoney.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelChangeMoney.setText("Change money:");

        btnContinue.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnContinue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/continue.png"))); // NOI18N
        btnContinue.setText("Continue");
        btnContinue.setPreferredSize(new java.awt.Dimension(150, 40));

        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setPreferredSize(new java.awt.Dimension(150, 40));

        labelError.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelError.setForeground(new java.awt.Color(255, 51, 51));
        labelError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelError.setText("Error");
        labelError.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelTotalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelTotalMoney.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTotalMoney.setText("Total money:");

        labelBillID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelBillID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelBillID.setText("Bill ID:");

        textfBillID.setEditable(false);

        textfTotalMoney.setEditable(false);
        textfTotalMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfGuestMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfChangeMoney.setEditable(false);
        textfChangeMoney.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("VND");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setText("VND");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setText("VND");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelMainTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 262, Short.MAX_VALUE)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelTotalMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelBillID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(labelChangeMoney)
                                            .addComponent(labelGuestMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textfBillID, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                    .addComponent(textfGuestMoney)
                                    .addComponent(textfChangeMoney)
                                    .addComponent(textfTotalMoney, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelError, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(labelMainTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfBillID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelBillID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTotalMoney)
                    .addComponent(textfTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelGuestMoney)
                    .addComponent(textfGuestMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelChangeMoney)
                    .addComponent(textfChangeMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(labelError)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContinue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnContinue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel labelBillID;
    private javax.swing.JLabel labelChangeMoney;
    private javax.swing.JLabel labelError;
    private javax.swing.JLabel labelGuestMoney;
    private javax.swing.JLabel labelMainTitle;
    private javax.swing.JLabel labelTotalMoney;
    private javax.swing.JTextField textfBillID;
    private javax.swing.JFormattedTextField textfChangeMoney;
    private javax.swing.JFormattedTextField textfGuestMoney;
    private javax.swing.JFormattedTextField textfTotalMoney;
    // End of variables declaration//GEN-END:variables
}
