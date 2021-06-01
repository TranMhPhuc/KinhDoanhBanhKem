package model.provider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.DatabaseUpdate;

public class ProviderDataStorage implements DatabaseUpdate {

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
                providers.add(ProviderModel.getInstance(resultSet));
            }

        } catch (SQLException ex) {
        }
    }

    public ProviderModelInterface getProvider(String providerIDText) {
        for (ProviderModelInterface element : providers) {
            if (element.getIDText().equals(providerIDText)) {
                return element;
            }
        }
        return null;
    }

}
