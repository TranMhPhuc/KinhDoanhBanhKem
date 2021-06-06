package view.main;

import control.bill.BillController;
import control.bill.BillControllerInterface;
import control.employee.EmployeeManageController;
import control.employee.EmployeeManageControllerInterface;
import control.ingredient.IngredientController;
import control.ingredient.IngredientControllerInterface;
import control.product.ProductController;
import control.product.ProductControllerInterface;
import control.provider.ProviderController;
import control.provider.ProviderControllerInterface;
import control.statistics.StatisticsController;
import control.statistics.StatisticsControllerInterface;
import model.bill.BillManageModel;
import model.bill.BillModel;
import model.bill.BillManageModelInterface;
import model.employee.EmployeeModel;
import model.employee.EmployeeModelInterface;
import model.ingredient.IngredientModel;
import model.ingredient.IngredientModelInterface;
import model.product.ProductModel;
import model.product.ProductModelInterface;
import model.provider.ProviderModel;
import model.provider.ProviderModelInterface;
import model.user.UserModelInterface;
import view.SideMenuPanel;

public class ManagerUIDisplay implements CustomUIDisplay {

    public ManagerUIDisplay() {
        BillManageModelInterface billModel = new BillManageModel();
        BillControllerInterface billController = BillController.getInstance(billModel);
        
        StatisticsControllerInterface statisticsController = StatisticsController.getInstance();
        
        ProductModelInterface productModel = new ProductModel();
        ProductControllerInterface productControllerInterface = ProductController.getInstance(productModel);
        
        IngredientModelInterface ingredientModel = new IngredientModel();
        IngredientControllerInterface ingredientController = IngredientController.getInstance(ingredientModel);
        
        ProviderModelInterface providerModel = new ProviderModel();
        ProviderControllerInterface providerController = ProviderController.getInstance(providerModel);
        
        EmployeeModelInterface employeeModel = new EmployeeModel();
        EmployeeManageControllerInterface employeeController = EmployeeManageController.getInstance(employeeModel);
    }
    
    @Override
    public void customUIDisplay(MainFrame mainFrame, UserModelInterface model) {
        mainFrame.setHomeFunctionState(true);
        mainFrame.setSellFunctionState(true);
        mainFrame.setStatisticFunctionState(true);
        mainFrame.setProductManageFunctionState(true);
        mainFrame.setEmployeeFunctionState(true);
        
        mainFrame.setLabelGreetingText("Hello, manager");
    }

}
