package model.ingredient.ingredientUnit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;
import util.AppLog;

public class IngredientUnitDataStorage implements DatabaseUpdate {
    
    private static IngredientUnitDataStorage uniqueInstance;
    
    private ArrayList<IngredientUnitModelInterface> ingredientUnits;
    
    static {
        uniqueInstance = new IngredientUnitDataStorage();
    }
    
    private IngredientUnitDataStorage() {
        ingredientUnits = new ArrayList<>();
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
            
            AppLog.getLogger().info("Update ingredient unit database: sucessfully, " 
                    + ingredientUnits.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().info("Update ingredient unit database: error.");
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
