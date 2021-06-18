package model.employee.shift.detail;

import model.DatabaseModel;
import model.employee.EmployeeModelInterface;

public interface ShiftDetailModelInterface extends DatabaseModel {

    void setEmployee(EmployeeModelInterface employee);
    
    void setShiftName(String shiftName);
    
    String getShiftName();
    
    EmployeeModelInterface getEmployee();
    
}