package model;

import java.sql.Connection;
import util.db.SQLServerConnection;

public interface DatabaseUpdate {
    
    Connection dbConnection = SQLServerConnection.getConnection();
    
    void updateFromDB();
    
    void clearData();
    
}
