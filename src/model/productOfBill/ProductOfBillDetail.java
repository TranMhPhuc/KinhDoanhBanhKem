package model.productOfBill;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static final String INSERT_QUERY_PROTOTYPE
            = "INSERT INTO " + TABLE_NAME + " ("
            + BILL_ID_HEADER + ", " + PRODUCT_ID_HEADER + ", " + AMOUNT_HEADER + ", "
            + PRICE_HEADER + ")"
            + " VALUES (?, ?, ?, ?)";

    private static ProductDataStorage productDataStorage;

    private BillModelInterface bill;
    private ProductModelInterface product;
    private int amount;
    private int price;

    static {
        productDataStorage = ProductDataStorage.getInstance();
    }

    public ProductOfBillDetail() {
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.product = productDataStorage.getProduct(resultSet.getString(PRODUCT_ID_HEADER));
            this.amount = resultSet.getInt(AMOUNT_HEADER);
            this.price = resultSet.getInt(PRICE_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(ProductOfBillDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(INSERT_QUERY_PROTOTYPE);

            this.bill.setKeyArg(1, BillModel.ID_HEADER, preparedStatement);
            this.product.setKeyArg(2, ProductModel.ID_HEADER, preparedStatement);
            preparedStatement.setInt(3, this.amount);
            preparedStatement.setInt(4, this.price);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductOfBillDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateInDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(BILL_ID_HEADER)) {
                this.bill.setKeyArg(index, BillModel.ID_HEADER, preparedStatement);
            } else if (header.equals(PRODUCT_ID_HEADER)) {
                this.product.setKeyArg(index, PRODUCT_ID_HEADER, preparedStatement);
            } else if (header.equals(AMOUNT_HEADER)) {
                preparedStatement.setInt(index, this.amount);
            } else if (header.equals(PRICE_HEADER)) {
                preparedStatement.setInt(index, this.amount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductOfBillDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setBill(BillModelInterface bill) {
        this.bill = bill;
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
        final ProductOfBillDetail other = (ProductOfBillDetail) obj;
        if (!Objects.equals(this.bill, other.bill)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return true;
    }

}
