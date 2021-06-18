package control.employee;

import java.util.Iterator;
import model.employee.EmployeeModelInterface;
import view.employee.EmployeePanel;

public interface EmployeeControllerInterface {

    void setEmployeePanelView(EmployeePanel employeePanel);
    
    void requestCreateEmployee();

    void requestUpdateEmployee();

    void requestImportExcel();

    void requestExportExcel();
    
    void requestCreateTemplateExcel();

    void requestShowEmployeeInfo();

    boolean isSearchMatching(String searchText, EmployeeModelInterface employee);

    boolean deleteIngredientInSearchList(EmployeeModelInterface employee);

    Iterator<EmployeeModelInterface> getAllEmployeeData();

    Iterator<EmployeeModelInterface> getEmployeeBySearchName(String searchText);

    boolean canCloseEmployeeManagePanel();
    
}
