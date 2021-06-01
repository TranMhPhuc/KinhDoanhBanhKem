package model.ingredient;

import model.ingredientOfProduct.IngredientOfProductDetailInterface;

public interface IngredientModelInterface {

    String getIngredientIDText();
    
    void addProductDetail(IngredientOfProductDetailInterface ingredientOfProductDetailInterface);
}
