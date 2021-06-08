package model.product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import util.AppLog;

public class ProductDataStorage implements ProductDataStorageInterface {

    private static ProductDataStorage uniqueInstance;

    private ArrayList<ProductModelInterface> products;
    
    static {
        uniqueInstance = new ProductDataStorage();
    }

    private ProductDataStorage() {
        products = new ArrayList<>();
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
            
            resultSet.close();
            statement.close();

            AppLog.getLogger().info("Update product database: successfully, "
                    + products.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update product database: error.");
        }
    }

    @Override
    public ProductModelInterface getProductByID(String productIDText) {
        for (ProductModelInterface product : products) {
            if (product.getProductIDText().equals(productIDText)) {
                return product;
            }
        }
        throw new IllegalArgumentException("Product id '" + productIDText +  "' is not existed.");
    }

    @Override
    public int getSize() {
        return this.products.size();
    }

    @Override
    public Iterator<ProductModelInterface> getProductSearchByName(String searchText) {
        List<ProductModelInterface> ret = new ArrayList<>();
        List<BoundExtractedResult<ProductModelInterface>> matches = FuzzySearch
                .extractSorted(searchText, this.products, product -> product.getName(), 80);
        for (BoundExtractedResult<ProductModelInterface> element : matches) {
            ret.add(element.getReferent());
        }
        return ret.iterator();
    }

    @Override
    public Iterator<ProductModelInterface> createIterator() {
        return this.products.iterator();
    }

    @Override
    public void add(ProductModelInterface product) {
        if (product == null) {
            throw new NullPointerException();
        }
        int index = this.products.indexOf(product);
        if (index != -1) {
            throw new IllegalArgumentException("Product instance is already existed.");
        } else {
            this.products.add(product);
            product.insertToDatabase();
        }
    }

    @Override
    public boolean update(ProductModelInterface product) {
        if (product == null) {
            throw new NullPointerException();
        }
        int index = this.products.indexOf(product);
        if (index == -1) {
            return false;
        } else {
            product.updateInDatabase();
            return true;
        }
    }

    @Override
    public boolean remove(ProductModelInterface product) {
        if (product == null) {
            throw new NullPointerException();
        }
        int index = this.products.indexOf(product);
        if (index == -1) {
            return false;
        } else {
            product.deleteInDatabase();
            this.products.remove(product);
            return true;
        }
    }

}
