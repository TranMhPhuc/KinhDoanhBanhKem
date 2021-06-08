package model.user;

import model.employee.EmployeeModelInterface;

/**
 * UserModel is a singleton class. Only one user use application one time.
 */
public class UserModel implements UserModelInterface {

    private static UserModel uniqueInstance;

    private EmployeeModelInterface impl;
    private UserType userType;

    static {
        uniqueInstance = new UserModel();
    }

    private UserModel() {
        userType = UserType.NO_PERMISSION;
    }

    @Override
    public void updateUser(EmployeeModelInterface impl) {
        if (impl == null) {
            throw new NullPointerException();
        }
        this.impl = impl;

        String positionName = impl.getEmployeePositionName();

        if (positionName.equals("Quản lý")) {
            userType = UserType.MANAGER_USER;
        } else if (positionName.equals("Bán hàng")) {
            userType = UserType.CASHIER_USER;
        } else if (positionName.equals("Kế toán")) {
            userType = UserType.ACCOUNTANT_USER;
        } else {
            userType = UserType.NO_PERMISSION;
        }
    }

    public static UserModel getInstance() {
        return uniqueInstance;
    }

    @Override
    public UserType getUserType() {
        return this.userType;
    }

    @Override
    public String getUserName() {
        return impl.getName();
    }

    @Override
    public EmployeeModelInterface getImpl() {
        return this.impl;
    }
}
