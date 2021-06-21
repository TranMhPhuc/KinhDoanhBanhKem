package model.provider;

import java.util.Iterator;
import model.DatabaseUpdate;
import view.provider.InsertedProviderObserver;
import view.provider.ModifiedProviderObserver;
import view.provider.RemovedProviderObserver;

public interface ProviderManageModelInterface extends DatabaseUpdate {

    void registerInsertedProviderObserver(InsertedProviderObserver observer);

    void removeInsertedProviderObserver(InsertedProviderObserver observer);
    
    void registerModifiedProviderObserver(ModifiedProviderObserver observer);

    void removeModifiedProviderObserver(ModifiedProviderObserver observer);
    
    void registerRemovedProviderObserver(RemovedProviderObserver observer);

    void removeRemovedProviderObserver(RemovedProviderObserver observer);
    
    //=========================================================================

    String getNextProviderIDText();

    //=========================================================================

    void addProvider(ProviderModelInterface newProvider);
    
    boolean updateProvider(ProviderModelInterface updatedProvider);
    
    boolean removeProvider(ProviderModelInterface removedProvider);
    
    //=========================================================================
    
    ProviderModelInterface getProviderByID(String providerIDText);
    
    Iterator<ProviderModelInterface> getAllProviderData();
    
    Iterator<ProviderModelInterface> getProviderSearchByName(String searchText);
    
}
