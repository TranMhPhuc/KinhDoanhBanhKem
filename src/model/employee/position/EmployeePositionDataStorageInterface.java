package model.employee.position;

import java.util.Iterator;
import model.DatabaseUpdate;

public interface EmployeePositionDataStorageInterface extends DatabaseUpdate {

    EmployeePositionModelInterface getPositionByID(String positionIDText);
    
    EmployeePositionModelInterface getPositionByName(String positionName);
    
    int getPositionIndex(EmployeePositionModelInterface position);
    
    Iterator<EmployeePositionModelInterface> createIterator();
    
}
