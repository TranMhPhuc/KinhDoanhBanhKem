package model.product;

import model.DatabaseModel;
import model.ingredientOfProduct.IngredientOfProductModelInterface;

public interface ProductModelInterface extends DatabaseModel {

    String getProductIDText();

    void addIngredientDetail(IngredientOfProductModelInterface ingredientOfProductDetailInterface);

}
