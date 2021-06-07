package model.product;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import model.DatabaseUpdate;
import view.function.product.ProductUpdateObserver;

public interface ProductDataStorageInterface extends DatabaseUpdate {

    ProductModelInterface getProduct(String productIDText);
    
    ProductModelInterface createProduct();
    
    int getSize();
    
    void registerObserver(ProductUpdateObserver observer);
    
    void removeObserver(ProductUpdateObserver observer);
    
    List<ProductModelInterface> getProductSearchByName(String searchText);
    
    List<ProductModelInterface> getAllProduct();
    
    Iterator<ProductModelInterface> createIterator();
}
