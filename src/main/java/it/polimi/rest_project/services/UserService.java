package it.polimi.rest_project.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.PersonalData;
import it.polimi.rest_project.entities.Teacher;
import it.polimi.rest_project.entities.User;

public abstract class UserService {

	protected EntityManager entityManager;

	public UserService() {
		entityManager = Back2School.getEntityManager();
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

	public List<Teacher> getTeachers() {
		Query query = entityManager
				.createQuery("Select t.userId, t.personalData.name, t.personalData.surname from Teacher t");
		return query.getResultList();
	}

	public List<Parent> getParents() {
		Query query = entityManager
				.createQuery("Select p.userId, p.personalData.name, p.personalData.surname from Parent p");
		return query.getResultList();
	}
}
