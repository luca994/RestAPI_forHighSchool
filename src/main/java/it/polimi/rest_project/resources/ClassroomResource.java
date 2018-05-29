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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.internal.util.Base64;

import it.polimi.rest_project.application.Back2School;
import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.services.ClassroomService;

@Path("classrooms")
public class ClassroomResource {

	private ClassroomService classroomService;

	public ClassroomResource() {
		classroomService = new ClassroomService();
	}

	@GET
	public List<Classroom> getClassrooms(@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return classroomService.getClassrooms(userId);
	}

	@GET
	@Path("{classroomId}")
	public Classroom getClassroom(@PathParam("classroomId") String classroomId,
			@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return classroomService.getClassroom(userId, classroomId);
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
	@Path("{classroomId}")
	public Response addInfoToClassroom(@PathParam("classroomId") String classroomId,
			@Context ContainerRequestContext requestContext, @FormParam("studentId") String studentId,
			@FormParam("lectureId") String lectureId) {
		String userId = getUserId(requestContext);
		if (studentId != null)
			return classroomService.addStudentToClass(userId, classroomId, studentId);
		if (lectureId != null)
			return classroomService.addLectureToClass(userId, classroomId, studentId);
		return Response.status(Status.BAD_REQUEST).build();
	}

	@POST
	public Response createClassroom(@Context UriInfo uriInfo, @FormParam("classroomId") String classroomId,
			@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return classroomService.createClassroom(userId, classroomId, uriInfo.getBaseUri().toString());
	}

	@DELETE
	@Path("{classroomId}")
	public Response deleteClassroom(@PathParam("classroomId") String classroomId,
			@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return classroomService.deleteClassroom(userId, classroomId);
	}
	
	@Path("{classroomId}/lectures")
	public GradeResource getGradeResource() {
		return new GradeResource();
	}

}
