package model.ingredient.type;

import model.DatabaseModel;

public interface IngredientTypeModelInterface extends DatabaseModel {

    void setName(String name);
    
    String getIngredientTypeIDText();
    
    String getName();
}
