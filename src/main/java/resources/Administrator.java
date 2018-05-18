package resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Administrator extends User {

	private List<Student> students;
	private List<Classroom> classrooms;
	private List<Teacher> teachers;
	private List<Parent> parents;
	
	public Administrator() {
		students = new ArrayList<>();
		classrooms = new ArrayList<>();
		teachers = new ArrayList<>();
		parents = new ArrayList<>();
		Integer random = new Random().nextInt();
		this.userId = random.toString();
	}

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
