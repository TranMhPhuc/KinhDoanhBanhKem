package model.employee.position;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;

public class EmployeePositionDataStorage implements DatabaseUpdate {
    
    private static EmployeePositionDataStorage uniqueInstance;
    
    private static ArrayList<EmployeePositionModel> positions;
    
    static {
        uniqueInstance = new EmployeePositionDataStorage();
        positions = new ArrayList<>();
    }
    
    private EmployeePositionDataStorage() {
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
                positions.add(EmployeePositionModel.getInstance(resultSet));
            }

        } catch (SQLException ex) {
        }
    }
    
    public EmployeePositionModel getPosition(String positionIDText) {
        for (EmployeePositionModel element: positions) {
            if (element.getPositionIDText().equals(positionIDText)) {
                return element;
            }
        }
        return null;
    }

}
