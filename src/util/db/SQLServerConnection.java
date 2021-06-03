package util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLServerConnection {

    private static final String DB_URL = "jdbc:sqlserver://;databaseName=BANHKEM_BLN";

    private static final String USERNAME = "Admin";

    private static final String PASSWORD = "1234";

    private static Connection uniqueConnection;

    private volatile static SQLServerConnection connectInstance;
    
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            uniqueConnection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private SQLServerConnection() {
    }
    
    public static synchronized SQLServerConnection getInstance() {
        if (connectInstance == null) {
            synchronized (SQLServerConnection.class) {
                if (connectInstance == null) {
                    connectInstance = new SQLServerConnection();
                }
            }
        }
        return connectInstance;
    }
    
    public static Connection getConnection() {
        return uniqueConnection;
    }

    public static void main(String[] args) {
        try {
            Statement statement = uniqueConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MaNV, TenNV FROM NHANVIEN");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("MaNV") + " | " + resultSet.getString("TenNV"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
