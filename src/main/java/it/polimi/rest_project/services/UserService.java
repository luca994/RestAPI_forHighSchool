package it.polimi.rest_project.services;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class UserService {

	protected EntityManager entityManager;

	public UserService() {
		entityManager = Back2School.getEntityManager();
	}


	public boolean isAdministrator(String userId) {
		Query queryAdmin = entityManager.createQuery("select a from Administrator a where a.userId=" + userId);
		if (queryAdmin.getResultList().size() == 1)
			return true;
		else
			return false;
	}
	
	public boolean isTeacher(String userId) {
		Query queryTeacher = entityManager.createQuery("select t from Teacher t where t.userId=" + userId);
		if (queryTeacher.getResultList().size() == 1)
			return true;
		else
			return false;
	}
	
	public boolean isParent(String userId) {
		Query queryParent = entityManager.createQuery("select p from Parent p where p.userId=" + userId);
		if (queryParent.getResultList().size() == 1)
			return true;
		else
			return false;
	}
	
	public boolean isStudent(String userId) {
		Query queryParent = entityManager.createQuery("select s from Student s where s.userId=" + userId);
		if (queryParent.getResultList().size() == 1)
			return true;
		else
			return false;
	}


}
