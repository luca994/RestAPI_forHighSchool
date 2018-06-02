package it.polimi.rest_project.services;

import java.util.GregorianCalendar;
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
		if (userId.equals(parentId) || isAdministrator(userId))
			return entityManager.find(Parent.class, parentId);
		return null;
	}

	public Response updateData(String userId, String parentId, String name, String surname, String childId, Integer day,
			Integer month, Integer year) {
		if (isAdministrator(userId))
			return updateParentData(parentId, name, surname, childId, day, month, year);
		else if(userId.equals(parentId) && childId==null) {
			return updateParentData(parentId, name, surname, childId, day, month, year);
		}
		else
			return Response.status(Status.UNAUTHORIZED).build();
	}

	public Response updateParentData(String id, String name, String surname, String childId, Integer day, Integer month,
			Integer year) {
		Parent targetParent = entityManager.find(Parent.class, id);
		Student targetStudent = null;
		if (name != null)
			targetParent.setName(name);
		if (surname != null)
			targetParent.setSurname(surname);
		if (day != null && month != null && year != null)
			targetParent.setDateOfBirth(new GregorianCalendar(year, month - 1, day));
		if (childId != null && isStudent(childId)) {
			targetStudent = entityManager.find(Student.class, childId);
			if (!targetParent.getStudents().contains(targetStudent)) {
				targetParent.getStudents().add(targetStudent);
				targetStudent.getParents().add(targetParent);
			}
		}
		entityManager.getTransaction().begin();
		entityManager.persist(targetParent);
		if (targetStudent != null)
			entityManager.persist(targetStudent);
		entityManager.getTransaction().commit();
		return Response.status(Status.OK).entity(targetParent).build();
	}

	public Response createParent(String userId, String name, String surname, Integer year, Integer month, Integer day,
			String password, String baseUri) {
		if (isAdministrator(userId)) {
			if (name == null || surname == null || day == null || month == null || year == null || password == null)
				return Response.status(Status.BAD_REQUEST).build();
			Parent newParent = new Parent(name, surname, password, new GregorianCalendar(year, month - 1, day));
			addResources(newParent, baseUri);
			entityManager.getTransaction().begin();
			entityManager.persist(newParent);
			entityManager.getTransaction().commit();
			return Response.created(newParent.getResources().get(0).getHref()).entity(newParent).build();
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
	}

	public List<Parent> getParents(String userId) {
		if (isAdministrator(userId))
			return entityManager.createQuery("Select p from Parent p").getResultList();
		return null;
	}
}
