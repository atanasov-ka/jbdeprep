package org.vb.backend.rest;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;


import org.vb.backend.dto.UserRSDTO;
import org.vb.backend.jpa.pojos.User;
import org.vb.backend.jpa.service.UserService;

@RequestScoped
@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UserRSDTOEndpoint {

	@EJB
	private UserService userService;
	
	@Context
	SecurityContext context;
	
	@PermitAll
	@POST
	@Path("/register")
	public Response registerNewUser(UserRSDTO user) {
		User registeredUser = userService.register(user.getUserName(), user.getHashedPassword(), false);
		if (registeredUser == null) {
			return Response.status(Status.CONFLICT.getStatusCode()).build();
		} else {
			return Response.status(Status.OK.getStatusCode()).build();
		}
	}
	
	@PermitAll
	@POST
	@Path("/authenticate")
	public Response authenticate(UserRSDTO user) {
		boolean isAuthenticated = userService.authenticate(user.getUserName(), user.getHashedPassword());
		if (isAuthenticated) {
			return Response.status(Status.OK.getStatusCode()).build();
		} else {
			return Response.status(Status.UNAUTHORIZED.getStatusCode()).build();
		}
	}
}
