package it.polimi.rest_project.entities;

public class Grade {

	private String gradeId;
	private String subject;
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

	public String getGradeId() {
		return gradeId;
	}

	public float getMark() {
		return mark;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public void setMark(float mark) {
		this.mark = mark;
	}

}
