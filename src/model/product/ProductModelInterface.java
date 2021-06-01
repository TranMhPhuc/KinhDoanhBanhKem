package model.product;

public interface ProductModelInterface {
    String getProductIDText();
    
    void registerObserver();
    
    void removeObserver();
    
    void notifyObserver();
}
