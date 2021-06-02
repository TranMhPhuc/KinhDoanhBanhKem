package model.productOfBill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;
import util.AppLog;

public class ProductOfBillDetailDataStorage implements DatabaseUpdate {
    
    private static ProductOfBillDetailDataStorage uniqueInstance;
    
    private ArrayList<ProductOfBillDetailInterface> storage;
    
    static {
        uniqueInstance = new ProductOfBillDetailDataStorage();
    }

    private ProductOfBillDetailDataStorage() {
        storage = new ArrayList<>();
    }
    
    public static ProductOfBillDetailDataStorage getInstance() {
        return uniqueInstance;
    }

    @Override
    public void updateFromDB(Connection connection) {
         try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + ProductOfBillDetail.TABLE_NAME;

            ResultSet resultSet = statement.executeQuery(query);

            storage.clear();

            while (resultSet.next()) {
                storage.add(ProductOfBillDetail.getInstance(resultSet));
            }

             AppLog.getLogger().info("Update bill's product database: successfully, " + storage.size() + " rows inserted");
        } catch (SQLException ex) {
             AppLog.getLogger().info("Update bill's product database: error.");
        }
    }
    
}
