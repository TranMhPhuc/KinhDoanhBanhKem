package model.bill;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.EmployeeDataStorage;
import model.employee.EmployeeDataStorageInterface;
import model.employee.EmployeeModel;
import model.employee.EmployeeModelInterface;

public class BillModel implements BillModelInterface {

    public static final String TABLE_NAME = "HoaDon";
    public static final String ID_HEADER = "MaHD";
    public static final String DATE_HEADER = "NgayLap";
    public static final String PAYMENT_HEADER = "TongTien";
    public static final String GUEST_MONEY_HEADER = "TienKhachTra";
    public static final String CHANGE_MONEY_HEADER = "TienThoi";
    public static final String EMPLOYEE_ID_HEADER = EmployeeModel.ID_HEADER;

    private static final String INSERT_QUERY_PROTOTYPE
            = "INSERT INTO " + TABLE_NAME + " ("
            + DATE_HEADER + ", " + PAYMENT_HEADER + ", "
            + GUEST_MONEY_HEADER + ", " + CHANGE_MONEY_HEADER + ", "
            + EMPLOYEE_ID_HEADER + ")"
            + " VALUES (?, ?, ?, ?, ?)";

    private static final EmployeeDataStorageInterface employeeDataStorage;

    private int id;
    private Timestamp dateTimeExport;
    private int payment;
    private int guestMoney;
    private int changeMoney;
    private EmployeeModelInterface employee;

    static {
        employeeDataStorage = EmployeeDataStorage.getInstance();
    }

    public BillModel() {
    }

    public BillModel(BillModel other) {
        this.id = other.id;
        this.dateTimeExport = other.dateTimeExport;
        this.payment = other.payment;
        this.guestMoney = other.guestMoney;
        this.changeMoney = other.changeMoney;
        this.employee = other.employee;
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt("MaHD");
            this.dateTimeExport = resultSet.getTimestamp("NgayLap");
            this.payment = resultSet.getInt("TongTien");
            this.guestMoney = resultSet.getInt("TienKhachTra");
            this.changeMoney = resultSet.getInt("TienThoi");
            this.employee = employeeDataStorage
                    .getEmployee(resultSet.getString(EMPLOYEE_ID_HEADER));
        } catch (SQLException ex) {
            Logger.getLogger(BillModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setBillID(int id) {
        this.id = id;
    }

    @Override
    public String getBillIDText() {
        return String.valueOf(this.id);
    }

    @Override
    public int getBillID() {
        return this.id;
    }

    @Override
    public void setKeyArg(int index, String header, PreparedStatement preparedStatement) {
        try {
            if (header.equals(ID_HEADER)) {
                preparedStatement.setInt(index, this.id);
            } else if (header.equals(ID_HEADER)) {
                preparedStatement.setTimestamp(index, this.dateTimeExport);
            } else if (header.equals(DATE_HEADER)) {
                preparedStatement.setInt(index, this.payment);
            } else if (header.equals(PAYMENT_HEADER)) {
                preparedStatement.setInt(index, this.payment);
            } else if (header.equals(GUEST_MONEY_HEADER)) {
                preparedStatement.setInt(index, this.guestMoney);
            } else if (header.equals(CHANGE_MONEY_HEADER)) {
                preparedStatement.setInt(index, this.changeMoney);
            } else if (header.equals(EMPLOYEE_ID_HEADER)) {
                this.employee.setKeyArg(index, EmployeeModel.ID_HEADER, preparedStatement);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(INSERT_QUERY_PROTOTYPE);
            // ID is indentity column

            preparedStatement.setTimestamp(1, this.dateTimeExport);
            preparedStatement.setInt(2, this.payment);
            preparedStatement.setInt(3, this.guestMoney);
            preparedStatement.setInt(4, this.changeMoney);
            this.employee.setKeyArg(5, EmployeeModel.ID_HEADER, preparedStatement);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(BillModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateInDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.id;
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
        final BillModel other = (BillModel) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public void setDateTimeExport(Timestamp dateTimeExport) {
        this.dateTimeExport = dateTimeExport;
    }

    @Override
    public void setPayment(int payment) {
        this.payment = payment;
    }

    @Override
    public void setGuestMoney(int guestMoney) {
        this.guestMoney = guestMoney;
    }

    @Override
    public void setChangeMoney(int changeMoney) {
        this.changeMoney = changeMoney;
    }

    @Override
    public void setEmployee(EmployeeModelInterface employee) {
        this.employee = employee;
    }

    @Override
    public Timestamp getDateTimeExport() {
        return this.dateTimeExport;
    }

    @Override
    public int getPayment() {
        return this.payment;
    }

    @Override
    public int getGuestMoney() {
        return this.guestMoney;
    }

    @Override
    public int getChangeMoney() {
        return this.changeMoney;
    }

    @Override
    public EmployeeModelInterface getEmployee() {
        return this.employee;
    }

    @Override
    public String toString() {
        return "Bill{" + "billID=" + id + ", dateExport=" + dateTimeExport
                + ", payment=" + payment + ", givenMoney=" + guestMoney + ", changeMoney="
                + changeMoney + ", employee=" + employee + '}';
    }
}
