package it.polimi.rest_project.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Student;

public class ParentService extends UserService {

	public ParentService() {
		super();
	}

	public Parent getParent(String userId, String parentId) {
		if (userId == parentId || isAdministrator(userId))
			return entityManager.find(Parent.class, parentId);
		return null;
	}

	public Response updateData(String userId, String parentId, String name, String surname, String childId, String day,
			String month, String year) {
		if (userId == parentId || isAdministrator(userId))
			return updateParentData(parentId, name, surname, childId, day, month, year);
		else
			return Response.status(Status.UNAUTHORIZED).build();
	}

	public Response updateParentData(String id, String name, String surname, String childId, String day, String month,
			String year) {
		Parent targetParent = entityManager.find(Parent.class, id);
		if (name != null)
			targetParent.setName(name);
		if (surname != null)
			targetParent.setSurname(surname);
		if (childId != null && isStudent(childId)) {
			targetParent.getStudents().add(entityManager.find(Student.class, childId));
		}
		if (day != null && month != null && year != null)
			try {
				targetParent.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
			} catch (ParseException e) {
				return Response.status(Status.NOT_MODIFIED).build();
			}
		entityManager.getTransaction().begin();
		entityManager.persist(targetParent);
		entityManager.getTransaction().commit();
		return Response.status(Status.OK).entity(targetParent).build();
	}

	public Response createParent(String userId, String name, String surname, String year, String month, String day,
			String password, String baseUri) {
		if (isAdministrator(userId)) {
			Parent newParent = new Parent();
			if (name == null || surname == null || day == null || month == null || year == null || password == null)
				return Response.status(Status.BAD_REQUEST).build();
			newParent.setName(name);
			newParent.setSurname(surname);
			newParent.setPassword(password);
			addResources(newParent, baseUri);
			try {
				newParent.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
			} catch (ParseException e) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			entityManager.getTransaction().begin();
			entityManager.persist(newParent);
			entityManager.getTransaction().commit();
			return Response.status(Status.CREATED).entity(newParent).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	private void addResources(Parent parent, String baseUri) {
		Link self = new Link(baseUri + "parents" + "/" + parent.getUserId(), "self");
		Link students = new Link(baseUri + "students", "children");
		Link appointments = new Link(baseUri + "appointments", "appointments");
		Link payments = new Link(baseUri + "payments", "payments");
		Link notifications = new Link(baseUri + "notifications", "notifications");
		parent.getResources().add(self);
		parent.getResources().add(students);
		parent.getResources().add(payments);
		parent.getResources().add(notifications);
		parent.getResources().add(appointments);
		entityManager.getTransaction().begin();
		entityManager.persist(self);
		entityManager.persist(students);
		entityManager.persist(notifications);
		entityManager.persist(appointments);
		entityManager.persist(payments);
		entityManager.getTransaction().commit();
	}

	public List<Parent> getParents(String userId) {
		if (isAdministrator(userId))
			return entityManager.createQuery("Select p from Parent p").getResultList();
		return null;
	}
}
