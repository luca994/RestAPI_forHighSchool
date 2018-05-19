package it.polimi.rest_project.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.PersonalData;
import it.polimi.rest_project.entities.Student;
import it.polimi.rest_project.entities.Teacher;
import it.polimi.rest_project.services.TeacherService;

@Path("teachers/{id}")
public class TeacherResource {

	private TeacherService teacherService;

	public TeacherResource() {
		teacherService = new TeacherService();
	}

	@GET
	public PersonalData getPersonalData(@PathParam("id") String userId) {
		return teacherService.getPersonalData(userId);
	}

	@PUT
	public Response updatePersonalData(@PathParam("id") String userId, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") String year, @FormParam("month") String month,
			@FormParam("day") String day) {

		try {
			PersonalData newPersonalData = new PersonalData(name, surname,
					new SimpleDateFormat().parse(year + "-" + month + "-" + day));
			teacherService.updatePersonalData(userId, newPersonalData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("classrooms")
	public List<Classroom> getClassrooms(@PathParam("id") String id) {
	}

	@GET
	@Path("classrooms/{classroomId}")
	public Classroom getClassroom(@PathParam("classroomId") String classroomId, @PathParam("id") String id) {
		Teacher targetTeacher = getTeacherById(id);
		for (Classroom c : targetTeacher.getClassSubject().keySet()) {
			if (c.getClassroomId().equals(classroomId))
				return c;
		}
		return null;
	}

	@POST
	@Path("classrooms/{classroomId}/students/{studentId}/grades/{subject}")
	public ResponseBuilder addStudentGrade(@PathParam("classroomId") String classroomId,
			@PathParam("studentId") String studentId, @PathParam("subject") String subject,
			@FormParam("grade") float grade, @PathParam("id") String id) {

		Teacher targetTeacher = getTeacherById(id);
		Classroom targetClassroom = null;
		Student targetStudent = null;
		for (Classroom c : targetTeacher.getClassSubject().keySet()) {
			if (c.getClassroomId().equals(classroomId)) {
				targetClassroom = c;
				break;
			}
		}
		for (Student s : targetClassroom.getStudents()) {
			if (s.getUserId().equals(studentId)) {
				targetStudent = s;
				break;
			}
		}
		if (targetTeacher == null || targetClassroom == null || targetStudent == null || grade < 0 || grade > 10)
			return Response.status(Response.Status.BAD_REQUEST);
		targetStudent.getGrades().put(subject, grade);
		return Response.status(Response.Status.ACCEPTED);
	}

	@PUT
	@Path("classrooms/{classroomId}/students/{studentId}/grades/{subject}")
	public ResponseBuilder modifyStudentGrade(@PathParam("classroomId") String classroomId,
			@PathParam("studentId") String studentId, @PathParam("subject") String subject,
			@FormParam("grade") float grade, @PathParam("id") String id) {

		Teacher targetTeacher = getTeacherById(id);
		Classroom targetClassroom = null;
		Student targetStudent = null;
		for (Classroom c : targetTeacher.getClassSubject().keySet()) {
			if (c.getClassroomId().equals(classroomId)) {
				targetClassroom = c;
				break;
			}
		}
		for (Student s : targetClassroom.getStudents()) {
			if (s.getUserId().equals(studentId)) {
				targetStudent = s;
				break;
			}
		}
		if (targetTeacher == null || targetClassroom == null || targetStudent == null || grade < 0 || grade > 10)
			return Response.status(Response.Status.BAD_REQUEST);
		targetStudent.getGrades().put(subject, grade);
		return Response.status(Response.Status.ACCEPTED);
	}

	@GET
	@Path("appointments")
	public List<Appointment> getAppointments(@PathParam("id") String id) {
		Teacher targetTeacher = getTeacherById(id);
		if (targetTeacher != null)
			return targetTeacher.getAppointments();
		return null;
	}

	@POST // controllare se l'orario è libero (vedere classi Parent o Teacher)
	@Path("appointments")
	public ResponseBuilder addAppointment(@PathParam("id") String id, @FormParam("day") String day,
			@FormParam("month") String month, @FormParam("year") String year, @FormParam("parentId") String parentId) {
		Teacher targetTeacher = getTeacherById(id);
		Parent targetParent = null;
		for (Parent p : parents) {
			if (p.getUserId().equals(parentId)) {
				targetParent = p;
				break;
			}
		}
		Appointment appToAdd = new Appointment();
		appToAdd.setParent(targetParent);
		appToAdd.setTeacher(targetTeacher);
		try {
			appToAdd.setDate(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return Response.status(Response.Status.BAD_REQUEST);
		}
		if (targetTeacher == null || targetParent == null)
			return Response.status(Response.Status.BAD_REQUEST);
		targetTeacher.getAppointments().add(appToAdd);
		targetParent.getAppointments().add(appToAdd);
		return Response.status(Response.Status.ACCEPTED);
	}

	@DELETE
	@Path("appointments/{appointmentId}")
	public ResponseBuilder deleteAppointment(@PathParam("id") String id,
			@PathParam("appointmentId") String appointmentId) {
		Teacher targetTeacher = getTeacherById(id);
		Appointment targetAppointment = null;
		for (Appointment a : targetTeacher.getAppointments()) {
			if (a.getAppointmentId().equals(appointmentId)) {
				targetAppointment = a;
				break;
			}
		}
		if (targetAppointment == null)
			return Response.status(Response.Status.BAD_REQUEST);
		targetTeacher.getAppointments().remove(targetAppointment);
		return Response.status(Response.Status.ACCEPTED);
	}

}
