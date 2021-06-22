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
import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.product.ProductSimpleModel;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import util.constant.AppConstant;
import util.db.SQLServerConnection;

public class ProductStatisticPanel extends javax.swing.JPanel {

    private static final String SP_GET_TOTAL_SELL_AMOUNT_OF_MONTH
            = "{? = call get_total_sell_amount_of_month(?, ?)}";

    private static final String SP_GET_TOP_5_PRODUCT_BEST_SELLING_OF_MONTH
            = "{call get_5_the_best_selling_products_by_month(?, ?)}";

    private static final String SP_GET_TOP_5_PRODUCT_SLOW_SELLING_OF_MONTH
            = "{call get_the_least_selling_products_by_month(?, ?)}";

    private static final Color[] PIECHART_BEST_SALES_SIDE_COLORS;
    private static final Color[] PIECHART_SLOW_SALES_SIDE_COLORS;

    private static Connection dbConnection;

    private PieChart pieChartBestSalesCurrYear;
    private PieChart pieChartBestSalesPreviousYear;
    private PieChart pieChartSlowSalesCurrYear;
    private PieChart pieChartSlowSalesPreviousYear;

    static {
        dbConnection = SQLServerConnection.getConnection();
        PIECHART_BEST_SALES_SIDE_COLORS = new Color[]{
            new Color(255, 112, 67), new Color(255, 167, 38), new Color(255, 202, 40),
            new Color(212, 225, 87), new Color(156, 204, 101), new Color(102, 187, 106)
        };
        PIECHART_SLOW_SALES_SIDE_COLORS = new Color[]{
            new Color(236, 64, 122), new Color(171, 71, 188), new Color(126, 87, 194),
            new Color(92, 107, 192), new Color(66, 165, 245), new Color(66, 165, 245)
        };
    }

    public ProductStatisticPanel() {
        initComponents();
        configChartBestSales();
        configChartSlowSales();

        loadProductBestSellingCurrYear(1);
        loadProductBestSellingPreviousYear(1);
        loadProductSlowSellingPreviousYear(1);
        loadProductSlowSellingPreviousYear(1);

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

    private void configChartBestSales() {
        pieChartBestSalesCurrYear = new PieChartBuilder().width(350).height(200)
                .theme(Styler.ChartTheme.Matlab).build();

        PieStyler pieChartBestSalesCurrYearStyle = pieChartBestSalesCurrYear.getStyler();

        pieChartBestSalesCurrYearStyle.setChartTitleFont(AppConstant.MAIN_TITLE_FONT);
        pieChartBestSalesCurrYearStyle.setChartPadding(30);
        pieChartBestSalesCurrYearStyle.setHasAnnotations(true);

        pieChartBestSalesCurrYearStyle.setLegendVisible(true);
        pieChartBestSalesCurrYearStyle.setLegendFont(AppConstant.LEGEND_FONT);
        pieChartBestSalesCurrYearStyle.setLegendPosition(Styler.LegendPosition.OutsideE);
        pieChartBestSalesCurrYearStyle.setLegendBorderColor(Color.WHITE);
        pieChartBestSalesCurrYearStyle.setPlotBorderVisible(false);
        pieChartBestSalesCurrYearStyle.setSeriesColors(PIECHART_BEST_SALES_SIDE_COLORS);

        panelTopRight.setLayout(new BorderLayout());
        panelTopRight.add(new XChartPanel<PieChart>(pieChartBestSalesCurrYear));

        //
        pieChartBestSalesPreviousYear = new PieChartBuilder().width(350).height(200)
                .theme(Styler.ChartTheme.Matlab).build();

        PieStyler pieChartBestSalesPreviousYearStyle = pieChartBestSalesPreviousYear.getStyler();
        pieChartBestSalesPreviousYearStyle.setChartPadding(30);
        pieChartBestSalesPreviousYearStyle.setHasAnnotations(true);

        pieChartBestSalesPreviousYearStyle.setLegendVisible(true);
        pieChartBestSalesPreviousYearStyle.setLegendFont(AppConstant.LEGEND_FONT);
        pieChartBestSalesPreviousYearStyle.setLegendPosition(Styler.LegendPosition.OutsideE);
        pieChartBestSalesPreviousYearStyle.setLegendBorderColor(Color.WHITE);
        pieChartBestSalesPreviousYearStyle.setPlotBorderVisible(false);
        pieChartBestSalesPreviousYearStyle.setSeriesColors(PIECHART_BEST_SALES_SIDE_COLORS);

        panelTopLeft.setLayout(new BorderLayout());
        panelTopLeft.add(new XChartPanel<PieChart>(pieChartBestSalesPreviousYear));
    }

    private void configChartSlowSales() {
        pieChartSlowSalesCurrYear = new PieChartBuilder().width(350).height(200)
                .theme(Styler.ChartTheme.Matlab).build();

        PieStyler pieChartSlowSalesCurrYearStyle = pieChartSlowSalesCurrYear.getStyler();
        pieChartSlowSalesCurrYearStyle.setChartPadding(30);
        pieChartSlowSalesCurrYearStyle.setHasAnnotations(true);

        pieChartSlowSalesCurrYearStyle.setLegendVisible(true);
        pieChartSlowSalesCurrYearStyle.setLegendFont(AppConstant.LEGEND_FONT);
        pieChartSlowSalesCurrYearStyle.setLegendPosition(Styler.LegendPosition.OutsideE);
        pieChartSlowSalesCurrYearStyle.setLegendBorderColor(Color.WHITE);
        pieChartSlowSalesCurrYearStyle.setPlotBorderVisible(false);
        pieChartSlowSalesCurrYearStyle.setSeriesColors(PIECHART_SLOW_SALES_SIDE_COLORS);

        panelBottomRight.setLayout(new BorderLayout());
        panelBottomRight.add(new XChartPanel<PieChart>(pieChartSlowSalesCurrYear));

        //
        pieChartSlowSalesPreviousYear = new PieChartBuilder().width(350).height(200)
                .theme(Styler.ChartTheme.Matlab).build();

        PieStyler pieChartSlowSalesPreviousYearStyle = pieChartSlowSalesPreviousYear.getStyler();
        pieChartSlowSalesPreviousYearStyle.setChartPadding(30);
        pieChartSlowSalesPreviousYearStyle.setHasAnnotations(true);

        pieChartSlowSalesPreviousYearStyle.setLegendVisible(true);
        pieChartSlowSalesPreviousYearStyle.setLegendFont(AppConstant.LEGEND_FONT);
        pieChartSlowSalesPreviousYearStyle.setLegendPosition(Styler.LegendPosition.OutsideE);
        pieChartSlowSalesPreviousYearStyle.setLegendBorderColor(Color.WHITE);
        pieChartSlowSalesPreviousYearStyle.setPlotBorderVisible(false);
        pieChartSlowSalesPreviousYearStyle.setSeriesColors(PIECHART_SLOW_SALES_SIDE_COLORS);

        panelBottomLeft.setLayout(new BorderLayout());
        panelBottomLeft.add(new XChartPanel<PieChart>(pieChartSlowSalesPreviousYear));

    }

    public void loadProductBestSellingCurrYear(int month) {
        if (month <= 0 || month >= 13) {
            throw new IllegalArgumentException();
        }

        Map<String, PieSeries> seriesMap = pieChartBestSalesCurrYear.getSeriesMap();

        Object[] seriesNames = seriesMap.keySet().toArray();

        for (int i = 0; i < seriesNames.length; i++) {
            pieChartBestSalesCurrYear.removeSeries((String) seriesNames[i]);
        }

        int currYear = LocalDate.now().getYear();

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_TOTAL_SELL_AMOUNT_OF_MONTH);

            callableStatement.registerOutParameter(1, Types.INTEGER);

            callableStatement.setInt(2, month);
            callableStatement.setInt(3, currYear);

            callableStatement.execute();

            long totalProductSell = callableStatement.getLong(1);
            long remainProductSell = totalProductSell;

            if (totalProductSell == 0) {
                return;
            }

            callableStatement = dbConnection
                    .prepareCall(SP_GET_TOP_5_PRODUCT_BEST_SELLING_OF_MONTH);

            callableStatement.setInt(1, month);
            callableStatement.setInt(2, currYear);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString(ProductSimpleModel.NAME_HEADER);
                String productSize = resultSet.getString(ProductSimpleModel.SIZE_HEADER);
                int sellAmount = resultSet.getInt("SoLuongBan");
                remainProductSell -= sellAmount;
                float percent = (float) sellAmount / totalProductSell * 100;
                String seriesName = String.format("%s - %s \n(%.2f%%)",
                        productName, productSize, percent);
                pieChartBestSalesCurrYear.addSeries(seriesName, sellAmount);
            }

            float percent = (float) remainProductSell / totalProductSell * 100;
            String seriesName = String.format("Other (%.2f%%)", percent);
            pieChartBestSalesCurrYear.addSeries(seriesName, remainProductSell);

        } catch (SQLException ex) {
            Logger.getLogger(ProductStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadProductBestSellingPreviousYear(int month) {
        if (month <= 0 || month >= 13) {
            throw new IllegalArgumentException();
        }

        Map<String, PieSeries> seriesMap = pieChartBestSalesPreviousYear.getSeriesMap();

        Object[] seriesNames = seriesMap.keySet().toArray();

        for (int i = 0; i < seriesNames.length; i++) {
            pieChartBestSalesPreviousYear.removeSeries((String) seriesNames[i]);
        }

        int previousYear = LocalDate.now().getYear() - 1;

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_TOTAL_SELL_AMOUNT_OF_MONTH);

            callableStatement.registerOutParameter(1, Types.INTEGER);

            callableStatement.setInt(2, month);
            callableStatement.setInt(3, previousYear);

            callableStatement.execute();

            long totalProductSell = callableStatement.getLong(1);
            long remainProductSell = totalProductSell;

            if (totalProductSell == 0) {
                return;
            }

            callableStatement = dbConnection
                    .prepareCall(SP_GET_TOP_5_PRODUCT_BEST_SELLING_OF_MONTH);

            callableStatement.setInt(1, month);
            callableStatement.setInt(2, previousYear);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString(ProductSimpleModel.NAME_HEADER);
                String productSize = resultSet.getString(ProductSimpleModel.SIZE_HEADER);
                int sellAmount = resultSet.getInt("SoLuongBan");
                remainProductSell -= sellAmount;
                float percent = (float) sellAmount / totalProductSell * 100;
                String seriesName = String.format("%s - %s \n(%.2f%%)",
                        productName, productSize, percent);
                pieChartBestSalesPreviousYear.addSeries(seriesName, sellAmount);
            }

            float percent = (float) remainProductSell / totalProductSell * 100;
            String seriesName = String.format("Other (%.2f%%)", percent);
            pieChartBestSalesPreviousYear.addSeries(seriesName, remainProductSell);

        } catch (SQLException ex) {
            Logger.getLogger(ProductStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadProductSlowSellingCurrYear(int month) {
        if (month <= 0 || month >= 13) {
            throw new IllegalArgumentException();
        }

        Map<String, PieSeries> seriesMap = pieChartSlowSalesCurrYear.getSeriesMap();

        Object[] seriesNames = seriesMap.keySet().toArray();

        for (int i = 0; i < seriesNames.length; i++) {
            pieChartSlowSalesCurrYear.removeSeries((String) seriesNames[i]);
        }

        int previousYear = LocalDate.now().getYear();

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_TOTAL_SELL_AMOUNT_OF_MONTH);

            callableStatement.registerOutParameter(1, Types.INTEGER);

            callableStatement.setInt(2, month);
            callableStatement.setInt(3, previousYear);

            callableStatement.execute();

            long totalProductSell = callableStatement.getLong(1);
            long remainProductSell = totalProductSell;

            if (totalProductSell == 0) {
                return;
            }

            callableStatement = dbConnection
                    .prepareCall(SP_GET_TOP_5_PRODUCT_SLOW_SELLING_OF_MONTH);

            callableStatement.setInt(1, month);
            callableStatement.setInt(2, previousYear);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString(ProductSimpleModel.NAME_HEADER);
                String productSize = resultSet.getString(ProductSimpleModel.SIZE_HEADER);
                int sellAmount = resultSet.getInt("SoLuongBan");
                remainProductSell -= sellAmount;
                float percent = (float) sellAmount / totalProductSell * 100;
                String seriesName = String.format("%s - %s \n(%.2f%%)",
                        productName, productSize, percent);
                pieChartSlowSalesCurrYear.addSeries(seriesName, sellAmount);
            }

            float percent = (float) remainProductSell / totalProductSell * 100;
            String seriesName = String.format("Other (%.2f%%)", percent);
            pieChartSlowSalesCurrYear.addSeries(seriesName, remainProductSell);

        } catch (SQLException ex) {
            Logger.getLogger(ProductStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadProductSlowSellingPreviousYear(int month) {
        if (month <= 0 || month >= 13) {
            throw new IllegalArgumentException();
        }

        Map<String, PieSeries> seriesMap = pieChartSlowSalesPreviousYear.getSeriesMap();

        Object[] seriesNames = seriesMap.keySet().toArray();

        for (int i = 0; i < seriesNames.length; i++) {
            pieChartSlowSalesPreviousYear.removeSeries((String) seriesNames[i]);
        }

        int previousYear = LocalDate.now().getYear() - 1;

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_TOTAL_SELL_AMOUNT_OF_MONTH);

            callableStatement.registerOutParameter(1, Types.INTEGER);
            
            callableStatement.setInt(2, month);
            callableStatement.setInt(3, previousYear);
            
            callableStatement.execute();

            long totalProductSell = callableStatement.getLong(1);
            long remainProductSell = totalProductSell;
            
            if (totalProductSell == 0) {
                return;
            }

            callableStatement = dbConnection
                    .prepareCall(SP_GET_TOP_5_PRODUCT_SLOW_SELLING_OF_MONTH);

            callableStatement.setInt(1, month);
            callableStatement.setInt(2, previousYear);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString(ProductSimpleModel.NAME_HEADER);
                String productSize = resultSet.getString(ProductSimpleModel.SIZE_HEADER);
                int sellAmount = resultSet.getInt("SoLuongBan");
                remainProductSell -= sellAmount;
                float percent = (float) sellAmount / totalProductSell * 100;
                String seriesName = String.format("%s - %s \n(%.2f%%)",
                        productName, productSize, percent);
                pieChartSlowSalesPreviousYear.addSeries(seriesName, sellAmount);
            }

            float percent = (float) remainProductSell / totalProductSell * 100;
            String seriesName = String.format("Other (%.2f%%)", percent);
            pieChartSlowSalesPreviousYear.addSeries(seriesName, remainProductSell);

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
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Best Sales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(97, 97, 97))); // NOI18N

        combMonthTop.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        combMonthTop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

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
        labelMonthTop.setText("Month:");

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
                        .addComponent(combMonthTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Slow sales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(97, 97, 97))); // NOI18N

        combMonthBottom.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        combMonthBottom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

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
        labelMonthBottom.setText("Month:");

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
                        .addComponent(combMonthBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
