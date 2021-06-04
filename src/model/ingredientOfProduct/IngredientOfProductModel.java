package model.ingredientOfProduct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ingredient.IngredientDataStorage;
import model.ingredient.IngredientModel;
import model.ingredient.IngredientModelInterface;
import model.ingredient.unit.IngredientUnitDataStorage;
import model.ingredient.unit.IngredientUnitModel;
import model.ingredient.unit.IngredientUnitModelInterface;
import model.product.ProductDataStorage;
import model.product.ProductModel;
import model.product.ProductModelInterface;

public class IngredientOfProductModel implements IngredientOfProductModelInterface {

    public static final String TABLE_NAME = "ThanhPhan";
    public static final String PRODUCT_ID_HEADER = ProductModel.ID_HEADER;
    public static final String INGREDIENT_ID_HEADER = IngredientModel.ID_HEADER;
    public static final String AMOUNT_HEADER = "SoLuong";
    public static final String UNIT_HEADER = IngredientUnitModel.ID_HEADER;

    private static final String INSERT_QUERY_PROTOTYPE
            = "INSERT INTO " + TABLE_NAME + " ("
            + PRODUCT_ID_HEADER + ", " + INGREDIENT_ID_HEADER + ", "
            + AMOUNT_HEADER + ", " + UNIT_HEADER + ")"
            + " VALUES (?, ?, ?, ?)";

    private static final String UPDATE_QUERY_PROTOTYPE
            = "UPDATE " + TABLE_NAME + " SET "
            + AMOUNT_HEADER + " = ?, " + UNIT_HEADER + " = ?"
            + " WHERE " + PRODUCT_ID_HEADER + " = ? AND "
            + INGREDIENT_ID_HEADER + " = ?";

    private static final String DELETE_QUERY_PROTOTYPE
            = "DELETE FROM " + TABLE_NAME
            + " WHERE " + PRODUCT_ID_HEADER + " = ? AND "
            + INGREDIENT_ID_HEADER + " = ?";

    private static ProductDataStorage productDataStorage;
    private static IngredientDataStorage ingredientDataStorage;
    private static IngredientUnitDataStorage ingredientUnitDataStorage;

    private ProductModelInterface product;
    private IngredientModelInterface ingredient;
    private int amount;
    private IngredientUnitModelInterface unit;

    static {
        productDataStorage = ProductDataStorage.getInstance();
        ingredientDataStorage = IngredientDataStorage.getInstance();
        ingredientUnitDataStorage = IngredientUnitDataStorage.getInstance();
    }

    public IngredientOfProductModel() {
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.product = productDataStorage.getProduct(resultSet.getString(PRODUCT_ID_HEADER));
            this.ingredient = ingredientDataStorage.getIngredient(resultSet.getString(INGREDIENT_ID_HEADER));
            this.amount = resultSet.getInt(AMOUNT_HEADER);
            this.unit = ingredientUnitDataStorage.getIngredientUnit(resultSet.getString(UNIT_HEADER));
        } catch (SQLException ex) {
            Logger.getLogger(IngredientOfProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(INSERT_QUERY_PROTOTYPE);

            this.product.setKeyArg(1, ProductModel.ID_HEADER, preparedStatement);
            this.ingredient.setKeyArg(2, IngredientModel.ID_HEADER, preparedStatement);
            preparedStatement.setInt(3, this.amount);
            this.unit.setKeyArg(4, IngredientUnitModel.ID_HEADER, preparedStatement);
        } catch (SQLException ex) {
            Logger.getLogger(IngredientOfProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(DELETE_QUERY_PROTOTYPE);

            this.product.setKeyArg(1, ProductModel.ID_HEADER, preparedStatement);
            this.ingredient.setKeyArg(2, IngredientModel.ID_HEADER, preparedStatement);
        } catch (SQLException ex) {
            Logger.getLogger(IngredientOfProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateInDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(UPDATE_QUERY_PROTOTYPE);

            preparedStatement.setInt(1, this.amount);
            this.unit.setKeyArg(2, IngredientUnitModel.ID_HEADER, preparedStatement);
            this.product.setKeyArg(3, ProductModel.ID_HEADER, preparedStatement);
            this.ingredient.setKeyArg(4, IngredientModel.ID_HEADER, preparedStatement);
        } catch (SQLException ex) {
            Logger.getLogger(IngredientOfProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(PRODUCT_ID_HEADER)) {
                this.product.setKeyArg(index, ProductModel.ID_HEADER, preparedStatement);
            } else if (header.equals(INGREDIENT_ID_HEADER)) {
                this.ingredient.setKeyArg(index, IngredientModel.ID_HEADER, preparedStatement);
            } else if (header.equals(AMOUNT_HEADER)) {
                preparedStatement.setInt(index, this.amount);
            } else if (header.equals(UNIT_HEADER)) {
                this.unit.setKeyArg(index, IngredientUnitModel.ID_HEADER, preparedStatement);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngredientOfProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
