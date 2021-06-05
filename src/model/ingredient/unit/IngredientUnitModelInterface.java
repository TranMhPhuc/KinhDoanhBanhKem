package model.ingredient.unit;

import model.DatabaseModel;

public interface IngredientUnitModelInterface extends DatabaseModel {
    
    void setName(String name);

    String getIngredientUnitIDText();
    
    String getName();
}
