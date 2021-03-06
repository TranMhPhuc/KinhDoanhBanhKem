package control.bill.history;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.bill.BillHistoryModelInterface;
import model.bill.BillModelInterface;
import model.bill.detail.ProductDetailModelInterface;
import model.setting.AppSetting;
import util.excel.ExcelTransfer;
import util.messages.Messages;
import view.bill.BillDetailDialog;
import view.bill.BillHistoryPanel;

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
            billHistoryPanel.showErrorMessage(Messages.getInstance().BILLH_LIST_EMPTY);
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
            billHistoryPanel.showErrorMessage(Messages.getInstance().BILLH_NO_BILL_CHOSEN);
            return;
        }

        if (dialogBillDetail == null) {
            dialogBillDetail = new BillDetailDialog(billHistoryPanel.getMainFrame(), true, this);
            AppSetting.getInstance().registerObserver(dialogBillDetail);
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
            billHistoryPanel.showErrorMessage(Messages.getInstance().BILLH_DATE_RANGE_INVALID);
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

    @Override
    public void updateInsertedBillObserver() {
        requestShowBillFromDateRange();
    }

}
