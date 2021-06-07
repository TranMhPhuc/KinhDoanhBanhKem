package model.provider;

import java.util.Iterator;
import model.DatabaseUpdate;

public interface ProviderDataStorageInterface extends DatabaseUpdate {

    ProviderModelInterface getProviderByID(String providerIDText);

    ProviderModelInterface getProviderByIndex(int providerIndex);

    int getSize();

    Iterator<ProviderModelInterface> createIterator();
    
    Iterator<ProviderModelInterface> getProviderSearchByName(String searchText);

    void add(ProviderModelInterface provider);

    boolean update(ProviderModelInterface updatedProvider);
    
    boolean remove(ProviderModelInterface provider);

    boolean isProviderNameExisted(String providerName);

    boolean isProviderEmailExisted(String providerEmail);

    boolean isProviderAddressExisted(String providerAddress);
    
    boolean isProviderPhoneNumExist(String providerPhoneNum);

}
