package model.ingredient;

import model.DatabaseModel;

public interface IngredientModelInterface extends DatabaseModel {

    String getIngredientIDText();

    void setIngredientID(String ingredientIDText);
    
    void setName(String name);
    
    void setIngredientTypeName(String typeName);
    
    void setCost(long cost);
    
    void setAmount(float amount);
    
    void setProviderName(String providerName);
    
    void setUnitName(String unitName);
    
    String getName();
    
    String getTypeName();
    
    long getCost();
    
    float getAmount();
    
    String getProviderName();
    
    String getUnitName();
}
