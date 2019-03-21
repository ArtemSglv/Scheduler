package scheduler;

import PropertiesHandlers.PropertiesHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import scheduler.SourceParseInformation;


import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static scheduler.Scheduler.PARSER_URL;


@Named
@ManagedBean
public class SchedulerController {
    private List<SourceParseInformation> sourceParseInformations;
    private SourceParseInformation[] selectedInfos;


    public String parse(String whatToParse){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate rt = new RestTemplate();
        // Data attached to the request.
        HttpEntity<String> requestBody = new HttpEntity<>(whatToParse, headers);
        // Send request with POST method.
        return rt.postForObject(PARSER_URL, requestBody, String.class);
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
                System.out.println("error while parsing " + whatToParse);
            }
        }
        if (isOk) FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Info", "OK"));

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
                System.out.println("error while parsing " + url);
            }
        }
        if (isOk) FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Info", "OK"));
        System.out.println("ok");
    }


    public void init() throws IOException {
        sourceParseInformations = SourceParseInformation.getInfoForTable();
        selectedInfos = new SourceParseInformation[sourceParseInformations.size()];
    }


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

}
