package model.bill;

import java.sql.Timestamp;
import model.DatabaseModel;
import model.employee.EmployeeModelInterface;

public interface BillModelInterface extends DatabaseModel {
    
    void setBillIDText(String billIDText);
    
    void setDateTimeExport(Timestamp dateTimeExport);
    
    void setPayment(long payment);
    
    void setProfit(long profit);
    
    void setGuestMoney(long guestMoney);
    
    void setEmployee(EmployeeModelInterface employee);
    
    int getBillID();

    String getBillIDText();

    Timestamp getDateTimeExport();
    
    long getPayment();
    
    long getGuestMoney();
    
    long getChangeMoney();
    
    EmployeeModelInterface getEmployee();
    
}
