package it.polimi.rest_project.entities;

import java.util.Calendar;

import javax.persistence.Entity;

@Entity
public class Teacher extends User {

	public Teacher() {
		super();
	}
	
	public Teacher(String name, String surname, String password, Calendar dateOfBirth) {
		super(name,surname,password,dateOfBirth);
	}

}
