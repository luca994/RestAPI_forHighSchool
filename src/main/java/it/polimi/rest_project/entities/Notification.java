package it.polimi.rest_project.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Notification {
	@Id
	private String id;
	@Column
	private String text;
	@Column
	private Date date;
	@JoinColumn
	private User user;

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}

	public User getUser() {
		return user;
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

}
