package model.bill;

import java.util.ArrayList;
import view.function.bill.BillUpdateObserver;

public class BillManageModel implements BillManageModelInterface {

    private ArrayList<BillUpdateObserver> observers;
    
    public BillManageModel() {
        observers = new ArrayList<>();
    }
    
    @Override
    public void registerObserver(BillUpdateObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(BillUpdateObserver observer) {
        int id = observers.indexOf(observer);
        if (id >= 0) {
            observers.remove(observer);
        }
    }
    
    private void notifyObserver(int billNumber) {
        for (BillUpdateObserver observer : observers) {
            observer.updateBillNumber(billNumber);
        }
    }

}
