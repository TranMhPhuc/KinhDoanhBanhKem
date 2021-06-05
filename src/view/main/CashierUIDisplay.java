package view.main;

import model.user.UserModelInterface;
import view.SideMenuPanel;

public class CashierUIDisplay implements CustomUIDisplay {

    @Override
    public void customUIDisplay(MainFrame mainFrame, UserModelInterface model) {
        mainFrame.setHomeFunctionState(true);
        mainFrame.setSellFunctionState(true);
        mainFrame.setStatisticFunctionState(false);
        mainFrame.setProductManageFunctionState(false);
        mainFrame.setEmployeeFunctionState(false);
        
        mainFrame.setLabelGreetingText("Hello, cashier");
    }

}
