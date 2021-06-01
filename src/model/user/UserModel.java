package model.user;

import model.employee.EmployeeModelInterface;

/**
 * UserModel is a singleton class. Only one user use application one time.
 */
public class UserModel {

    private static EmployeeModelInterface impl;
    
    private UserModel() {
    }
    
    public static void setUser(EmployeeModelInterface employee) {
        if (impl != employee) {
            impl = employee;
            // update view observer...
        }
    }

    public static EmployeeModelInterface getInstance() {
        return impl;
    }
}
