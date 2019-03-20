package scheduler;

import PropertiesHandlers.PropertiesHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import scheduler.SourceParseInformation;


import javax.annotation.PostConstruct;

import javax.inject.Named;


import java.io.IOException;
import java.util.*;

import static scheduler.Scheduler.PARSER_URL;


@Named
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
        for (int i = 0; i < selectedInfos.length; i++) {
            whatToParse = PropertiesHandler.getUrlByName(selectedInfos[i].getName());
            answer = parse(whatToParse);
            if (answer.equals("Someting gone wrong")) {
                System.out.println("error while parsing " + whatToParse);
            }
        }

    }
    public void parseAll() throws IOException {
        String answer;
        for(String url : new PropertiesHandler().getAllUrls()){
            answer = parse(url);
            if (answer.equals("Someting gone wrong")) {
                System.out.println("error while parsing " + url);
            }
        }
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
