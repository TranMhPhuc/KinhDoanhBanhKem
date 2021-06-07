package model.ingredient.type;

import model.DatabaseModel;

public interface IngredientTypeModelInterface extends DatabaseModel {

    void setIngredientTypeID(int id);
    
    void setName(String name);
    
    String getIngredientTypeIDText();
    
    String getName();
}
