package control.bill.history;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bill.BillModel;
import model.bill.BillModelInterface;
import util.db.SQLServerConnection;
import view.function.bill.BillHistoryPanel;

public class BillHistoryController implements BillHistoryControllerInterface {

    private static final String CLONE_TODAY_BILL_QUERY_PROTOTYPE
            = "SELECT * FROM " + BillModel.TABLE_NAME
            + " WHERE " + BillModel.DATE_HEADER + " = ?";

    private volatile static BillHistoryController uniqueInstance;

    private static Connection dbConnection;

    private BillHistoryPanel billHistoryPanel;
    private List<BillModelInterface> billList;

    static {
        dbConnection = SQLServerConnection.getConnection();
    }

    private BillHistoryController() {
        billList = new ArrayList<>();
        cloneTodayBill();
        this.billHistoryPanel = BillHistoryPanel.getInstance(this);
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
                System.out.println("Clone " + billList.size() + " bills.");
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(BillHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<BillModelInterface> getBillList(Date dateFrom, Date dateTo) {
        return billList;
    }

    @Override
    public void exportBillList() {

    }

    @Override
    public List<BillModelInterface> getTodayBillList() {
        cloneTodayBill();
        return billList;

    }

    @Override
    public void requestClearSearch() {
        
    }

    @Override
    public List<BillModelInterface> getBillSearchByEmployeeName(String employeeNameSearchText) {
        return billList;

    }

    @Override
    public List<BillModelInterface> getBillSearchByBillID(String billIDSearchText) {
        return billList;

    }

    @Override
    public void requestViewBillDetail(int rowID) {

    }

}
