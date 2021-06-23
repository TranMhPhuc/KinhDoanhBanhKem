package control.setting;

import model.setting.AppSetting;
import view.main.manager.ManagerSettingsPanel;

public class ManagerSettingController extends SettingController {

    private ManagerSettingsPanel managerSettingsPanel;

    public ManagerSettingController(AppSetting appSettingModel) {
        super(appSettingModel);
    }

    public void setManagerSettingPanel(ManagerSettingsPanel managerSettingsPanel) {
        this.managerSettingsPanel = managerSettingsPanel;
        managerSettingsPanel.setAppSettingModel(appSettingModel);
        managerSettingsPanel.setManagerSettingController(this);
    }

    @Override
    public void requestChangeDiagnosticState() {
        boolean showWarning = managerSettingsPanel.getDiagnosticFlagInput();
        appSettingModel.setDiagnosticFlag(showWarning);
    }

}
