package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import util.db.SQLServerConnection;

public interface DatabaseModel {

    Connection dbConnection = SQLServerConnection.getConnection();

    void setProperty(ResultSet resultSet);

    void insertToDatabase();

    void deleteInDatabase();
    
    void updateInDatabase();

    void setKeyArg(int index, String header, PreparedStatement preparedStatement);
}
