package model.ingredient.ingredientUnit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;

public class IngredientUnitDataStorage implements DatabaseUpdate {
    
    private static IngredientUnitDataStorage uniqueInstance;
    
    private static ArrayList<IngredientUnitModelInterface> ingredientUnits;
    
    static {
        uniqueInstance = new IngredientUnitDataStorage();
        ingredientUnits = new ArrayList<>();
    }
    
    private IngredientUnitDataStorage() {
    }
    
    public static IngredientUnitDataStorage getInstance() {
        return uniqueInstance;
    }

    @Override
    public void updateFromDB(Connection connection) {
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + IngredientUnitModel.TABLE_NAME;

            ResultSet resultSet = statement.executeQuery(query);

            ingredientUnits.clear();

            while (resultSet.next()) {
                ingredientUnits.add(IngredientUnitModel.getInstance(resultSet));
            }

        } catch (SQLException ex) {
        }
    }
    
    public IngredientUnitModelInterface getIngredientUnit(String ingredientUnitIDText) {
        for (IngredientUnitModelInterface element: ingredientUnits) {
            if (element.getIDText().equals(ingredientUnitIDText)) {
                return element;
            }
        }
        return null;
    }
    
}
