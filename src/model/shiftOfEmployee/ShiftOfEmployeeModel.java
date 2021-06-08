package model.shiftOfEmployee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.EmployeeDataStorage;
import model.employee.EmployeeDataStorageInterface;
import model.employee.EmployeeModel;
import model.employee.EmployeeModelInterface;
import model.employee.shift.EmployeeShiftDataStorage;
import model.employee.shift.EmployeeShiftDataStorageInterface;
import model.employee.shift.EmployeeShiftModel;
import model.employee.shift.EmployeeShiftModelInterface;

public class ShiftOfEmployeeModel implements ShiftOfEmployeeModelInterface {

    public static final String TABLE_NAME = "PhanCong";
    public static final String EMPLOYEE_ID_HEADER = "MaNV";
    public static final String SHIFT_ID_HEADER = "MaCa";

    private static EmployeeDataStorageInterface employeeDataStorage;
    private static EmployeeShiftDataStorageInterface employeeShiftDataStorage;

    private static final String INSERT_QUERY_PROTOTYPE
            = "INSERT " + TABLE_NAME + " ("
            + EMPLOYEE_ID_HEADER + ", " + SHIFT_ID_HEADER + ")"
            + " VALUES (?, ?)";

    private static final String DELETE_QUERY_PROTOTYPE
            = "DELETE FROM " + TABLE_NAME
            + " WHERE " + EMPLOYEE_ID_HEADER + " = ? AND "
            + SHIFT_ID_HEADER + " = ?";

    private EmployeeModelInterface employee;
    private EmployeeShiftModelInterface shift;

    static {
        employeeDataStorage = EmployeeDataStorage.getInstance();
        employeeShiftDataStorage = EmployeeShiftDataStorage.getInstance();
    }

    public ShiftOfEmployeeModel() {
    }
    
    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.employee = employeeDataStorage.getEmployeeByID(resultSet
                    .getString(EmployeeModel.ID_HEADER));
            this.shift = employeeShiftDataStorage.getShift(resultSet
                    .getString(EmployeeShiftModel.ID_HEADER));
        } catch (SQLException ex) {
            Logger.getLogger(ShiftOfEmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void insertToDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(INSERT_QUERY_PROTOTYPE);
            this.employee.setKeyArg(1, EmployeeModel.ID_HEADER, preparedStatement);
            this.shift.setKeyArg(2, EmployeeShiftModel.ID_HEADER, preparedStatement);
        } catch (SQLException ex) {
            Logger.getLogger(ShiftOfEmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(DELETE_QUERY_PROTOTYPE);
            this.employee.setKeyArg(1, EmployeeModel.ID_HEADER, preparedStatement);
            this.shift.setKeyArg(2, EmployeeShiftModel.ID_HEADER, preparedStatement);
        } catch (SQLException ex) {
            Logger.getLogger(ShiftOfEmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateInDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setEmployee(EmployeeModelInterface employee) {
        this.employee = employee;
    }

    @Override
    public void setShift(EmployeeShiftModelInterface shift) {
        this.shift = shift;
    }

}
