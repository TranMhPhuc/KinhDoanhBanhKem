package model;

import util.db.SQLServerConnect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeePosition {

    private static EmployeePosition adminPost = new EmployeePosition();
    private static EmployeePosition managerPost = new EmployeePosition();
    private static EmployeePosition accountantPost = new EmployeePosition();
    private static EmployeePosition cashierPost = new EmployeePosition();
    private static EmployeePosition bakeryMakerPost = new EmployeePosition();

    private int postCode;
    private String postName;
    private float salaryFactor = 1.0f;

    static {
        updateFromDB();
    }
    
    private EmployeePosition() {
    }

    public static void updateFromDB() {
        Connection connection = SQLServerConnect.getConnection();

        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM ChucVu";

            ResultSet rs = statement.executeQuery(query);

            rs.next();
            updatePost(adminPost, rs.getInt("MaCV"), rs.getString("TenCV"), rs.getFloat("HeSoLuong"));

            rs.next();
            updatePost(managerPost, rs.getInt("MaCV"), rs.getString("TenCV"), rs.getFloat("HeSoLuong"));

            rs.next();
            updatePost(cashierPost, rs.getInt("MaCV"), rs.getString("TenCV"), rs.getFloat("HeSoLuong"));

            rs.next();
            updatePost(accountantPost, rs.getInt("MaCV"), rs.getString("TenCV"), rs.getFloat("HeSoLuong"));

            rs.next();
            updatePost(bakeryMakerPost, rs.getInt("MaCV"), rs.getString("TenCV"), rs.getFloat("HeSoLuong"));

        } catch (SQLException ex) {
            Logger.getLogger(EmployeePosition.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void updatePost(EmployeePosition post, int postCode, String postName, float salaryFactor) {
        post.setPostCode(postCode);
        post.setPostName(postName);
        post.setSalaryFactor(salaryFactor);
    }

    public static EmployeePosition getAdminPostition() {
        return adminPost;
    }

    public static EmployeePosition getManagerPostition() {
        return managerPost;
    }

    public static EmployeePosition getAccountantPostition() {
        return accountantPost;
    }

    public static EmployeePosition getCashierPostition() {
        return cashierPost;
    }

    public static EmployeePosition getBarkeryMakerPostition() {
        return bakeryMakerPost;
    }

    public int getPostCode() {
        return postCode;
    }

    public String getPostName() {
        return postName;
    }

    public float getSalaryFactor() {
        return salaryFactor;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public void setSalaryFactor(float salaryFactor) {
        this.salaryFactor = salaryFactor;
    }
    
    public void getPostitionData(String[] view) {
        view = new String[5];
        view[0] = adminPost.getPostName();
        view[1] = managerPost.getPostName();
        view[2] = accountantPost.getPostName();
        view[3] = cashierPost.getPostName();
        view[4] = bakeryMakerPost.getPostName();
    }

    @Override
    public String toString() {
        return "Position{" + "postCode=" + postCode + ", postName=" + postName + ", salaryFactor=" + salaryFactor + '}';
    }
    
    public static void main(String[] args) {
        System.out.println(adminPost);
        System.out.println(managerPost);
        System.out.println(cashierPost);
        System.out.println(accountantPost);
        System.out.println(bakeryMakerPost);
    }

}//end EmployeePosition
