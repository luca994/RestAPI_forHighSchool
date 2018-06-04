package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@JsonPropertyOrder({ "userId", "name", "surname", "dateOfBirth", "grades", "resources" })
public class Student extends User {

	
	private static final long serialVersionUID = -3489086630776667506L;
	@JoinColumn
	private List<Grade> grades;
	@JoinColumn
	@JsonIgnoreProperties({ "students", "resources", "dateOfBirth" })
	private List<Parent> parents;

	public Student() {
		super();
		grades = new ArrayList<Grade>();
		parents = new ArrayList<Parent>();
	}

	public Student(String name, String surname, String password, Calendar dateOfBirth) {
		super(name, surname, password, dateOfBirth);
		grades = new ArrayList<Grade>();
		parents = new ArrayList<Parent>();
	}

	/**
	 * @return the grades
	 */
	public List<Grade> getGrades() {
		return grades;
	}

	/**
	 * @return the parents
	 */
	public List<Parent> getParents() {
		return parents;
	}

	/**
	 * @param grades
	 *            the grades to set
	 */
	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

	/**
	 * @param parents
	 *            the parents to set
	 */
	public void setParents(List<Parent> parents) {
		this.parents = parents;
	}

}
