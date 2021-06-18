package view.main.accountant;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.XYStyler;
import util.db.SQLServerConnection;

public class ProfitPanel extends javax.swing.JPanel {

    private static final Color[] XYCHART_PROFIT_MONTH_COLORS;
    private static final Color[] CATEGORYCHART_PROFIT_YEAR_COLORS;

    private static final String SP_GET_PROFIT_BY_MONTH
            = "{call get_profit_by_month(?, ?)}";

    private static final String SP_GET_PROFIT_BY_YEAR
            = "{call get_profit_by_year(?)}";

    private static Connection dbConnection;

    private XYChart xyChartProfitMonth;
    private CategoryChart categoryChartProfitYear;

    static {
        dbConnection = SQLServerConnection.getConnection();
        XYCHART_PROFIT_MONTH_COLORS = new Color[]{
            new Color(102, 187, 106), new Color(255, 202, 40)
        };
        CATEGORYCHART_PROFIT_YEAR_COLORS = new Color[]{
            new Color(66, 165, 245)
        };
    }

    public ProfitPanel() {
        initComponents();
        configChart();
        loadProfitByMonth();
        loadProfitByYear();
    }

    private void configChart() {
        xyChartProfitMonth = new XYChartBuilder().width(1000).height(500).
                theme(Styler.ChartTheme.Matlab).build();

        xyChartProfitMonth.setTitle("Profit of 12 months of this year compare with last year");
        xyChartProfitMonth.setXAxisTitle("Month");
        xyChartProfitMonth.setYAxisTitle("Profit");

        XYStyler xyChartStyler = xyChartProfitMonth.getStyler();
        xyChartStyler.setLegendVisible(true);
        xyChartStyler.setLegendPosition(Styler.LegendPosition.InsideNW);
        xyChartStyler.setSeriesColors(XYCHART_PROFIT_MONTH_COLORS);

        panelRevenueMonth.setLayout(new BorderLayout());
        panelRevenueMonth.add(new XChartPanel<XYChart>(xyChartProfitMonth));

        categoryChartProfitYear = new CategoryChartBuilder().width(1000)
                .height(500).theme(Styler.ChartTheme.Matlab).build();

        categoryChartProfitYear.setTitle("Profit of 5 years nearby");
        categoryChartProfitYear.setXAxisTitle("Year");
        categoryChartProfitYear.setYAxisTitle("Profit");

        CategoryStyler categoryStylerProfitYear = categoryChartProfitYear.getStyler();
        categoryStylerProfitYear.setLegendVisible(false);
        categoryStylerProfitYear.setPlotBorderVisible(false);
        categoryStylerProfitYear.setSeriesColors(CATEGORYCHART_PROFIT_YEAR_COLORS);

        panelRevenueYear.setLayout(new BorderLayout());
        panelRevenueYear.add(new XChartPanel<CategoryChart>(categoryChartProfitYear));
    }

    private void loadProfitByMonth() {
        xyChartProfitMonth.removeSeries("Data1");
        xyChartProfitMonth.removeSeries("Data2");

        int currYear = LocalDate.now().getYear();
        int previousYear = currYear - 1;
        
        List<Integer> months = new ArrayList<>();
        
        for (int i = 1; i <= 12; i++) {
            months.add(i);
        }
        
        List<Long> profitOfMonthCurrYear = new ArrayList<>();
        List<Long> profitOfMonthPreviousYear = new ArrayList<>();

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_PROFIT_BY_MONTH);

            ResultSet resultSet = null;

            for (int i = 0; i < 12; i++) {
                callableStatement.setInt(1, i);
                callableStatement.setInt(2, currYear);

                resultSet = callableStatement.executeQuery();

                if (resultSet.next()) {
                    profitOfMonthCurrYear.add(resultSet.getLong("LoiNhuan"));
                } else {
                    profitOfMonthCurrYear.add(0L);
                }
            }

            for (int i = 0; i < 12; i++) {
                callableStatement.setInt(1, i);
                callableStatement.setInt(2, previousYear);

                resultSet = callableStatement.executeQuery();

                if (resultSet.next()) {
                    profitOfMonthPreviousYear.add(resultSet.getLong("LoiNhuan"));
                } else {
                    profitOfMonthPreviousYear.add(0L);
                }
            }

            resultSet.close();
            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(ProfitPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        xyChartProfitMonth.addSeries("Data1", months, profitOfMonthCurrYear);
        xyChartProfitMonth.addSeries("Data2", months, profitOfMonthCurrYear);
    }

    private void loadProfitByYear() {
        categoryChartProfitYear.removeSeries("Data");

        LocalDate nowLocal = LocalDate.now();
        int currYear = nowLocal.getYear();
        
        List<Long> profitOfYears = new ArrayList<>();
        List<String> years = new ArrayList<>();

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_PROFIT_BY_YEAR);

            ResultSet resultSet = null;

            for (int i = 5; i > 0; i--) {
                years.add(String.valueOf(currYear - i + 1));
                
                callableStatement.setInt(1, currYear - i + 1);
                
                resultSet = callableStatement.executeQuery();

                if (resultSet.next()) {
                    profitOfYears.add(resultSet.getLong("LoiNhuan"));
                } else {
                    profitOfYears.add(0L);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProfitPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        categoryChartProfitYear.addSeries("Data", years, profitOfYears);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRevenueMonth = new javax.swing.JPanel();
        panelRevenueYear = new javax.swing.JPanel();

        setLayout(new java.awt.GridLayout(2, 0, 0, 20));

        panelRevenueMonth.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelRevenueMonthLayout = new javax.swing.GroupLayout(panelRevenueMonth);
        panelRevenueMonth.setLayout(panelRevenueMonthLayout);
        panelRevenueMonthLayout.setHorizontalGroup(
            panelRevenueMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelRevenueMonthLayout.setVerticalGroup(
            panelRevenueMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );

        add(panelRevenueMonth);

        panelRevenueYear.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelRevenueYearLayout = new javax.swing.GroupLayout(panelRevenueYear);
        panelRevenueYear.setLayout(panelRevenueYearLayout);
        panelRevenueYearLayout.setHorizontalGroup(
            panelRevenueYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1184, Short.MAX_VALUE)
        );
        panelRevenueYearLayout.setVerticalGroup(
            panelRevenueYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );

        add(panelRevenueYear);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelRevenueMonth;
    private javax.swing.JPanel panelRevenueYear;
    // End of variables declaration//GEN-END:variables
}
