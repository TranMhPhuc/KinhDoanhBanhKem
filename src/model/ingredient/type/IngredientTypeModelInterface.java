package model.ingredient.type;

import model.DatabaseModel;

public interface IngredientTypeModelInterface extends DatabaseModel {

    void setIngredientTypeIDText(String id);
    
    void setName(String name);
    
    String getIngredientTypeIDText();
    
    String getName();
}
