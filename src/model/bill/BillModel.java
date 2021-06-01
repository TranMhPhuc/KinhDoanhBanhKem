package model.bill;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import model.employee.EmployeeModel;

public class BillModel implements BillModelInterface {

    private int billID;
    private LocalDate dateExport;
    private int payment;
    private int guestMoney;
    private int changeMoney;
    private EmployeeModel employee;
    
    private BillDetailModel billDetail;

    public BillModel(LocalDate dateExport, int payment, int givenMoney, int changeMoney, EmployeeModel employee) {
        this.billID = billID;
        this.dateExport = dateExport;
        this.payment = payment;
        this.guestMoney = givenMoney;
        this.changeMoney = changeMoney;
        this.employee = employee;
    }

    private static void updateFromDB() {

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

    public EmployeeModel getEmployee() {
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

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Bill{" + "billID=" + billID + ", dateExport=" + dateExport + ", payment=" + payment + ", givenMoney=" + guestMoney + ", changeMoney=" + changeMoney + ", employee=" + employee + '}';
    }

    @Override
    public void notifyObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {

    }
}
