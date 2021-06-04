package control.employee;

import view.function.EmployeeManagePanel;

public class EmployeeManageController implements EmployeeManageControllerInterface {
    
    private volatile static EmployeeManageController uniqueInstance;
    
    private EmployeeManageControllerInterface model;
    private EmployeeManagePanel view;
    
    private EmployeeManageController(EmployeeManageControllerInterface model) {
        this.model = model;
        this.view = EmployeeManagePanel.getInstance(this);
    }
    
    public static EmployeeManageControllerInterface getInstance(EmployeeManageControllerInterface model) {
        if (uniqueInstance == null) {
            synchronized (EmployeeManageController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new EmployeeManageController(model);
                }
            }
        }
        return uniqueInstance;
    }
    
}
