package model.product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import util.AppLog;
import view.function.product.ProductUpdateObserver;

public class ProductDataStorage implements ProductDataStorageInterface {

    private static ProductDataStorage uniqueInstance;

    private ArrayList<ProductModelInterface> products;
    
    private ArrayList<ProductUpdateObserver> observers;

    static {
        uniqueInstance = new ProductDataStorage();
    }

    private ProductDataStorage() {
        products = new ArrayList<>();
        observers = new ArrayList<>();
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
                ProductModelInterface product = new ProductModel();
                product.setProperty(resultSet);
                products.add(product);
            }

            AppLog.getLogger().info("Update product database: successfully, "
                    + products.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update product database: error.");
        }
    }

    public ProductModelInterface getProduct(String productIDText) {
        for (ProductModelInterface element : products) {
            if (element.getProductIDText().equals(productIDText)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Product id '" + productIDText +  "' is not existed.");
    }

    @Override
    public ProductModelInterface createProduct() {
        ProductModelInterface newProduct = new ProductModel();
        this.products.add(newProduct);
        return newProduct;
    }

    @Override
    public int getSize() {
        return this.products.size();
    }

    @Override
    public void registerObserver(ProductUpdateObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ProductUpdateObserver observer) {
        int id = observers.indexOf(observer);
        if (id >= 0) {
            observers.remove(observer);
        }
    }
    
    private void notifyObserver() {
        for (ProductUpdateObserver observer : observers) {
            observer.updateProductNumber(this.products.size());
        }
    }

}
