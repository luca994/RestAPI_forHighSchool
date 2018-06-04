package it.polimi.rest_project.entities;

import java.util.Calendar;

import javax.persistence.Entity;

/**
 * The Administrator user
 * 
 *
 */
@Entity
public class Administrator extends User {

	private static final long serialVersionUID = 1863139377764230771L;

	/**
	 * Default constructor that calls the constructor of the superclass User
	 */
	public Administrator() {
		super();
	}

	/**
	 * Constructor for Administrator
	 * 
	 * @param name
	 *            the name of the new Administrator
	 * @param surname
	 *            the surname of the new Administrator
	 * @param password
	 *            the password for the new Administrator
	 * @param dateOfBirth
	 *            the date of birth of the new Administrator
	 */
	public Administrator(String name, String surname, String password, Calendar dateOfBirth) {
		super(name, surname, password, dateOfBirth);
	}

	/**
	 * Constructor for Administrator
	 * 
	 * @param userId
	 *            the userId of the new Administrator
	 * @param password
	 *            the password for the new Administrator
	 */
	public Administrator(String userId, String password) {
		super();
		this.setUserId(userId);
		this.setPassword(password);
	}

}
