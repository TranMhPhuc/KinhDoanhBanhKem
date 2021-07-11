package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppLog {

    private static final String CONFIG_PROPERTY = "log4j2.configurationFile";
    private static final String CONFIG_PATH = new File("").getAbsolutePath()
            + "/src/appconfig/log4j2.properties";

    private static final Logger logger;

    static {
        if (!new File(CONFIG_PATH).exists()) {
            writeNewLog4j2();
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

    private static void writeNewLog4j2() {
        String log4j2 = "status = error\n" +
        "dest = err\n" +
        "name = PropertiesConfig\n" +
        "property.foldername = log\n" +
        "property.filename = ${foldername}/app.log\n" +
        "filter.threshold.type = ThresholdFilter\n" +
        "filter.threshold.level = debug\n" +
        "appender.console.type = Console\n" +
        "appender.console.name = STDOUT\n" +
        "appender.console.layout.type = PatternLayout\n" +
        "appender.console.layout.pattern = %d{yyyy-MM-dd HH:mm:ss} [%-5p] %c{1}:%L - %m%n\n" +
        "appender.console.filter.threshold.type = ThresholdFilter\n" +
        "appender.console.filter.threshold.level = debug\n" +
        "rootLogger.level = debug\n" +
        "rootLogger.appenderRef.stdout.ref = STDOUT";
        
        String path = new File("").getAbsolutePath() + "/src/appconfig";
        File file = new File(path);
        file.mkdirs();
        
        try {
            FileWriter fw = new FileWriter(CONFIG_PATH);
            fw.write(log4j2);
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
