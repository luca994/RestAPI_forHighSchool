package it.polimi.rest_project.resources;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import it.polimi.rest_project.entities.Administrator;
import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Student;
import it.polimi.rest_project.entities.Teacher;
import it.polimi.rest_project.services.AdministratorService;

@Path("admins/{id}")
public class AdministratorResource {

	@Context
	private UriInfo uriInfo;
	private AdministratorService administratorService;

	public AdministratorResource() {
		administratorService = new AdministratorService();
	}

	@GET
	public Administrator getAdmin(@PathParam("id") String userId) {
		return administratorService.getAdmin(userId);
	}

	@PUT
	public Response updatePersonalData(@PathParam("id") String parentId, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") String year, @FormParam("month") String month,
			@FormParam("day") String day) {

		if (administratorService.updateUserData(parentId, name, surname, day, month, year))
			return Response.status(Response.Status.ACCEPTED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();

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

	@PUT
	@Path("classrooms/{classroomId}")
	public Response addStudentToClass(@PathParam("id") String id, @PathParam("classroomId") String classroomId,
			@FormParam("studentId") String studentId) {
		if (administratorService.addStudentToClass(classroomId, studentId))
			return Response.status(Response.Status.ACCEPTED).build();
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Path("students")
	public List<Student> getStudents(@PathParam("id") String id) {
		return administratorService.getAdminStudents();
	}

	@GET
	@Path("students/{studentId}")
	public Student getStudent(@PathParam("studentId") String studentId) {
		return administratorService.getStudent(studentId);
	}

	@PUT
	@Path("students/{studentId}")
	public Response updateStudentData(@PathParam("id") String userId, @PathParam("studentId") String studentId,
			@FormParam("name") String name, @FormParam("surname") String surname, @FormParam("year") String year,
			@FormParam("month") String month, @FormParam("day") String day, @FormParam("parentId") String parentId) {
		if (parentId != null)
			administratorService.addStudentToParent(parentId, studentId, uriInfo);
		if (administratorService.updateUserData(studentId, name, surname, day, month, year))
			return Response.status(Response.Status.ACCEPTED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@POST
	@Path("students")
	public Response createStudent(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year) {
		if (administratorService.createStudent(name, surname, day, month, year))
			return Response.status(Response.Status.ACCEPTED).build();
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Path("parents")
	public List<Parent> getParents(@PathParam("id") String id) {
		return administratorService.getAdminParents();
	}

	@POST
	@Path("parents")
	public Response createParent(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year) {
		if (administratorService.createParent(name, surname, day, month, year))
			return Response.status(Response.Status.ACCEPTED).build();
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@PUT
	@Path("parents/{parentId}")
	public Response updateParentData(@PathParam("id") String userId, @FormParam("parentId") String parentId,
			@FormParam("name") String name, @FormParam("surname") String surname, @FormParam("year") String year,
			@FormParam("month") String month, @FormParam("day") String day) {
		if (administratorService.updateUserData(parentId, name, surname, day, month, year))
			return Response.status(Response.Status.ACCEPTED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Path("administrators")
	public List<Administrator> getAdministrators(@PathParam("id") String id) {
		return administratorService.getAdminAdministrators();
	}

	@POST
	@Path("administrators")
	public Response createAdmin(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year) {
		if (administratorService.createAdministrator(name, surname, day, month, year, uriInfo.getBaseUri().toString()))
			return Response.status(Response.Status.ACCEPTED).build();
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Path("teachers")
	public List<Teacher> getTeachers(@PathParam("id") String id) {
		return administratorService.getAdminTeachers();
	}

	@POST
	@Path("teachers")
	public Response createTeacher(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year) {
		if (administratorService.createTeacher(name, surname, day, month, year))
			return Response.status(Response.Status.ACCEPTED).build();
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@POST
	@Path("payments")
	public Response createPaymentForParent(@PathParam("id") String id, @FormParam("parentId") String parentId,
			@FormParam("amount") String amount, @FormParam("reason") String reason, @FormParam("day") String day,
			@FormParam("month") String month, @FormParam("year") String year) {
		if (administratorService.createPayment(parentId, amount, reason, day, month, year, uriInfo.getBaseUri().toString()))
			return Response.status(Response.Status.ACCEPTED).build();
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@POST
	@Path("notifications")
	public Response createNotification(@PathParam("id") String id, @FormParam("userId") String userId,
			@FormParam("text") String text) {
		if (administratorService.createNotification(userId, text, uriInfo.getBaseUri().toString()))
			return Response.status(Response.Status.ACCEPTED).build();
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
}
