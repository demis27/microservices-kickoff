package org.demis27.kickoff.helidon.person;






import org.demis27.kickoff.helidon.person.client.ClientController;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@ApplicationPath("/data")
@ApplicationScoped
public class PersonRestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> classes = new HashSet<>();

        // resources
        classes.add(HelloController.class);
        
        
        
        
        classes.add(ClientController.class);
        
        
        return classes;
    }
}
