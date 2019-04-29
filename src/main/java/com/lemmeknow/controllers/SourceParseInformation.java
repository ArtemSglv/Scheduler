package com.lemmeknow.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class SourceParseInformation {
    private String name;
    private LocalDateTime date;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SourceParseInformation(String name, LocalDateTime date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;

    }

    public static   ArrayList<SourceParseInformation> getInfoForTable() throws IOException {
        //todo: relative file path
        ArrayList<SourceParseInformation> infos = new ArrayList<>();
        String[] names = PropertiesHandler.getAllNames();

        for (int i = 0; i < names.length; i++)
            infos.add(new SourceParseInformation(names[i],PropertiesHandler.getDateByName(names[i]),
                            PropertiesHandler.getDescriptionByName(names[i]) ));

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
        System.out.println("description:" + description);
    }




}
