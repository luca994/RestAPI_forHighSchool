package it.polimi.rest_project.services;

import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Teacher;

public class TeacherService extends UserService {

	public TeacherService() {
	}

	public Teacher getTeacher(String userId, String teacherId) {
		if ((userId.equals(teacherId) || isAdministrator(userId)) && isTeacher(teacherId))
			return entityManager.find(Teacher.class, teacherId);
		return null;
	}

	public Response updateData(String userId, String teacherId, String name, String surname, Integer day, Integer month,
			Integer year) {
		if ((userId.equals(teacherId) || isAdministrator(userId)) && isTeacher(teacherId))
			return updateTeacherData(teacherId, name, surname, day, month, year);
		else
			return Response.status(Status.UNAUTHORIZED).entity("You must be logged as "+teacherId+" or as an administrator to update his data").build();
	}

	public Response updateTeacherData(String id, String name, String surname, Integer day, Integer month,
			Integer year) {
		Teacher targetTeacher = entityManager.find(Teacher.class, id);
		if (name != null)
			targetTeacher.setName(name);
		if (surname != null)
			targetTeacher.setSurname(surname);
		if (day != null && month != null && year != null)
			targetTeacher.setDateOfBirth(new GregorianCalendar(year, month - 1, day));
		entityManager.getTransaction().begin();
		entityManager.persist(targetTeacher);
		entityManager.getTransaction().commit();
		return Response.status(Status.OK).entity(targetTeacher).build();
	}

	public Response createTeacher(String userId, String name, String surname, Integer year, Integer month, Integer day,
			String password, String baseUri) {
		if (isAdministrator(userId)) {
			if (name == null || surname == null || day == null || month == null || year == null || password == null)
				return Response.status(Status.BAD_REQUEST).entity("Some parameters are missing, you must insert all the parameters").build();
			Teacher newTeacher = new Teacher(name, surname, password, new GregorianCalendar(year, month - 1, day));
			addResources(newTeacher, baseUri);
			entityManager.getTransaction().begin();
			entityManager.persist(newTeacher);
			entityManager.getTransaction().commit();
			return Response.created(newTeacher.getResources().get(0).getHref()).entity(newTeacher).build();
		}
		return Response.status(Status.UNAUTHORIZED).entity("You must be logged in as administrator to create a teacher").build();
	}

	/**
	 * Adds the accessible resources to the entity
	 */
	private void addResources(Teacher teacher, String baseUri) {
		Link self = new Link(baseUri + "teachers" + "/" + teacher.getUserId(), "self");
		Link classrooms = new Link(baseUri + "classrooms", "classrooms");
		Link appointments = new Link(baseUri + "appointments", "appointments");
		teacher.getResources().add(self);
		teacher.getResources().add(classrooms);
		teacher.getResources().add(appointments);
	}

	public List<Teacher> getTeachers(String userId) {
		if (isAdministrator(userId))
			return entityManager.createQuery("Select t from Teacher t").getResultList();
		return null;
	}

}
