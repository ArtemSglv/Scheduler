package scheduler;

import PropertiesHandlers.PropertiesHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


public class SourceParseInformation {
    private String name;
    private LocalDateTime date;


    public SourceParseInformation(String name, LocalDateTime date) {
        this.name = name;
        this.date = date;

    }

    public static   ArrayList<SourceParseInformation> getInfoForTable() throws IOException {
        ArrayList<SourceParseInformation> infos = new ArrayList<>();
        String[] names = PropertiesHandler.getAllNames();

        for (int i = 0; i < names.length; i++)
            infos.add(new SourceParseInformation(names[i],PropertiesHandler.getDateByName(names[i]) ));

        return infos;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void show(){
        System.out.println("name:" + name);
        System.out.println("date:" + date);

    }




}
