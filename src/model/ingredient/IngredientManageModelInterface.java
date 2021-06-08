package model.ingredient;

import java.util.Iterator;
import model.ingredient.type.IngredientTypeModelInterface;
import model.ingredient.unit.IngredientUnitModelInterface;
import model.provider.ProviderModelInterface;
import view.function.ingredient.InsertedIngredientObserver;
import view.function.ingredient.InsertedIngredientTypeObserver;
import view.function.ingredient.ModifiedIngredientObserver;
import view.function.ingredient.RemovedIngredientObserver;

public interface IngredientManageModelInterface {

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
    
    int getIngredientNumber();

    void exportIngredientData();

    void importIngredientData();

    //=========================================================================

    void addNewIngredient(IngredientModelInterface newIngredient);

    void updateIngredient(IngredientModelInterface updatedIngredient);

    void removeIngredient(IngredientModelInterface removedIngredient);

    //=========================================================================
    
    void addNewIngredientType(IngredientTypeModelInterface newIngredientType);
    
    Iterator<IngredientTypeModelInterface> getAllIngredientTypeData();
    
    boolean isIngredientTypeNameExist(String ingredientTypeName);
    
    IngredientTypeModelInterface getIngredientTypeByIndex(int ingredientTypeIndex);
    
    //=========================================================================
    
    Iterator<IngredientUnitModelInterface> getAllIngredientUnit();
    
    IngredientUnitModelInterface getIngredientUnitByIndex(int ingredientUnitIndex);
    
    //=========================================================================
    
    IngredientModelInterface getIngredient(String ingredientIDText);

    Iterator<IngredientModelInterface> getAllIngredientData();
    
    Iterator<ProviderModelInterface> getAllProviderData();
    
    int getProviderIndex(ProviderModelInterface provider);
    
    ProviderModelInterface getProviderByIndex(int providerIndex);

    Iterator<IngredientModelInterface> getIngredientSearchByName(String searchText);

    boolean isIngredientNameExist(String ingredientName);
    
    boolean isIngredientOfAnyProduct(IngredientModelInterface ingredient);

}
