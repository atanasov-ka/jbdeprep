package org.vb.backend.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;

import org.vb.backend.jpa.service.BoxService;
import org.vb.backend.dto.BoxRSDTO;
import org.vb.backend.dto.VerbRSDTO;

@RequestScoped
@Path("/box")
@Produces("application/json")
@Consumes("application/json")
public class BoxRSDTOEndpoint {

	@EJB
	private BoxService boxService;

	@Context
	SecurityContext context;
	
	@RolesAllowed("user")
	@POST
	public Response create(@Valid final BoxRSDTO boxrsdto) {
		BoxRSDTO box = boxService.createBox(boxrsdto, getUsername());
		if (box == null) {
			return Response.status(Status.NOT_MODIFIED).build();
		} else {
			return Response.ok(box).build();
		}
	}

	@RolesAllowed("user")
	@GET
	@Path("/{id:[0-9]+}")
	public Response findById(@PathParam("id") final Long id) { 
		BoxRSDTO box = boxService.getBoxById(id, getUsername(), isAdmin());
		if (box == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(box).build();
	}
	
	@RolesAllowed("user")
	@GET
	@Path("/{id:[0-9]+}/verb")
	public Response findVerbsByBoxId(@PathParam("id") final Long id) { 
		List<VerbRSDTO> verbList = boxService.getVerbsByBoxId(id, getUsername(), isAdmin());
		if (verbList == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(verbList).build();
	}
	
	@RolesAllowed("user")
	@GET
	@Produces("application/json")
	public List<BoxRSDTO> listAll(@QueryParam("start") final Integer startPosition, @QueryParam("max") final Integer maxResult) {
		List<BoxRSDTO> boxList = boxService.getAll(getUsername(), isAdmin());
		return boxList;
	}
	
	@RolesAllowed("user")
	@GET
	@Path("/byGroup/{groupId}")
	@Produces("application/json")
	public List<BoxRSDTO> listAllByGroupId(@QueryParam("start") final Integer startPosition, @QueryParam("max") final Integer maxResult, @PathParam("groupId") Long groupId) {
		List<BoxRSDTO> result = boxService.getBoxListByGroupId(getUsername(), isAdmin(), groupId);
		return result;
	}

	@RolesAllowed("user")
	@PUT
	@Path("/{id:[0-9]+}")
	public Response update(@PathParam("id") Long id, final BoxRSDTO boxrsdto) {
		BoxRSDTO updatedBox = boxService.updateBox(boxrsdto);
		if (null == updatedBox) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(updatedBox).build();
	}

	@RolesAllowed("user")
	@DELETE
	@Path("/{id:[0-9]+}")
	public Response deleteById(@PathParam("id") final Long id) {
		
		String currentUser = getUsername();
		boolean isAdmin = isAdmin();
		boolean isUser = isRegularUser();
		boolean deleted;
		if (isAdmin) {
			deleted = boxService.deleteBoxById(id);
		} else if (isUser) {
			deleted = boxService.deleteBoxByIdAndUser(id, currentUser);
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		if (!deleted) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.ok().build();
	}

	private String getUsername() {
		String username = context.getUserPrincipal().getName();
		// auto registration
		//userService.register(username, isAdmin(), isRegularUser());
		return username;
	}
	
	private boolean isAdmin() {
		return context.isUserInRole("admin");
	}
	
	private boolean isRegularUser() {
		return context.isUserInRole("user");
	}
}
