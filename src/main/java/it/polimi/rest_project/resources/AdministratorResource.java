package it.polimi.rest_project.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import it.polimi.rest_project.entities.Administrator;
import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.PersonalData;
import it.polimi.rest_project.entities.Student;
import it.polimi.rest_project.entities.Teacher;
import it.polimi.rest_project.services.AdministratorService;

@Path("admins/{id}")
public class AdministratorResource {

	private AdministratorService administratorService;

	@GET
	@Path("classrooms")
	public List<Classroom> getClassrooms(@PathParam("id") String id) {
		Administrator targetAdmin = getAdminById(id);
		if (targetAdmin != null)
			return targetAdmin.getClassrooms();
		return null;
	}

	@GET
	@Path("classrooms/{classroomsId}")
	public Classroom getClassroom(@PathParam("id") String id, @PathParam("classroomId") String classroomId) {
		Administrator targetAdmin = getAdminById(classroomId);
		for (Classroom c : targetAdmin.getClassrooms()) {
			if (c.getClassroomId().equals(classroomId)) {
				return c;
			}
		}
		return null;
	}

	@GET
	@Path("parents")
	public List<Parent> getParents(@PathParam("id") String id) {
		Administrator targetAdmin = getAdminById(id);
		if (targetAdmin != null)
			return targetAdmin.getParents();
		return null;
	}

	@POST
	@Path("students")
	public ResponseBuilder createStudent(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year, @FormParam("parentIds") List<String> parentIds) {
		Administrator admin = getAdminById(id);
		if (admin == null || name == null || surname == null)
			return Response.status(Response.Status.BAD_REQUEST);
		Student studentToAdd = new Student();
		PersonalData pdat = new PersonalData();
		pdat.setName(name);
		pdat.setSurname(surname);
		try {
			pdat.setDateOfBirth(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			Response.status(Response.Status.BAD_REQUEST);
		}
		studentToAdd.setPersonalData(pdat);
		for (Parent p : admin.getParents()) {
			if (parentIds.contains(p.getUserId())) {
				studentToAdd.getParents().add(p);
				p.getChildren().add(studentToAdd);
			}
		}
		// the administrator must add the parents before create the students
		if (studentToAdd.getParents().isEmpty())
			return Response.status(Response.Status.BAD_REQUEST);
		admin.getStudents().add(studentToAdd);
		return Response.status(Response.Status.OK);
	}

	@PUT
	@Path("classrooms/{classroomId}")
	public ResponseBuilder addStudentToClass(@PathParam("id") String id, @PathParam("classroomId") String classroomId,
			@FormParam("studentId") String studentId) {
		Administrator targetAdministrator = getAdminById(id);
		Student studentToAdd = null;
		Classroom targetClassroom = null;
		for (Student s : targetAdministrator.getStudents()) {
			if (s.getUserId().equals(studentId)) {
				studentToAdd = s;
				break;
			}
		}
		for (Classroom c : targetAdministrator.getClassrooms()) {
			if (c.getClassroomId().equals(classroomId)) {
				targetClassroom = c;
				break;
			}
		}
		if (targetAdministrator == null || studentToAdd == null || targetClassroom == null)
			return Response.status(Response.Status.BAD_REQUEST);
		targetClassroom.getStudents().add(studentToAdd);
		return Response.status(Response.Status.OK);
	}

	@POST
	@Path("parents")
	public ResponseBuilder createParent(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year) {
		Administrator admin = getAdminById(id);
		Parent parentToAdd = new Parent();
		PersonalData pdat = new PersonalData();
		if (name == null || surname == null || admin == null)
			return Response.status(Response.Status.BAD_REQUEST);
		pdat.setName(name);
		pdat.setSurname(surname);
		try {
			pdat.setDateOfBirth(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return Response.status(Response.Status.BAD_REQUEST);
		}
		parentToAdd.setPersonalData(pdat);
		admin.getParents().add(parentToAdd);
		return Response.status(Response.Status.OK);
	}

	@POST
	@Path("teachers")
	public ResponseBuilder createTeacher(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year, @FormParam("mapClassSubject") Map<String, String> classIdSubject) {

		Administrator admin = getAdminById(id);
		Teacher teacherToAdd = new Teacher();
		PersonalData pdat = new PersonalData();
		if (name == null || surname == null || admin == null)
			return Response.status(Response.Status.BAD_REQUEST);
		pdat.setName(name);
		pdat.setSurname(surname);
		try {
			pdat.setDateOfBirth(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return Response.status(Response.Status.BAD_REQUEST);
		}
		teacherToAdd.setPersonalData(pdat);
		List<String> classroomsToAdd = new ArrayList<>(classIdSubject.keySet());
		for(Classroom c : admin.getClassrooms()) {
			if(classroomsToAdd.contains(c.getClassroomId())) {
				teacherToAdd.getClassSubject().put(c, classIdSubject.get(c.getClassroomId()));
			}
		}
		return Response.status(Response.Status.ACCEPTED);
	}

	@POST
	@Path("administrators")
	public ResponseBuilder createAdministrator(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("day") String day, @FormParam("month") String month,
			@FormParam("year") String year) {
		
		Administrator admin = getAdminById(id);
		Administrator adminToAdd = new Administrator();
		PersonalData pdat = new PersonalData();
		if (name == null || surname == null || admin == null)
			return Response.status(Response.Status.BAD_REQUEST);
		pdat.setName(name);
		pdat.setSurname(surname);
		try {
			pdat.setDateOfBirth(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return Response.status(Response.Status.BAD_REQUEST);
		}
		adminToAdd.setPersonalData(pdat);
		
		return Response.status(Response.Status.ACCEPTED);
	}
	
}
