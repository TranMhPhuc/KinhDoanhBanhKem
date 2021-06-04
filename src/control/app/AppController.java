package control.app;

import model.user.UserModelInterface;
import view.main.AccountantUIDisplay;
import view.main.CashierUIDisplay;
import view.main.CustomUIDisplay;
import view.main.MainFrame;
import view.main.ManagerUIDisplay;

public class AppController implements AppControllerInterface {

    private volatile static AppController uniqueInstance;

    private MainFrame mainFrame;
    private UserModelInterface model;
    private CustomUIDisplay customUIDisplay;

    private AppController(UserModelInterface model) {
        this.model = model;
//        this.mainFrame = new MainFrame(this, model);

        getDisplayUIStrategy();
        this.customUIDisplay.customUIDisplay(this.mainFrame, this.model);
        this.mainFrame.setVisible(true);
    }

    private void getDisplayUIStrategy() {
        UserModelInterface.UserType userType = this.model.getUserType();
        switch (userType) {
            case MANAGER_USER: {
                this.customUIDisplay = new ManagerUIDisplay();
                break;
            }
            case CASHIER_USER: {
                this.customUIDisplay = new CashierUIDisplay();
                break;
            }
            case ACCOUNTANT_USER: {
                this.customUIDisplay = new AccountantUIDisplay();
                break;
            }
        }
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
    public void updateProfile() {
    }

    @Override
    public void updatePassword() {
    }

    @Override
    public void signOut() {
    }

}
