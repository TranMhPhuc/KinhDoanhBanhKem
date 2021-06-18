package model.user;

import javax.swing.JFrame;
import model.employee.EmployeeModelInterface;
import view.login.LoginFrame;
import view.profile.ProfilePanel;

public interface UserModelInterface {

    public enum UserType {
        MANAGER_USER,
        ACCOUNTANT_USER,
        CASHIER_USER,
        NO_PERMISSION,
    }

    public void setLoginFrame(LoginFrame loginFrame);

    public void setProfilePanelView(ProfilePanel profilePanel);

    public void setMainFrame(JFrame mainFrame);

    UserType getUserType();

    String getUserName();

    EmployeeModelInterface getImpl();

    void setImpl(EmployeeModelInterface impl);

    void updateProfile(String updatedEmail, String updatedPhoneNum);

    void updatePassword(String updatedPassword);

    void clearSession();

}
