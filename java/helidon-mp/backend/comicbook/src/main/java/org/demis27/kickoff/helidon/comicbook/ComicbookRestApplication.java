package org.demis27.kickoff.helidon.comicbook;

import org.demis27.kickoff.helidon.comicbook.client.ClientController;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/comicbook/v1/comicbook")
@ApplicationScoped
public class ComicbookRestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> classes = new HashSet<>();
        // resources
        classes.add(ComicbookController.class);
        return classes;
    }
}
