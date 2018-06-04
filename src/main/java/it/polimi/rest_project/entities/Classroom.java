package it.polimi.rest_project.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The classroom entity
 *
 */
@Entity
@Table(name = "Classrooms")
@JsonPropertyOrder({ "classroomId", "students", "lectures", "resources" })
public class Classroom implements Serializable {

	private static final long serialVersionUID = -1812632198502625460L;

	/** the id of the classroom */
	@Id
	private String classroomId;

	/** the list of students enrolled in the class */
	@JoinTable(name = "Classroom_and_students")
	@JsonIgnoreProperties({ "userId", "grades", "dateOfBirth" })
	private List<Student> students;

	/** the list of lectures in the classroom */
	@JoinColumn
	private List<Lecture> lectures;

	/** the resources for the appointment */
	@JoinColumn
	private List<Link> resources;

	/**
	 * Default constructor for classroom that generates a random 8 characters id
	 */
	public Classroom() {
		students = new ArrayList<Student>();
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z')
				.filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();
		this.classroomId = generator.generate(8);
		this.resources = new ArrayList<Link>();
	}

	/**
	 * Constructor for classroom that construct a class with the specified id
	 */
	public Classroom(String id) {
		students = new ArrayList<Student>();
		this.resources = new ArrayList<Link>();
		this.classroomId = id;
	}

	/**
	 * @return the classroomId
	 */
	public String getClassroomId() {
		return classroomId;
	}

	/**
	 * @return the students
	 */
	public List<Student> getStudents() {
		return students;
	}

	/**
	 * @return the lectures
	 */
	public List<Lecture> getLectures() {
		return lectures;
	}

	/**
	 * @return the resources
	 */
	public List<Link> getResources() {
		return resources;
	}

	/**
	 * @param classroomId
	 *            the classroomId to set
	 */
	public void setClassroomId(String classroomId) {
		this.classroomId = classroomId;
	}

	/**
	 * @param students
	 *            the students to set
	 */
	public void setStudents(List<Student> students) {
		this.students = students;
	}

	/**
	 * @param lectures
	 *            the lectures to set
	 */
	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	/**
	 * @param resources
	 *            the resources to set
	 */
	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}