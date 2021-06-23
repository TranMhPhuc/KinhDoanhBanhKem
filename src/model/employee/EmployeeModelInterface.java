package model.employee;

import java.sql.Date;
import java.util.List;
import model.DatabaseModel;
import model.employee.shift.detail.ShiftDetailModelInterface;

public interface EmployeeModelInterface extends DatabaseModel {

    void setEmployeeID(String employeeIDText);
    
    void setName(String name);
    
    void setPhoneNum(String phoneNum);
    
    void setBirthday(Date birthday);
    
    void setEmail(String email);
    
    void setPersonalID(String personalID);
    
    void setPassword(String password);
    
    void setGender(boolean isMale);
    
    void setStartDate(Date startDate);
    
    void setStatus(boolean isActive);
    
    void setEndDate(Date endDate);
    
    void setPositionName(String positionName);
    
    String getEmployeeIDText();
    
    String getPositionName();
    
    String getName();
    
    String getPassword();
    
    String getPhoneNum();
    
    Date getBirthday();
    
    String getEmail();
    
    String getPersonalID();
    
    boolean getGender();
    
    Date getStartDate();
    
    boolean getStatus();
    
    Date getEndDate();
    
    String randomPassword();
    
    List<ShiftDetailModelInterface> getShiftDetails();
    
    void addShiftDetail(ShiftDetailModelInterface shiftDetail);
    
    void removeShiftDetail(ShiftDetailModelInterface shiftDetail);
    
    //==========================================================================
    
    void updateProfile(String updatedEmail, String updatedPhoneNum);
    
    void updatePassword(String updatedPlainPassword);
    
    int getRandomPasswordLength();
    
    boolean isCorrectPassword(String plaintextPassword);
    
}