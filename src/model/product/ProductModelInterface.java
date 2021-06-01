package model.product;

import model.ingredientOfProduct.IngredientOfProductDetailInterface;

public interface ProductModelInterface {
    String getProductIDText();
    
    void addIngredientDetail(IngredientOfProductDetailInterface ingredientOfProductDetailInterface);
    
    void registerObserver();
    
    void removeObserver();
    
    void notifyObserver();
}
