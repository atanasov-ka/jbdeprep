package org.vb.backend.rest;

import org.vb.backend.dto.GroupRSDTO;
import org.vb.backend.jpa.service.GroupService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@RequestScoped
@Path("/category")
@Produces("application/json")
@Consumes("application/json")
public class CategoryRSDTOEndpoint {

    @EJB
    private GroupService groupService;

    @Context
    SecurityContext context;

    @RolesAllowed("user")
    @GET
    public List<GroupRSDTO> listAllCategories(@QueryParam("start") final Integer startPosition, @QueryParam("max") final Integer maxResult) {
        List<GroupRSDTO> groupList = groupService.getAllGroups(getUsername(), isAdmin());
        return groupList;
    }

    @RolesAllowed("user")
    @POST
    public Response create(@Valid final GroupRSDTO groupRsDto) {
        GroupRSDTO group = groupService.createCategory(getUsername(), groupRsDto);
        if (group == null) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
        return Response.ok(group).build();
    }

    @RolesAllowed("user")
    @GET
    @Path("/{id:[0-9]+}")
    public Response findById(@PathParam("id") final Long categoryId) {
        GroupRSDTO group = groupService.getGroup(getUsername(), isAdmin(), categoryId);
        if (group == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(group).build();
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
}
