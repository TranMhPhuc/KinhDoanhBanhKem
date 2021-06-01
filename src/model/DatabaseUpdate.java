package model;

import java.sql.Connection;

public interface DatabaseUpdate {
    
    void updateFromDB(Connection connection);
    
}
