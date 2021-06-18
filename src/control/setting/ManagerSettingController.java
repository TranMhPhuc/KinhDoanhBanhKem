package control.setting;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.setting.AppSetting;
import view.PathInputState;
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
    public void requestInputExcelPath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Edit excel program path");

        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Excel execution file", "exe", "EXE");

        fileChooser.setFileFilter(fileFilter);

        int ret = fileChooser.showOpenDialog(managerSettingsPanel.getMainFrame());

        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();
        appSettingModel.setExcelProgramPath(file.getAbsolutePath());
        managerSettingsPanel.setExcelPathInputState(PathInputState.CORRECT);
    }

    @Override
    public void validatePDFPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void requestChangeDiagnosticState() {
        boolean showWarning = managerSettingsPanel.getDiagnosticFlagInput();
        appSettingModel.setDiagnosticFlag(showWarning);
    }

}
