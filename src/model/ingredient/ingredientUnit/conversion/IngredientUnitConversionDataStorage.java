package model.ingredient.ingredientUnit.conversion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;
import util.AppLog;

public class IngredientUnitConversionDataStorage implements DatabaseUpdate {

    private static IngredientUnitConversionDataStorage uniqueInstance;

    private ArrayList<IngredientUnitConversionModelInterface> unitConversions;

    static {
        uniqueInstance = new IngredientUnitConversionDataStorage();
    }

    private IngredientUnitConversionDataStorage() {
        unitConversions = new ArrayList<>();
    }

    public static IngredientUnitConversionDataStorage getInstance() {
        return uniqueInstance;
    }

    @Override
    public void updateFromDB(Connection connection) {
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + IngredientUnitConversionModel.TABLE_NAME;

            ResultSet resultSet = statement.executeQuery(query);

            unitConversions.clear();

            while (resultSet.next()) {
                unitConversions.add(IngredientUnitConversionModel.getInstance(resultSet));
            }
            
            AppLog.getLogger().info("Update ingredient unit conversion database: successfully, " 
                    + unitConversions.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update ingredient unit conversion database: error");
        }
    }

    
}
