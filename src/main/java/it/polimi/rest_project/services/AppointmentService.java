package it.polimi.rest_project.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.polimi.rest_project.entities.Appointment;
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
	public List<Appointment> getAppointments(String userId) {
		Query query = entityManager.createQuery("Select a from Appointment a where a.parent=:param");
		query.setParameter("param", entityManager.find(Parent.class, userId));
		return query.getResultList();
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

	public boolean addAppointment(String userId, String teacherId, Date date) {
		if (userId == null || teacherId == null || date == null)
			return false;
		Parent targetParent = entityManager.find(Parent.class, userId);
		Teacher targetTeacher = entityManager.find(Teacher.class, teacherId);
		Appointment newAppointment = new Appointment(targetParent, targetTeacher, date);
		entityManager.getTransaction().begin();
		entityManager.persist(newAppointment);
		entityManager.getTransaction().commit();
		return true;
	}

	public boolean deleteAppointment(String appointmentId) {
		Appointment targetAppointment;
		if (appointmentId == null)
			return false;
		entityManager.getTransaction().begin();
		targetAppointment = entityManager.find(Appointment.class, appointmentId);
		entityManager.remove(targetAppointment);
		entityManager.getTransaction().commit();
		return true;
	}

}
