package model.ingredient;

import model.ingredient.importDetail.IngredientImportDetail;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ingredient.ingredientType.IngredientTypeDataStorage;
import model.ingredient.ingredientType.IngredientTypeModelInterface;
import model.ingredient.ingredientUnit.IngredientUnitDataStorage;
import model.ingredient.ingredientUnit.IngredientUnitModelInterface;
import model.ingredientOfProduct.IngredientOfProductDetailInterface;
import model.provider.ProviderDataStorage;
import model.provider.ProviderModelInterface;
import util.db.SQLServerConnection;

public class IngredientModel implements IngredientModelInterface {

    public static final String TABLE_NAME = "NguyenLieu";
    public static final String ID_HEADER = "MaNguyenLieu";
    public static final String NAME_HEADER = "TenNguyenLieu";
    public static final String TYPE_HEADER = "MaLoai";
    public static final String COST_HEADER = "Gia";
    public static final String AMOUNT_HEADER = "MaNguyenLieu";
    public static final String PROVIDER_ID_HEADER = "MaNCC";
    public static final String UNIT_ID_HEADER = "MaDonVi";

    private static Connection dbConnection;
    private static IngredientTypeDataStorage ingredientTypeDataStorage;
    private static ProviderDataStorage providerDataStorage;
    private static IngredientUnitDataStorage ingredientUnitDataStorage;

    private int id;
    private String name;
    private IngredientTypeModelInterface type;
    private int cost;
    private float amount;
    private ProviderModelInterface provider;
    private IngredientUnitModelInterface unit;
    private ArrayList<IngredientOfProductDetailInterface> productDetails;

    static {
        dbConnection = SQLServerConnection.getConnection();
        ingredientTypeDataStorage = IngredientTypeDataStorage.getInstance();
        providerDataStorage = ProviderDataStorage.getInstance();
        ingredientUnitDataStorage = IngredientUnitDataStorage.getInstance();
    }

    public IngredientModel() {
        productDetails = new ArrayList<>();
    }

    public IngredientModel(int id, String name, IngredientTypeModelInterface type,
            int cost, float amount, ProviderModelInterface provider,
            IngredientUnitModelInterface unit,
            ArrayList<IngredientOfProductDetailInterface> productDetails) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.amount = amount;
        this.provider = provider;
        this.unit = unit;
        this.productDetails = productDetails;
    }

    public static IngredientModelInterface getInstance(ResultSet resultSet) {
        IngredientModel ret = new IngredientModel();
        try {
            ret.id = resultSet.getInt(ID_HEADER);
            ret.name = resultSet.getString(NAME_HEADER);
            ret.type = ingredientTypeDataStorage.getIngredientType(resultSet.getString(TYPE_HEADER));
            ret.cost = resultSet.getInt(COST_HEADER);
            ret.amount = resultSet.getInt(AMOUNT_HEADER);
            ret.provider = providerDataStorage.getProvider(resultSet.getString(PROVIDER_ID_HEADER));
            ret.unit = ingredientUnitDataStorage.getIngredientUnit(resultSet.getString(UNIT_ID_HEADER));

        } catch (SQLException ex) {
            Logger.getLogger(IngredientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public String getIngredientIDText() {
        return String.valueOf(this.id);
    }

    @Override
    public void addProductDetail(IngredientOfProductDetailInterface ingredientOfProductDetailInterface) {
        this.productDetails.add(ingredientOfProductDetailInterface);
    }

    @Override
    public String toString() {
        return "IngredientModel{" + "id=" + id + ", name=" + name + ", type="
                + type + ", cost=" + cost + ", amount=" + amount + ", provider="
                + provider + ", unit=" + unit + '}';
    }

}
