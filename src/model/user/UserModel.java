package model.user;

import control.app.MainFrameController;
import javax.swing.JFrame;
import model.employee.EmployeeModelInterface;
import control.app.MainFrameControllerInterface;
import view.MessageShowing;
import view.login.LoginFrame;
import view.profile.ProfilePanel;

/**
 * UserModel is a singleton class. Only one user use application one time.
 */
public class UserModel implements UserModelInterface {

    private static UserModel uniqueInstance;

    private EmployeeModelInterface impl;

    private UserType userType;

    private LoginFrame loginFrame;
    private JFrame mainFrame;
    private ProfilePanel profilePanel;

    static {
        uniqueInstance = new UserModel();
    }

    private UserModel() {
        userType = UserType.NO_PERMISSION;
    }

    @Override
    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    @Override
    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void setImpl(EmployeeModelInterface impl) {
        if (impl == null) {
            throw new NullPointerException();
        }
        this.impl = impl;

        String positionName = impl.getPositionName();

        if (positionName.equals("Quản lý")) {
            userType = UserType.MANAGER_USER;
        } else if (positionName.equals("Bán hàng")) {
            userType = UserType.CASHIER_USER;
        } else if (positionName.equals("Kế toán")) {
            userType = UserType.ACCOUNTANT_USER;
        } else {
            userType = UserType.NO_PERMISSION;
        }

        if (userType != UserType.NO_PERMISSION) {
            MainFrameControllerInterface mainFrameController = new MainFrameController(this);
            loginFrame.dispose();
        } else {
            loginFrame.showErrorMessage("You don't have permission to login program.");
        }
    }

    public static UserModel getInstance() {
        return uniqueInstance;
    }

    @Override
    public UserType getUserType() {
        return this.userType;
    }

    @Override
    public String getUserName() {
        return impl.getName();
    }

    @Override
    public EmployeeModelInterface getImpl() {
        return this.impl;
    }

    @Override
    public void clearSession() {
        this.impl = null;
        this.mainFrame.dispose();
        this.loginFrame.setVisible(true);
    }

    @Override
    public void updateProfile(String updatedEmail, String updatedPhoneNum) {
        this.impl.updateProfile(updatedEmail, updatedPhoneNum);
        if (this.profilePanel == null) {
            throw new NullPointerException("Profile panel observer is null");
        }
        ((MessageShowing) mainFrame).showInfoMessage("Update profile successfully!");
        this.profilePanel.setInputEnable(false);
    }

    @Override
    public void setProfilePanelView(ProfilePanel profilePanel) {
        if (profilePanel == null) {
            throw new NullPointerException();
        }
        this.profilePanel = profilePanel;
        this.profilePanel.setUserModel(this);
    }

    @Override
    public void updatePassword(String updatedPassword) {
        this.impl.updatePassword(updatedPassword);
    }

}
