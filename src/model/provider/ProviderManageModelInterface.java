package model.provider;

import java.util.Iterator;
import view.function.provider.InsertedProviderObserver;
import view.function.provider.ModifiedProviderObserver;

public interface ProviderManageModelInterface {

    void registerInsertedProviderObserver(InsertedProviderObserver observer);

    void removeInsertedProviderObserver(InsertedProviderObserver observer);
    
    void registerModifiedProviderObserver(ModifiedProviderObserver observer);

    void removeModifiedProviderObserver(ModifiedProviderObserver observer);

    String getNextProviderIDText();

    //=========================================================================
    void exportProviderData();

    void importProviderData();
    
    //=========================================================================

    void addNewProvider(ProviderModelInterface newProvider);
    
    ProviderModelInterface getProvider(String providerIDText);
    
    void updateProvider(ProviderModelInterface updatedProvider);
    
    void removeProvider(ProviderModelInterface removedProvider);
    //=========================================================================

    Iterator<ProviderModelInterface> getAllProvider();
    
    Iterator<ProviderModelInterface> getProviderSearchByName(String searchText);
    
    boolean isProviderNameExist(String providerName);
    
    boolean isProviderPhoneNumExist(String providerPhoneNum);
    
    boolean isProviderEmailExist(String providerEmail);
    
    boolean isProviderAddressExist(String providerAddress);
}
