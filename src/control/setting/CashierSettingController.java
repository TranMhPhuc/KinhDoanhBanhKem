package control.setting;

import model.setting.AppSetting;
import view.main.cashier.CashierSettingsPanel;

public class CashierSettingController extends SettingController {

    private CashierSettingsPanel cashierSettingsPanel;

    public CashierSettingController(AppSetting appSettingModel) {
        super(appSettingModel);
    }

    public void setCashierSettingsPanel(CashierSettingsPanel cashierSettingsPanel) {
        if (cashierSettingsPanel == null) {
            throw new NullPointerException();
        }
        this.cashierSettingsPanel = cashierSettingsPanel;
        cashierSettingsPanel.setAppSettingModel(appSettingModel);
        cashierSettingsPanel.setCashierSettingController(this);
    }

    @Override
    public void requestChangeDiagnosticState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
