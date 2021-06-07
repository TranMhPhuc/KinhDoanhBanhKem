package model.ingredient;

import java.util.Iterator;
import java.util.List;
import model.ingredient.type.IngredientTypeModelInterface;
import model.ingredient.unit.IngredientUnitModelInterface;
import model.provider.ProviderModelInterface;
import view.function.ingredient.IngredientUpdateObserver;

public interface IngredientManageModelInterface {

    void registerObserver(IngredientUpdateObserver observer);

    void removeObserver(IngredientUpdateObserver observer);

    String getNextIngredientID();

    String getNextIngredientTypeID();

    int getIngredientNumber();

    void exportIngredientData();

    void importIngredientData();
    
    //=========================================================================
    
    void prepareCreateIngredient();

    void setIngredientName(String name);

    void setIngredientCost(int cost);

    void setIngredientProvider(int providerSelectIndex);

    void setIngredientType(int ingredientTypeSelectIndex);
    
    void setIngredientUnit(int ingredientUnitSelectIndex);
    
    void createIngredient();

    //=========================================================================
    
    void createNewIngredientType(String ingredientTypeName);

    void updateIngredient(String ingredientIDText);

    void removeIngredient(String ingredientIDText);

    Iterator<IngredientTypeModelInterface> getAllIngredientType();

    Iterator<ProviderModelInterface> getAllProvider();

    Iterator<IngredientModelInterface> getAllIngredient();
    
    Iterator<IngredientUnitModelInterface> getAllIngredientUnit();

    Iterator<IngredientModelInterface> getIngredientSearchByName(String searchText);

    boolean isIngredientTypeExist(String ingredientTypeName);

    boolean isIngredientNameExist(String ingredientName);

}
