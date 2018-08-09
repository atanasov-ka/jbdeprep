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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.vb.backend.dto.BoxPlayRSDTO;
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
	
//	@POST
//	public Response create(final BoxPlayRSDTO boxplayrsdto) {
//		//TODO: process the given boxplayrsdto 
//		//you may want to use the following return statement, assuming that BoxPlayRSDTO#getId() or a similar method 
//		//would provide the identifier to retrieve the created BoxPlayRSDTO resource:
//		//return Response.created(UriBuilder.fromResource(BoxPlayRSDTOEndpoint.class).path(String.valueOf(boxplayrsdto.getId())).build()).build();
//		return Response.created(null).build();
//	}

	@GET
	@Path("/{id:[0-9]+}")
	public Response findById(@PathParam("id") final Long id) {
		BoxPlayRSDTO boxplayrsdto = playService.findBoxPlayByBoxIdAndPrincipal(id, getUsername());
		if (boxplayrsdto == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(boxplayrsdto).build();
	}

	@GET
	public List<BoxPlayRSDTO> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the boxplayrsdtoes 
		final List<BoxPlayRSDTO> boxplayrsdtoes = null;
		return boxplayrsdtoes;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final BoxPlayRSDTO boxplayrsdto) {
		//TODO: process the given boxplayrsdto 
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		//TODO: process the boxplayrsdto matching by the given id 
		return Response.noContent().build();
	}
	
	private String getUsername() { 
		return context.getUserPrincipal().getName();
	}
}
