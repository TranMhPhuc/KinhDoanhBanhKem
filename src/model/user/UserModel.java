package model.user;

import java.util.ArrayList;
import model.employee.EmployeeModelInterface;

/**
 * UserModel is a singleton class. Only one user use application one time.
 */
public class UserModel implements UserModelInterface {

    private EmployeeModelInterface impl;

    private static UserModel uniqueInstance;

    static {
        uniqueInstance = new UserModel();
    }

    private UserModel() {
    }

    @Override
    public void updateUser(EmployeeModelInterface impl) {
        if (impl == null) {
            throw new NullPointerException();
        }
        this.impl = impl;
    }

    public static UserModel getInstance() {
        return uniqueInstance;
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

    @Override
    public EmployeeModelInterface getImpl() {
        return this.impl;
    }
}
