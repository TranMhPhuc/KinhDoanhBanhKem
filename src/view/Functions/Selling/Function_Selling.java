/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Functions.Selling;

/**
 *
 * @author Minh Tu
 */
public class Function_Selling extends javax.swing.JPanel {

    /**
     * Creates new form Function_Selling
     */
    public Function_Selling() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabbedPanel = new javax.swing.JTabbedPane();
        sellingPanel = new view.Functions.Selling.SellingPanel();
        billHistoryPanel = new view.Functions.Selling.BillHistoryPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        TabbedPanel.addTab("Selling", sellingPanel);
        TabbedPanel.addTab("Bill", billHistoryPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1239, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabbedPanel;
    private view.Functions.Selling.BillHistoryPanel billHistoryPanel;
    private view.Functions.Selling.SellingPanel sellingPanel;
    // End of variables declaration//GEN-END:variables
}
