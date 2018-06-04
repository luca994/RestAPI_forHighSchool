package it.polimi.rest_project.entities;

import java.util.Calendar;

import javax.persistence.Entity;

@Entity
public class Teacher extends User {

	
	private static final long serialVersionUID = 2437572764439715603L;

	public Teacher() {
		super();
	}
	
	public Teacher(String name, String surname, String password, Calendar dateOfBirth) {
		super(name,surname,password,dateOfBirth);
	}

}
