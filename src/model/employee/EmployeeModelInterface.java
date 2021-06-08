package model.employee;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import model.DatabaseModel;
import model.employee.position.EmployeePositionModelInterface;
import model.employee.shift.EmployeeShiftModelInterface;

public interface EmployeeModelInterface extends DatabaseModel {

    void setEmployeeID(String employeeIDText);
    
    void setName(String name);
    
    void setPhoneNum(String phoneNum);
    
    void setBirthday(Date birthday);
    
    void setEmail(String email);
    
    void setPersonalID(String personalID);
    
    void setGender(boolean isMale);
    
    void setStartDate(Date startDate);
    
    void setStatus(boolean isActive);
    
    void setEndDate(Date endDate);
    
    void setPosition(EmployeePositionModelInterface position);
    
    void setShift(List<EmployeeShiftModelInterface> shifts);
    
    String getEmployeeIDText();
    
    String getEmployeePositionName();
    
    String getName();
    
    String getPhoneNum();
    
    Date getBirthday();
    
    String getEmail();
    
    String getPersonalID();
    
    boolean getGender();
    
    Date getStartDate();
    
    boolean getStatus();
    
    Date getEndDate();
    
    List<EmployeeShiftModelInterface> getShift();
    
    EmployeePositionModelInterface getPosition();
    
}