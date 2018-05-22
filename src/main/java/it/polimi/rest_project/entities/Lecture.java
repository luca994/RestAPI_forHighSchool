package it.polimi.rest_project.entities;

import java.time.DayOfWeek;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="Lectures")
public class Lecture {

	@Id
	private String id;
	@JoinColumn
	private Classroom classRoom;
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
		this.classRoom = classRoom;
		this.day = day;
		this.hour = hour;
		this.teacher = teacher;
		this.subject = subject;
	}
	
	public Classroom getClassRoom() {
		return classRoom;
	}
	public DayOfWeek getDay() {
		return day;
	}
	public Integer getHour() {
		return hour;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public String getSubject() {
		return subject;
	}
	public void setClassRoom(Classroom classRoom) {
		this.classRoom = classRoom;
	}
	public void setDay(DayOfWeek day) {
		this.day = day;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
}
