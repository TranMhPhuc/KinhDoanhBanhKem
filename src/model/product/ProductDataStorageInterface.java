package model.product;

import model.DatabaseUpdate;

public interface ProductDataStorageInterface extends DatabaseUpdate {

    ProductModelInterface getProduct(String productIDText);
    
    ProductModelInterface createProduct();
    
    int getSize();
}
