package jsf;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "jsfController")
public class JsfController {

        public String loadSchedulerPage() {
            checkPermission();
            return "/scheduler.xhtml";
        }

        private void checkPermission() {
            // Details omitted
        }
}
