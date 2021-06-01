package model.bill;

import java.time.LocalDate;
import java.util.Objects;
import model.employee.Employee;

public class BillModel {

    private int billID;
    private LocalDate dateExport;
    private int payment;
    private int guestMoney;
    private int changeMoney;
    private Employee employee;

    public BillModel(LocalDate dateExport, int payment, int givenMoney, int changeMoney, Employee employee) {
        this.billID = billID;
        this.dateExport = dateExport;
        this.payment = payment;
        this.guestMoney = givenMoney;
        this.changeMoney = changeMoney;
        this.employee = employee;
    }

    public int getBillID() {
        return billID;
    }

    public LocalDate getDateExport() {
        return dateExport;
    }

    public int getPayment() {
        return payment;
    }

    public int getGuestMoney() {
        return guestMoney;
    }

    public int getChangeMoney() {
        return changeMoney;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public void setDateExport(LocalDate dateExport) {
        this.dateExport = dateExport;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public void setGuestMoney(int givenMoney) {
        this.guestMoney = givenMoney;
    }

    public void setChangeMoney(int changeMoney) {
        this.changeMoney = changeMoney;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Bill{" + "billID=" + billID + ", dateExport=" + dateExport + ", payment=" + payment + ", givenMoney=" + guestMoney + ", changeMoney=" + changeMoney + ", employee=" + employee + '}';
    }

    public static void main(String[] args) {
        
    }
}
