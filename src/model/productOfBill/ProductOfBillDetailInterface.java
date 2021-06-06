package model.productOfBill;

import model.DatabaseModel;
import model.bill.BillModelInterface;
import model.product.ProductModelInterface;

public interface ProductOfBillDetailInterface extends DatabaseModel {

    void setBill(BillModelInterface bill);
    
    void setProduct(ProductModelInterface product);
    
    void setAmount(int amount);
    
    void setPrice(int price);
    
}
