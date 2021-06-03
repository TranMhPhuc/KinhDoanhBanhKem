package model.employee;

import view.function.EmployeeViewObserver;

public interface EmployeeModelInterface {

    String getEmployeeIDText();
    
    String getEmployeePositionName();
    
    String getEmployeeName();
    
    void registerObserver(EmployeeViewObserver employeeViewObserver);

    void removeObserver(EmployeeViewObserver employeeViewObserver);

    void notifyObserver();
}
