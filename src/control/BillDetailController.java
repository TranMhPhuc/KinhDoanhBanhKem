package control;

import java.time.LocalDate;
import java.util.ArrayList;
import model.bill.BillDetailModel;
import model.product.Product;
import org.javatuples.Pair;

public class BillDetailController {

    private BillDetailController() {
    }

    public static void addProduct(BillDetailModel billDetail, Product cake) {
        billDetail.addProduct(cake);
    }

    public static void removeProduct(BillDetailModel billDetail, Product cake) {
        billDetail.removeProduct(cake);
    }

    public static void getUpdateView(BillDetailModel billDetail, Product cake, String[] view)
            throws IllegalArgumentException {
        if (!billDetail.havingProduct(cake)) {
            throw new IllegalArgumentException("Cake code is not in bill.");
        }
        
    }

    /**
     * Save bill data to database and export it.
     */
    public void exportBill() {

    }
    
    public void getBillData(LocalDate dateFrom, LocalDate dateTo) {
        
    }
    
}
