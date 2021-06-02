package model.ingredient.ingredientUnit.conversion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ingredient.ingredientUnit.IngredientUnitDataStorage;
import model.ingredient.ingredientUnit.IngredientUnitModelInterface;

public class IngredientUnitConversionModel implements IngredientUnitConversionModelInterface {

    public static final String TABLE_NAME = "DoiDonVi";
    public static final String SRC_UNIT_HEADER = "MaDonViGoc";
    public static final String DST_UNIT_HEADER = "MaDonViDich";
    public static final String FACTOR_HEADER = "TrongSoDoi";

    private static IngredientUnitDataStorage ingredientUnitDataStorage;

    private IngredientUnitModelInterface srcUnit;
    private IngredientUnitModelInterface dstUnit;
    private double factor;

    static {
        ingredientUnitDataStorage = IngredientUnitDataStorage.getInstance();
    }

    public IngredientUnitConversionModel() {
    }

    public IngredientUnitConversionModel(IngredientUnitModelInterface srcUnit,
            IngredientUnitModelInterface dstUnit, double factor) {
        this.srcUnit = srcUnit;
        this.dstUnit = dstUnit;
        this.factor = factor;
    }

    public static IngredientUnitConversionModelInterface getInstance(ResultSet resultSet) {
        IngredientUnitConversionModel ret = new IngredientUnitConversionModel();
        try {
            ret.srcUnit = ingredientUnitDataStorage.getIngredientUnit(resultSet.getString(SRC_UNIT_HEADER));
            ret.dstUnit = ingredientUnitDataStorage.getIngredientUnit(resultSet.getString(DST_UNIT_HEADER));
            ret.factor = resultSet.getDouble(FACTOR_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(IngredientUnitConversionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public String toString() {
        return "IngredientUnitConversionModel{" + "srcUnit=" + srcUnit + ", dstUnit=" + dstUnit + ", factor=" + factor + '}';
    }

}
