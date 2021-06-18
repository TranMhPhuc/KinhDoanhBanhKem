package model.product;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ingredient.IngredientModelInterface;
import model.product.ingredientDetail.IngredientDetailModel;
import model.product.ingredientDetail.IngredientDetailModelInterface;

public class ProductModel implements ProductModelInterface {

    public static final String TABLE_NAME = "SanPham";
    public static final String ID_HEADER = "MaSP";
    public static final String NAME_HEADER = "TenSP";
    public static final String SIZE_HEADER = "KichThuoc";
    public static final String COST_HEADER = "GiaGoc";
    public static final String AMOUNT_HEADER = "SoLuong";
    public static final String PRICE_HEADER = "GiaBan";

    public static final int DEFAULT_INIT_AMOUNT = 0;

    private static final String SP_INSERT = "{call insert_SanPham(?, ?, ?, ?)}";

    private static final String SP_UPDATE = "{call update_SanPham(?, ?, ?, ?, ?)}";

    private static final String SP_GET_ALL_INGREDIENT_DETAIL
            = "{call get_all_ingredient_detail_of_product(?)}";

    private static final String SP_DELETE = "{call delete_SanPham(?)}";

    private int id;
    private String name;
    private ProductSize size;
    private long cost;
    private int amount;
    private long price;
    private List<IngredientDetailModelInterface> ingredientDetails;

    public ProductModel() {
        this.amount = DEFAULT_INIT_AMOUNT;
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
            this.size = ProductSize.getProductSizeFromString(resultSet.getString(SIZE_HEADER));
            this.cost = resultSet.getLong(COST_HEADER);
            this.amount = resultSet.getInt(AMOUNT_HEADER);
            this.price = resultSet.getLong(PRICE_HEADER);

            ingredientDetails.clear();

            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_ALL_INGREDIENT_DETAIL);
            callableStatement.setInt(1, this.id);
            ResultSet resultSet1 = callableStatement.executeQuery();
            while (resultSet1.next()) {
                IngredientDetailModelInterface ingredientDetail = new IngredientDetailModel();
                ingredientDetail.setProduct(this);
                ingredientDetail.setProperty(resultSet1);
                ingredientDetails.add(ingredientDetail);
            }

            resultSet1.close();
            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void reloadIngredientDetailList() {
        ingredientDetails.clear();
        
        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_ALL_INGREDIENT_DETAIL);
            callableStatement.setInt(1, this.id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                IngredientDetailModelInterface ingredientDetail = new IngredientDetailModel();
                ingredientDetail.setProduct(this);
                ingredientDetail.setProperty(resultSet);
                ingredientDetails.add(ingredientDetail);
            }
            
            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_INSERT);

            callableStatement.setString(1, this.name);
            callableStatement.setString(2, this.size.name());
            callableStatement.setLong(3, this.cost);
            callableStatement.setLong(4, this.price);

            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_DELETE);

            callableStatement.setInt(1, this.id);

            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateInDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_UPDATE);

            callableStatement.setInt(1, this.id);
            callableStatement.setString(2, this.name);
            callableStatement.setString(3, this.size.toString());
            callableStatement.setLong(4, this.cost);
            callableStatement.setLong(5, this.price);

            callableStatement.execute();
            callableStatement.close();
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
                preparedStatement.setString(index, this.size.toString());
            } else if (header.equals(COST_HEADER)) {
                preparedStatement.setLong(index, this.cost);
            } else if (header.equals(AMOUNT_HEADER)) {
                preparedStatement.setInt(index, this.amount);
            } else if (header.equals(PRICE_HEADER)) {
                preparedStatement.setLong(index, this.price);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setProductID(String id) {
        this.id = Integer.parseInt(id);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void setSize(ProductSize size) {
        this.size = size;
    }

    @Override
    public void setCost(long cost) {
        this.cost = cost;
    }

    @Override
    public void setPrice(long price) {
        this.price = price;
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
    public long getCost() {
        return this.cost;
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
        int hash = 5;
        hash = 31 * hash + this.id;
        return hash;
    }

    @Override
    public void addIngredientDetail(IngredientDetailModelInterface ingredientDetail) {
        if (ingredientDetail == null) {
            throw new NullPointerException();
        }
        int id = this.ingredientDetails.indexOf(ingredientDetail);
        if (id != -1) {
            throw new IllegalArgumentException("Ingredient detail is existed.");
        }
        this.ingredientDetails.add(ingredientDetail);
        ingredientDetail.insertToDatabase();
    }

    @Override
    public void updateIngredientDetail(IngredientDetailModelInterface ingredientDetail) {
        if (ingredientDetail == null) {
            throw new NullPointerException();
        }
        int id = this.ingredientDetails.indexOf(ingredientDetail);
        if (id == -1) {
            throw new IllegalArgumentException("Ingredient detail is not existed.");
        }
        if ((ingredientDetail instanceof IngredientDetailModel)
                && ingredientDetails.get(id).compareTo((IngredientDetailModel) ingredientDetail) != 0) {
            ingredientDetail.updateInDatabase();
        }
    }

    @Override
    public void removeIngredientDetail(IngredientDetailModelInterface ingredientDetail) {
        if (ingredientDetail == null) {
            throw new NullPointerException();
        }
        int id = this.ingredientDetails.indexOf(ingredientDetail);
        if (id == -1) {
            throw new IllegalArgumentException("Ingredient detail is not existed.");
        }
        ingredientDetail.deleteInDatabase();
        ingredientDetails.remove(id);
    }

    @Override
    public Iterator<IngredientDetailModelInterface> getAllIngredientDetail() {
        return ingredientDetails.iterator();
    }

    @Override
    public void removeAllIngredientDetail() {
        ingredientDetails.forEach(ingredientDetail -> {
            ingredientDetail.deleteInDatabase();
        });
        ingredientDetails.clear();
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
    public String toString() {
        return "ProductModel{" + "id=" + id + ", name=" + name + ", cost=" + cost
                + ", price=" + price + ", amount=" + amount + ", size=" + size + '}';
    }

}
