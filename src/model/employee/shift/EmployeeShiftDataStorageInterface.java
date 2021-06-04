package model.employee.shift;

import java.util.Iterator;
import model.DatabaseUpdate;

public interface EmployeeShiftDataStorageInterface extends DatabaseUpdate {

    EmployeeShiftModelInterface getShift(String shiftIDText);
    
    Iterator<EmployeeShiftModelInterface> createIterator();
    
    int getSize();
}
