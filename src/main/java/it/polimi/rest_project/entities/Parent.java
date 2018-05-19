package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Parent extends User {

	private List<Student> children;
	private List<Appointment> appointments; //  (to delete)override del metodo add della lista per controllare gli orari e i giorni
											// degli appuntamenti?
	private List<Payment> oldPayments;  //delete
	private List<Payment> newPayments;  //delete
	private List<Notification> notifications; //to delete

	public Parent(PersonalData personalData) {
		this.setPersonalData(personalData);
	}

	public Parent() {
		children = new ArrayList<>();
		appointments = new ArrayList<>();
		oldPayments = new ArrayList<>();
		newPayments = new ArrayList<>();
		notifications = new ArrayList<>();
		Integer random = new Random().nextInt();
		this.setUserId(random.toString());
	}

	public List<Student> getChildren() {
		return children;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public List<Payment> getOldPayments() {
		return oldPayments;
	}

	public List<Payment> getNewPayments() {
		return newPayments;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setChildren(List<Student> children) {
		this.children = children;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public void setOldPayments(List<Payment> oldPayments) {
		this.oldPayments = oldPayments;
	}

	public void setNewPayments(List<Payment> newPayments) {
		this.newPayments = newPayments;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

}
