package model.bill.detail;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bill.BillModel;
import model.bill.BillModelInterface;
import model.product.ProductSimpleModel;
import model.product.ProductSimpleModelInterface;

public class ProductDetailModel implements ProductDetailModelInterface {

    public static final String TABLE_NAME = "ChiTietHoaDon";
    public static final String BILL_ID_HEADER = BillModel.ID_HEADER;
    public static final String PRODUCT_ID_HEADER = ProductSimpleModel.ID_HEADER;
    public static final String AMOUNT_HEADER = "SoLuong";

    private static final int INIT_DETAIL_AMOUNT = 1;
    
    private static final String SP_INSERT = "{call insert_ChiTietHoaDon(?, ?, ?)}";

    private BillModelInterface bill;
    private ProductSimpleModelInterface product;
    private int amount;

    public ProductDetailModel() {
        this.amount = INIT_DETAIL_AMOUNT;
    }

    @Override
    public void setProperty(ResultSet resultSet) {
    }

    @Override
    public void insertToDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_INSERT);

            this.bill.setKeyArg(1, BillModel.ID_HEADER, callableStatement);
            this.product.setKeyArg(2, ProductSimpleModel.ID_HEADER, callableStatement);
            callableStatement.setInt(3, this.amount);

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
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setProduct(ProductSimpleModelInterface product) {
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
    public ProductSimpleModelInterface getProduct() {
        return this.product;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public long getPrice() {
        if (product == null) {
            throw new NullPointerException();
        }
        return this.product.getPrice() * this.amount;
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
