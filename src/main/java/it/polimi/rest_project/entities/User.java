package it.polimi.rest_project.entities;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.polimi.rest_project.json.OptimizedDateSerializer;

@Entity
@Table(name = "Users")
@JsonPropertyOrder({ "userId", "name", "surname", "dateOfBirth", "resources" })
public abstract class User implements Serializable {

	
	private static final long serialVersionUID = 6780293194341869904L;
	
	/** the id of the user */
	@Id
	private String userId;
	
	/** the password of the user */
	@Column
	@JsonIgnore
	private byte[] password;
	
	/** the name of the user */
	@Column
	private String name;
	
	/** the surname of the user */
	@Column
	private String surname;
	
	/** the date of birth of the user */
	@Column
	@JsonSerialize(using = OptimizedDateSerializer.class)
	private Calendar dateOfBirth;
	
	/** the resources of the user */
	@JoinColumn
	private List<Link> resources;

	/**
	 * Default constructor for the user
	 */
	public User() {
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z')
				.filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();
		this.userId = generator.generate(8);
		this.resources = new ArrayList<Link>();
		this.dateOfBirth = new GregorianCalendar();
	}

	public User(String name, String surname, String password, Calendar dateOfBirth) {
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z')
				.filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();
		this.userId = generator.generate(8);
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.resources = new ArrayList<Link>();
		setPassword(password);
	}

	/**
	 * @return the userPassowrd
	 */
	public byte[] getPassword() {
		return password;
	}

	/**
	 * Set the password as the hash of the password taken as input
	 * 
	 * @param password
	 *            the user password
	 */
	public void setPassword(String password) {
		try {
			byte[] plainPsw = password.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashedPsw = md.digest(plainPsw);
			this.password = hashedPsw;
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			this.password = null;
		}
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
	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @return the resources
	 */
	public List<Link> getResources() {
		return resources;
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
	public void setDateOfBirth(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @param resources
	 *            the resources to set
	 */
	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
