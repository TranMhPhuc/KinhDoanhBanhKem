package control.login;

import java.sql.CallableStatement;
import java.sql.Connection;
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
import util.messages.Messages;
import util.password.Hash;
import util.validator.EmailValidator;
import util.validator.EmailValidator.EmailValidateResult;
import view.login.LoginFrame;
import view.login.PasswordRecoveryDialog;

public class LoginController implements LoginControllerInterface {

    private static final String SP_LOGIN
            = "{call login(?, ?)}";
    
    private static final String FC_GET_SALT_FROM_EMAIL 
            = "{? = call get_salt_from_email(?)}";
    
    private static final String SP_GET_EMPLOYEE_BY_MAIL
            = "{call get_employee_by_mail(?)}";

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
    public void requestLogin(String email, String plaintextPassword) {
        EmailValidateResult emailValidateResult = EmailValidator.validate(email);

        switch (emailValidateResult) {
            case EMPTY: {
                this.loginFrame.showErrorMessage("Please enter your email");
                return;
            }
            case INVALLID: {
                this.loginFrame.showErrorMessage("Email format is invalid");
                return;
            }
        }

        if (plaintextPassword.isEmpty()) {
            this.loginFrame.showErrorMessage("Please enter your password");
            return;
        }
        
        CallableStatement callableStatement = null;
        
        try {
            // lay chuoi salt tu email
            callableStatement = dbConnection.prepareCall(FC_GET_SALT_FROM_EMAIL);
            callableStatement.setString(2, email);
            callableStatement.registerOutParameter(1, Types.BINARY);
            callableStatement.execute();
            byte[] salt = callableStatement.getBytes(1);
            if (salt == null) {
                loginFrame.showErrorMessage("Email or password is incorrect, please try again!");
                return;
            }
            
            String hashedPassword = Hash.doHash(plaintextPassword, salt);
            
            callableStatement = dbConnection.prepareCall(SP_LOGIN);
            callableStatement.setString(1, email);
            callableStatement.setString(2, hashedPassword);
            
            ResultSet resultSet = callableStatement.executeQuery();
            
            if (!resultSet.next()) {
                loginFrame.showErrorMessage("Email or password is incorrect, please try again!");
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
                this.loginFrame.showErrorMessage("Please enter your email");
                return;
            }
            case INVALLID: {
                this.loginFrame.showErrorMessage("Email format is invalid");
                return;
            }
        }

        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_EMPLOYEE_BY_MAIL);
            callableStatement.setString(1, email);
            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next() == false) {
                loginFrame.showErrorMessage("This is email is not linked to the database, please try again");
                return;
            }
            EmployeeModelInterface impl = new EmployeeModel();
            impl.setProperty(resultSet);
            
            String randomPlaintextPass = Hash.generateRandomPassword(impl.getRandomPasswordLength());
            impl.updatePassword(randomPlaintextPass);
            
            MailUtility.sendPasswordRecover(email, randomPlaintextPass);
            
            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        this.passwordRecoveryDialog.showInfoMessage("Your password has been sent to email successfully");

        this.passwordRecoveryDialog.dispose();

        loginFrame.resetLoginInput();
        loginFrame.setEmailText(email);
    }

}
