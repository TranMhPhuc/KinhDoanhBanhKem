package control.login;

import control.app.AppController;
import control.app.AppControllerInterface;
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
import view.dialog.PasswordRecoveryDialog;

public class LoginController implements LoginControllerInterface {

    private static Connection dbConnection;
    private volatile static LoginController uniqueInstance;

    private UserModelInterface model;
    private LoginFrame loginView;
    private PasswordRecoveryDialog passwordRecoveryDialog;

    static {
        dbConnection = SQLServerConnection.getConnection();
    }

    private LoginController(UserModelInterface model) {
        this.model = model;
        this.loginView = new LoginFrame(model, this);
        this.loginView.setVisible(true);
    }

    public static LoginController getInstance(UserModelInterface model) {
        if (uniqueInstance == null) {
            synchronized (LoginController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new LoginController(model);
                }
            }
        }
        return uniqueInstance;
    }

    @Override
    public void requestLogin(String email, String password) {
        if (EmailValidator.isEmailEmpty(email)) {
            this.loginView.showErrorMessage("Email must be required.");
            return;
        }

        if (!EmailValidator.isEmailVallid(email)) {
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

            // Clone neccessary data from database to memory
            DataModelUpdateManager.getInstance().updateFromDB();

            this.model.updateUser(EmployeeDataStorage.getInstance().getEmployee(
                    resultSet.getString(EmployeeModel.ID_HEADER)));

            this.loginView.dispose();
            
            AppControllerInterface appControllerInterface = AppController.getInstance(model);
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void requestRecoverPassword() {
        this.passwordRecoveryDialog = new PasswordRecoveryDialog(this.loginView, true, this);
        this.passwordRecoveryDialog.setVisible(true);
    }

    @Override
    public void sendPasswordToEmail(String email) {
        if (EmailValidator.isEmailEmpty(email)) {
            this.passwordRecoveryDialog.showErrorMessage("Email must be required.");
            return;
        }

        if (!EmailValidator.isEmailVallid(email)) {
            this.passwordRecoveryDialog.showErrorMessage("Email is invallid.");
            return;
        }

        try {
            Statement statement = dbConnection.createStatement();
            String emailFindingQuery = "SELECT " + EmployeeModel.PASSWORD_HEADER
                    + " FROM " + EmployeeModel.TABLE_NAME
                    + " WHERE " + EmployeeModel.EMAIL_HEADER + " = '" + email + "'";
            ResultSet resultSet = statement.executeQuery(emailFindingQuery);
            if (resultSet.next()) {
                String password = resultSet.getString(1);

                // TASK: send this password to email using Mail API
                // XXX
                this.passwordRecoveryDialog.showInfoMessage("Your password is send to email successfullly!");
                this.passwordRecoveryDialog.dispose();
                this.loginView.setEmailText(email);
            } else {
                this.passwordRecoveryDialog.showErrorMessage("Email is not available.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void showPasswordText() {
    }

}
