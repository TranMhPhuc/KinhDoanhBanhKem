package control.bill;

import java.util.List;
import model.product.ProductModelInterface;
import org.apache.commons.lang3.tuple.Pair;

public interface BillControllerInterface {

    // Show new bill id (not created yet)
    String getNewBillID();
    
    List<Pair<ProductModelInterface, Integer>> getProductSearch(String searchText);
    
    List<Pair<ProductModelInterface, Integer>> getRemainProduct();
    
    void requestClearSearch();
    
    void requestChooseProduct(int rowID);
    
    void requestRemoveSelectedProduct(int rowID);
    
    void requestClearTableSelect();
    
    void requestExportBill();
    
    void inputGuestMoney(String guestMoneyInput);
    
    void exportBill();
    
}
