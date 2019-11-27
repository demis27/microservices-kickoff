package org.demis27.kickoff.helidon.comicbook;

import org.demis27.kickoff.helidon.common.Comicbook;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Singleton
public class ComicbookController {

    private ComicbookRepository comicbookRepository;

    @Inject
    public ComicbookController(ComicbookRepository comicbookRepository) {
        this.comicbookRepository = comicbookRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comicbook> list() {
        return comicbookRepository.list();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comicbook get(@PathParam("id") String id) {
        return comicbookRepository.get(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Comicbook post(Comicbook person) {
        return comicbookRepository.create(person);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Comicbook put(@PathParam("id") String id, Comicbook comicbook) {
        return comicbookRepository.update(comicbook);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id) {
        comicbookRepository.delete(id);
    }

}
