package utils;

import java.io.*;
import java.util.Properties;

//Helper class to get API_KEY from .env file not to store it in code

public class EnvVariables {
    static final Properties properties = new Properties();
    static final File propertiesFile = new File(".env");

    static {
        try (FileInputStream inputStream = new FileInputStream(propertiesFile)) {
            properties.load(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static final String API_KEY = properties.getProperty("API_KEY");
}