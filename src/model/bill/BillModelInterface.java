package model.bill;

public interface BillModelInterface {

    void notifyObserver();

    void registerObserver();

    void removeObserver();
}
