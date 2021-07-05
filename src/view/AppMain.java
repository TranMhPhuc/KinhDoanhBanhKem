package view;

import control.login.LoginController;
import control.login.LoginControllerInterface;
import model.user.UserModel;
import model.user.UserModelInterface;

public class AppMain {

    public static void main(String[] args) {
        UserModelInterface user = UserModel.getInstance();
        LoginControllerInterface appControllerInterface = LoginController.getInstance(user);
    }
}
