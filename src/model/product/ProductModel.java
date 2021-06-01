package model.product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductModel implements ProductModelInterface {

    public static final String TABLE_NAME = "SanPham";
    public static final String ID_HEADER = "MaSP";
    public static final String NAME_HEADER = "TenSP";
    public static final String SIZE_HEADER = "KichThuoc";
    public static final String COST_HEADER = "GiaGoc";
    public static final String AMOUNT_HEADER = "SoLuong";
    public static final String PRICE_HEADER = "GiaBan";

    private int id;
    private String name;
    private String size;
    private int cost;
    private int amount;
    private int price;

    public ProductModel() {
    }

    public ProductModel(int id, String name, int cost, int price, int amount, String size) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.price = price;
        this.amount = amount;
        this.size = size;
    }

    @Override
    public String getProductIDText() {
        return String.valueOf(this.id);
    }

    public static ProductModelInterface getInstance(ResultSet resultSet) {
        ProductModel ret = new ProductModel();
        try {
            ret.id = resultSet.getInt(ID_HEADER);
            ret.name = resultSet.getString(NAME_HEADER);
            ret.size = resultSet.getString(SIZE_HEADER);
            ret.cost = resultSet.getInt(COST_HEADER);
            ret.amount = resultSet.getInt(AMOUNT_HEADER);
            ret.price = resultSet.getInt(PRICE_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(ProductModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public void registerObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "ProductModel{" + "id=" + id + ", name=" + name + ", cost=" + cost
                + ", price=" + price + ", amount=" + amount + ", size=" + size + '}';
    }

}
