package model.employee.position;

import model.DatabaseModel;

public interface EmployeePositionModelInterface extends DatabaseModel {
    
    String getPositionName();
    
    String getPositionIDText();
}
