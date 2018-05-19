package it.polimi.rest_project.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.Notification;
import it.polimi.rest_project.entities.Payment;
import it.polimi.rest_project.entities.PersonalData;
import it.polimi.rest_project.entities.Student;
import it.polimi.rest_project.services.ParentService;

@Path("parents/{id}")
public class ParentResource {

	private ParentService parentService;

	public ParentResource() {
		parentService = new ParentService();
	}

	@GET
	public PersonalData getPersonalData(@PathParam("id") String userId) {
		return parentService.getPersonalData(userId);
	}

	@PUT
	public Response updatePersonalData(@PathParam("id") String userId, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") String year, @FormParam("month") String month,
			@FormParam("day") String day) {

		try {
			PersonalData newPersonalData = new PersonalData(name, surname,
					new SimpleDateFormat().parse(year + "-" + month + "-" + day));
			parentService.updatePersonalData(userId, newPersonalData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("appointments")
	public List<Appointment> getAppointments(@PathParam("id") String userId) {
		return parentService.getParentAppointments(userId);
	}

	@PUT
	@Path("appointments/{appointmentId}")
	public Response updateAppointment(@PathParam("id") String userId, @PathParam("appointmentId") String appointmentId,
			@FormParam("year") String year, @FormParam("month") String month, @FormParam("day") String day) {
		try {
			parentService.updateParentAppointment(appointmentId,
					new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@POST
	@Path("appointments")
	public Response addAppointment(@PathParam("id") String userId, @FormParam("year") String year,
			@FormParam("month") String month, @FormParam("day") String day, @FormParam("teacherId") String teacherId) {

		try {
			parentService.addParentAppointment(userId, teacherId,
					new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@DELETE
	@Path("appointments/{appointmentId}")
	public Response deleteAppointment(@PathParam("id") String userId,
			@PathParam("appointmentId") String appointmentId) {
		parentService.deleteParentAppointment(appointmentId);
		return Response.ok().build();
	}

	@GET
	@Path("payments")
	public List<Payment> getPayments(@PathParam("id") String userId) {
		return parentService.getParentPayments(userId);
	}

	@PUT
	@Path("payments/{paymentId}")
	public Response payPayment(@PathParam("id") String userId, @PathParam("paymentId") String paymentId) {
		parentService.payParentPayments(paymentId);
		return Response.ok().build();
	}
	
	@GET
	@Path("notifications")
	public List<Notification> getNotifications(@PathParam("id") String userId){
		return parentService.getParentNotifications(userId);
	}
	
	@GET
	@Path("students")
	public List<Student> getStudents(@PathParam("id") String userId){
		return parentService.getParentStudents(userId);
	}
	
	@GET
	@Path("students/{studentId}")
	public Student getStudent(@PathParam("id") String userId,@PathParam("studentId") String studentId) {
		return parentService.getParentStudent(userId,studentId);
	}
	
	@PUT
	@Path("students/{studentId}")
	public Response updateStudentPersonalData(@PathParam("id") String userId,@PathParam("studentId") String studentId, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") String year, @FormParam("month") String month,
			@FormParam("day") String day) {

		try {
			PersonalData newPersonalData = new PersonalData(name, surname,
					new SimpleDateFormat().parse(year + "-" + month + "-" + day));
			parentService.updatePersonalData(studentId, newPersonalData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}

}
