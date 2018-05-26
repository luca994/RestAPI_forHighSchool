package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

@Entity
public class Student extends User {

	@JoinColumn
	private List<Grade> grades;

	public Student() {
		super();
		grades = new ArrayList<Grade>();
	}

	public Student(String name, String surname, String password, Calendar dateOfBirth) {
		super(name, surname, password, dateOfBirth);
		grades = new ArrayList<Grade>();
	}

	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

}
