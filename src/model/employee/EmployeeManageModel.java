package model.employee;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import model.user.UserModel;
import util.AppLog;
import util.constant.AppConstant;
import view.employee.InsertedEmployeeObserver;
import view.employee.ModifiedEmployeeObserver;

public class EmployeeManageModel implements EmployeeManageModelInterface {

    private static final String SP_FIND_NEXT_IDENTITY_EMPLOYEE
            = "{call get_next_identity_id_employee}";

    private static final String FIND_ALL_EMPLOYEE_QUERY
            = "select * from ChiTietNhanVien";

    private static final String SP_FIND_ALL_POSITION_NAME
            = "{call get_all_position_name}";

    private static final String SP_FIND_ALL_SHIFT_NAME
            = "{call get_all_shift_name}";

    private ArrayList<EmployeeModelInterface> employees;

    private List<InsertedEmployeeObserver> insertedEmployeeObservers;
    private List<ModifiedEmployeeObserver> modifiedEmployeeObservers;

    public EmployeeManageModel() {
        employees = new ArrayList<>();

        insertedEmployeeObservers = new ArrayList<>();
        modifiedEmployeeObservers = new ArrayList<>();

        updateFromDB();
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
                .extractSorted(searchText, this.employees, employee -> employee.getName(),
                        AppConstant.SEARCH_SCORE_CUT_OFF);
        for (BoundExtractedResult<EmployeeModelInterface> element : matches) {
            ret.add(element.getReferent());
        }
        return ret.iterator();
    }

    @Override
    public void addEmployee(EmployeeModelInterface employee) {
        if (employee == null) {
            throw new NullPointerException("Employee instance is null.");
        }
        int index = this.employees.indexOf(employee);
        if (index != -1) {
            throw new IllegalArgumentException("Employee instance already exists.");
        }
        this.employees.add(employee);
        employee.insertToDatabase();
        notifyInsertedEmployeeObserver(employee);
    }

    @Override
    public boolean updateEmployee(EmployeeModelInterface employee) {
        if (employee == null) {
            throw new NullPointerException("Employee instance is null.");
        }
        int index = this.employees.indexOf(employee);
        if (index == -1) {
            return false;
        }
        employee.updateInDatabase();
        notifyModifiedEmployeeObserver(employee);

        return true;
    }

    @Override
    public EmployeeModelInterface getEmployeeByID(String employeeIDText) {
        for (EmployeeModelInterface employee : employees) {
            if (employee.getEmployeeIDText().equals(employeeIDText)) {
                return employee;
            }
        }
        throw new IllegalArgumentException("Employee id '" + employeeIDText + "' doesn't exist.");
    }

    @Override
    public void registerInsertedEmployeeObserver(InsertedEmployeeObserver observer) {
        this.insertedEmployeeObservers.add(observer);
    }

    @Override
    public void removeInsertedEmployeeObserver(InsertedEmployeeObserver observer) {
        int id = this.insertedEmployeeObservers.indexOf(observer);
        if (id >= 0) {
            this.insertedEmployeeObservers.remove(id);
        }
    }

    @Override
    public void registerModifiedEmployeeObserver(ModifiedEmployeeObserver observer) {
        this.modifiedEmployeeObservers.add(observer);
    }

    @Override
    public void removeModifiedEmployeeObserver(ModifiedEmployeeObserver observer) {
        int id = this.modifiedEmployeeObservers.indexOf(observer);
        if (id >= 0) {
            this.modifiedEmployeeObservers.remove(id);
        }
    }

    private void notifyInsertedEmployeeObserver(EmployeeModelInterface insertedEmployee) {
        for (InsertedEmployeeObserver observer : insertedEmployeeObservers) {
            observer.updateInsertedEmployee(insertedEmployee);
        }
    }

    private void notifyModifiedEmployeeObserver(EmployeeModelInterface updatedEmployee) {
        for (ModifiedEmployeeObserver observer : modifiedEmployeeObservers) {
            observer.updateModifiedEmployee(updatedEmployee);
        }
    }

    @Override
    public String getNextEmployeeIDText() {
        int nextIdentity = 0;
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_FIND_NEXT_IDENTITY_EMPLOYEE);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                nextIdentity = resultSet.getInt(1);
            }
            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return String.valueOf(nextIdentity);
    }

    @Override
    public Iterator<EmployeeModelInterface> getAllEmployeeData() {
        return employees.iterator();
    }

    @Override
    public void updateFromDB() {
        try {
            Statement statement = dbConnection.createStatement();

            ResultSet resultSet = statement.executeQuery(FIND_ALL_EMPLOYEE_QUERY);

            employees.clear();

            while (resultSet.next()) {
                EmployeeModelInterface employee = new EmployeeModel();
                employee.setProperty(resultSet);
                if (employee.getPositionName().equals("Quản lý")) {
                    continue;
                }
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
    public void clearData() {
        employees.clear();
    }

    @Override
    public List<String> getAllPositionName() {
        List<String> ret = new ArrayList<>();
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_FIND_ALL_POSITION_NAME);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ret.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public List<String> getAllShiftName() {
        List<String> ret = new ArrayList<>();
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_FIND_ALL_SHIFT_NAME);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ret.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

}
