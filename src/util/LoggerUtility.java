package util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerUtility {

    private static boolean initialized = false;

    public static void initialize() {
        if (!initialized) {
            PropertyConfigurator.configure("src/log4j.properties");
            initialized = true;
        }
    }

    public static Logger getLogger(Class<?> clazz) {
        if (!initialized) {
            initialize();
        }
        return Logger.getLogger(clazz);
    }

    public static Logger getLogger(String name) {
        if (!initialized) {
            initialize();
        }
        return Logger.getLogger(name);
    }
}