package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppLog {

    private static final String CONFIG_PROPERTY = "log4j2.configurationFile";
    private static final String CONFIG_PATH = new File("").getAbsolutePath()
            + "/src/appconfig/log4j2.properties";

    private static final Logger logger;

    static {
        if (!new File(CONFIG_PATH).exists()) {
            throw new AssertionError("Can't read log4j2 configuration file.");
        }

        if (System.getProperty(CONFIG_PROPERTY) == null) {
            System.setProperty(CONFIG_PROPERTY, CONFIG_PATH);
        }
        logger = LogManager.getLogger(AppLog.class);
    }

    private AppLog() {
    }

    public static Logger getLogger() {
        return logger;
    }

}
