package model.bill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DatabaseUpdate;

public class BillDataStorage implements DatabaseUpdate {

    private volatile static BillDataStorage uniqueInstance;

    private static ArrayList<BillModelInterface> bills;

    static {
        uniqueInstance = new BillDataStorage();
        bills = new ArrayList<>();
    }

    private BillDataStorage() {
    }

    public static BillDataStorage getInstance() {
        return uniqueInstance;
    }

    @Override
    public void updateFromDB(Connection connection) {
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM HoaDon";

            ResultSet resultSet = statement.executeQuery(query);

            bills.clear();

            while (resultSet.next()) {
                bills.add(BillModel.getInstance(resultSet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillDataStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
