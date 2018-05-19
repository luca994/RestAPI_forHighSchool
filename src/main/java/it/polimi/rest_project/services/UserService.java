package it.polimi.rest_project.services;

import javax.persistence.EntityManager;

import it.polimi.rest_project.entities.PersonalData;
import it.polimi.rest_project.entities.User;

public abstract class UserService {

	protected EntityManager entityManager;

	public UserService() {
		entityManager = Back2School.emfactory.createEntityManager();
	}

	/**
	 * retrieves and returns personalData of a user by searching them to the db
	 * 
	 * @param userId
	 * @return
	 */
	public PersonalData getPersonalData(String userId) {
		User targetUser = entityManager.find(User.class, userId);
		return targetUser.getPersonalData();
	}

	/**
	 * takes userId and personalData and writes the not-null information to the db
	 * 
	 * @param userId
	 * @param personalData
	 */
	public void updatePersonalData(String userId, PersonalData personalData) {
		User userToUpdate;
		PersonalData personalDataToUpdate = getPersonalData(userId);
		if (personalData.getName() != null)
			personalDataToUpdate.setName(personalData.getName());
		if (personalData.getSurname() != null)
			personalDataToUpdate.setSurname(personalData.getSurname());
		if (personalData.getDateOfBirth() != null)
			personalDataToUpdate.setDateOfBirth(personalData.getDateOfBirth());
		entityManager.getTransaction().begin();
		userToUpdate = entityManager.find(User.class, userId);
		userToUpdate.setPersonalData(personalDataToUpdate);
		entityManager.persist(userToUpdate);
		entityManager.getTransaction().commit();
	}
}
