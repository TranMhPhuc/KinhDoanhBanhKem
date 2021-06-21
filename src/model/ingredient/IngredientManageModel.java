package model.ingredient;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import model.ingredient.importDetail.IngredientImportDetail;
import model.ingredient.importDetail.IngredientImportDetailInterface;
import model.ingredient.type.IngredientTypeModelInterface;
import util.AppLog;
import view.ingredient.InsertedIngredientObserver;
import view.ingredient.InsertedIngredientTypeObserver;
import view.ingredient.ModifiedIngredientObserver;
import view.ingredient.RemovedIngredientObserver;

public class IngredientManageModel implements IngredientManageModelInterface {

    private static final String SP_GET_ALL_INGREDIENT
            = "{call get_all_ingredients}";

    private static final String SP_GET_ALL_INGREDIENT_TYPE_NAME
            = "{call get_all_ingredient_type_name}";

    private static final String SP_GET_ALL_INGREDIENT_UNIT_NAME
            = "{call get_all_unit_name}";

    private static final String SP_GET_ALL_PROVIDER_NAME
            = "{call get_all_provider_name}";

    private static final String SP_FIND_NEXT_IDENTITY_INGREDIENT
            = "{call get_next_identity_id_ingredient}";

    private static final String SP_FIND_NEXT_IDENTITY_INGREDIENT_TYPE
            = "{call get_next_identity_id_ingredient_type}";

    private static final String SP_IMPORT_INGREDIENT
            = "{call insert_ChiTietNhapNL(?, ?, ?, ?)}";

    private static final String FT_GET_AMOUNT_OF_INGREDIENT
            = "{? = call get_amount_of_ingredient(?)}";

    private static final String SP_GET_ALL_IMPORT_HISTORY
            = "{call get_ChiTietNhapNguyneLieu_from_date_range(?, ?)}";

    private static final String SP_UPDATE_PROVIDER_NAME
            = "{? = call get_provider_name_of_ingredient(?)}";

    private ArrayList<IngredientModelInterface> ingredients;

    private List<InsertedIngredientObserver> insertedIngredientObservers;
    private List<ModifiedIngredientObserver> modifiedIngredientObservers;
    private List<RemovedIngredientObserver> removedIngredientObservers;
    private List<InsertedIngredientTypeObserver> insertedIngredientTypeObservers;

    public IngredientManageModel() {
        ingredients = new ArrayList<>();

        insertedIngredientObservers = new ArrayList<>();
        modifiedIngredientObservers = new ArrayList<>();
        removedIngredientObservers = new ArrayList<>();
        insertedIngredientTypeObservers = new ArrayList<>();

        updateFromDB();
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
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_FIND_NEXT_IDENTITY_INGREDIENT);

            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                nextIdentity = resultSet.getInt(1);
            }

            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return String.valueOf(nextIdentity);
    }

    @Override
    public String getNextIngredientTypeIDText() {
        int nextIdentity = 0;
        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_FIND_NEXT_IDENTITY_INGREDIENT_TYPE);

            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                nextIdentity = resultSet.getInt(1);
            }

            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return String.valueOf(nextIdentity);
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
    public IngredientModelInterface getIngredientByID(String ingredientIDText) {
        for (IngredientModelInterface element : ingredients) {
            if (element.getIngredientIDText().equals(ingredientIDText)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Ingredient id '" + ingredientIDText + "' doesn't exist.");
    }

    @Override
    public void addIngredient(IngredientModelInterface ingredient) {
        if (ingredient == null) {
            throw new NullPointerException();
        }
        int index = this.ingredients.indexOf(ingredient);
        if (index != -1) {
            throw new IllegalArgumentException("Ingredient instance already exists.");
        } else {
            this.ingredients.add(ingredient);
            ingredient.insertToDatabase();
            notifyInsertedIngredientObserver(ingredient);
        }
    }

    @Override
    public boolean updateIngredient(IngredientModelInterface ingredient) {
        if (ingredient == null) {
            throw new NullPointerException("Ingredient instance is null.");
        }
        int index = this.ingredients.indexOf(ingredient);
        if (index == -1) {
            return false;
        } else {
            ingredient.updateInDatabase();
            notifyModifiedIngredientObserver(ingredient);
            return true;
        }
    }

    @Override
    public boolean removeIngredient(IngredientModelInterface ingredient) {
        if (ingredient == null) {
            throw new NullPointerException("Ingredient instance is null.");
        }
        int index = this.ingredients.indexOf(ingredient);
        if (index == -1) {
            return false;
        } else {
            ingredient.deleteInDatabase();
            this.ingredients.remove(index);
            notifyRemovedIngredientObserver(ingredient);
            return true;
        }
    }

    @Override
    public Iterator<IngredientModelInterface> getAllIngredientData() {
        return ingredients.iterator();
    }

    @Override
    public Iterator<IngredientModelInterface> getIngredientSearchByName(String searchText) {
        List<IngredientModelInterface> ret = new ArrayList<>();
        List<BoundExtractedResult<IngredientModelInterface>> matches = FuzzySearch
                .extractSorted(searchText, this.ingredients, ingredient -> ingredient.getName(), 60);
        for (BoundExtractedResult<IngredientModelInterface> element : matches) {
            ret.add(element.getReferent());
        }
        return ret.iterator();
    }

    @Override
    public IngredientModelInterface getIngredientByIndex(int ingredientIndex) {
        if (ingredientIndex < 0 || ingredientIndex >= ingredients.size()) {
            throw new IndexOutOfBoundsException("Ingredient index is out of bound.");
        }
        return this.ingredients.get(ingredientIndex);
    }

    @Override
    public boolean isIngredientNameExisted(String ingredientName) {
        if (ingredientName.isEmpty()) {
            throw new IllegalArgumentException("Ingredient name is empty.");
        }
        for (IngredientModelInterface ingredient : ingredients) {
            if (ingredient.getName().equals(ingredientName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IngredientModelInterface getIngredientByName(String ingredientName) {
        for (IngredientModelInterface ingredient : ingredients) {
            if (ingredient.getName().equals(ingredientName)) {
                return ingredient;
            }
        }
        throw new IllegalArgumentException("Ingredient name doesn't exist.");
    }

    @Override
    public void updateFromDB() {
        ingredients.clear();

        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_ALL_INGREDIENT);

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                IngredientModelInterface ingredient = new IngredientModel();
                ingredient.setProperty(resultSet);
                ingredients.add(ingredient);
            }

            resultSet.close();
            callableStatement.close();

            AppLog.getLogger().info("Clone ingredient data from database: successfully, "
                    + ingredients.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Clone ingredient data from database: error");
        }
    }

    @Override
    public void clearData() {
        ingredients.clear();
    }

    @Override
    public List<String> getAllIngredientTypeName() {
        List<String> ret = new ArrayList<>();

        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_ALL_INGREDIENT_TYPE_NAME);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ret.add(resultSet.getString(1));
            }
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    @Override
    public List<String> getAllIngredientUnitName() {
        List<String> ret = new ArrayList<>();

        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_ALL_INGREDIENT_UNIT_NAME);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ret.add(resultSet.getString(1));
            }
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    @Override
    public List<String> getAllProviderName() {
        List<String> ret = new ArrayList<>();

        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_ALL_PROVIDER_NAME);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ret.add(resultSet.getString(1));
            }
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    @Override
    public int getIngredientTotalNumber() {
        return ingredients.size();
    }

    @Override
    public void importIngredient(IngredientModelInterface ingredient, Date importDate,
            int importAmount, String importUnitName) {
        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_IMPORT_INGREDIENT);
            ingredient.setKeyArg(1, IngredientModel.ID_HEADER, callableStatement);
            callableStatement.setTimestamp(2, Timestamp.from(importDate.toInstant()));
            callableStatement.setInt(3, importAmount);
            callableStatement.setString(4, importUnitName);

            callableStatement.execute();

            callableStatement = dbConnection.prepareCall(FT_GET_AMOUNT_OF_INGREDIENT);
            callableStatement.registerOutParameter(1, Types.FLOAT);
            ingredient.setKeyArg(2, IngredientModel.ID_HEADER, callableStatement);

            callableStatement.execute();

            float updatedIngredientAmount = callableStatement.getFloat(1);

            ingredient.setAmount(updatedIngredientAmount);

            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        notifyModifiedIngredientObserver(ingredient);
    }

    @Override
    public List<IngredientImportDetailInterface> getImportHistoryFromDateRange(Date dateFrom,
            Date dateTo) {
        List<IngredientImportDetailInterface> ret = new ArrayList<>();

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_ALL_IMPORT_HISTORY);

            callableStatement.setTimestamp(1, Timestamp.from(dateFrom.toInstant()));
            callableStatement.setTimestamp(2, Timestamp.from(dateTo.toInstant()));

            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                IngredientImportDetailInterface ingredientImportDetail = new IngredientImportDetail();
                ingredientImportDetail.setProperty(resultSet);
                ret.add(ingredientImportDetail);
            }

            resultSet.close();
            callableStatement.close();

        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    @Override
    public void updateProviderNameOfIngredientData() {
        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_UPDATE_PROVIDER_NAME);
            callableStatement.registerOutParameter(1, Types.NVARCHAR);

            for (IngredientModelInterface ingredient : ingredients) {
                ingredient.setKeyArg(2, IngredientModel.ID_HEADER, callableStatement);
                callableStatement.execute();
                String updatedProviderName = callableStatement.getNString(1);
                ingredient.setProviderName(updatedProviderName);
            }

            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(IngredientManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addIngredientType(IngredientTypeModelInterface ingredientType) {
        ingredientType.insertToDatabase();
        notifyInsertedIngredientTypeObserver(ingredientType);
    }

}
