package resources;

import java.util.List;
import java.util.Map;

public class Classroom {

	private String classroomId;
	private List<Student> students;
	private Map<String, Timetable> timetables;

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

}
