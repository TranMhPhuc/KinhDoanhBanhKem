package model.product.ingredientDetail;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ingredient.IngredientModel;
import model.product.ProductModel;
import model.product.ProductModelInterface;
import model.product.ProductSimpleModel;

public class IngredientDetailModel implements IngredientDetailModelInterface {

    public static final String TABLE_NAME = "ThanhPhan";
    public static final String PRODUCT_ID_HEADER = ProductSimpleModel.ID_HEADER;
    public static final String INGREDIENT_NAME_HEADER = IngredientModel.NAME_HEADER;
    public static final String INGREDIENT_TYPE_NAME_HEADER = IngredientModel.TYPE_NAME_HEADER;
    public static final String AMOUNT_HEADER = "SoLuong";
    public static final String UNIT_NAME_HEADER = "TenDonVi";

    private static final String SP_INSERT
            = "{call insert_ThanhPhan(?, ?, ?, ?)}";

    private static final String SP_UPDATE
            = "{call update_ThanhPhan(?, ?, ?, ?)}";

    private static final String SP_DELETE
            = "{call delete_ThanhPhan(?, ?)}";

    private static final String SP_GET_TYPE_NAME
            = "{call get_ingredient_type_name(?)}";

    private ProductModelInterface product;
    private String ingredientName;
    private String ingredientTypeName;
    private float amount;
    private String unitName;

    public IngredientDetailModel() {
    }

    @Override
    public void setProduct(ProductModelInterface product) {
        this.product = product;
    }

    @Override
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_TYPE_NAME);
            callableStatement.setString(1, this.ingredientName);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                this.ingredientTypeName = resultSet.getString(1);
            }
            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public ProductModelInterface getProduct() {
        return this.product;
    }

    @Override
    public String getIngredientName() {
        return this.ingredientName;
    }

    @Override
    public float getAmount() {
        return this.amount;
    }

    @Override
    public String getUnitName() {
        return this.unitName;
    }

    @Override
    public String getIngredientTypeName() {
        return this.ingredientTypeName;
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.ingredientName = resultSet.getString(INGREDIENT_NAME_HEADER);
            this.ingredientTypeName = resultSet.getString(INGREDIENT_TYPE_NAME_HEADER);
            this.amount = resultSet.getFloat(AMOUNT_HEADER);
            this.unitName = resultSet.getString(UNIT_NAME_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(IngredientDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_INSERT);

            this.product.setKeyArg(1, ProductSimpleModel.ID_HEADER, callableStatement);
            callableStatement.setString(2, this.ingredientName);
            callableStatement.setFloat(3, this.amount);
            callableStatement.setString(4, this.unitName);

            callableStatement.execute();
            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(IngredientDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_DELETE);

            this.product.setKeyArg(1, ProductSimpleModel.ID_HEADER, callableStatement);
            callableStatement.setString(2, this.ingredientName);

            callableStatement.execute();
            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(IngredientDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateInDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_UPDATE);

            this.product.setKeyArg(1, ProductSimpleModel.ID_HEADER, callableStatement);
            callableStatement.setString(2, this.ingredientName);
            callableStatement.setFloat(3, this.amount);
            callableStatement.setString(4, this.unitName);

            callableStatement.execute();
            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(IngredientDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(AMOUNT_HEADER)) {
                preparedStatement.setFloat(index, this.amount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngredientDetailModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.product);
        hash = 41 * hash + Objects.hashCode(this.ingredientName);
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
        final IngredientDetailModel other = (IngredientDetailModel) obj;
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.ingredientName, other.ingredientName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IngredientOfProductModel{" + "productID=" + product.getProductIDText()
                + ", ingredientName=" + ingredientName
                + ", amount=" + amount + ", unitName=" + unitName + '}';
    }

    @Override
    public int compareTo(IngredientDetailModelInterface o) {
        if (!this.product.equals(o.getProduct()) && !this.ingredientName.equals(o.getIngredientName())) {
            throw new IllegalArgumentException("Two comparing ingredient detail having not same id.");
        }
        if ((this.amount != o.getAmount())
                || (this.unitName.compareTo(o.getUnitName())) != 0) {
            return 1;
        }
        return 0;
    }

}
