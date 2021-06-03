package control.login;

public interface LoginControllerInterface {

    void requestLogin(String email, String password);
    
    void requestRecoverPassword();
    
    void sendPasswordToEmail(String email);
    
    void showPasswordText();
}
