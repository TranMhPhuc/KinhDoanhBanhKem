package model.ingredient;

import java.util.Iterator;
import model.DatabaseUpdate;

public interface IngredientDataStorageInterface extends DatabaseUpdate {

    IngredientModelInterface getIngredient(String ingredientIDText);

    void addNewIngredient(IngredientModelInterface ingredient);

    int getSize();

    Iterator<IngredientModelInterface> createIterator();

    Iterator<IngredientModelInterface> getIngredientSearchByName(String searchText);

}
