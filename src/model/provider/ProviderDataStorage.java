package model.provider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import util.AppLog;

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

            AppLog.getLogger().info("Update provider database: successfully, "
                    + providers.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update provider database: error.");
        }
    }

    @Override
    public ProviderModelInterface getProvider(String providerIDText) {
        for (ProviderModelInterface element : providers) {
            if (element.getIDText().equals(providerIDText)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Provider id '" + providerIDText + "' is not existed");
    }

    @Override
    public ProviderModelInterface getProvider(int providerIndex) {
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

}
