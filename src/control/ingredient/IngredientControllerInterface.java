package control.ingredient;

import java.util.Iterator;
import model.ingredient.IngredientModelInterface;

public interface IngredientControllerInterface {
    
    void requestCreateIngredient();
    
    void requestUpdateIngredient();
    
    void requestRemoveIngreident();

    void viewIngredientImportHistory(int rowID);

    void requestImportIngredient(int rowID);

    void selectRowTableIngredient(int rowID);

    void requestCreateNewIngredientType();
    
    void checkNewIngredientTypeInput();
    
    Iterator<IngredientModelInterface> getAllIngredient();
    
    Iterator<IngredientModelInterface> getIngredientSearchByName(String searchText);
}
