package model.employee;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.employee.shift.EmployeeShiftModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.position.EmployeePositionDataStorage;
import model.employee.position.EmployeePositionModel;
import model.employee.shift.EmployeeShiftDataStorage;
import util.db.SQLServerConnection;
import view.function.EmployeeViewObserver;

public class EmployeeModel implements EmployeeModelInterface {

    public static final String TABLE_NAME = "NhanVien";
    public static final String TABLE_RELATE_SHIFT_NAME = "PhanCong";
    public static final String ID_HEADER = "MaNV";
    public static final String NAME_HEADER = "TenNV";
    public static final String PHONE_HEADER = "SDT";
    public static final String BIRTHDAY_HEADER = "NgaySinh";
    public static final String EMAIL_HEADER = "Email";
    public static final String PERSONAL_ID_HEADER = "CCCD";
    public static final String PASSWORD_HEADER = "MatKhau";
    public static final String GENDER_HEADER = "GioiTinh";
    public static final String START_DATE_HEADER = "NgayGiaNhap";
    public static final String POSITION_HEADER = "MaCV";
    public static final String STATUS_HEADER = "TrangThai";
    public static final String END_DATE_HEADER = "NgayNghiViec";

    private static Connection dbConnection;
    private static EmployeePositionDataStorage employeePositionDataStorage;
    private static EmployeeShiftDataStorage employeeShiftDataStorage;

    private int employeeID;
    private String name;
    private int phoneNum;
    private Date birthday;
    private String email;
    private String personalID;
    private String password;
    private boolean isMale;
    private Date startDate;
    private EmployeePositionModel position;
    private boolean isActive;
    private Date endDate;
    private ArrayList<EmployeeShiftModel> shifts;

    static {
        dbConnection = SQLServerConnection.getConnection();
        employeePositionDataStorage = EmployeePositionDataStorage.getInstance();
        employeeShiftDataStorage = EmployeeShiftDataStorage.getInstance();
    }

    public EmployeeModel() {
        isActive = true;
        isMale = true;
    }

    public EmployeeModel(int employeeID, String name, int phoneNum, Date birthday,
            String email, String personalID, String password, boolean isMale,
            EmployeePositionModel position, boolean isActive, Date startDate,
            Date endDate, ArrayList<EmployeeShiftModel> shift) {
        this.employeeID = employeeID;
        this.name = name;
        this.phoneNum = phoneNum;
        this.birthday = birthday;
        this.email = email;
        this.personalID = personalID;
        this.password = password;
        this.isMale = isMale;
        this.position = position;
        this.isActive = isActive;
        this.startDate = startDate;
        this.endDate = endDate;
        this.shifts = shift;
    }

    public static EmployeeModel getInstance(ResultSet resultSet) {
        EmployeeModel ret = new EmployeeModel();

        try {
            ret.employeeID = resultSet.getInt(ID_HEADER);
            ret.name = resultSet.getString(NAME_HEADER);
            ret.phoneNum = resultSet.getInt(PHONE_HEADER);
            ret.birthday = resultSet.getDate(BIRTHDAY_HEADER);
            ret.email = resultSet.getString(EMAIL_HEADER);
            ret.personalID = resultSet.getString(PERSONAL_ID_HEADER);
            ret.password = resultSet.getString(PASSWORD_HEADER);
            ret.isMale = resultSet.getBoolean(GENDER_HEADER);

            ret.position = employeePositionDataStorage.getPosition(
                    resultSet.getString(POSITION_HEADER));

            ret.isActive = resultSet.getBoolean(STATUS_HEADER);
            ret.startDate = resultSet.getDate(START_DATE_HEADER);
            ret.endDate = resultSet.getDate(END_DATE_HEADER);

            Statement shiftFindStatement = dbConnection.createStatement();

            ret.shifts = new ArrayList<>();

            String shiftFindQuery
                    = "SELECT " + EmployeeShiftModel.ID_HEADER
                    + " FROM " + TABLE_RELATE_SHIFT_NAME
                    + " WHERE " + ID_HEADER + " = " + ret.employeeID;

            ResultSet shiftFindResultSet = shiftFindStatement.executeQuery(shiftFindQuery);

            while (shiftFindResultSet.next()) {
                String shiftIDText = shiftFindResultSet.getString(1);
                ret.shifts.add(employeeShiftDataStorage.getShift(shiftIDText));
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    @Override
    public void registerObserver(EmployeeViewObserver employeeViewObserver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeObserver(EmployeeViewObserver employeeViewObserver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEmployeeIDText() {
        return String.valueOf(this.employeeID);
    }

    @Override
    public String toString() {
        String shiftText = "{";
        for (int i = 0; i < shifts.size() - 1; i++) {
            shiftText += shifts.get(i).getShiftIDText() + ", ";
        }
        shiftText += shifts.get(shifts.size() - 1).getShiftIDText() + "}";

        return "EmployeeModel{" + "employeeID=" + employeeID + ", name=" + name + ", "
                + "phoneNum=" + phoneNum + ", birthday=" + birthday + ", email="
                + email + ", personalID=" + personalID + ", password=" + password + ", "
                + "isMale=" + isMale + ", startDate=" + startDate + ", positionID="
                + position.getPositionIDText() + ", isActive=" + isActive + ", endDate=" + endDate
                + ", shifts=" + shiftText + '}';
    }

}
