package control.bill.history;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bill.BillModel;
import model.bill.detail.ProductDetailModel;
import util.db.SQLServerConnection;
import view.dialog.BillDetailDialog;
import view.bill.BillHistoryPanel;
import model.bill.detail.ProductDetailModelInterface;
import model.bill.BillModelInterface;

public class BillHistoryController implements BillHistoryControllerInterface {

    private static final String CLONE_TODAY_BILL_QUERY_PROTOTYPE
            = "SELECT * FROM " + BillModel.TABLE_NAME
            + " WHERE CONVERT(date, " + BillModel.DATE_HEADER + ") = ?";

    private static final String CLONE_DAY_RANGE_BILL_QUERY_PROTOTYPE
            = "SELECT * FROM " + BillModel.TABLE_NAME
            + " WHERE ? <= CONVERT(date, " + BillModel.DATE_HEADER + ")"
            + " AND CONVERT(date, " + BillModel.DATE_HEADER + ") <= ?";

    private static final String FIND_PRODUCT_DETAIL_QUERY_PROTOTYPE
            = "SELECT * FROM " + ProductDetailModel.TABLE_NAME
            + " WHERE " + ProductDetailModel.BILL_ID_HEADER + " = ?";

    private volatile static BillHistoryController uniqueInstance;

    private static Connection dbConnection;

    private BillHistoryPanel billHistoryPanel;
    private List<BillModelInterface> billList;
    private BillDetailDialog billDetailDialog;

    static {
        dbConnection = SQLServerConnection.getConnection();
    }

    private BillHistoryController() {
        billList = new ArrayList<>();
//        this.billHistoryPanel = BillHistoryPanel.getInstance(this);
    }

    public static BillHistoryController getInstance() {
        if (uniqueInstance == null) {
            synchronized (BillHistoryController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new BillHistoryController();
                }
            }
        }
        return uniqueInstance;
    }

    private void cloneTodayBill() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(CLONE_TODAY_BILL_QUERY_PROTOTYPE);

            preparedStatement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));

            ResultSet resultSet = preparedStatement.executeQuery();

            billList.clear();

            while (resultSet.next()) {
                BillModelInterface bill = new BillModel();
                bill.setProperty(resultSet);
                billList.add(bill);
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(BillHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<BillModelInterface> getBillList(Date dateFrom, Date dateTo) {
        if (dateTo.before(dateFrom)) {
            this.billHistoryPanel.showErrorMessage("Date range is invallid.");
        } else {
            try {
                PreparedStatement preparedStatement = dbConnection
                        .prepareStatement(CLONE_DAY_RANGE_BILL_QUERY_PROTOTYPE);

                preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
                preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));

                ResultSet resultSet = preparedStatement.executeQuery();

                billList.clear();

                while (resultSet.next()) {
                    BillModelInterface bill = new BillModel();
                    bill.setProperty(resultSet);
                    billList.add(bill);
                }

                resultSet.close();
                preparedStatement.close();

            } catch (SQLException ex) {
                Logger.getLogger(BillHistoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return billList;
    }

    @Override
    public void exportBillList() {
        // XXX
    }

    @Override
    public List<BillModelInterface> getTodayBillList() {
        cloneTodayBill();
        return billList;

    }

    @Override
    public void requestClearSearch() {
        this.billHistoryPanel.setSearchText("");
        this.billHistoryPanel.showBills(billList);
    }

    @Override
    public List<BillModelInterface> getBillSearchByEmployeeName(String employeeNameSearchText) {
        // XXX

        return billList;
    }

    @Override
    public List<BillModelInterface> getBillSearchByBillID(String billIDSearchText) {
        // XXX

        return billList;

    }

    @Override
    public void requestViewBillDetail(int rowID) {
        BillModelInterface bill = billList.get(rowID);

        List<ProductDetailModelInterface> productDetails = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(FIND_PRODUCT_DETAIL_QUERY_PROTOTYPE);
            
            bill.setKeyArg(1, BillModel.ID_HEADER, preparedStatement);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                ProductDetailModelInterface productDetail = new ProductDetailModel();
                productDetail.setProperty(resultSet);
                productDetails.add(productDetail);
            }
            
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(BillHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        billDetailDialog = new BillDetailDialog(MainFrame.getInstance(), true, this);
        billDetailDialog.setTableBillDetail(productDetails);
        billDetailDialog.setBillInfo(bill);
        billDetailDialog.setVisible(true);
    }

}
