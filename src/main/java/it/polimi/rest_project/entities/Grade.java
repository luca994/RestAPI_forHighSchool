package it.polimi.rest_project.entities;

public class Grade {

	private String subject;
	private float value;
	
	public Grade() {}
	
	public Grade(String subject, float value) {
		super();
		this.subject = subject;
		this.value = value;
	}
	
	public String getSubject() {
		return subject;
	}
	public float getValue() {
		return value;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
	
}
