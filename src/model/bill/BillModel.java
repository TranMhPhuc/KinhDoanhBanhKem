package model.bill;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.employee.EmployeeManageModel;
import model.employee.EmployeeManageModelInterface;
import model.employee.EmployeeModel;

public class BillModel implements BillModelInterface {

    public static final String TABLE_NAME = "HoaDon";
    public static final String ID_HEADER = "MaHD";
    public static final String DATE_HEADER = "NgayLap";
    public static final String PAYMENT_HEADER = "TongTien";
    public static final String GUEST_MONEY_HEADER = "TienKhachTra";
    public static final String CHANGE_MONEY_HEADER = "TienThoi";
    public static final String EMPLOYEE_NAME_HEADER = EmployeeModel.NAME_HEADER;

    private static final String SP_INSERT
            = "{call insert_HoaDon(?, ?, ?, ?)}";

    private static EmployeeManageModelInterface employeeManageModel;

    private int id;
    private Timestamp dateTimeExport;
    private long payment;
    private long guestMoney;
    private long changeMoney;
    private String employeeName;

    static {
    }

    public BillModel() {
    }

    @Override
    public void setProperty(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt("MaHD");
            this.dateTimeExport = resultSet.getTimestamp("NgayLap");
            this.payment = resultSet.getInt("TongTien");
            this.guestMoney = resultSet.getInt("TienKhachTra");
            this.changeMoney = resultSet.getInt("TienThoi");
            this.employeeName = resultSet.getString(EMPLOYEE_NAME_HEADER);
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
            } else if (header.equals(DATE_HEADER)) {
                preparedStatement.setTimestamp(index, this.dateTimeExport);
            } else if (header.equals(PAYMENT_HEADER)) {
                preparedStatement.setLong(index, this.payment);
            } else if (header.equals(GUEST_MONEY_HEADER)) {
                preparedStatement.setLong(index, this.guestMoney);
            } else if (header.equals(CHANGE_MONEY_HEADER)) {
                preparedStatement.setLong(index, this.changeMoney);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertToDatabase() {
        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_INSERT);

            callableStatement.setTimestamp(1, this.dateTimeExport);
            callableStatement.setLong(2, this.payment);
            callableStatement.setLong(3, this.guestMoney);
            callableStatement.setString(4, this.employeeName);

            callableStatement.execute();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(BillModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteInDatabase() {
    }

    @Override
    public void updateInDatabase() {
    }

    @Override
    public void setDateTimeExport(Timestamp dateTimeExport) {
        this.dateTimeExport = dateTimeExport;
    }

    @Override
    public void setPayment(long payment) {
        this.payment = payment;
    }

    @Override
    public void setGuestMoney(long guestMoney) {
        this.guestMoney = guestMoney;
    }

    @Override
    public void setChangeMoney(long changeMoney) {
        this.changeMoney = changeMoney;
    }

    @Override
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public Timestamp getDateTimeExport() {
        return this.dateTimeExport;
    }

    @Override
    public long getPayment() {
        return this.payment;
    }

    @Override
    public long getGuestMoney() {
        return this.guestMoney;
    }

    @Override
    public long getChangeMoney() {
        return this.changeMoney;
    }

    @Override
    public String getEmployeeName() {
        return this.employeeName;
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
    public String toString() {
        return "Bill{" + "billID=" + id + ", dateExport=" + dateTimeExport
                + ", payment=" + payment + ", givenMoney=" + guestMoney + ", changeMoney="
                + changeMoney + ", employeeName=" + employeeName + '}';
    }

}
