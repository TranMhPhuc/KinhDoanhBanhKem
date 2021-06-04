package model.ingredientOfProduct;

import java.util.ArrayList;
import model.DatabaseUpdate;
import model.ingredient.IngredientModelInterface;

public interface IngredientOfProductDataStorageInterface extends DatabaseUpdate {
    
    ArrayList<IngredientModelInterface> getIngredientOfProduct(String productIDText);

}
