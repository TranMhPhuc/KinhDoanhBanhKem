package model.bill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.EmployeeModelInterface;
import util.db.SQLServerConnection;
import view.function.bill.BillUpdateObserver;

public class BillManageModel implements BillManageModelInterface {

    private static Connection dbConnection;
    private ArrayList<BillUpdateObserver> observers;
    private BillModelInterface bill;

    static {
        dbConnection = SQLServerConnection.getConnection();
    }

    public BillManageModel() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(BillUpdateObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(BillUpdateObserver observer) {
        int id = observers.indexOf(observer);
        if (id >= 0) {
            observers.remove(observer);
        }
    }

    private void notifyObserver(int billNumber) {
        for (BillUpdateObserver observer : observers) {
            observer.updateBillNumber(billNumber);
        }
    }

    @Override
    public String getNextBillID() {
        try {
            Statement statement = dbConnection.createStatement();
            String query
                    = "SELECT COUNT(*) FROM " + BillModel.TABLE_NAME;
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                throw new Exception("Can not get next bill id.");
            }
            int billCount = resultSet.getInt(1);
            return String.valueOf(billCount + 1);
        } catch (Exception ex) {
            Logger.getLogger(BillManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    @Override
    public void exportBill() {
        bill.insertToDatabase();
        notifyObserver(bill.getBillID());
        clearBill();
    }

    @Override
    public void reviewInPDF() {
        if (bill == null) {
            throw new NullPointerException("No bill exist.");
        }
    }

    @Override
    public void setBillDateTimeExport(Timestamp dateTimeExport) {
        if (bill == null) {
            prepareBill();
        }
        bill.setDateTimeExport(dateTimeExport);
    }

    @Override
    public void setBillPayment(int payment) {
        if (bill == null) {
            prepareBill();
        }
        bill.setPayment(payment);
    }

    @Override
    public void setBillGuestMoney(int guestMoney) {
        if (bill == null) {
            prepareBill();
        }
        bill.setGuestMoney(guestMoney);
    }

    @Override
    public void setBillChangeMoney(int changeMoney) {
        if (bill == null) {
            prepareBill();
        }
        bill.setChangeMoney(changeMoney);
    }

    @Override
    public void setBillEmployee(EmployeeModelInterface employee) {
        if (bill == null) {
            prepareBill();
        }
        bill.setEmployee(employee);
    }

    @Override
    public void prepareBill() {
        bill = new BillModel();
    }
    
    private void clearBill() {
        bill = null;
    }

}
