package model.product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;

public class ProductDataStorage implements DatabaseUpdate {

    private static ProductDataStorage uniqueInstance;

    private static ArrayList<ProductModelInterface> products;
    
    static {
        uniqueInstance = new ProductDataStorage();
        products = new ArrayList<>();
    }
    
    private ProductDataStorage() {
    }

    public static ProductDataStorage getInstance() {
        return uniqueInstance;
    }
    
    @Override
    public void updateFromDB(Connection connection) {
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + ProductModel.TABLE_NAME;

            ResultSet resultSet = statement.executeQuery(query);

            products.clear();

            while (resultSet.next()) {
                products.add(ProductModel.getInstance(resultSet));
            }

        } catch (SQLException ex) {
        }
    }
    
    public ProductModelInterface getProduct(String productIDText) {
        for (ProductModelInterface element: products) {
            if (element.getProductIDText().equals(productIDText)) {
                return element;
            }
        }
        return null;
    }
    

}
