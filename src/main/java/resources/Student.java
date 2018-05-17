package resources;

import java.util.List;
import java.util.Map;

public class Student extends User {

	private Map<String,Float> grades;
	private List<Parent> parents;
	
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
