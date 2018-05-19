package it.polimi.rest_project.services;

import java.util.Date;
import java.util.List;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.Payment;

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

}
