package it.polimi.rest_project.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

@Entity
public class Parent extends User {

	@JoinColumn
	private List<Student> students;
	
	public Parent() {
		super();
	}

	/**
	 * @return the students
	 */
	public List<Student> getStudents() {
		return students;
	}

	/**
	 * @param students the students to set
	 */
	public void setStudents(List<Student> students) {
		this.students = students;
	}

}
