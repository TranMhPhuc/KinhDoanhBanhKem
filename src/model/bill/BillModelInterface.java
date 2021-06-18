package model.bill;

import java.sql.Timestamp;
import model.DatabaseModel;
import model.bill.detail.ProductDetailModelInterface;
import model.employee.EmployeeModelInterface;

public interface BillModelInterface extends DatabaseModel {
    
    void setBillID(int id);
    
    void setDateTimeExport(Timestamp dateTimeExport);
    
    void setPayment(long payment);
    
    void setGuestMoney(long guestMoney);
    
    void setChangeMoney(long changeMoney);
    
    void setEmployeeName(String employeeName);
    
    int getBillID();

    String getBillIDText();

    Timestamp getDateTimeExport();
    
    long getPayment();
    
    long getGuestMoney();
    
    long getChangeMoney();
    
    String getEmployeeName();
    
}
