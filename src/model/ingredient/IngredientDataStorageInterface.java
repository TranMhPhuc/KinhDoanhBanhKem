package model.ingredient;

import model.DatabaseUpdate;

public interface IngredientDataStorageInterface extends DatabaseUpdate {

    IngredientModelInterface createIngredient();
    
    int getSize();
    
}
