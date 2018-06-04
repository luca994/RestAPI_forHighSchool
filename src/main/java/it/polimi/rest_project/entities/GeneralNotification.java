package it.polimi.rest_project.entities;

import javax.persistence.Entity;

/**
 * General Notification entity
 * @author luca
 *
 */
@Entity
public class GeneralNotification extends Notification {

	
	private static final long serialVersionUID = -7632423189421950490L;

	/**
	 * Default constructor of General Notification
	 * 
	 */
	public GeneralNotification() {
		super();
	}
}
