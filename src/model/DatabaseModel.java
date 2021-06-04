package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DatabaseModel {

    void setProperty(ResultSet resultSet);

    void setInsertStatementArgs(PreparedStatement preparedStatement);
    
    void setDeleteStatementArgs(PreparedStatement preparedStatement);

    void setKeyArg(int index, String header, PreparedStatement preparedStatement);
}
