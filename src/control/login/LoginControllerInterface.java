package control.login;

public interface LoginControllerInterface {

    void requestLogin(String email, String plaintextPassword);
    
    void requestRecoverPassword();
    
    void checkEmailToSendPassword();
    
}
