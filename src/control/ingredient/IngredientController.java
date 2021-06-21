package control.ingredient;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
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
import org.junit.Assert;
import util.constant.AppConstant;
import util.db.SQLServerConnection;
import util.excel.ExcelTransfer;
import view.ingredient.ImportHistoryDialog;
import view.ingredient.IngredientImportDialog;
import view.ingredient.NewIngredientTypeDialog;
import view.ingredient.IngredientPanel;

public class IngredientController implements IngredientControllerInterface {

    private static final String SP_CHECK_DELETE_CONDITION_INGREDIENT
            = "{? = call check_if_ingredient_exists_in_any_product(?)}";

    private static final String SP_CHECK_INGREDIENT_TYPE_NAME_EXISTED
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
        }
        this.dialogNewIngredientTypeCreate.setVisible(true);
    }

    @Override
    public void requestViewImportHistory() {
        if (dialogIngredientImport == null) {
            dialogImportHistory = new ImportHistoryDialog(ingredientPanel.getMainFrame(),
                    true, this, ingredientManageModel);
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
            dialogImportHistory.showErrorMessage("Error: Date to is before date from.");
            return;
        }

        dialogImportHistory.showImportHistoryFromDateRange();
    }

    @Override
    public void requestImportIngredient() {
        int ingredientTotalNumber = ingredientManageModel.getIngredientTotalNumber();

        if (ingredientTotalNumber == 0) {
            this.ingredientPanel.showErrorMessage("Ingredient data list is empty.");
            return;
        }

        int rowID = this.ingredientPanel.getSelectedRow();

        if (rowID == -1) {
            this.ingredientPanel.showInfoMessage("You should choose one ingredient first.");
            return;
        }

        IngredientModelInterface ingredient = this.searchList.get(rowID);

        if (dialogIngredientImport == null) {
            dialogIngredientImport = new IngredientImportDialog(
                    ingredientPanel.getMainFrame(), true, this);
        }

        dialogIngredientImport.setIngredientIDText(ingredient.getIngredientIDText());
        dialogIngredientImport.setIngredientName(ingredient.getName());
        dialogIngredientImport.setLabelIngredientUnit(ingredient.getUnitName());
        dialogIngredientImport.setIngredientTotalCost(String.valueOf(ingredient.getCost()));
        dialogIngredientImport.setImportDate(Date.from(Instant.now()));
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

        Date importDate = dialogIngredientImport.getImportDateInput();

        LocalDate importDateLocal = importDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (importDateLocal.isBefore(LocalDate.now())) {
            dialogIngredientImport.showErrorMessage("Please enter valid date import.");
            return;
        }

        ingredientManageModel.importIngredient(ingredient, importDate, importAmount,
                ingredient.getUnitName());

        ingredientPanel.showInfoMessage("Request to import ingredient successfully.");
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
            this.ingredientPanel.showErrorMessage("Please create a new provider first.");
            return false;
        }

        return true;
    }

    private boolean isIngredientTypeNameVallid(String ingredientTypeName) {
        if (ingredientTypeName == null) {
            this.ingredientPanel.showErrorMessage("Please create a new ingredient type first.");
            return false;
        }

        return true;
    }

    private boolean isIngredientUnitNameVallid(String ingredientUnitName) {
        if (ingredientUnitName == null) {
            this.ingredientPanel.showErrorMessage("System error: Can not load unit data.");
            return false;
        }

        return true;
    }

    private boolean isIngredientNameVallid(String ingredientName) {
        if (ingredientName.isEmpty()) {
            this.ingredientPanel.showErrorMessage("Ingredient name must not be empty.");
            return false;
        }

        Iterator<IngredientModelInterface> iterator = ingredientManageModel.getAllIngredientData();

        while (iterator.hasNext()) {
            IngredientModelInterface ingredient = iterator.next();
            if (ingredient.getName().equals(ingredientName)) {
                this.ingredientPanel.showErrorMessage("Ingredient name is already existed.");
                return false;
            }
        }

        return true;
    }

    private boolean isIngredientCostVallid(long ingredientCost) {
        if (ingredientCost <= 0) {
            this.ingredientPanel.showErrorMessage("Ingredient cost must be greater than 0.");
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
            this.ingredientPanel.showErrorMessage("Please enter ingredient cost in number.");
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

        this.ingredientPanel.showInfoMessage("Insert new ingredient successfully.");
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
            this.ingredientPanel.showErrorMessage("Please enter ingredient cost in number.");
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

        this.ingredientPanel.showInfoMessage("Update ingredient data successfully.");
    }

    @Override
    public void requestRemoveIngredient() {
        String ingredientIDText = this.ingredientPanel.getIngredientIDText();

        IngredientModelInterface ingredient = ingredientManageModel
                .getIngredientByID(ingredientIDText);
        Assert.assertNotNull(ingredient);

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_CHECK_DELETE_CONDITION_INGREDIENT);
            callableStatement.registerOutParameter(1, Types.BOOLEAN);
            ingredient.setKeyArg(2, IngredientModel.ID_HEADER, callableStatement);
            callableStatement.execute();

            boolean isIncludedInProduct = callableStatement.getBoolean(1);

            if (isIncludedInProduct) {
                this.ingredientPanel.showErrorMessage("Can not delete ingredient included in product.");
                return;
            }

        } catch (SQLException ex) {
            Logger.getLogger(IngredientController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.ingredientManageModel.removeIngredient(ingredient);

        this.searchList.remove(ingredient);

        this.ingredientPanel.showInfoMessage("Delete ingredient successfully.");
    }

    @Override
    public boolean isNewIngredientTypeNameVallid(String ingredientTypeName) {
        if (ingredientTypeName.isEmpty()) {
            this.dialogNewIngredientTypeCreate.showErrorMessage("Ingredient type name must not be empty.");
            return false;
        }

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_CHECK_INGREDIENT_TYPE_NAME_EXISTED);
            callableStatement.registerOutParameter(1, Types.BOOLEAN);
            callableStatement.setString(2, ingredientTypeName);
            callableStatement.execute();

            boolean isTypeNameExisted = callableStatement.getBoolean(1);

            if (isTypeNameExisted) {
                this.dialogNewIngredientTypeCreate.showErrorMessage("Ingredient type name is already existed.");
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

        if (!isNewIngredientTypeNameVallid(ingredientTypeName)) {
            return;
        }

        IngredientTypeModelInterface ingredientType = new IngredientTypeModel();
        ingredientType.setName(ingredientTypeName);

        ingredientManageModel.addIngredientType(ingredientType);

        this.ingredientPanel.showInfoMessage("Create new ingredient type successfully.");

        this.dialogNewIngredientTypeCreate.dispose();
    }

    @Override
    public void requestExportExcel() {
        if (ingredientPanel.getTableIngredientRowCount() == 0) {
            ingredientPanel.showErrorMessage("Table product data is empty.");
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
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
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
