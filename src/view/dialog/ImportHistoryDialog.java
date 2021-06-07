package view.dialog;

import java.awt.Color;
import util.swing.UIControl;

public class ImportHistoryDialog extends javax.swing.JDialog {

    public ImportHistoryDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        createView();
    }

    private void createView() {
        UIControl.setDefaultTableHeader(table_History);
        this.getContentPane().setBackground(Color.WHITE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_ImportHistory = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_History = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        label_ImportHistory.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        label_ImportHistory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_ImportHistory.setText("Import History");

        table_History.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ingredient name", "Import date", "Amount", "Unit", "Cost"
            }
        ));
        jScrollPane1.setViewportView(table_History);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_ImportHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 843, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_ImportHistory)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_ImportHistory;
    private javax.swing.JTable table_History;
    // End of variables declaration//GEN-END:variables
}
