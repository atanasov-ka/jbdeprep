/**
 * 
 */
package org.dep.rest;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;

import org.dep.beans.LoggerBean;
import org.dep.beans.MessageCounterBean;
import org.dep.beans.MessageSender;
import org.dep.qualifiers.MessageCounter;

/**
 * @author Estafet
 *
 */
@RequestScoped
@Path("service")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class MyFirstRestService {
	
	@PostConstruct
	private void postConstruct() {
		System.out.println("==> My rest service constructed!");
	}
	
	@Inject
	private MessageSender messageSender;
	
	@Inject @MessageCounter
	private MessageCounterBean messageCounter;
	
	@Inject
	private LoggerBean logger;
	
	@GET
	@Path("heathchek")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String testMe() {
		
		return "It Works" + messageCounter.getCount();
	}
	
	@POST
	@Path("send")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String sendToAirport(String body) {
		logger.logMessage("Received: " + body);
		String forSending = String.format("[%s]:[%s]", messageCounter.getCount(), body);
		messageSender.sendToAirport(forSending);
		return String.format("Sent: " + forSending);
	}

}
