package model.ingredient;

import java.util.Iterator;
import model.DatabaseUpdate;

public interface IngredientDataStorageInterface extends DatabaseUpdate {

    IngredientModelInterface getIngredientByID(String ingredientIDText);
    
    IngredientModelInterface getIngredientByIndex(int ingredientIndex);
    
    int getSize();
    
    Iterator<IngredientModelInterface> createIterator();
    
    Iterator<IngredientModelInterface> getIngredientSearchByName(String searchText);

    void add(IngredientModelInterface ingredient);

    boolean update(IngredientModelInterface ingredient);
    
    boolean remove(IngredientModelInterface ingredient);

    boolean isIngredientNameExisted(String ingredientName);
    
}
