package model.employee;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.employee.shift.EmployeeShiftModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.position.EmployeePositionDataStorage;
import model.employee.position.EmployeePositionModel;
import model.employee.position.EmployeePositionModelInterface;
import model.employee.shift.EmployeeShiftDataStorage;
import model.employee.shift.EmployeeShiftDataStorageInterface;
import model.employee.shift.EmployeeShiftModelInterface;
import util.db.SQLServerConnection;

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
    private static EmployeeShiftDataStorageInterface employeeShiftDataStorage;

    private int employeeID;
    private String name;
    private int phoneNum;
    private Date birthday;
    private String email;
    private String personalID;
    private String password;
    private boolean isMale;
    private Date startDate;
    private EmployeePositionModelInterface position;
    private boolean isActive;
    private Date endDate;
    private ArrayList<EmployeeShiftModelInterface> shifts;

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
            EmployeePositionModelInterface position, boolean isActive, Date startDate,
            Date endDate, ArrayList<EmployeeShiftModelInterface> shift) {
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

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.employeeID = resultSet.getInt(ID_HEADER);
            this.name = resultSet.getString(NAME_HEADER);
            this.phoneNum = resultSet.getInt(PHONE_HEADER);
            this.birthday = resultSet.getDate(BIRTHDAY_HEADER);
            this.email = resultSet.getString(EMAIL_HEADER);
            this.personalID = resultSet.getString(PERSONAL_ID_HEADER);
            this.password = resultSet.getString(PASSWORD_HEADER);
            this.isMale = resultSet.getBoolean(GENDER_HEADER);

            this.position = employeePositionDataStorage.getPosition(
                    resultSet.getString(POSITION_HEADER));

            this.isActive = resultSet.getBoolean(STATUS_HEADER);
            this.startDate = resultSet.getDate(START_DATE_HEADER);
            this.endDate = resultSet.getDate(END_DATE_HEADER);

            Statement shiftFindStatement = dbConnection.createStatement();

            this.shifts = new ArrayList<>();

            String shiftFindQuery
                    = "SELECT " + EmployeeShiftModel.ID_HEADER
                    + " FROM " + TABLE_RELATE_SHIFT_NAME
                    + " WHERE " + ID_HEADER + " = " + this.employeeID;

            ResultSet shiftFindResultSet = shiftFindStatement.executeQuery(shiftFindQuery);

            while (shiftFindResultSet.next()) {
                String shiftIDText = shiftFindResultSet.getString(1);
                this.shifts.add(employeeShiftDataStorage.getShift(shiftIDText));
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getEmployeeIDText() {
        return String.valueOf(this.employeeID);
    }

    @Override
    public String getEmployeePositionName() {
        return this.position.getPositionName();
    }

    @Override
    public String getEmployeeName() {
        return this.name;
    }

    @Override
    public void setInsertStatementArgs(PreparedStatement preparedStatement) {

    }

    @Override
    public void setDeleteStatementArgs(PreparedStatement preparedStatement) {
        throw new UnsupportedOperationException("Employee can not be deleted.");
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(ID_HEADER)) {
                preparedStatement.setInt(index, this.employeeID);
            } else if (header.equals(NAME_HEADER)) {
                preparedStatement.setString(index, this.name);
            } else if (header.equals(PHONE_HEADER)) {
                preparedStatement.setInt(index, this.phoneNum);
            } else if (header.equals(BIRTHDAY_HEADER)) {
                preparedStatement.setDate(index, this.birthday);
            } else if (header.equals(EMAIL_HEADER)) {
                preparedStatement.setString(index, this.email);
            } else if (header.equals(PERSONAL_ID_HEADER)) {
                preparedStatement.setString(index, this.personalID);
            } else if (header.equals(PASSWORD_HEADER)) {
                preparedStatement.setString(index, this.password);
            } else if (header.equals(GENDER_HEADER)) {
                preparedStatement.setBoolean(index, this.isMale);
            } else if (header.equals(START_DATE_HEADER)) {
                preparedStatement.setDate(index, this.startDate);
            } else if (header.equals(POSITION_HEADER)) {
                this.position.setKeyArg(index, EmployeePositionModel.ID_HEADER, preparedStatement);
            } else if (header.equals(START_DATE_HEADER)) {
                preparedStatement.setBoolean(index, this.isActive);
            } else if (header.equals(END_DATE_HEADER)) {
                preparedStatement.setDate(index, this.endDate);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
