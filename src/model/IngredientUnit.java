package model;

import util.db.SQLServerConnect;
import util.AppLog;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IngredientUnit {

    private static ArrayList<IngredientUnit> ingredientUnits;

    private int unitCode;
    private String unitName;

    static {
        ingredientUnits = new ArrayList<>();
        updateFromDB();
    }

    private IngredientUnit() {
    }

    private IngredientUnit(int unitCode, String unitName) {
        this.unitCode = unitCode;
        this.unitName = unitName;
    }

    public static void updateFromDB() {
        Connection connection = SQLServerConnect.getConnection();

        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM DonVi";

            ResultSet rs = statement.executeQuery(query);

            ingredientUnits.clear();

            while (rs.next()) {
                ingredientUnits.add(new IngredientUnit(rs.getInt("MaDonVi"), rs.getString("TenDonVi")));
            }
        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update ingredient unit from DB is not successfully.");
        }
    }

    public static String getUnitName(int unitCode) throws IndexOutOfBoundsException {
        String unitName = "";
        try {
            unitName = ingredientUnits.get(unitCode).getUnitName();
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Unit code is not valid!");
        }
        return unitName;
    }

    private String getUnitName() {
        return this.unitName;
    }

    public static void getIngredientUnitData(String[] view) {
        view = new String[ingredientUnits.size()];
        for (int i = 0; i < view.length; i++) {
            view[i] = ingredientUnits.get(i).getUnitName();
        }
    }

    @Override
    public String toString() {
        return "IngredientUnit{" + "unitCode=" + unitCode + ", unitName=" + unitName + '}';
    }

    public static void main(String[] args) {
        if (ingredientUnits.size() == 0) {
            throw new AssertionError("Unit set is empty!");
        }

        for (int i = 0; i < ingredientUnits.size(); i++) {
            System.out.println(ingredientUnits.get(i));
        }
    }

}//end IngredientUnit
