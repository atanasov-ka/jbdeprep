package org.airplane.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.airplane.jms.MessageSender;

@RequestScoped
@Path("service")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class AirplaneService {
	
	@Inject
	private MessageSender sender;
	
	@GET
	@Path("mayday")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response broadcastMaydaySignal() {
		sender.sendMayday();
		return Response.ok().build();
	}

}
