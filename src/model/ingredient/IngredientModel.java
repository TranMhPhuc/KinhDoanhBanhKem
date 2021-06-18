package model.ingredient;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ingredient.type.IngredientTypeModel;
import model.provider.ProviderModel;

public class IngredientModel implements IngredientModelInterface {

    public static final String TABLE_NAME = "NguyenLieu";
    public static final String ID_HEADER = "MaNguyenLieu";
    public static final String NAME_HEADER = "TenNguyenLieu";
    public static final String TYPE_NAME_HEADER = IngredientTypeModel.NAME_HEADER;
    public static final String COST_HEADER = "Gia";
    public static final String AMOUNT_HEADER = "TongSoLuong";
    public static final String PROVIDER_NAME_HEADER = ProviderModel.NAME_HEADER;
    public static final String UNIT_NAME_HEADER = "TenDonVi";

    public static final float DEFAULT_INIT_AMOUNT = 0f;

    private static final String SP_INSERT = "{call insert_NguyenLieu(?, ?, ?, ?, ?)}";

    private static final String SP_UPDATE = "{call update_NguyenLieu(?, ?, ?, ?, ?, ?, ?)}";

    private static final String SP_DELETE = "{call delete_NguyenLieu(?)}";

    private int id;
    private String name;
    private String typeName;
    private long cost;
    private float amount;
    private String providerName;
    private String unitName;

    public IngredientModel() {
        this.amount = DEFAULT_INIT_AMOUNT;
    }

    @Override
    public void setIngredientID(String ingredientIDText) {
        this.id = Integer.parseInt(ingredientIDText);
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
    public void setIngredientTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public void setCost(long cost) {
        this.cost = cost;
    }

    @Override
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }

    @Override
    public long getCost() {
        return this.cost;
    }

    @Override
    public float getAmount() {
        return this.amount;
    }

    @Override
    public String getProviderName() {
        return this.providerName;
    }

    @Override
    public String getUnitName() {
        return this.unitName;
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt(ID_HEADER);
            this.name = resultSet.getString(NAME_HEADER);
            this.typeName = resultSet.getString(TYPE_NAME_HEADER);
            this.cost = resultSet.getInt(COST_HEADER);
            this.amount = resultSet.getInt(AMOUNT_HEADER);
            this.providerName = resultSet.getString(PROVIDER_NAME_HEADER);
            this.unitName = resultSet.getString(UNIT_NAME_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(IngredientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_INSERT);

            callableStatement.setString(1, this.name);
            callableStatement.setString(2, this.typeName);
            callableStatement.setLong(3, this.cost);
            callableStatement.setString(4, this.providerName);
            callableStatement.setString(5, this.unitName);

            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_DELETE);

            callableStatement.setInt(1, this.id);

            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateInDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_UPDATE);

            callableStatement.setInt(1, this.id);
            callableStatement.setString(2, this.name);
            callableStatement.setString(3, this.typeName);
            callableStatement.setLong(4, this.cost);
            callableStatement.setFloat(5, this.amount);
            callableStatement.setString(6, this.providerName);
            callableStatement.setString(7, this.unitName);

            callableStatement.execute();
            callableStatement.close();
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
            } else if (header.equals(COST_HEADER)) {
                preparedStatement.setLong(index, this.cost);
            } else if (header.equals(AMOUNT_HEADER)) {
                preparedStatement.setFloat(index, this.amount);
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
                + typeName + ", cost=" + cost + ", amount=" + amount + ", provider="
                + providerName + ", unit=" + unitName + '}';
    }
}
