package control.employee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.employee.EmployeeManageModelInterface;
import model.employee.EmployeeModelInterface;
import view.function.employee.EmployeeManagePanel;

public class EmployeeController implements EmployeeControllerInterface {

    private volatile static EmployeeController uniqueInstance;

    private List<EmployeeModelInterface> searchList;
    
    private EmployeeManageModelInterface model;
    private EmployeeManagePanel view;

    private EmployeeController(EmployeeManageModelInterface model) {
        this.searchList = new ArrayList<>();
        
        this.model = model;
        this.view = EmployeeManagePanel.getInstance(this);
    }

    public static EmployeeControllerInterface getInstance(EmployeeManageModelInterface model) {
        if (uniqueInstance == null) {
            synchronized (EmployeeController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new EmployeeController(model);
                }
            }
        }
        return uniqueInstance;
    }

    public static EmployeeControllerInterface getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

    @Override
    public void requestCreateEmployee() {
        
    }

    @Override
    public void requestUpdateEmployee() {
        
    }

    @Override
    public void requestImportExcel() {
        
    }

    @Override
    public void requestExportExcel() {
        
    }

    @Override
    public void requestShowEmployeeInfo() {
        
    }

    @Override
    public boolean insertToSearchListByMatchingName(String searchText, EmployeeModelInterface employee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteIngredientInSearchList(EmployeeModelInterface employee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<EmployeeModelInterface> getAllEmployeeData() {
        return this.model.getAllEmployeeData();
    }

    @Override
    public Iterator<EmployeeModelInterface> getEmployeeBySearchName(String searchText) {
        return this.model.getEmployeeSearchByName(searchText);
    }

}
