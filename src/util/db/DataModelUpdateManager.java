package util.db;

import java.sql.Connection;
import model.bill.BillDataStorage;
import model.employee.EmployeeDataStorage;
import model.employee.position.EmployeePositionDataStorage;
import model.employee.shift.EmployeeShiftDataStorage;
import model.ingredient.IngredientDataStorage;
import model.ingredient.ingredientType.IngredientTypeDataStorage;
import model.ingredient.ingredientUnit.IngredientUnitDataStorage;
import model.ingredient.ingredientUnit.conversion.IngredientUnitConversionDataStorage;
import model.ingredientOfProduct.IngredientOfProductDataStorage;
import model.product.ProductDataStorage;
import model.provider.ProviderDataStorage;

/**
 * Utility class to update data model from database.
 *
 */
public class DataModelUpdateManager {

    private volatile static DataModelUpdateManager uniqueInstance;

    private static Connection connection;

    private EmployeeShiftDataStorage employeeShiftDataStorage;
    private EmployeePositionDataStorage employeePositionDataStorage;
    private EmployeeDataStorage employeeDataStorage;
    private BillDataStorage billDataStorage;
    private ProviderDataStorage providerDataStorage;
    private IngredientTypeDataStorage ingredientTypeDataStorage;
    private IngredientUnitDataStorage ingredientUnitDataStorage;
    private IngredientUnitConversionDataStorage ingredientUnitConversionDataStorage;
    private IngredientDataStorage ingredientDataStorage;
    private ProductDataStorage productDataStorage;
    private IngredientOfProductDataStorage ingredientOfProductDataStorage;

    static {
        connection = SQLServerConnection.getConnection();
    }

    private DataModelUpdateManager() {
        employeeShiftDataStorage = EmployeeShiftDataStorage.getInstance();
        employeePositionDataStorage = EmployeePositionDataStorage.getInstance();
        employeeDataStorage = EmployeeDataStorage.getInstance();
        billDataStorage = BillDataStorage.getInstance();
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
        billDataStorage.updateFromDB(connection);
        providerDataStorage.updateFromDB(connection);
        ingredientTypeDataStorage.updateFromDB(connection);
        ingredientUnitDataStorage.updateFromDB(connection);
        ingredientUnitConversionDataStorage.updateFromDB(connection);
        ingredientDataStorage.updateFromDB(connection);
        productDataStorage.updateFromDB(connection);
        ingredientOfProductDataStorage.updateFromDB(connection);
    }
}
