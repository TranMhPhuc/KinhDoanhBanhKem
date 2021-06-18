package model.ingredient.importDetail;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ingredient.IngredientModel;
import model.ingredient.IngredientModelInterface;

public class IngredientImportDetail implements IngredientImportDetailInterface {

    public static final String TABLE_NAME = "ChiTietNhapNL";
    public static final String INGREDIENT_NAME_HEADER = IngredientModel.NAME_HEADER;
    public static final String DATE_HEADER = "NgayGioNhap";
    public static final String AMOUNT_HEADER = "SoLuong";
    public static final String TOTAL_COST_HEADER = "GiaNhap";
    public static final String IMPORT_UNIT_NAME_HEADER = "TenDonVi";

    private static final String SP_INSERT = "{call insert_ChiTietNhapNL(?, ?, ?, ?)}";

    private String ingredientName;
    private Timestamp date;
    private int amount;
    private long totalCost;
    private String importUnitName;

    public IngredientImportDetail() {
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.ingredientName = resultSet.getString(INGREDIENT_NAME_HEADER);
            this.date = resultSet.getTimestamp(DATE_HEADER);
            this.amount = resultSet.getInt(AMOUNT_HEADER);
            this.totalCost = resultSet.getLong(TOTAL_COST_HEADER);
            this.importUnitName = resultSet.getString(IMPORT_UNIT_NAME_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(IngredientImportDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_INSERT);
            
            callableStatement.setString(1, this.ingredientName);
            callableStatement.setTimestamp(2, this.date);
            callableStatement.setInt(3, this.amount);
            callableStatement.setLong(4, this.totalCost);
            callableStatement.setString(5, this.importUnitName);

            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientImportDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
    }

    @Override
    public void updateInDatabase() {
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(INGREDIENT_NAME_HEADER)) {
                preparedStatement.setString(index, this.ingredientName);
            } else if (header.equals(DATE_HEADER)) {
                preparedStatement.setTimestamp(index, this.date);
            } else if (header.equals(AMOUNT_HEADER)) {
                preparedStatement.setInt(index, this.amount);
            } else if (header.equals(TOTAL_COST_HEADER)) {
                preparedStatement.setLong(index, this.totalCost);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngredientImportDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public void setDate(Timestamp date) {
        if (date == null) {
            throw new NullPointerException();
        }
        this.date = date;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void setTotalCost(long cost) {
        this.totalCost = cost;
    }

    @Override
    public void setImportUnitName(String importUnitName) {
        this.importUnitName = importUnitName;
    }

    @Override
    public String getIngredientName() {
        return this.ingredientName;
    }

    @Override
    public Timestamp getDate() {
        return this.date;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public long getTotalCost() {
        return this.totalCost;
    }

    @Override
    public String getImportUnitName() {
        return this.importUnitName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.ingredientName);
        hash = 89 * hash + Objects.hashCode(this.date);
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
        final IngredientImportDetail other = (IngredientImportDetail) obj;
        if (!Objects.equals(this.ingredientName, other.ingredientName)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

}
