package util.validator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.employee.EmployeeModel;
import util.db.SQLServerConnection;

public class EmailValidator {

    private static final Pattern EMAIL_VALID_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    
    private static Connection dbConnection;
    
    static {
        dbConnection = SQLServerConnection.getConnection();
    }

    private EmailValidator() {
    }

    public static boolean isEmailEmpty(String email) {
        return email.trim().isEmpty();
    }

    public static boolean isEmailVallid(String email) {
        Matcher matcher = EMAIL_VALID_PATTERN.matcher(email);
        return matcher.matches();
    }

    public static boolean isAvailable(String email) {
        try {
            Statement statement = dbConnection.createStatement();
            String emailFindingQuery = "SELECT * FROM " + EmployeeModel.TABLE_NAME + 
                    " WHERE " + EmployeeModel.EMAIL_HEADER + " = '" + email + "'";
            ResultSet resultSet = statement.executeQuery(emailFindingQuery);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmailValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
}
