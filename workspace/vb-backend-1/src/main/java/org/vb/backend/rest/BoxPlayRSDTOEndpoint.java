package org.vb.backend.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.vb.backend.dto.BoxPlayRSDTO;
import org.vb.backend.dto.VerbPlayRSDTO;
import org.vb.backend.jpa.service.PlayService;

@RequestScoped
@Path("/play")
@Produces("application/json")
@Consumes("application/json")
public class BoxPlayRSDTOEndpoint {

	@EJB
	private PlayService playService;
	
	@Context
	SecurityContext context;

	@GET
	@Path("/{id:[0-9]+}")
	public Response findById(@PathParam("id") final Long id) {
		BoxPlayRSDTO boxplayrsdto = playService.findBoxPlayByBoxIdAndUser(id, getUsername());
		if (boxplayrsdto == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(boxplayrsdto).build();
	}

	@PUT
	@Path("/{id:[0-9]+}")
	public Response update(@PathParam("id") Long id, final List<VerbPlayRSDTO> verbPlayList) {
		playService.updateVerbPlays(id, verbPlayList, getUsername());
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id:[0-9]+}")
	public Response deleteById(@PathParam("id") final Long id) {
		playService.removeHistorOfBoxPlay(id, getUsername());
		return Response.noContent().build();
	}
	
	private String getUsername() { 
		return context.getUserPrincipal().getName();
	}
}
