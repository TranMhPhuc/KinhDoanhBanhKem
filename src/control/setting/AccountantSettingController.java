package control.setting;

import model.setting.AppSetting;
import view.main.accountant.AccountantSettingsPanel;

public class AccountantSettingController extends SettingController {

    private AccountantSettingsPanel accountantSettingsPanel;
    
    public AccountantSettingController(AppSetting appSettingModel) {
        super(appSettingModel);
    }
    
    public void setManagerSettingPanel(AccountantSettingsPanel accountantSettingsPanel) {
        if (accountantSettingsPanel == null) {
            throw new NullPointerException();
        }
        this.accountantSettingsPanel = accountantSettingsPanel;
        accountantSettingsPanel.setAppSettingModel(appSettingModel);
        accountantSettingsPanel.setAccountantSettingController(this);
    }

    @Override
    public void requestChangeDiagnosticState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
