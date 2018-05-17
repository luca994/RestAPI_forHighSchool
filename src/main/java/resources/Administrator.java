package resources;

import java.util.List;

public class Administrator extends User {

	private List<Student> students;
	private List<Classroom> classrooms;
	private List<Teacher> teachers;
	private List<Parent> parents;

	public List<Student> getStudents() {
		return students;
	}

	public List<Classroom> getClassrooms() {
		return classrooms;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public List<Parent> getParents() {
		return parents;
	}


	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void setClassrooms(List<Classroom> classrooms) {
		this.classrooms = classrooms;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public void setParents(List<Parent> parents) {
		this.parents = parents;
	}

}
