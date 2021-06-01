package util.db;

import java.sql.Connection;
import model.bill.BillDataStorage;
import model.employee.EmployeeDataStorage;
import model.employee.position.EmployeePositionDataStorage;
import model.employee.shift.EmployeeShiftDataStorage;
import model.ingredient.IngredientDataStorage;
import model.ingredient.ingredientType.IngredientTypeDataStorage;
import model.ingredient.ingredientUnit.IngredientUnitDataStorage;
import model.ingredientOfProduct.IngredientOfProductDataStorage;
import model.product.ProductDataStorage;
import model.provider.ProviderDataStorage;

/**
 * Utility class to update data model from database.
 *
 */
public class DataModelUpdateManager {

    private static DataModelUpdateManager uniqueInstance;

    private static Connection connection;

    private static BillDataStorage billDataStorage;
    private static EmployeeDataStorage employeeDataStorage;
    private static EmployeeShiftDataStorage employeeShiftDataStorage;
    private static EmployeePositionDataStorage employeePositionDataStorage;
    private static IngredientTypeDataStorage ingredientTypeDataStorage;
    private static ProviderDataStorage providerDataStorage;
    private static ProductDataStorage productDataStorage;
    private static IngredientUnitDataStorage ingredientUnitDataStorage;
    private static IngredientDataStorage ingredientDataStorage;
    private static IngredientOfProductDataStorage ingredientOfProductDataStorage;

    static {
        uniqueInstance = new DataModelUpdateManager();

        connection = SQLServerConnect.getConnection();

        billDataStorage = BillDataStorage.getInstance();
        employeeDataStorage = EmployeeDataStorage.getInstance();
        employeeShiftDataStorage = EmployeeShiftDataStorage.getInstance();
        employeePositionDataStorage = EmployeePositionDataStorage.getInstance();
        ingredientTypeDataStorage = IngredientTypeDataStorage.getInstance();
        providerDataStorage = ProviderDataStorage.getInstance();
        productDataStorage = ProductDataStorage.getInstance();
        ingredientUnitDataStorage = IngredientUnitDataStorage.getInstance();
        ingredientDataStorage = IngredientDataStorage.getInstance();
        ingredientOfProductDataStorage = IngredientOfProductDataStorage.getInstance();
    }

    private DataModelUpdateManager() {
    }

    public static DataModelUpdateManager getInstance() {
        return uniqueInstance;
    }

    public void updateFromDB() {
        employeeShiftDataStorage.updateFromDB(connection);
        employeePositionDataStorage.updateFromDB(connection);
        employeeDataStorage.updateFromDB(connection);
        ingredientTypeDataStorage.updateFromDB(connection);
        providerDataStorage.updateFromDB(connection);
        productDataStorage.updateFromDB(connection);
        ingredientUnitDataStorage.updateFromDB(connection);
        ingredientDataStorage.updateFromDB(connection);
        ingredientOfProductDataStorage.updateFromDB(connection);
    }

    public static void main(String[] args) {
        DataModelUpdateManager dataModelUpdateManager = new DataModelUpdateManager();
        dataModelUpdateManager.updateFromDB();

    }

}
