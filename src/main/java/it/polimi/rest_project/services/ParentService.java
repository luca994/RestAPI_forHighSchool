package it.polimi.rest_project.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	 * returns the instance of the parent with that id
	 * 
	 * @param id
	 * @return
	 */
	public Parent getParent(String id) {
		Parent targetParent = entityManager.find(Parent.class, id);
		return targetParent;
	}

	/**
	 * returns the list of children of the parent with userId
	 * 
	 * @param userId
	 * @return
	 */
	public List<Student> getParentStudents(String userId) {
		Parent targetParent = getParent(userId);
		Query query = entityManager.createQuery("Select s from Student s where :param in s.parents");
		query.setParameter("param", targetParent);
		return query.getResultList();
	}

	/**
	 * returns the student with studentId if and only if the userId of the parent
	 * corresponds effectively to a student's parent
	 * 
	 * @param userId
	 * @param studentId
	 * @return
	 */
	public Student getParentStudent(String userId, String studentId) {
		Student targetStudent = entityManager.find(Student.class, studentId);
		if (getParentStudents(userId).contains(targetStudent))
			return targetStudent;
		else
			return null;
	}

	/**
	 * fetches from the db and returns the list of all the appointments of the
	 * parent with the selected userId
	 * 
	 * @param userId
	 * @return
	 */
	public List<Appointment> getParentAppointments(String userId) {
		return appointmentService.getParentAppointments(userId);
	}

	/**
	 * returns the appointment of with appointmentId and userId if it exists.
	 * 
	 * @param userId
	 * @param appointmentId
	 * @return
	 */
	public Appointment getParentAppointment(String userId, String appointmentId) {
		Appointment targetAppointment = entityManager.find(Appointment.class, appointmentId);
		if (getParentAppointments(userId).contains(targetAppointment))
			return targetAppointment;
		else
			return null;
	}

	/**
	 * if the appointment selected exists it updates that appointment with the new
	 * date
	 * 
	 * @param userId
	 * @param appointmentId
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public boolean updateParentAppointment(String userId, String appointmentId, String day, String month, String year) {
		if (!appointmentService.checkIfAppointmentExistsForParent(userId, appointmentId))
			return false;
		else if (day != null && month != null && year != null)
			try {
				Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day);
				return appointmentService.updateAppointment(appointmentId, newDate);
			} catch (ParseException e) {
				return false;
			}
		else
			return false;
	}

	/**
	 * if the data is correct it adds an appointment between the parent and the
	 * teacher
	 * 
	 * @param userId
	 * @param teacherId
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public boolean addParentAppointment(String userId, String teacherId, String day, String month, String year,
			String uriInfo) {
		if (day != null && month != null && year != null)
			try {
				Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day);
				return appointmentService.addAppointment(userId, teacherId, newDate, uriInfo);
			} catch (ParseException e) {
				return false;
			}
		else
			return false;

	}

	/**
	 * deletes the appointment with that id if exists an appointment with that
	 * appointmentid and userid
	 * 
	 * @param userId
	 * @param appointmentId
	 * @return
	 */
	public boolean deleteParentAppointment(String userId, String appointmentId) {
		if (appointmentService.checkIfAppointmentExistsForParent(userId, appointmentId) == false)
			return false;
		else
			return appointmentService.deleteAppointment(appointmentId);
	}

	/**
	 * returns all the payments involving the user with userId
	 * 
	 * @param userId
	 * @return
	 */
	public List<Payment> getParentPayments(String userId) {
		return paymentService.getPaymentsFromUser(userId);
	}

	/**
	 * returns the payment with paymentId and userId if it exists.
	 * 
	 * @param userId
	 * @param paymentId
	 * @return
	 */
	public Payment getParentPayment(String userId, String paymentId) {
		return paymentService.getPayment(userId, paymentId);
	}

	/**
	 * it "pays" the payment with paymentId, so it turns true the value of the
	 * attribute done of the corresponding payment
	 * 
	 * @param paymentId
	 * @return
	 */
	public boolean payParentPayments(String paymentId) {
		return paymentService.payPayment(paymentId);
	}

	/**
	 * returns all the notifications directed to userId
	 * 
	 * @param userId
	 * @return
	 */
	public List<Notification> getParentNotifications(String userId) {
		Query query = entityManager.createQuery("Select n from Notification n where n.user=:param");
		query.setParameter("param", entityManager.find(Parent.class, userId));
		return query.getResultList();
	}

	/**
	 * returns the notification with notificationId and userId if it exists.
	 * 
	 * @param userId
	 * @param notificationId
	 * @return
	 */
	public Notification getParentNotification(String userId, String notificationId) {
		Notification targetNotification = entityManager.find(Notification.class, notificationId);
		if (getParentNotifications(userId).contains(targetNotification))
			return targetNotification;
		else
			return null;
	}

}
