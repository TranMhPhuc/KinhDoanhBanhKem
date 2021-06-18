package control.login;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.EmployeeModel;
import model.employee.EmployeeModelInterface;
import model.user.UserModelInterface;
import util.db.SQLServerConnection;
import util.mail.MailUtility;
import util.validator.EmailValidator;
import util.validator.EmailValidator.EmailValidateResult;
import view.login.LoginFrame;
import view.login.PasswordRecoveryDialog;

public class LoginController implements LoginControllerInterface {

    private static final String SP_LOGIN
            = "{call login(?, ?)}";

    private static final String SP_GET_PASSWORD_FROM_EMAIL
            = "{? = call get_password_from_email(?)}";

    private static Connection dbConnection;
    private volatile static LoginController uniqueInstance;

    private UserModelInterface userModel;
    private LoginFrame loginFrame;

    private PasswordRecoveryDialog passwordRecoveryDialog;

    static {
        dbConnection = SQLServerConnection.getConnection();
    }

    private LoginController(UserModelInterface model) {
        this.userModel = model;
        this.loginFrame = new LoginFrame(model, this);
        this.loginFrame.setVisible(true);
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
        EmailValidateResult emailValidateResult = EmailValidator.validate(email);

        switch (emailValidateResult) {
            case EMPTY: {
                this.loginFrame.showErrorMessage("Email must be not empty.");
                return;
            }
            case INVALLID: {
                this.loginFrame.showErrorMessage("Email is invallid.");
                return;
            }
        }

        if (password.isEmpty()) {
            this.loginFrame.showErrorMessage("Password must be not empty.");
            return;
        }

        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_LOGIN);

            callableStatement.setString(1, email);
            callableStatement.setString(2, password);
            
            ResultSet resultSet = callableStatement.executeQuery();
            
            if (!resultSet.next()) {
                loginFrame.showErrorMessage("Email or password is incorrect. Try again!");
                return;
            }

            EmployeeModelInterface impl = new EmployeeModel();
            impl.setProperty(resultSet);

            resultSet.close();
            callableStatement.close();

            this.userModel.setImpl(impl);

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void requestRecoverPassword() {
        this.passwordRecoveryDialog = new PasswordRecoveryDialog(this.loginFrame, true, this);
        this.passwordRecoveryDialog.setVisible(true);
    }

    @Override
    public void checkEmailToSendPassword() {
        if (passwordRecoveryDialog == null) {
            throw new NullPointerException();
        }

        String email = passwordRecoveryDialog.getEmailInput();

        EmailValidateResult emailValidateResult = EmailValidator.validate(email);
        
        switch (emailValidateResult) {
            case EMPTY: {
                this.loginFrame.showErrorMessage("Email must be required.");
                return;
            }
            case INVALLID: {
                this.loginFrame.showErrorMessage("Email is invallid.");
                return;
            }
        }

        String userPassword = "";

        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_PASSWORD_FROM_EMAIL);
            
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.setString(1, email);

            callableStatement.execute();
            
            userPassword = callableStatement.getString(1);

            if (userPassword == null) {
                loginFrame.showErrorMessage("Email is not available. Enter your email exactly!");
                return;
            }

            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MailUtility.sendPasswordRecover(email, userPassword);
        this.passwordRecoveryDialog.showInfoMessage("Your password is sent to email successfullly!");

        this.passwordRecoveryDialog.dispose();

        loginFrame.resetLoginInput();
        loginFrame.setEmailText(email);
    }

}
