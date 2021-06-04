package model.bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.EmployeeModel;
import util.AppLog;

public class BillDataStorage implements BillDataStorageInterface, BillCreateModelInterface {

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
                    BillModelInterface bill = new BillModel();
                    bill.setProperty(resultSet);
                    bills.add(bill);
                }
            }

            AppLog.getLogger().info("Update bill database: successfully, " + bills.size() + " rows inserted.");
        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update bill database: error.");
        }
    }

    @Override
    public BillModelInterface getBill(String billIDText) {
        for (BillModelInterface element : bills) {
            if (element.getBillIDText().equals(billIDText)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public void addNewBill(BillModelInterface todayBill) {
        bills.add(todayBill);

        try {
            String addBillQuery
                    = "INSERT INTO " + BillModel.TABLE_NAME
                    + "(" + BillModel.ID_HEADER + ", " + BillModel.DATE_HEADER + ", "
                    + BillModel.PAYMENT_HEADER + ", " + BillModel.GUEST_MONEY_HEADER
                    + ", " + BillModel.CHANGE_MONEY_HEADER + EmployeeModel.ID_HEADER 
                    + ") VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = dbConnection.prepareCall(addBillQuery);
            
        } catch (SQLException ex) {
            Logger.getLogger(BillDataStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
