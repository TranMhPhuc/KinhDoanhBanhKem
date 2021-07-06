package model.ingredient.importDetail;

import java.sql.Timestamp;
import model.DatabaseModel;

public interface IngredientImportDetailInterface extends DatabaseModel {

    void setIngredientName(String ingredientName);
    
    void setProviderName(String providerName);
    
    void setDate(Timestamp date);
    
    void setAmount(int amount);
    
    void setTotalCost(long cost);
    
    void setImportUnitName(String importUnitName);
    
    String getIngredientName();
    
    String getProviderName();
    
    Timestamp getDate();
    
    int getAmount();
    
    long getTotalCost();
    
    String getImportUnitName();
    
}
