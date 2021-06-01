package model.employee.position;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeePositionModel {

    public static final String TABLE_NAME = "ChucVu";
    public static final String ID_HEADER = "MaCV";
    public static final String NAME_HEADER = "TenCV";
    public static final String SALARY_FACTOR_HEADER = "HeSoLuong";

    private int positionID;
    private String positionName;
    private float salaryFactor = 1.0f;

    public EmployeePositionModel() {
    }

    public static EmployeePositionModel getInstance(ResultSet resultSet) {
        EmployeePositionModel ret = new EmployeePositionModel();

        try {
            ret.positionID = resultSet.getInt(ID_HEADER);
            ret.positionName = resultSet.getString(NAME_HEADER);
            ret.salaryFactor = resultSet.getInt(SALARY_FACTOR_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeePositionModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }
    
    public String getPositionIDText() {
        return String.valueOf(this.positionID);
    }

    @Override
    public String toString() {
        return "EmployeePositionModel{" + "positionID=" + positionID + 
                ", positionName=" + positionName + ", salaryFactor=" + salaryFactor + '}';
    }
    
}
