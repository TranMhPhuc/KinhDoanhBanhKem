package model.employee;

import java.time.LocalDate;

public class EmployeeModel {

    private int employeeID;
    private String name;
    private int phoneNum;
    private LocalDate dateOfBirth;
    private String email;
    private String personalID;
    private String password;
    private boolean gender = true;
    private LocalDate dateEntry;
    private String position;
    private EmployeeStatus status = EmployeeStatus.Active;
    private LocalDate numDayOff;
    private EmployeeShift shift[];

    public EmployeeModel() {
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getName() {
        return name;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getPersonalID() {
        return personalID;
    }

    public String getPassword() {
        return password;
    }

    public boolean isGender() {
        return gender;
    }

    public LocalDate getDateEntry() {
        return dateEntry;
    }

    public String getPosition() {
        return position;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public LocalDate getNumDayOff() {
        return numDayOff;
    }

    public EmployeeShift[] getShift() {
        return shift;
    }

    

}
