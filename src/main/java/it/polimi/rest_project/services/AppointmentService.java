package it.polimi.rest_project.services;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.application.Back2School;
import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Teacher;

public class AppointmentService {

	private EntityManager entityManager;
	private final int appointmentPerDay;

	public AppointmentService() {
		entityManager = Back2School.getEntityManager();
		appointmentPerDay = 4;
	}

	public boolean isAuthorized(String userId, String appointmentId) {
		UserService userService = new UserService();
		Appointment targetAppointment = entityManager.find(Appointment.class, appointmentId);
		if (userService.isAdministrator(userId))
			return true;
		if (userService.isParent(userId) && targetAppointment.getParent().getUserId().equals(userId))
			return true;
		if (userService.isTeacher(userId) && targetAppointment.getTeacher().getUserId().equals(userId))
			return true;
		return false;
	}

	public Appointment getAppointment(String userId, String appointmentId) {
		if (isAuthorized(userId, appointmentId))
			return entityManager.find(Appointment.class, appointmentId);
		return null;
	}

	public Response updateAppointment(String userId, String appointmentId, Integer day, Integer month, Integer year) {
		if (isAuthorized(userId, appointmentId)) {
			Appointment targetAppointment = entityManager.find(Appointment.class, appointmentId);
			if (day != null || month != null || year != null) {
				if (areAvailable(targetAppointment.getParent().getUserId(), targetAppointment.getTeacher().getUserId(),
						day, month, year) == false)
					return Response.status(Status.BAD_REQUEST).entity("One person is not available on this date").build();
				targetAppointment.setDate(new GregorianCalendar(year, month - 1, day));
			}
			UserService userService = new UserService();
			if (userService.isParent(userId))
				targetAppointment.setParentConfirmation(true);
			else
				targetAppointment.setTeacherConfirmation(true);
			entityManager.getTransaction().begin();
			entityManager.persist(targetAppointment);
			entityManager.getTransaction().commit();
			return Response.status(Status.OK).entity(targetAppointment).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	public Response createAppointment(String userId, String user2Id, Integer day, Integer month, Integer year,
			String baseUri) {
		if (day == null || month == null || year == null
				|| new GregorianCalendar().after(new GregorianCalendar(year, month, day)))
			return Response.status(Status.BAD_REQUEST).entity("Invalid date").build();
		UserService userService = new UserService();
		Appointment newAppointment = new Appointment();
		if (userService.isParent(userId) && userService.isTeacher(user2Id)) {
			if (areAvailable(userId, user2Id, day, month, year) == false)
				return Response.status(Status.BAD_REQUEST).entity("One person is not available on this date").build();
			newAppointment.setParent(entityManager.find(Parent.class, userId));
			newAppointment.setTeacher(entityManager.find(Teacher.class, user2Id));
			newAppointment.setDate(new GregorianCalendar(year, month - 1, day));
			newAppointment.setParentConfirmation(true);
			addResources(newAppointment, baseUri);
			entityManager.getTransaction().begin();
			entityManager.persist(newAppointment);
			entityManager.getTransaction().commit();
			return Response.created(newAppointment.getResources().get(0).getHref()).entity(newAppointment).build();
		}
		if (userService.isParent(user2Id) && userService.isTeacher(userId)) {
			if (areAvailable(userId, user2Id, day, month, year) == false)
				return Response.status(Status.BAD_REQUEST).entity("One person is not available on this date").build();
			newAppointment.setParent(entityManager.find(Parent.class, user2Id));
			newAppointment.setTeacher(entityManager.find(Teacher.class, userId));
			newAppointment.setDate(new GregorianCalendar(year, month - 1, day));
			newAppointment.setTeacherConfirmation(true);
			addResources(newAppointment, baseUri);
			entityManager.getTransaction().begin();
			entityManager.persist(newAppointment);
			entityManager.getTransaction().commit();
			return Response.created(newAppointment.getResources().get(0).getHref()).entity(newAppointment).build();
		}
		return Response.status(Status.BAD_REQUEST).entity("You can create only appointment between a parent and a teacher").build();
	}

	private boolean areAvailable(String userId, String user2Id, Integer day, Integer month, Integer year) {
		UserService userService = new UserService();
		int counter = appointmentPerDay;
		Calendar date = new GregorianCalendar(year, month - 1, day);
		if (date.get(7) == 1 || date.get(7) == 7) // no appointments on saturday or sunday
			return false;
		if (userService.isParent(userId) && userService.isTeacher(user2Id)) {
			Query query1 = entityManager.createQuery("select a from Appointment a where a.parent.userId=:parent");
			query1.setParameter("parent", userId);
			List<Appointment> appointments = query1.getResultList();
			for (Appointment a : appointments)
				if (a.getDate().compareTo(date) == 0)
					counter--;
			if (counter == 0)
				return false;
			else
				counter = appointmentPerDay;
			Query query2 = entityManager.createQuery("select a from Appointment a where a.teacher.userId=:teacher");
			query2.setParameter("teacher", user2Id);
			appointments = query2.getResultList();
			for (Appointment a : appointments)
				if (a.getDate().compareTo(date) == 0)
					counter--;
			if (counter == 0)
				return false;
			else
				return true;
		}
		if (userService.isParent(user2Id) && userService.isTeacher(userId)) {
			Query query1 = entityManager.createQuery("select a from Appointment a where a.parent.userId=:parent");
			query1.setParameter("parent", user2Id);
			List<Appointment> appointments = query1.getResultList();
			for (Appointment a : appointments)
				if (a.getDate().compareTo(date) == 0)
					counter--;
			if (counter == 0)
				return false;
			else
				counter = appointmentPerDay;
			Query query2 = entityManager.createQuery("select a from Appointment a where a.teacher.userId=:teacher");
			query2.setParameter("teacher", userId);
			appointments = query2.getResultList();
			for (Appointment a : appointments)
				if (a.getDate().compareTo(date) == 0)
					counter--;
			if (counter == 0)
				return false;
			else
				return true;
		}
		return false;
	}

	/**
	 * Adds the accessible resources to the entity
	 */
	private void addResources(Appointment appointment, String baseUri) {
		Link self = new Link(baseUri + "appointments" + "/" + appointment.getAppointmentId(), "self");
		appointment.getResources().add(self);
	}

	public List<Appointment> getAppointments(String userId) {
		UserService userService = new UserService();
		if (userService.isTeacher(userId)) {
			Query query = entityManager.createQuery("Select a from Appointment a where a.teacher.userId=:userId");
			query.setParameter("userId", userId);
			return query.getResultList();
		}
		if (userService.isParent(userId)) {
			Query query = entityManager.createQuery("Select a from Appointment a where a.parent.userId=:userId");
			query.setParameter("userId", userId);
			return query.getResultList();
		}
		return null;
	}

	public Response deleteAppointment(String userId, String appointmentId) {
		if (isAuthorized(userId, appointmentId)) {
			Appointment toDelete = entityManager.find(Appointment.class, appointmentId);
			entityManager.getTransaction().begin();
			entityManager.remove(toDelete);
			entityManager.getTransaction().commit();
			return Response.status(Status.NO_CONTENT).entity("Appointment "+appointmentId+" deleted").build();
		}
		return Response.status(Status.UNAUTHORIZED).entity("You must be a person involved in this appointment to delete it").build();
	}

}
