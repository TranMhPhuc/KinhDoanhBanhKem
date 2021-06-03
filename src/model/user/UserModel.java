package model.user;

import java.util.ArrayList;
import model.employee.EmployeeModelInterface;
import view.UpdateFromUser;

/**
 * UserModel is a singleton class. Only one user use application one time.
 */
public class UserModel implements UserModelInterface {

    private EmployeeModelInterface impl;
    
    private static UserModel uniqueInstance;
    
    private ArrayList<UpdateFromUser> observers;
    
    static {
        uniqueInstance = new UserModel();
    }
    
    private UserModel() {
        observers = new ArrayList<>();
    }
    
    @Override
    public void updateUser(EmployeeModelInterface impl) {
        if (this.impl != impl) {
            this.impl = impl;
            notifyMenuObservers();
        }
    }
    
    public static UserModel getInstance() {
        return uniqueInstance;
    }

    @Override
    public void registerObserver(UpdateFromUser menuObserver) {
        observers.add(menuObserver);
    }

    @Override
    public void removeObserver(UpdateFromUser menuObserver) {
        int id = observers.indexOf(menuObserver);
        if (id >= 0) {
            observers.remove(menuObserver);
        }
    }

    public void notifyMenuObservers() {
        for (UpdateFromUser menuObserver: observers) {
            menuObserver.updateState();
        }
    }

    @Override
    public UserType getUserType() {
        if (impl != null) {
            String positionName = impl.getEmployeePositionName();
            if (positionName.equals("Quản lý")) {
                return UserType.MANAGER_USER;
            } else if (positionName.equals("Bán hàng")) {
                return UserType.CASHIER_USER;
            } else if (positionName.equals("Kế toán")) {
                return UserType.ACCOUNTANT_USER;
            } else {
                return UserType.NO_PERMISSION;
            }
        }
        return null;
    }

    @Override
    public String getUserName() {
        return impl.getEmployeeName();
    }
    
}
