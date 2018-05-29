package it.polimi.rest_project.services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.polimi.rest_project.application.Back2School;
import it.polimi.rest_project.entities.Administrator;

public class UserService {

	protected EntityManager entityManager;

	public UserService() {
		entityManager = Back2School.getEntityManager();
	}

	public void createFirstAdmin() {
		if (entityManager.createQuery("Select a from Administrator a").getResultList().size() == 0) {
			Administrator admin = new Administrator("1", "admin");
			entityManager.getTransaction().begin();
			entityManager.persist(admin);
			entityManager.getTransaction().commit();
		} else
			return;
	}

	public boolean verifyLogin(String userId, String password) {
		byte[] hashedPsw;
		try {
			hashedPsw = hashPassword(password);
			Query query = entityManager
					.createQuery("Select u from User u where u.userId=:user and u.password=:password");
			query.setParameter("user", userId);
			query.setParameter("password", hashedPsw);
			if (query.getResultList().size() == 1)
				return true;
			else
				return false;
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			return false;
		}
	}

	private byte[] hashPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] plainPsw = password.getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(plainPsw);
	}

	public boolean isAdministrator(String userId) {
		Query queryAdmin = entityManager.createQuery("select a from Administrator a where a.userId=:userId");
		queryAdmin.setParameter("userId", userId);
		if (queryAdmin.getResultList().size() == 1)
			return true;
		else
			return false;
	}

	public boolean isTeacher(String userId) {
		Query queryTeacher = entityManager.createQuery("select t from Teacher t where t.userId=:userId");
		queryTeacher.setParameter("userId", userId);
		if (queryTeacher.getResultList().size() == 1)
			return true;
		else
			return false;
	}

	public boolean isParent(String userId) {
		Query queryParent = entityManager.createQuery("select p from Parent p where p.userId=:userId");
		queryParent.setParameter("userId", userId);
		if (queryParent.getResultList().size() == 1)
			return true;
		else
			return false;
	}

	public boolean isStudent(String userId) {
		Query queryStudent = entityManager.createQuery("select s from Student s where s.userId=:userId");
		queryStudent.setParameter("userId", userId);
		if (queryStudent.getResultList().size() == 1)
			return true;
		else
			return false;
	}

}
