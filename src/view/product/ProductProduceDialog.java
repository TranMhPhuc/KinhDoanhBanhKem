package view.product;

import control.product.ProductControllerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import view.MessageShowing;

public class ProductProduceDialog extends javax.swing.JDialog implements MessageShowing {

    private static final String DIALOG_TITLE = "Request produce product dialog";

    private ProductControllerInterface productController;

    public ProductProduceDialog(java.awt.Frame parent, boolean modal,
            ProductControllerInterface productController) {
        super(parent, modal);
        initComponents();
        setTitle(DIALOG_TITLE);
        setLocationRelativeTo(parent);
        this.productController = productController;
        createControl();
    }

    private void createControl() {
        spinnerAmount.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                productController.showTotalCostProductProduce();
            }
        });
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productController.produceProduct();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void setProuductIDText(String productIDText) {
        this.textfProductID.setText(productIDText);
    }

    public void setProductName(String productName) {
        this.textfProductName.setText(productName);
    }

    public void setTotalCost(String totalCost) {
        this.textfTotalCost.setText(totalCost);
    }
    
    public int getProduceAmountInput() {
        return (int) spinnerAmount.getValue();
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, DIALOG_TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, DIALOG_TITLE, JOptionPane.WARNING_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        label_ImportProdcut = new javax.swing.JLabel();
        panelInfo = new javax.swing.JPanel();
        label_SelectProduct = new javax.swing.JLabel();
        spinnerAmount = new javax.swing.JSpinner();
        label_Amount = new javax.swing.JLabel();
        label_Cost = new javax.swing.JLabel();
        textfProductName = new javax.swing.JTextField();
        textfTotalCost = new javax.swing.JTextField();
        label_SelectProduct1 = new javax.swing.JLabel();
        textfProductID = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 32767));
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        label_ImportProdcut.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        label_ImportProdcut.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_ImportProdcut.setText("Import product");
        label_ImportProdcut.setPreferredSize(new java.awt.Dimension(177, 50));
        jPanel1.add(label_ImportProdcut, java.awt.BorderLayout.NORTH);

        panelInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelInfo.setForeground(new java.awt.Color(255, 255, 255));

        label_SelectProduct.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_SelectProduct.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label_SelectProduct.setText("Product Name:");

        spinnerAmount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        spinnerAmount.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        label_Amount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_Amount.setText("Amount:");

        label_Cost.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_Cost.setText("Total cost:");

        textfProductName.setEditable(false);
        textfProductName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfTotalCost.setEditable(false);
        textfTotalCost.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        label_SelectProduct1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_SelectProduct1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label_SelectProduct1.setText("Product ID:");

        textfProductID.setEditable(false);
        textfProductID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_Amount, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_Cost, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_SelectProduct, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textfProductName)
                            .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textfTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(label_SelectProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textfProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(86, 86, 86))
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfProductID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_SelectProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_SelectProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_Amount, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_Cost, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(panelInfo, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_SaveImport.png"))); // NOI18N
        btnOK.setContentAreaFilled(false);
        btnOK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel2.add(btnOK);
        jPanel2.add(filler1);

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button_cancel.png"))); // NOI18N
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setFocusPainted(false);
        jPanel2.add(btnCancel);

        jPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label_Amount;
    private javax.swing.JLabel label_Cost;
    private javax.swing.JLabel label_ImportProdcut;
    private javax.swing.JLabel label_SelectProduct;
    private javax.swing.JLabel label_SelectProduct1;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JSpinner spinnerAmount;
    private javax.swing.JTextField textfProductID;
    private javax.swing.JTextField textfProductName;
    private javax.swing.JTextField textfTotalCost;
    // End of variables declaration//GEN-END:variables
}
