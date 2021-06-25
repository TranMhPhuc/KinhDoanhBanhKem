package view.main.manager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import model.employee.EmployeeModel;
import model.employee.EmployeeModelInterface;
import model.ingredient.IngredientModel;
import model.ingredient.IngredientModelInterface;
import model.ingredient.type.IngredientTypeModel;
import model.ingredient.type.IngredientTypeModelInterface;
import model.product.ProductModelInterface;
import model.product.ProductSimpleModel;
import model.provider.ProviderModel;
import model.provider.ProviderModelInterface;
import model.setting.AppSetting;
import model.setting.SettingUpdateObserver;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import util.constant.AppConstant;
import util.db.SQLServerConnection;
import view.employee.InsertedEmployeeObserver;
import view.employee.ModifiedEmployeeObserver;
import view.ingredient.InsertedIngredientObserver;
import view.ingredient.InsertedIngredientTypeObserver;
import view.ingredient.ModifiedIngredientObserver;
import view.ingredient.RemovedIngredientObserver;
import view.product.InsertedProductObserver;
import view.product.ModifiedProductObserver;
import view.product.RemovedProductObserver;
import view.provider.InsertedProviderObserver;
import view.provider.ModifiedProviderObserver;
import view.provider.RemovedProviderObserver;

public class HomePanel extends javax.swing.JPanel implements SettingUpdateObserver,
        InsertedProductObserver, ModifiedProductObserver, RemovedProductObserver,
        InsertedIngredientObserver, ModifiedIngredientObserver, RemovedIngredientObserver,
        InsertedIngredientTypeObserver, InsertedProviderObserver, ModifiedProviderObserver,
        RemovedProviderObserver, InsertedEmployeeObserver, ModifiedEmployeeObserver {

    private static final Color[] CATEGORYCHART_PROVIDER_SIDE_COLORS;
    private static final Color[] PIECHART_PRODUCT_SIDE_COLORS;
    private static final Color[] PIECHART_INGREDIENT_SIDE_COLORS;
    private static final Color[] PIECHART_EMPLOYEE_POSITION_SIDE_COLORS;
    private static final Color[] PIECHART_EMPLOYEE_STATUS_SIDE_COLORS;

    private static final String FT_GET_PRODUCT_RECORD_COUNT
            = "{? = call get_product_record_count}";
    private static final String FT_GET_INGREDIENT_RECORD_COUNT
            = "{? = call get_ingredient_record_count}";
    private static final String FT_GET_PROVIDER_RECORD_COUNT
            = "{? = call get_provider_record_count}";
    private static final String FT_GET_EMPLOYEE_RECORD_COUNT
            = "{? = call get_employee_record_count}";

    private static final String SP_GET_PRODUCT_SIZE_STATISTICS
            = "{call get_product_size_statistics}";

    private static final String SP_GET_INGREDIENT_TYPE_STATISTICS
            = "{call get_ingredient_type_statistics}";

    private static final String SP_GET_EMPLOYEE_POSITION_STATISTICS
            = "{call get_employee_position_statistics}";

    private static final String SP_GET_EMPLOYEE_STATUS_STATISTICS
            = "{call get_employee_status_statistics}";

    private static final String SP_GET_PROVIDER_INGREDIENT_NUMBER_STATISTICS
            = "{call get_provider_ingredient_statistics}";

    // Diagnostics
    private static final String SP_GET_PRODUCT_ZERO_AMOUNT
            = "{call get_product_zero_amount}";

    private static final String SP_GET_INGREDIENT_ZERO_AMOUNT
            = "{call get_ingredient_zero_amount}";

    private static final String SP_GET_PROVIDER_NO_INGREDIENT
            = "{call get_provider_no_ingredient}";

    private static final String DIAGNOSTIC_ROOT_STRING_FORMAT
            = "%d total problem(s) found";

    private static final String DIAGNOSTIC_PRODUCT_PROBLEM_SUMMARY_STRING_FORMAT
            = "%d product(s) having zero amount";

    private static final String DIAGNOSTIC_INGREDIENT_PROBLEM_SUMMARY_STRING_FORMAT
            = "%d ingredient(s) having zero amount";

    private static final String DIAGNOSTIC_PROVIDER_PROBLEM_SUMMARY_STRING_FORMAT
            = "%d provider(s) having no ingredient belongs to";

    private CategoryChart categoryChartProviderState;

    private PieChart pieChartProductState;
    private PieChart pieChartIngredientState;
    private PieChart pieChartEmployeeStatusState;
    private PieChart pieChartEmployeePositionState;

    private ArrayList<String> pieChartProductStateSeriesNames;
    private ArrayList<String> pieChartIngredientStateSeriesNames;
    private ArrayList<String> pieChartEmployeeStatusStateSeriesNames;
    private ArrayList<String> pieChartEmployeePositionStateSeriesNames;

    private static Connection dbConnection;

    private boolean diagnosticPassFlag;

    private DefaultTreeModel diagnosticTreeModel;

    private String[] sqlFunctionGetStatisticNumber = new String[]{
        FT_GET_PRODUCT_RECORD_COUNT, FT_GET_INGREDIENT_RECORD_COUNT, FT_GET_PROVIDER_RECORD_COUNT, FT_GET_EMPLOYEE_RECORD_COUNT
    };

    private JLabel[] statisticsNumberLabels;

    static {
        dbConnection = SQLServerConnection.getConnection();
        CATEGORYCHART_PROVIDER_SIDE_COLORS = new Color[]{
            new Color(66, 165, 245)
        };
        PIECHART_PRODUCT_SIDE_COLORS = new Color[]{
            new Color(224, 68, 14), new Color(230, 105, 62), new Color(236, 143, 110), new Color(243, 180, 159)
        };
        PIECHART_INGREDIENT_SIDE_COLORS = new Color[]{
            new Color(236, 64, 122), new Color(171, 71, 188), new Color(126, 87, 194), new Color(92, 107, 192),
            new Color(66, 165, 245), new Color(41, 182, 246), new Color(38, 198, 218), new Color(38, 166, 154),
            new Color(102, 187, 106), new Color(156, 204, 101), new Color(212, 225, 87), new Color(255, 202, 40),
            new Color(255, 167, 38), new Color(255, 112, 67), new Color(93, 64, 55), new Color(97, 97, 97)
        };
        PIECHART_EMPLOYEE_POSITION_SIDE_COLORS = new Color[]{
            new Color(0, 176, 255), new Color(41, 121, 255), new Color(29, 233, 182)
        };
        PIECHART_EMPLOYEE_STATUS_SIDE_COLORS = new Color[]{
            new Color(165, 214, 167), new Color(76, 175, 80)
        };
    }

    public HomePanel() {
        initComponents();

        statisticsNumberLabels = new JLabel[]{
            labelProductNumber, labelIngredientNumber, labelProviderNumber, labelEmployeeNumber
        };

        pieChartProductStateSeriesNames = new ArrayList<>();
        pieChartIngredientStateSeriesNames = new ArrayList<>();
        pieChartEmployeeStatusStateSeriesNames = new ArrayList<>();
        pieChartEmployeePositionStateSeriesNames = new ArrayList<>();

        diagnosticPassFlag = true;
        diagnosticTreeModel = (DefaultTreeModel) diagnosticTree.getModel();

        configChart();
        loadStatisticNumberData();
        loadProductState();
        loadIngredientState();
        loadEmployeePositionState();
        loadEmployeeStatusState();
        loadProviderState();
        loadDiagnostics();

        ckbTurnOffDiagnostic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppSetting.getInstance().setDiagnosticFlag(!ckbTurnOffDiagnostic.isSelected());
            }
        });
    }

    public void loadStatisticNumberData() {
        try {
            CallableStatement callableStatement = null;

            for (int i = 0; i < sqlFunctionGetStatisticNumber.length; i++) {
                callableStatement = dbConnection.prepareCall(sqlFunctionGetStatisticNumber[i]);
                callableStatement.registerOutParameter(1, Types.INTEGER);
                callableStatement.execute();
                int recordNum = callableStatement.getInt(1);
                statisticsNumberLabels[i].setText(String.valueOf(recordNum));
            }

            if (callableStatement != null) {
                callableStatement.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(HomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configChart() {
        categoryChartProviderState = new CategoryChartBuilder().width(600)
                .height(300).theme(Styler.ChartTheme.Matlab).build();

        categoryChartProviderState.setTitle("Number Of Ingredient Belongs To Provider");
        categoryChartProviderState.setXAxisTitle("Provider");
        categoryChartProviderState.setYAxisTitle("Number of ingredient");

        CategoryStyler categoryChartProviderStateStyle = categoryChartProviderState.getStyler();

        categoryChartProviderStateStyle.setHasAnnotations(true);
        categoryChartProviderStateStyle.setChartPadding(30);

        categoryChartProviderStateStyle.setLegendVisible(false);
        categoryChartProviderStateStyle.setPlotBorderVisible(false);
        categoryChartProviderStateStyle.setSeriesColors(CATEGORYCHART_PROVIDER_SIDE_COLORS);
        categoryChartProviderStateStyle.setAvailableSpaceFill(0.4);
//        categoryChartProviderStateStyle.setXAxisTicksVisible(false);

        categoryChartProviderStateStyle.setAxisTitleFont(AppConstant.AXIS_TITLE_FONT);
        categoryChartProviderStateStyle.setAxisTickLabelsFont(AppConstant.AXIS_TICK_TITLE_FONT);

        categoryChartProviderStateStyle.setAnnotationsFont(AppConstant.ANNO_FONT);
        categoryChartProviderStateStyle.setLegendFont(AppConstant.LEGEND_FONT);
        panelStateProvider.setLayout(new BorderLayout());
        panelStateProvider.add(new XChartPanel<CategoryChart>(categoryChartProviderState));

        //
        pieChartProductState = new PieChartBuilder().width(400).height(300)
                .theme(Styler.ChartTheme.Matlab).build();

        pieChartProductState.setTitle("Product size statistics");

        PieStyler pieChartProductStateStyle = pieChartProductState.getStyler();

        pieChartProductStateStyle.setDrawAllAnnotations(true);
        pieChartProductStateStyle.setAnnotationType(PieStyler.AnnotationType.Percentage);
        pieChartProductStateStyle.setLegendVisible(true);
        pieChartProductStateStyle.setPlotBorderVisible(false);
        pieChartProductStateStyle.setSeriesColors(PIECHART_PRODUCT_SIDE_COLORS);
        pieChartProductStateStyle.setAnnotationsFont(AppConstant.ANNO_FONT);
        pieChartProductStateStyle.setLegendFont(AppConstant.LEGEND_FONT);
        panelStateProduct.setLayout(new BorderLayout());
        panelStateProduct.add(new XChartPanel<PieChart>(pieChartProductState));

        //
        pieChartIngredientState = new PieChartBuilder().width(400).height(300)
                .theme(Styler.ChartTheme.Matlab).build();

        pieChartIngredientState.setTitle("Ingredient type statistics");

        PieStyler pieChartIngredientStateStyle = pieChartIngredientState.getStyler();

        pieChartIngredientStateStyle.setDrawAllAnnotations(true);
        pieChartIngredientStateStyle.setAnnotationType(PieStyler.AnnotationType.Percentage);
        pieChartIngredientStateStyle.setLegendVisible(true);
        pieChartIngredientStateStyle.setPlotBorderVisible(false);
        pieChartIngredientStateStyle.setSeriesColors(PIECHART_INGREDIENT_SIDE_COLORS);
        pieChartIngredientStateStyle.setLegendFont(AppConstant.LEGEND_FONT);
        pieChartIngredientStateStyle.setAnnotationsFont(AppConstant.ANNO_FONT);
        panelStateIngredient.setLayout(new BorderLayout());
        panelStateIngredient.add(new XChartPanel<PieChart>(pieChartIngredientState));

        //
        pieChartEmployeePositionState = new PieChartBuilder().width(250).height(100)
                .theme(Styler.ChartTheme.Matlab).build();

        pieChartEmployeePositionState.setTitle("Employee position statistics");

        PieStyler pieChartEmployeePositionStateStyle = pieChartEmployeePositionState.getStyler();

        pieChartEmployeePositionStateStyle.setDrawAllAnnotations(true);
        pieChartEmployeePositionStateStyle.setAnnotationType(PieStyler.AnnotationType.Percentage);
        pieChartEmployeePositionStateStyle.setLegendVisible(true);
        pieChartEmployeePositionStateStyle.setPlotBorderVisible(false);
        pieChartEmployeePositionStateStyle.setSeriesColors(PIECHART_EMPLOYEE_POSITION_SIDE_COLORS);
        pieChartEmployeePositionStateStyle.setLegendFont(AppConstant.LEGEND_FONT);
        pieChartEmployeePositionStateStyle.setAnnotationsFont(AppConstant.ANNO_FONT);
        panelStateEmployeePosition.setLayout(new BorderLayout());
        panelStateEmployeePosition.add(new XChartPanel<PieChart>(pieChartEmployeePositionState));

        //
        pieChartEmployeeStatusState = new PieChartBuilder().width(250).height(100)
                .theme(Styler.ChartTheme.Matlab).build();

        pieChartEmployeeStatusState.setTitle("Employee working status statistics");

        PieStyler pieChartEmployeeStatusStateStyle = pieChartEmployeeStatusState.getStyler();

        pieChartEmployeeStatusStateStyle.setDrawAllAnnotations(true);
        pieChartEmployeeStatusStateStyle.setAnnotationType(PieStyler.AnnotationType.Percentage);
        pieChartEmployeeStatusStateStyle.setLegendVisible(true);
        pieChartEmployeeStatusStateStyle.setPlotBorderVisible(false);
        pieChartEmployeeStatusStateStyle.setSeriesColors(PIECHART_EMPLOYEE_STATUS_SIDE_COLORS);
        pieChartEmployeeStatusStateStyle.setLegendFont(AppConstant.LEGEND_FONT);
        pieChartEmployeeStatusStateStyle.setAnnotationsFont(AppConstant.ANNO_FONT);
        panelStateEmployeeStatus.setLayout(new BorderLayout());
        panelStateEmployeeStatus.add(new XChartPanel<PieChart>(pieChartEmployeeStatusState));
    }

    private void loadProviderState() {
        categoryChartProviderState.removeSeries("Data");

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_PROVIDER_INGREDIENT_NUMBER_STATISTICS);

            ResultSet resultSet = callableStatement.executeQuery();

            List<String> providerIDs = new ArrayList<>();
            List<Integer> importedAmounts = new ArrayList<>();

            while (resultSet.next()) {
                String providerID = resultSet.getString(ProviderModel.ID_HEADER);
                int importedAmount = resultSet.getInt(2);
                providerIDs.add(providerID);
                importedAmounts.add(importedAmount);
            }

            resultSet.close();
            callableStatement.close();

            categoryChartProviderState.addSeries("Data", providerIDs, importedAmounts);

        } catch (SQLException ex) {
            Logger.getLogger(HomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadProductState() {
        Map<String, PieSeries> seriesMap = pieChartProductState.getSeriesMap();

        Object[] seriesNames = seriesMap.keySet().toArray();
        for (int i = 0; i < seriesNames.length; i++) {
            pieChartProductState.removeSeries((String) seriesNames[i]);
        }

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(FT_GET_PRODUCT_RECORD_COUNT);

            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();

            callableStatement = dbConnection
                    .prepareCall(SP_GET_PRODUCT_SIZE_STATISTICS);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String productSize = resultSet.getString(ProductSimpleModel.SIZE_HEADER);
                int productAmount = resultSet.getInt(2);
                pieChartProductState.addSeries("Size " + productSize, productAmount);
            }

            resultSet.close();
            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(HomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadIngredientState() {
        Map<String, PieSeries> seriesMap = pieChartIngredientState.getSeriesMap();

        Object[] seriesNames = seriesMap.keySet().toArray();
        for (int i = 0; i < seriesNames.length; i++) {
            pieChartIngredientState.removeSeries((String) seriesNames[i]);
        }

        try {
            CallableStatement callableStatement;

            callableStatement = dbConnection.prepareCall(FT_GET_INGREDIENT_RECORD_COUNT);
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();

            callableStatement = dbConnection
                    .prepareCall(SP_GET_INGREDIENT_TYPE_STATISTICS);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String ingredientTypeName = resultSet.getString(IngredientTypeModel.NAME_HEADER);
                int amount = resultSet.getInt(2);
                pieChartIngredientState.addSeries(ingredientTypeName, amount);
            }

            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(HomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadEmployeePositionState() {
        Map<String, PieSeries> seriesMap = pieChartEmployeePositionState.getSeriesMap();

        Object[] seriesNames = seriesMap.keySet().toArray();
        for (int i = 0; i < seriesNames.length; i++) {
            pieChartEmployeePositionState.removeSeries((String) seriesNames[i]);
        }

        try {
            CallableStatement callableStatement;

            callableStatement = dbConnection.prepareCall(FT_GET_EMPLOYEE_RECORD_COUNT);
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();

            callableStatement = dbConnection
                    .prepareCall(SP_GET_EMPLOYEE_POSITION_STATISTICS);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String positionName = resultSet.getString(EmployeeModel.POSITION_NAME_HEADER);
                int amount = resultSet.getInt(2);
                pieChartEmployeePositionState.addSeries(positionName, amount);
            }

            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(HomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadEmployeeStatusState() {
        Map<String, PieSeries> seriesMap = pieChartEmployeeStatusState.getSeriesMap();

        Object[] seriesNames = seriesMap.keySet().toArray();
        for (int i = 0; i < seriesNames.length; i++) {
            pieChartEmployeeStatusState.removeSeries((String) seriesNames[i]);
        }

        try {
            CallableStatement callableStatement;

            callableStatement = dbConnection.prepareCall(FT_GET_EMPLOYEE_RECORD_COUNT);
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();

            int totalEmployeeAmount = callableStatement.getInt(1);

            callableStatement = dbConnection
                    .prepareCall(SP_GET_EMPLOYEE_STATUS_STATISTICS);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                boolean employeeStatus = resultSet.getBoolean(EmployeeModel.STATUS_HEADER);
                String statusName = employeeStatus ? "Working" : "No-working";
                int amount = resultSet.getInt(2);
                pieChartEmployeeStatusState.addSeries(statusName, amount);
            }

            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(HomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDiagnostics() {
        diagnosticTree.removeAll();

        List<String> productProblems = new ArrayList<>();
        List<String> ingredientProblems = new ArrayList<>();
        List<String> providerProblems = new ArrayList<>();

        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_PRODUCT_ZERO_AMOUNT);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString(ProductSimpleModel.NAME_HEADER);
                String productSize = resultSet.getString(ProductSimpleModel.SIZE_HEADER);
                productProblems.add(productName + " - " + productSize);
            }

            callableStatement = dbConnection.prepareCall(SP_GET_INGREDIENT_ZERO_AMOUNT);

            resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String ingredientName = resultSet.getString(IngredientModel.NAME_HEADER);
                ingredientProblems.add(ingredientName);
            }

            callableStatement = dbConnection.prepareCall(SP_GET_PROVIDER_NO_INGREDIENT);

            resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String providerName = resultSet.getString(ProviderModel.NAME_HEADER);
                providerProblems.add(providerName);
            }

            resultSet.close();
            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(HomePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (productProblems.isEmpty() && ingredientProblems.isEmpty() && providerProblems.isEmpty()) {
            diagnosticPassFlag = true;
            showDiagnosticCard(panelWarningGood.getName());
            return;
        }

        diagnosticPassFlag = false;

        int totalProblemNum = productProblems.size() + ingredientProblems.size()
                + providerProblems.size();

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(
                String.format(DIAGNOSTIC_ROOT_STRING_FORMAT, totalProblemNum));

        diagnosticTreeModel.setRoot(rootNode);

        if (!productProblems.isEmpty()) {
            DefaultMutableTreeNode productProblemRootNode = new DefaultMutableTreeNode(
                    String.format(DIAGNOSTIC_PRODUCT_PROBLEM_SUMMARY_STRING_FORMAT,
                            productProblems.size()));
            rootNode.add(productProblemRootNode);

            for (String productProblem : productProblems) {
                DefaultMutableTreeNode productProblemNode = new DefaultMutableTreeNode(productProblem);
                productProblemRootNode.add(productProblemNode);
            }
        }

        if (!ingredientProblems.isEmpty()) {
            DefaultMutableTreeNode ingredientProblemRootNode = new DefaultMutableTreeNode(
                    String.format(DIAGNOSTIC_INGREDIENT_PROBLEM_SUMMARY_STRING_FORMAT,
                            ingredientProblems.size()));
            rootNode.add(ingredientProblemRootNode);

            for (String ingredientProblem : ingredientProblems) {
                DefaultMutableTreeNode ingredientProblemNode = new DefaultMutableTreeNode(ingredientProblem);
                ingredientProblemRootNode.add(ingredientProblemNode);
            }
        }

        if (!providerProblems.isEmpty()) {
            DefaultMutableTreeNode providerProblemRootNode = new DefaultMutableTreeNode(
                    String.format(DIAGNOSTIC_PROVIDER_PROBLEM_SUMMARY_STRING_FORMAT,
                            providerProblems.size()));
            rootNode.add(providerProblemRootNode);

            for (String providerProblem : providerProblems) {
                DefaultMutableTreeNode providerProblemNode = new DefaultMutableTreeNode(providerProblem);
                providerProblemRootNode.add(providerProblemNode);
            }
        }

        diagnosticTree.expandRow(0);
        panelWarning.repaint();
    }

    private void showDiagnosticCard(String cardName) {
        CardLayout cardLayout = (CardLayout) panelWarning.getLayout();
        cardLayout.show(panelWarning, cardName);
    }

    @Override
    public void updateSettingObserver() {
        AppSetting appSetting = AppSetting.getInstance();

        AppSetting.Language language = appSetting.getAppLanguage();

        switch (language) {
            case ENGLISH: {
                labelTitleProductNumber.setText("Total product");
                labelTitleIngredientNumber.setText("Total ingredient");
                labelTitleProviderNumber.setText("Total provider");
                labelTitleEmployeeNumber.setText("Total employee");
                labelProblemFound.setText("Problem found");
                ckbTurnOffDiagnostic.setText("Turn off diagnostic");
                labelTurnOffDiagnostic.setText("Diagnostic is turned off. Turn on now!");
                labelAllThingGood.setText("All thing good!");
                pieChartProductState.setTitle("Product size statistics");
                pieChartIngredientState.setTitle("Ingredient type statistics");
                pieChartEmployeePositionState.setTitle("Employee position statistics");
                pieChartEmployeeStatusState.setTitle("Employee working status statistics");
                categoryChartProviderState.setTitle("Number Of Ingredient Belongs To Provider");
                categoryChartProviderState.setXAxisTitle("Provider");
                categoryChartProviderState.setYAxisTitle("Number of ingredient");

                break;
            }
            case VIETNAMESE: {
                labelTitleProductNumber.setText("Tổng số sản phẩm");
                labelTitleIngredientNumber.setText("Tổng số nguyên liệu");
                labelTitleProviderNumber.setText("Tổng số nhà cung cấp");
                labelTitleEmployeeNumber.setText("Tổng số nhân viên");
                labelProblemFound.setText("Tìm thấy vấn đề");
                ckbTurnOffDiagnostic.setText("Tắt chức năng cảnh báo");
                labelTurnOffDiagnostic.setText("Cảnh báo không khả dụng. Kích hoạt ngay!");
                labelAllThingGood.setText("Không tìm thấy vấn đề");
                pieChartProductState.setTitle("Thống kê số lượng sản phẩm theo kích thước");
                pieChartIngredientState.setTitle("Thống kê số lượng nguyên liệu theo loại");
                pieChartEmployeePositionState.setTitle("Số lượng nhân viên theo chức vụ");
                pieChartEmployeeStatusState.setTitle("Số lượng nhân viên theo trạng thái");
                categoryChartProviderState.setTitle("Số lượng nguyên liệu thuộc mỗi nhà cung cấp");
                categoryChartProviderState.setXAxisTitle("Nhà cung cấp");
                categoryChartProviderState.setYAxisTitle("Số lượng nguyên liệu");
                break;
            }
        }

        boolean diagnosticFlag = appSetting.getDiagnosticFlag();

        if (diagnosticFlag) {
            if (diagnosticPassFlag) {
                showDiagnosticCard(panelWarningGood.getName());
            } else {
                showDiagnosticCard(panelWarningProblem.getName());
            }
        } else {
            showDiagnosticCard(panelWarningBeTurnOff.getName());
        }

        ckbTurnOffDiagnostic.setSelected(!diagnosticFlag);

        repaint();
    }

    @Override
    public void updateInsertedProductObserver(ProductModelInterface insertedProduct) {
        loadStatisticNumberData();
        loadProductState();
        loadDiagnostics();
    }

    @Override
    public void updateModifiedProductObserver(ProductModelInterface updatedProduct) {
        loadProductState();
        loadDiagnostics();
    }

    @Override
    public void updateRemovedProductObserver(ProductModelInterface removedProduct) {
        loadStatisticNumberData();
        loadProductState();
        loadDiagnostics();
    }

    @Override
    public void updateInsertedIngredientObserver(IngredientModelInterface ingredient) {
        loadStatisticNumberData();
        loadIngredientState();
        loadProviderState();
        loadDiagnostics();
    }

    @Override
    public void updateModifiedIngredientObserver(IngredientModelInterface ingredient) {
        loadIngredientState();
        loadProviderState();
        loadDiagnostics();
    }

    @Override
    public void updateRemovedIngredient(IngredientModelInterface ingredient) {
        loadStatisticNumberData();
        loadIngredientState();
        loadProviderState();
        loadStatisticNumberData();
    }

    @Override
    public void updateInsertedIngredientType(IngredientTypeModelInterface insertedIngredientType) {
        loadIngredientState();
    }

    @Override
    public void updateInsertedProviderObserver(ProviderModelInterface insertedProvider) {
        loadStatisticNumberData();
        loadProviderState();
        loadDiagnostics();
    }

    @Override
    public void updateModifiedProviderObserver(ProviderModelInterface updatedProvider) {
        loadProductState();
        loadDiagnostics();
    }

    @Override
    public void updateRemovedProviderObserver(ProviderModelInterface provider) {
        loadStatisticNumberData();
        loadProviderState();
        loadDiagnostics();
    }

    @Override
    public void updateInsertedEmployee(EmployeeModelInterface insertedEmployee) {
        loadStatisticNumberData();
        loadEmployeePositionState();
        loadEmployeeStatusState();
    }

    @Override
    public void updateModifiedEmployee(EmployeeModelInterface updatedEmployee) {
        loadStatisticNumberData();
        loadEmployeePositionState();
        loadEmployeeStatusState();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        labelProductNumber = new javax.swing.JLabel();
        labelTitleProductNumber = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        labelIngredientNumber = new javax.swing.JLabel();
        labelTitleIngredientNumber = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        labelProviderNumber = new javax.swing.JLabel();
        labelTitleProviderNumber = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        labelEmployeeNumber = new javax.swing.JLabel();
        labelTitleEmployeeNumber = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        panelStateProduct = new javax.swing.JPanel();
        panelStateIngredient = new javax.swing.JPanel();
        panelStateProvider = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        panelDiagnostic = new javax.swing.JPanel();
        panelWarning = new javax.swing.JPanel();
        panelWarningProblem = new javax.swing.JPanel();
        labelProblemFound = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        diagnosticTree = new javax.swing.JTree();
        panelWarningGood = new javax.swing.JPanel();
        labelAllThingGood = new javax.swing.JLabel();
        panelWarningBeTurnOff = new javax.swing.JPanel();
        labelTurnOffDiagnostic = new javax.swing.JLabel();
        ckbTurnOffDiagnostic = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        panelStateEmployeePosition = new javax.swing.JPanel();
        panelStateEmployeeStatus = new javax.swing.JPanel();

        setBackground(new java.awt.Color(228, 228, 228));
        setName("Home"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(228, 228, 228));
        jPanel1.setLayout(new java.awt.GridLayout(4, 1, 20, 20));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(2, 0));

        labelProductNumber.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        labelProductNumber.setForeground(new java.awt.Color(51, 61, 73));
        labelProductNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelProductNumber.setText("50");
        labelProductNumber.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel2.add(labelProductNumber);

        labelTitleProductNumber.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelTitleProductNumber.setForeground(new java.awt.Color(52, 152, 219));
        labelTitleProductNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitleProductNumber.setText("Total product");
        labelTitleProductNumber.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        labelTitleProductNumber.setPreferredSize(new java.awt.Dimension(107, 50));
        jPanel2.add(labelTitleProductNumber);

        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(2, 0));

        labelIngredientNumber.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        labelIngredientNumber.setForeground(new java.awt.Color(51, 61, 73));
        labelIngredientNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelIngredientNumber.setText("50");
        labelIngredientNumber.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(labelIngredientNumber);

        labelTitleIngredientNumber.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelTitleIngredientNumber.setForeground(new java.awt.Color(52, 152, 219));
        labelTitleIngredientNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitleIngredientNumber.setText("Total Ingredient");
        labelTitleIngredientNumber.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        labelTitleIngredientNumber.setPreferredSize(new java.awt.Dimension(107, 50));
        jPanel3.add(labelTitleIngredientNumber);

        jPanel1.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(2, 0));

        labelProviderNumber.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        labelProviderNumber.setForeground(new java.awt.Color(51, 61, 73));
        labelProviderNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelProviderNumber.setText("50");
        labelProviderNumber.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel4.add(labelProviderNumber);

        labelTitleProviderNumber.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelTitleProviderNumber.setForeground(new java.awt.Color(52, 152, 219));
        labelTitleProviderNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitleProviderNumber.setText("Total provider");
        labelTitleProviderNumber.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        labelTitleProviderNumber.setPreferredSize(new java.awt.Dimension(107, 50));
        jPanel4.add(labelTitleProviderNumber);

        jPanel1.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(2, 0));

        labelEmployeeNumber.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        labelEmployeeNumber.setForeground(new java.awt.Color(51, 61, 73));
        labelEmployeeNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelEmployeeNumber.setText("50");
        labelEmployeeNumber.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel5.add(labelEmployeeNumber);

        labelTitleEmployeeNumber.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelTitleEmployeeNumber.setForeground(new java.awt.Color(52, 152, 219));
        labelTitleEmployeeNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitleEmployeeNumber.setText("Total employee");
        labelTitleEmployeeNumber.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        labelTitleEmployeeNumber.setPreferredSize(new java.awt.Dimension(107, 50));
        jPanel5.add(labelTitleEmployeeNumber);

        jPanel1.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(228, 228, 228));
        jPanel6.setMinimumSize(new java.awt.Dimension(1098, 850));

        jPanel13.setBackground(new java.awt.Color(228, 228, 228));
        jPanel13.setLayout(new java.awt.GridLayout(2, 1, 0, 20));

        jPanel10.setBackground(new java.awt.Color(228, 228, 228));
        jPanel10.setPreferredSize(new java.awt.Dimension(600, 435));
        jPanel10.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        panelStateProduct.setBackground(new java.awt.Color(255, 255, 255));
        panelStateProduct.setPreferredSize(new java.awt.Dimension(400, 300));

        javax.swing.GroupLayout panelStateProductLayout = new javax.swing.GroupLayout(panelStateProduct);
        panelStateProduct.setLayout(panelStateProductLayout);
        panelStateProductLayout.setHorizontalGroup(
            panelStateProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );
        panelStateProductLayout.setVerticalGroup(
            panelStateProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );

        jPanel10.add(panelStateProduct);

        panelStateIngredient.setBackground(new java.awt.Color(255, 255, 255));
        panelStateIngredient.setPreferredSize(new java.awt.Dimension(400, 300));

        javax.swing.GroupLayout panelStateIngredientLayout = new javax.swing.GroupLayout(panelStateIngredient);
        panelStateIngredient.setLayout(panelStateIngredientLayout);
        panelStateIngredientLayout.setHorizontalGroup(
            panelStateIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );
        panelStateIngredientLayout.setVerticalGroup(
            panelStateIngredientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel10.add(panelStateIngredient);

        jPanel13.add(jPanel10);

        panelStateProvider.setBackground(new java.awt.Color(255, 255, 255));
        panelStateProvider.setPreferredSize(new java.awt.Dimension(400, 300));

        javax.swing.GroupLayout panelStateProviderLayout = new javax.swing.GroupLayout(panelStateProvider);
        panelStateProvider.setLayout(panelStateProviderLayout);
        panelStateProviderLayout.setHorizontalGroup(
            panelStateProviderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
        );
        panelStateProviderLayout.setVerticalGroup(
            panelStateProviderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );

        jPanel13.add(panelStateProvider);

        jPanel14.setBackground(new java.awt.Color(228, 228, 228));
        jPanel14.setLayout(new java.awt.GridLayout(2, 1, 0, 20));

        panelDiagnostic.setBackground(new java.awt.Color(255, 255, 255));
        panelDiagnostic.setPreferredSize(new java.awt.Dimension(400, 300));
        panelDiagnostic.setLayout(new java.awt.BorderLayout());

        panelWarning.setLayout(new java.awt.CardLayout());

        panelWarningProblem.setName("DiagnosticProblem"); // NOI18N
        panelWarningProblem.setLayout(new java.awt.BorderLayout());

        labelProblemFound.setBackground(new java.awt.Color(255, 202, 40));
        labelProblemFound.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelProblemFound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Warning_50px.png"))); // NOI18N
        labelProblemFound.setText("Problem found");
        labelProblemFound.setIconTextGap(10);
        labelProblemFound.setOpaque(true);
        labelProblemFound.setPreferredSize(new java.awt.Dimension(187, 60));
        panelWarningProblem.add(labelProblemFound, java.awt.BorderLayout.NORTH);

        jScrollPane2.setBorder(null);

        diagnosticTree.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        diagnosticTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        diagnosticTree.setRowHeight(25);
        jScrollPane2.setViewportView(diagnosticTree);

        panelWarningProblem.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        panelWarning.add(panelWarningProblem, "DiagnosticProblem");

        panelWarningGood.setBackground(new java.awt.Color(255, 255, 255));
        panelWarningGood.setName("DiagnosticGood"); // NOI18N
        panelWarningGood.setLayout(new java.awt.BorderLayout());

        labelAllThingGood.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelAllThingGood.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelAllThingGood.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/OK_50px.png"))); // NOI18N
        labelAllThingGood.setText("All thing good!");
        labelAllThingGood.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelAllThingGood.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        panelWarningGood.add(labelAllThingGood, java.awt.BorderLayout.CENTER);

        panelWarning.add(panelWarningGood, "DiagnosticGood");

        panelWarningBeTurnOff.setBackground(new java.awt.Color(255, 255, 255));
        panelWarningBeTurnOff.setName("DiagnosticOff"); // NOI18N
        panelWarningBeTurnOff.setLayout(new java.awt.BorderLayout());

        labelTurnOffDiagnostic.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelTurnOffDiagnostic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTurnOffDiagnostic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Warning_50px.png"))); // NOI18N
        labelTurnOffDiagnostic.setText("Diagnostic is turned off. Turn on now!");
        labelTurnOffDiagnostic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelTurnOffDiagnostic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        panelWarningBeTurnOff.add(labelTurnOffDiagnostic, java.awt.BorderLayout.CENTER);

        panelWarning.add(panelWarningBeTurnOff, "DiagnosticOff");

        panelDiagnostic.add(panelWarning, java.awt.BorderLayout.CENTER);

        ckbTurnOffDiagnostic.setBackground(new java.awt.Color(255, 255, 255));
        ckbTurnOffDiagnostic.setText("Turn off diagnostic");
        panelDiagnostic.add(ckbTurnOffDiagnostic, java.awt.BorderLayout.PAGE_END);

        jPanel14.add(panelDiagnostic);

        jPanel9.setBackground(new java.awt.Color(228, 228, 228));
        jPanel9.setLayout(new java.awt.GridLayout(2, 1, 0, 20));

        panelStateEmployeePosition.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelStateEmployeePositionLayout = new javax.swing.GroupLayout(panelStateEmployeePosition);
        panelStateEmployeePosition.setLayout(panelStateEmployeePositionLayout);
        panelStateEmployeePositionLayout.setHorizontalGroup(
            panelStateEmployeePositionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelStateEmployeePositionLayout.setVerticalGroup(
            panelStateEmployeePositionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 197, Short.MAX_VALUE)
        );

        jPanel9.add(panelStateEmployeePosition);

        panelStateEmployeeStatus.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelStateEmployeeStatusLayout = new javax.swing.GroupLayout(panelStateEmployeeStatus);
        panelStateEmployeeStatus.setLayout(panelStateEmployeeStatusLayout);
        panelStateEmployeeStatusLayout.setHorizontalGroup(
            panelStateEmployeeStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelStateEmployeeStatusLayout.setVerticalGroup(
            panelStateEmployeeStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 197, Short.MAX_VALUE)
        );

        jPanel9.add(panelStateEmployeeStatus);

        jPanel14.add(jPanel9);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ckbTurnOffDiagnostic;
    private javax.swing.JTree diagnosticTree;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelAllThingGood;
    private javax.swing.JLabel labelEmployeeNumber;
    private javax.swing.JLabel labelIngredientNumber;
    private javax.swing.JLabel labelProblemFound;
    private javax.swing.JLabel labelProductNumber;
    private javax.swing.JLabel labelProviderNumber;
    private javax.swing.JLabel labelTitleEmployeeNumber;
    private javax.swing.JLabel labelTitleIngredientNumber;
    private javax.swing.JLabel labelTitleProductNumber;
    private javax.swing.JLabel labelTitleProviderNumber;
    private javax.swing.JLabel labelTurnOffDiagnostic;
    private javax.swing.JPanel panelDiagnostic;
    private javax.swing.JPanel panelStateEmployeePosition;
    private javax.swing.JPanel panelStateEmployeeStatus;
    private javax.swing.JPanel panelStateIngredient;
    private javax.swing.JPanel panelStateProduct;
    private javax.swing.JPanel panelStateProvider;
    private javax.swing.JPanel panelWarning;
    private javax.swing.JPanel panelWarningBeTurnOff;
    private javax.swing.JPanel panelWarningGood;
    private javax.swing.JPanel panelWarningProblem;
    // End of variables declaration//GEN-END:variables
}
