package it.polimi.rest_project.entities;

import java.util.HashMap;
import java.util.Map;

public class Timetable {

	private Map<Integer,String> lessons;

	public Timetable() {
		lessons = new HashMap<>();
	}
	
	public Map<Integer, String> getLessons() {
		return lessons;
	}

	public void setLessons(Map<Integer, String> lessons) {
		this.lessons = lessons;
	}
	
}
