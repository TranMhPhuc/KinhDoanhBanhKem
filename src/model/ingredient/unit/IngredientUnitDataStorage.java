package model.ingredient.unit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import util.AppLog;

public class IngredientUnitDataStorage implements IngredientUnitDataStorageInterface {
    
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
                IngredientUnitModelInterface ingredientUnit = new IngredientUnitModel();
                ingredientUnit.setProperty(resultSet);
                ingredientUnits.add(ingredientUnit);
            }
            
            AppLog.getLogger().info("Update ingredient unit database: sucessfully, " 
                    + ingredientUnits.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update ingredient unit database: error.");
        }
    }
    
    @Override
    public IngredientUnitModelInterface getIngredientUnit(String ingredientUnitIDText) {
        for (IngredientUnitModelInterface element: ingredientUnits) {
            if (element.getIngredientUnitIDText().equals(ingredientUnitIDText)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Ingredient unit id '" + ingredientUnitIDText + "' is not existed.");
    }

    @Override
    public IngredientUnitModelInterface getIngredientUnit(int ingredientUnitSelectIndex) {
        if (ingredientUnitSelectIndex < 0 || ingredientUnitSelectIndex >= this.ingredientUnits.size()) {
            throw new IndexOutOfBoundsException();
        }
        return this.ingredientUnits.get(ingredientUnitSelectIndex);
    }

    @Override
    public int getSize() {
        return this.ingredientUnits.size();
    }

    @Override
    public Iterator<IngredientUnitModelInterface> createIterator() {
        return this.ingredientUnits.iterator();
    }
    
}
