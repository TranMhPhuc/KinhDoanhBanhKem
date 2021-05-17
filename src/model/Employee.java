package Model;

import static Model.EmployeeStatus.Active;
import java.time.LocalDate;

public class Employee {

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
    private EmployeeStatus status = Active;
    private int numDayOff = 0;
    private String shift;

    public EmployeeStatus getEmployeeStatus() {
        return this.status;
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

    public boolean getGender() {
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

    public int getNumDayOff() {
        return numDayOff;
    }

    public String getShift() {
        return shift;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public void setNumDayOff(int numDayOff) {
        this.numDayOff = numDayOff;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPersonalID(String personalID) {
        this.personalID = personalID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setDateEntry(LocalDate dateEntry) {
        this.dateEntry = dateEntry;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.employeeID;
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
        final Employee other = (Employee) obj;
        if (this.employeeID != other.employeeID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Employee{" + "employeeID=" + employeeID + ", name=" + name + ", phoneNum=" + phoneNum + ", dateOfBirth=" + dateOfBirth + ", email=" + email + ", personalID=" + personalID + ", password=" + password + ", gender=" + gender + ", dateEntry=" + dateEntry + ", position=" + position + ", status=" + status + ", numDayOff=" + numDayOff + ", shift=" + shift + '}';
    }

}
