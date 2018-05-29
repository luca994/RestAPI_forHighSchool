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

	private GradeService gradeService;

	public GradeResource() {
		gradeService = new GradeService();
	}

	@GET
	public List<Grade> getGrades(@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return gradeService.getGrades(userId);
	}

	@GET
	@Path("{gradeId}")
	public Grade getGrade(@PathParam("gradeId") String gradeId, @Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return gradeService.getGrade(userId, gradeId);
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
	public Response addGrade(@Context UriInfo uriInfo, @Context ContainerRequestContext requestContext,
			@PathParam("studentId") String studentId, @FormParam("subject") String subject,
			@FormParam("mark") String mark) {
		String userId = getUserId(requestContext);
		return gradeService.createGrade(userId, studentId, subject, mark, uriInfo.getAbsolutePath().toString());
	}

	@PUT
	@Path("{gradeId}")
	public Response updateGrade(@PathParam("gradeId") String gradeId, @Context ContainerRequestContext requestContext,
			@FormParam("mark") String mark) {
		String userId = getUserId(requestContext);
		return gradeService.updateGrade(userId, gradeId, mark);
	}

	@DELETE
	@Path("{gradeId}")
	public Response deleteGrade(@PathParam("gradeId") String gradeId, @Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return gradeService.deleteGrade(userId, gradeId);
	}

}
