package it.polimi.rest_project.services;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Teacher;

public class ParentService extends UserService {

	public ParentService() {
		super();
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

	public boolean updateParentAppointment(Appointment appointment) {
		String appointmentId = appointment.getAppointmentId();
		Date newDate = appointment.getDate();
		if (newDate == null)
			return false;
		entityManager.getTransaction().begin();
		appointment = entityManager.find(Appointment.class, appointmentId);
		appointment.setDate(newDate);
		entityManager.persist(appointment);
		entityManager.getTransaction().commit();
		return true;
	}

	public boolean addParentAppointment(String userId, String teacherId, Date date) {
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

}
