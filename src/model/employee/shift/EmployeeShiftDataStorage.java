package model.employee.shift;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;
import util.AppLog;

public class EmployeeShiftDataStorage implements DatabaseUpdate {

    private static EmployeeShiftDataStorage uniqueInstance;
    
    private ArrayList<EmployeeShiftModel> shifts;

    static {
        uniqueInstance = new EmployeeShiftDataStorage();
    }
    
    private EmployeeShiftDataStorage() {
        shifts = new ArrayList<>();
    }

    public static EmployeeShiftDataStorage getInstance() {
        return uniqueInstance;
    }
    
    @Override
    public void updateFromDB(Connection connection) {
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + EmployeeShiftModel.TABLE_NAME;

            ResultSet resultSet = statement.executeQuery(query);

            shifts.clear();

            while (resultSet.next()) {
                shifts.add(EmployeeShiftModel.getInstance(resultSet));
            }
            
            AppLog.getLogger().info("Update employee shift database: sucessfully, " + shifts.size() + " rows inserted.");
                
        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update employee shift database: error.");
        }
    }
    
    public EmployeeShiftModel getShift(String shiftIDText) {
        for (EmployeeShiftModel element: shifts) {
            if (element.getShiftIDText().equals(shiftIDText)) {
                return element;
            }
        }
        return null;
    }
}
