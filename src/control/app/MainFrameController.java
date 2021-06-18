package control.app;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.setting.AppSetting;
import model.user.UserModelInterface;
import util.db.SQLServerConnection;
import util.validator.EmailValidator;
import util.validator.PhoneValidator;
import view.MessageShowing;
import view.profile.PasswordChangeDialog;
import view.main.accountant.AccountantMainFrame;
import view.main.cashier.CashierMainFrame;
import view.main.manager.ManagerMainFrame;
import view.profile.ProfilePanel;

public class MainFrameController implements MainFrameControllerInterface {

    private JFrame mainFrame;
    private ProfilePanel profilePanel;
    private UserModelInterface userModel;

    private PasswordChangeDialog passwordChangeDialog;

    public MainFrameController(UserModelInterface userModel) {
        this.userModel = userModel;
        switch (userModel.getUserType()) {
            case MANAGER_USER: {
                mainFrame = new ManagerMainFrame(this, userModel);
                break;
            }
            case ACCOUNTANT_USER: {
                mainFrame = new AccountantMainFrame(this, userModel);
                break;
            }
            case CASHIER_USER: {
                mainFrame = new CashierMainFrame(this, userModel);
                break;
            }
        }
        mainFrame.setVisible(true);
    }

    @Override
    public void setProfilePanelView(ProfilePanel profilePanel) {
        if (profilePanel == null) {
            throw new NullPointerException();
        }
        this.profilePanel = profilePanel;
        this.profilePanel.setMainFrameController(this);

    }

    @Override
    public void setMainFrameView(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void requestUpdateProfile() {
        if (profilePanel == null) {
            throw new NullPointerException();
        }
        String updatedEmail = profilePanel.getUserEmailInput();

        EmailValidator.EmailValidateResult emailValidateResult = EmailValidator.validate(updatedEmail);

        switch (emailValidateResult) {
            case EMPTY: {
                ((MessageShowing) mainFrame).showErrorMessage("Updating profile requires email not empty.");
                return;
            }
            case INVALLID: {
                ((MessageShowing) mainFrame).showErrorMessage("Updating profile requires email valid.");
                return;
            }
        }

        String updatedPhoneNum = profilePanel.getUserPhoneNumInput();

        PhoneValidator.PhoneValidateResult phoneValidateResult = PhoneValidator.validate(updatedPhoneNum);

        switch (phoneValidateResult) {
            case EMPTY: {
                ((MessageShowing) mainFrame).showErrorMessage("Updating profile requires phone num not empty.");
                return;
            }
            case ERROR_FORMAT: {
                ((MessageShowing) mainFrame).showErrorMessage("Updating profile requires phone num is in number format.");
                return;
            }
            case INVALLID: {
                ((MessageShowing) mainFrame).showErrorMessage("Updating profile requires phone num has exact 10 digits.");
                return;
            }
        }

        userModel.updateProfile(updatedEmail, updatedPhoneNum);
    }

    @Override
    public void requestChangePassword() {
        if (passwordChangeDialog == null) {
            passwordChangeDialog = new PasswordChangeDialog(mainFrame, true, this);
        }
        passwordChangeDialog.setVisible(true);
    }

    @Override
    public void checkPasswordUpdateInput() {
        if (passwordChangeDialog == null) {
            throw new NullPointerException();
        }

        String oldPasswordInput = passwordChangeDialog.getOldPasswordInput();

        if (oldPasswordInput.isEmpty()) {
            ((MessageShowing) mainFrame).showErrorMessage("Old password is required.");
            return;
        }

        String userPassword = userModel.getImpl().getPassword();

        if (!oldPasswordInput.equals(userPassword)) {
            ((MessageShowing) mainFrame).showErrorMessage("Old password is incorrect.");
            return;
        }

        String newPasswordInput = passwordChangeDialog.getNewPasswordInput();

        if (newPasswordInput.isEmpty()) {
            ((MessageShowing) mainFrame).showErrorMessage("New password is required.");
            return;
        }

        String verifyPasswordInput = passwordChangeDialog.getVerifyPasswordInput();

        if (verifyPasswordInput.isEmpty()) {
            ((MessageShowing) mainFrame).showErrorMessage("Verifying password is required.");
            return;
        }

        if (!newPasswordInput.equals(verifyPasswordInput)) {
            ((MessageShowing) mainFrame).showErrorMessage("Verifying password is incorrect.");
            return;
        }

        userModel.updatePassword(newPasswordInput);

        ((MessageShowing) mainFrame).showInfoMessage("Change password successfully.");

        passwordChangeDialog.dispose();
    }

    @Override
    public void requestSignOut() {
        if (!AppSetting.getInstance().getConfirmSignOutFlag()) {
            this.userModel.clearSession();
            return;
        }

        int ret = JOptionPane.showConfirmDialog(this.mainFrame, "Do you want to sign out?",
                "Confirm to sign out dialog", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ret == JOptionPane.YES_OPTION) {
            this.userModel.clearSession();
        }
    }

    @Override
    public void requestCloseProgram() {
        if (!AppSetting.getInstance().getConfirmExitFlag()) {
            try {
                AppSetting.getInstance().writeProperty();
                SQLServerConnection.getConnection().close();
                System.exit(0);
            } catch (SQLException ex) {
                Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }

        // Confirm exit, if exit then close database connection
        int ret = JOptionPane.showConfirmDialog(this.mainFrame, "Do you want to exit?",
                "Confirm to close program dialog", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ret == JOptionPane.YES_OPTION) {
            try {
                AppSetting.getInstance().writeProperty();
                SQLServerConnection.getConnection().close();
                System.exit(0);
            } catch (SQLException ex) {
                Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean canCloseProfilePanel() {
        boolean isProfileUpdating = profilePanel.isProfileEditing();
        if (isProfileUpdating) {
            int ret = JOptionPane.showConfirmDialog(mainFrame,
                    "Cancel editing profile?",
                    "Cancel editing profile confirm dialog",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (ret == JOptionPane.YES_OPTION) {
                this.profilePanel.resetProfileEditing();
                this.profilePanel.setInputEnable(false);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

}
