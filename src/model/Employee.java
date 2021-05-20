package model;

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
    private EmployeeStatus status = EmployeeStatus.Active;
    private LocalDate numDayOff;
    private WorkShift shift[];

    public Employee() {
    }


}
