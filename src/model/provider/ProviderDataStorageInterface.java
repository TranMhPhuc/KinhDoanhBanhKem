package model.provider;

import model.DatabaseUpdate;

public interface ProviderDataStorageInterface extends DatabaseUpdate{

    ProviderModelInterface getProvider(String providerIDText);
    
    int getSize();
    
}
