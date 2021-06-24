package model.employee;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.shift.detail.ShiftDetailModel;
import model.employee.shift.detail.ShiftDetailModelInterface;
import util.password.Hash;

public class EmployeeModel implements EmployeeModelInterface {

    public static final String TABLE_NAME = "NhanVien";
    public static final String ID_HEADER = "MaNV";
    public static final String NAME_HEADER = "TenNV";
    public static final String PHONE_HEADER = "SDT";
    public static final String BIRTHDAY_HEADER = "NgaySinh";
    public static final String EMAIL_HEADER = "Email";
    public static final String PERSONAL_ID_HEADER = "CCCD";
    public static final String PASSWORD_HEADER = "MatKhau";
    public static final String GENDER_HEADER = "GioiTinh";
    public static final String START_DATE_HEADER = "NgayGiaNhap";
    public static final String POSITION_NAME_HEADER = "TenCV";
    public static final String STATUS_HEADER = "TrangThai";
    public static final String END_DATE_HEADER = "NgayNghiViec";

    private static final int RANDOM_PASSWORD_LENGTH = 6;

    private static final String SP_INSERT
            = "{call insert_NhanVien(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

    private static final String SP_UPDATE
            = "{call update_NhanVien(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

    private static final String SP_GET_SHIFT_DETAIL
            = "{call get_employee_shift(?)}";

    private static final String SP_UPDATE_PROFILE
            = "{call update_employee_profile(?, ?, ?)}";

    private static final String SP_UPDATE_PASSWORD
            = "{call update_employee_password(?, ?, ?)}";
    
    private static final String FC_GET_SALT_FROM_EMAIL 
            = "{? = call get_salt_from_email(?)}";
    
    private static byte[] salt = null;

    private int employeeID;
    private String name;
    private String phoneNum;
    private Date birthday;
    private String email;
    private String personalID;
    private String password;
    private boolean isMale;
    private Date startDate;
    private String positionName;
    private boolean isActive;
    private Date endDate;
    private List<ShiftDetailModelInterface> shiftDetails;

    public EmployeeModel() {
        shiftDetails = new ArrayList<>();
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
            this.positionName = resultSet.getString(POSITION_NAME_HEADER);
            this.isActive = resultSet.getBoolean(STATUS_HEADER);
            this.startDate = resultSet.getDate(START_DATE_HEADER);
            this.endDate = resultSet.getDate(END_DATE_HEADER);

            shiftDetails.clear();

            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_SHIFT_DETAIL);

            callableStatement.setInt(1, this.employeeID);

            ResultSet getShiftResultSet = callableStatement.executeQuery();

            while (getShiftResultSet.next()) {
                ShiftDetailModelInterface shiftDetail = new ShiftDetailModel();
                shiftDetail.setEmployee(this);
                shiftDetail.setProperty(getShiftResultSet);
                shiftDetails.add(shiftDetail);
            }

            getShiftResultSet.close();
            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
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
    public String getEmployeeIDText() {
        return String.valueOf(this.employeeID);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return this.password;
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
    public String randomPassword() {
        EmployeeModel.salt = Hash.getNextSalt();
        String randomPlaintextPass = Hash.generateRandomPassword(RANDOM_PASSWORD_LENGTH);
        String hashedPass = Hash.doHash(randomPlaintextPass, EmployeeModel.salt);
        this.password = hashedPass;
        return randomPlaintextPass;
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
            } else if (header.equals(STATUS_HEADER)) {
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
            CallableStatement callableStatement = dbConnection.prepareCall(SP_INSERT);

            callableStatement.setString(1, this.name);
            callableStatement.setString(2, this.phoneNum);
            callableStatement.setDate(3, this.birthday);
            callableStatement.setString(4, this.email);
            callableStatement.setString(5, this.personalID);
            callableStatement.setString(6, this.password);
            callableStatement.setBoolean(7, this.isMale);
            callableStatement.setDate(8, this.startDate);
            callableStatement.setString(9, this.positionName); // ERROR
            callableStatement.setBoolean(10, this.isActive);
            callableStatement.setDate(11, this.endDate);
            callableStatement.setBytes(12, EmployeeModel.salt);
            EmployeeModel.salt = null;

            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
    }

    @Override
    public void updateInDatabase() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_UPDATE);

            callableStatement.setInt(1, this.employeeID);
            callableStatement.setString(2, this.name);
            callableStatement.setString(3, this.phoneNum);
            callableStatement.setDate(4, this.birthday);
            callableStatement.setString(5, this.email);
            callableStatement.setString(6, this.personalID);
            callableStatement.setString(7, this.password);
            callableStatement.setBoolean(8, this.isMale);
            callableStatement.setDate(9, this.startDate);
            callableStatement.setString(10, this.positionName);
            callableStatement.setBoolean(11, this.isActive);
            callableStatement.setDate(12, this.endDate);

            callableStatement.execute();
            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setEmployeeID(String employeeIDText) {
        this.employeeID = Integer.parseInt(employeeIDText);
    }

    @Override
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String getPositionName() {
        return this.positionName;
    }

    @Override
    public void updateProfile(String updatedEmail, String updatedPhoneNum) {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_UPDATE_PROFILE);

            callableStatement.setInt(1, this.employeeID);
            callableStatement.setString(2, updatedEmail);
            callableStatement.setString(3, updatedPhoneNum);

            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updatePassword(String updatedPlainPassword) {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_UPDATE_PASSWORD);

            callableStatement.setInt(1, this.employeeID);
            
            byte[] salt = Hash.getNextSalt();
            this.password = Hash.doHash(updatedPlainPassword, salt);
            
            callableStatement.setString(2, this.password);
            callableStatement.setBytes(3, salt);

            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<ShiftDetailModelInterface> getShiftDetails() {
        return this.shiftDetails;
    }

    @Override
    public void addShiftDetail(ShiftDetailModelInterface shiftDetail) {
        if (shiftDetail == null) {
            throw new NullPointerException();
        }
        int id = this.shiftDetails.indexOf(shiftDetail);
        if (id >= 0) {
            throw new IllegalArgumentException("Shift detail instance already exists.");
        }
        this.shiftDetails.add(shiftDetail);
        shiftDetail.insertToDatabase();
    }

    @Override
    public void removeShiftDetail(ShiftDetailModelInterface shiftDetail) {
        if (shiftDetail == null) {
            throw new NullPointerException();
        }
        int id = this.shiftDetails.indexOf(shiftDetail);
        if (id == -1) {
            throw new IllegalArgumentException("Shift detail instance doesn't exist.");
        }
        this.shiftDetails.remove(shiftDetail);
        shiftDetail.deleteInDatabase();
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
    public String toString() {
        return "EmployeeModel{" + "employeeID=" + employeeID + ", name=" + name + ", "
                + "phoneNum=" + phoneNum + ", birthday=" + birthday + ", email="
                + email + ", personalID=" + personalID + ", password=" + password + ", "
                + "isMale=" + isMale + ", startDate=" + startDate + ", positionName="
                + positionName + ", isActive=" + isActive + ", endDate=" + endDate
                + '}';
    }
    
    @Override
    public int getRandomPasswordLength() {
        return EmployeeModel.RANDOM_PASSWORD_LENGTH;
    }
    
    @Override
    public boolean isCorrectPassword(String plaintextPassword) {
        CallableStatement callableStatement = null;
        try {
            callableStatement = dbConnection.prepareCall(FC_GET_SALT_FROM_EMAIL);
            callableStatement.setString(2, email);
            callableStatement.registerOutParameter(1, Types.BINARY);
            callableStatement.execute();
            
            byte[] empSalt = callableStatement.getBytes(1);
            String hashedPass = Hash.doHash(plaintextPassword, empSalt);
            callableStatement.close();
            
            return this.password.equals(hashedPass);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
