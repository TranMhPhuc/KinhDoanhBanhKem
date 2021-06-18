package model.ingredient.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IngredientTypeModel implements IngredientTypeModelInterface {

    public static final String TABLE_NAME = "LoaiNguyenLieu";
    public static final String ID_HEADER = "MaLoai";
    public static final String NAME_HEADER = "TenLoai";

    private static final String SP_INSERT = "{call insert_LoaiNguyenLieu(?)}";

    private int id;
    private String name;

    public IngredientTypeModel() {
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getIngredientTypeIDText() {
        return String.valueOf(this.id);
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt(ID_HEADER);
            this.name = resultSet.getString(NAME_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(IngredientTypeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_INSERT);

            callableStatement.setString(1, this.name);
            
            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientTypeModel.class.getName()).log(Level.SEVERE, null, ex);
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
            if (header.equals(ID_HEADER)) {
                preparedStatement.setInt(index, this.id);
            } else if (header.equals(NAME_HEADER)) {
                preparedStatement.setString(index, this.name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngredientTypeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
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
        final IngredientTypeModel other = (IngredientTypeModel) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IngredientTypeModel{" + "id=" + id + ", name=" + name + '}';
    }

}
