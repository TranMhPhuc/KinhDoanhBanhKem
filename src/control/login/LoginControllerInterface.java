package control.login;

public interface LoginControllerInterface {

    void requestLogin(String email, String password);
    
    void requestRecoverPassword();
    
    void checkEmailToSendPassword();
    
}
