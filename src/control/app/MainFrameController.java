package control.app;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.setting.AppSetting;
import model.user.UserModelInterface;
import util.db.SQLServerConnection;
import util.messages.Messages;
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

    private PasswordChangeDialog dialogPasswordChange;
    
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
               ((MessageShowing) mainFrame).showErrorMessage(Messages.getInstance().PROFILE_EMAIL_EMPTY);
                return;
            }
            case INVALLID: {
               ((MessageShowing) mainFrame).showErrorMessage(Messages.getInstance().PROFILE_EMAIL_INVALID);
                return;
            }
        }

        String updatedPhoneNum = profilePanel.getUserPhoneNumInput();

        PhoneValidator.PhoneValidateResult phoneValidateResult = PhoneValidator.validate(updatedPhoneNum);

        switch (phoneValidateResult) {
            case EMPTY: {
                ((MessageShowing) mainFrame).showErrorMessage(Messages.getInstance().PROFILE_PHONE_NUMBER_EMPTY);
                return;
            }
            case ERROR_FORMAT: {
                ((MessageShowing) mainFrame).showErrorMessage(Messages.getInstance().PROFILE_PHONE_NUMBER_FORMAT);
                return;
            }
            case INVALLID: {
                ((MessageShowing) mainFrame).showErrorMessage(Messages.getInstance().PROFILE_PHONE_NUMBER_DIGITS);
                return;
            }
        }

        userModel.updateProfile(updatedEmail, updatedPhoneNum);
    }

    @Override
    public void requestChangePassword() {
        if (dialogPasswordChange == null) {
            dialogPasswordChange = new PasswordChangeDialog(mainFrame, true, this);
            AppSetting.getInstance().registerObserver(dialogPasswordChange);
        }
        dialogPasswordChange.setVisible(true);
    }

    @Override
    public void checkPasswordUpdateInput() {
        if (dialogPasswordChange == null) {
            throw new NullPointerException();
        }

        String oldPasswordInput = dialogPasswordChange.getOldPasswordInput();

        if (oldPasswordInput.isEmpty()) {
            ((MessageShowing) mainFrame).showErrorMessage(Messages.getInstance().PROFILE_OLD_PASSWORD_EMPTY);
            return;
        }

        if (!userModel.getImpl().isCorrectPassword(oldPasswordInput)) {
            ((MessageShowing) mainFrame).showErrorMessage(Messages.getInstance().PROFILE_OLD_PASSWORD_INCORRECT);
            return;
        }

        String newPasswordInput = dialogPasswordChange.getNewPasswordInput();

        if (newPasswordInput.isEmpty()) {
            ((MessageShowing) mainFrame).showErrorMessage(Messages.getInstance().PROFILE_NEW_PASSWORD_EMPTY);
            return;
        }

        String verifyPasswordInput = dialogPasswordChange.getVerifyPasswordInput();

        if (verifyPasswordInput.isEmpty()) {
            ((MessageShowing) mainFrame).showErrorMessage(Messages.getInstance().PROFILE_NEW_PASSWORD_VERIFICATION_EMPTY);
            return;
        }

        if (!newPasswordInput.equals(verifyPasswordInput)) {
            ((MessageShowing) mainFrame).showErrorMessage(Messages.getInstance().PROFILE_NEW_PASSWORD_VERIFICATION_INCORRECT);
            return;
        }

        userModel.updatePassword(newPasswordInput);

        ((MessageShowing) mainFrame).showInfoMessage(Messages.getInstance().PROFILE_CHANGE_PASSWORD_SUCCESSFULLY);
        
        dialogPasswordChange.dispose();
    }

    @Override
    public void requestSignOut() {
        if (!AppSetting.getInstance().getConfirmSignOutFlag()) {
            this.userModel.clearSession();
            return;
        }

        int ret = JOptionPane.showConfirmDialog(this.mainFrame, Messages.getInstance().PROFILE_SIGN_OUT_CONFIRMATION,
                "BakeryMS", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/question.png")));
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
        int ret = JOptionPane.showConfirmDialog(this.mainFrame, Messages.getInstance().PROFILE_EXIT_CONFIRMATION,
                "BakeryMS", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/warning.png")));
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
                    Messages.getInstance().PROFILE_CANCEL_EDITING,
                    "BakeryMS",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/img/warning.png")));
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
