package it.polimi.rest_project.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.Grade;
import it.polimi.rest_project.entities.Teacher;
import it.polimi.rest_project.services.TeacherService;

@Path("teachers/{id}")
public class TeacherResource {

	@Context
	private UriInfo uriInfo;
	private TeacherService teacherService;

	public TeacherResource() {
		teacherService = new TeacherService();
	}

	@GET
	public Teacher getPersonalData(@PathParam("id") String userId) {
		return teacherService.getTeacher(userId);
	}

	@PUT
	public Response updatePersonalData(@PathParam("id") String teacherId, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") String year, @FormParam("month") String month,
			@FormParam("day") String day) {

		if (teacherService.updateUserData(teacherId, name, surname, day, month, year))
			return Response.status(Response.Status.ACCEPTED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();

	}

	@GET
	@Path("classrooms/{classroomId}")
	public Classroom getClassroom(@PathParam("classroomId") String classroomId, @PathParam("id") String teacherId) {
		return teacherService.getClassroom(classroomId);
	}

	@GET
	@Path("students/{studentId}/grades/{gradeId}")
	public Grade getGrade(@PathParam("id") String teacherId, @PathParam("gradeId") String gradeId) {
		return teacherService.getGrade(teacherId, gradeId);
	}

	@POST
	@Path("students/{studentId}/grades")
	public Response addStudentGrade(@PathParam("id") String teacherId, @PathParam("studentId") String studentId,
			@FormParam("subject") String subject, @FormParam("grade") float grade) {
		teacherService.addGrade(teacherId, studentId, subject, grade);
		return Response.status(Response.Status.ACCEPTED).build();
	}

	@PUT
	@Path("students/{studentId}/grades/{gradeId}")
	public Response modifyStudentGrade(@PathParam("id") String teacherId, @PathParam("studentId") String studentId,
			@PathParam("gradeId") String gradeId, @FormParam("grade") float newGrade) {
		teacherService.modifyGrade(teacherId, studentId, gradeId, newGrade);
		return Response.status(Response.Status.ACCEPTED).build();
	}

	@GET
	@Path("appointments/{appointmentId}")
	public Appointment getAppointment(@PathParam("id") String teacherId,
			@PathParam("appointmentId") String appointmentId) {
		return teacherService.getTeacherAppointment(teacherId, appointmentId);
	}

	@PUT
	@Path("appointments/{appointmentId}")
	public Response updateAppointment(@PathParam("id") String userId, @PathParam("appointmentId") String appointmentId,
			@FormParam("year") String year, @FormParam("month") String month, @FormParam("day") String day) {
		if (teacherService.updateTeacherAppointment(userId, appointmentId, day, month, year))
			return Response.status(Response.Status.ACCEPTED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@POST
	@Path("appointments")
	public Response addAppointment(@PathParam("id") String userId, @FormParam("year") String year,
			@FormParam("month") String month, @FormParam("day") String day, @FormParam("parentId") String parentId) {

		if (teacherService.addTeacherAppointment(parentId, userId, day, month, year,uriInfo.getBaseUri().toString()))
			return Response.status(Response.Status.ACCEPTED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@DELETE
	@Path("appointments/{appointmentId}")
	public Response deleteAppointment(@PathParam("id") String userId,
			@PathParam("appointmentId") String appointmentId) {
		if (teacherService.deleteTeacherAppointment(userId, appointmentId))
			return Response.status(Response.Status.ACCEPTED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

}
