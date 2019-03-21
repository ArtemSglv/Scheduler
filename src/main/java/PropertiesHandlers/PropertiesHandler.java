package PropertiesHandlers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Set;


public class PropertiesHandler {

    private PropertiesHandler() {}

    private static final String PROPERTIES_URI = "src/main/resources/schedule.properties";

    public static String getUrlByName(String propName) throws IOException {
        Properties prop = PropertiesHandler.loadPropertiesFile();
        return prop.getProperty(propName).split(",")[0];
    }

    public static LocalDateTime getDateByName(String propName) throws IOException {
        Properties prop = PropertiesHandler.loadPropertiesFile();
        return LocalDateTime.parse(prop.getProperty(propName).split(",")[1]);
    }


    public static void writeDate(String propName, LocalDateTime date) throws IOException {
        Properties prop = PropertiesHandler.loadPropertiesFile();
        prop.setProperty(propName, PropertiesHandler.getUrlByName(propName) + "," + date.toString());
        prop.store(new FileOutputStream(PROPERTIES_URI), null);
    }




    public static  String[] getAllNames() throws IOException {
        Properties prop = PropertiesHandler.loadPropertiesFile();
        Set<String> s = prop.stringPropertyNames();
        return s.toArray(new String[0]);
    }



    public static String[] getAllUrls() throws IOException {
        String[] names = PropertiesHandler.getAllNames();
        for (int i = 0; i < names.length; i++)
            names[i] = PropertiesHandler.getUrlByName(names[i]);
        return names;
    }


    public static Properties loadPropertiesFile() throws IOException {
        FileInputStream fis = new FileInputStream(PROPERTIES_URI);
        Properties prop = new Properties();
        prop.load(fis);
        fis.close();
        return prop;
    }


}
