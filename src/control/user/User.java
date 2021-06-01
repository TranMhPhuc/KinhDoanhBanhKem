package control.user;

import model.employee.EmployeeModel;

/**
 * User is a singleton class. Only one user use application one time.
 */
public class User {

    private EmployeeModel employee;
    
    private static User uniqueInstance;
    
    private User() {
    }

//    public static User getInstance() {
//        return 
//    }
}
