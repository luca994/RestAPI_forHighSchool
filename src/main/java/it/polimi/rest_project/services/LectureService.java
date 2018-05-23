package it.polimi.rest_project.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.polimi.rest_project.entities.Lecture;
import it.polimi.rest_project.entities.Teacher;

public class LectureService {

	private EntityManager entityManager;

	public LectureService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Lecture> getLectures() {
		Query query = entityManager.createQuery("Select l from Lecture l");
		return query.getResultList();
	}

	/**
	 * returns the list of lectures in which the teacher teachs the specific subject
	 * 
	 * @param teacherId
	 * @param subject
	 * @return
	 */
	public List<Lecture> getLecturesFromTeacherAndSubject(String teacherId, String subject) {
		Query q1 = entityManager.createQuery("Select l from Lecture l where l.teacher=:param1 and l.subject=:param2");
		q1.setParameter("param1", entityManager.find(Teacher.class, teacherId));
		q1.setParameter("param2", subject);
		return q1.getResultList();
	}

	/**
	 * returns the list of lecture in which the teacher teaches
	 * 
	 * @param teacherId
	 * @return
	 */
	public List<Lecture> getLecturesFromTeacher(String teacherId) {
		Query q1 = entityManager.createQuery("Select l from Lecture l where l.teacher=:param1");
		q1.setParameter("param1", entityManager.find(Teacher.class, teacherId));
		return q1.getResultList();
	}
}
