package control.bill.history;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bill.BillHistoryModelInterface;
import model.bill.BillModel;
import model.bill.detail.ProductDetailModel;
import util.db.SQLServerConnection;
import view.dialog.BillDetailDialog;
import view.bill.BillHistoryPanel;
import model.bill.detail.ProductDetailModelInterface;
import model.bill.BillModelInterface;
import util.excel.ExcelTransfer;

public class BillHistoryController implements BillHistoryControllerInterface {

    private BillHistoryModelInterface billHistoryModel;
    private BillHistoryPanel billHistoryPanel;

    private BillDetailDialog dialogBillDetail;

    private List<BillModelInterface> searchList;

    public BillHistoryController(BillHistoryModelInterface billHistoryModel) {
        searchList = new ArrayList<>();
        this.billHistoryModel = billHistoryModel;
    }

    @Override
    public void setBillHistoryPanel(BillHistoryPanel billHistoryPanel) {
        if (billHistoryPanel == null) {
            throw new NullPointerException();
        }
        this.billHistoryPanel = billHistoryPanel;
        billHistoryPanel.setBillHistoryController(this);
        billHistoryPanel.setBillHistoryModel(billHistoryModel);
    }

    @Override
    public void exportBillToExcel() {
        if (searchList.isEmpty()) {
            billHistoryPanel.showErrorMessage("Bill list is empty.");
            return;
        }
        
        ExcelTransfer.exportTableToExcel(billHistoryPanel.getTableBillInfo());
    }

    @Override
    public Iterator<BillModelInterface> getTodayBillData() {
        billHistoryModel.loadTodayBillData();

        searchList.clear();
        Iterator<BillModelInterface> iterator = billHistoryModel.getAllBillDataFromDateRange();
        while (iterator.hasNext()) {
            searchList.add(iterator.next());
        }
        return searchList.iterator();
    }

    @Override
    public Iterator<BillModelInterface> getBillDataFromDateRange() {
        searchList.clear();
        Iterator<BillModelInterface> iterator = billHistoryModel.getAllBillDataFromDateRange();
        while (iterator.hasNext()) {
            searchList.add(iterator.next());
        }
        return searchList.iterator();
    }

    @Override
    public Iterator<BillModelInterface> getBillSearchByEmployeeName() {
        String searchText = billHistoryPanel.getSearchText();

        searchList.clear();
        Iterator<BillModelInterface> iterator = billHistoryModel.getBillDataSearchByEmployeeName(searchText);
        while (iterator.hasNext()) {
            searchList.add(iterator.next());
        }
        return searchList.iterator();
    }

    @Override
    public void requestShowBillDetail() {
        int rowID = billHistoryPanel.getTableBillSelectedRowIndex();
        
        if (rowID == -1) {
            billHistoryPanel.showErrorMessage("You should choose bill first.");
            return;
        }
        
        if (dialogBillDetail == null) {
            dialogBillDetail = new BillDetailDialog(billHistoryPanel.getMainFrame(), true, this);
        }
        
        BillModelInterface selectedBill = searchList.get(rowID);
        
        dialogBillDetail.setBillInfo(selectedBill);
        
        Iterator<ProductDetailModelInterface> iterator = billHistoryModel.getBillDetail(selectedBill);
        
        dialogBillDetail.setTableBillDetail(iterator);
        
        dialogBillDetail.setVisible(true);
    }

    @Override
    public void requestShowBillFromDateRange() {
        LocalDate dateFrom = billHistoryPanel.getDateFromInput();
        LocalDate dateTo = billHistoryPanel.getDateToInput();

        if (dateFrom.isAfter(dateTo)) {
            billHistoryPanel.showErrorMessage("Date range is invalid.");
            return;
        }

        billHistoryModel.loadBillDataFromDateRange(dateFrom, dateTo);
    }

    @Override
    public void requestShowTodayBill() {
        billHistoryModel.loadTodayBillData();
        billHistoryPanel.setDateFromInput(LocalDate.now());
        billHistoryPanel.setDateToInput(LocalDate.now());
    }

}
