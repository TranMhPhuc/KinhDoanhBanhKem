package model.employee.position;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeePositionModel implements EmployeePositionModelInterface {

    public static final String TABLE_NAME = "ChucVu";
    public static final String ID_HEADER = "MaCV";
    public static final String NAME_HEADER = "TenCV";
    public static final String SALARY_FACTOR_HEADER = "HeSoLuong";

    private int id;
    private String name;
    private double salaryFactor = 1.0f;

    public EmployeePositionModel() {
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt(ID_HEADER);
            this.name = resultSet.getString(NAME_HEADER);
            this.salaryFactor = resultSet.getDouble(SALARY_FACTOR_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeePositionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getPositionIDText() {
        return String.valueOf(this.id);
    }

    @Override
    public String getPositionName() {
        return this.name;
    }

    @Override
    public void insertToDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteInDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateInDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(ID_HEADER)) {
                preparedStatement.setInt(index, this.id);
            } else if (header.equals(NAME_HEADER)) {
                preparedStatement.setString(index, this.name);
            } else if (header.equals(SALARY_FACTOR_HEADER)) {
                preparedStatement.setDouble(index, this.salaryFactor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeePositionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
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
        final EmployeePositionModel other = (EmployeePositionModel) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EmployeePositionModel{" + "id=" + id
                + ", name=" + name + ", salaryFactor=" + salaryFactor + '}';
    }
}
