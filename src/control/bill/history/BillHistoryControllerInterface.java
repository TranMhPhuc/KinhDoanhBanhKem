package control.bill.history;

import java.util.Date;
import java.util.List;
import model.bill.BillModelInterface;

public interface BillHistoryControllerInterface {

    List<BillModelInterface> getBillList(Date dateFrom, Date dateTo);

    void exportBillList();

    List<BillModelInterface> getTodayBillList();

    void requestClearSearch();

    List<BillModelInterface> getBillSearchByEmployeeName(String employeeNameSearchText);

    List<BillModelInterface> getBillSearchByBillID(String billIDSearchText);

    void requestViewBillDetail(int rowID);
}
