package model.employee;

import java.util.Iterator;
import model.DatabaseUpdate;

public interface EmployeeDataStorageInterface extends DatabaseUpdate {

    EmployeeModelInterface getEmployee(String employeeIDText);
    
    EmployeeModelInterface createEmployee();
    
    Iterator<EmployeeModelInterface> createIterator();
    
    int getSize();
    
}
