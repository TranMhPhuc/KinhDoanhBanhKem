package view.function.bill;

public class BillManagePanel extends javax.swing.JPanel {

    public BillManagePanel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabbedPanel = new javax.swing.JTabbedPane();
        panelBillCreate = view.function.bill.BillCreatePanel.getInstance();
        panelBillHistory = view.function.bill.BillHistoryPanel.getInstance();

        setBackground(new java.awt.Color(255, 255, 255));

        TabbedPanel.addTab("Selling", panelBillCreate);
        TabbedPanel.addTab("Bill", panelBillHistory);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1239, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPanel, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabbedPanel;
    private view.function.bill.BillCreatePanel panelBillCreate;
    private view.function.bill.BillHistoryPanel panelBillHistory;
    // End of variables declaration//GEN-END:variables
}
