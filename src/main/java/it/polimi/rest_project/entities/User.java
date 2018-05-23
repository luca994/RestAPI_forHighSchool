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

	public User(String name, String surname, Date dateOfBirth) {
		Long random = new Random().nextLong();
		this.userId = Long.toUnsignedString(random);
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.resources = new ArrayList<Link>();
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the resources
	 */
	public List<Link> getResources() {
		return resources;
	}

	/**
	 * @param resources
	 *            the resources to set
	 */
	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}
