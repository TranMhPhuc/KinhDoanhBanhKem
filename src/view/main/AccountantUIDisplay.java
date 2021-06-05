package view.main;

import model.user.UserModelInterface;
import view.SideMenuPanel;

public class AccountantUIDisplay implements CustomUIDisplay {

    @Override
    public void customUIDisplay(MainFrame mainFrame, UserModelInterface model) {
        mainFrame.setHomeFunctionState(true);
        mainFrame.setSellFunctionState(false);
        mainFrame.setStatisticFunctionState(true);
        mainFrame.setProductManageFunctionState(false);
        mainFrame.setEmployeeFunctionState(false);
        
        mainFrame.setLabelGreetingText("Hello, accountant");
    }

}
