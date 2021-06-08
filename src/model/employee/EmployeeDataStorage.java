package model.employee;

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
import util.constant.AppConstant;

public class EmployeeDataStorage implements EmployeeDataStorageInterface {

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

            resultSet.close();
            statement.close();

            AppLog.getLogger().info("Update employee database: sucessfully, "
                    + employees.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update employee database: error.");
        }
    }

    @Override
    public EmployeeModelInterface getEmployeeByID(String employeeIDText) {
        for (EmployeeModelInterface employee : employees) {
            if (employee.getEmployeeIDText().equals(employeeIDText)) {
                return employee;
            }
        }
        throw new IllegalArgumentException("Employee id '" + employeeIDText + "' is not existed.");
    }

    @Override
    public int getSize() {
        return this.employees.size();
    }

    @Override
    public Iterator<EmployeeModelInterface> createIterator() {
        return this.employees.iterator();
    }

    @Override
    public EmployeeModelInterface getEmployeeByIndex(int employeeIndex) {
        if (employeeIndex < 0 || employeeIndex >= this.employees.size()) {
            throw new IndexOutOfBoundsException("Employee index is out of bound.");
        }
        return this.employees.get(employeeIndex);
    }

    @Override
    public Iterator<EmployeeModelInterface> getEmployeeSearchByName(String searchText) {
        List<EmployeeModelInterface> ret = new ArrayList<>();
        List<BoundExtractedResult<EmployeeModelInterface>> matches = FuzzySearch
                .extractSorted(searchText, this.employees, employee -> employee.getName(), AppConstant.SEARCH_SCORE_CUT_OFF);
        for (BoundExtractedResult<EmployeeModelInterface> element : matches) {
            ret.add(element.getReferent());
        }
        return ret.iterator();
    }

    @Override
    public void add(EmployeeModelInterface employee) {
        if (employee == null) {
            throw new NullPointerException("Employee instance is null.");
        }
        int index = this.employees.indexOf(employee);
        if (index != -1) {
            throw new IllegalArgumentException("Employee instance is already existed.");
        }
        this.employees.add(employee);
        employee.insertToDatabase();
    }

    @Override
    public boolean update(EmployeeModelInterface employee) {
        if (employee == null) {
            throw new NullPointerException("Employee instance is null.");
        }
        int index = this.employees.indexOf(employee);
        if (index == -1) {
            return false;
        }
        employee.updateInDatabase();
        return true;
    }

    @Override
    public boolean remove(EmployeeModelInterface employee) {
        if (employee == null) {
            throw new NullPointerException("Employee instance is null.");
        }
        int index = this.employees.indexOf(employee);
        if (index == -1) {
            return false;
        }
        this.employees.remove(index);
        employee.deleteInDatabase();
        return true;
    }

}
