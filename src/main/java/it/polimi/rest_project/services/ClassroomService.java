package it.polimi.rest_project.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.Lecture;
import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Student;
import it.polimi.rest_project.entities.Teacher;

public class ClassroomService {

	private EntityManager entityManager;

	public ClassroomService() {
		entityManager = Back2School.getEntityManager();
	}

	private boolean isAuthorized(Teacher teacher, String classroomId) {
		Query query = entityManager.createQuery("Select c.lectures from Classroom c");
		List<Lecture> lectures = query.getResultList();
		for (Lecture l : lectures)
			if (l.getTeacher().getUserId().equals(teacher.getUserId()))
				return true;
		return false;
	}

	public Classroom getClassroom(String userId, String classroomId) {
		boolean isAuthorized = false;
		Query queryAdmin = entityManager.createQuery("select a from Administrator a where a.userId=:userId");
		queryAdmin.setParameter("userId", userId);
		Query queryTeacher = entityManager.createQuery("select t from Teacher t where t.userId=:userId");
		queryTeacher.setParameter("userId", userId);
		if (queryAdmin.getResultList().size() == 1)
			isAuthorized = true;
		if (queryTeacher.getResultList().size() == 1)
			isAuthorized = isAuthorized(entityManager.find(Teacher.class, userId), classroomId);
		if (isAuthorized)
			return entityManager.find(Classroom.class, classroomId);
		return null;
	}

	public Response addStudentToClass(String userId, String classroomId, String studentId) {
		UserService userService = new StudentService();
		if (userService.isAdministrator(userId)) {
			try {
				Classroom targetClass = entityManager.find(Classroom.class, classroomId);
				if (!userService.isStudent(studentId))
					return Response.status(Status.BAD_REQUEST).build();
				Student targetStudent = entityManager.find(Student.class, studentId);
				targetClass.getStudents().add(targetStudent);
				entityManager.getTransaction().begin();
				entityManager.persist(targetClass);
				entityManager.getTransaction().commit();
				return Response.status(Status.OK).entity(targetClass).build();
			} catch (IllegalArgumentException e) {
				return Response.status(Status.NOT_FOUND).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	public Response addLectureToClass(String userId, String classroomId, String lectureId) {
		UserService userService = new StudentService();
		if (userService.isAdministrator(userId)) {
			try {
				Classroom targetClass = entityManager.find(Classroom.class, classroomId);
				Lecture targetLecture = entityManager.find(Lecture.class, lectureId);
				Query query = entityManager.createQuery("select c from Classroom c where :lecture in c.lectures");
				query.setParameter("lecture", targetLecture);
				if (query.getResultList().size() != 0)
					return Response.status(Status.BAD_REQUEST)
							.entity("this lecture is already associated to a classroom").build();
				targetClass.getLectures().add(targetLecture);
				entityManager.getTransaction().begin();
				entityManager.persist(targetClass);
				entityManager.getTransaction().commit();
				return Response.status(Status.OK).entity(targetClass).build();
			} catch (IllegalArgumentException e) {
				return Response.status(Status.NOT_FOUND).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	public Response createClassroom(String userId, String classroomId, String baseUri) {
		UserService userService = new StudentService();
		if (userService.isAdministrator(userId)) {
			Classroom newClassroom = new Classroom(classroomId);
			addResources(newClassroom, baseUri);
			entityManager.getTransaction().begin();
			entityManager.persist(newClassroom);
			entityManager.getTransaction().commit();
			return Response.status(Status.CREATED).entity(newClassroom).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	private void addResources(Classroom classrooms, String baseUri) {
		Link self = new Link(baseUri + "classrooms" + "/" + classrooms.getClassroomId(), "self");
		classrooms.getResources().add(self);
	}

	public List<Classroom> getClassrooms(String userId) {
		UserService userService = new UserService();
		if (userService.isAdministrator(userId))
			return entityManager.createQuery("Select c from Classroom c").getResultList();
		if (userService.isTeacher(userId)) {
			List<Classroom> allClassrooms = entityManager.createQuery("Select c from Classroom c").getResultList();
			List<Classroom> displayableClassroom = new ArrayList<Classroom>();
			for (Classroom c : allClassrooms)
				for (Lecture l : c.getLectures())
					if (l.getTeacher().getUserId().equals(userId))
						displayableClassroom.add(c);
			return displayableClassroom;
		}
		return null;
	}

	public Response deleteClassroom(String userId, String classroomId) {
		UserService userService = new UserService();
		Classroom toDelete = entityManager.find(Classroom.class, classroomId);
		if (userService.isAdministrator(userId)) {
			entityManager.getTransaction().begin();
			entityManager.remove(toDelete);
			entityManager.getTransaction().commit();
			return Response.status(Status.NO_CONTENT).entity("Deleted").build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

}
