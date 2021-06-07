package model.ingredient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import util.AppLog;

public class IngredientDataStorage implements IngredientDataStorageInterface {

    private static IngredientDataStorage uniqueInstance;

    private ArrayList<IngredientModelInterface> ingredients;

    static {
        uniqueInstance = new IngredientDataStorage();
    }

    private IngredientDataStorage() {
        ingredients = new ArrayList<>();
    }

    public static IngredientDataStorage getInstance() {
        return uniqueInstance;
    }

    @Override
    public void updateFromDB(Connection connection) {
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + IngredientModel.TABLE_NAME;

            ResultSet resultSet = statement.executeQuery(query);

            ingredients.clear();

            while (resultSet.next()) {
                IngredientModelInterface ingredient = new IngredientModel();
                ingredient.setProperty(resultSet);
                ingredients.add(ingredient);
            }

            resultSet.close();
            statement.close();

            AppLog.getLogger().info("Update ingredient database: successfully, "
                    + ingredients.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update ingredient database: error.");
        }
    }

    @Override
    public IngredientModelInterface getIngredientByID(String ingredientIDText) {
        for (IngredientModelInterface element : ingredients) {
            if (element.getIngredientIDText().equals(ingredientIDText)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Ingredient id '" + ingredientIDText + "' is not existed.");
    }

    @Override
    public void add(IngredientModelInterface ingredient) {
        if (ingredient == null) {
            throw new NullPointerException();
        }
        int index = this.ingredients.indexOf(ingredient);
        if (index != -1) {
            throw new IllegalArgumentException("Ingredient instance is already existed.");
        } else {
            this.ingredients.add(ingredient);
            ingredient.insertToDatabase();
        }
    }

    @Override
    public int getSize() {
        return this.ingredients.size();
    }

    @Override
    public Iterator<IngredientModelInterface> createIterator() {
        return this.ingredients.iterator();
    }

    @Override
    public Iterator<IngredientModelInterface> getIngredientSearchByName(String searchText) {
        List<IngredientModelInterface> ret = new ArrayList<>();
        List<BoundExtractedResult<IngredientModelInterface>> matches = FuzzySearch
                .extractSorted(searchText, this.ingredients, ingredient -> ingredient.getName(), 60);
        for (BoundExtractedResult<IngredientModelInterface> element : matches) {
            ret.add(element.getReferent());
        }
        return ret.iterator();
    }

    @Override
    public IngredientModelInterface getIngredientByIndex(int ingredientIndex) {
        if (ingredientIndex < 0 || ingredientIndex >= ingredients.size()) {
            throw new IndexOutOfBoundsException("Ingredient index is out of bound.");
        }
        return this.ingredients.get(ingredientIndex);
    }

    @Override
    public boolean update(IngredientModelInterface ingredient) {
        if (ingredient == null) {
            throw new NullPointerException("Ingredient instance is null.");
        }
        int index = this.ingredients.indexOf(ingredient);
        if (index == -1) {
            return false;
        } else {
            ingredient.updateInDatabase();
            return true;
        }
    }

    @Override
    public boolean remove(IngredientModelInterface ingredient) {
        if (ingredient == null) {
            throw new NullPointerException("Ingredient instance is null.");
        }
        int index = this.ingredients.indexOf(ingredient);
        if (index == -1) {
            return false;
        } else {
            ingredient.deleteInDatabase();
            this.ingredients.remove(index);
            return true;
        }
    }

    @Override
    public boolean isIngredientNameExisted(String ingredientName) {
        if (ingredientName.isEmpty()) {
            throw new IllegalArgumentException("Ingredient name is empty.");
        }
        for (IngredientModelInterface ingredient : ingredients) {
            if (ingredient.getName().equals(ingredientName)) {
                return true;
            }
        }
        return false;
    }

}
