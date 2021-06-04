package model.ingredient;

import model.DatabaseModel;
import model.ingredientOfProduct.IngredientOfProductDetailInterface;

public interface IngredientModelInterface extends DatabaseModel {

    String getIngredientIDText();
    
    void addProductDetail(IngredientOfProductDetailInterface ingredientOfProductDetailInterface);
}
