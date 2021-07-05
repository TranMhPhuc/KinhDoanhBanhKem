package model.bill;

import java.time.LocalDate;
import java.util.Iterator;
import model.bill.detail.ProductDetailModelInterface;
import view.bill.BillHistoryPanel;

public interface BillHistoryModelInterface {

    void setBillHistoryPanel(BillHistoryPanel billHistoryPanel);
    
    void loadTodayBillData();
    
    void loadBillDataFromDateRange(LocalDate dateFrom, LocalDate dateTo);
    
    Iterator<BillModelInterface> getAllBillDataFromDateRange();
    
    Iterator<BillModelInterface> getBillDataSearchByEmployeeName(String employeeSearchText);
    
    Iterator<ProductDetailModelInterface> getBillDetail(BillModelInterface bill);
    
}
