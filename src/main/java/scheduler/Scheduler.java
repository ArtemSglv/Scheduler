package scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Configuration
@EnableScheduling
public class Scheduler {
    static final String PARSER_URL = "http://localhost:8082/parse_all";
    static final String CRON_EXP = "0 0 3 * * ?";

//https://dzone.com/articles/schedulers-in-java-and-spring
    @Scheduled(cron = CRON_EXP)
    public void parserScheduler(){
        runParser("https://www.afisha.ru");
        System.out.println("Now: " + new Date());
    }
// для тестирования
 //   @Scheduled(fixedRate = 6000)
    public void testScheduler(){
        runParser("https://www.afisha.ru");
        System.out.println("Now: " + new Date());
    }

    public void runParser(String whatToParse){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate rt = new RestTemplate();
        // Data attached to the request.
        HttpEntity<String> requestBody = new HttpEntity<>(whatToParse, headers);
        // Send request with POST method.
        String str = rt.postForObject(PARSER_URL, requestBody, String.class);
        System.out.println(str);
    }
}


