package model.provider;

import java.util.Iterator;
import model.DatabaseUpdate;

public interface ProviderDataStorageInterface extends DatabaseUpdate {

    ProviderModelInterface getProviderByID(String providerIDText);

    ProviderModelInterface getProvider(int providerIndex);

    int getSize();

    Iterator<ProviderModelInterface> createIterator();
    
    Iterator<ProviderModelInterface> getProviderSearchByName(String searchText);

    boolean remove(ProviderModelInterface provider);

    void add(ProviderModelInterface provider);

    boolean update(ProviderModelInterface updatedProvider);

    boolean isProviderNameExisted(String providerName);

    boolean isProviderEmailExisted(String providerEmail);

    boolean isProviderAddressExisted(String providerAddress);
    
    boolean isProviderPhoneNumExist(String providerPhoneNum);

}
