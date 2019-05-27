package com.lemmeknow.controllers;


import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;


@Named
public class SchedulerController {
    private final static String LOCAL_PARSER_URL = "http://localhost:8094/parser/parse";
	private final static String PARSER_URL = "http://lemmeknow.tk/parser/parse";
	private final static String PARSER1_URL = "/parser/parse";

    private List<SourceParseInformation> sourceParseInformations;
    private SourceParseInformation[] selectedInfos;
    private Date time;
    private Logger logger = LoggerFactory.getLogger(SchedulerController.class);


    public String parse(String whatToParse){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.setContentType(MediaType.APPLICATION_JSON);
            RestTemplate rt = new RestTemplate();
            // Data attached to the request.
            HttpEntity<String> requestBody = new HttpEntity<>(whatToParse, headers);
            // Send request with POST method.
            return rt.postForObject(PARSER_URL, requestBody, String.class);
        }catch (Exception e){
            logger.warn("Error while parsing(parse error) " + whatToParse);
            return null;
        }
    }


    public void parseSelected() throws IOException {
        String whatToParse, answer;
        Boolean isOk = true;
        for (int i = 0; i < selectedInfos.length; i++) {
            whatToParse = PropertiesHandler.getUrlByName(selectedInfos[i].getName());

            answer = parse(whatToParse);
            if(answer.equals("OK")){
                selectedInfos[i].setDate(LocalDateTime.now());//если спарсилось, то меняем дату последнего парсинга на сейчас
                PropertiesHandler.writeDate(selectedInfos[i].getName(), LocalDateTime.now());
            }
            if (answer.equals("Someting gone wrong")) {
                isOk = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Warning!", "error while parsing " + whatToParse));
                System.out.println("error while parsing(parse selected error) " + whatToParse);
            }
        }
        if (isOk) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Info", "OK"));
            logger.info("parseSelected is OK");
        }

    }
    public void parseAll() throws IOException {
        String answer, url;
        Boolean isOk = true;
        for(SourceParseInformation spi : sourceParseInformations){
            url = PropertiesHandler.getUrlByName(spi.getName());
            answer = parse(url);
            if(answer.equals("OK")) {
                spi.setDate(LocalDateTime.now());//если спарсилось, то меняем дату последнего парсинга на сейчас
                PropertiesHandler.writeDate(spi.getName(), LocalDateTime.now());
            }
            if (answer.equals("Someting gone wrong")) {
                isOk = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Warning!", "error while parsing " + url));
                logger.warn("error while parsing " + url);
            }
        }
        if (isOk) FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Info", "OK"));

    }

    public void onDateSelect(SelectEvent event){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        new Scheduler().reSchedule(Scheduler.dateToCron(time));
        logger.info("Новая дата парсинга " + time);
        //facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }


    /*
    public void click() {
        PrimeFaces.current().ajax().update("form:display");
        PrimeFaces.current().executeScript("PF('dlg').show()");
    }
*/


    public void init() throws IOException {
        sourceParseInformations = SourceParseInformation.getInfoForTable();
        selectedInfos = new SourceParseInformation[sourceParseInformations.size()];
    }
    //https://www.primefaces.org/showcase-ext/sections/timePicker/basicUsage.jsf

    public List<SourceParseInformation> getSourceParseInformations() {

        return sourceParseInformations;
    }

    public void setSourceParseInformations(List<SourceParseInformation> sourceParseInformations) {
        this.sourceParseInformations = sourceParseInformations;
    }

    public SourceParseInformation[] getSelectedInfos() {
        return selectedInfos;
    }

    public void setSelectedInfos(SourceParseInformation[] selectedInfos) {
        this.selectedInfos = selectedInfos;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }



    private String getFormattedTime(Date time, String format) {
        if (time == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(time);
    }

}
