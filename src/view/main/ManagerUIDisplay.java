package view.main;

import model.user.UserModelInterface;
import view.SideMenuPanel;

public class ManagerUIDisplay implements CustomUIDisplay {

    @Override
    public void customUIDisplay(MainFrame mainFrame, UserModelInterface model) {
        SideMenuPanel sideMenuPanel = mainFrame.getPanelSideMenu();
        
        sideMenuPanel.setHomeFunctionState(true);
        sideMenuPanel.setSellButtonState(true);
        sideMenuPanel.setStatisticButtonState(true);
        sideMenuPanel.setProductManageButtonState(true);
        sideMenuPanel.setEmployeeButtonState(true);
        
        sideMenuPanel.setLabelGreetingText("Hello, manager");
    }

}
