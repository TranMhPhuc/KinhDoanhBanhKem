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
import model.productOfBill.ProductOfBillDetailDataStorage;
import model.provider.ProviderDataStorage;

/**
 * Utility class to update data model from database.
 *
 */
public class DataModelUpdateManager {

    private static DataModelUpdateManager uniqueInstance;

    private static Connection connection;

    private static EmployeeShiftDataStorage employeeShiftDataStorage;
    private static EmployeePositionDataStorage employeePositionDataStorage;
    private static EmployeeDataStorage employeeDataStorage;
    private static BillDataStorage billDataStorage;
    private static IngredientTypeDataStorage ingredientTypeDataStorage;
    private static ProviderDataStorage providerDataStorage;
    private static IngredientUnitDataStorage ingredientUnitDataStorage;
    private static IngredientDataStorage ingredientDataStorage;
    private static ProductDataStorage productDataStorage;
    private static IngredientOfProductDataStorage ingredientOfProductDataStorage;
    private static ProductOfBillDetailDataStorage productOfBillDetailDataStorage;
    
    static {
        uniqueInstance = new DataModelUpdateManager();

        connection = SQLServerConnect.getConnection();

        employeeShiftDataStorage = EmployeeShiftDataStorage.getInstance();
        employeePositionDataStorage = EmployeePositionDataStorage.getInstance();
        employeeDataStorage = EmployeeDataStorage.getInstance();
        billDataStorage = BillDataStorage.getInstance();
        ingredientTypeDataStorage = IngredientTypeDataStorage.getInstance();
        providerDataStorage = ProviderDataStorage.getInstance();
        ingredientUnitDataStorage = IngredientUnitDataStorage.getInstance();
        ingredientDataStorage = IngredientDataStorage.getInstance();
        productDataStorage = ProductDataStorage.getInstance();
        ingredientOfProductDataStorage = IngredientOfProductDataStorage.getInstance();
        productOfBillDetailDataStorage = ProductOfBillDetailDataStorage.getInstance();
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
        billDataStorage.updateFromDB(connection);
        ingredientTypeDataStorage.updateFromDB(connection);
        providerDataStorage.updateFromDB(connection);
        ingredientUnitDataStorage.updateFromDB(connection);
        ingredientDataStorage.updateFromDB(connection);
        productDataStorage.updateFromDB(connection);
        ingredientOfProductDataStorage.updateFromDB(connection);
        productOfBillDetailDataStorage.updateFromDB(connection);
    }

    public static void main(String[] args) {
        DataModelUpdateManager dataModelUpdateManager = new DataModelUpdateManager();
        dataModelUpdateManager.updateFromDB();

    }

}
