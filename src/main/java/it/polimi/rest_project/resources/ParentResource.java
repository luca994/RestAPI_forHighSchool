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

import it.polimi.rest_project.application.Back2School;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.services.ParentService;

@Path("parents")
public class ParentResource {

	/** The parent service used by the resource */
	private ParentService parentService;

	/**
	 * Default constructor that initializes the service used by the resource
	 */
	public ParentResource() {
		parentService = new ParentService();
	}

	/**
	 * Returns the list of parents that can be viewed by the user
	 */
	@GET
	public List<Parent> getParents(@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return parentService.getParents(userId);
	}

	/**
	 * Returns the parent if it can be viewed by the user
	 */
	@GET
	@Path("{parentId}")
	public Parent getParent(@PathParam("parentId") String parentId, @Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return parentService.getParent(userId, parentId);
	}

	/**
	 * Gets the userId of the user who made the request
	 * 
	 * @param requestContext
	 *            the request context of this call
	 * @return the id of the user who made the request
	 */
	private String getUserId(ContainerRequestContext requestContext) {
		List<String> headers = requestContext.getHeaders().get(Back2School.AUTHORIZATION_HEADER_KEY);
		String auth = headers.get(0);
		auth = auth.replaceFirst(Back2School.AUTHORIZATION_HEADER_PREFIX, "");
		auth = Base64.decodeAsString(auth);
		StringTokenizer tokenizer = new StringTokenizer(auth, ":");
		String userId = tokenizer.nextToken();
		return userId;
	}

	/**
	 * Updates the resource with the parameters taken as input
	 */
	@PUT
	@Path("{parentId}")
	public Response updateParent(@PathParam("parentId") String parentId,
			@Context ContainerRequestContext requestContext, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("studentId") String studentId,
			@FormParam("year") Integer year, @FormParam("month") Integer month, @FormParam("day") Integer day) {
		String userId = getUserId(requestContext);
		return parentService.updateData(userId, parentId, name, surname, studentId, year, month, day);
	}

	/**
	 * Creates a resource with the parameters taken as input if the user who made the request is allowed to do it
	 */
	@POST
	public Response createParent(@Context UriInfo uriInfo, @Context ContainerRequestContext requestContext,
			@FormParam("name") String name, @FormParam("surname") String surname, @FormParam("year") Integer year,
			@FormParam("month") Integer month, @FormParam("day") Integer day, @FormParam("password") String password) {
		String userId = getUserId(requestContext);
		return parentService.createParent(userId, name, surname, year, month, day, password,
				uriInfo.getBaseUri().toString());
	}

}
