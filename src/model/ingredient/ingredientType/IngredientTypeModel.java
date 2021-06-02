package model.ingredient.ingredientType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IngredientTypeModel implements IngredientTypeModelInterface {

    public static final String TABLE_NAME = "LoaiNguyenLieu";
    public static final String ID_HEADER = "MaLoai";
    public static final String NAME_HEADER = "TenLoai";

    private int id;
    private String name;

    public IngredientTypeModel() {
    }

    public IngredientTypeModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static IngredientTypeModel getInstance(ResultSet resultSet) {
        IngredientTypeModel ret = new IngredientTypeModel();

        try {
            ret.id = resultSet.getInt(ID_HEADER);
            ret.name = resultSet.getString(NAME_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(IngredientTypeModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    @Override
    public String getIngredientTypeIDText() {
        return String.valueOf(this.id);
    }

    @Override
    public String toString() {
        return "IngredientTypeModel{" + "id=" + id + ", name=" + name + '}';
    }

}
