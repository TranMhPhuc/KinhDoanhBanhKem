package model.ingredient.unit.conversion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ingredient.unit.IngredientUnitDataStorage;
import model.ingredient.unit.IngredientUnitModelInterface;

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

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.srcUnit = ingredientUnitDataStorage.getIngredientUnit(resultSet.getString(SRC_UNIT_HEADER));
            this.dstUnit = ingredientUnitDataStorage.getIngredientUnit(resultSet.getString(DST_UNIT_HEADER));
            this.factor = resultSet.getDouble(FACTOR_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(IngredientUnitConversionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return "IngredientUnitConversionModel{" + "srcUnit=" + srcUnit + ", dstUnit=" + dstUnit + ", factor=" + factor + '}';
    }
}
