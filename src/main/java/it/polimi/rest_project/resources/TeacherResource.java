package it.polimi.rest_project.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.PersonalData;
import it.polimi.rest_project.services.TeacherService;

@Path("teachers/{id}")
public class TeacherResource {

	private TeacherService teacherService;

	public TeacherResource() {
		teacherService = new TeacherService();
	}

	@GET
	public PersonalData getPersonalData(@PathParam("id") String teacherId) {
		return teacherService.getPersonalData(teacherId);
	}

	@PUT
	public Response updatePersonalData(@PathParam("id") String teacherId, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") String year, @FormParam("month") String month,
			@FormParam("day") String day) {

		try {
			PersonalData newPersonalData = new PersonalData(name, surname,
					new SimpleDateFormat().parse(year + "-" + month + "-" + day));
			teacherService.updatePersonalData(teacherId, newPersonalData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("classrooms")
	public List<Classroom> getClassrooms(@PathParam("id") String teacherId) {
		return teacherService.getClassrooms(teacherId);
	}

	@GET
	@Path("classrooms/{classroomId}")
	public Classroom getClassroom(@PathParam("classroomId") String classroomId, @PathParam("id") String teacherId) {
		return teacherService.getClassroom(classroomId);
	}

	@POST
	@Path("students/{studentId}/grades")
	public Response addStudentGrade(@PathParam("id") String teacherId, @PathParam("studentId") String studentId,
			@FormParam("subject") String subject, @FormParam("grade") float grade) {
		teacherService.addGrade(teacherId, studentId, subject, grade);
		return Response.ok().build();
	}

	@PUT
	@Path("students/{studentId}/grades/{gradeId}")
	public Response modifyStudentGrade(@PathParam("id") String teacherId, @PathParam("studentId") String studentId,
			@PathParam("gradeId") String gradeId, @FormParam("grade") float newGrade) {
		teacherService.modifyGrade(teacherId, studentId, gradeId, newGrade);
		return Response.ok().build();
	}

	@GET
	@Path("appointments")
	public List<Appointment> getAppointments(@PathParam("id") String teacherId) {
		return teacherService.getTeacherAppointments(teacherId);
	}

	@PUT
	@Path("appointments/{appointmentId}")
	public Response updateAppointment(@PathParam("id") String userId, @PathParam("appointmentId") String appointmentId,
			@FormParam("year") String year, @FormParam("month") String month, @FormParam("day") String day) {
		try {
			teacherService.updateTeacherAppointment(userId, appointmentId,
					new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@POST
	@Path("appointments")
	public Response addAppointment(@PathParam("id") String userId, @FormParam("year") String year,
			@FormParam("month") String month, @FormParam("day") String day, @FormParam("parentId") String parentId) {

		try {
			teacherService.addTeacherAppointment(userId, parentId,
					new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@DELETE
	@Path("appointments/{appointmentId}")
	public Response deleteAppointment(@PathParam("id") String userId,
			@PathParam("appointmentId") String appointmentId) {
		teacherService.deleteTeacherAppointment(userId, appointmentId);
		return Response.ok().build();
	}

}
