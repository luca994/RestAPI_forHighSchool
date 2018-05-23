package it.polimi.rest_project.entities;

import java.time.DayOfWeek;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Lectures")
public class Lecture {

	@Id
	private String id;
	@Column
	private DayOfWeek day;
	@Column
	private Integer hour;
	@JoinColumn
	private Teacher teacher;
	@Column
	private String subject;

	public Lecture() {
		Long random = new Random().nextLong();
		this.id = Long.toUnsignedString(random);
	}

	public Lecture(Classroom classRoom, DayOfWeek day, Integer hour, Teacher teacher, String subject) {
		Long random = new Random().nextLong();
		this.id = Long.toUnsignedString(random);
		this.day = day;
		this.hour = hour;
		this.teacher = teacher;
		this.subject = subject;
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

}
