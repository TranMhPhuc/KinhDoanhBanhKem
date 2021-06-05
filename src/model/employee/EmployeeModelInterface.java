package model.employee;

import java.sql.Date;
import java.util.ArrayList;
import model.DatabaseModel;
import model.employee.shift.EmployeeShiftModelInterface;

public interface EmployeeModelInterface extends DatabaseModel {

    void setEmployeeID(int id);
    
    void setName(String name);
    
    void setPhoneNum(int phoneNum);
    
    void setBirthday(Date birthday);
    
    void setEmail(String email);
    
    void setPersonalID(String personalID);
    
    void setGender(boolean isMale);
    
    void setStartDate(Date startDate);
    
    void setPosition(String positionName);
    
    void setStatus(boolean isActive);
    
    void setEndDate(Date endDate);
    
    void setShift(ArrayList<EmployeeShiftModelInterface> shifts);
    
    int getEmployeeID();
    
    String getEmployeeIDText();
    
    String getEmployeePositionName();
    
    String getEmployeeName();
    
    int getPhoneNum();
    
    Date getBirthday();
    
    String getEmail();
    
    String getPersonalID();
    
    boolean getGender();
    
    Date getStartDate();
    
    boolean getStatus();
    
    Date getEndDate();
    
}