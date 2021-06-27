package control.ingredient;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import model.ingredient.IngredientManageModelInterface;
import model.ingredient.IngredientModel;
import model.ingredient.IngredientModelInterface;
import model.ingredient.type.IngredientTypeModel;
import model.ingredient.type.IngredientTypeModelInterface;
import model.setting.AppSetting;
import org.junit.Assert;
import util.common.string.StringUtil;
import util.constant.AppConstant;
import util.db.SQLServerConnection;
import util.excel.ExcelTransfer;
import util.messages.Messages;
import view.ingredient.ImportHistoryDialog;
import view.ingredient.IngredientImportDialog;
import view.ingredient.NewIngredientTypeDialog;
import view.ingredient.IngredientPanel;

public class IngredientController implements IngredientControllerInterface {

    private static final String FC_CHECK_INGREDIENT_IN_PRODUCT
            = "{? = call check_if_ingredient_exists_in_any_product(?)}";
    
    private static final String FC_CHECK_INGREDIENT_IMPORTED
            = "{? = call check_if_ingredient_imported(?)}";

    private static final String FC_CHECK_INGREDIENT_TYPE_NAME_EXISTED
            = "{? = call check_if_ingredient_type_name_exist(?)}";

    private static Connection dbConnection;

    private List<IngredientModelInterface> searchList;

    private IngredientManageModelInterface ingredientManageModel;
    private IngredientPanel ingredientPanel;

    private NewIngredientTypeDialog dialogNewIngredientTypeCreate;
    private IngredientImportDialog dialogIngredientImport;
    private ImportHistoryDialog dialogImportHistory;

    static {
        dbConnection = SQLServerConnection.getConnection();
    }

    public IngredientController(IngredientManageModelInterface model) {
        this.searchList = new ArrayList<>();
        this.ingredientManageModel = model;
    }

    @Override
    public void setIngredientPanelView(IngredientPanel ingredientPanel) {
        if (ingredientPanel == null) {
            throw new NullPointerException();
        }
        this.ingredientPanel = ingredientPanel;
        ingredientPanel.setIngredientController(this);
        ingredientPanel.setIngredientManageModel(ingredientManageModel);
    }

    @Override
    public void requestCreateNewIngredientType() {
        if (this.dialogNewIngredientTypeCreate == null) {
            this.dialogNewIngredientTypeCreate = new NewIngredientTypeDialog(
                    ingredientPanel.getMainFrame(), true, ingredientManageModel, this);
            AppSetting.getInstance().registerObserver(dialogNewIngredientTypeCreate);
        }
        this.dialogNewIngredientTypeCreate.setVisible(true);
    }

    @Override
    public void requestViewImportHistory() {
        if (dialogImportHistory == null) {
            dialogImportHistory = new ImportHistoryDialog(ingredientPanel.getMainFrame(),
                    true, this, ingredientManageModel);
            AppSetting.getInstance().registerObserver(dialogImportHistory);
        }

        Date todayStart = Date.from(LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        dialogImportHistory.setDateFrom(todayStart);
        dialogImportHistory.setDateTo(Date.from(Instant.now()));

        dialogImportHistory.showImportHistoryFromDateRange();

        dialogImportHistory.setVisible(true);
    }

    @Override
    public void viewImportHistory() {
        Date dateFrom = dialogImportHistory.getDateFromInput();

        Date dateTo = dialogImportHistory.getDateToInput();

        LocalDate dateFromLocal = dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate dateToLocal = dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (dateFromLocal.isAfter(dateToLocal)) {
            dialogImportHistory.showErrorMessage(Messages.getInstance().INGR_DATE_TO_BEFORE);
            return;
        }

        dialogImportHistory.showImportHistoryFromDateRange();
    }

    @Override
    public void requestImportIngredient() {
        int ingredientTotalNumber = ingredientManageModel.getIngredientTotalNumber();

        if (ingredientTotalNumber == 0) {
            this.ingredientPanel.showErrorMessage(Messages.getInstance().INGR_DATA_LIST_EMPTY);
            return;
        }

        int rowID = this.ingredientPanel.getSelectedRow();

        if (rowID == -1) {
            this.ingredientPanel.showInfoMessage(Messages.getInstance().INGR_NO_INGR_CHOSEN);
            return;
        }

        IngredientModelInterface ingredient = this.searchList.get(rowID);

        if (dialogIngredientImport == null) {
            dialogIngredientImport = new IngredientImportDialog(
                    ingredientPanel.getMainFrame(), true, this);
            AppSetting.getInstance().registerObserver(dialogIngredientImport);
        }

        dialogIngredientImport.setIngredientIDText(ingredient.getIngredientIDText());
        dialogIngredientImport.setIngredientName(ingredient.getName());
        dialogIngredientImport.setLabelIngredientUnit(ingredient.getUnitName());
        dialogIngredientImport.setIngredientTotalCost(String.valueOf(ingredient.getCost()));
        dialogIngredientImport.setVisible(true);
    }

    @Override
    public void showTotalCostIngredientImport() {
        int importAmount = dialogIngredientImport.getImportAmountInput();

        int rowID = this.ingredientPanel.getSelectedRow();

        IngredientModelInterface ingredient = this.searchList.get(rowID);

        long totalCost = ingredient.getCost() * importAmount;

        dialogIngredientImport.setIngredientTotalCost(String.valueOf(totalCost));
    }

    @Override
    public void importIngredient() {
        int importAmount = dialogIngredientImport.getImportAmountInput();

        int rowID = this.ingredientPanel.getSelectedRow();

        IngredientModelInterface ingredient = this.searchList.get(rowID);

        Date importDate = Date.from(Instant.now());

        ingredientManageModel.importIngredient(ingredient, importDate, importAmount,
                ingredient.getUnitName());

        ingredientPanel.showInfoMessage(Messages.getInstance().INGR_IMPORTED_SUCCESSFULLY);
    }

    @Override
    public void requestShowIngredientInfo() {
        if (ingredientPanel.getEditState() == IngredientPanel.EditState.ADD) {
            return;
        }

        int rowID = this.ingredientPanel.getSelectedRow();
        if (rowID == -1) {
            return;
        }
        if (rowID >= searchList.size()) {
            throw new IndexOutOfBoundsException("Row index is not in bound.");
        }
        IngredientModelInterface ingredient = this.searchList.get(rowID);
        this.ingredientPanel.showIngredientInfo(ingredient);
    }

    @Override
    public Iterator<IngredientModelInterface> getAllIngredientData() {
        Iterator<IngredientModelInterface> iterator = this.ingredientManageModel.getAllIngredientData();
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }

    @Override
    public Iterator<IngredientModelInterface> getIngredientBySearchName(String searchText) {
        Iterator<IngredientModelInterface> iterator = this.ingredientManageModel.getIngredientSearchByName(searchText);
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }

    private boolean isProviderNameVallid(String providerName) {
        if (providerName == null) {
            this.ingredientPanel.showErrorMessage(Messages.getInstance().INGR_REQUEST_CREATE_PROVIDER);
            return false;
        }

        return true;
    }

    private boolean isIngredientTypeNameVallid(String ingredientTypeName) {
        if (ingredientTypeName == null) {
            this.ingredientPanel.showErrorMessage(Messages.getInstance().INGR_REQUEST_CREATE_ING_TYPE);
            return false;
        }

        return true;
    }

    private boolean isIngredientUnitNameVallid(String ingredientUnitName) {
        if (ingredientUnitName == null) {
            this.ingredientPanel.showErrorMessage(Messages.getInstance().INGR_CANT_LOAD_UNIT);
            return false;
        }

        return true;
    }

    private boolean isIngredientNameVallid(String ingredientName) {
        if (ingredientName.isEmpty()) {
            this.ingredientPanel.showErrorMessage(Messages.getInstance().INGR_NAME_EMPTY);
            return false;
        }

        Iterator<IngredientModelInterface> iterator = ingredientManageModel
                .getAllIngredientData();

        while (iterator.hasNext()) {
            IngredientModelInterface ingredient = iterator.next();
            if (ingredient.getName().equals(ingredientName)) {
                this.ingredientPanel.showErrorMessage(Messages.getInstance().INGR_NAME_EXISTS);
                return false;
            }
        }

        return true;
    }

    private boolean isIngredientCostVallid(long ingredientCost) {
        if (ingredientCost <= 0) {
            this.ingredientPanel.showErrorMessage(Messages.getInstance().INGR_COST_GREATER_THAN_0);
            return false;
        }

        return true;
    }

    @Override
    public void requestCreateIngredient() {
        String ingredientIDText = this.ingredientPanel.getIngredientIDText();

        String ingredientName = this.ingredientPanel.getIngredientNameInput();

        if (!isIngredientNameVallid(ingredientName)) {
            return;
        }

        String providerName = this.ingredientPanel.getProviderNameSelected();
        if (!isProviderNameVallid(providerName)) {
            return;
        }

        String ingredientTypeName = this.ingredientPanel.getIngredientTypeNameSelected();

        if (!isIngredientTypeNameVallid(ingredientTypeName)) {
            return;
        }

        String ingredientUnitName = this.ingredientPanel.getIngredientUnitNameSelected();

        if (!isIngredientUnitNameVallid(ingredientUnitName)) {
            return;
        }

        String ingredientCostInputText = this.ingredientPanel.getIngredientCostInput();

        long ingredientCost = 0;

        try {
            ingredientCost = Long.parseLong(ingredientCostInputText);
        } catch (NumberFormatException ex) {
            this.ingredientPanel.showErrorMessage(Messages.getInstance().INGR_COST_FORMAT);
            return;
        }

        if (!isIngredientCostVallid(ingredientCost)) {
            return;
        }

        IngredientModelInterface ingredient = new IngredientModel();
        ingredient.setIngredientID(ingredientIDText);
        ingredient.setName(ingredientName);
        ingredient.setCost(ingredientCost);
        ingredient.setProviderName(providerName);
        ingredient.setIngredientTypeName(ingredientTypeName);
        ingredient.setUnitName(ingredientUnitName);

        this.ingredientManageModel.addIngredient(ingredient);

        // Update view
        this.ingredientPanel.exitEditState();

        this.ingredientPanel.showInfoMessage(Messages.getInstance().INGR_INSERTED_SUCCESSFULLY);
    }

    @Override
    public void requestUpdateIngredient() {
        String ingredientIDText = this.ingredientPanel.getIngredientIDText();

        IngredientModelInterface ingredient = ingredientManageModel.getIngredientByID(ingredientIDText);

        String ingredientName = this.ingredientPanel.getIngredientNameInput();

        if (!ingredient.getName().equals(ingredientName)) {
            if (!isIngredientNameVallid(ingredientName)) {
                return;
            }
        }

        String providerName = this.ingredientPanel.getProviderNameSelected();
        if (!isProviderNameVallid(providerName)) {
            return;
        }

        String ingredientTypeName = this.ingredientPanel.getIngredientTypeNameSelected();

        if (!isIngredientTypeNameVallid(ingredientTypeName)) {
            return;
        }

        String ingredientUnitName = this.ingredientPanel.getIngredientUnitNameSelected();

        if (!isIngredientUnitNameVallid(ingredientUnitName)) {
            return;
        }

        String ingredientCostInputText = this.ingredientPanel.getIngredientCostInput();

        long ingredientCost = 0;

        try {
            ingredientCost = Long.parseLong(ingredientCostInputText);
        } catch (NumberFormatException ex) {
            this.ingredientPanel.showErrorMessage(Messages.getInstance().INGR_COST_FORMAT);
            return;
        }

        if (!isIngredientCostVallid(ingredientCost)) {
            return;
        }

        ingredient.setName(ingredientName);
        ingredient.setCost(ingredientCost);
        ingredient.setProviderName(providerName);
        ingredient.setIngredientTypeName(ingredientTypeName);
        ingredient.setUnitName(ingredientUnitName);

        this.ingredientManageModel.updateIngredient(ingredient);

        this.ingredientPanel.exitEditState();

        this.ingredientPanel.showInfoMessage(Messages.getInstance().INGR_UPDATED_SUCCESSFULLY);
    }

    @Override
    public boolean isUnitModifiable() {
        String ingredientIDText = this.ingredientPanel.getIngredientIDText();

        IngredientModelInterface ingredient = ingredientManageModel
                .getIngredientByID(ingredientIDText);
        Assert.assertNotNull(ingredient);

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(FC_CHECK_INGREDIENT_IN_PRODUCT);
            callableStatement.registerOutParameter(1, Types.BOOLEAN);
            ingredient.setKeyArg(2, IngredientModel.ID_HEADER, callableStatement);
            callableStatement.execute();

            boolean isIncludedInProduct = callableStatement.getBoolean(1);

            if (isIncludedInProduct) {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(IngredientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public void requestRemoveIngredient() {
        String ingredientIDText = this.ingredientPanel.getIngredientIDText();

        IngredientModelInterface ingredient = ingredientManageModel
                .getIngredientByID(ingredientIDText);
        Assert.assertNotNull(ingredient);

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(FC_CHECK_INGREDIENT_IN_PRODUCT);
            callableStatement.registerOutParameter(1, Types.BOOLEAN);
            ingredient.setKeyArg(2, IngredientModel.ID_HEADER, callableStatement);
            callableStatement.execute();

            boolean isIncludedInProduct = callableStatement.getBoolean(1);

            if (isIncludedInProduct) {
                this.ingredientPanel.showErrorMessage(Messages.getInstance().INGR_CANT_REMOVE_1);
                return;
            }
            
            callableStatement = dbConnection.prepareCall(FC_CHECK_INGREDIENT_IMPORTED);
            callableStatement.registerOutParameter(1, Types.BOOLEAN);
            ingredient.setKeyArg(2, IngredientModel.ID_HEADER, callableStatement);
            callableStatement.execute();
            
            boolean isImported = callableStatement.getBoolean(1);
            
            if (isImported) {
                this.ingredientPanel.showErrorMessage(Messages.getInstance().INGR_CANT_REMOVE_2);
                return;
            }

        } catch (SQLException ex) {
            Logger.getLogger(IngredientController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.ingredientManageModel.removeIngredient(ingredient);

        this.searchList.remove(ingredient);

        this.ingredientPanel.showInfoMessage(Messages.getInstance().INGR_REMOVE_SUCCESSFULLY);
    }

    @Override
    public boolean isNewIngredientTypeNameVallid(String ingredientTypeName) {
        if (ingredientTypeName.isEmpty()) {
            this.dialogNewIngredientTypeCreate.showErrorMessage(Messages.getInstance().INGR_TYPE_EMPTY);
            return false;
        }

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(FC_CHECK_INGREDIENT_TYPE_NAME_EXISTED);
            callableStatement.registerOutParameter(1, Types.BOOLEAN);
            callableStatement.setString(2, ingredientTypeName);
            callableStatement.execute();

            boolean isTypeNameExisted = callableStatement.getBoolean(1);

            if (isTypeNameExisted) {
                this.dialogNewIngredientTypeCreate.showErrorMessage(Messages.getInstance().INGR_TYPE_EXISTS);
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(IngredientController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    @Override
    public void createNewIngredientType() {
        String ingredientTypeName = this.dialogNewIngredientTypeCreate.getIngredientTypeName();
        ingredientTypeName = StringUtil.getCapitalizeWord(ingredientTypeName);

        if (!isNewIngredientTypeNameVallid(ingredientTypeName)) {
            return;
        }

        IngredientTypeModelInterface ingredientType = new IngredientTypeModel();
        ingredientType.setName(ingredientTypeName);

        ingredientManageModel.addIngredientType(ingredientType);

        this.ingredientPanel.showInfoMessage(Messages.getInstance().INGR_TYPE_INSERTED_SUCCESSFULLY);

        this.dialogNewIngredientTypeCreate.dispose();
    }

    @Override
    public void requestExportExcel() {
        if (ingredientPanel.getTableIngredientRowCount() == 0) {
            ingredientPanel.showErrorMessage(Messages.getInstance().INGR_TABLE_EMPTY);
        } else {
            ExcelTransfer.exportTableToExcel(ingredientPanel.getTableIngredient());
        }
    }

    @Override
    public void requestCreateTemplateExcel() {
        ExcelTransfer.createExcelFileTemplate(ingredientPanel.getTableIngredient());
    }

    @Override
    public boolean isSearchMatching(String searchText, IngredientModelInterface ingredient) {
        if (ingredient == null) {
            throw new NullPointerException("Ingredient instance is null.");
        }
        boolean ret = searchText.isEmpty()
                || (FuzzySearch.weightedRatio(searchText, ingredient.getName())
                >= AppConstant.SEARCH_SCORE_CUT_OFF);
        if (ret) {
            this.searchList.add(ingredient);
        }
        return ret;
    }

    @Override
    public boolean deleteIngredientInSearchList(IngredientModelInterface ingredient) {
        if (ingredient == null) {
            throw new NullPointerException("Ingredient instance is null.");
        }
        int id = this.searchList.indexOf(ingredient);
        if (id >= 0) {
            this.searchList.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean canCloseIngredientManagePanel() {
        if (ingredientPanel.getEditState() == IngredientPanel.EditState.ADD
                || ingredientPanel.getEditState() == IngredientPanel.EditState.MODIFY) {
            int ret = JOptionPane.showConfirmDialog(ingredientPanel.getMainFrame(),
                    "Cancel editing ingredient?",
                    "Cancel editing ingredient confirm dialog",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/warning.png")));
            if (ret == JOptionPane.YES_OPTION) {
                ingredientPanel.exitEditState();
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

}
