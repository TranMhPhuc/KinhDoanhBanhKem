package model.ingredient;

import model.DatabaseModel;
import model.ingredient.type.IngredientTypeModelInterface;
import model.ingredient.unit.IngredientUnitModelInterface;
import model.provider.ProviderModelInterface;

public interface IngredientModelInterface extends DatabaseModel {

    String getIngredientIDText();
    
    void setName(String name);
    
    void setIngredientType(IngredientTypeModelInterface type);
    
    void setCost(int cost);
    
    void setAmount(float amount);
    
    void setProvider(ProviderModelInterface provider);
    
    void setIngredientUnit(IngredientUnitModelInterface unit);
    
    String getName();
    
    IngredientTypeModelInterface getIngredientType();
    
    int getCost();
    
    float getAmount();
    
    ProviderModelInterface getProvider();
    
    IngredientUnitModelInterface getIngredientUnit();
}
