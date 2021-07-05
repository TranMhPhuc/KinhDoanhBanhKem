package model.ingredient.importDetail;

import java.sql.Timestamp;
import model.DatabaseModel;

public interface IngredientImportDetailInterface extends DatabaseModel {

    void setIngredientName(String ingredientName);
    
    void setDate(Timestamp date);
    
    void setAmount(int amount);
    
    void setTotalCost(long cost);
    
    void setImportUnitName(String importUnitName);
    
    String getIngredientName();
    
    Timestamp getDate();
    
    int getAmount();
    
    long getTotalCost();
    
    String getImportUnitName();
    
}
