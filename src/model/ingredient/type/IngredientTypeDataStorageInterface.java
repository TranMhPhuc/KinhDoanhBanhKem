package model.ingredient.type;

import model.DatabaseUpdate;

public interface IngredientTypeDataStorageInterface extends DatabaseUpdate{

    IngredientTypeModelInterface createIngredientType();
    
}
