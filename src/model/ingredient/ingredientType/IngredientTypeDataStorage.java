package model.ingredient.ingredientType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;

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

        } catch (SQLException ex) {
        }
    }
    
    public IngredientTypeModelInterface getIngredientType(String ingredientTypeIDText) {
        for (IngredientTypeModelInterface element: ingredientTypes) {
            if (element.getIngredientIDText().equals(ingredientTypeIDText)) {
                return element;
            }
        }
        return null;
    }
    
    public void get() {
        for (IngredientTypeModelInterface e: ingredientTypes) {
            System.out.println(e);
        }
    }

}
