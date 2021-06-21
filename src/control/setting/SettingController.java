package control.setting;

import model.setting.AppSetting;

public abstract class SettingController {

    protected AppSetting appSettingModel;

    public SettingController(AppSetting appSettingModel) {
        this.appSettingModel = appSettingModel;
    }
    
    public void requestChangeLanguage(AppSetting.Language language) {
        if (language == null) {
            throw new NullPointerException();
        }
        appSettingModel.setAppLanguage(language);
    }
    
    public abstract void requestChangeDiagnosticState();
    
    public void requestChangeConfirmSignOutFlag(boolean flag) {
        appSettingModel.setConfirmSignOutFlag(flag);
    }
    
    public void requestChangeConfirmExitFlag(boolean flag) {
        appSettingModel.setConfirmExitFlag(flag);
    }
    
}
