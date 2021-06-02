package model.ingredientOfProduct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ingredient.IngredientDataStorage;
import model.ingredient.IngredientModel;
import model.ingredient.IngredientModelInterface;
import model.ingredient.ingredientUnit.IngredientUnitDataStorage;
import model.ingredient.ingredientUnit.IngredientUnitModel;
import model.ingredient.ingredientUnit.IngredientUnitModelInterface;
import model.product.ProductDataStorage;
import model.product.ProductModel;
import model.product.ProductModelInterface;

public class IngredientOfProductDetail implements IngredientOfProductDetailInterface {
    
    public static final String TABLE_NAME = "ThanhPhan";
    public static final String PRODUCT_ID_HEADER = ProductModel.ID_HEADER;
    public static final String INGREDIENT_ID_HEADER = IngredientModel.ID_HEADER;
    public static final String AMOUNT_HEADER = "SoLuong";
    public static final String UNIT_HEADER = IngredientUnitModel.ID_HEADER;
    
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

    public IngredientOfProductDetail() {
    }
    
    public static IngredientOfProductDetailInterface getInstance(ResultSet resultSet) {
        IngredientOfProductDetail ret = new IngredientOfProductDetail();
        try {
            ret.product = productDataStorage.getProduct(resultSet.getString(PRODUCT_ID_HEADER));
            ret.product.addIngredientDetail(ret);
            ret.ingredient = ingredientDataStorage.getIngredient(resultSet.getString(INGREDIENT_ID_HEADER));
            ret.ingredient.addProductDetail(ret);
            ret.amount = resultSet.getInt(AMOUNT_HEADER);
            ret.unit = ingredientUnitDataStorage.getIngredientUnit(resultSet.getString(UNIT_HEADER));
        } catch (SQLException ex) {
            Logger.getLogger(IngredientOfProductDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
}
