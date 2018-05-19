package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Student extends User {

	private Map<String,Float> grades;
	private List<Parent> parents;
	
	public Student() {
		grades = new HashMap<>();
		parents = new ArrayList<>();
		Integer random = new Random().nextInt();
		this.setUserId(random.toString());
	}
	
	public Map<String, Float> getGrades() {
		return grades;
	}
	public List<Parent> getParents() {
		return parents;
	}
	public void setGrades(Map<String, Float> grades) {
		this.grades = grades;
	}
	public void setParents(List<Parent> parents) {
		this.parents = parents;
	}
	
}
