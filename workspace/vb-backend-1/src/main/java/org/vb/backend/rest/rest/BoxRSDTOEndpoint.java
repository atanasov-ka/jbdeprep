package org.vb.backend.rest.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
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

import org.vb.backend.jpa.pojos.Box;
import org.vb.backend.jpa.pojos.Verb;
import org.vb.backend.jpa.service.BoxDAO;
import org.vb.backend.rest.dto.BoxRSDTO;
import org.vb.backend.rest.dto.DTOMapper;

@RequestScoped
@Path("/box")
@Produces("application/json")
@Consumes("application/json")
public class BoxRSDTOEndpoint {

	@EJB
	private BoxDAO boxDao;
	
	@Context
	SecurityContext context;
	
	@RolesAllowed({"admin"})
	@POST
	public Response create(@Valid final BoxRSDTO boxrsdto) {
		//TODO: process the given boxrsdto 
		//you may want to use the following return statement, assuming that BoxRSDTO#getId() or a similar method 
		//would provide the identifier to retrieve the created BoxRSDTO resource:
		//return Response.created(UriBuilder.fromResource(BoxRSDTOEndpoint.class).path(String.valueOf(boxrsdto.getId())).build()).build();
				
		List<Verb> verbList = DTOMapper.getVerbList(boxrsdto.getVerbList());
		Box box = boxDao.createBox(boxrsdto.getName(), boxrsdto.getFront(), boxrsdto.getBack(), boxrsdto.isPublic(), context.getUserPrincipal().getName(), verbList);
		return Response.created(null).build();
	}

	@RolesAllowed("user")
	@GET
	@Path("/{id:[0-9]+}")
	public Response findById(@PathParam("id") final Long id) { 
		Box box = boxDao.getBoxById(id);
		BoxRSDTO boxrsdto = DTOMapper.getBoxDTO(box);
		if (boxrsdto == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(boxrsdto).build();
	}
	
	@RolesAllowed("user")
	@GET
	@Produces("application/json")
	public List<BoxRSDTO> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		
		List<Box> boxList = boxDao.getAll();
		List<BoxRSDTO> boxRsDtoList = DTOMapper.getBoxDTOList(boxList);
		return boxRsDtoList;
	}

	@RolesAllowed("user")
	@PUT
	@Path("/{id:[0-9]+}")
	public Response update(@PathParam("id") Long id, final BoxRSDTO boxrsdto) {
		Box box = DTOMapper.getBox(boxrsdto);
		box.setId(id);
		Box updatedBox = boxDao.updateBox(box);
		if (null == updatedBox) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(updatedBox).build();
	}

	@RolesAllowed("user")
	@DELETE
	@Path("/{id:[0-9]+}")
	public Response deleteById(@PathParam("id") final Long id) {
		
		String currentUser = context.getUserPrincipal().getName();
		boolean isAdmin = context.isUserInRole("admin");
		boolean isUser = context.isUserInRole("user");
		boolean deleted = false;
		if (isAdmin) {
			deleted = boxDao.deleteBoxById(id);
		} else if (isUser) {
			if (!boxDao.deleteBoxByIdAndUser(id, username)) {
				return Response.status(Status.METHOD_NOT_ALLOWED).build();
			}
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		if (!deleted) {
			return Response.status(Status.METHOD_NOT_ALLOWED).build();
		}
		return Response.ok().build();
	}

}
