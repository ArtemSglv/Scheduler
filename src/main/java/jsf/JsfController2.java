package jsf;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scheduler.SourceParseInformation;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import java.util.ArrayList;
import java.util.List;


@ManagedBean
@ViewScoped
public class JsfController2 {
    private List<SourceParseInformation> sourceParseInformations;
    @PostConstruct
    public void init(){
        sourceParseInformations = new ArrayList<>();
        sourceParseInformations.add(new SourceParseInformation("asd", "asd", "asd"));
    }

    public List<SourceParseInformation> getSourceParseInformations() {
        sourceParseInformations.add(new SourceParseInformation("asd", "asd", "asd"));
        return sourceParseInformations;
    }

    public void setSourceParseInformations(List<SourceParseInformation> sourceParseInformations) {
        this.sourceParseInformations = sourceParseInformations;
    }
}
