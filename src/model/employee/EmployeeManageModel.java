package model.employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.position.EmployeePositionDataStorage;
import model.employee.position.EmployeePositionDataStorageInterface;
import model.employee.position.EmployeePositionModelInterface;
import model.employee.shift.EmployeeShiftDataStorage;
import model.employee.shift.EmployeeShiftDataStorageInterface;
import model.employee.shift.EmployeeShiftModelInterface;
import util.db.SQLServerConnection;
import view.function.employee.InsertedEmployeeObserver;
import view.function.employee.ModifiedEmployeeObserver;

public class EmployeeManageModel implements EmployeeManageModelInterface {

    private static final String FIND_NEXT_IDENTITY_EMPLOYEE
            = "SELECT IDENT_CURRENT('" + EmployeeModel.TABLE_NAME + "') + 1";

    private volatile static EmployeeManageModel uniqueInstance;

    private static EmployeeDataStorageInterface employeeDataStorage;
    private static EmployeeShiftDataStorageInterface shiftDataStorage;
    private static EmployeePositionDataStorageInterface positionDataStorage;

    private static Connection dbConnection;

    static {
        dbConnection = SQLServerConnection.getConnection();
        employeeDataStorage = EmployeeDataStorage.getInstance();
        shiftDataStorage = EmployeeShiftDataStorage.getInstance();
        positionDataStorage = EmployeePositionDataStorage.getInstance();
    }

    private List<InsertedEmployeeObserver> insertedEmployeeObservers;
    private List<ModifiedEmployeeObserver> modifiedEmployeeObservers;

    private EmployeeManageModel() {
        insertedEmployeeObservers = new ArrayList<>();
        modifiedEmployeeObservers = new ArrayList<>();
    }

    public static EmployeeManageModelInterface getInstance() {
        if (uniqueInstance == null) {
            synchronized (EmployeeManageModel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new EmployeeManageModel();
                }
            }
        }
        return uniqueInstance;
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
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_NEXT_IDENTITY_EMPLOYEE);
            if (resultSet.next()) {
                nextIdentity = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return String.valueOf(nextIdentity);
    }

    @Override
    public void exportEmployeeData() {
        // XXX
    }

    @Override
    public void importEmployeeData() {
        // XXX
    }

    @Override
    public void addNewEmployee(EmployeeModelInterface newEmployee) {
        employeeDataStorage.add(newEmployee);
        notifyInsertedEmployeeObserver(newEmployee);
    }

    @Override
    public void updateEmployee(EmployeeModelInterface updatedEmployee) {
        employeeDataStorage.update(updatedEmployee);
        notifyModifiedEmployeeObserver(updatedEmployee);
    }

    @Override
    public EmployeeModelInterface getEmployeeByID(String employeeIDText) {
        return employeeDataStorage.getEmployeeByID(employeeIDText);
    }

    @Override
    public Iterator<EmployeeModelInterface> getAllEmployeeData() {
        return employeeDataStorage.createIterator();
    }

    @Override
    public Iterator<EmployeeModelInterface> getEmployeeSearchByName(String searchText) {
        return employeeDataStorage.getEmployeeSearchByName(searchText);
    }

    @Override
    public int getEmployeePositionIndex(EmployeeModelInterface employee) {
        return positionDataStorage.getPositionIndex(employee.getPosition());
    }

    @Override
    public Iterator<EmployeePositionModelInterface> getAllPositionData() {
        return positionDataStorage.createIterator();
    }

    @Override
    public Iterator<EmployeeShiftModelInterface> getAllShiftData() {
        return shiftDataStorage.createIterator();
    }

    @Override
    public int getShiftIndex(EmployeeShiftModelInterface shift) {
        return shiftDataStorage.getShiftIndex(shift.getShiftIDText());
    }

}
