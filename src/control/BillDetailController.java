package control;

import java.util.ArrayList;
import model.BillDetail;
import model.Cake;
import org.javatuples.Pair;

public class BillDetailController {

    private BillDetailController() {
    }

    public static void addProduct(BillDetail billDetail, Cake cake) {
        billDetail.addProduct(cake);
    }

    public static void removeProduct(BillDetail billDetail, Cake cake) {
        billDetail.removeProduct(cake);
    }

    public static void getUpdateView(BillDetail billDetail, Cake cake, String[] view)
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
}
