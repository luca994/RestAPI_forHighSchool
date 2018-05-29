package it.polimi.rest_project.entities;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.apache.commons.text.RandomStringGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "Lectures")
@JsonPropertyOrder({ "day", "hour", "subject", "teacher", "resources" })
public class Lecture {

	@Id
	@JsonIgnore
	private String id;
	@Column
	private DayOfWeek day;
	@Column
	private Integer hour;
	@JoinColumn
	@JsonIgnoreProperties({ "userId", "resources", "dateOfBirth" })
	private Teacher teacher;
	@Column
	private String subject;
	@JoinColumn
	private List<Link> resources;

	public Lecture() {
		char[][] range = { { 'a', 'z' }, { '0', '9' } };
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(range).build();
		this.id = generator.generate(8);
		this.resources = new ArrayList<Link>();
	}

	public String getId() {
		return id;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public Integer getHour() {
		return hour;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public String getSubject() {
		return subject;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<Link> getResources() {
		return resources;
	}

	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}
