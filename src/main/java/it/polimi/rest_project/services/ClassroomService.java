package it.polimi.rest_project.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.polimi.rest_project.entities.Classroom;

public class ClassroomService {

	private EntityManager entityManager;

	public ClassroomService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Classroom> getClassrooms() {
		Query query = entityManager.createQuery("Select c.classroomId from Classroom c");
		return query.getResultList();
	}

	public Classroom getClassroom(String classroomId) {
		Query query = entityManager.createQuery("Select c from Classroom c where c.classroomId=" + classroomId);
		return (Classroom) query.getSingleResult();
	}
}
