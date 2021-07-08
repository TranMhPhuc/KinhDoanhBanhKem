package model.product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductSimpleModel implements ProductSimpleModelInterface {

    public static final String TABLE_NAME = "SanPham";
    public static final String ID_HEADER = "MaSP";
    public static final String NAME_HEADER = "TenSP";
    public static final String SIZE_HEADER = "KichThuoc";
    public static final String AMOUNT_HEADER = "SoLuong";
    public static final String COST_HEADER = "GiaGoc";
    public static final String PRICE_HEADER = "GiaBan";

    private int id;
    private String name;
    private ProductSize size;
    private int amount;
    private long price;
    private long cost;

    @Override
    public void setProductID(String id) {
        this.id = Integer.parseInt(id);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSize(ProductSize size) {
        this.size = size;
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
    public String getProductIDText() {
        return String.valueOf(this.id);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ProductSize getSize() {
        return this.size;
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
    public void setProperty(ResultSet resultSet) {
        if (resultSet == null) {
            throw new NullPointerException();
        }
        try {
            this.id = resultSet.getInt(ProductSimpleModel.ID_HEADER);
            this.name = resultSet.getString(ProductSimpleModel.NAME_HEADER);
            this.size = ProductSize.getProductSizeFromString(resultSet.getString(ProductSimpleModel.SIZE_HEADER));
            this.amount = resultSet.getInt(ProductSimpleModel.AMOUNT_HEADER);
            this.cost = resultSet.getLong(ProductSimpleModel.COST_HEADER);
            this.price = resultSet.getLong(ProductSimpleModel.PRICE_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(ProductSimpleModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(ProductSimpleModel.ID_HEADER)) {
                preparedStatement.setInt(index, this.id);
            } else if (header.equals(ProductSimpleModel.NAME_HEADER)) {
                preparedStatement.setString(index, this.name);
            } else if (header.equals(ProductSimpleModel.SIZE_HEADER)) {
                preparedStatement.setString(index, this.size.toString());
            } else if (header.equals(ProductSimpleModel.AMOUNT_HEADER)) {
                preparedStatement.setInt(index, this.amount);
            } else if (header.equals(ProductSimpleModel.PRICE_HEADER)) {
                preparedStatement.setLong(index, this.price);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
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
        final ProductSimpleModel other = (ProductSimpleModel) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public void setCost(long cost) {
        this.cost = cost;
    }

    @Override
    public long getCost() {
        return this.cost;
    }

    @Override
    public long getProfit() {
        return this.price - this.cost;
    }
}
