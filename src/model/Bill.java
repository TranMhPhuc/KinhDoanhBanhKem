package model;

import java.time.LocalDate;
import java.util.Objects;

public class Bill {

    private int billCode;
    private LocalDate dateSet;
    private int totalCost = 0;
    private int givenMoney = 0;
    private int returnedMoney = 0;
    private Employee employee;

    public Bill() {
    }

    public Bill(int billCode, LocalDate dateSet, int totalCost, int givenMoney, int returnedMoney, Employee employee) {
        this.billCode = billCode;
        this.dateSet = dateSet;
        this.totalCost = totalCost;
        this.givenMoney = givenMoney;
        this.returnedMoney = returnedMoney;
        this.employee = employee;
    }

    public void setBillCode(int billCode) {
        this.billCode = billCode;
    }

    public void setDateSet(LocalDate dateSet) {
        this.dateSet = dateSet;
    }

    public void setTotalCost(int totalCost) throws IllegalArgumentException {
        if (totalCost < 0) {
            throw new IllegalArgumentException("The total cost is negative.");
        }

        this.totalCost = totalCost;
    }

    public void setGivenMoney(int givenMoney) {
        this.givenMoney = givenMoney;
    }

    public void setReturnedMoney(int returnedMoney) {
        this.returnedMoney = returnedMoney;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getBillCode() {
        return billCode;
    }

    public LocalDate getDateSet() {
        return dateSet;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getGivenMoney() {
        return givenMoney;
    }

    public int getReturnedMoney() {
        return returnedMoney;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public String toString() {
        return "Bill{" + "billCode=" + billCode + ", dateSet=" + dateSet + ", totalMoney=" + totalCost + ", givenMoney=" + givenMoney + ", returnedMoney=" + returnedMoney + ", employeeID=" + employee.getEmployeeID() + '}';
    }

}//end Bill
