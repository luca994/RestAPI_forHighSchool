package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.polimi.rest_project.json.OptimizedDateSerializer;

@Entity
@Table(name = "Notifications")
@JsonPropertyOrder({ "date", "text", "user", "resources" })
public abstract class Notification {

	@Id
	@JsonIgnore
	private String id;
	@Column
	private String text;
	@Column
	@JsonSerialize(using = OptimizedDateSerializer.class)
	private Calendar date;
	@JoinColumn
	@JsonIgnoreProperties({ "userId", "dateOfBirth" })
	private User user;
	@JoinColumn
	private List<Link> resources;

	public Notification() {
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z')
				.filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();
		this.id = generator.generate(8);
		date = new GregorianCalendar();
		this.resources = new ArrayList<Link>();
	}

	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Calendar getDate() {
		return date;
	}

	public User getUser() {
		return user;
	}

	public List<Link> getResources() {
		return resources;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}
