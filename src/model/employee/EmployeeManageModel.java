package model.employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.db.SQLServerConnection;

public class EmployeeManageModel implements EmployeeManageModelInterface {

    private static Connection dbConnection;
    
    static {
        dbConnection = SQLServerConnection.getConnection();
    }
    
    @Override
    public boolean isEmployeeEmailExisted(String employeeEmail) {
        try {
            Statement statement = dbConnection.createStatement();
            String query = "SELECT * FROM " + EmployeeModel.TABLE_NAME + 
                    " WHERE " + EmployeeModel.EMAIL_HEADER + " = '" + employeeEmail + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

}
