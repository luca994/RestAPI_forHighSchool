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
		this.subject = subject;
		this.mark = value;
		Long random = new Random().nextLong();
		this.gradeId = Long.toUnsignedString(random);
	}

	/**
	 * @return the gradeId
	 */
	public String getGradeId() {
		return gradeId;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return the mark
	 */
	public float getMark() {
		return mark;
	}

	/**
	 * @param gradeId
	 *            the gradeId to set
	 */
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @param mark
	 *            the mark to set
	 */
	public void setMark(float mark) {
		this.mark = mark;
	}

}
