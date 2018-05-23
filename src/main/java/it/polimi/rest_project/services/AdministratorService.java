package it.polimi.rest_project.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import it.polimi.rest_project.entities.Administrator;
import it.polimi.rest_project.entities.Classroom;
import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Notification;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Payment;
import it.polimi.rest_project.entities.Student;
import it.polimi.rest_project.entities.Teacher;
import it.polimi.rest_project.entities.User;
import it.polimi.rest_project.resources.ParentResource;

public class AdministratorService extends UserService {

	private ClassroomService classroomService;

	public AdministratorService() {
		super();
		classroomService = new ClassroomService(entityManager);
	}

	public Administrator getAdmin(String adminId) {
		return entityManager.find(Administrator.class, adminId);
	}

	public Student getStudent(String studentId) {
		return entityManager.find(Student.class, studentId);
	}

	public Parent getParent(String parentId) {
		return entityManager.find(Parent.class, parentId);
	}

	public Teacher getTeacher(String teacherId) {
		return entityManager.find(Teacher.class, teacherId);
	}

	public List<Classroom> getAdminClassrooms() {
		return classroomService.getClassrooms();
	}

	public Classroom getAdminClassroom(String classroomId) {
		return classroomService.getClassroom(classroomId);
	}

	public List<Student> getAdminStudents() {
		return getStudents();
	}

	public List<Parent> getAdminParents() {
		return getParents();
	}

	public List<Administrator> getAdminAdministrators() {
		return getAdmins();
	}

	public List<Teacher> getAdminTeachers() {
		return getTeachers();
	}

	public boolean createStudent(String name, String surname, String day, String month, String year) {
		Student newStudent = new Student();
		if (name == null || surname == null || day == null || month == null || year == null)
			return false;
		newStudent.setName(name);
		newStudent.setSurname(surname);
		try {
			newStudent.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return false;
		}
		entityManager.getTransaction().begin();
		entityManager.persist(newStudent);
		entityManager.getTransaction().commit();
		return true;
	}

	public boolean addStudentToClass(String classroomId, String studentId) {
		Classroom targetClass = entityManager.find(Classroom.class, classroomId);
		Student targetStudent = entityManager.find(Student.class, studentId);
		if (targetClass == null || targetStudent == null)
			return false;
		targetClass.getStudents().add(targetStudent);
		entityManager.getTransaction().begin();
		entityManager.persist(targetClass);
		entityManager.getTransaction().commit();
		return true;
	}

	public boolean addStudentToParent(String parentId, String studentId, UriInfo uriInfo) {
		Parent targetParent = entityManager.find(Parent.class, parentId);
		Student targetStudent = entityManager.find(Student.class, studentId);
		if (targetParent == null || targetStudent == null)
			return false;
		Link link = new Link();
		link.setRel("children");
		link.setUri(uriInfo.getBaseUriBuilder().path(ParentResource.class).path(targetParent.getUserId())
				.path("students").path(studentId).build().toString());
		targetParent.getResources().add(link);
		targetStudent.getParents().add(targetParent);
		entityManager.getTransaction().begin();
		entityManager.persist(targetStudent);
		entityManager.persist(targetParent);
		entityManager.getTransaction().commit();
		return true;
	}

	public boolean createParent(String name, String surname, String day, String month, String year) {
		Parent newParent = new Parent();
		if (name == null || surname == null || day == null || month == null || year == null)
			return false;
		newParent.setName(name);
		newParent.setSurname(surname);
		try {
			newParent.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return false;
		}
		entityManager.getTransaction().begin();
		entityManager.persist(newParent);
		entityManager.getTransaction().commit();
		return true;
	}

	public boolean createAdministrator(String name, String surname, String day, String month, String year,
			String uriInfo) {
		Administrator newAdministrator = new Administrator();
		if (name == null || surname == null || day == null || month == null || year == null)
			return false;
		newAdministrator.setName(name);
		newAdministrator.setSurname(surname);
		try {
			newAdministrator.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return false;
		}
		setAdministratorResources(newAdministrator, uriInfo);
		entityManager.getTransaction().begin();
		entityManager.persist(newAdministrator);
		entityManager.getTransaction().commit();
		return true;
	}

	private void setAdministratorResources(Administrator admin, String uriInfo) {
		Link link = new Link();
		link.setRel("students");
		link.setUri(uriInfo + "admins" + "/" + admin.getUserId() + "/" + "students");
		admin.getResources().add(link);
		link = new Link();
		link.setRel("parents");
		link.setUri(uriInfo + "admins" + "/" + admin.getUserId() + "/" + "parents");
		admin.getResources().add(link);
		link = new Link();
		link.setRel("teachers");
		link.setUri(uriInfo + "admins" + "/" + admin.getUserId() + "/" + "teachers");
		admin.getResources().add(link);
		link = new Link();
		link.setRel("administrators");
		link.setUri(uriInfo + "admins" + "/" + admin.getUserId() + "/" + "administrators");
		admin.getResources().add(link);
		link = new Link();
		link.setRel("classrooms");
		link.setUri(uriInfo + "admins" + "/" + admin.getUserId() + "/" + "classrooms");
		admin.getResources().add(link);
		link = new Link();
		link.setRel("notifications");
		link.setUri(uriInfo + "admins" + "/" + admin.getUserId() + "/" + "notifications");
		admin.getResources().add(link);
		link = new Link();
		link.setRel("payments");
		link.setUri(uriInfo + "admins" + "/" + admin.getUserId() + "/" + "payments");
		admin.getResources().add(link);
	}

	public boolean createTeacher(String name, String surname, String day, String month, String year) {
		Teacher newTeacher = new Teacher();
		if (name == null || surname == null || day == null || month == null || year == null)
			return false;
		newTeacher.setName(name);
		newTeacher.setSurname(surname);
		try {
			newTeacher.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return false;
		}
		entityManager.getTransaction().begin();
		entityManager.persist(newTeacher);
		entityManager.getTransaction().commit();
		return true;
	}

	public boolean createPayment(String parentId, String amount, String reason, String day, String month, String year,
			String uriInfo) {
		Parent targetParent = entityManager.find(Parent.class, parentId);
		if (targetParent == null || amount == null || reason == null)
			return false;
		Payment newPayment = new Payment();
		newPayment.setAmount(new Integer(amount));
		newPayment.setDone(false);
		newPayment.setReason(reason);
		newPayment.setUser(targetParent);
		try {
			newPayment.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			return false;
		}
		Link link = new Link();
		link.setRel("payment");
		link.setUri(uriInfo + "parents" + "/" + targetParent.getUserId() + "/" + "payments" + "/"
				+ newPayment.getPaymentId());
		targetParent.getResources().add(link);
		entityManager.getTransaction().begin();
		entityManager.persist(newPayment);
		entityManager.persist(targetParent);
		entityManager.getTransaction().commit();
		return true;
	}

	public boolean createNotification(String userId, String text, String uriInfo) {
		User targetUser = entityManager.find(User.class, userId);
		if (targetUser == null || text == null)
			return false;
		Notification notificationToAdd = new Notification();
		notificationToAdd.setText(text);
		notificationToAdd.setUser(targetUser);
		notificationToAdd.setDate(new Date());
		Link link = new Link();
		link.setRel("notification");
		link.setUri(uriInfo + "parents" + "/" + targetUser.getUserId() + "/" + "notifications" + "/"
				+ notificationToAdd.getId());
		targetUser.getResources().add(link);
		entityManager.getTransaction().begin();
		entityManager.persist(notificationToAdd);
		entityManager.persist(targetUser);
		entityManager.getTransaction().commit();
		return true;
	}
}
