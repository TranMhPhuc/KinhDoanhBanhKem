package view.ingredient;

import model.ingredient.IngredientModelInterface;

public interface RemovedIngredientObserver {

    void updateRemovedIngredient(IngredientModelInterface ingredient);
    
}
