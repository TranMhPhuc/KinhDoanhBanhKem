package model.product;

import model.DatabaseUpdate;
import view.function.product.ProductUpdateObserver;

public interface ProductDataStorageInterface extends DatabaseUpdate {

    ProductModelInterface getProduct(String productIDText);
    
    ProductModelInterface createProduct();
    
    int getSize();
    
    void registerObserver(ProductUpdateObserver observer);
    
    void removeObserver(ProductUpdateObserver observer);
}
