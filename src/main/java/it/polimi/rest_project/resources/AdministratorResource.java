package it.polimi.rest_project.resources;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.services.AdministratorService;

@Path("admins/{id}")
public class AdministratorResource {

	private AdministratorService administratorService;

	public AdministratorResource() {
		administratorService = new AdministratorService();
	}

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
	public Response createStudent(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year) {
		if (!administratorService.createStudent(name, surname, day, month, year))
			return Response.notModified().build();
		return Response.ok().build();
	}

	@PUT
	@Path("parents/{parentId}")
	public Response addStudentToParent(@PathParam("id") String id, @PathParam("parentId") String parentId,
			@FormParam("studentId") String studentId) {
		if (!administratorService.addStudentToParent(parentId, studentId))
			return Response.notModified().build();
		return Response.ok().build();
	}

	@PUT
	@Path("classrooms/{classroomId}")
	public Response addStudentToClass(@PathParam("id") String id, @PathParam("classroomId") String classroomId,
			@FormParam("studentId") String studentId) {
		if (!administratorService.addStudentToClass(classroomId, studentId))
			return Response.notModified().build();
		return Response.ok().build();
	}

	@POST
	@Path("parents")
	public Response createParent(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year) {
		if (!administratorService.createParent(name, surname, day, month, year))
			return Response.notModified().build();
		return Response.ok().build();
	}

	/*
	 * @POST
	 * 
	 * @Path("teachers") public Response createTeacher(@PathParam("id") String
	 * id, @FormParam("name") String name,
	 * 
	 * @FormParam("surname") String surname, @FormParam("day") String
	 * day, @FormParam("month") String month,
	 * 
	 * @FormParam("year") String year, @FormParam("mapClassSubject") Map<String,
	 * String> classIdSubject) { if (!administratorService.createTeacher(name,
	 * surname, day, month, year)) return Response.notModified().build(); return
	 * Response.ok().build(); }
	 */

	@POST
	@Path("administrators")
	public Response createAdministrator(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year) {
		if (!administratorService.createAdministrator(name, surname, day, month, year))
			return Response.notModified().build();
		return Response.ok().build();
	}

	@POST
	@Path("parents/{parentId}/payments")
	public Response createPaymentForParent(@PathParam("id") String id, @PathParam("parentId") String parentId,
			@FormParam("amount") String amount, @FormParam("reason") String reason, @FormParam("day") String day,
			@FormParam("month") String month, @FormParam("year") String year) {
		if (!administratorService.createPaymentForParent(parentId, amount, reason, day, month, year))
			return Response.notModified().build();
		return Response.ok().build();
	}

	@POST
	@Path("notifications")
	public Response createNotification(@PathParam("id") String id, @FormParam("userId") String userId,
			@FormParam("text") String text) {
		if (!administratorService.createNotification(userId, text))
			return Response.notModified().build();
		return Response.ok().build();
	}
}
