package it.polimi.rest_project.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.polimi.rest_project.json.OptimizedDateSerializer;

/**
 * The Appointment entity
 *
 */
@Entity
@Table(name = "Appointments")
@JsonPropertyOrder({ "date", "accepted", "teacher", "parent", "resources" })
public class Appointment implements Serializable {

	private static final long serialVersionUID = -981096035532368791L;

	/** the id of the appointment */
	@Id
	@JsonIgnore
	private String appointmentId;

	/** the boolean indicating the teacher has accepted the appointment */
	@Column
	private boolean TeacherConfirmation;

	/** the boolean indicating the parent has accepted the appointment */
	@Column
	private boolean ParentConfirmation;

	/** the date for the appointment */
	@Column
	@JsonSerialize(using = OptimizedDateSerializer.class)
	private Calendar date;

	/** the teacher involved in the appointment */
	@JoinColumn
	@JsonIgnoreProperties({ "userId", "resources", "dateOfBirth" })
	private Teacher teacher;

	/** the parent involved in the appointment */
	@JoinColumn
	@JsonIgnoreProperties({ "userId", "resources", "dateOfBirth" })
	private Parent parent;

	/** the resources for the appointment */
	@JoinColumn
	private List<Link> resources;

	/**
	 * Default constructor for appointment that generates a random 8 characters id
	 */
	public Appointment() {
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z')
				.filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();
		this.appointmentId = generator.generate(8);
		this.resources = new ArrayList<Link>();
		this.TeacherConfirmation = false;
		this.ParentConfirmation = false;
	}

	/**
	 * @return the appointmentId
	 */
	public String getAppointmentId() {
		return appointmentId;
	}

	/**
	 * @return the teacherConfirmation
	 */
	public boolean isTeacherConfirmation() {
		return TeacherConfirmation;
	}

	/**
	 * @return the parentConfirmation
	 */
	public boolean isParentConfirmation() {
		return ParentConfirmation;
	}

	/**
	 * @return the date
	 */
	public Calendar getDate() {
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
	 * @return the resources
	 */
	public List<Link> getResources() {
		return resources;
	}

	/**
	 * @param appointmentId
	 *            the appointmentId to set
	 */
	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	/**
	 * @param teacherConfirmation
	 *            the teacherConfirmation to set
	 */
	public void setTeacherConfirmation(boolean teacherConfirmation) {
		TeacherConfirmation = teacherConfirmation;
	}

	/**
	 * @param parentConfirmation
	 *            the parentConfirmation to set
	 */
	public void setParentConfirmation(boolean parentConfirmation) {
		ParentConfirmation = parentConfirmation;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Calendar date) {
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

	/**
	 * @param resources
	 *            the resources to set
	 */
	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}
