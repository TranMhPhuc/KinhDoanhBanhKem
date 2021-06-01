package model.shift;

import util.db.SQLServerConnect;
import util.AppLog;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkShift {

    private static ArrayList<WorkShift> workShifts;

    private int shiftCode;
    private String shiftName;
    private Time timeStart;
    private Time timeEnd;

    static {
        workShifts = new ArrayList<>();
        updateFromDB();
    }

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
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM CaLamViec";

            ResultSet resultSet = statement.executeQuery(query);

            workShifts.clear();

            while (resultSet.next()) {
                workShifts.add(new WorkShift(resultSet.getInt("MaCaLamViec"),
                        resultSet.getString("TenCaLamViec"),
                        resultSet.getTime("GioBatDau"), 
                        resultSet.getTime("GioKetThuc")));
            }

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update work shift from DB is not successfully.");
        }
    }

    public static String getShiftName(int shiftCode) {
        String shiftName = "";
        try {
            shiftName = workShifts.get(shiftCode).getShiftName();
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Shift code is not valid!");
        }
        return shiftName;
    }

    public static void getShiftData(String[] view) {
        view = new String[workShifts.size()];
        for (int i = 0; i < workShifts.size(); i++) {
            view[i] = workShifts.get(i).getShiftName();
        }
    }

    private String getShiftName() {
        return shiftName;
    }

    @Override
    public String toString() {
        return "WorkShift{" + "shiftCode=" + shiftCode + ", shiftName=" + shiftName + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + '}';
    }

    public static void main(String[] args) {
        if (workShifts.size() == 0) {
            throw new AssertionError("Shift set is empty!");
        }

        for (int i = 0; i < workShifts.size(); i++) {
            System.out.println(workShifts.get(i));
        }
    }

}//end WorkShift
