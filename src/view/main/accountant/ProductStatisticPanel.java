package view.main.accountant;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.product.ProductModel;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import util.db.SQLServerConnection;

public class ProductStatisticPanel extends javax.swing.JPanel {

    private static final String SP_GET_TOTAL_SELL_AMOUNT_OF_MONTH
            = "{? = call get_total_sell_amount_of_month(?, ?)}";

    private static final String SP_GET_TOP_5_PRODUCT_BEST_SELLING_OF_MONTH
            = "{call get_5_the_best_selling_products_by_month(?, ?)}";

    private static final String SP_GET_TOP_5_PRODUCT_SLOW_SELLING_OF_MONTH
            = "{call get_the_least_selling_products_by_month(?, ?)}";

    private static final Color[] PIECHART_BEST_SELLER_SIDE_COLORS;
    private static final Color[] PIECHART_SLOW_SELLER_SIDE_COLORS;

    private static Connection dbConnection;

    private PieChart pieChartBestSellingCurrYear;
    private PieChart pieChartBestSellingPreviousYear;
    private PieChart pieChartSlowSellingCurrYear;
    private PieChart pieChartSlowSellingPreviousYear;

    private List<String> pieChartBestSellingCurrYearSeriesNames;
    private List<String> pieChartBestSellingPreviousYearSeriesNames;
    private List<String> pieChartSlowSellingCurrYearSeriesNames;
    private List<String> pieChartSlowSellingPreviousYearSeriesNames;

    static {
        dbConnection = SQLServerConnection.getConnection();
        PIECHART_BEST_SELLER_SIDE_COLORS = new Color[]{
            new Color(255, 112, 67), new Color(255, 167, 38), new Color(255, 202, 40),
            new Color(212, 225, 87), new Color(156, 204, 101), new Color(102, 187, 106)
        };
        PIECHART_SLOW_SELLER_SIDE_COLORS = new Color[]{
            new Color(236, 64, 122), new Color(171, 71, 188), new Color(126, 87, 194),
            new Color(92, 107, 192), new Color(66, 165, 245), new Color(66, 165, 245)
        };
    }

    public ProductStatisticPanel() {
        initComponents();
        pieChartBestSellingCurrYearSeriesNames = new ArrayList<>();
        pieChartBestSellingPreviousYearSeriesNames = new ArrayList<>();
        pieChartSlowSellingCurrYearSeriesNames = new ArrayList<>();
        pieChartSlowSellingPreviousYearSeriesNames = new ArrayList<>();
        
        configChart();
        
        loadProductBestSellingCurrYear(1);
        loadProductBestSellingPreviousYear(1);
        loadProductSlowSellingPreviousYear(1);
        loadProductSlowSellingPreviousYear(1);
        
        String[] monthNames = new DateFormatSymbols().getMonths();
        for (String monthName : monthNames) {
            combMonthTop.addItem(monthName);
            combMonthBottom.addItem(monthName);
        }
        
        combMonthTop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int monthIndex = combMonthTop.getSelectedIndex() + 1;
                loadProductBestSellingCurrYear(monthIndex);
                loadProductBestSellingPreviousYear(monthIndex);
            }
        });
        
        combMonthBottom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int monthIndex = combMonthBottom.getSelectedIndex() + 1;
                loadProductSlowSellingCurrYear(monthIndex);
                loadProductSlowSellingPreviousYear(monthIndex);
            }
        });
    }

    private void configChart() {
        pieChartBestSellingCurrYear = new PieChartBuilder().width(350).height(200)
                .theme(Styler.ChartTheme.Matlab).build();

        PieStyler pieChartBestSellingCurrYearStyle = pieChartBestSellingCurrYear.getStyler();
        pieChartBestSellingCurrYearStyle.setLegendVisible(true);
        pieChartBestSellingCurrYearStyle.setPlotBorderVisible(false);
        pieChartBestSellingCurrYearStyle.setSeriesColors(PIECHART_BEST_SELLER_SIDE_COLORS);

        panelTopLeft.setLayout(new BorderLayout());
        panelTopLeft.add(new XChartPanel<PieChart>(pieChartBestSellingCurrYear));

        //
        pieChartBestSellingPreviousYear = new PieChartBuilder().width(350).height(200)
                .theme(Styler.ChartTheme.Matlab).build();

        PieStyler pieChartBestSellingPreviousYearStyle = pieChartBestSellingPreviousYear.getStyler();
        pieChartBestSellingPreviousYearStyle.setLegendVisible(true);
        pieChartBestSellingPreviousYearStyle.setPlotBorderVisible(false);
        pieChartBestSellingPreviousYearStyle.setSeriesColors(PIECHART_BEST_SELLER_SIDE_COLORS);

        panelTopRight.setLayout(new BorderLayout());
        panelTopRight.add(new XChartPanel<PieChart>(pieChartBestSellingPreviousYear));

        //
        pieChartSlowSellingCurrYear = new PieChartBuilder().width(350).height(200)
                .theme(Styler.ChartTheme.Matlab).build();

        PieStyler pieChartSlowSellingCurrYearStyle = pieChartSlowSellingCurrYear.getStyler();
        pieChartSlowSellingCurrYearStyle.setLegendVisible(true);
        pieChartSlowSellingCurrYearStyle.setPlotBorderVisible(false);
        pieChartSlowSellingCurrYearStyle.setSeriesColors(PIECHART_SLOW_SELLER_SIDE_COLORS);

        panelBottomLeft.setLayout(new BorderLayout());
        panelBottomLeft.add(new XChartPanel<PieChart>(pieChartSlowSellingCurrYear));

        //
        pieChartSlowSellingPreviousYear = new PieChartBuilder().width(350).height(200)
                .theme(Styler.ChartTheme.Matlab).build();

        PieStyler pieChartSlowSellingPreviousYearStyle = pieChartSlowSellingPreviousYear.getStyler();
        pieChartSlowSellingPreviousYearStyle.setLegendVisible(true);
        pieChartSlowSellingPreviousYearStyle.setPlotBorderVisible(false);
        pieChartSlowSellingPreviousYearStyle.setSeriesColors(PIECHART_SLOW_SELLER_SIDE_COLORS);

        panelBottomRight.setLayout(new BorderLayout());
        panelBottomRight.add(new XChartPanel<PieChart>(pieChartSlowSellingPreviousYear));
    }

    public void loadProductBestSellingCurrYear(int month) {
        pieChartBestSellingCurrYearSeriesNames.forEach(seriesName -> {
            pieChartBestSellingCurrYear.removeSeries(seriesName);
        });

        pieChartBestSellingCurrYearSeriesNames.clear();

        int currYear = LocalDate.now().getYear();

        try {
            CallableStatement callableStatement;

            callableStatement = dbConnection.prepareCall(SP_GET_TOTAL_SELL_AMOUNT_OF_MONTH);

            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.setInt(2, month);
            callableStatement.setInt(3, currYear);
            callableStatement.execute();

            long totalProductSell = callableStatement.getLong(1);
            long remainProductSell = totalProductSell;

            callableStatement = dbConnection
                    .prepareCall(SP_GET_TOP_5_PRODUCT_BEST_SELLING_OF_MONTH);

            callableStatement.setInt(1, month);
            callableStatement.setInt(2, currYear);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString(ProductModel.NAME_HEADER);
                String productSize = resultSet.getString(ProductModel.SIZE_HEADER);
                int sellAmount = resultSet.getInt("SoLuongBan");
                remainProductSell -= sellAmount;
                float percent = (float) sellAmount / totalProductSell * 100;
                String seriesName = String.format("%s - %s \n(%.2f%%)",
                        productName, productSize, percent);
                pieChartBestSellingCurrYear.addSeries(seriesName, sellAmount);
                pieChartBestSellingCurrYearSeriesNames.add(seriesName);
            }

            float percent = (float) remainProductSell / totalProductSell * 100;
            String seriesName = String.format("Other (%.2f%%)", percent);
            pieChartBestSellingCurrYear.addSeries(seriesName, remainProductSell);
            pieChartBestSellingCurrYearSeriesNames.add(seriesName);

        } catch (SQLException ex) {
            Logger.getLogger(ProductStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadProductBestSellingPreviousYear(int month) {
        pieChartBestSellingPreviousYearSeriesNames.forEach(seriesName -> {
            pieChartBestSellingPreviousYear.removeSeries(seriesName);
        });

        pieChartBestSellingPreviousYearSeriesNames.clear();

        int previousYear = LocalDate.now().getYear() - 1;

        try {
            CallableStatement callableStatement;

            callableStatement = dbConnection.prepareCall(SP_GET_TOTAL_SELL_AMOUNT_OF_MONTH);

            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.setInt(2, month);
            callableStatement.setInt(3, previousYear);
            callableStatement.execute();

            long totalProductSell = callableStatement.getLong(1);
            long remainProductSell = totalProductSell;

            callableStatement = dbConnection
                    .prepareCall(SP_GET_TOP_5_PRODUCT_BEST_SELLING_OF_MONTH);

            callableStatement.setInt(1, month);
            callableStatement.setInt(2, previousYear);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString(ProductModel.NAME_HEADER);
                String productSize = resultSet.getString(ProductModel.SIZE_HEADER);
                int sellAmount = resultSet.getInt("SoLuongBan");
                remainProductSell -= sellAmount;
                float percent = (float) sellAmount / totalProductSell * 100;
                String seriesName = String.format("%s - %s \n(%.2f%%)",
                        productName, productSize, percent);
                pieChartBestSellingPreviousYear.addSeries(seriesName, sellAmount);
                pieChartBestSellingPreviousYearSeriesNames.add(seriesName);
            }

            float percent = (float) remainProductSell / totalProductSell * 100;
            String seriesName = String.format("Other (%.2f%%)", percent);
            pieChartBestSellingPreviousYear.addSeries(seriesName, remainProductSell);
            pieChartBestSellingPreviousYearSeriesNames.add(seriesName);

        } catch (SQLException ex) {
            Logger.getLogger(ProductStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadProductSlowSellingCurrYear(int month) {
        pieChartSlowSellingCurrYearSeriesNames.forEach(seriesName -> {
            pieChartSlowSellingCurrYear.removeSeries(seriesName);
        });

        pieChartSlowSellingCurrYearSeriesNames.clear();

        int previousYear = LocalDate.now().getYear();

        try {
            CallableStatement callableStatement;

            callableStatement = dbConnection.prepareCall(SP_GET_TOTAL_SELL_AMOUNT_OF_MONTH);

            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.setInt(2, month);
            callableStatement.setInt(3, previousYear);
            callableStatement.execute();

            long totalProductSell = callableStatement.getLong(1);
            long remainProductSell = totalProductSell;

            callableStatement = dbConnection
                    .prepareCall(SP_GET_TOP_5_PRODUCT_SLOW_SELLING_OF_MONTH);

            callableStatement.setInt(1, month);
            callableStatement.setInt(2, previousYear);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString(ProductModel.NAME_HEADER);
                String productSize = resultSet.getString(ProductModel.SIZE_HEADER);
                int sellAmount = resultSet.getInt("SoLuongBan");
                remainProductSell -= sellAmount;
                float percent = (float) sellAmount / totalProductSell * 100;
                String seriesName = String.format("%s - %s \n(%.2f%%)",
                        productName, productSize, percent);
                pieChartSlowSellingCurrYear.addSeries(seriesName, sellAmount);
                pieChartSlowSellingCurrYearSeriesNames.add(seriesName);
            }

            float percent = (float) remainProductSell / totalProductSell * 100;
            String seriesName = String.format("Other (%.2f%%)", percent);
            pieChartSlowSellingCurrYear.addSeries(seriesName, remainProductSell);
            pieChartSlowSellingCurrYearSeriesNames.add(seriesName);

        } catch (SQLException ex) {
            Logger.getLogger(ProductStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadProductSlowSellingPreviousYear(int month) {
        pieChartSlowSellingPreviousYearSeriesNames.forEach(seriesName -> {
            pieChartSlowSellingPreviousYear.removeSeries(seriesName);
        });

        pieChartSlowSellingPreviousYearSeriesNames.clear();

        int previousYear = LocalDate.now().getYear() - 1;

        try {
            CallableStatement callableStatement;

            callableStatement = dbConnection.prepareCall(SP_GET_TOTAL_SELL_AMOUNT_OF_MONTH);

            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.setInt(2, month);
            callableStatement.setInt(3, previousYear);
            callableStatement.execute();

            long totalProductSell = callableStatement.getLong(1);
            long remainProductSell = totalProductSell;

            callableStatement = dbConnection
                    .prepareCall(SP_GET_TOP_5_PRODUCT_SLOW_SELLING_OF_MONTH);

            callableStatement.setInt(1, month);
            callableStatement.setInt(2, previousYear);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString(ProductModel.NAME_HEADER);
                String productSize = resultSet.getString(ProductModel.SIZE_HEADER);
                int sellAmount = resultSet.getInt("SoLuongBan");
                remainProductSell -= sellAmount;
                float percent = (float) sellAmount / totalProductSell * 100;
                String seriesName = String.format("%s - %s \n(%.2f%%)",
                        productName, productSize, percent);
                pieChartSlowSellingPreviousYear.addSeries(seriesName, sellAmount);
                pieChartSlowSellingPreviousYearSeriesNames.add(seriesName);
            }

            float percent = (float) remainProductSell / totalProductSell * 100;
            String seriesName = String.format("Other (%.2f%%)", percent);
            pieChartSlowSellingPreviousYear.addSeries(seriesName, remainProductSell);
            pieChartSlowSellingPreviousYearSeriesNames.add(seriesName);

        } catch (SQLException ex) {
            Logger.getLogger(ProductStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        combMonthTop = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        panelTopLeft = new javax.swing.JPanel();
        panelTopRight = new javax.swing.JPanel();
        labelMonthTop = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        combMonthBottom = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        panelBottomLeft = new javax.swing.JPanel();
        panelBottomRight = new javax.swing.JPanel();
        labelMonthBottom = new javax.swing.JLabel();

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hàng bán chạy", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(204, 204, 204))); // NOI18N

        combMonthTop.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setLayout(new java.awt.GridLayout(1, 0, 3, 0));

        panelTopLeft.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTopLeftLayout = new javax.swing.GroupLayout(panelTopLeft);
        panelTopLeft.setLayout(panelTopLeftLayout);
        panelTopLeftLayout.setHorizontalGroup(
            panelTopLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        panelTopLeftLayout.setVerticalGroup(
            panelTopLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );

        jPanel6.add(panelTopLeft);

        panelTopRight.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTopRightLayout = new javax.swing.GroupLayout(panelTopRight);
        panelTopRight.setLayout(panelTopRightLayout);
        panelTopRightLayout.setHorizontalGroup(
            panelTopRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        panelTopRightLayout.setVerticalGroup(
            panelTopRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel6.add(panelTopRight);

        labelMonthTop.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelMonthTop.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelMonthTop.setText("Tháng:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(labelMonthTop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combMonthTop, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMonthTop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combMonthTop, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hàng bán chậm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(204, 204, 204))); // NOI18N

        combMonthBottom.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setLayout(new java.awt.GridLayout(1, 0, 3, 0));

        panelBottomLeft.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelBottomLeftLayout = new javax.swing.GroupLayout(panelBottomLeft);
        panelBottomLeft.setLayout(panelBottomLeftLayout);
        panelBottomLeftLayout.setHorizontalGroup(
            panelBottomLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        panelBottomLeftLayout.setVerticalGroup(
            panelBottomLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );

        jPanel9.add(panelBottomLeft);

        panelBottomRight.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelBottomRightLayout = new javax.swing.GroupLayout(panelBottomRight);
        panelBottomRight.setLayout(panelBottomRightLayout);
        panelBottomRightLayout.setHorizontalGroup(
            panelBottomRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        panelBottomRightLayout.setVerticalGroup(
            panelBottomRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel9.add(panelBottomRight);

        labelMonthBottom.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelMonthBottom.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelMonthBottom.setText("Tháng:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(labelMonthBottom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combMonthBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMonthBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combMonthBottom, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> combMonthBottom;
    private javax.swing.JComboBox<String> combMonthTop;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel labelMonthBottom;
    private javax.swing.JLabel labelMonthTop;
    private javax.swing.JPanel panelBottomLeft;
    private javax.swing.JPanel panelBottomRight;
    private javax.swing.JPanel panelTopLeft;
    private javax.swing.JPanel panelTopRight;
    // End of variables declaration//GEN-END:variables
}
