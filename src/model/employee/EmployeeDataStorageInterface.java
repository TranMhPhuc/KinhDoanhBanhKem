package model.employee;

import model.DatabaseUpdate;

public interface EmployeeDataStorageInterface extends DatabaseUpdate {

    EmployeeModelInterface getEmployee(String employeeIDText);
    
    EmployeeModelInterface createEmployee();
    
    int getSize();
    
}
