package it.polimi.rest_project.entities;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Appointments")
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
		Long random = new Random().nextLong();
		this.appointmentId = Long.toUnsignedString(random);
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
		Long random = new Random().nextLong();
		this.appointmentId = Long.toUnsignedString(random);
	}

	/**
	 * @return the appointmentId
	 */
	public String getAppointmentId() {
		return appointmentId;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @return the parent
	 */
	public Parent getParent() {
		return parent;
	}

	/**
	 * @param appointmentId
	 *            the appointmentId to set
	 */
	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @param teacher
	 *            the teacher to set
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(Parent parent) {
		this.parent = parent;
	}

}
