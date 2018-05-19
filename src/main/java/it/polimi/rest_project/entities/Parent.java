package it.polimi.rest_project.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Parent extends User {

	private List<Student> children;

	public Parent(PersonalData personalData) {
		this.setPersonalData(personalData);
	}

	public Parent() {
		children = new ArrayList<>();
		Integer random = new Random().nextInt();
		this.setUserId(random.toString());
	}

	public List<Student> getChildren() {
		return children;
	}

	public void setChildren(List<Student> children) {
		this.children = children;
	}

}
