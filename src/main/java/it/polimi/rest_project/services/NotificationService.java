package it.polimi.rest_project.services;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Notification;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.SpecificNotification;
import it.polimi.rest_project.entities.Teacher;

public class NotificationService {

	private EntityManager entityManager;

	public NotificationService() {
		entityManager = Back2School.getEntityManager();
	}

	public boolean isAuthorized(String userId, String notificationId) {
		UserService userService = new AdministratorService();
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

	public Response createNotification(String userId, String user2Id, String text, String baseUri) {
		UserService userService = new AdministratorService();
		if (userService.isAdministrator(userId)) {
			SpecificNotification newNotification = new SpecificNotification();
			if (userService.isParent(user2Id))
				newNotification.setUser(entityManager.find(Parent.class, user2Id));
			if (userService.isTeacher(user2Id))
				newNotification.setUser(entityManager.find(Teacher.class, user2Id));
			newNotification.setText(text);
			addResources(newNotification, baseUri);
			entityManager.getTransaction().begin();
			entityManager.persist(newNotification);
			entityManager.getTransaction().commit();
			return Response.status(Status.CREATED).entity(newNotification).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	private void addResources(Notification notification, String baseUri) {
		Link self = new Link(baseUri + "/" + "notifications" + notification.getId(), "self");
		notification.getResources().add(self);
		entityManager.getTransaction().begin();
		entityManager.persist(self);
		entityManager.getTransaction().commit();
	}

}
