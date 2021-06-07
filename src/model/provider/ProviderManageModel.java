package model.provider;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.db.SQLServerConnection;
import view.function.provider.InsertedProviderObserver;
import view.function.provider.ModifiedProviderObserver;

public class ProviderManageModel implements ProviderManageModelInterface {

    private volatile static ProviderManageModel uniqueInstance;

    private static Connection dbConnection;

    private static ProviderDataStorageInterface providerDataStorage;

    private List<InsertedProviderObserver> insertedProviderObservers;
    private List<ModifiedProviderObserver> modifiedProviderObservers;

    static {
        dbConnection = SQLServerConnection.getConnection();
        providerDataStorage = ProviderDataStorage.getInstance();
    }

    private ProviderManageModel() {
        insertedProviderObservers = new ArrayList<>();
        modifiedProviderObservers = new ArrayList<>();
    }

    public static ProviderManageModelInterface getInstance() {
        if (uniqueInstance == null) {
            synchronized (ProviderManageModel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ProviderManageModel();
                }
            }
        }
        return uniqueInstance;
    }

    @Override
    public void registerInsertedProviderObserver(InsertedProviderObserver observer) {
        this.insertedProviderObservers.add(observer);
    }

    @Override
    public void removeInsertedProviderObserver(InsertedProviderObserver observer) {
        int id = insertedProviderObservers.indexOf(observer);
        if (id >= 0) {
            insertedProviderObservers.remove(id);
        }
    }

    @Override
    public void registerModifiedProviderObserver(ModifiedProviderObserver observer) {
        this.modifiedProviderObservers.add(observer);
    }

    @Override
    public void removeModifiedProviderObserver(ModifiedProviderObserver observer) {
        int id = modifiedProviderObservers.indexOf(observer);
        if (id >= 0) {
            this.modifiedProviderObservers.remove(id);
        }
    }

    private void notifyInsertedProviderObserver(ProviderModelInterface insertedProvider) {
        for (InsertedProviderObserver observer : insertedProviderObservers) {
            observer.updateInsertedProvider(insertedProvider);
        }
    }

    /**
     *
     * @param rowID -- row index of search result in view
     * @param updatedProvider
     */
    private void notifyUpdatedProviderObserver(ProviderModelInterface updatedProvider) {
        for (ModifiedProviderObserver observer : modifiedProviderObservers) {
            observer.updateModifiedProvider(updatedProvider);
        }
    }

    @Override
    public String getNextProviderIDText() {
        int nextSize = providerDataStorage.getSize() + 1;
        if (nextSize < 10) {
            return "HD00" + nextSize;
        } else if (nextSize < 100) {
            return "HD0" + nextSize;
        } else {
            return "HD" + nextSize;
        }
    }

    @Override
    public void exportProviderData() {
        // XXX

    }

    @Override
    public void importProviderData() {
        // XXX

    }

    @Override
    public Iterator<ProviderModelInterface> getAllProvider() {
        return providerDataStorage.createIterator();
    }

    @Override
    public Iterator<ProviderModelInterface> getProviderSearchByName(String searchText) {
        return providerDataStorage.getProviderSearchByName(searchText);
    }

    @Override
    public boolean isProviderNameExist(String providerName) {
        return providerDataStorage.isProviderNameExisted(providerName);
    }

    @Override
    public boolean isProviderPhoneNumExist(String providerPhoneNum) {
        return providerDataStorage.isProviderPhoneNumExist(providerPhoneNum);
    }

    @Override
    public boolean isProviderEmailExist(String providerEmail) {
        return providerDataStorage.isProviderEmailExisted(providerEmail);
    }

    @Override
    public boolean isProviderAddressExist(String providerAddress) {
        return providerDataStorage.isProviderAddressExisted(providerAddress);
    }

    @Override
    public void addNewProvider(ProviderModelInterface newProvider) {
        providerDataStorage.add(newProvider);
        notifyInsertedProviderObserver(newProvider);
    }

    @Override
    public void updateProvider(ProviderModelInterface updatedProvider) {
        providerDataStorage.update(updatedProvider);
        notifyUpdatedProviderObserver(updatedProvider);
    }

    @Override
    public void removeProvider(ProviderModelInterface removedProvider) {
        providerDataStorage.remove(removedProvider);
    }

    @Override
    public ProviderModelInterface getProvider(String providerIDText) {
        return providerDataStorage.getProviderByID(providerIDText);
    }

}
