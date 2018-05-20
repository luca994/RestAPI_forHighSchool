package it.polimi.rest_project.entities;

import java.io.Serializable;
import java.util.Date;

public class PersonalData implements Serializable {


	private static final long serialVersionUID = -2265801665743184194L;
	private String name;
	private String surname;
	private Date dateOfBirth;

	public PersonalData() {
	}

	public PersonalData(String name, String surname, Date dateOfBirth) {
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

}
