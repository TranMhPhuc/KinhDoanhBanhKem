package model.employee.shift;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeShiftModel implements EmployeeShiftModelInterface {

    public static final String TABLE_NAME = "CaLamViec";
    public static final String ID_HEADER = "MaCa";
    public static final String NAME_HEADER = "TenCa";
    public static final String STARTTIME_HEADER = "GioBatDau";
    public static final String ENDTIME_HEADER = "GioKetThuc";

    private int id;
    private String name;
    private Time startTime;
    private Time endTime;

    public EmployeeShiftModel() {
    }

    @Override
    public String getShiftIDText() {
        return String.valueOf(this.id);
    }

    @Override
    public String getShiftName() {
        return this.name;
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt(ID_HEADER);
            this.name = resultSet.getString(NAME_HEADER);
            this.startTime = resultSet.getTime(STARTTIME_HEADER);
            this.endTime = resultSet.getTime(ENDTIME_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeShiftModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            } else if (header.equals(STARTTIME_HEADER)) {
                preparedStatement.setTime(index, this.startTime);
            } else if (header.equals(ENDTIME_HEADER)) {
                preparedStatement.setTime(index, this.endTime);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeShiftModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final EmployeeShiftModel other = (EmployeeShiftModel) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EmployeeShiftModel{" + "id=" + id + ", name="
                + name + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }
}
