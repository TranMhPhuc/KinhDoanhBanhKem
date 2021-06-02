package model.ingredient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;
import util.AppLog;

public class IngredientDataStorage implements DatabaseUpdate {

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
                ingredients.add(IngredientModel.getInstance(resultSet));
            }

            AppLog.getLogger().info("Update ingredient database: successfully, "
                    + ingredients.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update ingredient database: error.");
        }
    }

    public IngredientModelInterface getIngredient(String ingredientIDText) {
        for (IngredientModelInterface element : ingredients) {
            if (element.getIngredientIDText().equals(ingredientIDText)) {
                return element;
            }
        }
        return null;
    }
}
