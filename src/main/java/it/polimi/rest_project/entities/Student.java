package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

@Entity
public class Student extends User {

	@JoinColumn
	private List<Grade> grades;

	public Student() {
		grades = new ArrayList<Grade>();
		Integer random = new Random().nextInt();
		this.setUserId(random.toString());
	}

	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

}
