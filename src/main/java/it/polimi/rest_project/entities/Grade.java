package it.polimi.rest_project.entities;

import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@JoinColumn
	private Teacher teacher;
	@JoinColumn
	private List<Link> resources;

	public Grade() {
		Long random = new Random().nextLong();
		this.gradeId = Long.toUnsignedString(random);
	}

	public String getGradeId() {
		return gradeId;
	}

	public String getSubject() {
		return subject;
	}

	public float getMark() {
		return mark;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setMark(float mark) {
		this.mark = mark;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Link> getResources() {
		return resources;
	}

	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}
