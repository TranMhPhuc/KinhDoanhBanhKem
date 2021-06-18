package model.product.ingredientDetail;

import model.DatabaseModel;
import model.product.ProductModelInterface;

public interface IngredientDetailModelInterface extends DatabaseModel, 
        Comparable<IngredientDetailModelInterface> {

    void setProduct(ProductModelInterface product);
    
    void setIngredientName(String ingredientName);
    
    void setAmount(float amount);
    
    void setUnitName(String unitName);
    
    ProductModelInterface getProduct();
    
    String getIngredientName();
    
    String getIngredientTypeName();
    
    float getAmount();
    
    String getUnitName();
    
}
