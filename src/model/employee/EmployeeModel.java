package model.employee;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.employee.shift.EmployeeShiftModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.position.EmployeePositionDataStorage;
import model.employee.position.EmployeePositionModel;
import model.employee.position.EmployeePositionModelInterface;
import model.employee.shift.EmployeeShiftDataStorage;
import model.employee.shift.EmployeeShiftDataStorageInterface;
import model.employee.shift.EmployeeShiftModelInterface;

public class EmployeeModel implements EmployeeModelInterface {

    public static final String TABLE_NAME = "NhanVien";
    public static final String TABLE_EMPLOYEE_SHIFT_NAME = "PhanCong";
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

    private static final String INSERT_QUERY_PROTOTYPE
            = "INSERT INTO" + TABLE_NAME + " ("
            + NAME_HEADER + ", " + PHONE_HEADER + ", "
            + BIRTHDAY_HEADER + ", " + EMAIL_HEADER + ", " + PASSWORD_HEADER + ", "
            + GENDER_HEADER + ", " + START_DATE_HEADER + ", " + POSITION_HEADER + ", "
            + STATUS_HEADER + ", " + END_DATE_HEADER + ")"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY_PROTOTYPE
            = "UPDATE " + TABLE_NAME
            + " SET " + NAME_HEADER + " = ?, " + ", " + PHONE_HEADER + " = ?, "
            + BIRTHDAY_HEADER + " = ?, " + EMAIL_HEADER + " = ?, " + PASSWORD_HEADER + " = ?, "
            + GENDER_HEADER + " = ?, " + START_DATE_HEADER + " = ?, " + POSITION_HEADER + " = ?, "
            + STATUS_HEADER + " = ?, " + END_DATE_HEADER + " = ? "
            + " WHERE " + ID_HEADER + " = ?";

    private static final String INSERT_EMPLOYEE_SHIFT_QUERY_PROTOTYPE
            = "INSERT INTO " + TABLE_EMPLOYEE_SHIFT_NAME + " ("
            + ID_HEADER + ", " + EmployeeShiftModel.ID_HEADER + ")"
            + " VALUES (?, ?)";

    private static final String DELETE_EMPLOYEE_SHIFT_QUERY_PROTOTYPE
            = "DELETE FROM " + TABLE_EMPLOYEE_SHIFT_NAME
            + " WHERE " + ID_HEADER + " = ?";

    private static EmployeePositionDataStorage employeePositionDataStorage;
    private static EmployeeShiftDataStorageInterface employeeShiftDataStorage;

    private int employeeID;
    private String name;
    private String phoneNum;
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
        employeePositionDataStorage = EmployeePositionDataStorage.getInstance();
        employeeShiftDataStorage = EmployeeShiftDataStorage.getInstance();
    }

    public EmployeeModel() {
        this.shifts = new ArrayList<>();
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.employeeID = resultSet.getInt(ID_HEADER);
            this.name = resultSet.getString(NAME_HEADER);
            this.phoneNum = resultSet.getString(PHONE_HEADER);
            this.birthday = resultSet.getDate(BIRTHDAY_HEADER);
            this.email = resultSet.getString(EMAIL_HEADER);
            this.personalID = resultSet.getString(PERSONAL_ID_HEADER);
            this.password = resultSet.getString(PASSWORD_HEADER);
            this.isMale = resultSet.getBoolean(GENDER_HEADER);

            this.position = employeePositionDataStorage.getPositionByID(
                    resultSet.getString(POSITION_HEADER));

            this.isActive = resultSet.getBoolean(STATUS_HEADER);
            this.startDate = resultSet.getDate(START_DATE_HEADER);
            this.endDate = resultSet.getDate(END_DATE_HEADER);

            this.shifts.clear();

            Statement statement = dbConnection.createStatement();

            String shiftFindQuery
                    = "SELECT " + EmployeeShiftModel.ID_HEADER
                    + " FROM " + TABLE_EMPLOYEE_SHIFT_NAME
                    + " WHERE " + ID_HEADER + " = " + this.employeeID;

            ResultSet findShiftResultSet = statement.executeQuery(shiftFindQuery);

            while (findShiftResultSet.next()) {
                this.shifts.add(employeeShiftDataStorage.getShift(findShiftResultSet
                        .getString(1)));
            }

            findShiftResultSet.close();
            statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setPersonalID(String personalID) {
        this.personalID = personalID;
    }

    @Override
    public void setGender(boolean isMale) {
        this.isMale = isMale;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public void setStatus(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public void setShift(List<EmployeeShiftModelInterface> shifts) {
        if (shifts == null) {
            throw new NullPointerException();
        }
        this.shifts.clear();
        for (EmployeeShiftModelInterface shift : shifts) {
            this.shifts.add(shift);
        }
    }

    @Override
    public String getEmployeeIDText() {
        return String.valueOf(this.employeeID);
    }

    @Override
    public String getEmployeePositionName() {
        return this.position.getName();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPhoneNum() {
        return this.phoneNum;
    }

    @Override
    public Date getBirthday() {
        return this.birthday;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPersonalID() {
        return this.personalID;
    }

    @Override
    public boolean getGender() {
        return this.isMale;
    }

    @Override
    public Date getStartDate() {
        return this.startDate;
    }

    @Override
    public boolean getStatus() {
        return this.isActive;
    }

    @Override
    public Date getEndDate() {
        return this.endDate;
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(ID_HEADER)) {
                preparedStatement.setInt(index, this.employeeID);
            } else if (header.equals(NAME_HEADER)) {
                preparedStatement.setString(index, this.name);
            } else if (header.equals(PHONE_HEADER)) {
                preparedStatement.setString(index, this.phoneNum);
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
    public void insertToDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(INSERT_QUERY_PROTOTYPE);

            // ID is identity column
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.phoneNum);
            preparedStatement.setDate(3, this.birthday);
            preparedStatement.setString(4, this.email);
            preparedStatement.setString(5, this.personalID);
            preparedStatement.setString(6, this.password);
            preparedStatement.setBoolean(7, this.isMale);
            preparedStatement.setDate(8, this.startDate);
            this.position.setKeyArg(9, EmployeePositionModel.ID_HEADER, preparedStatement);
            preparedStatement.setBoolean(10, this.isActive);
            preparedStatement.setDate(11, this.endDate);

            preparedStatement.execute();
            preparedStatement.close();

            preparedStatement = dbConnection
                    .prepareStatement(INSERT_EMPLOYEE_SHIFT_QUERY_PROTOTYPE);

            for (EmployeeShiftModelInterface shift : shifts) {
                preparedStatement.clearParameters();
                preparedStatement.setInt(1, this.employeeID);
                shift.setKeyArg(2, EmployeeShiftModel.ID_HEADER, preparedStatement);
                preparedStatement.execute();
            }
            preparedStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateInDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(UPDATE_QUERY_PROTOTYPE);

            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.phoneNum);
            preparedStatement.setDate(3, this.birthday);
            preparedStatement.setString(4, this.email);
            preparedStatement.setString(5, this.personalID);
            preparedStatement.setString(6, this.password);
            preparedStatement.setBoolean(7, this.isMale);
            preparedStatement.setDate(8, this.startDate);
            this.position.setKeyArg(9, EmployeePositionModel.ID_HEADER, preparedStatement);
            preparedStatement.setBoolean(10, this.isActive);
            preparedStatement.setDate(11, this.endDate);
            preparedStatement.setInt(12, this.employeeID);

            preparedStatement.execute();
            preparedStatement.close();

            preparedStatement = dbConnection
                    .prepareStatement(DELETE_EMPLOYEE_SHIFT_QUERY_PROTOTYPE);

            preparedStatement.setInt(1, this.employeeID);
            preparedStatement.execute();
            preparedStatement.close();

            preparedStatement = dbConnection
                    .prepareStatement(INSERT_EMPLOYEE_SHIFT_QUERY_PROTOTYPE);

            for (EmployeeShiftModelInterface shift : shifts) {
                preparedStatement.clearParameters();
                preparedStatement.setInt(1, this.employeeID);
                shift.setKeyArg(2, EmployeeShiftModel.ID_HEADER, preparedStatement);
                preparedStatement.execute();
            }

            preparedStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.employeeID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmployeeModel other = (EmployeeModel) obj;
        if (this.employeeID != other.employeeID) {
            return false;
        }
        return true;
    }

    @Override
    public void setEmployeeID(String employeeIDText) {
        this.employeeID = Integer.parseInt(employeeIDText);
    }

    @Override
    public String toString() {
        return "EmployeeModel{" + "employeeID=" + employeeID + ", name=" + name + ", "
                + "phoneNum=" + phoneNum + ", birthday=" + birthday + ", email="
                + email + ", personalID=" + personalID + ", password=" + password + ", "
                + "isMale=" + isMale + ", startDate=" + startDate + ", positionID="
                + position.getPositionIDText() + ", isActive=" + isActive + ", endDate=" + endDate
                + '}';
    }

    @Override
    public void setPosition(EmployeePositionModelInterface position) {
        if (position == null) {
            throw new NullPointerException("Position instance is null.");
        }
        this.position = position;
    }

    @Override
    public EmployeePositionModelInterface getPosition() {
        return this.position;
    }

    @Override
    public List<EmployeeShiftModelInterface> getShift() {
        return this.shifts;
    }

}
