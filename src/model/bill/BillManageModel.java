package model.bill;

import java.sql.CallableStatement;
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
import model.bill.detail.ProductDetailModel;
import org.apache.commons.lang3.tuple.Pair;
import util.db.SQLServerConnection;
import view.bill.BillUpdateObserver;
import model.bill.detail.ProductDetailModelInterface;
import model.ingredient.IngredientManageModel;

public class BillManageModel implements BillManageModelInterface {

    private volatile static BillManageModel uniqueInstance;

    private static Connection dbConnection;
    
    private ArrayList<ProductDetailModelInterface> productDetails;

    private ArrayList<BillUpdateObserver> observers;

    private static final String FIND_NEXT_IDENTITY_BILL
            = "{call get_next_identity_id_bill}";

    static {
        dbConnection = SQLServerConnection.getConnection();
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
    public String getNextBillIDText() {
        int nextIdentity = 0;
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(FIND_NEXT_IDENTITY_BILL);
            ResultSet resultSet = callableStatement.executeQuery(FIND_NEXT_IDENTITY_BILL);
            if (resultSet.next()) {
                nextIdentity = resultSet.getInt(1);
            }
            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return String.valueOf(nextIdentity);
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

    private void notifyObserver(BillModelInterface bill) {
        for (BillUpdateObserver observer : observers) {
            observer.updateObserver(bill);
        }
    }

    @Override
    public void exportBill(BillModelInterface bill) {
        bill.insertToDatabase();
        for (ProductDetailModelInterface productDetail : productDetails) {
            productDetail.insertToDatabase();
        }
        notifyObserver(bill);
    }
}
