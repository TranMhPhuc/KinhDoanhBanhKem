package control.bill.create;

import java.util.Iterator;
import model.bill.OfferedProductUpdateObserver;
import model.product.ProductSimpleModelInterface;
import view.bill.BillCreatePanel;

public interface BillCreateControllerInterface extends OfferedProductUpdateObserver {

    void setBillCreatePanel(BillCreatePanel billCreatePanel);
            
    void requestClearProductSearch();

    void requestChooseOfferedProduct();

    void requestRemoveSelectedProduct();

    void requestEditSelectedProductAmount();
    
    void requestClearTableSelectedProduct();

    void requestExportBill();

    void validateGuestMoney();

    void exportBill();
    
    void validateAmountInputFromDialog();

    Iterator<ProductSimpleModelInterface> getProductSearch(String searchText);
    
    Iterator<ProductSimpleModelInterface> getAllProduct();
    
}
