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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.XYStyler;
import util.constant.AppConstant;
import util.db.SQLServerConnection;
import util.swing.UIControl;

public class ProfitStatisticPanel extends javax.swing.JPanel implements SettingUpdateObserver {

    private static final int TABLE_PROFIT_MONTH_COLUMN_MONTH_INDEX = 0;

    private static final int NUMBER_OF_YEAR_PROFIT_STATISTIC = 5;

    private static final int TABLE_PROFIT_YEAR_COLUMN_YEAR_INDEX = 0;
    private static final int TABLE_PROFIT_YEAR_COLUMN_PROFIT_INDEX = 1;

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

    public ProfitStatisticPanel() {
        initComponents();
        configChartProfitByMonth();
        configChartProfitByYear();
        loadProfitByMonth();
        loadProfitByYear();
        createView();
    }

    private void createView() {
        UIControl.setDefaultTableHeader(tableProfitByMonth);
        UIControl.setDefaultTableHeader(tableProfitByYear);
        UIControl.setNumberCellRenderer(tableProfitByMonth, new int[]{1, 2});
    }

    private void configChartProfitByMonth() {
        xyChartProfitMonth = new XYChartBuilder().width(1000).height(500).
                theme(Styler.ChartTheme.Matlab).build();

        xyChartProfitMonth.setTitle("Profit statistics chart of each month of 2020 and 2021");
        xyChartProfitMonth.setXAxisTitle("Month");
        xyChartProfitMonth.setYAxisTitle("Profit");

        XYStyler xyChartStyler = xyChartProfitMonth.getStyler();
        xyChartStyler.setChartTitleFont(AppConstant.MAIN_TITLE_FONT);

        xyChartStyler.setChartPadding(30);
//        xyChartStyler.setHasAnnotations(true);

        xyChartStyler.setLegendVisible(true);
        xyChartStyler.setLegendFont(AppConstant.LEGEND_FONT);
        xyChartStyler.setLegendPosition(Styler.LegendPosition.OutsideS);
        xyChartStyler.setLegendLayout(Styler.LegendLayout.Horizontal);
        xyChartStyler.setLegendBorderColor(Color.WHITE);
        xyChartStyler.setLegendSeriesLineLength(60);

        xyChartStyler.setAxisTitleFont(AppConstant.AXIS_TITLE_FONT);
        xyChartStyler.setAxisTickLabelsFont(AppConstant.AXIS_TICK_TITLE_FONT);

        xyChartStyler.setSeriesColors(XYCHART_PROFIT_MONTH_COLORS);
        xyChartStyler.setYAxisDecimalPattern("###,###,###");
        //  xyChartStyler.setYAxisMax(1e6);

        panelRevenueMonth.setLayout(new BorderLayout());
        panelRevenueMonth.add(new XChartPanel<XYChart>(xyChartProfitMonth));
    }

    private void configChartProfitByYear() {
        categoryChartProfitYear = new CategoryChartBuilder().width(1000)
                .height(500).theme(Styler.ChartTheme.Matlab).build();

        categoryChartProfitYear.setTitle("Profit statistics chart of year from 2017 to 2021");
        categoryChartProfitYear.setXAxisTitle("Year");
        categoryChartProfitYear.setYAxisTitle("Profit");

        CategoryStyler categoryStylerProfitYear = categoryChartProfitYear.getStyler();
        categoryStylerProfitYear.setChartTitleFont(AppConstant.MAIN_TITLE_FONT);

        categoryStylerProfitYear.setChartPadding(30);
        categoryStylerProfitYear.setHasAnnotations(true);

        categoryStylerProfitYear.setLegendVisible(false);
        categoryStylerProfitYear.setSeriesColors(CATEGORYCHART_PROFIT_YEAR_COLORS);
        categoryStylerProfitYear.setAvailableSpaceFill(0.25);

        categoryStylerProfitYear.setAxisTitleFont(AppConstant.AXIS_TITLE_FONT);
        categoryStylerProfitYear.setAxisTickLabelsFont(AppConstant.AXIS_TICK_TITLE_FONT);
        //  categoryStylerProfitYear.setYAxisMax(1e7);
        categoryStylerProfitYear.setDecimalPattern("###,###,###");

        panelRevenueYear.setLayout(new BorderLayout());
        panelRevenueYear.add(new XChartPanel<CategoryChart>(categoryChartProfitYear));
    }

    private void loadProfitByMonth() {
        Map<String, XYSeries> seriersMap = xyChartProfitMonth.getSeriesMap();

        Object[] seriesNames = seriersMap.keySet().toArray();
        for (int i = 0; i < seriesNames.length; i++) {
            xyChartProfitMonth.removeSeries((String) seriesNames[i]);
        }

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

            for (int i = 1; i <= 12; i++) {
                callableStatement.setInt(1, i);
                callableStatement.setInt(2, previousYear);

                resultSet = callableStatement.executeQuery();

                if (resultSet.next()) {
                    profitOfMonthPreviousYear.add(resultSet.getLong("LoiNhuan"));
                } else {
                    profitOfMonthPreviousYear.add(0L);
                }
            }

            for (int i = 1; i <= 12; i++) {
                callableStatement.setInt(1, i);
                callableStatement.setInt(2, currYear);

                resultSet = callableStatement.executeQuery();

                if (resultSet.next()) {
                    profitOfMonthCurrYear.add(resultSet.getLong("LoiNhuan"));
                } else {
                    profitOfMonthCurrYear.add(0L);
                }
            }

            resultSet.close();
            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(ProfitStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        xyChartProfitMonth.addSeries(String.valueOf(previousYear), months, profitOfMonthPreviousYear);
        xyChartProfitMonth.addSeries(String.valueOf(currYear), months, profitOfMonthCurrYear);

        DefaultTableModel dtm = (DefaultTableModel) tableProfitByMonth.getModel();
        dtm.setRowCount(0);

        for (int i = 0; i < 12; i++) {
            dtm.addRow(new Object[]{
                i + 1,
                profitOfMonthPreviousYear.get(i),
                profitOfMonthCurrYear.get(i)
            });
        }
    }

    private void loadProfitByYear() {
        categoryChartProfitYear.removeSeries("Data");

        LocalDate nowLocal = LocalDate.now();
        int currYear = nowLocal.getYear();

        List<String> years = new ArrayList<>();

        for (int i = 5; i > 0; i--) {
            years.add(String.valueOf(currYear - i + 1));
        }

        List<Long> profitOfYears = new ArrayList<>();

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_PROFIT_BY_YEAR);

            ResultSet resultSet = null;

            for (int i = 5; i > 0; i--) {
                callableStatement.setInt(1, currYear - i + 1);
                resultSet = callableStatement.executeQuery();
                if (resultSet.next()) {
                    long profitOfYear = resultSet.getLong("LoiNhuan");
                    profitOfYears.add(profitOfYear);
                } else {
                    profitOfYears.add(0L);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProfitStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        categoryChartProfitYear.addSeries("Data", years, profitOfYears);

        DefaultTableModel dtm = (DefaultTableModel) tableProfitByYear.getModel();
        dtm.setRowCount(0);

        for (int i = 5; i > 0; i--) {
            dtm.addRow(new Object[]{
                currYear - i + 1,
                profitOfYears.get(5 - i)
            });
        }
    }

    @Override
    public void updateSettingObserver() {
        AppSetting.Language language = AppSetting.getInstance().getAppLanguage();

        LocalDate nowLocal = LocalDate.now();
        int currYear = nowLocal.getYear();

        switch (language) {
            case ENGLISH: {
                TableColumnModel tableColumnModel = tableProfitByMonth.getColumnModel();

                tableColumnModel.getColumn(TABLE_PROFIT_MONTH_COLUMN_MONTH_INDEX).setHeaderValue("Month");

                tableColumnModel = tableProfitByYear.getColumnModel();
                tableColumnModel.getColumn(TABLE_PROFIT_YEAR_COLUMN_YEAR_INDEX).setHeaderValue("Year");
                tableColumnModel.getColumn(TABLE_PROFIT_YEAR_COLUMN_PROFIT_INDEX).setHeaderValue("Profit");

                String chartTitle = "Profit statistics chart of each month of %d and %d";
                xyChartProfitMonth.setTitle(String.format(chartTitle, currYear - 1, currYear));
                xyChartProfitMonth.setXAxisTitle("Month");
                xyChartProfitMonth.setYAxisTitle("Profit");

                chartTitle = "Profit statistics chart of year from %d to %d";
                categoryChartProfitYear.setTitle(String.format(chartTitle,
                        currYear - NUMBER_OF_YEAR_PROFIT_STATISTIC + 1, currYear));
                categoryChartProfitYear.setXAxisTitle("Year");
                categoryChartProfitYear.setYAxisTitle("Profit");

                labelProfitByYearsTable.setText("Profit by years Table (VND)");
                labelProfitByMonthsTable.setText("Profit by months Table (VND)");
                break;
            }
            case VIETNAMESE: {
                TableColumnModel tableColumnModel = tableProfitByMonth.getColumnModel();

                tableColumnModel.getColumn(TABLE_PROFIT_MONTH_COLUMN_MONTH_INDEX).setHeaderValue("Tháng");

                tableColumnModel = tableProfitByYear.getColumnModel();
                tableColumnModel.getColumn(TABLE_PROFIT_YEAR_COLUMN_YEAR_INDEX).setHeaderValue("Năm");
                tableColumnModel.getColumn(TABLE_PROFIT_YEAR_COLUMN_PROFIT_INDEX).setHeaderValue("Lợi nhuận");

                String chartTitle = "Đồ thị thống kê lợi nhuận từng tháng trong năm %d và %d";
                xyChartProfitMonth.setTitle(String.format(chartTitle, currYear - 1, currYear));
                xyChartProfitMonth.setXAxisTitle("Tháng");
                xyChartProfitMonth.setYAxisTitle("Lợi nhuận");

                chartTitle = "Đồ thị thống kê lợi nhuận từ năm %d đến năm %d";
                categoryChartProfitYear.setTitle(String.format(chartTitle,
                        currYear - NUMBER_OF_YEAR_PROFIT_STATISTIC + 1, currYear));
                categoryChartProfitYear.setXAxisTitle("Năm");
                categoryChartProfitYear.setYAxisTitle("Lợi nhuận");

                labelProfitByYearsTable.setText("Bảng lợi nhuận theo năm (VND)");
                labelProfitByMonthsTable.setText("Bảng lợi nhuận theo tháng (VND)");
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelRevenueMonth = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProfitByMonth = new javax.swing.JTable();
        labelProfitByMonthsTable = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        panelRevenueYear = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableProfitByYear = new javax.swing.JTable();
        labelProfitByYearsTable = new javax.swing.JLabel();

        setLayout(new java.awt.GridLayout(2, 0, 0, 5));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        panelRevenueMonth.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout panelRevenueMonthLayout = new javax.swing.GroupLayout(panelRevenueMonth);
        panelRevenueMonth.setLayout(panelRevenueMonthLayout);
        panelRevenueMonthLayout.setHorizontalGroup(
            panelRevenueMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 535, Short.MAX_VALUE)
        );
        panelRevenueMonthLayout.setVerticalGroup(
            panelRevenueMonthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tableProfitByMonth.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tableProfitByMonth.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Month", "2020", "2021"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableProfitByMonth.setRowHeight(25);
        tableProfitByMonth.setRowMargin(5);
        tableProfitByMonth.setSelectionBackground(new java.awt.Color(113, 168, 255));
        tableProfitByMonth.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableProfitByMonth);

        labelProfitByMonthsTable.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelProfitByMonthsTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelProfitByMonthsTable.setText("Bảng lợi nhuận theo tháng (VND)");
        labelProfitByMonthsTable.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelProfitByMonthsTable, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRevenueMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelProfitByMonthsTable, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                    .addComponent(panelRevenueMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        panelRevenueYear.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout panelRevenueYearLayout = new javax.swing.GroupLayout(panelRevenueYear);
        panelRevenueYear.setLayout(panelRevenueYearLayout);
        panelRevenueYearLayout.setHorizontalGroup(
            panelRevenueYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 537, Short.MAX_VALUE)
        );
        panelRevenueYearLayout.setVerticalGroup(
            panelRevenueYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tableProfitByYear.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tableProfitByYear.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Year", "Profit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableProfitByYear.setRowHeight(25);
        tableProfitByYear.setSelectionBackground(new java.awt.Color(113, 168, 255));
        tableProfitByYear.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tableProfitByYear);

        labelProfitByYearsTable.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        labelProfitByYearsTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelProfitByYearsTable.setText("Bảng lợi nhuận theo tháng (VND)");
        labelProfitByYearsTable.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelProfitByYearsTable, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRevenueYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(labelProfitByYearsTable, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
                    .addComponent(panelRevenueYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelProfitByMonthsTable;
    private javax.swing.JLabel labelProfitByYearsTable;
    private javax.swing.JPanel panelRevenueMonth;
    private javax.swing.JPanel panelRevenueYear;
    private javax.swing.JTable tableProfitByMonth;
    private javax.swing.JTable tableProfitByYear;
    // End of variables declaration//GEN-END:variables
}
