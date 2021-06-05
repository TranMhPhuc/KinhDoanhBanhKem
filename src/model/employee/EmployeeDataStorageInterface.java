package model.employee;

import java.util.Iterator;
import model.DatabaseUpdate;
import view.function.employee.EmployeeUpdateObserver;

public interface EmployeeDataStorageInterface extends DatabaseUpdate {

    EmployeeModelInterface getEmployee(String employeeIDText);

    EmployeeModelInterface createEmployee();

    Iterator<EmployeeModelInterface> createIterator();

    int getSize();

    void registerObserver(EmployeeUpdateObserver observer);

    void removeObserver(EmployeeUpdateObserver observer);
}
