package it.polimi.rest_project.entities;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Appointment {

	@Id
	private String appointmentId;
	@Column
	private Date date;
	@JoinColumn
	private Teacher teacher;
	@JoinColumn
	private Parent parent;


	public Appointment(Parent parent, Teacher teacher, Date date) {
		Integer random = new Random().nextInt();
		appointmentId = random.toString();
		this.parent = parent;
		this.teacher = teacher;
		this.date = date;
	}

	public Appointment(String appointmentId, Parent parent, Teacher teacher, Date date) {
		this.appointmentId = appointmentId;
		this.parent = parent;
		this.teacher = teacher;
		this.date = date;
	}

	public Appointment() {
		Integer random = new Random().nextInt();
		appointmentId = random.toString();
	}

	public Date getDate() {
		return date;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public Parent getParent() {
		return parent;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		if (appointmentId == null) {
			if (other.appointmentId != null)
				return false;
		} else if (!appointmentId.equals(other.appointmentId))
			return false;
		return true;
	}
}
