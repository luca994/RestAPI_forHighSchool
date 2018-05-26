package it.polimi.rest_project.entities;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@JsonPropertyOrder({ "userId", "name", "surname", "dateOfBirth", "students", "resources" })
public class Parent extends User {

	@JoinColumn
	private List<Student> students;

	public Parent() {
		super();
	}
	
	public Parent(String name, String surname, String password, Calendar dateOfBirth) {
		super(name,surname,password,dateOfBirth);
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

}
