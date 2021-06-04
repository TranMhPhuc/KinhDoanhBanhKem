package model.product;

import model.DatabaseModel;
import model.ingredientOfProduct.IngredientOfProductDetailInterface;

public interface ProductModelInterface extends DatabaseModel {

    String getProductIDText();

    void addIngredientDetail(IngredientOfProductDetailInterface ingredientOfProductDetailInterface);

}
