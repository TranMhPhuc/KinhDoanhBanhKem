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
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.TitledBorder;
import model.product.ProductSimpleModel;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import org.junit.Assert;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import util.constant.AppConstant;
import util.db.SQLServerConnection;

public class ProductStatisticPanel extends javax.swing.JPanel implements SettingUpdateObserver {

    private static final String SP_GET_TOTAL_SELL_AMOUNT_OF_MONTH
            = "{? = call get_total_sell_amount_of_month(?, ?)}";

    private static final String SP_GET_TOP_5_PRODUCT_BEST_SELLING_OF_MONTH
            = "{call get_5_the_best_selling_products_by_month(?, ?)}";

    private static final String SP_GET_TOP_5_PRODUCT_SLOW_SELLING_OF_MONTH
            = "{call get_the_least_selling_products_by_month(?, ?)}";

    private static final String ENG_STRING_FORMAT_BEST_SALES_OF_YEAR
            = "Best sales statistics chart in %s, %d";
    private static final String VIE_STRING_FORMAT_BEST_SALES_OF_YEAR
            = "Đồ thị thống kê hàng bán chạy trong tháng %s của năm %d";

    private static final String ENG_STRING_FORMAT_SLOW_SALES_OF_YEAR
            = "Slow sales statistics chart in %s, %d";
    private static final String VIE_STRING_FORMAT_SLOW_SALES_OF_YEAR
            = "Đồ thị thống kê hàng bán chậm trong tháng %s của năm %d";

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

        loadProductBestSalesCurrYear(1);
        loadProductBestSalesPreviousYear(1);
        loadProductSlowSalesCurrYear(1);
        loadProductSlowSalesPreviousYear(1);

        combMonthTop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int monthIndex = combMonthTop.getSelectedIndex() + 1;
                loadProductBestSalesPreviousYear(monthIndex);
                loadProductBestSalesCurrYear(monthIndex);
                panelBestSales.repaint();
            }
        });

        combMonthBottom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int monthIndex = combMonthBottom.getSelectedIndex() + 1;
                loadProductSlowSalesPreviousYear(monthIndex);
                loadProductSlowSalesCurrYear(monthIndex);
                panelSlowSales.repaint();
            }
        });
    }

    private void configChartBestSales() {
        pieChartBestSalesPreviousYear = new PieChartBuilder().width(350).height(200)
                .theme(Styler.ChartTheme.Matlab).build();

        pieChartBestSalesCurrYear = new PieChartBuilder().width(350).height(200)
                .theme(Styler.ChartTheme.Matlab).build();

        PieChart charts[] = {
            pieChartBestSalesPreviousYear,
            pieChartBestSalesCurrYear
        };

        for (PieChart chart : charts) {
            PieStyler chartStyler = chart.getStyler();
            chartStyler.setDrawAllAnnotations(true);
            chartStyler.setAnnotationType(PieStyler.AnnotationType.Percentage);
            chartStyler.setChartPadding(30);
            chartStyler.setHasAnnotations(true);
            chartStyler.setAnnotationsFont(AppConstant.CHART_ANNOTAION_FONT);
            
            chartStyler.setDecimalPattern("#.##");
            chartStyler.setLegendVisible(true);
            chartStyler.setLegendFont(AppConstant.CHART_LEGEND_FONT);
            chartStyler.setLegendPosition(Styler.LegendPosition.OutsideE);
            chartStyler.setLegendBorderColor(Color.WHITE);
            chartStyler.setPlotBorderVisible(false);
            chartStyler.setSeriesColors(PIECHART_BEST_SALES_SIDE_COLORS);
        }

        panelTopRight.setLayout(new BorderLayout());
        panelTopRight.add(new XChartPanel<PieChart>(pieChartBestSalesCurrYear));

        panelTopLeft.setLayout(new BorderLayout());
        panelTopLeft.add(new XChartPanel<PieChart>(pieChartBestSalesPreviousYear));
    }

    private void configChartSlowSales() {
        pieChartSlowSalesPreviousYear = new PieChartBuilder().width(350).height(200)
                .theme(Styler.ChartTheme.Matlab).build();
        pieChartSlowSalesCurrYear = new PieChartBuilder().width(350).height(200)
                .theme(Styler.ChartTheme.Matlab).build();

        PieChart[] charts = new PieChart[]{
            pieChartSlowSalesPreviousYear,
            pieChartSlowSalesCurrYear
        };

        for (PieChart chart : charts) {
            PieStyler chartStyler = chart.getStyler();
            chartStyler.setDrawAllAnnotations(true);
            chartStyler.setAnnotationType(PieStyler.AnnotationType.Percentage);
            chartStyler.setChartPadding(30);
            chartStyler.setHasAnnotations(true);
            chartStyler.setAnnotationsFont(AppConstant.CHART_ANNOTAION_FONT);
            
           // chartStyler.setClockwiseDirectionType(PieStyler.ClockwiseDirectionType.COUNTER_CLOCKWISE);
            chartStyler.setAnnotationDistance(1.3);
            chartStyler.setStartAngleInDegrees(280);
            chartStyler.setDecimalPattern("#.##");
            chartStyler.setLegendVisible(true);
            chartStyler.setLegendFont(AppConstant.CHART_LEGEND_FONT);
            chartStyler.setLegendPosition(Styler.LegendPosition.OutsideE);
            chartStyler.setLegendBorderColor(Color.WHITE);
            chartStyler.setPlotBorderVisible(false);
            chartStyler.setSeriesColors(PIECHART_SLOW_SALES_SIDE_COLORS);
        }

        panelBottomRight.setLayout(new BorderLayout());
        panelBottomRight.add(new XChartPanel<PieChart>(pieChartSlowSalesCurrYear));

        panelBottomLeft.setLayout(new BorderLayout());
        panelBottomLeft.add(new XChartPanel<PieChart>(pieChartSlowSalesPreviousYear));
    }

    private void loadProductBestSalesCurrYear(int month) {
        if (month <= 0 || month >= 13) {
            throw new IllegalArgumentException();
        }

        int currYear = LocalDate.now().getYear();

        String selectedMonth = (String) combMonthTop.getSelectedItem();

        if (AppSetting.getInstance().getAppLanguage() == AppSetting.Language.ENGLISH) {
            pieChartBestSalesCurrYear.setTitle(
                    String.format(ENG_STRING_FORMAT_BEST_SALES_OF_YEAR, selectedMonth, currYear));
        } else {
            pieChartBestSalesCurrYear.setTitle(
                    String.format(VIE_STRING_FORMAT_BEST_SALES_OF_YEAR, selectedMonth, currYear));
        }

        Map<String, PieSeries> seriesMap = pieChartBestSalesCurrYear.getSeriesMap();

        Object[] seriesNames = seriesMap.keySet().toArray();

        for (int i = 0; i < seriesNames.length; i++) {
            pieChartBestSalesCurrYear.removeSeries((String) seriesNames[i]);
        }

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
                String seriesName = String.format("%s - %s", productName, productSize);
                pieChartBestSalesCurrYear.addSeries(seriesName, sellAmount);
            }
            
            pieChartBestSalesCurrYear.addSeries("Other", remainProductSell);

        } catch (SQLException ex) {
            Logger.getLogger(ProductStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadProductBestSalesPreviousYear(int month) {
        if (month <= 0 || month >= 13) {
            throw new IllegalArgumentException();
        }

        int previousYear = LocalDate.now().getYear() - 1;

        String selectedMonth = (String) combMonthTop.getSelectedItem();

        if (AppSetting.getInstance().getAppLanguage() == AppSetting.Language.ENGLISH) {
            pieChartBestSalesPreviousYear.setTitle(
                    String.format(ENG_STRING_FORMAT_BEST_SALES_OF_YEAR, selectedMonth, previousYear));
        } else {
            pieChartBestSalesPreviousYear.setTitle(
                    String.format(VIE_STRING_FORMAT_BEST_SALES_OF_YEAR, selectedMonth, previousYear));
        }

        Map<String, PieSeries> seriesMap = pieChartBestSalesPreviousYear.getSeriesMap();

        Object[] seriesNames = seriesMap.keySet().toArray();

        for (int i = 0; i < seriesNames.length; i++) {
            pieChartBestSalesPreviousYear.removeSeries((String) seriesNames[i]);
        }

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
                String seriesName = String.format("%s - %s", productName, productSize);
                pieChartBestSalesPreviousYear.addSeries(seriesName, sellAmount);
            }

            pieChartBestSalesPreviousYear.addSeries("Other", remainProductSell);

        } catch (SQLException ex) {
            Logger.getLogger(ProductStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadProductSlowSalesCurrYear(int month) {
        if (month <= 0 || month >= 13) {
            throw new IllegalArgumentException();
        }

        int currYear = LocalDate.now().getYear();

        String selectedMonth = (String) combMonthBottom.getSelectedItem();

        if (AppSetting.getInstance().getAppLanguage() == AppSetting.Language.ENGLISH) {
            pieChartSlowSalesCurrYear.setTitle(
                    String.format(ENG_STRING_FORMAT_SLOW_SALES_OF_YEAR, selectedMonth, currYear));
        } else {
            pieChartSlowSalesCurrYear.setTitle(
                    String.format(VIE_STRING_FORMAT_SLOW_SALES_OF_YEAR, selectedMonth, currYear));
        }

        Map<String, PieSeries> seriesMap = pieChartSlowSalesCurrYear.getSeriesMap();

        Object[] seriesNames = seriesMap.keySet().toArray();

        for (int i = 0; i < seriesNames.length; i++) {
            pieChartSlowSalesCurrYear.removeSeries((String) seriesNames[i]);
        }

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
                    .prepareCall(SP_GET_TOP_5_PRODUCT_SLOW_SELLING_OF_MONTH);

            callableStatement.setInt(1, month);
            callableStatement.setInt(2, currYear);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString(ProductSimpleModel.NAME_HEADER);
                String productSize = resultSet.getString(ProductSimpleModel.SIZE_HEADER);
                int sellAmount = resultSet.getInt("SoLuongBan");
                remainProductSell -= sellAmount;
                String seriesName = String.format("%s - %s", productName, productSize);
                pieChartSlowSalesCurrYear.addSeries(seriesName, sellAmount);
            }

            pieChartSlowSalesCurrYear.addSeries("Other", remainProductSell);

        } catch (SQLException ex) {
            Logger.getLogger(ProductStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadProductSlowSalesPreviousYear(int month) {
        if (month <= 0 || month >= 13) {
            throw new IllegalArgumentException();
        }

        int previousYear = LocalDate.now().getYear() - 1;

        String selectedMonth = (String) combMonthBottom.getSelectedItem();

        if (AppSetting.getInstance().getAppLanguage() == AppSetting.Language.ENGLISH) {
            pieChartSlowSalesPreviousYear.setTitle(
                    String.format(ENG_STRING_FORMAT_SLOW_SALES_OF_YEAR, selectedMonth, previousYear));
        } else {
            pieChartSlowSalesPreviousYear.setTitle(
                    String.format(VIE_STRING_FORMAT_SLOW_SALES_OF_YEAR, selectedMonth, previousYear));
        }

        Map<String, PieSeries> seriesMap = pieChartSlowSalesPreviousYear.getSeriesMap();

        Object[] seriesNames = seriesMap.keySet().toArray();

        for (int i = 0; i < seriesNames.length; i++) {
            pieChartSlowSalesPreviousYear.removeSeries((String) seriesNames[i]);
        }

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
                String seriesName = String.format("%s - %s", productName, productSize);
                pieChartSlowSalesPreviousYear.addSeries(seriesName, sellAmount);
            }

            pieChartSlowSalesPreviousYear.addSeries("Other", remainProductSell);
        } catch (SQLException ex) {
            Logger.getLogger(ProductStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateSettingObserver() {
        AppSetting.Language language = AppSetting.getInstance().getAppLanguage();

        int currYear = LocalDate.now().getYear();

        switch (language) {
            case ENGLISH: {
                TitledBorder titledBorder = (TitledBorder) panelTop.getBorder();
                titledBorder.setTitle("Best sales");

                titledBorder = (TitledBorder) panelBottom.getBorder();
                titledBorder.setTitle("Slow sales");

                labelMonthTop.setText("Month:");
                labelMonthBottom.setText("Month:");

                String selectedMonth = (String) combMonthTop.getSelectedItem();

                pieChartBestSalesPreviousYear.setTitle(
                        String.format(ENG_STRING_FORMAT_BEST_SALES_OF_YEAR, selectedMonth, (currYear - 1)));
                pieChartBestSalesCurrYear.setTitle(
                        String.format(ENG_STRING_FORMAT_BEST_SALES_OF_YEAR, selectedMonth, currYear));

                selectedMonth = (String) combMonthBottom.getSelectedItem();

                pieChartSlowSalesPreviousYear.setTitle(
                        String.format(ENG_STRING_FORMAT_SLOW_SALES_OF_YEAR, selectedMonth, (currYear - 1)));
                pieChartSlowSalesCurrYear.setTitle(
                        String.format(ENG_STRING_FORMAT_SLOW_SALES_OF_YEAR, selectedMonth, currYear));

                break;
            }
            case VIETNAMESE: {
                TitledBorder titledBorder = (TitledBorder) panelTop.getBorder();
                titledBorder.setTitle("Hàng bán chạy");

                titledBorder = (TitledBorder) panelBottom.getBorder();
                titledBorder.setTitle("Hàng bán chậm");

                labelMonthTop.setText("Tháng:");
                labelMonthBottom.setText("Tháng:");

                String selectedMonth = (String) combMonthTop.getSelectedItem();

                pieChartBestSalesPreviousYear.setTitle(
                        String.format(VIE_STRING_FORMAT_BEST_SALES_OF_YEAR, selectedMonth, (currYear - 1)));
                pieChartBestSalesCurrYear.setTitle(
                        String.format(VIE_STRING_FORMAT_BEST_SALES_OF_YEAR, selectedMonth, currYear));

                selectedMonth = (String) combMonthBottom.getSelectedItem();

                pieChartSlowSalesPreviousYear.setTitle(
                        String.format(VIE_STRING_FORMAT_SLOW_SALES_OF_YEAR, selectedMonth, (currYear - 1)));
                pieChartSlowSalesCurrYear.setTitle(
                        String.format(VIE_STRING_FORMAT_SLOW_SALES_OF_YEAR, selectedMonth, currYear));

                break;
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTop = new javax.swing.JPanel();
        combMonthTop = new javax.swing.JComboBox<>();
        panelBestSales = new javax.swing.JPanel();
        panelTopLeft = new javax.swing.JPanel();
        panelTopRight = new javax.swing.JPanel();
        labelMonthTop = new javax.swing.JLabel();
        panelBottom = new javax.swing.JPanel();
        combMonthBottom = new javax.swing.JComboBox<>();
        panelSlowSales = new javax.swing.JPanel();
        panelBottomLeft = new javax.swing.JPanel();
        panelBottomRight = new javax.swing.JPanel();
        labelMonthBottom = new javax.swing.JLabel();

        panelTop.setBackground(new java.awt.Color(255, 255, 255));
        panelTop.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Best Sales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(97, 97, 97))); // NOI18N

        combMonthTop.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        combMonthTop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        panelBestSales.setBackground(new java.awt.Color(204, 204, 204));
        panelBestSales.setLayout(new java.awt.GridLayout(1, 0, 3, 0));

        panelTopLeft.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTopLeftLayout = new javax.swing.GroupLayout(panelTopLeft);
        panelTopLeft.setLayout(panelTopLeftLayout);
        panelTopLeftLayout.setHorizontalGroup(
            panelTopLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        panelTopLeftLayout.setVerticalGroup(
            panelTopLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );

        panelBestSales.add(panelTopLeft);

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

        panelBestSales.add(panelTopRight);

        labelMonthTop.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelMonthTop.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelMonthTop.setText("Month:");

        javax.swing.GroupLayout panelTopLayout = new javax.swing.GroupLayout(panelTop);
        panelTop.setLayout(panelTopLayout);
        panelTopLayout.setHorizontalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBestSales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelTopLayout.createSequentialGroup()
                        .addComponent(labelMonthTop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combMonthTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTopLayout.setVerticalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelMonthTop, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(combMonthTop, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBestSales, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBottom.setBackground(new java.awt.Color(255, 255, 255));
        panelBottom.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Slow sales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(97, 97, 97))); // NOI18N

        combMonthBottom.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        combMonthBottom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        panelSlowSales.setBackground(new java.awt.Color(204, 204, 204));
        panelSlowSales.setLayout(new java.awt.GridLayout(1, 0, 3, 0));

        panelBottomLeft.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelBottomLeftLayout = new javax.swing.GroupLayout(panelBottomLeft);
        panelBottomLeft.setLayout(panelBottomLeftLayout);
        panelBottomLeftLayout.setHorizontalGroup(
            panelBottomLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        panelBottomLeftLayout.setVerticalGroup(
            panelBottomLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );

        panelSlowSales.add(panelBottomLeft);

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

        panelSlowSales.add(panelBottomRight);

        labelMonthBottom.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelMonthBottom.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelMonthBottom.setText("Month:");

        javax.swing.GroupLayout panelBottomLayout = new javax.swing.GroupLayout(panelBottom);
        panelBottom.setLayout(panelBottomLayout);
        panelBottomLayout.setHorizontalGroup(
            panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBottomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSlowSales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBottomLayout.createSequentialGroup()
                        .addComponent(labelMonthBottom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combMonthBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelBottomLayout.setVerticalGroup(
            panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBottomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMonthBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combMonthBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSlowSales, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> combMonthBottom;
    private javax.swing.JComboBox<String> combMonthTop;
    private javax.swing.JLabel labelMonthBottom;
    private javax.swing.JLabel labelMonthTop;
    private javax.swing.JPanel panelBestSales;
    private javax.swing.JPanel panelBottom;
    private javax.swing.JPanel panelBottomLeft;
    private javax.swing.JPanel panelBottomRight;
    private javax.swing.JPanel panelSlowSales;
    private javax.swing.JPanel panelTop;
    private javax.swing.JPanel panelTopLeft;
    private javax.swing.JPanel panelTopRight;
    // End of variables declaration//GEN-END:variables
}
