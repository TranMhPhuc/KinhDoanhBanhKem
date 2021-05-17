package Model;

import java.time.LocalDate;
import java.util.Objects;

public class Bill {

    private String billCode;
    private LocalDate dateSet;
    private int totalMoney;
    private int givenMoney;
    private int returnedMoney;
    private String employeeID;

    public Bill() {

    }

    public Bill(String billCode, LocalDate dateSet, int totalMoney, int givenMoney, int returnedMoney, String employeeID) {
        this.billCode = billCode;
        this.dateSet = dateSet;
        this.totalMoney = totalMoney;
        this.givenMoney = givenMoney;
        this.returnedMoney = returnedMoney;
        this.employeeID = employeeID;
    }

    public String getBillCode() {
        return billCode;
    }

    public LocalDate getDateSet() {
        return dateSet;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public int getGivenMoney() {
        return givenMoney;
    }

    public int getReturnedMoney() {
        return returnedMoney;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public void setDateSet(LocalDate dateSet) {
        this.dateSet = dateSet;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void setGivenMoney(int givenMoney) {
        this.givenMoney = givenMoney;
    }

    public void setReturnedMoney(int returnedMoney) {
        this.returnedMoney = returnedMoney;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
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
        final Bill other = (Bill) obj;
        if (!Objects.equals(this.billCode, other.billCode)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.billCode);
        return hash;
    }

    @Override
    public String toString() {
        return "Bill{" + "billCode=" + billCode + ", dateSet=" + dateSet + ", totalMoney=" + totalMoney + ", givenMoney=" + givenMoney + ", returnedMoney=" + returnedMoney + ", employeeID=" + employeeID + '}';
    }

}//end Bill
