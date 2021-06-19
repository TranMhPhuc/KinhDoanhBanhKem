package view.bill;

import model.bill.BillModelInterface;
import model.bill.detail.ProductDetailModelInterface;

public interface BillUpdateObserver {

    void updateFromNewBillCreated(BillModelInterface bill);
    
    void updateFromBillState();
    
    void updateFromModifiedSelectedProduct(ProductDetailModelInterface productDetail);
    
    void updateFromInsertedSelectedProduct(ProductDetailModelInterface productDetail);
    
    void updateFromDeletedSelectedProduct(int rowID);
    
}
