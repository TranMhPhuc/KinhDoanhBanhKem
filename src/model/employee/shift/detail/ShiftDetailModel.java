package model.employee.shift.detail;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.EmployeeModel;
import model.employee.EmployeeModelInterface;

public class ShiftDetailModel implements ShiftDetailModelInterface {

    public static final String TABLE_NAME = "PhanCong";
    public static final String EMPLOYEE_ID_HEADER = "MaNV";
    public static final String SHIFT_NAME_HEADER = "TenCa";

    private static final String SP_INSERT = "{call insert_PhanCong(?, ?)}";

    private static final String SP_DELETE = "{call delete_PhanCong(?, ?)}";

    private EmployeeModelInterface employee;
    private String shiftName;

    public ShiftDetailModel() {
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.shiftName = resultSet.getString(SHIFT_NAME_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(ShiftDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_INSERT);

            this.employee.setKeyArg(1, EmployeeModel.ID_HEADER, callableStatement);
            callableStatement.setString(2, this.shiftName);

            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShiftDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_DELETE);

            this.employee.setKeyArg(1, EmployeeModel.ID_HEADER, callableStatement);
            callableStatement.setString(2, this.shiftName);

            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShiftDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateInDatabase() {
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
    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    @Override
    public String getShiftName() {
        return shiftName;
    }

    @Override
    public EmployeeModelInterface getEmployee() {
        return employee;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.employee);
        hash = 29 * hash + Objects.hashCode(this.shiftName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ShiftDetailModel other = (ShiftDetailModel) obj;
        if (!Objects.equals(this.shiftName, other.shiftName)) {
            return false;
        }
        if (!Objects.equals(this.employee, other.employee)) {
            return false;
        }
        return true;
    }

}
