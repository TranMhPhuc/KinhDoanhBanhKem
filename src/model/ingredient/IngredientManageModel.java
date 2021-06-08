package model.ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
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
import model.ingredientOfProduct.IngredientDetailOfProduct;
import model.provider.ProviderDataStorage;
import model.provider.ProviderDataStorageInterface;
import model.provider.ProviderModelInterface;
import org.junit.Assert;
import util.db.SQLServerConnection;
import view.function.ingredient.InsertedIngredientObserver;
import view.function.ingredient.InsertedIngredientTypeObserver;
import view.function.ingredient.ModifiedIngredientObserver;
import view.function.ingredient.RemovedIngredientObserver;

public class IngredientManageModel implements IngredientManageModelInterface {

    private static final String FIND_NEXT_IDENTITY_INGREDIENT
            = "SELECT IDENT_CURRENT('" + IngredientModel.TABLE_NAME + "') + 1";

    private static final String FIND_NEXT_IDENTITY_INGREDIENT_TYPE
            = "SELECT IDENT_CURRENT('" + IngredientTypeModel.TABLE_NAME + "') + 1";

    private static final String CHECK_EXIST_INGREDIENT_OF_PRODUCT_QUERY_PROTOTYPE
            = "IF EXISTS(SELECT * FROM " + IngredientDetailOfProduct.TABLE_NAME
            + " WHERE " + IngredientModel.ID_HEADER + " = ?)\n"
            + "BEGIN\n"
            + "	PRINT 1\n"
            + "END\n"
            + "ELSE\n"
            + "BEGIN\n"
            + "	PRINT 0\n"
            + "END";

    private volatile static IngredientManageModel uniqueInstance;

    private static IngredientDataStorageInterface ingredientDataStorage;
    private static IngredientTypeDataStorageInterface ingredientTypeDataStorage;
    private static IngredientUnitDataStorageInterface ingredientUnitDataStorage;
    private static ProviderDataStorageInterface providerDataStorage;

    private static Connection dbConnection;

    private List<InsertedIngredientObserver> insertedIngredientObservers;
    private List<ModifiedIngredientObserver> modifiedIngredientObservers;
    private List<RemovedIngredientObserver> removedIngredientObservers;
    private List<InsertedIngredientTypeObserver> insertedIngredientTypeObservers;

    static {
        dbConnection = SQLServerConnection.getConnection();
        ingredientDataStorage = IngredientDataStorage.getInstance();
        ingredientTypeDataStorage = IngredientTypeDataStorage.getInstance();
        ingredientUnitDataStorage = IngredientUnitDataStorage.getInstance();
        providerDataStorage = ProviderDataStorage.getInstance();
    }

    private IngredientManageModel() {
        insertedIngredientObservers = new ArrayList<>();
        modifiedIngredientObservers = new ArrayList<>();
        removedIngredientObservers = new ArrayList<>();
        insertedIngredientTypeObservers = new ArrayList<>();
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
    public void registerInsertedIngredientObserver(InsertedIngredientObserver observer) {
        this.insertedIngredientObservers.add(observer);
    }

    @Override
    public void removeInsertedIngredientObserver(InsertedIngredientObserver observer) {
        int id = insertedIngredientObservers.indexOf(observer);
        if (id >= 0) {
            insertedIngredientObservers.remove(observer);
        }
    }

    @Override
    public void registerModifiedIngredientObserver(ModifiedIngredientObserver observer) {
        this.modifiedIngredientObservers.add(observer);
    }

    @Override
    public void removeModifiedIngredientObserver(ModifiedIngredientObserver observer) {
        int id = this.modifiedIngredientObservers.indexOf(observer);
        if (id >= 0) {
            this.modifiedIngredientObservers.remove(id);
        }
    }

    @Override
    public void registerRemovedIngredientObserver(RemovedIngredientObserver observer) {
        this.removedIngredientObservers.add(observer);
    }

    @Override
    public void removeRemovedIngredientObserver(RemovedIngredientObserver observer) {
        int id = this.removedIngredientObservers.indexOf(observer);
        if (id >= 0) {
            this.removedIngredientObservers.remove(id);
        }
    }

    @Override
    public void registerInsertedIngredientTypeObserver(InsertedIngredientTypeObserver observer) {
        this.insertedIngredientTypeObservers.add(observer);
    }

    @Override
    public void removeInsertedIngredientTypeObserver(InsertedIngredientTypeObserver observer) {
        int id = this.insertedIngredientTypeObservers.indexOf(observer);
        if (id >= 0) {
            this.insertedIngredientTypeObservers.remove(id);
        }
    }

    private void notifyInsertedIngredientObserver(IngredientModelInterface insertedIngredient) {
        for (InsertedIngredientObserver observer : insertedIngredientObservers) {
            observer.updateInsertedIngredientObserver(insertedIngredient);
        }
    }

    private void notifyModifiedIngredientObserver(IngredientModelInterface updatedIngredient) {
        for (ModifiedIngredientObserver observer : modifiedIngredientObservers) {
            observer.updateModifiedIngredientObserver(updatedIngredient);
        }
    }

    private void notifyRemovedIngredientObserver(IngredientModelInterface removedIngredient) {
        for (RemovedIngredientObserver observer : removedIngredientObservers) {
            observer.updateRemovedIngredient(removedIngredient);
        }
    }

    private void notifyInsertedIngredientTypeObserver(IngredientTypeModelInterface insertedIngredientType) {
        for (InsertedIngredientTypeObserver observer : insertedIngredientTypeObservers) {
            observer.updateInsertedIngredientType(insertedIngredientType);
        }
    }

    @Override
    public String getNextIngredientIDText() {
        int nextIdentity = 0;
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_NEXT_IDENTITY_INGREDIENT);
            if (resultSet.next()) {
                nextIdentity = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return String.valueOf(nextIdentity);
    }

    @Override
    public String getNextIngredientTypeIDText() {
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
    public void addNewIngredient(IngredientModelInterface newIngredient) {
        ingredientDataStorage.add(newIngredient);
        notifyInsertedIngredientObserver(newIngredient);
    }

    @Override
    public void updateIngredient(IngredientModelInterface updatedIngredient) {
        ingredientDataStorage.update(updatedIngredient);
        notifyModifiedIngredientObserver(updatedIngredient);
    }

    @Override
    public void removeIngredient(IngredientModelInterface removedIngredient) {
        ingredientDataStorage.remove(removedIngredient);
        notifyRemovedIngredientObserver(removedIngredient);
    }

    @Override
    public IngredientModelInterface getIngredient(String ingredientIDText) {
        return ingredientDataStorage.getIngredientByID(ingredientIDText);
    }

    @Override
    public boolean isIngredientOfAnyProduct(IngredientModelInterface ingredient) {
        if (ingredient == null) {
            throw new NullPointerException("Ingredient instance is null.");
        }

        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(CHECK_EXIST_INGREDIENT_OF_PRODUCT_QUERY_PROTOTYPE);

            ingredient.setKeyArg(1, IngredientModel.ID_HEADER, preparedStatement);

            preparedStatement.execute();

            SQLWarning warnings = preparedStatement.getWarnings();

            preparedStatement.close();

            if (warnings.getMessage().equals("1")) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public void addNewIngredientType(IngredientTypeModelInterface newIngredientType) {
        ingredientTypeDataStorage.add(newIngredientType);
        notifyInsertedIngredientTypeObserver(newIngredientType);
    }

    @Override
    public Iterator<IngredientTypeModelInterface> getAllIngredientTypeData() {
        return ingredientTypeDataStorage.createIterator();
    }

    @Override
    public Iterator<IngredientModelInterface> getAllIngredientData() {
        return ingredientDataStorage.createIterator();
    }

    @Override
    public Iterator<IngredientModelInterface> getIngredientSearchByName(String searchText) {
        return ingredientDataStorage.getIngredientSearchByName(searchText);
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
    public Iterator<IngredientUnitModelInterface> getAllIngredientUnit() {
        return ingredientUnitDataStorage.createIterator();
    }

    @Override
    public boolean isIngredientTypeNameExist(String ingredientTypeName) {
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
    public Iterator<ProviderModelInterface> getAllProviderData() {
        return providerDataStorage.createIterator();
    }

    @Override
    public ProviderModelInterface getProviderByIndex(int providerIndex) {
        return providerDataStorage.getProviderByIndex(providerIndex);
    }

    @Override
    public IngredientTypeModelInterface getIngredientTypeByIndex(int ingredientTypeIndex) {
        return ingredientTypeDataStorage.getIngredientType(ingredientTypeIndex);
    }

    @Override
    public IngredientUnitModelInterface getIngredientUnitByIndex(int ingredientUnitIndex) {
        return ingredientUnitDataStorage.getIngredientUnit(ingredientUnitIndex);
    }

    @Override
    public int getProviderIndex(ProviderModelInterface provider) {
        return this.providerDataStorage.getProviderIndex(provider);
    }

}
