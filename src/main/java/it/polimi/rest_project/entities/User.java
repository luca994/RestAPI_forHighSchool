package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public abstract class User {

	@Id
	private String userId;
	@Column
	private String name;
	@Column
	private String surname;
	@Column
	private Date dateOfBirth;
	@JoinColumn
	private List<Link> resources;

	public User() {
		Long random = new Random().nextLong();
		this.userId = Long.toUnsignedString(random);
		this.resources = new ArrayList<Link>();
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Link> getResources() {
		return resources;
	}

	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}
