package model.employee;

import model.DatabaseModel;

public interface EmployeeModelInterface extends DatabaseModel {

    String getEmployeeIDText();
    
    String getEmployeePositionName();
    
    String getEmployeeName();
}
