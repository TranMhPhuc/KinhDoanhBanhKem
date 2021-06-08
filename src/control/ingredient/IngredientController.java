package control.ingredient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import model.ingredient.IngredientManageModelInterface;
import model.ingredient.IngredientModel;
import model.ingredient.IngredientModelInterface;
import model.ingredient.type.IngredientTypeModel;
import model.ingredient.type.IngredientTypeModelInterface;
import model.ingredient.unit.IngredientUnitModelInterface;
import model.provider.ProviderModelInterface;
import org.junit.Assert;
import util.constant.AppConstant;
import view.dialog.ImportHistoryDialog;
import view.dialog.IngredientImportDialog;
import view.dialog.NewIngredientTypeCreateDialog;
import view.function.ingredient.IngredientPanel;
import view.main.MainFrame;

public class IngredientController implements IngredientControllerInterface {

    private volatile static IngredientController uniqueInstance;

    private List<IngredientModelInterface> searchList;

    private IngredientManageModelInterface model;
    private IngredientPanel view;

    private NewIngredientTypeCreateDialog newIngredientTypeCreateDialog;
    private IngredientImportDialog ingredientImportDialog;
    private ImportHistoryDialog importHistoryDialog;

    private IngredientController(IngredientManageModelInterface model) {
        this.searchList = new ArrayList<>();

        this.model = model;
        this.view = IngredientPanel.getInstance(model, this);
    }

    public static IngredientController getInstance(IngredientManageModelInterface model) {
        if (uniqueInstance == null) {
            synchronized (IngredientController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new IngredientController(model);
                }
            }
        }
        return uniqueInstance;
    }

    public static IngredientController getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

    @Override
    public void requestCreateNewIngredientType() {
        if (this.newIngredientTypeCreateDialog == null) {
            this.newIngredientTypeCreateDialog = new NewIngredientTypeCreateDialog(
                    MainFrame.getInstance(), true, model, this);
        }
        this.newIngredientTypeCreateDialog.setIngredientTypeID(String.valueOf(
                this.model.getNextIngredientTypeIDText()));
        this.newIngredientTypeCreateDialog.setVisible(true);
    }

    @Override
    public void requestViewImportHistory() {
        int rowID = this.view.getSelectedRow();
        if (rowID == -1) {
            this.view.showInfoMessage("You should choose one ingredient first.");
        } else {
            // XXX
        }
    }

    @Override
    public void requestImportIngredient() {
        int rowID = this.view.getSelectedRow();
        if (rowID == -1) {
            this.view.showInfoMessage("You should choose one ingredient first.");
        } else {
            // XXX
        }
    }

    @Override
    public void requestShowIngredientInfo() {
        int rowID = this.view.getSelectedRow();
        if (rowID == -1) {
            return;
        }
        if (rowID >= searchList.size()) {
            throw new IndexOutOfBoundsException("Row index is not in bound.");
        }
        IngredientModelInterface ingredient = this.searchList.get(rowID);
        this.view.showIngredientInfo(ingredient);
    }

    @Override
    public Iterator<IngredientModelInterface> getAllIngredientData() {
        Iterator<IngredientModelInterface> iterator = this.model.getAllIngredientData();
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }

    @Override
    public Iterator<IngredientModelInterface> getIngredientBySearchName(String searchText) {
        Iterator<IngredientModelInterface> iterator = this.model.getIngredientSearchByName(searchText);
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }

    @Override
    public void requestCreateIngredient() {
        String ingredientIDText = this.view.getIngredientIDText();

        int providerSelectIndex = this.view.getProviderSelectIndex();

        if (providerSelectIndex == -1) {
            this.view.showErrorMessage("Please create a new provider first.");
            return;
        }

        ProviderModelInterface provider = this.model.getProviderByIndex(providerSelectIndex);

        int ingredientTypeSelectIndex = this.view.getIngredientTypeSelectIndex();

        if (ingredientTypeSelectIndex == -1) {
            this.view.showErrorMessage("Please create a new ingredient type first.");
            return;
        }

        IngredientTypeModelInterface ingredientType = this.model.getIngredientTypeByIndex(ingredientTypeSelectIndex);

        int ingredientUnitSelectIndex = this.view.getIngredientUnitSelectIndex();

        if (ingredientUnitSelectIndex == -1) {
            this.view.showErrorMessage("Please create a new ingredient unit first.");
            return;
        }

        IngredientUnitModelInterface ingredientUnit = this.model.getIngredientUnitByIndex(ingredientUnitSelectIndex);

        String ingredientName = this.view.getIngredientNameInput();

        if (ingredientName.isEmpty()) {
            this.view.showErrorMessage("Ingredient name is required.");
            return;
        }
        if (this.model.isIngredientNameExist(ingredientName)) {
            this.view.showErrorMessage("Ingredient name is existed.");
            return;
        }

        String ingredientCostText = this.view.getIngredientCostInput();
        long ingredientCost = 0;
        try {
            ingredientCost = Long.parseLong(ingredientCostText);
        } catch (NumberFormatException ex) {
            this.view.showErrorMessage("Please enter ingredient cost in number.");
            return;
        }
        if (ingredientCost <= 0) {
            this.view.showErrorMessage("Ingredient cost is invallid.");
            return;
        }

        // Update model
        IngredientModelInterface ingredient = new IngredientModel();
        ingredient.setIngredientID(ingredientIDText);
        ingredient.setName(ingredientName);
        ingredient.setCost(ingredientCost);
        ingredient.setProvider(provider);
        ingredient.setIngredientType(ingredientType);
        ingredient.setIngredientUnit(ingredientUnit);

        this.model.addNewIngredient(ingredient);

        // Update view
        this.view.exitEditState();
        this.view.showInfoMessage("Insert new ingredient successfully.");
    }

    @Override
    public void requestUpdateIngredient() {
        String ingredientIDText = this.view.getIngredientIDText();

        IngredientModelInterface ingredient = this.model.getIngredient(ingredientIDText);

        Assert.assertNotNull(ingredient);

        int providerSelectIndex = this.view.getProviderSelectIndex();

        ProviderModelInterface provider = this.model.getProviderByIndex(providerSelectIndex);

        int ingredientTypeSelectIndex = this.view.getIngredientTypeSelectIndex();

        IngredientTypeModelInterface ingredientType = this.model.getIngredientTypeByIndex(ingredientTypeSelectIndex);

        int ingredientUnitSelectIndex = this.view.getIngredientUnitSelectIndex();

        IngredientUnitModelInterface ingredientUnit = this.model.getIngredientUnitByIndex(ingredientUnitSelectIndex);

        String ingredientNameInput = this.view.getIngredientNameInput();

        if (!ingredient.getName().equals(ingredientNameInput)) {
            if (ingredientNameInput.isEmpty()) {
                this.view.showErrorMessage("Ingredient name is required.");
                return;
            }
            if (this.model.isIngredientNameExist(ingredientNameInput)) {
                this.view.showErrorMessage("Ingredient name is existed.");
                return;
            }
        }

        String ingredientCostText = this.view.getIngredientCostInput();
        long ingredientCost = 0;
        try {
            ingredientCost = Long.parseLong(ingredientCostText);
        } catch (NumberFormatException ex) {
            this.view.showErrorMessage("Please enter ingredient cost in number.");
            return;
        }
        if (ingredientCost <= 0) {
            this.view.showErrorMessage("Ingredient cost is invallid.");
            return;
        }

        ingredient.setName(ingredientNameInput);
        ingredient.setCost(ingredientCost);
        ingredient.setProvider(provider);
        ingredient.setIngredientType(ingredientType);
        ingredient.setIngredientUnit(ingredientUnit);

        this.model.updateIngredient(ingredient);

        this.view.exitEditState();
        this.view.showInfoMessage("Update ingredient data successfully.");
    }

    @Override
    public void requestRemoveIngredient() {
        String ingredientIDText = this.view.getIngredientIDText();

        IngredientModelInterface ingredient = this.model.getIngredient(ingredientIDText);

        Assert.assertNotNull(ingredient);

        if (this.model.isIngredientOfAnyProduct(ingredient)) {
            this.view.showErrorMessage("Can not delete ingredient with existed product including it.");
            return;
        }

        this.model.removeIngredient(ingredient);
        this.searchList.remove(ingredient);

        this.view.showInfoMessage("Delete ingredient successfully.");
    }

    @Override
    public void createNewIngredientType() {
        String ingredientTypeIDText = this.newIngredientTypeCreateDialog.getIngredientTypeIDText();

        String ingredientTypeName = this.newIngredientTypeCreateDialog.getIngredientTypeName();

        if (ingredientTypeName.isEmpty()) {
            this.newIngredientTypeCreateDialog.showErrorMessage("Ingredient type name is required.");
            return;
        }

        boolean isTypeNameExist = this.model.isIngredientTypeNameExist(ingredientTypeName);

        if (isTypeNameExist) {
            this.newIngredientTypeCreateDialog.showErrorMessage("Ingredient type with name is already existed.");
            return;
        }
        IngredientTypeModelInterface ingredientType = new IngredientTypeModel();
        ingredientType.setIngredientTypeIDText(ingredientTypeIDText);
        ingredientType.setName(ingredientTypeName);
        this.model.addNewIngredientType(ingredientType);

        this.view.setIngredientTypeSelectIndex(ingredientType.getName());
        this.view.showInfoMessage("Create new ingredient type successfully.");

        this.newIngredientTypeCreateDialog.dispose();
    }

    @Override
    public void requestImportExcel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void requestExportExcel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insertToSearchListByMatchingName(String searchText, IngredientModelInterface ingredient) {
        if (ingredient == null) {
            throw new NullPointerException("Ingredient instance is null.");
        }
        boolean ret = searchText.isEmpty()
                || (FuzzySearch.ratio(searchText, ingredient.getName()) >= AppConstant.SEARCH_SCORE_CUT_OFF);
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

}
