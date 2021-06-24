package view.product;

import control.product.ProductControllerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import view.MessageShowing;

public class ProductProduceDialog extends javax.swing.JDialog implements MessageShowing,
        SettingUpdateObserver {

    private ProductControllerInterface productController;

    public ProductProduceDialog(java.awt.Frame parent, boolean modal,
            ProductControllerInterface productController) {
        super(parent, modal);
        initComponents();
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
        btnContinue.addActionListener(new ActionListener() {
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
        if(totalCost.equals("")){
            this.textfTotalCost.setValue(null);
        }else{
            this.textfTotalCost.setText(String.valueOf(totalCost));
        }
        
    }

    public int getProduceAmountInput() {
        return (int) spinnerAmount.getValue();
    }

    @Override
    public void updateSettingObserver() {
        if (AppSetting.getInstance().getAppLanguage() == AppSetting.Language.ENGLISH) {
            setTitle("Produce product dialog");
            labelMainTitle.setText("Produce Product");
            labelProductID.setText("Product ID:");
            labelProductName.setText("Product name:");
            labelAmount.setText("Amount:");
            labelCost.setText("Cost:");
            btnContinue.setText("Continue");
            btnCancel.setText("Cancel");
        } else {
            setTitle("Hộp thoại sản xuất sản phẩm");
            labelMainTitle.setText("Sản Xuất Sản Phẩm");
            labelProductID.setText("Mã sản phẩm:");
            labelProductName.setText("Tên sản phẩm:");
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

        jPanel1 = new javax.swing.JPanel();
        labelMainTitle = new javax.swing.JLabel();
        panelInfo = new javax.swing.JPanel();
        labelProductName = new javax.swing.JLabel();
        spinnerAmount = new javax.swing.JSpinner();
        labelAmount = new javax.swing.JLabel();
        labelCost = new javax.swing.JLabel();
        textfProductName = new javax.swing.JTextField();
        labelProductID = new javax.swing.JLabel();
        textfProductID = new javax.swing.JTextField();
        textfTotalCost = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        btnContinue = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 32767));
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        labelMainTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        labelMainTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMainTitle.setText("Import product");
        labelMainTitle.setPreferredSize(new java.awt.Dimension(177, 50));
        jPanel1.add(labelMainTitle, java.awt.BorderLayout.NORTH);

        panelInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelInfo.setForeground(new java.awt.Color(255, 255, 255));

        labelProductName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelProductName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelProductName.setText("Product Name:");

        spinnerAmount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        spinnerAmount.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        labelAmount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelAmount.setText("Amount:");

        labelCost.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelCost.setText("Total cost:");

        textfProductName.setEditable(false);
        textfProductName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        labelProductID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelProductID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelProductID.setText("Product ID:");

        textfProductID.setEditable(false);
        textfProductID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        textfTotalCost.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelProductName, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(labelAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelCost, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textfProductName, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                            .addComponent(textfTotalCost)
                            .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(labelProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textfProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(86, 86, 86))
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfProductID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelProductID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCost, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textfTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        jPanel1.add(panelInfo, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 60));

        btnContinue.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnContinue.setText("Continue");
        btnContinue.setPreferredSize(new java.awt.Dimension(120, 30));
        jPanel2.add(btnContinue);
        jPanel2.add(filler1);

        btnCancel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setPreferredSize(new java.awt.Dimension(120, 30));
        jPanel2.add(btnCancel);

        jPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnContinue;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelAmount;
    private javax.swing.JLabel labelCost;
    private javax.swing.JLabel labelMainTitle;
    private javax.swing.JLabel labelProductID;
    private javax.swing.JLabel labelProductName;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JSpinner spinnerAmount;
    private javax.swing.JTextField textfProductID;
    private javax.swing.JTextField textfProductName;
    private javax.swing.JFormattedTextField textfTotalCost;
    // End of variables declaration//GEN-END:variables
}
