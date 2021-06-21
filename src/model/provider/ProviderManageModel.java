package model.provider;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import util.constant.AppConstant;
import view.provider.InsertedProviderObserver;
import view.provider.ModifiedProviderObserver;
import view.provider.RemovedProviderObserver;

public class ProviderManageModel implements ProviderManageModelInterface {

    private static final String SP_GET_ALL_PROVIDER
            = "{call get_all_providers}";

    private ArrayList<ProviderModelInterface> providers;

    private List<InsertedProviderObserver> insertedProviderObservers;
    private List<ModifiedProviderObserver> modifiedProviderObservers;
    private List<RemovedProviderObserver> removedProviderObservers;

    public ProviderManageModel() {
        providers = new ArrayList<>();

        insertedProviderObservers = new ArrayList<>();
        modifiedProviderObservers = new ArrayList<>();
        removedProviderObservers = new ArrayList<>();

        updateFromDB();
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
            observer.updateInsertedProviderObserver(insertedProvider);
        }
    }

    private void notifyUpdatedProviderObserver(ProviderModelInterface updatedProvider) {
        for (ModifiedProviderObserver observer : modifiedProviderObservers) {
            observer.updateModifiedProviderObserver(updatedProvider);
        }
    }

    private void notifyRemovedProviderObserver(ProviderModelInterface removedProvider) {
        for (RemovedProviderObserver observer : removedProviderObservers) {
            observer.updateRemovedProviderObserver(removedProvider);
        }
    }

    @Override
    public String getNextProviderIDText() {
        int currSize = providers.size();

        int nextID;

        if (currSize == 0) {
            nextID = 1;
        } else {
            ProviderModelInterface provider = providers.get(currSize - 1);

            String providerIDText = provider.getProviderIDText();

            String IDPart = providerIDText.substring(2);

            nextID = Integer.parseInt(IDPart) + 1;
        }

        if (nextID < 10) {
            return "HD00" + nextID;
        } else if (nextID < 100) {
            return "HD0" + nextID;
        } else {
            return "HD" + nextID;
        }
    }

    @Override
    public Iterator<ProviderModelInterface> getAllProviderData() {
        return providers.iterator();
    }

    @Override
    public void updateFromDB() {
        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_ALL_PROVIDER);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ProviderModelInterface provider = new ProviderModel();
                provider.setProperty(resultSet);
                this.providers.add(provider);
            }
            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProviderManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void clearData() {
        providers.clear();
    }

    @Override
    public ProviderModelInterface getProviderByID(String providerIDText) {
        for (ProviderModelInterface element : providers) {
            if (element.getProviderIDText().equals(providerIDText)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Provider id '" + providerIDText + "' is not existed");
    }

    @Override
    public boolean removeProvider(ProviderModelInterface provider) {
        if (provider == null) {
            throw new NullPointerException("Provider instance is null.");
        }
        int index = providers.indexOf(provider);
        if (index == -1) {
            return false;
        } else {
            provider.deleteInDatabase();
            this.providers.remove(index);
            notifyRemovedProviderObserver(provider);
            return true;
        }
    }

    @Override
    public void addProvider(ProviderModelInterface provider) {
        if (provider == null) {
            throw new NullPointerException("Provider instance is null");
        }
        int index = providers.indexOf(provider);
        if (index != -1) {
            throw new IllegalArgumentException("Provider instance is already existed.");
        } else {
            provider.insertToDatabase();
            this.providers.add(provider);
            notifyInsertedProviderObserver(provider);
        }
    }

    @Override
    public boolean updateProvider(ProviderModelInterface updatedProvider) {
        if (updatedProvider == null) {
            throw new NullPointerException("Provider instance is null object");
        }
        int index = providers.indexOf(updatedProvider);
        if (index == -1) {
            return false;
        } else {
            updatedProvider.updateInDatabase();
            notifyUpdatedProviderObserver(updatedProvider);
            return true;
        }
    }

    @Override
    public Iterator<ProviderModelInterface> getProviderSearchByName(String searchText) {
        List<ProviderModelInterface> ret = new ArrayList<>();
        List<BoundExtractedResult<ProviderModelInterface>> matches = FuzzySearch
                .extractSorted(searchText, this.providers, provider -> provider.getName(),
                        AppConstant.SEARCH_SCORE_CUT_OFF);
        for (BoundExtractedResult<ProviderModelInterface> element : matches) {
            ret.add(element.getReferent());
        }
        return ret.iterator();
    }

    public int getProviderIndex(ProviderModelInterface provider) {
        if (provider == null) {
            throw new NullPointerException();
        }
        return this.providers.indexOf(provider);
    }

}
