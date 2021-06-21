package model.ingredient;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import model.DatabaseUpdate;
import model.ingredient.importDetail.IngredientImportDetailInterface;
import model.ingredient.type.IngredientTypeModelInterface;
import view.ingredient.InsertedIngredientObserver;
import view.ingredient.InsertedIngredientTypeObserver;
import view.ingredient.ModifiedIngredientObserver;
import view.ingredient.RemovedIngredientObserver;

public interface IngredientManageModelInterface extends DatabaseUpdate {

    void registerInsertedIngredientObserver(InsertedIngredientObserver observer);

    void removeInsertedIngredientObserver(InsertedIngredientObserver observer);

    void registerModifiedIngredientObserver(ModifiedIngredientObserver observer);

    void removeModifiedIngredientObserver(ModifiedIngredientObserver observer);

    void registerRemovedIngredientObserver(RemovedIngredientObserver observer);

    void removeRemovedIngredientObserver(RemovedIngredientObserver observer);

    void registerInsertedIngredientTypeObserver(InsertedIngredientTypeObserver observer);

    void removeInsertedIngredientTypeObserver(InsertedIngredientTypeObserver observer);

    //=========================================================================
    String getNextIngredientIDText();

    String getNextIngredientTypeIDText();

    //=========================================================================
    
    int getIngredientTotalNumber();

    Iterator<IngredientModelInterface> getAllIngredientData();

    Iterator<IngredientModelInterface> getIngredientSearchByName(String searchText);

    IngredientModelInterface getIngredientByID(String ingredientIDText);

    IngredientModelInterface getIngredientByIndex(int ingredientIndex);

    IngredientModelInterface getIngredientByName(String ingredientName);

    void addIngredient(IngredientModelInterface ingredient);

    boolean updateIngredient(IngredientModelInterface ingredient);

    boolean removeIngredient(IngredientModelInterface ingredient);

    boolean isIngredientNameExisted(String ingredientName);

    List<String> getAllIngredientTypeName();

    List<String> getAllIngredientUnitName();

    List<String> getAllProviderName();
    
    void importIngredient(IngredientModelInterface ingredient, Date importDate, 
            int importAmount, String importUnitName);

    List<IngredientImportDetailInterface> getImportHistoryFromDateRange(Date dateFrom, 
            Date dateTo);
    
    void updateProviderNameOfIngredientData();
    
    void addIngredientType(IngredientTypeModelInterface ingredientType);
}
