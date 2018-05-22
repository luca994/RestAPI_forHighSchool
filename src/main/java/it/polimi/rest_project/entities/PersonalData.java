package it.polimi.rest_project.entities;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PersonalData")
public class PersonalData {

	@Id
	private String personalDataid;
	@Column
	private String name;
	@Column
	private String surname;
	@Column
	private Date dateOfBirth;

	public PersonalData() {
		Long random = new Random().nextLong();
		this.personalDataid = Long.toUnsignedString(random);
	}

	public PersonalData(String name, String surname, Date dateOfBirth) {
		Long random = new Random().nextLong();
		this.personalDataid = Long.toUnsignedString(random);
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPersonalDataid() {
		return personalDataid;
	}

	public void setPersonalDataid(String personalDataid) {
		this.personalDataid = personalDataid;
	}

}
