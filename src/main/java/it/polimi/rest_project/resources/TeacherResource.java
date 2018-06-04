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
import it.polimi.rest_project.entities.Teacher;
import it.polimi.rest_project.services.TeacherService;

@Path("teachers")
public class TeacherResource {

	/** The teacher service used by the resource */
	private TeacherService teacherService;

	/**
	 * Default constructor that initializes the service used by the resource
	 */
	public TeacherResource() {
		teacherService = new TeacherService();
	}

	/**
	 * Returns the list of teachers that can be viewed by the user
	 */
	@GET
	public List<Teacher> getTeachers(@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return teacherService.getTeachers(userId);
	}

	/**
	 * Returns the teacher if it can be viewed by the user
	 */
	@GET
	@Path("{teacherId}")
	public Teacher getParent(@PathParam("teacherId") String teacherId,
			@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return teacherService.getTeacher(userId, teacherId);
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
	@Path("{teacherId}")
	public Response updateTeacher(@PathParam("teacherId") String teacherId,
			@Context ContainerRequestContext requestContext, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") Integer year, @FormParam("month") Integer month,
			@FormParam("day") Integer day) {
		String userId = getUserId(requestContext);
		return teacherService.updateData(userId, teacherId, name, surname, year, month, day);
	}

	/**
	 * Creates a resource with the parameters taken as input if the user who made the request is allowed to do it
	 */
	@POST
	public Response createTeacher(@Context UriInfo uriInfo, @Context ContainerRequestContext requestContext,
			@FormParam("name") String name, @FormParam("surname") String surname, @FormParam("year") Integer year,
			@FormParam("month") Integer month, @FormParam("day") Integer day, @FormParam("password") String password) {
		String userId = getUserId(requestContext);
		return teacherService.createTeacher(userId, name, surname, year, month, day, password,
				uriInfo.getBaseUri().toString());
	}
}
