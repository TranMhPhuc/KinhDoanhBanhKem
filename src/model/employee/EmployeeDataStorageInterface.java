package model.employee;

import java.util.Iterator;
import model.DatabaseUpdate;

public interface EmployeeDataStorageInterface extends DatabaseUpdate {

    EmployeeModelInterface getEmployeeByID(String employeeIDText);
    
    EmployeeModelInterface getEmployeeByIndex(int employeeIndex);

    Iterator<EmployeeModelInterface> createIterator();

    int getSize();

    Iterator<EmployeeModelInterface> getEmployeeSearchByName(String searchText);
    
    void add(EmployeeModelInterface employee);
    
    boolean update(EmployeeModelInterface employee);
    
    boolean remove(EmployeeModelInterface employee);
    
}
