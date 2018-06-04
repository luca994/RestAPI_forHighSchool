package it.polimi.rest_project.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.application.Back2School;
import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.Grade;
import it.polimi.rest_project.entities.Lecture;
import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Student;
import it.polimi.rest_project.entities.Teacher;

public class GradeService {

	private EntityManager entityManager;

	public GradeService() {
		entityManager = Back2School.getEntityManager();
	}

	public boolean isAuthorized(String userId, String gradeId) {
		UserService userService = new UserService();
		Grade targetGrade = entityManager.find(Grade.class, gradeId);
		if (userService.isParent(userId)) {
			Parent targetParent = entityManager.find(Parent.class, userId);
			for (Student s : targetParent.getStudents())
				for (Grade g : s.getGrades())
					if (g.getGradeId().equals(gradeId))
						return true;
		}
		if (userService.isTeacher(userId) && targetGrade.getTeacher().getUserId().equals(userId))
			return true;
		return false;
	}

	public Grade getGrade(String userId, String gradeId) {
		if (isAuthorized(userId, gradeId))
			return entityManager.find(Grade.class, gradeId);
		return null;
	}

	private boolean isTeachingTo(String userId, String studentId) {
		Query query = entityManager.createQuery("Select c from Classroom c");
		List<Classroom> classrooms = query.getResultList();
		Student targetStudent = entityManager.find(Student.class, studentId);
		for (Classroom c : classrooms) {
			if (c.getStudents().contains(targetStudent))
				for (Lecture l : c.getLectures())
					if (l.getTeacher().getUserId().equals(userId))
						return true;
		}
		return false;
	}

	public Response createGrade(String userId, String studentId, String subject, String mark, String baseUri) {
		UserService userService = new UserService();
		if (subject == null || mark == null || studentId == null)
			return Response.status(Status.BAD_REQUEST).build();
		if (userService.isTeacher(userId) && userService.isStudent(studentId)) {
			Student targetStudent = entityManager.find(Student.class, studentId);
			if (isTeachingTo(userId, studentId)) {
				Grade newGrade = new Grade();
				newGrade.setSubject(subject);
				newGrade.setMark(Float.parseFloat(mark));
				newGrade.setTeacher(entityManager.find(Teacher.class, userId));
				targetStudent.getGrades().add(newGrade);
				addResources(newGrade, baseUri);
				entityManager.getTransaction().begin();
				entityManager.persist(newGrade);
				entityManager.persist(targetStudent);
				entityManager.getTransaction().commit();
				return Response.created(newGrade.getResources().get(0).getHref()).entity(newGrade).build();
			} else
				return Response.status(Status.UNAUTHORIZED).build();
		} else
			return Response.status(Status.UNAUTHORIZED).build();
	}

	public Response updateGrade(String userId, String gradeId, String mark) {
		UserService userService = new UserService();
		if (gradeId == null || mark == null)
			return Response.status(Status.BAD_REQUEST).build();
		if (userService.isTeacher(userId)) {
			Grade targetGrade = entityManager.find(Grade.class, gradeId);
			if (targetGrade.getTeacher().getUserId().equals(userId)) {
				targetGrade.setMark(Float.parseFloat(mark));
				entityManager.getTransaction().begin();
				entityManager.persist(targetGrade);
				entityManager.getTransaction().commit();
				return Response.status(Status.OK).entity(targetGrade).build();
			} else
				return Response.status(Status.UNAUTHORIZED).build();
		} else
			return Response.status(Status.UNAUTHORIZED).build();
	}

	/**
	 * Adds the accessible resources to the entity
	 */
	private void addResources(Grade grade, String baseUri) {
		Link self = new Link(baseUri + "/" + grade.getGradeId(), "self");
		grade.getResources().add(self);
	}

	public List<Grade> getGrades(String userId) {
		UserService userService = new UserService();
		if (userService.isParent(userId)) {
			List<Grade> displayableGrades = new ArrayList<Grade>();
			Parent targetParent = entityManager.find(Parent.class, userId);
			for (Student s : targetParent.getStudents())
				displayableGrades.addAll(s.getGrades());
			return displayableGrades;
		}
		if (userService.isTeacher(userId)) {
			Query query = entityManager.createQuery("Select g from Grade g where g.teacher.userId=:userId");
			query.setParameter("userId", userId);
			return query.getResultList();
		}
		return null;
	}

	public Response deleteGrade(String userId, String gradeId) {
		UserService userService = new UserService();
		Grade toDelete = entityManager.find(Grade.class, gradeId);
		if (userService.isTeacher(userId) && toDelete.getTeacher().getUserId().equals(userId)) {
			entityManager.getTransaction().begin();
			entityManager.remove(toDelete);
			entityManager.getTransaction().commit();
			return Response.status(Status.NO_CONTENT).entity("Deleted").build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

}
