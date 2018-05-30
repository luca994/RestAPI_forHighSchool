package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "Grades")
@JsonPropertyOrder({"subject", "mark", "teacher", "resources" })
public class Grade {

	@Id
	@JsonIgnore
	private String gradeId;
	@Column
	private String subject;
	@Column
	private float mark;
	@JoinColumn
	@JsonIgnoreProperties({ "resources", "dateOfBirth" })
	private Teacher teacher;
	@JoinColumn
	private List<Link> resources;

	public Grade() {
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z')
				.filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();
		this.gradeId = generator.generate(8);
		this.resources=new ArrayList<Link>();
	}

	public String getGradeId() {
		return gradeId;
	}

	public String getSubject() {
		return subject;
	}

	public float getMark() {
		return mark;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setMark(float mark) {
		this.mark = mark;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Link> getResources() {
		return resources;
	}

	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}
