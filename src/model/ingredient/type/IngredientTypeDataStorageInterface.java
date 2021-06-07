package model.ingredient.type;

import java.util.Iterator;
import model.DatabaseUpdate;

public interface IngredientTypeDataStorageInterface extends DatabaseUpdate{
    
    IngredientTypeModelInterface getIngredientType(String ingredientIDText);
    
    IngredientTypeModelInterface getIngredientType(int ingredientTypeIndex);

    void addIngredientType(IngredientTypeModelInterface ingredientType);
    
    Iterator<IngredientTypeModelInterface> createIterator();
    
    int getSize();
    
}
