package model.ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IngredientImportDetail {
    
    public static final String TABLE_NAME = "ChiTietNhapNL";
    public static final String DATE_HEADER = "NgayGioNhap";
    public static final String AMOUNT_HEADER = "SoLuong";
    public static final String TOTAL_COST_HEADER = "GiaNhap";

    private Timestamp date;
    private int amount;
    private int totalCost;

    public IngredientImportDetail() {
    }
    
    public IngredientImportDetail(Timestamp date, int amount, int totalCost) {
        this.date = date;
        this.amount = amount;
        this.totalCost = totalCost;
    }

    public static IngredientImportDetail getInstance(ResultSet resultSet) {
        IngredientImportDetail ret = new IngredientImportDetail();
        try {
            ret.date = resultSet.getTimestamp(DATE_HEADER);
            ret.amount = resultSet.getInt(AMOUNT_HEADER);
            ret.totalCost = resultSet.getInt(TOTAL_COST_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(IngredientImportDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
