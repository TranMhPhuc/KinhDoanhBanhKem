package model.productOfBill;

import model.DatabaseModel;
import model.bill.BillModelInterface;

public interface ProductOfBillDetailInterface extends DatabaseModel {

    void setBill(BillModelInterface bill);
    
}
