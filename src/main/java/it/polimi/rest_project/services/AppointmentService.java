package it.polimi.rest_project.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Teacher;

public class AppointmentService {

	private EntityManager entityManager;

	public AppointmentService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * fetches from the db and returns the list of all the appointments of the
	 * parent with the selected userId
	 * 
	 * @param userId
	 * @return
	 */
	public List<Appointment> getParentAppointments(String userId) {
		Query query = entityManager.createQuery("Select a from Appointment a where a.parent=:param");
		query.setParameter("param", entityManager.find(Parent.class, userId));
		return query.getResultList();
	}

	public List<Appointment> getTeacherAppointments(String userId) {
		Query query = entityManager.createQuery("Select a from Appointment a where a.teacher=:param");
		query.setParameter("param", entityManager.find(Teacher.class, userId));
		return query.getResultList();
	}

	public boolean checkIfAppointmentExistsForTeacher(String userId, String appointmentId) {
		Query query = entityManager.createQuery("Select a from Appointment a where a.teacher=:param");
		query.setParameter("param", entityManager.find(Teacher.class, userId));
		if (query.getResultList().size() != 1)
			return false;
		else
			return true;
	}

	public boolean checkIfAppointmentExistsForParent(String userId, String appointmentId) {
		Query query = entityManager.createQuery("Select a from Appointment a where a.parent=:param");
		query.setParameter("param", entityManager.find(Parent.class, userId));
		if (query.getResultList().size() != 1)
			return false;
		else
			return true;
	}

	public boolean updateAppointment(String appointmentId, Date newDate) {
		Appointment targetAppointment;
		if (newDate == null || appointmentId == null)
			return false;
		entityManager.getTransaction().begin();
		targetAppointment = entityManager.find(Appointment.class, appointmentId);
		targetAppointment.setDate(newDate);
		entityManager.persist(targetAppointment);
		entityManager.getTransaction().commit();
		return true;
	}

	public boolean addAppointment(String parentId, String teacherId, Date date, String uriInfo) {
		if (parentId == null || teacherId == null || date == null)
			return false;
		Parent targetParent = entityManager.find(Parent.class, parentId);
		Teacher targetTeacher = entityManager.find(Teacher.class, teacherId);
		Appointment newAppointment = new Appointment(targetParent, targetTeacher, date);
		Link link1 = new Link();
		link1.setRel("appointment");
		link1.setUri(uriInfo + "parents" + "/" + targetParent.getUserId() + "/" + "appointments" + "/"
				+ newAppointment.getAppointmentId());
		Link link2 = new Link();
		link2.setRel("appointment");
		link2.setUri(uriInfo + "teachers" + "/" + targetTeacher.getUserId() + "/" + "appointments" + "/"
				+ newAppointment.getAppointmentId());
		targetParent.getResources().add(link1);
		targetTeacher.getResources().add(link2);
		entityManager.getTransaction().begin();
		entityManager.persist(newAppointment);
		entityManager.persist(targetParent);
		entityManager.persist(targetTeacher);
		entityManager.getTransaction().commit();
		return true;
	}

	public boolean deleteAppointment(String appointmentId) {
		Appointment targetAppointment;
		Link toDelete = null;
		if (appointmentId == null)
			return false;
		targetAppointment = entityManager.find(Appointment.class, appointmentId);
		for (Link l : targetAppointment.getTeacher().getResources())
			if (l.getUri().contains("appointments" + "/" + targetAppointment.getAppointmentId()))
				toDelete = l;
		if (toDelete != null)
			targetAppointment.getTeacher().getResources().remove(toDelete);
		toDelete = null;
		for (Link l : targetAppointment.getParent().getResources())
			if (l.getUri().contains("appointments" + "/" + targetAppointment.getAppointmentId()))
				toDelete = l;
		if (toDelete != null)
			targetAppointment.getTeacher().getResources().remove(toDelete);
		entityManager.getTransaction().begin();
		entityManager.persist(targetAppointment.getTeacher());
		entityManager.persist(targetAppointment.getParent());
		entityManager.remove(targetAppointment);
		entityManager.getTransaction().commit();
		return true;
	}

}
