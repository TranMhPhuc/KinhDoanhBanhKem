package control.employee;

import java.util.Iterator;
import model.employee.EmployeeModelInterface;

public interface EmployeeControllerInterface {

    void requestCreateEmployee();

    void requestUpdateEmployee();

    void requestImportExcel();

    void requestExportExcel();

    void requestShowEmployeeInfo();

    boolean insertToSearchListByMatchingName(String searchText, EmployeeModelInterface employee);

    boolean deleteIngredientInSearchList(EmployeeModelInterface employee);

    Iterator<EmployeeModelInterface> getAllEmployeeData();

    Iterator<EmployeeModelInterface> getEmployeeBySearchName(String searchText);

}
