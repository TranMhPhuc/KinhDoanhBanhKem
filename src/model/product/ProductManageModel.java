package model.product;

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
import model.productOfBill.ProductOfBillDetail;
import util.db.SQLServerConnection;
import view.function.product.InsertedProductObserver;
import view.function.product.ModifiedProductObserver;
import view.function.product.RemovedProductObserver;
import model.ingredientOfProduct.IngredientDetailOfProductInterface;

public class ProductManageModel implements ProductManageModelInterface {

    private static final String FIND_NEXT_IDENTITY_PRODUCT
            = "SELECT IDENT_CURRENT('" + ProductModel.TABLE_NAME + "') + 1";

    private static final String CHECK_EXIST_PRODUCT_OF_BILL_QUERY_PROTOTYPE
            = "IF EXISTS (SELECT * FROM " + ProductOfBillDetail.TABLE_NAME + " WHERE MaSP = 100)\n"
            + "BEGIN\n"
            + "	PRINT 1\n"
            + "END\n"
            + "ELSE\n"
            + "BEGIN\n"
            + "	PRINT 0\n"
            + "END";

    private volatile static ProductManageModel uniqueInstance;

    private static Connection dbConnection;

    private static ProductDataStorageInterface productDataStorage;

    private List<InsertedProductObserver> insertedProductObservers;
    private List<ModifiedProductObserver> modifiedProductObservers;
    private List<RemovedProductObserver> removedProductObservers;

    static {
        dbConnection = SQLServerConnection.getConnection();
        productDataStorage = ProductDataStorage.getInstance();
    }

    private ProductManageModel() {
        insertedProductObservers = new ArrayList<>();
        modifiedProductObservers = new ArrayList<>();
        removedProductObservers = new ArrayList<>();
    }

    public static ProductManageModelInterface getInstance() {
        if (uniqueInstance == null) {
            synchronized (ProductManageModel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ProductManageModel();
                }
            }
        }
        return uniqueInstance;
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
            ResultSet resultSet = statement.executeQuery(FIND_NEXT_IDENTITY_PRODUCT);
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
    public void addNewProduct(ProductModelInterface newProduct) {
        productDataStorage.add(newProduct);
        notifyInsertedProductObserver(newProduct);
    }

    @Override
    public void updateProduct(ProductModelInterface updatedProduct) {
        productDataStorage.update(updatedProduct);
        notifyModifiedProductObserver(updatedProduct);
    }

    @Override
    public void removeProduct(ProductModelInterface removedProduct) {
        productDataStorage.remove(removedProduct);
        notifyRemovedProductObserver(removedProduct);
    }

    @Override
    public Iterator<ProductModelInterface> getAllProductData() {
        return productDataStorage.createIterator();
    }

    @Override
    public Iterator<ProductModelInterface> getProductSearchByName(String searchText) {
        return productDataStorage.getProductSearchByName(searchText);
    }

    @Override
    public boolean isProductOfAnyBill(ProductModelInterface product) {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .prepareStatement(CHECK_EXIST_PRODUCT_OF_BILL_QUERY_PROTOTYPE);

            product.setKeyArg(1, ProductModel.ID_HEADER, preparedStatement);

            preparedStatement.execute();

            SQLWarning warning = preparedStatement.getWarnings();

            preparedStatement.close();

            if (warning.getMessage().equals("1")) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductManageModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public ProductModelInterface getProductByID(String productIDText) {
        return productDataStorage.getProductByID(productIDText);
    }

}
