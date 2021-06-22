package control.bill.history;

import java.util.Iterator;
import model.bill.BillModelInterface;
import view.bill.BillHistoryPanel;
import view.bill.BillInsertedObserver;

public interface BillHistoryControllerInterface extends BillInsertedObserver {

    void setBillHistoryPanel(BillHistoryPanel billHistoryPanel);
    
    void exportBillToExcel();

    Iterator<BillModelInterface> getTodayBillData();

    Iterator<BillModelInterface> getBillDataFromDateRange();

    Iterator<BillModelInterface> getBillSearchByEmployeeName();

    void requestShowBillDetail();
    
    void requestShowBillFromDateRange();
    
    void requestShowTodayBill();
}
