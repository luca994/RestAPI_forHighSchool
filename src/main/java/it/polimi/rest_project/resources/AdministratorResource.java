package it.polimi.rest_project.resources;

import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.internal.util.Base64;

import it.polimi.rest_project.entities.Administrator;
import it.polimi.rest_project.services.AdministratorService;
import it.polimi.rest_project.services.Back2School;

@Path("admins")
public class AdministratorResource {

	private AdministratorService administratorService;

	public AdministratorResource() {
		administratorService = new AdministratorService();
	}

	@GET
	public List<Administrator> getAdministrators(@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return administratorService.getAdministrators(userId);
	}

	@GET
	@Path("{adminId}")
	public Administrator getAdministrator(@PathParam("adminId") String adminId,
			@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return administratorService.getAdministrator(userId, adminId);
	}

	private String getUserId(ContainerRequestContext requestContext) {
		List<String> headers = requestContext.getHeaders().get(Back2School.AUTHORIZATION_HEADER_KEY);
		String auth = headers.get(0);
		auth = auth.replaceFirst(Back2School.AUTHORIZATION_HEADER_PREFIX, "");
		auth = Base64.decodeAsString(auth);
		StringTokenizer tokenizer = new StringTokenizer(auth, ":");
		String userId = tokenizer.nextToken();
		return userId;
	}

	@PUT
	@Path("{adminId}")
	public Response updateAdministrator(@PathParam("adminId") String adminId,
			@Context ContainerRequestContext requestContext, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") String year, @FormParam("month") String month,
			@FormParam("day") String day) {
		String userId = getUserId(requestContext);
		return administratorService.updateData(userId, adminId, name, surname, year, month, day);
	}

	@POST
	public Response createAdministrator(@Context UriInfo uriInfo, @Context ContainerRequestContext requestContext,
			@FormParam("name") String name, @FormParam("surname") String surname, @FormParam("year") String year,
			@FormParam("month") String month, @FormParam("day") String day,@FormParam("password") String password) {
		String userId = getUserId(requestContext);
		return administratorService.createAdministrator(userId, name, surname, year, month, day,password,
				uriInfo.getBaseUri().toString());
	}
}
