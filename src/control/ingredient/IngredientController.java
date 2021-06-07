package control.ingredient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.ingredient.IngredientManageModelInterface;
import model.ingredient.IngredientModelInterface;
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
        this.newIngredientTypeCreateDialog = new NewIngredientTypeCreateDialog(
                MainFrame.getInstance(), true, model, this);
        this.newIngredientTypeCreateDialog.setIngredientTypeID(String.valueOf(
                this.model.getNextIngredientTypeID()));
        this.newIngredientTypeCreateDialog.setVisible(true);
    }

    @Override
    public void viewIngredientImportHistory(int rowID) {

    }

    @Override
    public void requestImportIngredient(int rowID) {

    }

    @Override
    public void selectRowTableIngredient(int rowID) {
        if (rowID >= searchList.size()) {
            throw new IllegalArgumentException("RowID is not in search list.");
        }
        IngredientModelInterface ingredient = this.searchList.get(rowID);
        this.view.showIngredientInfo(ingredient);
    }

    @Override
    public Iterator<IngredientModelInterface> getAllIngredient() {
        Iterator<IngredientModelInterface> iterator = this.model.getAllIngredient();
        searchList.clear();
        while (iterator.hasNext()) {
            searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }

    @Override
    public Iterator<IngredientModelInterface> getIngredientSearchByName(String searchText) {
        Iterator<IngredientModelInterface> iterator = this.model.getIngredientSearchByName(searchText);
        this.searchList.clear();
        while (iterator.hasNext()) {
            this.searchList.add(iterator.next());
        }
        return this.searchList.iterator();
    }

    @Override
    public void requestCreateIngredient() {
        String ingredientIDText = this.view.getIngredientID();

        // Check name valid
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
        int ingredientCost = 0;
        try {
            ingredientCost = Integer.parseInt(ingredientCostText);
        } catch (NumberFormatException ex) {
            this.view.showErrorMessage("Please enter ingredient cost in number.");
            return;
        }

        if (ingredientCost <= 0) {
            this.view.showErrorMessage("Ingredient cost is negative.");
            return;
        }

        int providerSelectIndex = this.view.getProviderSelectIndex();
        int ingredientTypeSelectIndex = this.view.getIngredientTypeSelectIndex();
        int ingredientUnitSelectIndex = this.view.getIngredientUnitSelectIndex();

        // Update model
        this.model.prepareCreateIngredient();
        this.model.setIngredientName(ingredientName);
        this.model.setIngredientCost(ingredientCost);
        this.model.setIngredientProvider(providerSelectIndex);
        this.model.setIngredientType(ingredientTypeSelectIndex);
        this.model.setIngredientUnit(ingredientUnitSelectIndex);
        this.model.createIngredient();

        // Update view
        this.view.setTextfSearch("");
        this.view.resetIngredientList();
    }

    @Override
    public void requestUpdateIngredient() {

    }

    @Override
    public void requestRemoveIngreident() {

    }

    @Override
    public void checkNewIngredientTypeInput() {
        String ingredientTypeName = this.newIngredientTypeCreateDialog.getIngredientTypeName();
        boolean isExist = this.model.isIngredientTypeExist(ingredientTypeName);
        if (isExist) {
            this.newIngredientTypeCreateDialog.showErrorMessage("Ingredient type with name is already existed.");
        } else {
            this.model.createNewIngredientType(ingredientTypeName);
            this.newIngredientTypeCreateDialog.dispose();
        }
    }

}
