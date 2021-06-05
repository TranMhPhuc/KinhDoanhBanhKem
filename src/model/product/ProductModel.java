package model.product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ingredientOfProduct.IngredientOfProductModelInterface;

public class ProductModel implements ProductModelInterface {

    public static final String TABLE_NAME = "SanPham";
    public static final String ID_HEADER = "MaSP";
    public static final String NAME_HEADER = "TenSP";
    public static final String SIZE_HEADER = "KichThuoc";
    public static final String COST_HEADER = "GiaGoc";
    public static final String AMOUNT_HEADER = "SoLuong";
    public static final String PRICE_HEADER = "GiaBan";

    private static final String INSERT_QUERY_PROTOTYPE
            = "INSERT INTO " + TABLE_NAME + " ("
            + ID_HEADER + ", " + NAME_HEADER + ", " + SIZE_HEADER + ", "
            + COST_HEADER + ", " + AMOUNT_HEADER + ", " + PRICE_HEADER + ")"
            + " VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY_PROTOTYPE
            = "UPDATE " + TABLE_NAME
            + " SET " + NAME_HEADER + " = ?, " + SIZE_HEADER + " = ?, "
            + COST_HEADER + " = ?, " + AMOUNT_HEADER + " = ?, " + PRICE_HEADER
            + " = ?"
            + " WHERE " + ID_HEADER + " = ?";

    private static final String DELETE_QUERY_PROTOTYPE
            = "DELETE FROM " + TABLE_NAME
            + " WHERE " + ID_HEADER + " = ?";

    private int id;
    private String name;
    private String size;
    private int cost;
    private int amount;
    private int price;
    private ArrayList<IngredientOfProductModelInterface> ingredientDetails;

    public ProductModel() {
        ingredientDetails = new ArrayList<>();
    }

    @Override
    public String getProductIDText() {
        return String.valueOf(this.id);
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt(ID_HEADER);
            this.name = resultSet.getString(NAME_HEADER);
            this.size = resultSet.getString(SIZE_HEADER);
            this.cost = resultSet.getInt(COST_HEADER);
            this.amount = resultSet.getInt(AMOUNT_HEADER);
            this.price = resultSet.getInt(PRICE_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(INSERT_QUERY_PROTOTYPE);

            preparedStatement.setInt(1, this.id);
            preparedStatement.setString(2, this.name);
            preparedStatement.setString(3, this.size);
            preparedStatement.setInt(4, this.cost);
            preparedStatement.setInt(5, this.amount);
            preparedStatement.setInt(6, this.price);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(DELETE_QUERY_PROTOTYPE);
            preparedStatement.setInt(1, this.id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void updateInDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(UPDATE_QUERY_PROTOTYPE);

            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.size);
            preparedStatement.setInt(3, this.cost);
            preparedStatement.setInt(4, this.amount);
            preparedStatement.setInt(5, this.price);
            preparedStatement.setInt(6, this.id);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(ID_HEADER)) {
                preparedStatement.setInt(index, this.id);
            } else if (header.equals(NAME_HEADER)) {
                preparedStatement.setString(index, this.name);
            } else if (header.equals(SIZE_HEADER)) {
                preparedStatement.setString(index, this.size);
            } else if (header.equals(COST_HEADER)) {
                preparedStatement.setInt(index, this.cost);
            } else if (header.equals(AMOUNT_HEADER)) {
                preparedStatement.setInt(index, this.amount);
            } else if (header.equals(PRICE_HEADER)) {
                preparedStatement.setInt(index, this.price);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.id;
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
        final ProductModel other = (ProductModel) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public void setString(String name) {
        this.name = name;
    }

    @Override
    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSize() {
        return this.size;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return "ProductModel{" + "id=" + id + ", name=" + name + ", cost=" + cost
                + ", price=" + price + ", amount=" + amount + ", size=" + size + '}';
    }
}
