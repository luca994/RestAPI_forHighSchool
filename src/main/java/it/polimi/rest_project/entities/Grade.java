package it.polimi.rest_project.entities;

import java.io.Serializable;
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

/**
 * The grade entity
 *
 */
@Entity
@Table(name = "Grades")
@JsonPropertyOrder({ "subject", "mark", "teacher", "resources" })
public class Grade implements Serializable {

	private static final long serialVersionUID = -910093429329235622L;

	/** the id of the grade */
	@Id
	@JsonIgnore
	private String gradeId;

	/** the subject related to the grade */
	@Column
	private String subject;

	/** the value of the grade */
	@Column
	private float mark;

	/** the teacher who issued the grade */
	@JoinColumn
	@JsonIgnoreProperties({ "resources", "dateOfBirth" })
	private Teacher teacher;

	/** the resources for the grade */
	@JoinColumn
	private List<Link> resources;

	public Grade() {
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z')
				.filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();
		this.gradeId = generator.generate(8);
		this.resources = new ArrayList<Link>();
	}

	/**
	 * @return the gradeId
	 */
	public String getGradeId() {
		return gradeId;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return the mark
	 */
	public float getMark() {
		return mark;
	}

	/**
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @return the resources
	 */
	public List<Link> getResources() {
		return resources;
	}

	/**
	 * @param gradeId
	 *            the gradeId to set
	 */
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @param mark
	 *            the mark to set
	 */
	public void setMark(float mark) {
		this.mark = mark;
	}

	/**
	 * @param teacher
	 *            the teacher to set
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * @param resources
	 *            the resources to set
	 */
	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}
