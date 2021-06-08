package model.employee.shift;

import model.DatabaseModel;

public interface EmployeeShiftModelInterface extends DatabaseModel {

    String getShiftIDText();
    
    String getName();
}
