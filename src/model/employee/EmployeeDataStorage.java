package model.employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import util.AppLog;

public class EmployeeDataStorage implements EmployeeDataStorageInterface, EmployeeManageModelInterface {

    private static EmployeeDataStorage uniqueInstance;

    private ArrayList<EmployeeModelInterface> employees;

    static {
        uniqueInstance = new EmployeeDataStorage();
    }

    private EmployeeDataStorage() {
        employees = new ArrayList<>();
    }

    public static EmployeeDataStorageInterface getInstance() {
        return uniqueInstance;
    }

    @Override
    public void updateFromDB(Connection connection) {
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + EmployeeModel.TABLE_NAME;

            ResultSet resultSet = statement.executeQuery(query);

            employees.clear();

            while (resultSet.next()) {
                EmployeeModelInterface employee = new EmployeeModel();
                employee.setProperty(resultSet);
                employees.add(employee);
            }

            AppLog.getLogger().info("Update employee database: sucessfully, "
                    + employees.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update employee database: error.");
        }
    }

    public EmployeeModelInterface getEmployee(String employeeIDText) {
        for (EmployeeModelInterface element : employees) {
            if (element.getEmployeeIDText().equals(employeeIDText)) {
                return element;
            }
        }
        return null;
    }
}
