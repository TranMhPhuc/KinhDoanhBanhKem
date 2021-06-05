package model.bill;

import view.function.bill.BillUpdateObserver;

public interface BillManageModelInterface {

    void registerObserver(BillUpdateObserver observer);
    
    void removeObserver(BillUpdateObserver observer);
    
}
