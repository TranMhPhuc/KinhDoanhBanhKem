package util.db;

import java.sql.Connection;
import model.bill.BillDataStorage;
import model.employee.EmployeeDataStorage;
import model.employee.position.EmployeePositionDataStorage;
import model.employee.shift.EmployeeShiftDataStorage;

/**
 * Utility class to update data model from database.
 *
 */
public class DataModelUpdateManager {

    private static DataModelUpdateManager uniqueInstance;

    private static Connection connection;

    private static BillDataStorage billDataStorage;
    private static EmployeeDataStorage employeeDataStorage;
    private static EmployeeShiftDataStorage employeeShiftDataStorage;
    private static EmployeePositionDataStorage employeePositionDataStorage;

    static {
        uniqueInstance = new DataModelUpdateManager();

        connection = SQLServerConnect.getConnection();

        billDataStorage = BillDataStorage.getInstance();
        employeeDataStorage = EmployeeDataStorage.getInstance();
        employeeShiftDataStorage = EmployeeShiftDataStorage.getInstance();
        employeePositionDataStorage = EmployeePositionDataStorage.getInstance();
    }

    private DataModelUpdateManager() {
    }

    public static DataModelUpdateManager getInstance() {
        return uniqueInstance;
    }

    public void updateFromDB() {
        employeeShiftDataStorage.updateFromDB(connection);
        employeePositionDataStorage.updateFromDB(connection);
        employeeDataStorage.updateFromDB(connection);
    }

    public static void main(String[] args) {
        DataModelUpdateManager dataModelUpdateManager = new DataModelUpdateManager();
        dataModelUpdateManager.updateFromDB();
    }
    
}
