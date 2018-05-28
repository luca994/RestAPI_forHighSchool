package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "Classrooms")
@JsonPropertyOrder({ "classroomId","students", "lectures", "resources" })
public class Classroom {

	@Id
	private String classroomId;
	@JoinTable(name = "Classroom_and_students")
	@JsonIgnoreProperties({ "userId", "grades", "dateOfBirth" })
	private List<Student> students;
	@JoinColumn
	private List<Lecture> lectures;
	@JoinColumn
	private List<Link> resources;

	public Classroom() {
		students = new ArrayList<Student>();
		Long random = new Random().nextLong();
		this.classroomId = Long.toUnsignedString(random);
		this.resources = new ArrayList<Link>();
	}

	public Classroom(String id) {
		students = new ArrayList<Student>();
		this.resources = new ArrayList<Link>();
		this.classroomId = id;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public String getClassroomId() {
		return classroomId;
	}

	public void setClassroomId(String classroomId) {
		this.classroomId = classroomId;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	public List<Link> getResources() {
		return resources;
	}

	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}