package view.employee;

import model.employee.EmployeeModelInterface;

public interface ModifiedEmployeeObserver {

    void updateModifiedEmployee(EmployeeModelInterface updatedEmployee);
}
