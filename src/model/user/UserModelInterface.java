package model.user;

import model.employee.EmployeeModelInterface;
import view.login.LoginObserver;

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
    
    void registerObserver(LoginObserver menuObserver);
    
    void removeObserver(LoginObserver menuObserver);
    
}
