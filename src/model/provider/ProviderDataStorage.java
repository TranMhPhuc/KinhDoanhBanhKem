package model.provider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import util.AppLog;
import util.constant.AppConstant;

public class ProviderDataStorage implements ProviderDataStorageInterface {
    
    private static ProviderDataStorage uniqueInstance;
    
    private ArrayList<ProviderModelInterface> providers;
    
    static {
        uniqueInstance = new ProviderDataStorage();
    }
    
    private ProviderDataStorage() {
        providers = new ArrayList<>();
    }
    
    public static ProviderDataStorage getInstance() {
        return uniqueInstance;
    }
    
    @Override
    public void updateFromDB(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            
            String query = "SELECT * FROM " + ProviderModel.TABLE_NAME;
            
            ResultSet resultSet = statement.executeQuery(query);
            
            providers.clear();
            
            while (resultSet.next()) {
                ProviderModelInterface provider = new ProviderModel();
                provider.setProperty(resultSet);
                providers.add(provider);
            }
            
            resultSet.close();
            statement.close();
            
            AppLog.getLogger().info("Update provider database: successfully, "
                    + providers.size() + " rows inserted.");
            
        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update provider database: error.");
        }
    }
    
    @Override
    public ProviderModelInterface getProviderByID(String providerIDText) throws IllegalArgumentException {
        for (ProviderModelInterface element : providers) {
            if (element.getProviderIDText().equals(providerIDText)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Provider id '" + providerIDText + "' is not existed");
    }
    
    @Override
    public ProviderModelInterface getProviderByIndex(int providerIndex) throws IndexOutOfBoundsException {
        if (providerIndex < 0 || providerIndex >= providers.size()) {
            throw new IndexOutOfBoundsException("Provider index is out of bound.");
        }
        return this.providers.get(providerIndex);
    }
    
    @Override
    public int getSize() {
        return this.providers.size();
    }
    
    @Override
    public Iterator<ProviderModelInterface> createIterator() {
        return providers.iterator();
    }
    
    @Override
    public boolean remove(ProviderModelInterface provider) {
        if (provider == null) {
            throw new NullPointerException("Provider instance is null.");
        }
        int index = providers.indexOf(provider);
        if (index == -1) {
            return false;
        } else {
            provider.deleteInDatabase();
            this.providers.remove(index);
            return true;
        }
    }
    
    @Override
    public void add(ProviderModelInterface provider) {
        if (provider == null) {
            throw new NullPointerException("Provider instance is null");
        }
        int index = providers.indexOf(provider);
        if (index != -1) {
            throw new IllegalArgumentException("Provider instance is already existed.");
        } else {
            this.providers.add(provider);
            provider.insertToDatabase();
        }
    }
    
    @Override
    public boolean update(ProviderModelInterface updatedProvider) {
        if (updatedProvider == null) {
            throw new NullPointerException("Provider instance is null object");
        }
        int index = providers.indexOf(updatedProvider);
        if (index == -1) {
            return false;
        } else {
            updatedProvider.updateInDatabase();
            return true;
        }
    }
    
    @Override
    public boolean isProviderNameExisted(String providerName) {
        if (providerName.isEmpty()) {
            throw new IllegalArgumentException("Provider name is empty.");
        }
        for (ProviderModelInterface provider : providers) {
            if (provider.getName().equals(providerName)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isProviderEmailExisted(String providerEmail) {
        if (providerEmail.isEmpty()) {
            throw new IllegalArgumentException("Provider email is empty.");
        }
        for (ProviderModelInterface element : providers) {
            if (element.getEmail().equals(providerEmail)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isProviderAddressExisted(String providerAddress) {
        if (providerAddress.isEmpty()) {
            throw new IllegalArgumentException("Provider name is empty.");
        }
        for (ProviderModelInterface element : providers) {
            if (element.getAddress().equals(providerAddress)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isProviderPhoneNumExist(String providerPhoneNum) {
        if (providerPhoneNum.isEmpty()) {
            throw new IllegalArgumentException("Provider phone num is empty.");
        }
        for (ProviderModelInterface element : providers) {
            if (element.getPhoneNum().equals(providerPhoneNum)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Iterator<ProviderModelInterface> getProviderSearchByName(String searchText) {
        List<ProviderModelInterface> ret = new ArrayList<>();
        List<BoundExtractedResult<ProviderModelInterface>> matches = FuzzySearch
                .extractSorted(searchText, this.providers, provider -> provider.getName(), AppConstant.SEARCH_SCORE_CUT_OFF);
        for (BoundExtractedResult<ProviderModelInterface> element : matches) {
            ret.add(element.getReferent());
        }
        return ret.iterator();
    }
    
    @Override
    public int getProviderIndex(ProviderModelInterface provider) {
        return this.providers.indexOf(provider);
    }
    
}
