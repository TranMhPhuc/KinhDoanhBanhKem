package model.user;

import model.employee.EmployeeModelInterface;
import view.login.LoginUpdateObserver;

public interface UserModelInterface {

    enum UserType {
        MANAGER_USER,
        ACCOUNTANT_USER,
        CASHIER_USER,
        NO_PERMISSION,
    }
    
    UserType getUserType();
    
    String getUserName();
    
    void updateUser(EmployeeModelInterface impl);
    
    void registerObserver(LoginUpdateObserver menuObserver);
    
    void removeObserver(LoginUpdateObserver menuObserver);
    
}
