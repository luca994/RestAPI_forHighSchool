package it.polimi.rest_project.resources;

import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.DELETE;
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
import it.polimi.rest_project.entities.Grade;
import it.polimi.rest_project.services.GradeService;


public class GradeResource {

	/** The grade service used by the resource */
	private GradeService gradeService;

	/**
	 * Default constructor that initializes the service used by the resource
	 */
	public GradeResource() {
		gradeService = new GradeService();
	}

	/**
	 * Returns the list of grades that can be viewed by the user
	 */
	@GET
	public List<Grade> getGrades(@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return gradeService.getGrades(userId);
	}

	/**
	 * Returns the grade if it can be viewed by the user
	 */
	@GET
	@Path("{gradeId}")
	public Grade getGrade(@PathParam("gradeId") String gradeId, @Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return gradeService.getGrade(userId, gradeId);
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
	 * Creates a resource with the parameters taken as input if the user who made the request is allowed to do it
	 */
	@POST
	public Response addGrade(@Context UriInfo uriInfo, @Context ContainerRequestContext requestContext,
			@PathParam("studentId") String studentId, @FormParam("subject") String subject,
			@FormParam("mark") String mark) {
		String userId = getUserId(requestContext);
		return gradeService.createGrade(userId, studentId, subject, mark, uriInfo.getAbsolutePath().toString());
	}

	/**
	 * Updates the resource with the parameters taken as input
	 */
	@PUT
	@Path("{gradeId}")
	public Response updateGrade(@PathParam("gradeId") String gradeId, @Context ContainerRequestContext requestContext,
			@FormParam("mark") String mark) {
		String userId = getUserId(requestContext);
		return gradeService.updateGrade(userId, gradeId, mark);
	}

	/**
	 * Deletes the resource with the id taken as input, if the user who made the request is allowed to do it
	 */
	@DELETE
	@Path("{gradeId}")
	public Response deleteGrade(@PathParam("gradeId") String gradeId, @Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return gradeService.deleteGrade(userId, gradeId);
	}

}
