package model.employee.position;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;
import util.AppLog;

public class EmployeePositionDataStorage implements DatabaseUpdate {
    
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
                positions.add(EmployeePositionModel.getInstance(resultSet));
            }
            
            AppLog.getLogger().info("Update employee position database: sucessfully, " + positions.size() + " rows inserted.");
        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update employee position database: error.");
        }
    }
    
    public EmployeePositionModelInterface getPosition(String positionIDText) {
        for (EmployeePositionModelInterface element: positions) {
            if (element.getPositionIDText().equals(positionIDText)) {
                return element;
            }
        }
        return null;
    }

}
