package model.employee.position;

import model.DatabaseUpdate;

public interface EmployeePositionDataStorageInterface extends DatabaseUpdate {

    EmployeePositionModelInterface getPositionByID(String positionIDText);
    
    EmployeePositionModelInterface getPositionByName(String positionName);
    
}
