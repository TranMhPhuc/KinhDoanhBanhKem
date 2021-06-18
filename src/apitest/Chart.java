/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apitest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JTable;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.CategorySeries;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.XYStyler;

public class Chart extends Thread {

    /**
     * @param args the command line arguments
     */
    private JTable table;
    private String col1;
    private String col2;

    private static final Font MAIN_TITLE_FONT = new Font("Segoe UI", Font.BOLD, 30);
    private static final Font AXIS_TITLE_FONT = new Font("Segoe UI", Font.PLAIN, 25);
    private static final Font AXIS_TICK_TITLE_FONT = new Font("Segoe UI", Font.ITALIC, 17);
    private static final Font TOOL_TIP_FONT = new Font("Segoe UI", Font.ITALIC, 15);
    private static final Color SERI_1_COLOR = new Color(248, 118, 109);
    private static final Font LEGEND_FONT = new Font("Segoe UI", Font.PLAIN, 20);
    private static final Font SUM_FONT = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font DELETE_FONT = new Font("Segoe UI", Font.BOLD, 2);

    private SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy");

    public Chart(String col1, String col2, JTable table) {
        this.col1 = col1;
        this.col2 = col2;
        this.table = table;
    }

    public Chart() {

    }

    @Override
    public void run() {
        super.run();
        //draw cả 3 để test
        drawLineChart("Tiêu đề", "Date", "Số lượng");
        drawPieChart("Tiêu đề", "Sản phẩm", "Số lượng");
        drawSingleBarChart("Tiêu đề", "Sản phẩm", "Số lượng");
    }

    public int indexOfCol(String col, JTable table) {
        int colCount = table.getModel().getColumnCount();
        int index = 0;
        for (int i = 0; i < colCount; i++) {
            if (table.getModel().getColumnName(i).equals(col)) {
                return i;
            }
        }
        return index;
    }

    public ArrayList<String> xData(String col, JTable table) {
        ArrayList<String> list = new ArrayList<>();
        int indOfCol = indexOfCol(col, table);
        int countRow = table.getModel().getRowCount();
        int i = 0;
        while (i < countRow) {
            list.add(table.getModel().getValueAt(i, indOfCol).toString());
            System.out.println(list.get(i));
            i++;
        }
        return list;
    }

    public ArrayList<Date> xDataDate(String col, JTable table) {
        ArrayList<Date> list = new ArrayList<>();
        int indOfCol = indexOfCol(col, table);
        int countRow = table.getModel().getRowCount();
        int i = 0;
        try {
            while (i < countRow) {
                Date date = spd.parse(table.getModel().getValueAt(i, indOfCol).toString());
                list.add(date);
                System.out.println(list.get(i));
                i++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return list;
        }

    }

    public ArrayList<Double> convertStringtoDouble(ArrayList arrL) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < arrL.size(); i++) {
            list.add(Double.parseDouble((String) arrL.get(i)));
            list.get(i);
        }
        return list;
    }

    public void drawSingleBarChart(String mainTitle, String xTitle, String yTitle) {

        CategoryChart barChart = new CategoryChartBuilder().width(1000).height(700).theme(ChartTheme.Matlab).build();
        CategoryStyler style = barChart.getStyler();

        barChart.setTitle(mainTitle);
        barChart.setXAxisTitle(xTitle);
        barChart.setYAxisTitle(yTitle);

        ArrayList<String> xData = xData(this.col1, this.table);
        ArrayList<String> yDataTemp = xData(this.col2, this.table);
        ArrayList<Double> yData = convertStringtoDouble(yDataTemp);

        CategorySeries seri = barChart.addSeries("Data", xData, yData);
        seri.setFillColor(SERI_1_COLOR);

        style.setLegendVisible(false);

        style.setChartTitleFont(MAIN_TITLE_FONT);
        style.setAxisTitleFont(AXIS_TITLE_FONT);
        style.setAxisTickLabelsFont(AXIS_TICK_TITLE_FONT);

        style.setToolTipsEnabled(true);

        style.setToolTipFont(TOOL_TIP_FONT);
        style.setToolTipHighlightColor(SERI_1_COLOR);
        style.setToolTipsAlwaysVisible(true);
        style.setChartFontColor(Color.RED);

        JFrame frame = new SwingWrapper(barChart).displayChart();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        // frame.setResizable(false);
        frame.setLocation(new Point(300, 0));

    }

    public void drawPieChart(String mainTitle, String xTitle, String yTitle) {

        PieChart pieChart = new PieChartBuilder().width(1000).height(700).theme(ChartTheme.Matlab).build();
        PieStyler style = pieChart.getStyler();

        pieChart.setTitle(mainTitle);
        pieChart.setXAxisTitle(xTitle);
        pieChart.setYAxisTitle(yTitle);

        ArrayList<String> xData = xData(this.col1, this.table);
        ArrayList<String> yDataTemp = xData(this.col2, this.table);
        ArrayList<Double> yData = convertStringtoDouble(yDataTemp);

        for (int i = 0; i < xData.size(); i++) {
            pieChart.addSeries(xData.get(i), yData.get(i));
        }

        style.setLegendVisible(false);

        style.setChartTitleFont(MAIN_TITLE_FONT);
        
        style.setLegendFont(LEGEND_FONT);
        style.setSumFont(SUM_FONT);
        style.setSumVisible(true);

        style.setYAxisTitleColor(SERI_1_COLOR);
        style.setPlotBorderVisible(false);

//        style.setToolTipsEnabled(true);
        style.setToolTipsEnabled(false);
        style.setToolTipsAlwaysVisible(false);
        style.setToolTipFont(TOOL_TIP_FONT);
        style.setToolTipHighlightColor(Color.YELLOW);
        style.setAnnotationsFont(DELETE_FONT);

        style.setChartFontColor(Color.RED);

        JFrame frame = new SwingWrapper(pieChart).displayChart();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        // frame.setResizable(false);
        frame.setLocation(new Point(600, 0));

    }

    public void drawLineChart(String mainTitle, String xTitle, String yTitle) {

        XYChart lineChart = new XYChartBuilder().width(1000).height(700).theme(ChartTheme.Matlab).build();
        XYStyler style = lineChart.getStyler();

        lineChart.setTitle(mainTitle);
        lineChart.setXAxisTitle(xTitle);
        lineChart.setYAxisTitle(yTitle);

        ArrayList<Date> xData = xDataDate(this.col1, this.table);
        ArrayList<String> yDataTemp = xData(this.col2, this.table);
        ArrayList<Double> yData = convertStringtoDouble(yDataTemp);

        XYSeries seri = lineChart.addSeries("Data", xData, yData);
        seri.setFillColor(SERI_1_COLOR);

        style.setLegendVisible(false);

        style.setChartTitleFont(MAIN_TITLE_FONT);
        style.setAxisTitleFont(AXIS_TITLE_FONT);
        style.setAxisTickLabelsFont(AXIS_TICK_TITLE_FONT);

        style.setToolTipsEnabled(true);
        style.setToolTipsAlwaysVisible(true);
        style.setToolTipFont(TOOL_TIP_FONT);
        style.setToolTipHighlightColor(SERI_1_COLOR);
        style.setChartTitleBoxVisible(false);
        style.setChartFontColor(Color.RED);
        style.setDatePattern("dd/MM");

        JFrame frame = new SwingWrapper(lineChart).displayChart();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocation(new Point(0, 0));
        // frame.setResizable(false);

    }

}
