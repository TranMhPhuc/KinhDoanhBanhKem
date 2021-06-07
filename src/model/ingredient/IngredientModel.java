package model.ingredient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ingredient.type.IngredientTypeDataStorage;
import model.ingredient.type.IngredientTypeModel;
import model.ingredient.type.IngredientTypeModelInterface;
import model.ingredient.unit.IngredientUnitDataStorage;
import model.ingredient.unit.IngredientUnitModel;
import model.ingredient.unit.IngredientUnitModelInterface;
import model.provider.ProviderDataStorage;
import model.provider.ProviderModel;
import model.provider.ProviderModelInterface;

public class IngredientModel implements IngredientModelInterface {

    public static final String TABLE_NAME = "NguyenLieu";
    public static final String ID_HEADER = "MaNguyenLieu";
    public static final String NAME_HEADER = "TenNguyenLieu";
    public static final String TYPE_HEADER = "MaLoai";
    public static final String COST_HEADER = "Gia";
    public static final String AMOUNT_HEADER = "TongSoLuong";
    public static final String PROVIDER_ID_HEADER = "MaNCC";
    public static final String UNIT_ID_HEADER = "MaDonVi";

    private static final String INSERT_QUERY_PROTOTYPE
            = "INSERT INTO " + TABLE_NAME + " ("
            + NAME_HEADER + ", " + TYPE_HEADER + ", "
            + COST_HEADER + ", " + AMOUNT_HEADER + ", " + PROVIDER_ID_HEADER + ", "
            + UNIT_ID_HEADER + ")"
            + " VALUES (?, ?, ?, ?, ?, ?)";
    
    private static final String UPDATE_QUERY_PROTOTYPE
            = "UPDATE " + TABLE_NAME
            + " SET " + NAME_HEADER + " = ?, " + TYPE_HEADER + " = ?, "
            + COST_HEADER + " = ?, " + AMOUNT_HEADER + " = ?, " + PROVIDER_ID_HEADER + " = ?, "
            + UNIT_ID_HEADER + " = ?"
            + " WHERE " + ID_HEADER + " = ?";

    private static final String DELETE_QUERY_PROTOTYPE
            = "DELETE FROM " + TABLE_NAME
            + " WHERE " + ID_HEADER + " = ?";

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

    static {
        ingredientTypeDataStorage = IngredientTypeDataStorage.getInstance();
        providerDataStorage = ProviderDataStorage.getInstance();
        ingredientUnitDataStorage = IngredientUnitDataStorage.getInstance();
    }

    public IngredientModel() {
    }

    @Override
    public String getIngredientIDText() {
        return String.valueOf(this.id);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setIngredientType(IngredientTypeModelInterface type) {
        this.type = type;
    }

    @Override
    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public void setProvider(ProviderModelInterface provider) {
        this.provider = provider;
    }

    @Override
    public void setIngredientUnit(IngredientUnitModelInterface unit) {
        this.unit = unit;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IngredientTypeModelInterface getIngredientType() {
        return this.type;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public float getAmount() {
        return this.amount;
    }

    @Override
    public ProviderModelInterface getProvider() {
        return this.provider;
    }

    @Override
    public IngredientUnitModelInterface getIngredientUnit() {
        return this.unit;
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt(ID_HEADER);
            this.name = resultSet.getString(NAME_HEADER);
            this.type = ingredientTypeDataStorage.getIngredientType(resultSet.getString(TYPE_HEADER));
            this.cost = resultSet.getInt(COST_HEADER);
            this.amount = resultSet.getInt(AMOUNT_HEADER);
            this.provider = providerDataStorage.getProviderByID(resultSet.getString(PROVIDER_ID_HEADER));
            this.unit = ingredientUnitDataStorage.getIngredientUnit(resultSet.getString(UNIT_ID_HEADER));
        } catch (SQLException ex) {
            Logger.getLogger(IngredientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(INSERT_QUERY_PROTOTYPE);

//            preparedStatement.setInt(1, this.id);
            preparedStatement.setString(1, this.name);
            this.type.setKeyArg(2, IngredientTypeModel.ID_HEADER, preparedStatement);
            preparedStatement.setInt(3, this.cost);
            preparedStatement.setFloat(4, this.amount);
            this.provider.setKeyArg(5, ProviderModel.ID_HEADER, preparedStatement);
            this.unit.setKeyArg(6, IngredientUnitModel.ID_HEADER, preparedStatement);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientModel.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(IngredientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateInDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(UPDATE_QUERY_PROTOTYPE);

            preparedStatement.setString(1, this.name);
            this.type.setKeyArg(2, IngredientUnitModel.ID_HEADER, preparedStatement);
            preparedStatement.setInt(3, this.cost);
            preparedStatement.setFloat(4, this.amount);
            this.provider.setKeyArg(5, ProviderModel.ID_HEADER, preparedStatement);
            this.unit.setKeyArg(6, IngredientUnitModel.ID_HEADER, preparedStatement);
            preparedStatement.setInt(7, this.id);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(ID_HEADER)) {
                preparedStatement.setInt(index, this.id);
            } else if (header.equals(NAME_HEADER)) {
                preparedStatement.setString(index, this.name);
            } else if (header.equals(TYPE_HEADER)) {
                this.type.setKeyArg(index, IngredientModel.ID_HEADER, preparedStatement);
            } else if (header.equals(COST_HEADER)) {
                preparedStatement.setInt(index, this.cost);
            } else if (header.equals(AMOUNT_HEADER)) {
                preparedStatement.setFloat(index, this.amount);
            } else if (header.equals(PROVIDER_ID_HEADER)) {
                preparedStatement.setString(index, this.name);
            } else if (header.equals(UNIT_ID_HEADER)) {
                preparedStatement.setString(index, this.name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngredientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
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
        final IngredientModel other = (IngredientModel) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IngredientModel{" + "id=" + id + ", name=" + name + ", type="
                + type + ", cost=" + cost + ", amount=" + amount + ", provider="
                + provider + ", unit=" + unit + '}';
    }

}
