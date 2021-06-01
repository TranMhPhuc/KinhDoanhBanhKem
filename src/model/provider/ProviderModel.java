package model.provider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProviderModel implements ProviderModelInterface {

    public static final String TABLE_NAME = "NhaCungCap";
    public static final String ID_HEADER = "MaNCC";
    public static final String NAME_HEADER = "TenNCC";
    public static final String EMAIL_HEADER = "Email";
    public static final String ADDRESS_HEADER = "DiaChi";
    public static final String PHONE_HEADER = "SDT";

    private String id;
    private String name;
    private String email;
    private String address;
    private int phoneNum;

    public ProviderModel() {
    }

    public ProviderModel(String id, String producerName, String email, String address, int phoneNum) {
        this.id = id;
        this.name = producerName;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public static ProviderModelInterface getInstance(ResultSet resultSet) {
        ProviderModel ret = new ProviderModel();

        try {
            ret.id = resultSet.getString(ID_HEADER);
            ret.name = resultSet.getString(NAME_HEADER);
            ret.email = resultSet.getString(EMAIL_HEADER);
            ret.address = resultSet.getString(ADDRESS_HEADER);
            ret.phoneNum = resultSet.getInt(PHONE_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(ProviderModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    @Override
    public String getIDText() {
        return this.id;
    }

    @Override
    public void notifyObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public String toString() {
        return "ProviderModel{" + "id=" + id + ", name=" + name + ", email=" + email + ", "
                + "address=" + address + ", phoneNum=" + phoneNum + '}';
    }

}
