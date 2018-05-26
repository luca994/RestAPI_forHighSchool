package it.polimi.rest_project.services;

import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Query;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Student;

public class StudentService extends UserService {

	public StudentService() {
		super();
	}

	public Student getStudent(String userId, String studentId) {
		boolean isAuthorized = isAuthorized(userId, studentId);
		if (isAuthorized && isStudent(studentId))
			return entityManager.find(Student.class, studentId);
		return null;
	}

	private boolean isAuthorized(String userId, String studentId) {
		boolean isAuthorized = false;
		Query queryAdmin = entityManager.createQuery("select a from Administrator a where a.userId=:userId");
		queryAdmin.setParameter("userId", userId);
		Query queryParent = entityManager.createQuery("select p from Parent p where p.userId=:userId");
		queryAdmin.setParameter("userId", userId);
		if (queryAdmin.getResultList().size() == 1)
			isAuthorized = true;
		if (queryParent.getResultList().size() == 1) {
			Parent targetParent = entityManager.find(Parent.class, userId);
			for (Student s : targetParent.getStudents())
				if (s.getUserId().equals(studentId))
					isAuthorized = true;
		}
		return isAuthorized;
	}

	public Response updateData(String userId, String studentId, String name, String surname, Integer day, Integer month,
			Integer year) {
		if (isAuthorized(userId, studentId) && isStudent(studentId))
			return updateStudentData(studentId, name, surname, day, month, year);
		else
			return Response.status(Status.UNAUTHORIZED).build();
	}

	public Response updateStudentData(String id, String name, String surname, Integer day, Integer month,
			Integer year) {
		Student targetStudent = entityManager.find(Student.class, id);
		if (name != null)
			targetStudent.setName(name);
		if (surname != null)
			targetStudent.setSurname(surname);
		if (day != null && month != null && year != null)
			targetStudent.setDateOfBirth(new GregorianCalendar(year, month - 1, day));
		entityManager.getTransaction().begin();
		entityManager.persist(targetStudent);
		entityManager.getTransaction().commit();
		return Response.status(Status.OK).entity(targetStudent).build();
	}

	public Response createStudent(String userId, String name, String surname, Integer year, Integer month, Integer day,
			String password, String baseUri) {
		if (isAdministrator(userId)) {
			if (name == null || surname == null || day == null || month == null || year == null || password == null)
				return Response.status(Status.BAD_REQUEST).build();
			Student newStudent = new Student(name, surname, password, new GregorianCalendar(year, month - 1, day));
			addResources(newStudent, baseUri);
			entityManager.getTransaction().begin();
			entityManager.persist(newStudent);
			entityManager.getTransaction().commit();
			return Response.status(Status.CREATED).entity(newStudent).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	private void addResources(Student student, String baseUri) {
		Link self = new Link(baseUri + "students" + "/" + student.getUserId(), "self");
		student.getResources().add(self);
	}

	public List<Student> getStudentsInClassroom(String userId, String classroomId) {
		if (isAdministrator(userId)) {
			Query query = entityManager.createQuery("Select c.students from Classroom c where c.classroomId=:classId");
			query.setParameter("classId", classroomId);
			return query.getResultList();
		}
		return null;
	}

	public List<Student> getStudents(String userId) {
		if (isAdministrator(userId))
			return entityManager.createQuery("Select s from Student s").getResultList();
		if (isParent(userId))
			return entityManager.createQuery("Select p.students from Parent p").getResultList();
		return null;
	}
}
