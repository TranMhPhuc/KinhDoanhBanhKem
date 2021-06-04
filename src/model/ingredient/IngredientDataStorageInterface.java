package model.ingredient;

import model.DatabaseUpdate;

public interface IngredientDataStorageInterface extends DatabaseUpdate {

    IngredientModelInterface getIngredient(String ingredientIDText);
    
    IngredientModelInterface createIngredient();
    
    int getSize();
    
}
