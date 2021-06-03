package control.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.EmployeeDataStorage;
import model.employee.EmployeeModel;
import model.user.UserModelInterface;
import util.db.DataModelUpdateManager;
import util.db.SQLServerConnection;
import util.validator.EmailValidator;
import view.login.LoginFrame;

public class AppController implements AppControllerInterface {

    private static Connection dbConnection;
    private volatile static AppController uniqueInstance;

    private UserModelInterface model;
    private LoginFrame loginView;

    static {
        dbConnection = SQLServerConnection.getConnection();
    }

    private AppController(UserModelInterface model) {
        this.model = model;
        this.loginView = new LoginFrame(model, this);
        this.loginView.setVisible(true);
    }

    public static AppController getInstance(UserModelInterface model) {
        if (uniqueInstance == null) {
            synchronized (AppController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new AppController(model);
                }
            }
        }
        return uniqueInstance;
    }

    @Override
    public void requestLogin(String email, String password) {
        if (EmailValidator.isEmailEmpty(email) || !EmailValidator.isEmailVallid(email)) {
            this.loginView.showErrorMessage("Email is invallid.");
            return;
        }

        try {
            Statement statement = dbConnection.createStatement();

            String passwordFindingQuery = "SELECT * FROM " + EmployeeModel.TABLE_NAME
                    + " WHERE " + EmployeeModel.EMAIL_HEADER + " = '" + email + "'";

            ResultSet resultSet = statement.executeQuery(passwordFindingQuery);

            if (!resultSet.next()) {
                this.loginView.showErrorMessage("Email or password is invallid.");
                return;
            }

            DataModelUpdateManager.getInstance().updateFromDB();
            
            this.model.updateUser(EmployeeDataStorage.getInstance().getEmployee(resultSet.getString(EmployeeModel.ID_HEADER)));

        } catch (SQLException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
