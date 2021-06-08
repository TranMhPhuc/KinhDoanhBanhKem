package view.main;

import control.bill.create.BillCreateController;
import control.employee.EmployeeController;
import control.ingredient.IngredientController;
import control.ingredient.IngredientControllerInterface;
import control.product.ProductController;
import control.product.ProductControllerInterface;
import control.provider.ProviderController;
import control.provider.ProviderControllerInterface;
import control.statistics.StatisticsController;
import control.statistics.StatisticsControllerInterface;
import model.bill.BillManageModel;
import model.bill.BillManageModelInterface;
import model.employee.EmployeeModel;
import model.product.ProductManageModelInterface;
import model.user.UserModelInterface;
import control.bill.create.BillCreateControllerInterface;
import control.bill.history.BillHistoryController;
import control.bill.history.BillHistoryControllerInterface;
import model.ingredient.IngredientManageModel;
import model.ingredient.IngredientManageModelInterface;
import model.product.ProductManageModel;
import model.provider.ProviderManageModel;
import model.provider.ProviderManageModelInterface;
import control.employee.EmployeeControllerInterface;
import model.employee.EmployeeManageModel;
import model.employee.EmployeeManageModelInterface;

public class ManagerUIDisplay implements CustomUIDisplay {

    public ManagerUIDisplay() {
        BillManageModelInterface billModel = BillManageModel.getInstance();
        BillCreateControllerInterface billCreateController = BillCreateController.getInstance(billModel);
        
        BillHistoryControllerInterface billHistoryController = BillHistoryController.getInstance();
        
        StatisticsControllerInterface statisticsController = StatisticsController.getInstance();
        
        ProductManageModelInterface productModel = ProductManageModel.getInstance();
        ProductControllerInterface productControllerInterface = ProductController.getInstance(productModel);
        
        IngredientManageModelInterface ingredientModel = IngredientManageModel.getInstance();
        IngredientControllerInterface ingredientController = IngredientController.getInstance(ingredientModel);
        
        ProviderManageModelInterface providerModel = ProviderManageModel.getInstance();
        ProviderControllerInterface providerController = ProviderController.getInstance(providerModel);
        
        EmployeeManageModelInterface employeeModel = EmployeeManageModel.getInstance();
        EmployeeControllerInterface employeeController = EmployeeController.getInstance(employeeModel);
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
