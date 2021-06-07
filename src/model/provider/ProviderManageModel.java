package model.provider;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.ingredient.IngredientDataStorage;
import model.ingredient.IngredientDataStorageInterface;
import model.ingredient.IngredientModelInterface;
import util.db.SQLServerConnection;
import view.function.provider.InsertedProviderObserver;
import view.function.provider.ModifiedProviderObserver;
import view.function.provider.RemovedProviderObserver;

public class ProviderManageModel implements ProviderManageModelInterface {

    private volatile static ProviderManageModel uniqueInstance;

    private static Connection dbConnection;

    private static ProviderDataStorageInterface providerDataStorage;
    private static IngredientDataStorageInterface ingredientDataStorage;

    private List<InsertedProviderObserver> insertedProviderObservers;
    private List<ModifiedProviderObserver> modifiedProviderObservers;
    private List<RemovedProviderObserver> removedProviderObservers;

    static {
        dbConnection = SQLServerConnection.getConnection();
        providerDataStorage = ProviderDataStorage.getInstance();
        ingredientDataStorage = IngredientDataStorage.getInstance();
    }

    private ProviderManageModel() {
        insertedProviderObservers = new ArrayList<>();
        modifiedProviderObservers = new ArrayList<>();
        removedProviderObservers = new ArrayList<>();
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

    @Override
    public void registerRemovedProviderObserver(RemovedProviderObserver observer) {
        this.removedProviderObservers.add(observer);
    }

    @Override
    public void removeRemovedProviderObserver(RemovedProviderObserver observer) {
        int id = this.removedProviderObservers.indexOf(observer);
        if (id >= 0) {
            this.removedProviderObservers.remove(id);
        }
    }

    private void notifyInsertedProviderObserver(ProviderModelInterface insertedProvider) {
        for (InsertedProviderObserver observer : insertedProviderObservers) {
            observer.updateInsertedProvider(insertedProvider);
        }
    }

    private void notifyUpdatedProviderObserver(ProviderModelInterface updatedProvider) {
        for (ModifiedProviderObserver observer : modifiedProviderObservers) {
            observer.updateModifiedProvider(updatedProvider);
        }
    }

    private void notifyRemovedProviderObserver(ProviderModelInterface removedProvider) {
        for (RemovedProviderObserver observer : removedProviderObservers) {
            observer.updateRemovedProvider(removedProvider);
        }
    }

    @Override
    public String getNextProviderIDText() {
        ProviderModelInterface provider = providerDataStorage
                .getProviderByIndex(providerDataStorage.getSize() - 1);

        String providerIDText = provider.getProviderIDText();

        String IDPart = providerIDText.substring(2);
        
        int nextID = Integer.parseInt(IDPart) + 1;

        if (nextID < 10) {
            return "HD00" + nextID;
        } else if (nextID < 100) {
            return "HD0" + nextID;
        } else {
            return "HD" + nextID;
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
    public Iterator<ProviderModelInterface> getAllProviderData() {
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
        notifyRemovedProviderObserver(removedProvider);
    }

    @Override
    public ProviderModelInterface getProvider(String providerIDText) {
        return providerDataStorage.getProviderByID(providerIDText);
    }

    @Override
    public boolean isProviderHavingAnyIngredient(ProviderModelInterface provider) {
        if (provider == null) {
            throw new NullPointerException("Provider instance is null.");
        }
        Iterator<IngredientModelInterface> iterator = ingredientDataStorage.createIterator();
        while (iterator.hasNext()) {
            IngredientModelInterface ingredient = iterator.next();
            if (ingredient.getProvider() == provider) {
                return true;
            }
        }
        return false;
    }

}
