package model.ingredient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ingredient.type.IngredientTypeDataStorage;
import model.ingredient.type.IngredientTypeDataStorageInterface;
import model.ingredient.type.IngredientTypeModel;
import model.ingredient.type.IngredientTypeModelInterface;
import model.ingredient.unit.IngredientUnitDataStorage;
import model.ingredient.unit.IngredientUnitDataStorageInterface;
import model.ingredient.unit.IngredientUnitModelInterface;
import model.provider.ProviderDataStorage;
import model.provider.ProviderDataStorageInterface;
import model.provider.ProviderModelInterface;
import util.db.SQLServerConnection;
import view.function.ingredient.IngredientUpdateObserver;

public class IngredientManageModel implements IngredientManageModelInterface {

    public static final String FIND_NEXT_IDENTITY_INGREDIENT
            = "SELECT IDENT_CURRENT('" + IngredientModel.TABLE_NAME + "') + 1";

    public static final String FIND_NEXT_IDENTITY_INGREDIENT_TYPE
            = "SELECT IDENT_CURRENT('" + IngredientTypeModel.TABLE_NAME + "') + 1";

    private volatile static IngredientManageModel uniqueInstance;

    private static IngredientDataStorageInterface ingredientDataStorage;
    private static IngredientTypeDataStorageInterface ingredientTypeDataStorage;
    private static ProviderDataStorageInterface providerDataStorage;
    private static IngredientUnitDataStorageInterface ingredientUnitDataStorage;

    private static Connection dbConnection;

    private IngredientModelInterface ingredient;

    private List<IngredientUpdateObserver> observers;

    static {
        dbConnection = SQLServerConnection.getConnection();
        ingredientDataStorage = IngredientDataStorage.getInstance();
        ingredientTypeDataStorage = IngredientTypeDataStorage.getInstance();
        providerDataStorage = ProviderDataStorage.getInstance();
        ingredientUnitDataStorage = IngredientUnitDataStorage.getInstance();
    }

    private IngredientManageModel() {
        ingredient = null;
        observers = new ArrayList<>();
    }

    public static IngredientManageModelInterface getInstance() {
        if (uniqueInstance == null) {
            synchronized (IngredientManageModel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new IngredientManageModel();
                }
            }
        }
        return uniqueInstance;
    }

    @Override
    public void registerObserver(IngredientUpdateObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IngredientUpdateObserver observer) {
        int id = observers.indexOf(observer);
        if (id >= 0) {
            observers.remove(observer);
        }
    }

    private void notifyObserver() {
        for (IngredientUpdateObserver observer : observers) {
            observer.updateIngredientState();
        }
    }

    @Override
    public String getNextIngredientID() {
        int nextIdentity = 0;
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_NEXT_IDENTITY_INGREDIENT);
            if (resultSet.next()) {
                nextIdentity = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return String.valueOf(nextIdentity);
    }

    @Override
    public String getNextIngredientTypeID() {
        int nextIdentity = 0;
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_NEXT_IDENTITY_INGREDIENT_TYPE);
            if (resultSet.next()) {
                nextIdentity = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return String.valueOf(nextIdentity);
    }

    @Override
    public int getIngredientNumber() {
        return ingredientDataStorage.getSize();
    }

    @Override
    public void exportIngredientData() {
        // XXX

    }

    @Override
    public void importIngredientData() {
        // XXX

    }

    @Override
    public void setIngredientName(String name) {
        this.ingredient.setName(name);
    }

    @Override
    public void setIngredientCost(int cost) {
        this.ingredient.setCost(cost);
    }

    @Override
    public void createNewIngredientType(String ingredientTypeName) {
        IngredientTypeModelInterface ingredientType = new IngredientTypeModel();
        ingredientType.setIngredientTypeID(ingredientTypeDataStorage.getSize() + 1);
        ingredientType.setName(ingredientTypeName);
        ingredientTypeDataStorage.addIngredientType(ingredientType);
    }

    @Override
    public void createIngredient() {
        this.ingredient.insertToDatabase();
        ingredientDataStorage.addNewIngredient(ingredient);
        clearIngredientObject();
    }

    @Override
    public void updateIngredient(String ingredientIDText) {

    }

    @Override
    public void removeIngredient(String ingredientIDText) {

    }

    @Override
    public Iterator<IngredientTypeModelInterface> getAllIngredientType() {
        return ingredientTypeDataStorage.createIterator();
    }

    @Override
    public Iterator<IngredientModelInterface> getAllIngredient() {
        return ingredientDataStorage.createIterator();
    }

    @Override
    public Iterator<IngredientModelInterface> getIngredientSearchByName(String searchText) {
        return ingredientDataStorage.getIngredientSearchByName(searchText);
    }

    @Override
    public Iterator<ProviderModelInterface> getAllProvider() {
        return providerDataStorage.createIterator();
    }

    @Override
    public boolean isIngredientTypeExist(String ingredientTypeName) {
        Iterator<IngredientTypeModelInterface> iterator = ingredientTypeDataStorage.createIterator();
        while (iterator.hasNext()) {
            IngredientTypeModelInterface ingredientType = iterator.next();
            if (ingredientType.getName().equals(ingredientTypeName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIngredientNameExist(String ingredientName) {
        Iterator<IngredientModelInterface> iterator = ingredientDataStorage.createIterator();
        while (iterator.hasNext()) {
            IngredientModelInterface ingredient = iterator.next();
            if (ingredient.getName().equals(ingredientName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setIngredientProvider(int providerSelectIndex) {
        ProviderModelInterface provider = providerDataStorage.getProvider(providerSelectIndex);
        this.ingredient.setProvider(provider);
    }

    @Override
    public void setIngredientType(int ingredientTypeSelectIndex) {
        IngredientTypeModelInterface ingredientType = ingredientTypeDataStorage
                .getIngredientType(ingredientTypeSelectIndex);
        this.ingredient.setIngredientType(ingredientType);
    }

    @Override
    public void prepareCreateIngredient() {
        this.ingredient = new IngredientModel();
    }

    private void clearIngredientObject() {
        this.ingredient = null;
    }

    @Override
    public void setIngredientUnit(int ingredientUnitSelectIndex) {
        IngredientUnitModelInterface ingredientUnit = ingredientUnitDataStorage.getIngredientUnit(ingredientUnitSelectIndex);
        this.ingredient.setIngredientUnit(ingredientUnit);
    }

    @Override
    public Iterator<IngredientUnitModelInterface> getAllIngredientUnit() {
        return ingredientUnitDataStorage.createIterator();
    }

}
