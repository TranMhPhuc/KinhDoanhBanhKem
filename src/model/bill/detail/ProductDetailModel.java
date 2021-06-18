package model.bill.detail;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bill.BillModel;
import model.product.ProductModel;
import model.product.ProductModelInterface;
import model.bill.BillModelInterface;
import model.product.ProductManageModel;
import model.product.ProductManageModelInterface;

public class ProductDetailModel implements ProductDetailModelInterface {

    public static final String TABLE_NAME = "ChiTietHoaDon";
    public static final String BILL_ID_HEADER = BillModel.ID_HEADER;
    public static final String PRODUCT_ID_HEADER = ProductModel.ID_HEADER;
    public static final String AMOUNT_HEADER = "SoLuong";
    public static final String PRICE_HEADER = "ThanhTien";

    private static final String SP_INSERT = "{call insert_ChiTietHoaDon(?, ?, ?, ?)}";

    private static ProductManageModelInterface productManageModel;
    
    private BillModelInterface bill;
    private ProductModelInterface product;
    private int amount;
    private long price;

    public ProductDetailModel() {
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.product = productManageModel.getProductByID(resultSet.getString(PRODUCT_ID_HEADER));
            this.amount = resultSet.getInt(AMOUNT_HEADER);
            this.price = resultSet.getInt(PRICE_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_INSERT);

            this.bill.setKeyArg(1, BillModel.ID_HEADER, callableStatement);
            this.product.setKeyArg(2, ProductModel.ID_HEADER, callableStatement);
            callableStatement.setInt(3, this.amount);
            callableStatement.setLong(4, this.price);

            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
    }

    @Override
    public void updateInDatabase() {
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(AMOUNT_HEADER)) {
                preparedStatement.setInt(index, this.amount);
            } else if (header.equals(PRICE_HEADER)) {
                preparedStatement.setLong(index, this.price);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setProduct(ProductModelInterface product) {
        if (product == null) {
            throw new NullPointerException();
        }
        this.product = product;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public void setBill(BillModelInterface bill) {
        if (bill == null) {
            throw new NullPointerException();
        }
        this.bill = bill;
    }

    @Override
    public BillModelInterface getBill() {
        return this.bill;
    }

    @Override
    public ProductModelInterface getProduct() {
        return this.product;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public long getPrice() {
        return this.price;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.bill);
        hash = 79 * hash + Objects.hashCode(this.product);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductDetailModel other = (ProductDetailModel) obj;
        if (!Objects.equals(this.bill, other.bill)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return true;
    }

}
