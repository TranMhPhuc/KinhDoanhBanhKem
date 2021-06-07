package model.ingredient.unit;

import java.util.Iterator;
import model.DatabaseUpdate;

public interface IngredientUnitDataStorageInterface extends DatabaseUpdate {

    IngredientUnitModelInterface getIngredientUnit(String ingredientUnitIDText);
    
    IngredientUnitModelInterface getIngredientUnit(int ingredientUnitSelectIndex);
    
    int getSize();
    
    Iterator<IngredientUnitModelInterface> createIterator();
    
}
