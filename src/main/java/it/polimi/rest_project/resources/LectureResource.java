package it.polimi.rest_project.resources;

import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.internal.util.Base64;

import it.polimi.rest_project.entities.Lecture;
import it.polimi.rest_project.services.Back2School;
import it.polimi.rest_project.services.LectureService;

public class LectureResource {

	private LectureService lectureService;

	public LectureResource() {
		lectureService = new LectureService();
	}

	@GET
	@Path("{lectureId}")
	public Lecture getLecture(@PathParam("lectureId") String lectureId,
			@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return lectureService.getLecture(userId, lectureId);
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

	@POST
	public Response addLecture(@Context UriInfo uriInfo, @Context ContainerRequestContext requestContext,
			@FormParam("classroomId") String classroomId, @FormParam("day") String day, @FormParam("hour") String hour,
			@FormParam("teacherId") String teacherId, @FormParam("subject") String subject) {
		String userId = getUserId(requestContext);
		return lectureService.createLecture(userId, classroomId, teacherId, day, hour, subject,
				uriInfo.getBaseUri().toString());
	}

}
