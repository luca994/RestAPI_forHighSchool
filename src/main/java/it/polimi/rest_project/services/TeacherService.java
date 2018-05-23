package it.polimi.rest_project.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.Grade;
import it.polimi.rest_project.entities.Lecture;
import it.polimi.rest_project.entities.Student;
import it.polimi.rest_project.entities.Teacher;

public class TeacherService extends UserService {

	private AppointmentService appointmentService;
	private ClassroomService classroomService;
	private LectureService lectureService;

	public TeacherService() {
		appointmentService = new AppointmentService(entityManager);
		classroomService = new ClassroomService(entityManager);
		lectureService = new LectureService(entityManager);
	}

	/**
	 * returns the instance of the parent with that id
	 * 
	 * @param id
	 * @return
	 */
	public Teacher getTeacher(String id) {
		Teacher targetTeacher = entityManager.find(Teacher.class, id);
		return targetTeacher;
	}

	/**
	 * returns the classroom with the corresponding classroomId
	 * 
	 * @param classroomId
	 * @return
	 */
	public Classroom getClassroom(String classroomId) {
		return entityManager.find(Classroom.class, classroomId);
	}

	public Grade getGrade(String teacherId, String gradeId) {
		Grade targetGrade = entityManager.find(Grade.class, gradeId);
		if (!lectureService.getLecturesFromTeacherAndSubject(teacherId, targetGrade.getSubject()).isEmpty())
			return targetGrade;
		else
			return null;
	}

	/**
	 * verifies that the teacher teaches effectively the subject to that student and
	 * add the grade to the student
	 * 
	 * @param teacherId
	 * @param studentId
	 * @param subject
	 * @param grade
	 * @return
	 */
	public boolean addGrade(String teacherId, String studentId, String subject, float grade) {
		boolean authorization = false;
		List<Lecture> lectures = lectureService.getLecturesFromTeacherAndSubject(teacherId, subject);
		List<Classroom> classrooms = classroomService.getClassroomFromStudent(studentId);
		for (Classroom c : classrooms)
			for (Lecture l : lectures)
				if (c.getLectures().contains(l))
					authorization = true;
		if (!authorization)
			return false;
		Student targetStudent = entityManager.find(Student.class, studentId);
		Grade newGrade = new Grade(subject, grade);
		entityManager.getTransaction().begin();
		entityManager.persist(newGrade);
		targetStudent.getGrades().add(newGrade);
		entityManager.getTransaction().commit();
		return true;
	}

	/**
	 * verifies that the teacher teaches effectively the subject to that student and
	 * modify the grade to the student
	 * 
	 * @param teacherId
	 * @param studentId
	 * @param gradeId
	 * @param newGrade
	 * @return
	 */
	public boolean modifyGrade(String teacherId, String studentId, String gradeId, float newGrade) {
		boolean authorization = false;
		Grade targetGrade = entityManager.find(Grade.class, gradeId);
		List<Lecture> lectures = lectureService.getLecturesFromTeacherAndSubject(teacherId, targetGrade.getSubject());
		List<Classroom> classrooms = classroomService.getClassroomFromStudent(studentId);
		for (Classroom c : classrooms)
			for (Lecture l : lectures)
				if (c.getLectures().contains(l))
					authorization = true;
		if (!authorization)
			return false;
		entityManager.getTransaction().begin();
		targetGrade.setMark(newGrade);
		entityManager.getTransaction().commit();
		return true;
	}

	/**
	 * returns the appointment if the is an appointment involving the teacher
	 * 
	 * @param teacherId
	 * @param appointmentId
	 * @return
	 */
	public Appointment getTeacherAppointment(String teacherId, String appointmentId) {
		Appointment targetAppointment = entityManager.find(Appointment.class, appointmentId);
		if (appointmentService.getTeacherAppointments(teacherId).contains(targetAppointment))
			return targetAppointment;
		else
			return null;
	}

	/**
	 * returns all the appointments involving the teacher
	 * 
	 * @param teacherId
	 * @return
	 */
	public List<Appointment> getTeacherAppointments(String teacherId) {
		return appointmentService.getParentAppointments(teacherId);
	}

	/**
	 * 
	 * @param userId
	 * @param appointmentId
	 * @param date
	 * @return
	 */
	public boolean updateTeacherAppointment(String userId, String appointmentId, String day, String month,
			String year) {
		if (!appointmentService.checkIfAppointmentExistsForTeacher(userId, appointmentId))
			return false;
		else if (day != null && month != null && year != null)
			try {
				Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day);
				return appointmentService.updateAppointment(appointmentId, newDate);
			} catch (ParseException e) {
				return false;
			}
		else
			return false;
	}

	public boolean addTeacherAppointment(String parentId, String teacherId, String day, String month, String year,
			String uriInfo) {
		if (day != null && month != null && year != null)
			try {
				Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day);
				return appointmentService.addAppointment(parentId, teacherId, newDate, uriInfo);
			} catch (ParseException e) {
				return false;
			}
		else
			return false;

	}

	public boolean deleteTeacherAppointment(String userId, String appointmentId) {
		if (appointmentService.checkIfAppointmentExistsForTeacher(userId, appointmentId) == false)
			return false;
		else
			return appointmentService.deleteAppointment(appointmentId);
	}

}
