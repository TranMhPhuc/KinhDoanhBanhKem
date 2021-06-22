package model.bill;

import control.bill.history.BillHistoryControllerInterface;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import model.bill.detail.ProductDetailModel;
import model.bill.detail.ProductDetailModelInterface;
import model.product.ProductSimpleModel;
import model.product.ProductSimpleModelInterface;
import model.product.ProductSize;
import util.constant.AppConstant;
import util.db.SQLServerConnection;
import view.bill.BillHistoryPanel;

public class BillHistoryModel implements BillHistoryModelInterface {

    private static final String SP_GET_BILL_FROM_DATE_RANGE
            = "{call get_bill_from_day_range(?, ?)}";

    private static final String SP_GET_BILL_DETAIL
            = "{call get_bill_detail(?)}";

    private static Connection dbConnection;

    private List<BillModelInterface> billFromDateRange;

    // Observer
    private BillHistoryPanel billHistoryPanel;

    static {
        dbConnection = SQLServerConnection.getConnection();
    }

    public BillHistoryModel() {
        billFromDateRange = new ArrayList<>();
        loadTodayBillData();
    }

    @Override
    public void setBillHistoryPanel(BillHistoryPanel billHistoryPanel) {
        if (billHistoryPanel == null) {
            throw new NullPointerException();
        }
        this.billHistoryPanel = billHistoryPanel;
        billHistoryPanel.updateBillHistoryObserver();
    }

    @Override
    public void loadTodayBillData() {
        billFromDateRange.clear();

        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_BILL_FROM_DATE_RANGE);
            callableStatement.setDate(1, Date.valueOf(LocalDate.now()));
            callableStatement.setDate(2, Date.valueOf(LocalDate.now()));
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                BillModelInterface bill = new BillModel();
                bill.setProperty(resultSet);
                billFromDateRange.add(bill);
            }
            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(BillHistoryModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (billHistoryPanel != null) {
            billHistoryPanel.updateBillHistoryObserver();
        }
    }

    @Override
    public void loadBillDataFromDateRange(LocalDate dateFrom, LocalDate dateTo) {
        billFromDateRange.clear();

        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_BILL_FROM_DATE_RANGE);
            callableStatement.setDate(1, Date.valueOf(dateFrom));
            callableStatement.setDate(2, Date.valueOf(dateTo));
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                BillModelInterface bill = new BillModel();
                bill.setProperty(resultSet);
                billFromDateRange.add(bill);
            }
            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(BillHistoryModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (billHistoryPanel != null) {
            billHistoryPanel.updateBillHistoryObserver();
        }
    }

    @Override
    public Iterator<BillModelInterface> getAllBillDataFromDateRange() {
        return billFromDateRange.iterator();
    }

    @Override
    public Iterator<BillModelInterface> getBillDataSearchByEmployeeName(String employeeSearchText) {
        List<BillModelInterface> ret = new ArrayList<>();
        List<BoundExtractedResult<BillModelInterface>> matches
                = FuzzySearch.extractSorted(employeeSearchText, billFromDateRange, bill
                        -> bill.getEmployee().getName(), AppConstant.SEARCH_SCORE_CUT_OFF);
        matches.forEach(match -> ret.add(match.getReferent()));
        return ret.iterator();
    }

    @Override
    public Iterator<ProductDetailModelInterface> getBillDetail(BillModelInterface bill) {
        if (bill == null) {
            throw new NullPointerException();
        }

        List<ProductDetailModelInterface> ret = new ArrayList<>();

        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_BILL_DETAIL);
            bill.setKeyArg(1, BillModel.ID_HEADER, callableStatement);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ProductDetailModelInterface productDetail = new ProductDetailModel();
                ProductSimpleModelInterface product = new ProductSimpleModel();
                product.setProductID(resultSet.getString(ProductSimpleModel.ID_HEADER));
                product.setName(resultSet.getString(ProductSimpleModel.NAME_HEADER));
                product.setSize(ProductSize.getProductSizeFromString(resultSet.getString(ProductSimpleModel.SIZE_HEADER)));
                product.setPrice(resultSet.getLong(ProductSimpleModel.PRICE_HEADER));
                productDetail.setProduct(product);
                productDetail.setAmount(resultSet.getInt(ProductDetailModel.AMOUNT_HEADER));
                ret.add(productDetail);
            }
            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(BillHistoryModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret.iterator();
    }

}
