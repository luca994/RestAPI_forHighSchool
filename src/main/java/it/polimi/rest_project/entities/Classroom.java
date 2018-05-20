package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;

@Entity
public class Classroom {

	@Id
	private String classroomId;
	@JoinTable
	private List<Student> students;

	public Classroom() {
		students = new ArrayList<>();
		Integer random = new Random().nextInt();
		classroomId = random.toString();
	}

	public String getClassroomId() {
		return classroomId;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setClassroomId(String classroomId) {
		this.classroomId = classroomId;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
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
