package model.employee.shift;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import util.AppLog;

public class EmployeeShiftDataStorage implements EmployeeShiftDataStorageInterface {

    private static EmployeeShiftDataStorage uniqueInstance;

    private ArrayList<EmployeeShiftModelInterface> shifts;

    static {
        uniqueInstance = new EmployeeShiftDataStorage();
    }

    private EmployeeShiftDataStorage() {
        shifts = new ArrayList<>();
    }

    public static EmployeeShiftDataStorageInterface getInstance() {
        return uniqueInstance;
    }

    @Override
    public void updateFromDB(Connection connection) {
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + EmployeeShiftModel.TABLE_NAME;

            ResultSet resultSet = statement.executeQuery(query);

            shifts.clear();

            while (resultSet.next()) {
                EmployeeShiftModelInterface shift = new EmployeeShiftModel();
                shift.setProperty(resultSet);
                shifts.add(shift);
            }

            AppLog.getLogger().info("Update employee shift database: sucessfully, " + shifts.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update employee shift database: error.");
        }
    }

    @Override
    public EmployeeShiftModelInterface getShift(String shiftIDText) {
        for (EmployeeShiftModelInterface shift : shifts) {
            if (shift.getShiftIDText().equals(shiftIDText)) {
                return shift;
            }
        }
        throw new IllegalArgumentException("Shift id '" + shiftIDText + "' is not existed.");
    }

    @Override
    public Iterator<EmployeeShiftModelInterface> createIterator() {
        return shifts.iterator();
    }

    @Override
    public int getSize() {
        return shifts.size();
    }

    @Override
    public int getShiftIndex(String shiftIDText) {
        for (int i = 0; i < shifts.size(); i++) {
            if (shifts.get(i).getShiftIDText().equals(shiftIDText)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Shift id '" + shiftIDText + "' is not existed.");
    }

    @Override
    public EmployeeShiftModelInterface getShift(int shiftIndex) {
        if (shiftIndex < 0 || shiftIndex > this.shifts.size()) {
            throw new IllegalArgumentException("Shift index is out of bound.");
        }
        return shifts.get(shiftIndex);
    }
}
