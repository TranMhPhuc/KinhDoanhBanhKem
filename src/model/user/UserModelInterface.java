package model.user;

import model.employee.EmployeeModelInterface;

public interface UserModelInterface {

    enum UserType {
        MANAGER_USER,
        ACCOUNTANT_USER,
        CASHIER_USER,
        NO_PERMISSION,
    }
    
    UserType getUserType();
    
    String getUserName();
    
    EmployeeModelInterface getImpl();
    
    void updateUser(EmployeeModelInterface impl);
    
}
