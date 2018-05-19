package it.polimi.rest_project.resources;

import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.services.AdministratorService;

@Path("admins/{id}")
public class AdministratorResource {

	private AdministratorService administratorService;

	@GET
	@Path("classrooms")
	public List<Classroom> getClassrooms(@PathParam("id") String id) {
		return administratorService.getAdminClassrooms();
	}

	@GET
	@Path("classrooms/{classroomsId}")
	public Classroom getClassroom(@PathParam("id") String id, @PathParam("classroomId") String classroomId) {
		return administratorService.getAdminClassroom(classroomId);
	}

	@GET
	@Path("parents")
	public List<Parent> getParents(@PathParam("id") String id) {
		return administratorService.getAdminParents();
	}

	@POST
	@Path("students")
	public ResponseBuilder createStudent(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year) {
		if (!administratorService.createStudent(name, surname, day, month, year))
			return Response.status(Response.Status.BAD_REQUEST);
		return Response.status(Response.Status.OK);
	}

	@PUT
	@Path("parents/{parentId}")
	public ResponseBuilder addStudentToParent(@PathParam("id") String id, @PathParam("parentId") String parentId,
			@FormParam("studentId") String studentId) {
		if (!administratorService.addStudentToParent(parentId, studentId))
			return Response.status(Response.Status.BAD_REQUEST);
		return Response.status(Response.Status.OK);
	}

	@PUT
	@Path("classrooms/{classroomId}")
	public ResponseBuilder addStudentToClass(@PathParam("id") String id, @PathParam("classroomId") String classroomId,
			@FormParam("studentId") String studentId) {
		if (!administratorService.addStudentToClass(classroomId, studentId))
			return Response.status(Response.Status.BAD_REQUEST);
		return Response.status(Response.Status.OK);
	}

	@POST
	@Path("parents")
	public ResponseBuilder createParent(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year) {
		if (!administratorService.createParent(name, surname, day, month, year))
			return Response.status(Response.Status.BAD_REQUEST);
		return Response.status(Response.Status.OK);
	}

	@POST
	@Path("teachers")
	public ResponseBuilder createTeacher(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year, @FormParam("mapClassSubject") Map<String, String> classIdSubject) {
		if (!administratorService.createTeacher(name, surname, day, month, year))
			return Response.status(Response.Status.BAD_REQUEST);
		return Response.status(Response.Status.OK);
	}

	@POST
	@Path("administrators")
	public ResponseBuilder createAdministrator(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year) {
		if (!administratorService.createAdministrator(name, surname, day, month, year))
			return Response.status(Response.Status.BAD_REQUEST);
		return Response.status(Response.Status.OK);
	}

	@POST
	@Path("parents/{parentId}/payments")
	public ResponseBuilder createPaymentForParent(@PathParam("id") String id, @PathParam("parentId") String parentId,
			@FormParam("amount") String amount, @FormParam("reason") String reason, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year) {
		if(!administratorService.createPaymentForParent(parentId, amount, reason, day, month, year))
			return Response.status(Response.Status.BAD_REQUEST);
		return Response.status(Response.Status.OK);
	}
	
	@POST
	@Path("notifications")
	public ResponseBuilder createNotification(@PathParam("id") String id, @FormParam("userId") String userId, @FormParam("text") String text) {
		if(!administratorService.createNotification(userId, text))
			return Response.status(Response.Status.BAD_REQUEST);
		return Response.status(Response.Status.OK);
	}
}
