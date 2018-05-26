package it.polimi.rest_project.entities;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	private User user;
	@JoinColumn
	private List<Link> resources;

	public Notification() {
		Long random = new Random().nextLong();
		this.id = Long.toUnsignedString(random);
		date = new GregorianCalendar();
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
