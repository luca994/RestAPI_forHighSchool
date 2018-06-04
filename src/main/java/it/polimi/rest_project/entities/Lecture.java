package it.polimi.rest_project.entities;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
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

@Entity
@Table(name = "Lectures")
@JsonPropertyOrder({ "day", "hour", "subject", "teacher", "resources" })
public class Lecture implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3869829608058783077L;
	@Id
	@JsonIgnore
	private String id;
	@Column
	private DayOfWeek day;
	@Column
	private Integer hour;
	@JoinColumn
	@JsonIgnoreProperties({ "userId", "resources", "dateOfBirth" })
	private Teacher teacher;
	@Column
	private String subject;
	@JoinColumn
	private List<Link> resources;

	public Lecture() {
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z')
				.filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();
		this.id = generator.generate(8);
		this.resources = new ArrayList<Link>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the day
	 */
	public DayOfWeek getDay() {
		return day;
	}

	/**
	 * @return the hour
	 */
	public Integer getHour() {
		return hour;
	}

	/**
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return the resources
	 */
	public List<Link> getResources() {
		return resources;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param day
	 *            the day to set
	 */
	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	/**
	 * @param hour
	 *            the hour to set
	 */
	public void setHour(Integer hour) {
		this.hour = hour;
	}

	/**
	 * @param teacher
	 *            the teacher to set
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @param resources
	 *            the resources to set
	 */
	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}
