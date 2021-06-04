package model.ingredient.type;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import util.AppLog;

public class IngredientTypeDataStorage implements IngredientTypeDataStorageInterface {

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
                IngredientTypeModelInterface ingredientType = new IngredientTypeModel();
                ingredientType.setProperty(resultSet);
                ingredientTypes.add(ingredientType);
            }

            AppLog.getLogger().info("Update ingredient type database: successfully, "
                    + ingredientTypes.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update ingredient type database: error");
        }
    }

    public IngredientTypeModelInterface getIngredientType(String ingredientTypeIDText) {
        for (IngredientTypeModelInterface element : ingredientTypes) {
            if (element.getIngredientTypeIDText().equals(ingredientTypeIDText)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public IngredientTypeModelInterface createIngredientType() {
        IngredientTypeModelInterface newIngredientType = new IngredientTypeModel();
        this.ingredientTypes.add(newIngredientType);
        return newIngredientType;
    }

}
