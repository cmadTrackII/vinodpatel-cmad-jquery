package com.cisco.cmad.rogastis.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cisco.cmad.rogastis.api.Rogastis;
import com.cisco.cmad.rogastis.api.User;
import com.cisco.cmad.rogastis.service.RogastisService;

@Path("/user")
public class UserResource {

	private Rogastis rogastis;

	public UserResource() {
		rogastis = new RogastisService();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response add(User user) {
		rogastis.registerUser(user);
		return Response.ok().build();
	}

	@Path("/{loginId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response get(@PathParam("loginId") String loginId) {
		User user = rogastis.getUser(loginId);
		return Response.ok().entity(user).header("Access-Control-Allow-Origin", "*").build();
	}
}
