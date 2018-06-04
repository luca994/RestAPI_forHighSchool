package it.polimi.rest_project.services;

import java.time.DayOfWeek;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.application.Back2School;
import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.Lecture;
import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Teacher;

public class LectureService {

	private EntityManager entityManager;

	public LectureService() {
		entityManager = Back2School.getEntityManager();
	}

	public boolean isAuthorized(String userId, String lectureId) {
		UserService userService = new UserService();
		if (userService.isAdministrator(userId) || userService.isTeacher(userId))
			return true;
		else
			return false;
	}

	public Lecture getLecture(String userId, String lectureId) {
		if (isAuthorized(userId, lectureId))
			return entityManager.find(Lecture.class, lectureId);
		return null;
	}

	public Response createLecture(String userId, String classroomId, String teacherId, String day, String hour,
			String subject, String baseUri) {
		UserService userService = new UserService();
		if (userService.isAdministrator(userId)) {
			if (teacherId == null || day == null || hour == null || subject == null
					|| !userService.isTeacher(teacherId))
				return Response.status(Status.BAD_REQUEST).build();
			Classroom targetClassroom = entityManager.find(Classroom.class, classroomId);
			Lecture newLecture = new Lecture();
			newLecture.setDay(DayOfWeek.valueOf(day.toUpperCase()));
			newLecture.setHour(Integer.parseInt(hour));
			newLecture.setSubject(subject);
			newLecture.setTeacher(entityManager.find(Teacher.class, teacherId));
			targetClassroom.getLectures().add(newLecture);
			addResources(newLecture, baseUri);
			entityManager.getTransaction().begin();
			entityManager.persist(newLecture);
			entityManager.persist(targetClassroom);
			entityManager.getTransaction().commit();
			return Response.created(newLecture.getResources().get(0).getHref()).entity(newLecture).build();
		} else
			return Response.status(Status.UNAUTHORIZED).build();

	}

	/**
	 * Adds the accessible resources to the entity
	 */
	private void addResources(Lecture lecture, String baseUri) {
		Link self = new Link(baseUri + "/" + lecture.getId(), "self");
		lecture.getResources().add(self);
	}

	public Response deleteLecture(String userId, String lectureId) {
		UserService userService = new UserService();
		if (userService.isAdministrator(userId)) {
			Lecture toDelete = entityManager.find(Lecture.class, lectureId);
			Query query = entityManager.createQuery("select c from Classroom c where :lecture in c.lectures");
			query.setParameter("lecture", toDelete);
			if (query.getResultList().size() == 1) {
				Classroom classroom = (Classroom) query.getResultList().get(0);
				classroom.getLectures().remove(toDelete);
				entityManager.getTransaction().begin();
				entityManager.persist(classroom);
				entityManager.getTransaction().commit();
			}
			entityManager.getTransaction().begin();
			entityManager.remove(toDelete);
			entityManager.getTransaction().commit();
			return Response.status(Status.NO_CONTENT).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
}
