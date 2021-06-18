package control.setting;

import model.setting.AppSetting;

public class AccountantSettingController extends SettingController {

    public AccountantSettingController(AppSetting appSettingModel) {
        super(appSettingModel);
    }

    @Override
    public void requestInputExcelPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void validatePDFPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void requestChangeDiagnosticState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
