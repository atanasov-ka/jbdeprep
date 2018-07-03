/**
 * 
 */
package org.dep.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Estafet
 *
 */
@RequestScoped
@Path("service")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class MyFirstRestService {
	
	@GET
	@Path("heathchek")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String testMe() {
		return "It Works";
	}

}
