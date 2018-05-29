package it.polimi.rest_project.services;

import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.entities.Administrator;
import it.polimi.rest_project.entities.Link;

public class AdministratorService extends UserService {

	public AdministratorService() {
		super();
	}

	public Administrator getAdministrator(String userId, String adminId) {
		if (userId.equals(adminId) && isAdministrator(adminId))
			return entityManager.find(Administrator.class, adminId);
		return null;
	}

	public Response updateData(String userId, String teacherId, String name, String surname, Integer day, Integer month,
			Integer year) {
		if (userId.equals(teacherId) && isAdministrator(userId))
			return updateAdministratorData(teacherId, name, surname, day, month, year);
		else
			return Response.status(Status.UNAUTHORIZED).build();
	}

	public Response updateAdministratorData(String id, String name, String surname, Integer day, Integer month,
			Integer year) {
		Administrator targetAdministrator = entityManager.find(Administrator.class, id);
		if (name != null)
			targetAdministrator.setName(name);
		if (surname != null)
			targetAdministrator.setSurname(surname);
		if (day != null && month != null && year != null)
			targetAdministrator.setDateOfBirth(new GregorianCalendar(year, month - 1, day));
		entityManager.getTransaction().begin();
		entityManager.persist(targetAdministrator);
		entityManager.getTransaction().commit();
		return Response.status(Status.OK).entity(targetAdministrator).build();
	}

	public Response createAdministrator(String userId, String name, String surname, Integer year, Integer month,
			Integer day, String password, String baseUri) {
		if (isAdministrator(userId)) {
			if (name == null || surname == null || day == null || month == null || year == null || password == null)
				return Response.status(Status.BAD_REQUEST).build();
			Administrator newAdministrator = new Administrator(name,surname,password,new GregorianCalendar(year, month - 1, day));
			addResources(newAdministrator, baseUri);
			entityManager.getTransaction().begin();
			entityManager.persist(newAdministrator);
			entityManager.getTransaction().commit();
			return Response.created(newAdministrator.getResources().get(0).getHref()).entity(newAdministrator).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	private void addResources(Administrator administrator, String baseUri) {
		Link self = new Link(baseUri + "admins" + "/" + administrator.getUserId(), "self");
		Link students = new Link(baseUri + "students", "students");
		Link teachers = new Link(baseUri + "teachers", "teachers");
		Link parents = new Link(baseUri + "parents", "parents");
		Link payments = new Link(baseUri + "payments", "payments");
		Link notifications = new Link(baseUri + "notifications", "notifications");
		Link classrooms = new Link(baseUri + "classrooms", "classrooms");
		administrator.getResources().add(self);
		administrator.getResources().add(students);
		administrator.getResources().add(payments);
		administrator.getResources().add(notifications);
		administrator.getResources().add(teachers);
		administrator.getResources().add(parents);
		administrator.getResources().add(classrooms);
	}

	public List<Administrator> getAdministrators(String userId) {
		if (isAdministrator(userId))
			return entityManager.createQuery("Select a from Administrator a").getResultList();
		return null;
	}

}
