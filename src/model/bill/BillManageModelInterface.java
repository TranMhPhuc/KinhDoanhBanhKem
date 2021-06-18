package model.bill;

import java.sql.Timestamp;
import java.util.List;
import model.employee.EmployeeModelInterface;
import model.product.ProductModelInterface;
import org.apache.commons.lang3.tuple.Pair;
import view.bill.BillUpdateObserver;

public interface BillManageModelInterface {

    void registerObserver(BillUpdateObserver observer);

    void removeObserver(BillUpdateObserver observer);

    String getNextBillIDText();

    void exportBill(BillModelInterface bill);

}
