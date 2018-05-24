package it.polimi.rest_project.entities;

import java.util.Date;
import java.util.List;
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
	@JoinColumn
	private List<Link> resources;

	public Appointment() {
		Long random = new Random().nextLong();
		this.appointmentId = Long.toUnsignedString(random);
	}

	public String getAppointmentId() {
		return appointmentId;
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

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
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

	public List<Link> getResources() {
		return resources;
	}

	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}
