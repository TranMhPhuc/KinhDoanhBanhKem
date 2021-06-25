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
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.XYStyler;
import util.constant.AppConstant;
import util.db.SQLServerConnection;

public class IngredientStatisticPanel extends javax.swing.JPanel implements SettingUpdateObserver {

    private static final Color[] XYCHART_INGREDIENT_COST_MONTH_COLORS;

    private static final String SP_GET_INGREDIENT_COST_BY_MONTH
            = "{call get_ingredient_cost_by_month(?, ?)}";

    private static Connection dbConnection;

    private XYChart xyChartIngredientCost;

    static {
        dbConnection = SQLServerConnection.getConnection();
        XYCHART_INGREDIENT_COST_MONTH_COLORS = new Color[]{
            new Color(102, 187, 106), new Color(255, 202, 40)
        };
    }

    public IngredientStatisticPanel() {
        initComponents();
        configChart();
        loadIngredientCostByMonth();
    }

    private void configChart() {
        xyChartIngredientCost = new XYChartBuilder().width(1000).height(500).
                theme(Styler.ChartTheme.Matlab).build();

        XYStyler xyChartStyler = xyChartIngredientCost.getStyler();
        xyChartStyler.setChartTitleFont(AppConstant.MAIN_TITLE_FONT);
        xyChartStyler.setChartPadding(30);

        xyChartStyler.setLegendVisible(true);
        xyChartStyler.setLegendFont(AppConstant.LEGEND_FONT);
        xyChartStyler.setLegendPosition(Styler.LegendPosition.OutsideS);
        xyChartStyler.setLegendLayout(Styler.LegendLayout.Horizontal);
        xyChartStyler.setLegendBorderColor(Color.WHITE);
        xyChartStyler.setLegendSeriesLineLength(60);
        xyChartStyler.setSeriesColors(XYCHART_INGREDIENT_COST_MONTH_COLORS);

        xyChartStyler.setAxisTitleFont(AppConstant.AXIS_TITLE_FONT);
        xyChartStyler.setAxisTickLabelsFont(AppConstant.AXIS_TICK_TITLE_FONT);

        xyChartStyler.setYAxisDecimalPattern("###,###,###");
     //   xyChartStyler.setYAxisMax(1e6);
        xyChartStyler.setToolTipsEnabled(true);
        xyChartStyler.setToolTipFont(AppConstant.TOOL_TIP_FONT);

        panelIngredientCost.setLayout(new BorderLayout());
        panelIngredientCost.add(new XChartPanel<XYChart>(xyChartIngredientCost));
    }

    private void loadIngredientCostByMonth() {
        Map<String, XYSeries> seriersMap = xyChartIngredientCost.getSeriesMap();

        Object[] seriesNames = seriersMap.keySet().toArray();

        for (int i = 0; i < seriesNames.length; i++) {
            xyChartIngredientCost.removeSeries((String) seriesNames[i]);
        }

        int currYear = LocalDate.now().getYear();
        int previousYear = currYear - 1;

        List<Integer> months = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            months.add(i);
        }

        List<Long> ingredientCostOfMonthCurrYear = new ArrayList<>();
        List<Long> ingredientCostOfMonthPreviousYear = new ArrayList<>();

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_INGREDIENT_COST_BY_MONTH);

            ResultSet resultSet = null;

            for (int i = 0; i < 12; i++) {
                callableStatement.setInt(1, i);
                callableStatement.setInt(2, previousYear);

                resultSet = callableStatement.executeQuery();

                if (resultSet.next()) {
                    ingredientCostOfMonthPreviousYear.add(resultSet.getLong("ChiPhi"));
                } else {
                    ingredientCostOfMonthPreviousYear.add(0L);
                }
            }

            for (int i = 0; i < 12; i++) {
                callableStatement.setInt(1, i);
                callableStatement.setInt(2, currYear);

                resultSet = callableStatement.executeQuery();

                if (resultSet.next()) {
                    ingredientCostOfMonthCurrYear.add(resultSet.getLong("ChiPhi"));
                } else {
                    ingredientCostOfMonthCurrYear.add(0L);
                }
            }

            resultSet.close();
            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(IngredientStatisticPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        xyChartIngredientCost.addSeries(String.valueOf(previousYear), months, ingredientCostOfMonthCurrYear);
        xyChartIngredientCost.addSeries(String.valueOf(currYear), months, ingredientCostOfMonthPreviousYear);
    }

    @Override
    public void updateSettingObserver() {
        AppSetting.Language language = AppSetting.getInstance().getAppLanguage();

        LocalDate nowLocal = LocalDate.now();
        int currYear = nowLocal.getYear();

        switch (language) {
            case ENGLISH: {
                String chartTitle = "Ingredient cost statistics chart of each month of year %d and %d";
                xyChartIngredientCost.setTitle(String.format(chartTitle, currYear - 1, currYear));
                xyChartIngredientCost.setXAxisTitle("Month");
                xyChartIngredientCost.setYAxisTitle("Ingredient cost");

                break;
            }
            case VIETNAMESE: {
                String chartTitle = "Đồ thị chi phí nguyên liệu trong mỗi tháng của năm %d và %d";
                xyChartIngredientCost.setTitle(String.format(chartTitle, currYear - 1, currYear));
                xyChartIngredientCost.setXAxisTitle("Tháng");
                xyChartIngredientCost.setYAxisTitle("Chi phí nguyên liệu");
                
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelIngredientCost = new javax.swing.JPanel();

        javax.swing.GroupLayout panelIngredientCostLayout = new javax.swing.GroupLayout(panelIngredientCost);
        panelIngredientCost.setLayout(panelIngredientCostLayout);
        panelIngredientCostLayout.setHorizontalGroup(
            panelIngredientCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1206, Short.MAX_VALUE)
        );
        panelIngredientCostLayout.setVerticalGroup(
            panelIngredientCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelIngredientCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelIngredientCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelIngredientCost;
    // End of variables declaration//GEN-END:variables
}
