package it.polimi.rest_project.entities;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Notifications")
public abstract class Notification {
	@Id
	private String id;
	@Column
	private String text;
	@Column
	private Date date;
	@JoinColumn
	private User user;
	@JoinColumn
	private List<Link> resources;

	public Notification() {
		Long random = new Random().nextLong();
		this.id = Long.toUnsignedString(random);
		date = new Date();
	}

	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}

	public User getUser() {
		return user;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Link> getResources() {
		return resources;
	}

	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}
