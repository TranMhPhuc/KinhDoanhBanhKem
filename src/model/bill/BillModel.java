package model.bill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.EmployeeDataStorage;
import model.employee.EmployeeModel;
import model.employee.EmployeeModelInterface;
import view.function.product.ProductViewObserver;

public class BillModel implements BillModelInterface {

    public static final String TABLE_NAME = "HoaDon";
    public static final String ID_HEADER = "MaHD";
    public static final String DATE_HEADER = "NgayLap";
    public static final String PAYMENT_HEADER = "TongTien";
    public static final String GUEST_MONEY_HEADER = "TienKhachTra";
    public static final String CHANGE_MONEY_HEADER = "TienThoi";
    public static final String EMPLOYEE_ID_HEADER = EmployeeModel.ID_HEADER;

    private static EmployeeDataStorage employeeDataStorage;

    private int id;
    private Timestamp dateTimeExport;
    private int payment;
    private int guestMoney;
    private int changeMoney;
    private EmployeeModelInterface employee;

    static {
        employeeDataStorage = EmployeeDataStorage.getInstance();
    }

    public BillModel() {
    }

    public static BillModel getInstance(ResultSet resultSet) {
        BillModel ret = new BillModel();

        try {
            ret.id = resultSet.getInt("MaHD");
            ret.dateTimeExport = resultSet.getTimestamp("NgayLap");
            ret.payment = resultSet.getInt("TongTien");
            ret.guestMoney = resultSet.getInt("TienKhachTra");
            ret.changeMoney = resultSet.getInt("TienThoi");

            ret.employee = employeeDataStorage
                    .getEmployee(resultSet.getString(EMPLOYEE_ID_HEADER));
        } catch (SQLException ex) {
            Logger.getLogger(BillModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    @Override
    public String getBillIDText() {
        return String.valueOf(this.id);
    }

    @Override
    public void notifyObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerObserver(ProductViewObserver productViewObserver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeObserver(ProductViewObserver productViewObserver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Bill{" + "billID=" + id + ", dateExport=" + dateTimeExport
                + ", payment=" + payment + ", givenMoney=" + guestMoney + ", changeMoney="
                + changeMoney + ", employee=" + employee + '}';
    }
}
