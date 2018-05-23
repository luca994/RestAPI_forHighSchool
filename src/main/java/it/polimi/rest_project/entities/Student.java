package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

@Entity
public class Student extends User {

	@JoinColumn
	private List<Parent> parents;
	@JoinColumn
	private List<Grade> grades;

	public Student() {
		super();
		grades = new ArrayList<Grade>();
	}

	/**
	 * @return the parents
	 */
	public List<Parent> getParents() {
		return parents;
	}

	/**
	 * @return the grades
	 */
	public List<Grade> getGrades() {
		return grades;
	}

	/**
	 * @param parents
	 *            the parents to set
	 */
	public void setParents(List<Parent> parents) {
		this.parents = parents;
	}

	/**
	 * @param grades
	 *            the grades to set
	 */
	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

}
