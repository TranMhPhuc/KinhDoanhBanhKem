package view.function.product;

public class ProductManagePanel extends javax.swing.JPanel {

    public ProductManagePanel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabbedPanel = new javax.swing.JTabbedPane();
        panelIngredient = view.function.ingredient.IngredientPanel.getInstance();
        panelProvider = view.function.provider.ProviderPanel.getInstance();
        panelProduct = view.function.product.ProductPanel.getInstance();

        setBackground(new java.awt.Color(255, 255, 255));

        TabbedPanel.setBackground(new java.awt.Color(255, 255, 255));
        TabbedPanel.addTab("Ingredient", panelIngredient);
        TabbedPanel.addTab("Provider", panelProvider);
        TabbedPanel.addTab("Product", panelProduct);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1232, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabbedPanel;
    private view.function.ingredient.IngredientPanel panelIngredient;
    private view.function.product.ProductPanel panelProduct;
    private view.function.provider.ProviderPanel panelProvider;
    // End of variables declaration//GEN-END:variables
}
