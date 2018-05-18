package resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Teacher extends User {

	private Map<Classroom, String> classSubject;
	private List<Notification> notifications;
	private List<Appointment> appointments; //override del metodo add della lista per controllare gli orari e i giorni degli appuntamenti?

	public Teacher() {
		classSubject = new HashMap<>();
		notifications = new ArrayList<>();
		appointments = new ArrayList<>();
		Integer random = new Random().nextInt();
		this.userId = random.toString();
	}
	
	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Map<Classroom, String> getClassSubject() {
		return classSubject;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setClassSubject(Map<Classroom, String> classSubject) {
		this.classSubject = classSubject;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

}
