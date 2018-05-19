package it.polimi.rest_project.services;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.Grade;
import it.polimi.rest_project.entities.Student;
import it.polimi.rest_project.entities.Teacher;

public class TeacherService extends UserService {

	private AppointmentService appointmentService;

	public TeacherService() {
		appointmentService = new AppointmentService(entityManager);
	}

	private boolean checkIfAppointmentExists(String userId, String appointmentId) {
		Query query = entityManager.createQuery("Select a from Appointment a where a.parent=:param");
		query.setParameter("param", entityManager.find(Teacher.class, userId));
		if (query.getResultList().size() != 1)
			return false;
		else
			return true;
	}

	public List<Classroom> getClassrooms(String teacherId) {
		Query query = entityManager.createQuery("Select l.classRoom from Lecture l where l.teacher=:param");
		query.setParameter("param", entityManager.find(Teacher.class, teacherId));
		return query.getResultList();
	}

	public Classroom getClassroom(String classroomId) {
		return entityManager.find(Classroom.class, classroomId);
	}

	public boolean addGrade(String teacherId, String studentId, String subject, float grade) {
		boolean authorization = false;
		Query query = entityManager.createQuery(
				"Select l.classroom.students from Lecture l where l.teacher=:param1 and l.subject=:param2");
		query.setParameter("param1", entityManager.find(Teacher.class, teacherId));
		query.setParameter("param2", subject);
		List<Student> students = query.getResultList();
		for (Student s : students)
			if (s.getUserId() == studentId)
				authorization = true;
		if (!authorization)
			return false;
		Grade newGrade = new Grade(subject, grade);
		entityManager.getTransaction().begin();
		entityManager.persist(newGrade);
		entityManager.getTransaction().commit();
		return true;
	}

	public boolean modifyGrade(String teacherId, String studentId, String gradeId, float newGrade) {
		boolean authorization = false;
		Grade targetGrade = entityManager.find(Grade.class, gradeId);
		Query query = entityManager.createQuery(
				"Select l.classroom.students from Lecture l where l.teacher=:param1 and l.subject=:param2");
		query.setParameter("param1", entityManager.find(Teacher.class, teacherId));
		query.setParameter("param2", targetGrade.getSubject());
		List<Student> students = query.getResultList();
		for (Student s : students)
			if (s.getUserId() == studentId)
				authorization = true;
		if (!authorization)
			return false;
		entityManager.getTransaction().begin();
		targetGrade.setMark(newGrade);
		entityManager.getTransaction().commit();
		return true;

	}

	public List<Appointment> getTeacherAppointments(String teacherId) {
		return appointmentService.getAppointments(teacherId);
	}

	public boolean updateTeacherAppointment(String userId, String appointmentId, Date date) {
		if (checkIfAppointmentExists(userId, appointmentId) == false)
			return false;
		else
			return appointmentService.updateAppointment(appointmentId, date);
	}

	public boolean addTeacherAppointment(String userId, String teacherId, Date date) {
		return appointmentService.addAppointment(userId, teacherId, date);

	}

	public boolean deleteTeacherAppointment(String userId, String appointmentId) {
		if (checkIfAppointmentExists(userId, appointmentId) == false)
			return false;
		else
			return appointmentService.deleteAppointment(appointmentId);
	}

}
