package control.ingredient;

import java.util.Iterator;
import model.ingredient.IngredientModelInterface;

public interface IngredientControllerInterface {
    
    void requestCreateIngredient();
    
    void requestUpdateIngredient();
    
    void requestRemoveIngredient();

    void requestViewImportHistory();

    void requestImportIngredient();
    
    void requestImportExcel();
    
    void requestExportExcel();

    void requestShowIngredientInfo();

    void requestCreateNewIngredientType();
    
    void createNewIngredientType();
    
    boolean insertToSearchListByMatchingName(String searchText, IngredientModelInterface ingredient);
    
    boolean deleteIngredientInSearchList(IngredientModelInterface ingredient);
    
    Iterator<IngredientModelInterface> getAllIngredientData();
    
    Iterator<IngredientModelInterface> getIngredientBySearchName(String searchText);
}
