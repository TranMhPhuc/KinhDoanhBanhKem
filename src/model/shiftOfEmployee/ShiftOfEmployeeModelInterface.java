package model.shiftOfEmployee;

import model.DatabaseModel;
import model.employee.EmployeeModelInterface;
import model.employee.shift.EmployeeShiftModelInterface;

public interface ShiftOfEmployeeModelInterface extends DatabaseModel {

    void setEmployee(EmployeeModelInterface employee);
    
    void setShift(EmployeeShiftModelInterface shift);
    
}
