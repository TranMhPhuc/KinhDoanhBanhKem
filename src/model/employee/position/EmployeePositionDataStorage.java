package model.employee.position;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import util.AppLog;

public class EmployeePositionDataStorage implements EmployeePositionDataStorageInterface {

    private static EmployeePositionDataStorage uniqueInstance;

    private ArrayList<EmployeePositionModelInterface> positions;

    static {
        uniqueInstance = new EmployeePositionDataStorage();
    }

    private EmployeePositionDataStorage() {
        positions = new ArrayList<>();
    }

    public static EmployeePositionDataStorage getInstance() {
        return uniqueInstance;
    }

    @Override
    public void updateFromDB(Connection connection) {
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + EmployeePositionModel.TABLE_NAME;

            ResultSet resultSet = statement.executeQuery(query);

            positions.clear();

            while (resultSet.next()) {
                EmployeePositionModelInterface position = new EmployeePositionModel();
                position.setProperty(resultSet);
                positions.add(position);
            }

            AppLog.getLogger().info("Update employee position database: sucessfully, " + positions.size() + " rows inserted.");
        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update employee position database: error.");
        }
    }

    @Override
    public EmployeePositionModelInterface getPositionByID(String positionIDText) {
        for (EmployeePositionModelInterface position : positions) {
            if (position.getPositionIDText().equals(positionIDText)) {
                return position;
            }
        }
        throw new IllegalArgumentException("Position id '" + positionIDText + "' is not existed.");
    }

    @Override
    public EmployeePositionModelInterface getPositionByName(String positionName) {
        for (EmployeePositionModelInterface position : positions) {
            if (position.getName().equals(positionName)) {
                return position;
            }
        }
        throw new IllegalArgumentException("Position name '" + positionName + "' is not existed.");
    }

    @Override
    public int getPositionIndex(EmployeePositionModelInterface position) {
        if (position == null) {
            throw new NullPointerException();
        }
        return this.positions.indexOf(position);
    }

    @Override
    public Iterator<EmployeePositionModelInterface> createIterator() {
        return this.positions.iterator();
    }

}
