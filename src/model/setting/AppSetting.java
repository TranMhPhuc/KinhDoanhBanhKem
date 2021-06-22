package model.setting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AppSetting {

    public enum Language {
        ENGLISH {
            @Override
            public String toString() {
                return "English";
            }
        },
        VIETNAMESE {
            @Override
            public String toString() {
                return "Vietnamese";
            }
        };
        
        public static Language getFromName(String name) {
            for (Language language : values()) {
                if (language.name().equals(name)) {
                    return language;
                }
            }
            return ENGLISH;
        }
        
        public static Language getFromToString(String toString) {
            for (Language language : values()) {
                if (language.toString().equals(toString)) {
                    return language;
                }
            }
            return ENGLISH;
        }
    }
    
    public static final String PDF_PROGRAM_PATH_PROPERTY_KEY = "pdfPath";
    public static final String EXCEL_PROGRAM_PATH_PROPERTY_KEY = "excelPath";
    public static final String APP_LANGUAGE_PROPERTY_KEY = "language";
    public static final String DIAGNOSTIC_STATE_PROPERTY_KEY = "diagnostic";
    public static final String CONFIRM_SIGN_OUT_PROPERTY_KEY = "confirmSignOut";
    public static final String CONFIRM_EXIT_PROPERTY_KEY = "confirmExit";

    private static final String CONFIG_PATH = new File("").getAbsolutePath()
            + "/src/appconfig/setting.properties";
    
    private volatile static AppSetting uniqueInstance;

    private Properties properties;
    
    private List<SettingUpdateObserver> observers;

    private AppSetting() {
        properties = new Properties();
        observers = new ArrayList<>();
        readConfig();
    }

    public static AppSetting getInstance() {
        if (uniqueInstance == null) {
            synchronized (AppSetting.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new AppSetting();
                }
            }
        }
        return uniqueInstance;
    }
    
    public void registerObserver(SettingUpdateObserver observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        this.observers.add(observer);
        observer.updateSettingObserver();
    }
    
    public void removeObserver(SettingUpdateObserver observer) {
        int id = observers.indexOf(observer);
        if (id >= 0) {
            observers.remove(observer);
        }
    }
    
    private void notifyObserver() {
        observers.forEach(observer -> {
            observer.updateSettingObserver();
        });
    }

    private void readConfig() {
        if (!new File(CONFIG_PATH).exists()) {
            writePropertyNew();
            return;
        }

        try (InputStream inputStream = new FileInputStream(CONFIG_PATH)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setExcelProgramPath(String excelProgramPath) {
        properties.setProperty(EXCEL_PROGRAM_PATH_PROPERTY_KEY, excelProgramPath);
        notifyObserver();
    }
    
    public void setPDFProgramPath(String pdfProgramPath) {
        properties.setProperty(PDF_PROGRAM_PATH_PROPERTY_KEY, pdfProgramPath);
        notifyObserver();
    }

    public void setConfirmSignOutFlag(boolean confirmSignOut) {
        properties.setProperty(CONFIRM_SIGN_OUT_PROPERTY_KEY, confirmSignOut ? "true" : "false");
    }

    public void setConfirmExitFlag(boolean confirmExit) {
        properties.setProperty(CONFIRM_EXIT_PROPERTY_KEY, confirmExit ? "true" : "false");
    }

    public void setAppLanguage(Language appLanguage) {
        properties.setProperty(APP_LANGUAGE_PROPERTY_KEY, appLanguage.toString());
        notifyObserver();
    }
    
    public void setDiagnosticFlag(boolean diagnosticFlag) {
        properties.setProperty(DIAGNOSTIC_STATE_PROPERTY_KEY, diagnosticFlag ? "true" : "false");
        notifyObserver();
    }
    
    public String getExcelProgramPath() {
        return properties.getProperty(EXCEL_PROGRAM_PATH_PROPERTY_KEY);
    }
    
    public String getPDFProgramPath() {
        return properties.getProperty(PDF_PROGRAM_PATH_PROPERTY_KEY);
    }

    public boolean getConfirmSignOutFlag() {
        return properties.getProperty(CONFIRM_SIGN_OUT_PROPERTY_KEY).equals("true");
    }

    public boolean getConfirmExitFlag() {
        return properties.getProperty(CONFIRM_EXIT_PROPERTY_KEY).equals("true");
    }
    
    public boolean getDiagnosticFlag() {
        return properties.getProperty(DIAGNOSTIC_STATE_PROPERTY_KEY).equals("true");
    }

    public Language getAppLanguage() {
        String lanaguageName = properties.getProperty(APP_LANGUAGE_PROPERTY_KEY);
        return Language.getFromToString(lanaguageName);
    }

    public void writeProperty() {
        try (OutputStream output = new FileOutputStream(CONFIG_PATH)) {
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    
    public void writePropertyNew() {
        try (OutputStream output = new FileOutputStream(CONFIG_PATH)) {
            properties.setProperty(EXCEL_PROGRAM_PATH_PROPERTY_KEY, "");
            properties.setProperty(PDF_PROGRAM_PATH_PROPERTY_KEY, "");
            properties.setProperty(APP_LANGUAGE_PROPERTY_KEY, Language.ENGLISH.toString());
            properties.setProperty(DIAGNOSTIC_STATE_PROPERTY_KEY, "true");
            properties.setProperty(CONFIRM_SIGN_OUT_PROPERTY_KEY, "true");
            properties.setProperty(CONFIRM_EXIT_PROPERTY_KEY, "true");
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
