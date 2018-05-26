package it.polimi.rest_project.entities;

import java.util.Calendar;

import javax.persistence.Entity;

@Entity
public class Administrator extends User {

	public Administrator() {
		super();
	}

	public Administrator(String name, String surname, String password, Calendar dateOfBirth) {
		super(name,surname,password,dateOfBirth);
	}
	
	public Administrator(String userId,String password) {
		super();
		this.setUserId(userId);
		this.setPassword(password);
	}

}
