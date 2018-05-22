package it.polimi.rest_project.entities;

import java.util.Random;

import javax.persistence.Entity;

@Entity
public class Administrator extends User {

	public Administrator() {
		Long random = new Random().nextLong();
		this.setUserId(Long.toUnsignedString(random));
	}
	
	public Administrator(String userId) {
		this.setUserId(userId);
	}

}
