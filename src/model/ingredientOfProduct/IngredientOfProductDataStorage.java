package model.ingredientOfProduct;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.ingredient.IngredientModelInterface;
import util.AppLog;

public class IngredientOfProductDataStorage implements IngredientOfProductDataStorageInterface {
    
    private static IngredientOfProductDataStorage uniqueInstance;
    
    private ArrayList<IngredientOfProductModelInterface> ingredientOfProductDetails;
    
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

            String query = "SELECT * FROM " + IngredientOfProductModel.TABLE_NAME;

            ResultSet resultSet = statement.executeQuery(query);

            ingredientOfProductDetails.clear();

            while (resultSet.next()) {
                IngredientOfProductModelInterface productIngredient = new IngredientOfProductModel();
                productIngredient.setProperty(resultSet);
                ingredientOfProductDetails.add(productIngredient);
            }
            
            AppLog.getLogger().info("Update ingredient of product database: successfully, " 
                    + ingredientOfProductDetails.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update ingredient of product database: error.");
        }
    }

    @Override
    public ArrayList<IngredientModelInterface> getIngredientOfProduct(String productIDText) {
        ArrayList<IngredientModelInterface> ret = null;
        return ret;
    }
}
