package model.product;

import java.util.Iterator;
import model.DatabaseModel;
import model.product.ingredientDetail.IngredientDetailModelInterface;

public interface ProductModelInterface extends DatabaseModel {

    void setProductID(String id);
    
    void setName(String name);
    
    void setSize(ProductSize size);
    
    void setAmount(int amount);
    
    void setCost(long cost);
    
    void setPrice(long price);
    
    String getProductIDText();

    String getName();
    
    ProductSize getSize();
    
    long getCost();
    
    int getAmount();
    
    long getPrice();
    
    long getProfit();
        
    Iterator<IngredientDetailModelInterface> getAllIngredientDetail();
    
    void addIngredientDetail(IngredientDetailModelInterface ingredientDetail);
    
    void updateIngredientDetail(IngredientDetailModelInterface ingredientDetail);
    
    void removeIngredientDetail(IngredientDetailModelInterface ingredientDetail);
    
    void removeAllIngredientDetail();
    
    void reloadIngredientDetailList();
    
}
