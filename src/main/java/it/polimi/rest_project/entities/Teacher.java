package it.polimi.rest_project.entities;

import java.util.Random;

import javax.persistence.Entity;

@Entity
public class Teacher extends User {

	public Teacher() {
		Integer random = new Random().nextInt();
		this.setUserId(random.toString());
	}

}
