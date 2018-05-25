package it.polimi.rest_project.resources;

import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.internal.util.Base64;

import it.polimi.rest_project.entities.Student;
import it.polimi.rest_project.services.Back2School;
import it.polimi.rest_project.services.StudentService;

@Path("students")
public class StudentResource {

	private StudentService studentService;

	public StudentResource() {
		studentService = new StudentService();
	}

	@GET
	public List<Student> getStudents(@Context ContainerRequestContext requestContext,@QueryParam("classroom") String classId) {
		String userId = getUserId(requestContext);
		if(classId!=null)
			return studentService.getStudentsInClassroom(userId,classId);
		return studentService.getStudents(userId);
		
	}

	@GET
	@Path("{studentId}")
	public Student getStudent(@PathParam("studentId") String studentId,
			@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return studentService.getStudent(userId, studentId);
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
	@Path("{studentId}")
	public Response updateStudent(@PathParam("studentId") String studentId,
			@Context ContainerRequestContext requestContext, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") String year, @FormParam("month") String month,
			@FormParam("day") String day) {
		String userId = getUserId(requestContext);
		return studentService.updateData(userId, studentId, name, surname, year, month, day);
	}

	@POST
	public Response createStudent(@Context UriInfo uriInfo, @Context ContainerRequestContext requestContext,
			@FormParam("name") String name, @FormParam("surname") String surname, @FormParam("year") String year,
			@FormParam("month") String month, @FormParam("day") String day, @FormParam("password") String password) {
		String userId = getUserId(requestContext);
		return studentService.createStudent(userId, name, surname, year, month, day,password, uriInfo.getBaseUri().toString());
	}

}
