package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

@Entity
@Table(name = "Classrooms")
public class Classroom {

	@Id
	private String classroomId;

	@JoinTable(name = "Classroom_and_students")
	private List<Student> students;

	@JoinColumn
	private List<Lecture> lectures;

	public Classroom() {
		students = new ArrayList<Student>();
		Long random = new Random().nextLong();
		this.classroomId = Long.toUnsignedString(random);
	}

	/**
	 * @return the students
	 */
	public List<Student> getStudents() {
		return students;
	}

	/**
	 * @param students
	 *            the students to set
	 */
	public void setStudents(List<Student> students) {
		this.students = students;
	}

	/**
	 * @return the classroomId
	 */
	public String getClassroomId() {
		return classroomId;
	}

	/**
	 * @param classroomId
	 *            the classroomId to set
	 */
	public void setClassroomId(String classroomId) {
		this.classroomId = classroomId;
	}

	/**
	 * @return the lectures
	 */
	public List<Lecture> getLectures() {
		return lectures;
	}

	/**
	 * @param lectures
	 *            the lectures to set
	 */
	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

}
