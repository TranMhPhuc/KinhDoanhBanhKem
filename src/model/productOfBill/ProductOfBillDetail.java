package model.productOfBill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.product.ProductDataStorage;
import model.product.ProductModel;
import model.product.ProductModelInterface;

public class ProductOfBillDetail implements ProductOfBillDetailInterface {
    
    public static final String TABLE_NAME = "ChiTietHoaDon";
    public static final String PRODUCT_ID_HEADER = ProductModel.ID_HEADER;
    public static final String AMOUNT_HEADER = "SoLuong";
    public static final String PRICE_HEADER = "ThanhTien";
    
    private static ProductDataStorage productDataStorage;
    
    private ProductModelInterface product;
    private int amount;
    private int price;
    
    static {
        productDataStorage = ProductDataStorage.getInstance();
    }

    public ProductOfBillDetail() {
    }
    
    public static ProductOfBillDetail getInstance(ResultSet resultSet) {
        ProductOfBillDetail ret = new ProductOfBillDetail();
        try {
            ret.product = productDataStorage.getProduct(resultSet.getString(PRODUCT_ID_HEADER));
            ret.amount = resultSet.getInt(AMOUNT_HEADER);
            ret.price = resultSet.getInt(PRICE_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(ProductOfBillDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
