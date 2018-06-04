package it.polimi.rest_project.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.application.Back2School;
import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.GeneralNotification;
import it.polimi.rest_project.entities.Lecture;
import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Notification;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.SpecificNotification;
import it.polimi.rest_project.entities.Student;
import it.polimi.rest_project.entities.Teacher;

public class NotificationService {

	private EntityManager entityManager;

	public NotificationService() {
		entityManager = Back2School.getEntityManager();
	}

	public boolean isAuthorized(String userId, String notificationId) {
		UserService userService = new UserService();
		Notification targetNotification = entityManager.find(Notification.class, notificationId);
		if (userService.isAdministrator(userId))
			return true;
		if (targetNotification.getUser().getUserId().equals(userId))
			return true;
		return false;
	}

	public Notification getNotification(String userId, String notificationId) {
		if (isAuthorized(userId, notificationId))
			return entityManager.find(Notification.class, notificationId);
		return null;
	}

	public Response createNotification(String userId, String id, String text, String baseUri) {
		UserService userService = new UserService();
		if (text == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (userService.isAdministrator(userId)) {
			if (userService.isParent(id) || userService.isTeacher(id))
				return createSpecificNotification(id, text, baseUri);
			if (id == null)
				return createGeneralNotification(text, baseUri);
			Query query = entityManager.createQuery("Select c from Classroom c where c.classroomId=:classId");
			query.setParameter("classId", id);
			List<Classroom> classroom = query.getResultList();
			if (classroom.size() == 1)
				return createGeneralClassNotification(classroom.get(0), text, baseUri);
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	private Response createGeneralClassNotification(Classroom classroom, String text, String baseUri) {
		List<Parent> parents = entityManager.createQuery("Select p from Parent p").getResultList();
		List<Teacher> teachers = entityManager.createQuery("Select t from Teacher t").getResultList();
		for (Parent p : parents) {
			for (Student s : p.getStudents())
				for(Student s1:classroom.getStudents())
					if(s1.getUserId().equals(s.getUserId()))
				{
					GeneralNotification newNotification = new GeneralNotification();
					newNotification.setUser(p);
					newNotification.setText(text);
					addResources(newNotification, baseUri);
					entityManager.getTransaction().begin();
					entityManager.persist(newNotification);
					entityManager.getTransaction().commit();
					break;
				}
		}
		for (Teacher t : teachers) {
			for (Lecture l : classroom.getLectures())
				if (l.getTeacher().getUserId().equals(t.getUserId())) {
					GeneralNotification newNotification = new GeneralNotification();
					newNotification.setUser(t);
					newNotification.setText(text);
					addResources(newNotification, baseUri);
					entityManager.getTransaction().begin();
					entityManager.persist(newNotification);
					entityManager.getTransaction().commit();
					break;
				}
		}
		return Response.status(Status.CREATED).build();
	}

	private Response createGeneralNotification(String text, String baseUri) {
		List<Parent> parents = entityManager.createQuery("Select p from Parent p").getResultList();
		List<Teacher> teachers = entityManager.createQuery("Select t from Teacher t").getResultList();
		for (Parent p : parents) {
			GeneralNotification newNotification = new GeneralNotification();
			newNotification.setUser(p);
			newNotification.setText(text);
			addResources(newNotification, baseUri);
			entityManager.getTransaction().begin();
			entityManager.persist(newNotification);
			entityManager.getTransaction().commit();
		}
		for (Teacher t : teachers) {
			GeneralNotification newNotification = new GeneralNotification();
			newNotification.setUser(t);
			newNotification.setText(text);
			addResources(newNotification, baseUri);
			entityManager.getTransaction().begin();
			entityManager.persist(newNotification);
			entityManager.getTransaction().commit();
		}
		return Response.status(Status.CREATED).build();
	}

	private Response createSpecificNotification(String id, String text, String baseUri) {
		UserService userService = new UserService();
		SpecificNotification newNotification = new SpecificNotification();
		if (userService.isParent(id)) {
			newNotification.setUser(entityManager.find(Parent.class, id));
		}
		if (userService.isTeacher(id)) {
			newNotification.setUser(entityManager.find(Teacher.class, id));
		}
		newNotification.setText(text);
		addResources(newNotification, baseUri);
		entityManager.getTransaction().begin();
		entityManager.persist(newNotification);
		entityManager.getTransaction().commit();
		return Response.created(newNotification.getResources().get(0).getHref()).entity(newNotification).build();
	}

	/**
	 * Adds the accessible resources to the entity
	 */
	private void addResources(Notification notification, String baseUri) {
		Link self = new Link(baseUri + "notifications" + "/" + notification.getId(), "self");
		notification.getResources().add(self);
	}

	public List<Notification> getNotifications(String userId) {
		UserService userService = new UserService();
		if (userService.isAdministrator(userId))
			return entityManager.createQuery("Select n from Notification n").getResultList();
		if (userService.isParent(userId) || userService.isTeacher(userId)) {
			Query query = entityManager.createQuery("Select n from Notification n where n.user.userId=:userId");
			query.setParameter("userId", userId);
			return query.getResultList();
		}
		return null;
	}

	public List<Notification> getGeneralNotifications(String userId) {
		UserService userService = new UserService();
		if (userService.isAdministrator(userId))
			return entityManager.createQuery("Select n from GeneralNotification n").getResultList();
		if (userService.isParent(userId) || userService.isTeacher(userId)) {
			Query query = entityManager.createQuery("Select n from GeneralNotification n where n.user.userId=:userId");
			query.setParameter("userId", userId);
			return query.getResultList();
		}
		return null;
	}

	public List<Notification> getSpecificNotifications(String userId) {
		UserService userService = new UserService();
		if (userService.isAdministrator(userId))
			return entityManager.createQuery("Select n from SpecificNotification n").getResultList();
		if (userService.isParent(userId) || userService.isTeacher(userId)) {
			Query query = entityManager.createQuery("Select n from SpecificNotification n where n.user.userId=:userId");
			query.setParameter("userId", userId);
			return query.getResultList();
		}
		return null;
	}

}
