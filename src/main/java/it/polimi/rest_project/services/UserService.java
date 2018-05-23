package it.polimi.rest_project.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.polimi.rest_project.entities.Administrator;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Student;
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
	public User getUser(String userId) {
		User targetUser = entityManager.find(User.class, userId);
		return targetUser;
	}

	public List<Parent> getParents() {
		Query query = entityManager.createQuery("Select p from Parent p");
		return query.getResultList();
	}

	public List<Teacher> getTeachers() {
		Query query = entityManager.createQuery("Select t from Teacher t");
		return query.getResultList();
	}

	public List<Student> getStudents() {
		Query query = entityManager.createQuery("Select s from Student s");
		return query.getResultList();
	}

	public List<Administrator> getAdmins() {
		Query query = entityManager.createQuery("Select a from Administrator a");
		return query.getResultList();
	}

	public boolean updateUserData(String id, String name, String surname, String day, String month, String year) {
		User targetUser = getUser(id);
		if (name != null)
			targetUser.setName(name);
		if (surname != null)
			targetUser.setSurname(surname);
		if (day != null && month != null && year != null)
			try {
				targetUser.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
			} catch (ParseException e) {
				return false;
			}
		entityManager.getTransaction().begin();
		entityManager.persist(targetUser);
		entityManager.getTransaction().commit();
		return true;
	}

}
