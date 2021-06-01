package model.ingredientOfProduct;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;

public class IngredientOfProductDataStorage implements DatabaseUpdate {
    
    private static IngredientOfProductDataStorage uniqueInstance;
    
    private ArrayList<IngredientOfProductDetailInterface> ingredientOfProductDetails;
    
    static {
        uniqueInstance = new IngredientOfProductDataStorage();
    }

    private IngredientOfProductDataStorage() {
        ingredientOfProductDetails = new ArrayList<>();
    }
    
    public static final IngredientOfProductDataStorage getInstance() {
        return uniqueInstance;
    }

    @Override
    public void updateFromDB(Connection connection) {
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + IngredientOfProductDetail.TABLE_NAME;

            ResultSet resultSet = statement.executeQuery(query);

            ingredientOfProductDetails.clear();

            while (resultSet.next()) {
                ingredientOfProductDetails.add(IngredientOfProductDetail.getInstance(resultSet));
            }

        } catch (SQLException ex) {
        }
    }
}
