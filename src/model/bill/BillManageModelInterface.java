package model.bill;

import java.sql.Timestamp;
import java.util.List;
import model.employee.EmployeeModelInterface;
import model.product.ProductModelInterface;
import org.apache.commons.lang3.tuple.Pair;
import view.function.bill.BillUpdateObserver;

public interface BillManageModelInterface {

    void registerObserver(BillUpdateObserver observer);

    void removeObserver(BillUpdateObserver observer);

    String getNextBillID();
    
    int getBillNumber();

    void exportBill();

    void reviewInPDF();
    
    void prepareBill();

    void setBillDateTimeExport(Timestamp dateTimeExport);

    void setBillPayment(int payment);

    void setBillGuestMoney(int guestMoney);

    void setBillChangeMoney(int changeMoney);

    void setBillEmployee(EmployeeModelInterface employee);
    
    void setProductListOfBill(List<Pair<ProductModelInterface, Integer>> products);

}
