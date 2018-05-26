package it.polimi.rest_project.resources;

import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.internal.util.Base64;

import it.polimi.rest_project.entities.Teacher;
import it.polimi.rest_project.services.Back2School;
import it.polimi.rest_project.services.TeacherService;

@Produces(MediaType.APPLICATION_JSON)
@Path("teachers")
public class TeacherResource {

	private TeacherService teacherService;

	public TeacherResource() {
		teacherService = new TeacherService();
	}

	@GET
	public List<Teacher> getTeachers(@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return teacherService.getTeachers(userId);
	}

	@GET
	@Path("{teacherId}")
	public Teacher getParent(@PathParam("teacherId") String teacherId,
			@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return teacherService.getTeacher(userId, teacherId);
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
	@Path("{teacherId}")
	public Response updateTeacher(@PathParam("teacherId") String teacherId,
			@Context ContainerRequestContext requestContext, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") Integer year, @FormParam("month") Integer month,
			@FormParam("day") Integer day) {
		String userId = getUserId(requestContext);
		return teacherService.updateData(userId, teacherId, name, surname, year, month, day);
	}

	@POST
	public Response createTeacher(@Context UriInfo uriInfo, @Context ContainerRequestContext requestContext,
			@FormParam("name") String name, @FormParam("surname") String surname, @FormParam("year") Integer year,
			@FormParam("month") Integer month, @FormParam("day") Integer day, @FormParam("password") String password) {
		String userId = getUserId(requestContext);
		return teacherService.createTeacher(userId, name, surname, year, month, day, password,
				uriInfo.getBaseUri().toString());
	}
}
