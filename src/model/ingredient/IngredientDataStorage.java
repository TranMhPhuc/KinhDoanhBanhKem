package model.ingredient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;

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

        } catch (SQLException ex) {
        }
    }

    public void get() {
        for (IngredientModelInterface e : ingredients) {
            System.out.println(e);
        }
    }
}
