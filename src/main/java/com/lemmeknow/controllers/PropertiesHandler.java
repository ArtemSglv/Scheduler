package com.lemmeknow.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Set;


public class PropertiesHandler {

    private PropertiesHandler() {

    }

    //private static final String PROPERTIES_URI = "/home/deploy/nc-project/scheduler.properties";
    private static final String PROPERTIES_URI = "src/main/resources/scheduler.properties";
    //private static final String PROPERTIES_URI = System.getenv().get("");
    private static Logger logger = LoggerFactory.getLogger(PropertiesHandler.class);

    public static String getUrlByName(String propName) {
        Properties prop = PropertiesHandler.loadPropertiesFile();
        return prop.getProperty(propName).split(",")[0];
    }

    public static LocalDateTime getDateByName(String propName) {
        Properties prop = PropertiesHandler.loadPropertiesFile();
        return LocalDateTime.parse(prop.getProperty(propName).split(",")[1]);
    }

    public static String getDescriptionByName(String propName) {
        Properties prop = PropertiesHandler.loadPropertiesFile();
        return prop.getProperty(propName).split(",")[2];
    }

    public static void writeDate(String propName, LocalDateTime date) {
        Properties prop = PropertiesHandler.loadPropertiesFile();
        prop.setProperty(propName, PropertiesHandler.getUrlByName(propName) + "," + date.toString() + "," +
                PropertiesHandler.getDescriptionByName(propName));
        try {
            prop.store(new FileOutputStream(PROPERTIES_URI), null);
            logger.info("Data saved");
        } catch (IOException e) {
            logger.error("Couldn't save new data");
            e.printStackTrace();
        }
    }

    public static String[] getAllNames() {
        Properties prop = PropertiesHandler.loadPropertiesFile();
        Set<String> s = prop.stringPropertyNames();
        return s.toArray(new String[0]);
    }

    public static String[] getAllUrls() {
        String[] names = PropertiesHandler.getAllNames();
        for (int i = 0; i < names.length; i++)
            names[i] = PropertiesHandler.getUrlByName(names[i]);
        return names;
    }

    public static Properties loadPropertiesFile() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(PROPERTIES_URI);
            Properties prop = new Properties();
            prop.load(fis);
            fis.close();
            logger.info("properties correctly uploaded");
            return prop;
        } catch (IOException e) {
            logger.error("something wrong with properties file");
            e.printStackTrace();
            return null;
        }


    }


}
