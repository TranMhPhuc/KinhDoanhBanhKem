package model.employee;

import java.util.Iterator;
import model.employee.position.EmployeePositionModelInterface;
import model.employee.shift.EmployeeShiftModelInterface;
import view.function.employee.InsertedEmployeeObserver;
import view.function.employee.ModifiedEmployeeObserver;

public interface EmployeeManageModelInterface {

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
    
    void addNewEmployee(EmployeeModelInterface newEmployee);
    
    void updateEmployee(EmployeeModelInterface updatedEmployee);
    
    //=========================================================================
    
    int getEmployeePositionIndex(EmployeeModelInterface employee);
    
    Iterator<EmployeePositionModelInterface> getAllPositionData();
    
    //=========================================================================
    
    Iterator<EmployeeShiftModelInterface> getAllShiftData();
    
    int getShiftIndex(EmployeeShiftModelInterface shift);
    
    //=========================================================================
    
    EmployeeModelInterface getEmployeeByID(String employeeIDText);
    
    Iterator<EmployeeModelInterface> getAllEmployeeData();
    
    Iterator<EmployeeModelInterface> getEmployeeSearchByName(String searchText);
    
    
}
