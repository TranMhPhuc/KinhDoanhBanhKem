package model.provider;

import java.sql.PreparedStatement;
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

    private static final String INSERT_QUERY_PROTOTYPE
            = "INSERT INTO " + TABLE_NAME + " ("
            + ID_HEADER + ", " + NAME_HEADER + ", " + EMAIL_HEADER + ", "
            + ADDRESS_HEADER + ", " + PHONE_HEADER + ")"
            + " VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY_PROTOTYPE
            = "UPDATE " + TABLE_NAME + " SET "
            + NAME_HEADER + " = ?, " + EMAIL_HEADER + " = ?, "
            + ADDRESS_HEADER + " = ?, " + PHONE_HEADER + " = ?"
            + " WHERE " + ID_HEADER + " = ?";

    private static final String DELETE_QUERY_PROTOTYPE
            = "DELETE FROM " + TABLE_NAME
            + " WHERE " + ID_HEADER + " = ?";

    private String id;
    private String name;
    private String email;
    private String address;
    private String phoneNum;

    public ProviderModel() {
    }

    @Override
    public String getIDText() {
        return this.id;
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.id = resultSet.getString(ID_HEADER);
            this.name = resultSet.getString(NAME_HEADER);
            this.email = resultSet.getString(EMAIL_HEADER);
            this.address = resultSet.getString(ADDRESS_HEADER);
            this.phoneNum = resultSet.getString(PHONE_HEADER);
        } catch (SQLException ex) {
            Logger.getLogger(ProviderModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(INSERT_QUERY_PROTOTYPE);

            preparedStatement.setString(1, this.id);
            preparedStatement.setString(2, this.name);
            preparedStatement.setString(3, this.email);
            preparedStatement.setString(4, this.address);
            preparedStatement.setString(5, this.phoneNum);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProviderModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(DELETE_QUERY_PROTOTYPE);
            
            preparedStatement.setString(1, this.id);
            
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProviderModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateInDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(UPDATE_QUERY_PROTOTYPE);

            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.address);
            preparedStatement.setString(4, this.phoneNum);
            preparedStatement.setString(5, this.id);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProviderModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(ID_HEADER)) {
                preparedStatement.setString(index, this.id);
            } else if (header.equals(NAME_HEADER)) {
                preparedStatement.setString(index, this.name);
            } else if (header.equals(EMAIL_HEADER)) {
                preparedStatement.setString(index, this.email);
            } else if (header.equals(NAME_HEADER)) {
                preparedStatement.setString(index, this.address);
            } else if (header.equals(NAME_HEADER)) {
                preparedStatement.setString(index, this.phoneNum);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProviderModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String toString() {
        return "ProviderModel{" + "id=" + id + ", name=" + name + ", email=" + email + ", "
                + "address=" + address + ", phoneNum=" + phoneNum + '}';
    }

}
