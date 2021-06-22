package model.bill;

import java.util.Iterator;
import model.DatabaseUpdate;
import model.bill.detail.ProductDetailModelInterface;
import model.product.ProductSimpleModelInterface;
import view.bill.BillInsertedObserver;
import view.bill.BillUpdateObserver;

public interface BillCreateModelInterface extends DatabaseUpdate {

    void registerBillUpdateObserver(BillUpdateObserver observer);

    void removeBillUpdateObserver(BillUpdateObserver observer);
    
    void registerOfferedProductUpdateObserver(OfferedProductUpdateObserver observer);

    void removeOfferedProductUpdateObserver(OfferedProductUpdateObserver observer);
    
    void registerInsertedBillObserver(BillInsertedObserver observer);

    void removeInsertedBillObserver(BillInsertedObserver observer);

    String getNextBillIDText();

    void createBill();

    boolean isBillHavingNoProduct();

    void setGuestMoney(long guestMoney);

    long getTotalMoney();

    long getChangeMoney();
    
    int getOriginAmountOfProduct(ProductDetailModelInterface productDetail);

    Iterator<ProductSimpleModelInterface> getSearchByProductName(String searchText);

    Iterator<ProductSimpleModelInterface> getAllProduct();
    
    Iterator<ProductDetailModelInterface> getSelectedProductData();
    
    ProductDetailModelInterface getSelectedProductByIndex(int rowID);

    void addOfferedProductToBill(ProductSimpleModelInterface product);
    
    void removeSeletedProductInBill(int selectedProductIndex);
    
    void updateAmountOfSelectedProduct(int selectedProductIndex, int newAmount);
    
    void clearSelectedProductData();
    
}
