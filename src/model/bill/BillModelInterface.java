package model.bill;

import model.productOfBill.ProductOfBillDetailInterface;
import view.function.product.ProductViewObserver;

public interface BillModelInterface {

    String getBillIDText();
    
    void addProductDetail(ProductOfBillDetailInterface productOfBillDetailInterface);
    
    void notifyObserver();

    /**
     * Update product amount when new bill created.
     * @param productViewObserver 
     */
    void registerObserver(ProductViewObserver productViewObserver);

    void removeObserver(ProductViewObserver productViewObserver);
}
