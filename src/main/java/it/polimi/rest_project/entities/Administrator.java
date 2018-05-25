package it.polimi.rest_project.entities;

import javax.persistence.Entity;

@Entity
public class Administrator extends User {

	public Administrator() {
		super();
	}

	public Administrator(String userId,String password) {
		super();
		this.setUserId(userId);
		this.setPassword(password);
	}

}
