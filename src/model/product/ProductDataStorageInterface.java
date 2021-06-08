package model.product;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import model.DatabaseUpdate;

public interface ProductDataStorageInterface extends DatabaseUpdate {

    ProductModelInterface getProductByID(String productIDText);
    
    int getSize();
    
    Iterator<ProductModelInterface> createIterator();
    
    Iterator<ProductModelInterface> getProductSearchByName(String searchText);
    
    void add(ProductModelInterface product);
    
    boolean update(ProductModelInterface product);
    
    boolean remove(ProductModelInterface product);
    
}
