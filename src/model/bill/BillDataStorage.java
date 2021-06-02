package model.bill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;
import util.AppLog;

public class BillDataStorage implements DatabaseUpdate {

    private volatile static BillDataStorage uniqueInstance;

    private ArrayList<BillModelInterface> bills;

    static {
        uniqueInstance = new BillDataStorage();
    }

    private BillDataStorage() {
        bills = new ArrayList<>();
    }

    public static BillDataStorage getInstance() {
        return uniqueInstance;
    }

    @Override
    public void updateFromDB(Connection connection) {
        String dateNow = java.time.LocalDate.now().toString();

        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + BillModel.TABLE_NAME;

            ResultSet resultSet = statement.executeQuery(query);

            bills.clear();

            while (resultSet.next()) {
                if (resultSet.getDate(BillModel.DATE_HEADER).toString().equals(dateNow)) {
                    bills.add(BillModel.getInstance(resultSet));
                }
            }

            AppLog.getLogger().info("Update bill database: successfully, " + bills.size() + " rows inserted.");
        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update bill database: error.");
        }
    }

    public BillModelInterface getBill(String billIDText) {
        for (BillModelInterface element : bills) {
            if (element.getBillIDText().equals(billIDText)) {
                return element;
            }
        }
        return null;
    }
}
