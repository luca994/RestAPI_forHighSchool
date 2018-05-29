package it.polimi.rest_project.entities;

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

import org.apache.commons.text.RandomStringGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.polimi.rest_project.json.OptimizedDateSerializer;

@Entity
@Table(name = "Users")
@JsonPropertyOrder({ "userId", "name", "surname", "dateOfBirth", "resources" })
public abstract class User {

	@Id
	private String userId;
	@Column
	@JsonIgnore
	private byte[] password;
	@Column
	private String name;
	@Column
	private String surname;
	@Column
	@JsonSerialize(using = OptimizedDateSerializer.class)
	private Calendar dateOfBirth;
	@JoinColumn
	private List<Link> resources;

	public User() {
		char[][] range = { { 'a', 'z' }, { '0', '9' } };
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(range).build();
		this.userId = generator.generate(8);
		this.resources = new ArrayList<Link>();
		this.dateOfBirth = new GregorianCalendar();
	}

	public User(String name, String surname, String password, Calendar dateOfBirth) {
		char[][] range = { { 'a', 'z' }, { '0', '9' } };
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(range).build();
		this.userId = generator.generate(8);
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.resources = new ArrayList<Link>();
		setPassword(password);
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

	public Calendar getDateOfBirth() {
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

	public void setDateOfBirth(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Link> getResources() {
		return resources;
	}

	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

	public byte[] getPassword() {
		return password;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

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
