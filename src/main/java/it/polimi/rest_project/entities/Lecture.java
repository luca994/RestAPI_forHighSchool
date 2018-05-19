package it.polimi.rest_project.entities;

import java.time.DayOfWeek;

public class Lecture {

	private Classroom classRoom;
	private DayOfWeek day;
	private Integer hour;
	private Teacher teacher;
	private String subject;
	
	public Lecture() {}
	
	public Lecture(Classroom classRoom, DayOfWeek day, Integer hour, Teacher teacher, String subject) {
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
