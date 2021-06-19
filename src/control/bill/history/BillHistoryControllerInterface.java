package control.bill.history;

import java.util.Iterator;
import model.bill.BillModelInterface;
import view.bill.BillHistoryPanel;

public interface BillHistoryControllerInterface {

    void setBillHistoryPanel(BillHistoryPanel billHistoryPanel);
    
    void exportBillToExcel();

    Iterator<BillModelInterface> getTodayBillData();

    Iterator<BillModelInterface> getBillDataFromDateRange();

    Iterator<BillModelInterface> getBillSearchByEmployeeName();

    void requestShowBillDetail();
    
    void requestShowBillFromDateRange();
    
    void requestShowTodayBill();
}
