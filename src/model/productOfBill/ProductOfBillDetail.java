package model.productOfBill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bill.BillDataStorage;
import model.bill.BillModel;
import model.bill.BillModelInterface;
import model.product.ProductDataStorage;
import model.product.ProductModel;
import model.product.ProductModelInterface;

public class ProductOfBillDetail implements ProductOfBillDetailInterface {
    
    public static final String TABLE_NAME = "ChiTietHoaDon";
    public static final String BILL_ID_HEADER = BillModel.ID_HEADER;
    public static final String PRODUCT_ID_HEADER = ProductModel.ID_HEADER;
    public static final String AMOUNT_HEADER = "SoLuong";
    public static final String PRICE_HEADER = "ThanhTien";
    
    private static BillDataStorage billDataStorage;
    private static ProductDataStorage productDataStorage;
    
    private BillModelInterface bill;
    private ProductModelInterface product;
    private int amount;
    private int price;
    
    static {
        billDataStorage = BillDataStorage.getInstance();
        productDataStorage = ProductDataStorage.getInstance();
    }

    public ProductOfBillDetail() {
    }
    
    public static ProductOfBillDetail getInstance(ResultSet resultSet) {
        ProductOfBillDetail ret = new ProductOfBillDetail();
        try {
            ret.bill = billDataStorage.getBill(resultSet.getString(BILL_ID_HEADER));
            ret.bill.addProductDetail(ret);
            ret.product = productDataStorage.getProduct(resultSet.getString(PRODUCT_ID_HEADER));
            ret.product.addBillDetail(ret);
            ret.amount = resultSet.getInt(AMOUNT_HEADER);
            ret.price = resultSet.getInt(PRICE_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(ProductOfBillDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
