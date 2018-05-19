package it.polimi.rest_project.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.polimi.rest_project.entities.Administrator;
import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.Notification;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Payment;
import it.polimi.rest_project.entities.PersonalData;
import it.polimi.rest_project.entities.Student;
import it.polimi.rest_project.entities.Teacher;
import it.polimi.rest_project.entities.User;

public class AdministratorService extends UserService{
	
	private ClassroomService classroomService;
	
	public AdministratorService() {
		super();
		classroomService = new ClassroomService(entityManager);
	}
	
	public List<Classroom> getAdminClassrooms(){
		return classroomService.getClassrooms();
	}
	
	public Classroom getAdminClassroom(String classroomId) {
		return classroomService.getClassroom(classroomId);
	}
	
	public List<Parent> getAdminParents(){
		return getParents();
	}
	
	public boolean createStudent(String name, String surname, String day, String month, String year) {
		if (name == null || surname == null)
			return false;
		Student studentToAdd = new Student();
		PersonalData pdat = new PersonalData();
		pdat.setName(name);
		pdat.setSurname(surname);
		try {
			pdat.setDateOfBirth(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return false;
		}
		studentToAdd.setPersonalData(pdat);
		entityManager.getTransaction().begin();
		entityManager.persist(studentToAdd);
		entityManager.getTransaction().commit();
		return true;
	}
	
	public boolean addStudentToParent(String parentId, String studentId) {
		Parent targetParent = entityManager.find(Parent.class, parentId);
		Student targetStudent = entityManager.find(Student.class, studentId);
		if(targetParent==null || targetStudent==null)
			return false;
		targetParent.getChildren().add(targetStudent);
		entityManager.getTransaction().begin();
		entityManager.persist(targetParent);
		entityManager.getTransaction().commit();
		return true;
	}
	
	public boolean addStudentToClass(String classroomId, String studentId) {
		Classroom targetClass = entityManager.find(Classroom.class, classroomId);
		Student targetStudent = entityManager.find(Student.class, studentId);
		if(targetClass==null || targetStudent==null)
			return false;
		targetClass.getStudents().add(targetStudent);
		entityManager.getTransaction().begin();
		entityManager.persist(targetClass);
		entityManager.getTransaction().commit();
		return true;
	}
	
	public boolean createParent(String name, String surname, String day, String month, String year) {
		if (name == null || surname == null)
			return false;
		Parent parentToAdd = new Parent();
		PersonalData pdat = new PersonalData();
		pdat.setName(name);
		pdat.setSurname(surname);
		try {
			pdat.setDateOfBirth(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return false;
		}
		parentToAdd.setPersonalData(pdat);
		entityManager.getTransaction().begin();
		entityManager.persist(parentToAdd);
		entityManager.getTransaction().commit();
		return true;
	}
	
	public boolean createTeacher(String name, String surname, String day, String month, String year) {
		if (name == null || surname == null)
			return false;
		Teacher teacherToAdd = new Teacher();
		PersonalData pdat = new PersonalData();
		pdat.setName(name);
		pdat.setSurname(surname);
		try {
			pdat.setDateOfBirth(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return false;
		}
		teacherToAdd.setPersonalData(pdat);
		entityManager.getTransaction().begin();
		entityManager.persist(teacherToAdd);
		entityManager.getTransaction().commit();
		return true;
	}
	
	public boolean createAdministrator(String name, String surname, String day, String month, String year) {
		if (name == null || surname == null)
			return false;
		Administrator administratorToAdd = new Administrator();
		PersonalData pdat = new PersonalData();
		pdat.setName(name);
		pdat.setSurname(surname);
		try {
			pdat.setDateOfBirth(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return false;
		}
		administratorToAdd.setPersonalData(pdat);
		entityManager.getTransaction().begin();
		entityManager.persist(administratorToAdd);
		entityManager.getTransaction().commit();
		return true;
	}
	
	public boolean createPaymentForParent(String parentId, String amount, String reason, String day, String month, String year) {
		Parent targetParent = entityManager.find(Parent.class, parentId);
		if(targetParent==null || amount == null || reason==null)
			return false;
		Payment newPayment = new Payment();
		newPayment.setAmount(new Integer(amount));
		newPayment.setDone(false);
		newPayment.setReason(reason);
		newPayment.setUser(targetParent);
		try {
			newPayment.setDate(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return false;
		}
		entityManager.getTransaction().begin();
		entityManager.persist(newPayment);
		entityManager.getTransaction().commit();
		return true;
	}
	
	public boolean createNotification(String userId, String text) {
		User targetUser = entityManager.find(User.class, userId);
		if(targetUser == null || text == null)
			return false;
		Notification notificationToAdd = new Notification();
		notificationToAdd.setText(text);
		notificationToAdd.setUser(targetUser);
		notificationToAdd.setDate(new Date());
		entityManager.getTransaction().begin();
		entityManager.persist(notificationToAdd);
		entityManager.getTransaction().commit();
		return true;
	}
}

