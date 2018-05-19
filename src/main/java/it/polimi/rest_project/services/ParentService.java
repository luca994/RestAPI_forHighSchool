package it.polimi.rest_project.services;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.Notification;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Payment;
import it.polimi.rest_project.entities.Student;

public class ParentService extends UserService {

	private AppointmentService appointmentService;
	private PaymentService paymentService;

	public ParentService() {
		super();
		appointmentService = new AppointmentService(entityManager);
		paymentService = new PaymentService(entityManager);
	}

	/**
	 * fetches from the db and returns the list of all the appointments of the
	 * parent with the selected userId
	 * 
	 * @param userId
	 * @return
	 */
	public List<Appointment> getParentAppointments(String userId) {
		return appointmentService.getAppointments(userId);
	}

	public boolean updateParentAppointment(String appointmentId, Date newDate) {
		return appointmentService.updateAppointment(appointmentId, newDate);
	}

	public boolean addParentAppointment(String userId, String teacherId, Date date) {
		return appointmentService.addAppointment(userId, teacherId, date);
	}

	public boolean deleteParentAppointment(String appointmentId) {
		return appointmentService.deleteAppointment(appointmentId);
	}

	public List<Payment> getParentPayments(String userId) {
		return paymentService.getPayments(userId);
	}

	public boolean payParentPayments(String paymentId) {
		return paymentService.payPayment(paymentId);
	}
	
	public List<Notification> getParentNotifications(String userId){
		Query query = entityManager.createQuery("Select n from Notification n where n.user=:param");
		query.setParameter("param", entityManager.find(Parent.class, userId));
		return query.getResultList();
	}
	
	public List<Student> getParentStudents(String userId){
		Parent targetParent = entityManager.find(Parent.class, userId);
		return targetParent.getChildren();
	}

	public Student getParentStudent(String userId,String studentId){
		Student targetStudent = null;
		for(Student s:getParentStudents(userId))
			if(s.getUserId().equals(studentId)) {
				targetStudent=s;
				break;
			}
		return targetStudent;
	}
	
	
}
