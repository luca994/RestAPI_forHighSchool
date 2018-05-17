package services;

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

import resources.Appointment;
import resources.Classroom;
import resources.Parent;
import resources.PersonalData;
import resources.Student;
import resources.Teacher;

@Path("teachers/{id}")
public class TeacherServices {
	List<Teacher> teachers; // vedere se metterli statici o tramite database
	List<Parent> parents; // vedere se metterli statici o tramite database

	private Teacher getTeacherById(String tId) {
		for (Teacher t : teachers) {
			if (t.getUserId().equals(tId)) {
				return t;
			}
		}
		return null;
	}

	@GET
	public PersonalData getPersonalData(@PathParam("id") String id) {
		Teacher targetTeacher = getTeacherById(id);
		if (targetTeacher != null)
			return targetTeacher.getPersonalData();
		return null;
	}

	@PUT
	public Response setPersonalData(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") String year, @FormParam("month") String month,
			@FormParam("day") String day) {
		Teacher targetTeacher = getTeacherById(id);
		if (name != null)
			targetTeacher.getPersonalData().setName(name);
		if (surname != null)
			targetTeacher.getPersonalData().setSurname(surname);
		try {
			targetTeacher.getPersonalData()
					.setDateOfBirth(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build(); //da modificare
	}

	@GET
	@Path("classrooms")
	public List<Classroom> getClassrooms(@PathParam("id") String id) {
		Teacher targetTeacher = getTeacherById(id);
		if (targetTeacher != null)
			return new ArrayList<Classroom>(targetTeacher.getClassSubject().keySet());
		return null;
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
	public Response addStudentGrade(@PathParam("classroomId") String classroomId,
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
		targetStudent.getGrades().put(subject, grade);
		return Response.ok().build(); //da modificare
	}

	@PUT
	@Path("classrooms/{classroomId}/students/{studentId}/grades/{subject}")
	public Response modifyStudentGrade(@PathParam("classroomId") String classroomId,
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
		targetStudent.getGrades().put(subject, grade);
		return Response.ok().build(); //da modificare
	}

	@GET
	@Path("appointments")
	public List<Appointment> getAppointments(@PathParam("id") String id) {
		Teacher targetTeacher = getTeacherById(id);
		if (targetTeacher != null)
			return targetTeacher.getAppointments();
		return null;
	}

	@POST
	@Path("appointments")
	public Response addAppointment(@PathParam("id") String id, @FormParam("day") String day,
			@FormParam("month") String month, @FormParam("year") String year, @FormParam("parentId") String parentId) {
		Teacher targetTeacher = getTeacherById(id);
		Parent targetParent = null;
		for(Parent p : parents) {
			if(p.getUserId().equals(parentId)) {
				targetParent=p;
				break;
			}
		}
		Appointment appToAdd = new Appointment();
		appToAdd.setParent(targetParent);
		appToAdd.setTeacher(targetTeacher);
		try {
			appToAdd.setDate(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		targetTeacher.getAppointments().add(appToAdd);
		targetParent.getAppointments().add(appToAdd);
		return Response.ok().build(); //da modificare
	}
	
	@DELETE
	@Path("appointments/{appointmentId}")
	public ResponseBuilder deleteAppointment(@PathParam("id") String id, @PathParam("appointmentId") String appointmentId) {
		Teacher targetTeacher = getTeacherById(id);
		for(Appointment a : targetTeacher.getAppointments()) {
			if(a.getAppointmentId().equals(appointmentId)) {
				targetTeacher.getAppointments().remove(a);
				return Response.status(Response.Status.OK);
			}
		}
		return Response.status(Response.Status.BAD_REQUEST);
	}

}
