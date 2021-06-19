package control.bill.create;

import java.util.Iterator;
import java.util.List;
import model.bill.OfferedProductUpdateObserver;
import model.product.ProductModelInterface;
import model.product.ProductSimpleModelInterface;
import org.apache.commons.lang3.tuple.Pair;
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
