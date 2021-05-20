package control.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLServerConnect {

    private static final String DB_URL = "jdbc:sqlserver://;databaseName=BANHKEM_BLN";

    private static final String USERNAME = "Admin";

    private static final String PASSWORD = "1234";

    private static Connection connection;

    private volatile static SQLServerConnect connectInstance;
    
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private SQLServerConnect() {
    }
    
    public static synchronized SQLServerConnect getInstance() {
        if (connectInstance == null) {
            synchronized (SQLServerConnect.class) {
                if (connectInstance == null) {
                    connectInstance = new SQLServerConnect();
                }
            }
        }
        return connectInstance;
    }
    
    public static Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MaNV, TenNV FROM NHANVIEN");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("MaNV") + " | " + resultSet.getString("TenNV"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLServerConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
