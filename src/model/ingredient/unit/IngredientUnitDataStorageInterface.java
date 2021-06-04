package model.ingredient.unit;

import model.DatabaseUpdate;

public interface IngredientUnitDataStorageInterface extends DatabaseUpdate {

    IngredientUnitModelInterface getIngredientUnit(String ingredientUnitIDText);
    
}
