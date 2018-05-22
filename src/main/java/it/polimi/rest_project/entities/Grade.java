package it.polimi.rest_project.entities;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Grades")
public class Grade {

	@Id
	private String gradeId;
	@Column
	private String subject;
	@Column
	private float mark;

	public Grade() {
		Long random = new Random().nextLong();
		this.gradeId = Long.toUnsignedString(random);
	}

	public Grade(String subject, float value) {
		super();
		this.subject = subject;
		this.mark = value;
		Long random = new Random().nextLong();
		this.gradeId = Long.toUnsignedString(random);
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
