/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.function.bill;

import util.swing.UIControl;

/**
 *
 * @author DELL
 */
public class BillHistoryPanel extends javax.swing.JPanel {

    /**
     * Creates new form BillHistoryPanel
     */
    public BillHistoryPanel() {
        initComponents();
        UIControl.setDefaultTableHeader(tableBill);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBillDateInfo = new javax.swing.JPanel();
        label_dayCreBillFrom = new javax.swing.JLabel();
        label_dayCreBillTo = new javax.swing.JLabel();
        btnApply = new javax.swing.JButton();
        datechooserBillDateFrom = new com.toedter.calendar.JDateChooser();
        datechooserBillDateTo = new com.toedter.calendar.JDateChooser();
        scrollPane_BillTable = new javax.swing.JScrollPane();
        tableBill = new javax.swing.JTable();
        label_Search = new javax.swing.JLabel();
        textfSearch = new javax.swing.JTextField();
        panelBtn = new javax.swing.JPanel();
        btnDetail = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        combobox_typeToSearch = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        panelBillDateInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelBillDateInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Date filter", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        label_dayCreBillFrom.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_dayCreBillFrom.setText("From");

        label_dayCreBillTo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_dayCreBillTo.setText("To");

        btnApply.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnApply.setText("Apply");
        btnApply.setFocusPainted(false);

        datechooserBillDateFrom.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        datechooserBillDateTo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout panelBillDateInfoLayout = new javax.swing.GroupLayout(panelBillDateInfo);
        panelBillDateInfo.setLayout(panelBillDateInfoLayout);
        panelBillDateInfoLayout.setHorizontalGroup(
            panelBillDateInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBillDateInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label_dayCreBillFrom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(datechooserBillDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_dayCreBillTo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(datechooserBillDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnApply, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(243, 243, 243))
        );
        panelBillDateInfoLayout.setVerticalGroup(
            panelBillDateInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBillDateInfoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelBillDateInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(datechooserBillDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datechooserBillDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBillDateInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_dayCreBillFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_dayCreBillTo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnApply, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        tableBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Bill ID", "Total Bill Money", "Customer Pay Money", "Change", "Day Created", "Employee Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane_BillTable.setViewportView(tableBill);

        label_Search.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        label_Search.setText("Type");

        textfSearch.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        panelBtn.setBackground(new java.awt.Color(255, 255, 255));
        panelBtn.setLayout(new java.awt.GridLayout(1, 0, 50, 0));

        btnDetail.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnDetail.setText("Details");
        btnDetail.setFocusPainted(false);
        btnDetail.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtn.add(btnDetail);

        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnClear.setText("Clear");
        btnClear.setPreferredSize(new java.awt.Dimension(115, 40));
        panelBtn.add(btnClear);

        combobox_typeToSearch.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        combobox_typeToSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee name", "Bill ID" }));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("to search");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBillDateInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(label_Search)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combobox_typeToSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 409, Short.MAX_VALUE)
                        .addComponent(panelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPane_BillTable)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(panelBillDateInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_Search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(combobox_typeToSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(panelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scrollPane_BillTable, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents
    public void clearAll() {
        datechooserBillDateFrom.setDate(null);
        datechooserBillDateTo.setDate(null);
        combobox_typeToSearch.setSelectedIndex(0);
        textfSearch.setText(null);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDetail;
    private javax.swing.JComboBox<String> combobox_typeToSearch;
    private com.toedter.calendar.JDateChooser datechooserBillDateFrom;
    private com.toedter.calendar.JDateChooser datechooserBillDateTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel label_Search;
    private javax.swing.JLabel label_dayCreBillFrom;
    private javax.swing.JLabel label_dayCreBillTo;
    private javax.swing.JPanel panelBillDateInfo;
    private javax.swing.JPanel panelBtn;
    private javax.swing.JScrollPane scrollPane_BillTable;
    private javax.swing.JTable tableBill;
    private javax.swing.JTextField textfSearch;
    // End of variables declaration//GEN-END:variables
}
