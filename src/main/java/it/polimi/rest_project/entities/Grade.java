package it.polimi.rest_project.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Grade{

	@Id
	private String gradeId;
	@Column
	private String subject;
	@Column
	private float mark;

	public Grade() {
	}

	public Grade(String subject, float value) {
		super();
		this.subject = subject;
		this.mark = value;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public float getMark() {
		return mark;
	}

	public void setMark(float mark) {
		this.mark = mark;
	}

}
