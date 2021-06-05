package util.db;

import java.sql.Connection;
import model.employee.EmployeeDataStorage;
import model.employee.EmployeeDataStorageInterface;
import model.employee.position.EmployeePositionDataStorage;
import model.employee.position.EmployeePositionDataStorageInterface;
import model.employee.shift.EmployeeShiftDataStorage;
import model.employee.shift.EmployeeShiftDataStorageInterface;
import model.ingredient.IngredientDataStorage;
import model.ingredient.IngredientDataStorageInterface;
import model.ingredient.type.IngredientTypeDataStorage;
import model.ingredient.type.IngredientTypeDataStorageInterface;
import model.ingredient.unit.IngredientUnitDataStorage;
import model.ingredient.unit.IngredientUnitDataStorageInterface;
import model.ingredient.unit.conversion.IngredientUnitConversionDataStorage;
import model.ingredient.unit.conversion.IngredientUnitConversionDataStorageInterface;
import model.ingredientOfProduct.IngredientOfProductDataStorage;
import model.ingredientOfProduct.IngredientOfProductDataStorageInterface;
import model.product.ProductDataStorage;
import model.product.ProductDataStorageInterface;
import model.provider.ProviderDataStorage;
import model.provider.ProviderDataStorageInterface;

/**
 * Utility class to update data model from database.
 *
 */
public class DataModelUpdateManager {

    private volatile static DataModelUpdateManager uniqueInstance;

    private static Connection connection;

    private EmployeeShiftDataStorageInterface employeeShiftDataStorage;
    private EmployeePositionDataStorageInterface employeePositionDataStorage;
    private EmployeeDataStorageInterface employeeDataStorage;
    private ProviderDataStorageInterface providerDataStorage;
    private IngredientTypeDataStorageInterface ingredientTypeDataStorage;
    private IngredientUnitDataStorageInterface ingredientUnitDataStorage;
    private IngredientUnitConversionDataStorageInterface ingredientUnitConversionDataStorage;
    private IngredientDataStorageInterface ingredientDataStorage;
    private ProductDataStorageInterface productDataStorage;
    private IngredientOfProductDataStorageInterface ingredientOfProductDataStorage;

    static {
        connection = SQLServerConnection.getConnection();
    }

    private DataModelUpdateManager() {
        employeeShiftDataStorage = EmployeeShiftDataStorage.getInstance();
        employeePositionDataStorage = EmployeePositionDataStorage.getInstance();
        employeeDataStorage = EmployeeDataStorage.getInstance();
        providerDataStorage = ProviderDataStorage.getInstance();
        ingredientTypeDataStorage = IngredientTypeDataStorage.getInstance();
        ingredientUnitDataStorage = IngredientUnitDataStorage.getInstance();
        ingredientUnitConversionDataStorage = IngredientUnitConversionDataStorage.getInstance();
        ingredientDataStorage = IngredientDataStorage.getInstance();
        productDataStorage = ProductDataStorage.getInstance();
        ingredientOfProductDataStorage = IngredientOfProductDataStorage.getInstance();
    }

    public static DataModelUpdateManager getInstance() {
        if (uniqueInstance == null) {
            synchronized (DataModelUpdateManager.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new DataModelUpdateManager();
                }
            }
        }
        return uniqueInstance;
    }

    public void updateFromDB() {
        employeeShiftDataStorage.updateFromDB(connection);
        employeePositionDataStorage.updateFromDB(connection);
        employeeDataStorage.updateFromDB(connection);
        providerDataStorage.updateFromDB(connection);
        ingredientTypeDataStorage.updateFromDB(connection);
        ingredientUnitDataStorage.updateFromDB(connection);
        ingredientUnitConversionDataStorage.updateFromDB(connection);
        ingredientDataStorage.updateFromDB(connection);
        productDataStorage.updateFromDB(connection);
        ingredientOfProductDataStorage.updateFromDB(connection);
    }
    
    public static void main(String[] args) {
        DataModelUpdateManager dataModelUpdateManager = getInstance();
        dataModelUpdateManager.updateFromDB();
    }
}
