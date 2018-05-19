package it.polimi.rest_project.services;

import javax.persistence.EntityManager;

public class ClassroomService {

	private EntityManager entityManager;
	
	public ClassroomService(EntityManager entityManager) {
		this.entityManager=entityManager;
	}
}
