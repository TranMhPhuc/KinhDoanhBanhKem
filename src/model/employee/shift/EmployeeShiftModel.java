package model.employee.shift;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeShiftModel {

    public static final String TABLE_NAME = "CaLamViec";
    public static final String ID_HEADER = "MaCa";
    public static final String NAME_HEADER = "TenCa";
    public static final String STARTTIME_HEADER = "GioBatDau";
    public static final String ENDTIME_HEADER = "GioKetThuc";

    private int shiftID;
    private String shiftName;
    private Time startTime;
    private Time endTime;

    public EmployeeShiftModel() {
    }

    public EmployeeShiftModel(int shiftCode, String shiftName, Time timeStart, Time timeEnd) {
        this.shiftID = shiftCode;
        this.shiftName = shiftName;
        this.startTime = timeStart;
        this.endTime = timeEnd;
    }

    public static EmployeeShiftModel getInstance(ResultSet resultSet) {
        EmployeeShiftModel ret = new EmployeeShiftModel();

        try {
            ret.shiftID = resultSet.getInt(ID_HEADER);
            ret.shiftName = resultSet.getString(NAME_HEADER);
            ret.startTime = resultSet.getTime(STARTTIME_HEADER);
            ret.endTime = resultSet.getTime(ENDTIME_HEADER);

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeShiftModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ret;
    }

    public String getShiftIDText() {
        return String.valueOf(this.shiftID);
    }
    
    @Override
    public String toString() {
        return "EmployeeShiftModel{" + "shiftID=" + shiftID + ", shiftName=" 
                + shiftName + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }
    
}
