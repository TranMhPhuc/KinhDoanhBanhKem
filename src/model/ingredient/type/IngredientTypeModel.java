package model.ingredient.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IngredientTypeModel implements IngredientTypeModelInterface {

    public static final String TABLE_NAME = "LoaiNguyenLieu";
    public static final String ID_HEADER = "MaLoai";
    public static final String NAME_HEADER = "TenLoai";

    private static final String INSERT_QUERY_PROTOTYPE
            = "INSERT INTO " + TABLE_NAME + " ("
            + ID_HEADER + ", " + NAME_HEADER + ")"
            + " VALUES (?, ?)";

    private int id;
    private String name;

    public IngredientTypeModel() {
    }

    @Override
    public void setIngredientTypeID(int id) {
        this.id = id;
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
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(INSERT_QUERY_PROTOTYPE);

            preparedStatement.setInt(1, this.id);
            preparedStatement.setString(2, this.name);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientTypeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateInDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
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
