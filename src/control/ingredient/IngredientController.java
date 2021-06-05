package control.ingredient;

import model.ingredient.IngredientModelInterface;
import view.function.product.IngredientPanel;

public class IngredientController implements IngredientControllerInterface {

    private volatile static IngredientController uniqueInstance;

    private IngredientModelInterface model;
    private IngredientPanel view;

    private IngredientController(IngredientModelInterface model) {
        this.model = model;
        this.view = IngredientPanel.getInstance(model, this);
    }

    public static IngredientController getInstance(IngredientModelInterface model) {
        if (uniqueInstance == null) {
            synchronized (IngredientController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new IngredientController(model);
                }
            }
        }
        return uniqueInstance;
    }

    public static IngredientController getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }
}
