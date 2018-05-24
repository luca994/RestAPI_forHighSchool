package it.polimi.rest_project.services;

import java.time.DayOfWeek;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
		UserService userService = new AdministratorService();
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
		UserService userService = new AdministratorService();
		if (userService.isAdministrator(userId)) {
			if (teacherId == null || day == null || hour == null || subject == null
					|| !userService.isTeacher(teacherId))
				return Response.status(Status.BAD_REQUEST).build();
			Classroom targetClassroom = entityManager.find(Classroom.class, classroomId);
			Lecture newLecture = new Lecture();
			newLecture.setDay(DayOfWeek.valueOf(day));
			newLecture.setHour(Integer.parseInt(hour));
			newLecture.setSubject(subject);
			newLecture.setTeacher(entityManager.find(Teacher.class, teacherId));
			targetClassroom.getLectures().add(newLecture);
			addResources(newLecture, baseUri);
			entityManager.getTransaction().begin();
			entityManager.persist(newLecture);
			entityManager.persist(targetClassroom);
			entityManager.getTransaction().commit();
			return Response.status(Status.CREATED).entity(newLecture).build();
		} else
			return Response.status(Status.UNAUTHORIZED).build();

	}

	private void addResources(Lecture lecture, String baseUri) {
		Link self = new Link(baseUri + "/" + "lectures" + lecture.getId(), "self");
		lecture.getResources().add(self);
		entityManager.getTransaction().begin();
		entityManager.persist(self);
		entityManager.getTransaction().commit();
	}
}
