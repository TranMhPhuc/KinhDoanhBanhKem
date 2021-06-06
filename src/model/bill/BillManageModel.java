package model.bill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.EmployeeModelInterface;
import model.product.ProductModelInterface;
import model.productOfBill.ProductOfBillDetail;
import model.productOfBill.ProductOfBillDetailInterface;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import util.db.SQLServerConnection;
import view.function.bill.BillUpdateObserver;

public class BillManageModel implements BillManageModelInterface {
    
    private volatile static BillManageModel uniqueInstance;

    private static Connection dbConnection;
    private ArrayList<BillUpdateObserver> observers;
    private BillModelInterface bill;
    private ArrayList<ProductOfBillDetailInterface> productDetails;

    private static int billCount;

    static {
        dbConnection = SQLServerConnection.getConnection();
        try {
            Statement statement = dbConnection.createStatement();
            String query
                    = "SELECT COUNT(*) FROM " + BillModel.TABLE_NAME;
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                billCount = resultSet.getInt(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(BillManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private BillManageModel() {
        observers = new ArrayList<>();
        productDetails = new ArrayList<>();
    }
    
    public static BillManageModel getInstance() {
        if (uniqueInstance == null) {
            synchronized (BillManageModel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new BillManageModel();
                }
            }
        }
        return uniqueInstance;
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
        System.out.println("Observer num: " + observers.size());
        for (BillUpdateObserver observer : observers) {
            observer.updateBillNumber(billNumber);
        }
    }

    @Override
    public String getNextBillID() {
        return String.valueOf(billCount + 1);
    }

    @Override
    public void exportBill() {
        bill.insertToDatabase();
        for (ProductOfBillDetailInterface productDetail : productDetails) {
            productDetail.insertToDatabase();
        }
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
        billCount++;
        bill = new BillModel();
        bill.setBillID(billCount);
    }

    private void clearBill() {
        bill = null;
        productDetails.clear();
    }

    @Override
    public void setProductListOfBill(List<Pair<ProductModelInterface, Integer>> products) {
        for (Pair<ProductModelInterface, Integer> element : products) {
            ProductModelInterface product = element.getKey();
            int amount = element.getValue();
            ProductOfBillDetailInterface productDetail = new ProductOfBillDetail();
            productDetail.setBill(bill);
            productDetail.setProduct(product);
            productDetail.setAmount(amount);
            productDetail.setPrice(amount * product.getPrice());
            productDetails.add(productDetail);
        }
    }

    @Override
    public int getBillNumber() {
        return billCount;
    }

}
