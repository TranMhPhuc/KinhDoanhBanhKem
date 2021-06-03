package view;

import control.app.AppController;
import model.user.UserModel;
import model.user.UserModelInterface;
import control.app.AppControllerInterface;

public class AppMain {
    public static void main(String[] args) {
        UserModelInterface user = UserModel.getInstance();
        AppControllerInterface appControllerInterface = AppController.getInstance(user);
    }
}
