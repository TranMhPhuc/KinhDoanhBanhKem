package control.employee;

import model.employee.EmployeeModelInterface;
import view.function.employee.EmployeeManagePanel;

public class EmployeeManageController implements EmployeeManageControllerInterface {

    private volatile static EmployeeManageController uniqueInstance;

    private EmployeeModelInterface model;
    private EmployeeManagePanel view;

    private EmployeeManageController(EmployeeModelInterface model) {
        this.model = model;
        this.view = EmployeeManagePanel.getInstance(this);
    }

    public static EmployeeManageControllerInterface getInstance(EmployeeModelInterface model) {
        if (uniqueInstance == null) {
            synchronized (EmployeeManageController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new EmployeeManageController(model);
                }
            }
        }
        return uniqueInstance;
    }

    public static EmployeeManageControllerInterface getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

}
