package view.main;

import model.user.UserModelInterface;
import view.SideMenuPanel;

public class AccountantUIDisplay implements CustomUIDisplay {

    @Override
    public void customUIDisplay(MainFrame mainFrame, UserModelInterface model) {
        SideMenuPanel sideMenuPanel = mainFrame.getPanelSideMenu();

        sideMenuPanel.setHomeFunctionState(true);
        sideMenuPanel.setSellButtonState(false);
        sideMenuPanel.setStatisticButtonState(true);
        sideMenuPanel.setProductManageButtonState(false);
        sideMenuPanel.setEmployeeButtonState(false);

        sideMenuPanel.setLabelGreetingText("Hello, accountant");
    }

}
