package control.ingredient;

import java.util.Iterator;
import model.ingredient.IngredientModelInterface;
import view.ingredient.IngredientPanel;

public interface IngredientControllerInterface {
    
    void setIngredientPanelView(IngredientPanel ingredientPanel);
    
    void requestCreateIngredient();
    
    void requestUpdateIngredient();
    
    void requestRemoveIngredient();

    void requestViewImportHistory();

    void requestImportIngredient();
    
    void requestExportExcel();
    
    void requestCreateTemplateExcel();

    void requestShowIngredientInfo();

    void requestCreateNewIngredientType();
    
    void createNewIngredientType();
    
    boolean isUnitModifiable();
    
    boolean isSearchMatching(String searchText, IngredientModelInterface ingredient);
    
    boolean deleteIngredientInSearchList(IngredientModelInterface ingredient);
    
    Iterator<IngredientModelInterface> getAllIngredientData();
    
    Iterator<IngredientModelInterface> getIngredientBySearchName(String searchText);
    
    boolean isNewIngredientTypeNameVallid(String ingredientTypeName);
    
    void showTotalCostIngredientImport();
    
    void importIngredient();
    
    void viewImportHistory();
    
    boolean canCloseIngredientManagePanel();
}
