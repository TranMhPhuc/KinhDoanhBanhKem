package model.bill;

import java.sql.Timestamp;
import model.DatabaseModel;
import model.employee.EmployeeModelInterface;

public interface BillModelInterface extends DatabaseModel {
    
    void setBillID(int id);
    
    void setDateTimeExport(Timestamp dateTimeExport);
    
    void setPayment(int payment);
    
    void setGuestMoney(int guestMoney);
    
    void setChangeMoney(int changeMoney);
    
    void setEmployee(EmployeeModelInterface employee);
    
    int getBillID();

    String getBillIDText();

    Timestamp getDateTimeExport();
    
    int getPayment();
    
    int getGuestMoney();
    
    int getChangeMoney();
    
    EmployeeModelInterface getEmployee();
    
}
