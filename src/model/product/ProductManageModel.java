package model.product;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import model.product.ingredientDetail.IngredientDetailModelInterface;
import util.AppLog;
import view.product.InsertedProductObserver;
import view.product.ModifiedProductObserver;
import view.product.RemovedProductObserver;

public class ProductManageModel implements ProductManageModelInterface {

    private static final String SP_GET_ALL_PRODUCT = "{call get_all_products}";

    private static final String SP_GET_ALL_INGREDIENT_NAME
            = "{call get_all_ingredient_name}";

    private static final String SP_GET_ALL_INGREDIENT_UNIT_NAME_CONVERT_POSSIBLE
            = "{call get_all_unit_convert_possible(?)}";

    private static final String SP_GET_NEXT_IDENTITY_PRODUCT
            = "{call get_next_identity_id_product}";

    private static final String SP_PRODUCE_PRODUCT
            = "{call import_product(?, ?)}";

    private List<IngredientDetailModelInterface> ingredientDetailBufferList;

    private boolean ingredientBufferListModified;

    private List<InsertedProductObserver> insertedProductObservers;
    private List<ModifiedProductObserver> modifiedProductObservers;
    private List<RemovedProductObserver> removedProductObservers;

    private ArrayList<ProductModelInterface> products;

    public ProductManageModel() {
        ingredientDetailBufferList = new ArrayList<>();
        products = new ArrayList<>();
        ingredientBufferListModified = false;

        insertedProductObservers = new ArrayList<>();
        modifiedProductObservers = new ArrayList<>();
        removedProductObservers = new ArrayList<>();

        updateFromDB();
    }

    public List<IngredientDetailModelInterface> getIngredientDetailBufferList() {
        return this.ingredientDetailBufferList;
    }

    @Override
    public void registerInsertedProductObserver(InsertedProductObserver observer) {
        this.insertedProductObservers.add(observer);
    }

    @Override
    public void removeInsertedProductObserver(InsertedProductObserver observer) {
        int id = this.insertedProductObservers.indexOf(observer);
        if (id >= 0) {
            this.insertedProductObservers.remove(id);
        }
    }

    @Override
    public void registerModifiedProductObserver(ModifiedProductObserver observer) {
        this.modifiedProductObservers.add(observer);
    }

    @Override
    public void removeModifiedProductObserver(ModifiedProductObserver observer) {
        int id = this.modifiedProductObservers.indexOf(observer);
        if (id >= 0) {
            this.modifiedProductObservers.remove(id);
        }
    }

    @Override
    public void registerRemovedProductObserver(RemovedProductObserver observer) {
        this.removedProductObservers.add(observer);
    }

    @Override
    public void removeRemovedProductObserver(RemovedProductObserver observer) {
        int id = this.removedProductObservers.indexOf(observer);
        if (id >= 0) {
            this.removedProductObservers.remove(id);
        }
    }

    private void notifyInsertedProductObserver(ProductModelInterface insertedProduct) {
        for (InsertedProductObserver observer : insertedProductObservers) {
            observer.updateInsertedProductObserver(insertedProduct);
        }
    }

    private void notifyModifiedProductObserver(ProductModelInterface updatedProduct) {
        for (ModifiedProductObserver observer : modifiedProductObservers) {
            observer.updateModifiedProductObserver(updatedProduct);
        }
    }

    private void notifyRemovedProductObserver(ProductModelInterface removedProduct) {
        for (RemovedProductObserver observer : removedProductObservers) {
            observer.updateRemovedProductObserver(removedProduct);
        }
    }
    
    @Override
    public String getNextProductIDText() {
        int nextIdentity = 0;
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(SP_GET_NEXT_IDENTITY_PRODUCT);
            if (resultSet.next()) {
                nextIdentity = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return String.valueOf(nextIdentity);
    }

    @Override
    public void exportProductData() {
        // XXX
    }

    @Override
    public void importProductData() {
        // XXX

    }

    @Override
    public void updateFromDB() {
        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_ALL_PRODUCT);

            ResultSet resultSet = callableStatement.executeQuery();

            products.clear();

            while (resultSet.next()) {
                ProductModelInterface product = new ProductModel();
                product.setProperty(resultSet);
                products.add(product);
            }

            resultSet.close();
            callableStatement.close();

            AppLog.getLogger().info("Update product database: successfully, "
                    + products.size() + " rows inserted.");

        } catch (SQLException ex) {
            AppLog.getLogger().fatal("Update product database: error.");
        }
    }

    @Override
    public ProductModelInterface getProductByID(String productIDText) {
        for (ProductModelInterface product : products) {
            if (product.getProductIDText().equals(productIDText)) {
                return product;
            }
        }
        throw new IllegalArgumentException("Product id '" + productIDText + "' is not existed.");
    }

    @Override
    public Iterator<ProductModelInterface> getProductSearchByName(String searchText) {
        List<ProductModelInterface> ret = new ArrayList<>();
        List<BoundExtractedResult<ProductModelInterface>> matches = FuzzySearch
                .extractSorted(searchText, this.products, product -> product.getName(), 80);
        for (BoundExtractedResult<ProductModelInterface> element : matches) {
            ret.add(element.getReferent());
        }
        return ret.iterator();
    }

    @Override
    public void addProduct(ProductModelInterface product) {
        if (product == null) {
            throw new NullPointerException();
        }
        int index = this.products.indexOf(product);
        if (index != -1) {
            throw new IllegalArgumentException("Product instance is already existed.");
        } else {
            this.products.add(product);
            product.insertToDatabase();
            notifyInsertedProductObserver(product);
        }
    }

    @Override
    public boolean updateProduct(ProductModelInterface product) {
        if (product == null) {
            throw new NullPointerException();
        }
        int index = this.products.indexOf(product);
        if (index == -1) {
            return false;
        } else {
            product.updateInDatabase();
            notifyModifiedProductObserver(product);
            return true;
        }
    }

    @Override
    public boolean removeProduct(ProductModelInterface product) {
        if (product == null) {
            throw new NullPointerException();
        }
        int index = this.products.indexOf(product);
        if (index == -1) {
            return false;
        } else {
            product.deleteInDatabase();
            this.products.remove(product);
            this.ingredientDetailBufferList.clear();
            notifyRemovedProductObserver(product);
            return true;
        }
    }

    @Override
    public Iterator<ProductModelInterface> getProductByName(String productName) {
        List<ProductModelInterface> ret = new ArrayList<>();
        for (ProductModelInterface product : products) {
            if (product.getName().equals(productName)) {
                ret.add(product);
            }
        }
        return ret.iterator();
    }

    @Override
    public ProductModelInterface getProductByNameAndSize(String productName, String productSize) {
        for (ProductModelInterface product : products) {
            if (product.getName().equals(productName)
                    && product.getSize().equals(productSize)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public Iterator<ProductModelInterface> getAllProductData() {
        return products.iterator();
    }

    @Override
    public void clearData() {
        products.clear();
    }

    @Override
    public List<String> getAllIngredientName() {
        List<String> ret = new ArrayList<>();

        try {
            CallableStatement callableStatement = dbConnection.prepareCall(SP_GET_ALL_INGREDIENT_NAME);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ret.add(resultSet.getString(1));
            }
            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    @Override
    public List<String> getUnitNamePossibleOfIngredient(String ingredientName) {
        List<String> ret = new ArrayList<>();

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_GET_ALL_INGREDIENT_UNIT_NAME_CONVERT_POSSIBLE);

            callableStatement.setString(1, ingredientName);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ret.add(resultSet.getString(1));
            }

            resultSet.close();
            callableStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    @Override
    public void setIngredientDetailBufferList(ProductModelInterface product) {
        this.ingredientDetailBufferList.clear();
        Iterator<IngredientDetailModelInterface> iterator = product.getAllIngredientDetail();
        while (iterator.hasNext()) {
            this.ingredientDetailBufferList.add(iterator.next());
        }
    }

    @Override
    public void setBufferListModifiedFlag(boolean modified) {
        this.ingredientBufferListModified = modified;
    }

    @Override
    public boolean getBufferListModifiedFlag() {
        return this.ingredientBufferListModified;
    }

    @Override
    public void produceProduct(ProductModelInterface product, int produceAmount) {
        if (product == null) {
            throw new NullPointerException();
        }

        try {
            CallableStatement callableStatement = dbConnection
                    .prepareCall(SP_PRODUCE_PRODUCT);

            product.setKeyArg(1, ProductSimpleModel.ID_HEADER, callableStatement);
            callableStatement.setInt(2, produceAmount);

            callableStatement.execute();

            callableStatement.close();

            product.setAmount(product.getAmount() + produceAmount);

            notifyModifiedProductObserver(product);

        } catch (SQLException ex) {
            Logger.getLogger(ProductManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
