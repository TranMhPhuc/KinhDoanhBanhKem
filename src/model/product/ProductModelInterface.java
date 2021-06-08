package model.product;

import java.util.Iterator;
import model.DatabaseModel;
import model.ingredientOfProduct.IngredientDetailOfProductInterface;

public interface ProductModelInterface extends DatabaseModel {

    void setName(String name);
    
    void setSize(String size);
    
    void setCost(long cost);
    
    void setAmount(int amount);
    
    void setPrice(long price);
    
    String getProductIDText();

    String getName();
    
    String getSize();
    
    long getCost();
    
    int getAmount();
    
    long getPrice();
    
    Iterator<IngredientDetailOfProductInterface> getAllIngredient();
    
    void addIngredient(IngredientDetailOfProductInterface ingredient);
    
    void removeIngredient(IngredientDetailOfProductInterface ingredient);
    
    void updateIngredient(IngredientDetailOfProductInterface ingredient);
    
}
