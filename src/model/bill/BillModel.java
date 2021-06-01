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

    private static EmployeeDataStorage employeeDataStorage;

    private int billID;
    private Timestamp dateTimeExport;
    private int payment;
    private int guestMoney;
    private int changeMoney;
    private EmployeeModelInterface employee;

    private BillDetailModel billDetail;

    static {
        employeeDataStorage = EmployeeDataStorage.getInstance();
    }

    public BillModel() {
    }

    public static BillModel getInstance(ResultSet resultSet) {
        BillModel ret = new BillModel();
                
        try {
            ret.billID = resultSet.getInt("MaHD");
            ret.dateTimeExport = resultSet.getTimestamp("NgayLap");
            ret.payment = resultSet.getInt("TongTien");
            ret.guestMoney = resultSet.getInt("TienKhachTra");
            ret.changeMoney = resultSet.getInt("TienThoi");
            
            String employeeIDText = resultSet.getString(EmployeeModel.ID_HEADER);

            EmployeeModelInterface employee = employeeDataStorage
                    .getEmployee(employeeIDText);
            
            ret.employee = employee;

        } catch (SQLException ex) {
            Logger.getLogger(BillModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ret;
    }

    private static void updateFromDB() {

    }

    public int getBillID() {
        return billID;
    }

    public Timestamp getDateExport() {
        return dateTimeExport;
    }

    public int getPayment() {
        return payment;
    }

    public int getGuestMoney() {
        return guestMoney;
    }

    public int getChangeMoney() {
        return changeMoney;
    }

    public EmployeeModelInterface getEmployee() {
        return employee;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public void setDateTimeExport(Timestamp dateExport) {
        this.dateTimeExport = dateExport;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public void setGuestMoney(int givenMoney) {
        this.guestMoney = givenMoney;
    }

    public void setChangeMoney(int changeMoney) {
        this.changeMoney = changeMoney;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Bill{" + "billID=" + billID + ", dateExport=" + dateTimeExport + ", payment=" + payment + ", givenMoney=" + guestMoney + ", changeMoney=" + changeMoney + ", employee=" + employee + '}';
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
}
