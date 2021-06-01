package model.employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DatabaseUpdate;

public class EmployeeDataStorage implements DatabaseUpdate {

    private static EmployeeDataStorage uniqueInstance;

    private static ArrayList<EmployeeModelInterface> employees;

    static {
        uniqueInstance = new EmployeeDataStorage();
        employees = new ArrayList<>();
    }

    private EmployeeDataStorage() {
    }

    public static EmployeeDataStorage getInstance() {
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
                employees.add(EmployeeModel.getInstance(resultSet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDataStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public EmployeeModelInterface getEmployee(String employeeIDText) {
        for (EmployeeModelInterface element: employees) {
            if (element.getEmployeeIDText().equals(employeeIDText)) {
                return element;
            }
        }
        return null;
    }
}
