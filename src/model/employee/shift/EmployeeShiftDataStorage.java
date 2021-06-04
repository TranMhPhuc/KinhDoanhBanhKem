package model.employee.shift;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import util.AppLog;

public class EmployeeShiftDataStorage implements EmployeeShiftDataStorageInterface {

    private static EmployeeShiftDataStorage uniqueInstance;
    
    private ArrayList<EmployeeShiftModelInterface> shifts;

    static {
        uniqueInstance = new EmployeeShiftDataStorage();
    }
    
    private EmployeeShiftDataStorage() {
        shifts = new ArrayList<>();
    }

    public static EmployeeShiftDataStorageInterface getInstance() {
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
                EmployeeShiftModelInterface shift = new EmployeeShiftModel();
                shift.setProperty(resultSet);
                shifts.add(shift);
            }
            
            AppLog.getLogger().info("Update employee shift database: sucessfully, " + shifts.size() + " rows inserted.");
                
        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update employee shift database: error.");
        }
    }
    
    @Override
    public EmployeeShiftModelInterface getShift(String shiftIDText) {
        for (EmployeeShiftModelInterface element: shifts) {
            if (element.getShiftIDText().equals(shiftIDText)) {
                return element;
            }
        }
        return null;
    }
    
    @Override
    public Iterator<EmployeeShiftModelInterface> createIterator() {
        return shifts.iterator();
    }
    
    @Override
    public int getSize() {
        return shifts.size();
    }
}
