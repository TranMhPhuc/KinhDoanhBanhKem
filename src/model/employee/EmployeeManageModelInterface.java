package model.employee;

import java.util.Iterator;
import java.util.List;
import model.DatabaseUpdate;
import view.employee.InsertedEmployeeObserver;
import view.employee.ModifiedEmployeeObserver;

public interface EmployeeManageModelInterface extends DatabaseUpdate {

    void registerInsertedEmployeeObserver(InsertedEmployeeObserver observer);

    void removeInsertedEmployeeObserver(InsertedEmployeeObserver observer);

    void registerModifiedEmployeeObserver(ModifiedEmployeeObserver observer);

    void removeModifiedEmployeeObserver(ModifiedEmployeeObserver observer);

    //=========================================================================
    
    String getNextEmployeeIDText();
    
    //=========================================================================
    
    void exportEmployeeData();
    
    void importEmployeeData();
    
    //=========================================================================
    
    void addEmployee(EmployeeModelInterface employee);
    
    boolean updateEmployee(EmployeeModelInterface employee);
    
    //=========================================================================
    
    EmployeeModelInterface getEmployeeByID(String employeeIDText);
    
    EmployeeModelInterface getEmployeeByIndex(int employeeIndex);
    
    Iterator<EmployeeModelInterface> getAllEmployeeData();
    
    Iterator<EmployeeModelInterface> getEmployeeSearchByName(String searchText);
    
    List<String> getAllPositionName();
    
    List<String> getAllShiftName();
}
