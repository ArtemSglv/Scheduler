package scheduler;

import PropertiesHandlers.PropertiesHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class SourceParseInformation {
    private String name;
    private Date date;
    private String description;

    public SourceParseInformation(String name, Date date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }

    public static   ArrayList<SourceParseInformation> getInfoForTable() throws IOException {
        ArrayList<SourceParseInformation> infos = new ArrayList<>();
        String[] names = new PropertiesHandler().getAllNames();
        for (int i = 0; i < names.length; i++)
            infos.add(new SourceParseInformation(names[i],new Date(), "asd"));

        return infos;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void show(){
        System.out.println("name:" + name);
        System.out.println("date:" + date);
        System.out.println("description:" + description);
    }




}
