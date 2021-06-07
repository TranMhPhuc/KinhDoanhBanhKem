package model.provider;

import java.util.Iterator;
import view.function.provider.InsertedProviderObserver;
import view.function.provider.ModifiedProviderObserver;
import view.function.provider.RemovedProviderObserver;

public interface ProviderManageModelInterface {

    void registerInsertedProviderObserver(InsertedProviderObserver observer);

    void removeInsertedProviderObserver(InsertedProviderObserver observer);
    
    void registerModifiedProviderObserver(ModifiedProviderObserver observer);

    void removeModifiedProviderObserver(ModifiedProviderObserver observer);
    
    void registerRemovedProviderObserver(RemovedProviderObserver observer);

    void removeRemovedProviderObserver(RemovedProviderObserver observer);
    
    //=========================================================================

    String getNextProviderIDText();

    //=========================================================================
    
    void exportProviderData();

    void importProviderData();
    
    //=========================================================================

    void addNewProvider(ProviderModelInterface newProvider);
    
    void updateProvider(ProviderModelInterface updatedProvider);
    
    void removeProvider(ProviderModelInterface removedProvider);
    
    //=========================================================================
    
    ProviderModelInterface getProvider(String providerIDText);

    Iterator<ProviderModelInterface> getAllProviderData();
    
    Iterator<ProviderModelInterface> getProviderSearchByName(String searchText);
    
    boolean isProviderNameExist(String providerName);
    
    boolean isProviderPhoneNumExist(String providerPhoneNum);
    
    boolean isProviderEmailExist(String providerEmail);
    
    boolean isProviderAddressExist(String providerAddress);
    
    boolean isProviderHavingAnyIngredient(ProviderModelInterface provider);
}
