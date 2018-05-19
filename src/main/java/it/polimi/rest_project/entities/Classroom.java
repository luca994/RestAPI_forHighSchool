package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Classroom {

	private String classroomId;
	private List<Student> students;
	private Map<String, Timetable> timetables;
	
	public Classroom() {
		students = new ArrayList<>();
		timetables = new HashMap<>();
		Integer random = new Random().nextInt();
		classroomId = random.toString();
	}

	public String getClassroomId() {
		return classroomId;
	}

	public List<Student> getStudents() {
		return students;
	}

	public Map<String, Timetable> getTimetables() {
		return timetables;
	}

	public void setClassroomId(String classroomId) {
		this.classroomId = classroomId;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void setTimetables(Map<String, Timetable> timetables) {
		this.timetables = timetables;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Classroom other = (Classroom) obj;
		if (classroomId == null) {
			if (other.classroomId != null)
				return false;
		} else if (!classroomId.equals(other.classroomId))
			return false;
		return true;
	}

}
