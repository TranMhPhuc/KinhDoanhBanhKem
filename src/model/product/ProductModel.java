package model.product;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.product.ingredientDetail.IngredientDetailModel;
import model.product.ingredientDetail.IngredientDetailModelInterface;

public class ProductModel implements ProductModelInterface {

    public static final String COST_HEADER = "GiaGoc";

    public static final int DEFAULT_INIT_AMOUNT = 0;

    private static final String SP_INSERT = "{call insert_SanPham(?, ?, ?, ?)}";

    private static final String SP_UPDATE = "{call update_SanPham(?, ?, ?, ?, ?)}";

    private static final String SP_GET_ALL_INGREDIENT_DETAIL
            = "{call get_all_ingredient_detail_of_product(?)}";

    private static final String SP_DELETE = "{call delete_SanPham(?)}";

    private ProductSimpleModelInterface productSimpleModel;

    private long cost;

    private List<IngredientDetailModelInterface> ingredientDetails;

    public ProductModel() {
        productSimpleModel = new ProductSimpleModel();
        productSimpleModel.setAmount(DEFAULT_INIT_AMOUNT);
        ingredientDetails = new ArrayList<>();
    }

    @Override
    public String getProductIDText() {
        return productSimpleModel.getProductIDText();
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        if (resultSet == null) {
            throw new NullPointerException();
        }
        try {
            productSimpleModel.setProperty(resultSet);
            this.cost = resultSet.getLong(COST_HEADER);

            ingredientDetails.clear();

            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_ALL_INGREDIENT_DETAIL);
            productSimpleModel.setKeyArg(1, ProductSimpleModel.ID_HEADER, callableStatement);
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
            productSimpleModel.setKeyArg(1, ProductSimpleModel.ID_HEADER, callableStatement);
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

            productSimpleModel.setKeyArg(1, ProductSimpleModel.NAME_HEADER, callableStatement);
            productSimpleModel.setKeyArg(2, ProductSimpleModel.SIZE_HEADER, callableStatement);
            callableStatement.setLong(3, this.cost);
            productSimpleModel.setKeyArg(4, ProductSimpleModel.PRICE_HEADER, callableStatement);

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

            productSimpleModel.setKeyArg(1, ProductSimpleModel.ID_HEADER, callableStatement);

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

            productSimpleModel.setKeyArg(1, ProductSimpleModel.ID_HEADER, callableStatement);
            productSimpleModel.setKeyArg(2, ProductSimpleModel.NAME_HEADER, callableStatement);
            productSimpleModel.setKeyArg(3, ProductSimpleModel.SIZE_HEADER, callableStatement);
            callableStatement.setLong(4, this.cost);
            productSimpleModel.setKeyArg(5, ProductSimpleModel.PRICE_HEADER, callableStatement);

            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(ProductSimpleModel.ID_HEADER)) {
                productSimpleModel.setKeyArg(index, header, preparedStatement);
            } else if (header.equals(ProductSimpleModel.NAME_HEADER)) {
                productSimpleModel.setKeyArg(index, header, preparedStatement);
            } else if (header.equals(ProductSimpleModel.SIZE_HEADER)) {
                productSimpleModel.setKeyArg(index, header, preparedStatement);
            } else if (header.equals(COST_HEADER)) {
                preparedStatement.setLong(index, this.cost);
            } else if (header.equals(ProductSimpleModel.AMOUNT_HEADER)) {
                productSimpleModel.setKeyArg(index, header, preparedStatement);
            } else if (header.equals(ProductSimpleModel.PRICE_HEADER)) {
                productSimpleModel.setKeyArg(index, header, preparedStatement);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setProductID(String id) {
        productSimpleModel.setProductID(id);
    }

    @Override
    public void setName(String name) {
        productSimpleModel.setName(name);
    }

    @Override
    public void setAmount(int amount) {
        productSimpleModel.setAmount(amount);
    }

    @Override
    public void setSize(ProductSize size) {
        productSimpleModel.setSize(size);
    }

    @Override
    public void setCost(long cost) {
        this.cost = cost;
    }

    @Override
    public void setPrice(long price) {
        productSimpleModel.setPrice(price);
    }

    @Override
    public String getName() {
        return productSimpleModel.getName();
    }

    @Override
    public ProductSize getSize() {
        return productSimpleModel.getSize();
    }

    @Override
    public long getCost() {
        return this.cost;
    }

    @Override
    public int getAmount() {
        return productSimpleModel.getAmount();
    }

    @Override
    public long getPrice() {
        return productSimpleModel.getPrice();
    }

    @Override
    public void addIngredientDetail(IngredientDetailModelInterface ingredientDetail) {
        if (ingredientDetail == null) {
            throw new NullPointerException();
        }
        int id = this.ingredientDetails.indexOf(ingredientDetail);
        if (id != -1) {
            throw new IllegalArgumentException("Ingredient detail already exists.");
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
            throw new IllegalArgumentException("Ingredient detail doesn't exist.");
        }
        if (ingredientDetails.get(id).compareTo(ingredientDetail) != 0) {
            ingredientDetails.set(id, ingredientDetail);
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
            throw new IllegalArgumentException("Ingredient detail doesn't exist.");
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
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.productSimpleModel);
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
        if (!Objects.equals(this.productSimpleModel, other.productSimpleModel)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProductModel{" + "productSimpleModel=" + productSimpleModel
                + ", cost=" + cost + ", ingredientDetails=" + ingredientDetails + '}';
    }

}
