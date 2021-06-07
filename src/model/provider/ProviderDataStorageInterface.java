package model.provider;

import java.util.Iterator;
import model.DatabaseUpdate;

public interface ProviderDataStorageInterface extends DatabaseUpdate{

    ProviderModelInterface getProvider(String providerIDText);
    
    ProviderModelInterface getProvider(int providerIndex);
    
    int getSize();
    
    Iterator<ProviderModelInterface> createIterator();
}
