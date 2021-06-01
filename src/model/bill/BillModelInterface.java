package model.bill;

import view.function.product.ProductViewObserver;

public interface BillModelInterface {

    void notifyObserver();

    /**
     * Update product amount when new bill created.
     * @param productViewObserver 
     */
    void registerObserver(ProductViewObserver productViewObserver);

    void removeObserver(ProductViewObserver productViewObserver);
}
