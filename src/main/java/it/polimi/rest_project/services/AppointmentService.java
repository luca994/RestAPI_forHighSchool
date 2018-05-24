package it.polimi.rest_project.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Teacher;

public class AppointmentService {

	private EntityManager entityManager;

	public AppointmentService() {
		entityManager = Back2School.getEntityManager();
	}

	public boolean isAuthorized(String userId, String appointmentId) {
		UserService userService = new AdministratorService();
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

	public Response updateAppointment(String userId, String appointmentId, String day, String month, String year) {
		if (isAuthorized(userId, appointmentId)) {
			Appointment targetAppointment = entityManager.find(Appointment.class, appointmentId);
			if (day == null || month == null || year == null)
				return Response.status(Status.BAD_REQUEST).build();
			try {
				Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day);
				targetAppointment.setDate(newDate);
				entityManager.getTransaction().begin();
				entityManager.persist(targetAppointment);
				entityManager.getTransaction().commit();
				return Response.status(Status.OK).entity(targetAppointment).build();
			} catch (ParseException e) {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	public Response createAppointment(String userId, String user2Id, String day, String month, String year,
			String baseUri) {
		UserService userService = new AdministratorService();
		Appointment newAppointment = new Appointment();
		if (userService.isParent(userId) && userService.isTeacher(user2Id)) {
			newAppointment.setParent(entityManager.find(Parent.class, userId));
			newAppointment.setTeacher(entityManager.find(Teacher.class, user2Id));
			try {
				newAppointment.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
				addResources(newAppointment, baseUri);
				entityManager.getTransaction().begin();
				entityManager.persist(newAppointment);
				entityManager.getTransaction().commit();
				return Response.status(Status.CREATED).entity(newAppointment).build();
			} catch (ParseException e) {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
		if (userService.isParent(user2Id) && userService.isTeacher(userId)) {
			newAppointment.setParent(entityManager.find(Parent.class, user2Id));
			newAppointment.setTeacher(entityManager.find(Teacher.class, userId));
			try {
				newAppointment.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
				addResources(newAppointment, baseUri);
				entityManager.getTransaction().begin();
				entityManager.persist(newAppointment);
				entityManager.getTransaction().commit();
				return Response.status(Status.CREATED).entity(newAppointment).build();
			} catch (ParseException e) {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	private void addResources(Appointment appointment, String baseUri) {
		Link self = new Link(baseUri + "/" + "appointments" + appointment.getAppointmentId(), "self");
		appointment.getResources().add(self);
		entityManager.getTransaction().begin();
		entityManager.persist(self);
		entityManager.getTransaction().commit();
	}

	public List<Appointment> getAppointments(String userId) {
		UserService userService = new AdministratorService();
		if (userService.isTeacher(userId))
			return entityManager.createQuery("Select a from Appointment a where a.teacher.userId=" + userId)
					.getResultList();
		if (userService.isParent(userId))
			return entityManager.createQuery("Select a from Appointment a where a.parent.userId=" + userId)
					.getResultList();
		return null;
	}

}
