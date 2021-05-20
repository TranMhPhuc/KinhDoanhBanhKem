package model;

import control.dbconnect.SQLServerConnect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkShift {

    private static WorkShift MORNING_SHIFT = new WorkShift(1, "Ca sáng", null, null);
    private static WorkShift AFTERNOON_SHIFT = new WorkShift(2, "Ca chiều", null, null);
    private static WorkShift EVENING_SHIFT = new WorkShift(3, "Ca tối", null, null);
    private static WorkShift NIGHT_SHIFT = new WorkShift(4, "Ca đêm", null, null);

    private int shiftCode;
    private String shiftName;
    private Time timeStart;
    private Time timeEnd;

    static {
        updateFromDB();
    }

    // Avoid to create instance
    private WorkShift() {
    }

    private WorkShift(int shiftCode, String shiftName, Time timeStart, Time timeEnd) {
        this.shiftCode = shiftCode;
        this.shiftName = shiftName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public static void updateFromDB() {
        Connection connection = SQLServerConnect.getConnection();

        try {
            Statement st = connection.createStatement();

            String query = "SELECT TenCaLamViec, GioBatDau, GioKetThuc FROM CaLamViec";

            ResultSet rs = st.executeQuery(query);

            rs.next();
            updateShift(MORNING_SHIFT, rs.getString("TenCaLamViec"), rs.getTime("GioBatDau"), rs.getTime("GioKetThuc"));

            rs.next();
            updateShift(AFTERNOON_SHIFT, rs.getString("TenCaLamViec"), rs.getTime("GioBatDau"), rs.getTime("GioKetThuc"));

            rs.next();
            updateShift(EVENING_SHIFT, rs.getString("TenCaLamViec"), rs.getTime("GioBatDau"), rs.getTime("GioKetThuc"));

            rs.next();
            updateShift(NIGHT_SHIFT, rs.getString("TenCaLamViec"), rs.getTime("GioBatDau"), rs.getTime("GioKetThuc"));

        } catch (SQLException ex) {
            Logger.getLogger(WorkShift.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void updateShift(WorkShift shift, String shiftName, Time timeStart, Time timeEnd) {
        shift.setShiftName(shiftName);
        shift.setTimeStart(timeStart);
        shift.setTimeEnd(timeEnd);
    }

    public static WorkShift getMorningShift() {
        return MORNING_SHIFT;
    }

    public static WorkShift getAfternoonShift() {
        return AFTERNOON_SHIFT;
    }

    public static WorkShift getEveningShift() {
        return EVENING_SHIFT;
    }

    public static WorkShift getNightShift() {
        return NIGHT_SHIFT;
    }

    public void setShiftCode(int shiftCode) {
        this.shiftCode = shiftCode;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String toString() {
        return "WorkShift{" + "shiftCode=" + shiftCode + ", shiftName=" + shiftName + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + '}';
    }

    public static void main(String[] args) {
        System.out.println(MORNING_SHIFT);
        System.out.println(AFTERNOON_SHIFT);
        System.out.println(EVENING_SHIFT);
        System.out.println(NIGHT_SHIFT);
    }

}//end WorkShift
