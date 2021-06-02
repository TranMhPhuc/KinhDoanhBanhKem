package model.ingredient.ingredientType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;
import util.AppLog;

public class IngredientTypeDataStorage implements DatabaseUpdate {

    private static IngredientTypeDataStorage uniqueInstance;
    
    private ArrayList<IngredientTypeModelInterface> ingredientTypes;
    
    static {
        uniqueInstance = new IngredientTypeDataStorage();
    }
    
    private IngredientTypeDataStorage() {
        ingredientTypes = new ArrayList<>();
    }

    public static IngredientTypeDataStorage getInstance() {
        return uniqueInstance;
    }
    
    @Override
    public void updateFromDB(Connection connection) {
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + IngredientTypeModel.TABLE_NAME;

            ResultSet resultSet = statement.executeQuery(query);

            ingredientTypes.clear();

            while (resultSet.next()) {
                ingredientTypes.add(IngredientTypeModel.getInstance(resultSet));
            }
            
            AppLog.getLogger().info("Update ingredient type database: successfully, " 
                    + ingredientTypes.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update ingredient type database: error");
        }
    }
    
    public IngredientTypeModelInterface getIngredientType(String ingredientTypeIDText) {
        for (IngredientTypeModelInterface element: ingredientTypes) {
            if (element.getIngredientTypeIDText().equals(ingredientTypeIDText)) {
                return element;
            }
        }
        return null;
    }
    
}
