package model.ingredientOfProduct;

import model.DatabaseModel;
import model.ingredient.IngredientModelInterface;
import model.ingredient.unit.IngredientUnitModelInterface;
import model.product.ProductModelInterface;

public interface IngredientOfProductModelInterface extends DatabaseModel {

    void setProduct(ProductModelInterface product);
    
    void setIngredient(IngredientModelInterface ingredient);
    
    void setAmount(int amount);
    
    void setIngredientUnit(IngredientUnitModelInterface unit);
    
    ProductModelInterface getProduct();
    
    IngredientModelInterface getIngredient();
    
    int getAmount();
    
    IngredientUnitModelInterface getUnit();
    
}
