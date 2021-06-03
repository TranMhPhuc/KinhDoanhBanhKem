package model.user;

import model.employee.EmployeeModelInterface;
import view.UpdateFromUser;

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
    
    void registerObserver(UpdateFromUser menuObserver);
    
    void removeObserver(UpdateFromUser menuObserver);
    
}
