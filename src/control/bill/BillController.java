package control.bill;

import java.time.LocalDate;
import model.bill.BillModelInterface;
import model.product.ProductModel;
import view.function.bill.BillCreatePanel;
import view.function.bill.BillHistoryPanel;

public class BillController implements BillControllerInterface {

    private volatile static BillController uniqueInstance;

    private BillModelInterface model;
    private BillCreatePanel billCreatePanel;
    private BillHistoryPanel billHistoryPanel;

    private BillController(BillModelInterface model) {
        this.model = model;
        this.billCreatePanel = BillCreatePanel.getInstance(model, this);
        this.billHistoryPanel = BillHistoryPanel.getInstance(this);
    }

    public static BillControllerInterface getInstance(BillModelInterface model) {
        if (uniqueInstance == null) {
            synchronized (BillController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new BillController(model);
                }
            }
        }
        return uniqueInstance;
    }

    public static BillController getInstance() {
        if (uniqueInstance == null) {
            throw new NullPointerException();
        }
        return uniqueInstance;
    }

    public void exportBill() {

    }

    public void getBillData(LocalDate dateFrom, LocalDate dateTo) {

    }

}
