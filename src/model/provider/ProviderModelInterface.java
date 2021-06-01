package model.provider;

public interface ProviderModelInterface {
    
    String getIDText();
    
    void notifyObserver();
    
    void registerObserver();
    
    void removeObserver();
    
}
