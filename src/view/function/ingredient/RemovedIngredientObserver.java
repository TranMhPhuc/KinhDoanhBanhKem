package view.function.ingredient;

import model.ingredient.IngredientModelInterface;

public interface RemovedIngredientObserver {

    void updateRemovedIngredient(IngredientModelInterface ingredient);
    
}
