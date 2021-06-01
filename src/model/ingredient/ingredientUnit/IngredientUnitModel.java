package model.ingredient.ingredientUnit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IngredientUnitModel implements IngredientUnitModelInterface {

    public static final String TABLE_NAME = "DonVi";
    public static final String ID_HEADER = "MaDonVi";
    public static final String NAME_HEADER = "TenDonVi";

    private int id;
    private String name;

    public IngredientUnitModel() {
    }

    public IngredientUnitModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getIDText() {
        return String.valueOf(this.id);
    }

    public static IngredientUnitModelInterface getInstance(ResultSet resultSet) {
        IngredientUnitModel ret = new IngredientUnitModel();
        try {
            ret.id = resultSet.getInt(ID_HEADER);
            ret.name = resultSet.getString(NAME_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(IngredientUnitModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public String toString() {
        return "IngredientUnitModel{" + "id=" + id + ", name=" + name + '}';
    }

}
