package view.main;

import model.user.UserModelInterface;
import view.SideMenuPanel;

public class CashierUIDisplay implements CustomUIDisplay {

    @Override
    public void customUIDisplay(MainFrame mainFrame, UserModelInterface model) {
        SideMenuPanel sideMenuPanel = mainFrame.getPanelSideMenu();
        
        sideMenuPanel.setHomeFunctionState(true);
        sideMenuPanel.setSellButtonState(true);
        sideMenuPanel.setStatisticButtonState(false);
        sideMenuPanel.setProductManageButtonState(false);
        sideMenuPanel.setEmployeeButtonState(false);
        
        sideMenuPanel.setLabelGreetingText("Hello, cashier");
    }

}
