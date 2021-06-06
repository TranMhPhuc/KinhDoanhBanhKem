package model.bill;

import java.sql.Timestamp;
import model.employee.EmployeeModelInterface;
import view.function.bill.BillUpdateObserver;

public interface BillManageModelInterface {

    void registerObserver(BillUpdateObserver observer);

    void removeObserver(BillUpdateObserver observer);

    String getNextBillID();

    void exportBill();

    void reviewInPDF();
    
    void prepareBill();

    void setBillDateTimeExport(Timestamp dateTimeExport);

    void setBillPayment(int payment);

    void setBillGuestMoney(int guestMoney);

    void setBillChangeMoney(int changeMoney);

    void setBillEmployee(EmployeeModelInterface employee);

}
