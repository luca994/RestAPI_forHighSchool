package it.polimi.rest_project.services;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.entities.Grade;
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
		UserService userService = new AdministratorService();
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

	public Response createGrade(String userId, String studentId, String subject, String mark,String baseUri) {
		UserService userService = new AdministratorService();
		if (subject == null || mark == null || studentId == null)
			return Response.status(Status.BAD_REQUEST).build();
		if (userService.isTeacher(userId) && userService.isStudent(studentId)) {
			Student targetStudent = entityManager.find(Student.class, studentId);
			Query query = entityManager.createQuery("Select c.lectures from Classroom c where :student in c.students");
			query.setParameter("student", targetStudent);
			if (query.getResultList().size() >= 1) {
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
				return Response.status(Status.CREATED).entity(newGrade).build();
			} else
				return Response.status(Status.UNAUTHORIZED).build();
		} else
			return Response.status(Status.UNAUTHORIZED).build();
	}

	public Response updateGrade(String userId, String gradeId, String mark) {
		UserService userService = new AdministratorService();
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

	private void addResources(Grade grade,String baseUri) {
		Link self = new Link(baseUri+"/"+"grades"+grade.getGradeId(),"self");
		grade.getResources().add(self);
		entityManager.getTransaction().begin();
		entityManager.persist(self);
		entityManager.getTransaction().commit();
	}
	
}
