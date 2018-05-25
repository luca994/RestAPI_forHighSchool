package it.polimi.rest_project.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Teacher;

public class TeacherService extends UserService {

	public TeacherService() {
	}

	public Teacher getTeacher(String userId, String teacherId) {
		if ((userId == teacherId || isAdministrator(userId)) && isTeacher(teacherId))
			return entityManager.find(Teacher.class, teacherId);
		return null;
	}

	public Response updateData(String userId, String teacherId, String name, String surname, String day, String month,
			String year) {
		if ((userId == teacherId || isAdministrator(userId)) && isTeacher(teacherId))
			return updateTeacherData(teacherId, name, surname, day, month, year);
		else
			return Response.status(Status.UNAUTHORIZED).build();
	}

	public Response updateTeacherData(String id, String name, String surname, String day, String month, String year) {
		Teacher targetTeacher = entityManager.find(Teacher.class, id);
		if (name != null)
			targetTeacher.setName(name);
		if (surname != null)
			targetTeacher.setSurname(surname);
		if (day != null && month != null && year != null)
			try {
				targetTeacher.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
			} catch (ParseException e) {
				return Response.status(Status.NOT_MODIFIED).build();
			}
		entityManager.getTransaction().begin();
		entityManager.persist(targetTeacher);
		entityManager.getTransaction().commit();
		return Response.status(Status.OK).entity(targetTeacher).build();
	}

	public Response createTeacher(String userId, String name, String surname, String year, String month, String day,String password,
			String baseUri) {
		if (isAdministrator(userId)) {
			Teacher newTeacher = new Teacher();
			if (name == null || surname == null || day == null || month == null || year == null || password==null)
				return Response.status(Status.BAD_REQUEST).build();
			newTeacher.setName(name);
			newTeacher.setSurname(surname);
			newTeacher.setPassword(password);
			addResources(newTeacher, baseUri);
			try {
				newTeacher.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
			} catch (ParseException e) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			entityManager.getTransaction().begin();
			entityManager.persist(newTeacher);
			entityManager.getTransaction().commit();
			return Response.status(Status.CREATED).entity(newTeacher).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	private void addResources(Teacher teacher, String baseUri) {
		Link self = new Link(baseUri + "teachers" + "/" + teacher.getUserId(), "self");
		Link classrooms = new Link(baseUri + "classrooms", "classrooms");
		Link grades = new Link(baseUri + "grades", "grades");
		Link appointments = new Link(baseUri + "appointments", "appointments");
		teacher.getResources().add(self);
		teacher.getResources().add(classrooms);
		teacher.getResources().add(grades);
		teacher.getResources().add(appointments);
		entityManager.getTransaction().begin();
		entityManager.persist(self);
		entityManager.persist(classrooms);
		entityManager.persist(grades);
		entityManager.persist(appointments);
		entityManager.getTransaction().commit();
	}

	public List<Teacher> getTeachers(String userId) {
		if (isAdministrator(userId))
			return entityManager.createQuery("Select t from Teacher t").getResultList();
		return null;
	}

}
