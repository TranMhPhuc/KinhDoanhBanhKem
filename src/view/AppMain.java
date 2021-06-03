package view;

import control.login.LoginController;
import model.user.UserModel;
import model.user.UserModelInterface;
import control.login.LoginControllerInterface;

public class AppMain {
    public static void main(String[] args) {
        UserModelInterface user = UserModel.getInstance();
        LoginControllerInterface appControllerInterface = LoginController.getInstance(user);
    }
}
