package resources;

import java.util.Date;

public class PersonalData {

	String name;
	String surname;
	String id;
	Date dateOfBirth;

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getId() {
		return id;
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

	public void setId(String id) {
		this.id = id;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
