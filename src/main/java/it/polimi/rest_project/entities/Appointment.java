package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.polimi.rest_project.json.OptimizedDateSerializer;

@Entity
@Table(name = "Appointments")
@JsonPropertyOrder({ "date", "teacher", "parent", "resources" })
public class Appointment {

	@Id
	@JsonIgnore
	private String appointmentId;
	@Column
	@JsonSerialize(using = OptimizedDateSerializer.class)
	private Calendar date;
	@JoinColumn
	@JsonIgnoreProperties({ "userId", "resources", "dateOfBirth" })
	private Teacher teacher;
	@JoinColumn
	@JsonIgnoreProperties({ "userId", "resources", "dateOfBirth" })
	private Parent parent;
	@JoinColumn
	private List<Link> resources;

	public Appointment() {
		Long random = new Random().nextLong();
		this.appointmentId = Long.toUnsignedString(random);
		this.resources=new ArrayList<Link>();
	}

	public String getAppointmentId() {
		return appointmentId;
	}

	public Calendar getDate() {
		return date;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public Parent getParent() {
		return parent;
	}

	public List<Link> getResources() {
		return resources;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public void setResources(List<Link> resources) {
		this.resources = resources;
	}


}
