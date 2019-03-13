package PropertiesHandlers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

@Scope(value = "session")
@Component(value = "propertiesHandler")
public class PropertiesHandler {


        static final String PROPERTIES_URI = "src/main/resources/schedule.properties";

        public String getUrlByName(String propName) throws IOException {
            Properties prop = PropertiesHandler.loadPropertiesFile();
            return prop.getProperty(propName);
        }

        public void addParsingTarget(String propName, String propUri) throws IOException {
            //FileOutputStream fos = new FileOutputStream(PROPERTIES_URI);// В этом случае удаляет все старые проперти
            Properties prop = PropertiesHandler.loadPropertiesFile();
            prop.setProperty(propName, propUri);
            prop.store(new FileOutputStream(PROPERTIES_URI),null);//как закрывать файл?
        }

        public String[] getAllNames() throws IOException{
            Properties prop = PropertiesHandler.loadPropertiesFile();
            Set<String> s = prop.stringPropertyNames();
            return s.toArray(new String[s.size()]);
        }
        public String[] getAllUrls() throws IOException{
            Properties prop = PropertiesHandler.loadPropertiesFile();
            PropertiesHandler p = new PropertiesHandler();
            String[] names = p.getAllNames();
            String[] urls = new String[names.length];
            for(int i = 0; i < names.length; i++)
                names[i] = p.getUrlByName(names[i]);
            return names;

        }

        public static Properties loadPropertiesFile() throws IOException {
            FileInputStream fis = new FileInputStream(PROPERTIES_URI);;
            Properties prop = new Properties();
            prop.load(fis);
            fis.close();
            return prop;
        }


    public static void main(String[] args) throws IOException {
        PropertiesHandler ph = new PropertiesHandler();
        System.out.println(ph.getUrlByName("afishaCinema"));
        System.out.println(ph.getUrlByName("afishaTheatre"));
        System.out.println(ph.getUrlByName("afishaExhibition"));
        System.out.println(ph.getUrlByName("afishaConcert"));
        ph.addParsingTarget("asd", "123");
        System.out.println(ph.getUrlByName("asd"));
        for(String str : ph.getAllNames())
            System.out.println(str);
        for(String str : ph.getAllUrls())
            System.out.println(str);
    }
}
