package it.polimi.rest_project.entities;

import java.util.Random;

import javax.persistence.Entity;

@Entity
public class Teacher extends User {

	public Teacher() {
		Long random = new Random().nextLong();
		this.setUserId(Long.toUnsignedString(random));
	}

}
