package it.polimi.rest_project.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.Notification;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Payment;
import it.polimi.rest_project.entities.Student;
import it.polimi.rest_project.services.ParentService;

@Path("parents/{id}")
public class ParentResource {

	@Context
	private UriInfo uriInfo;
	private ParentService parentService;
	
	public ParentResource() {
		parentService = new ParentService();
	}

	@GET
	public Parent getPersonalData(@PathParam("id") String userId) {
		return parentService.getParent(userId);
	}

	@PUT
	public Response updatePersonalData(@PathParam("id") String parentId, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") String year, @FormParam("month") String month,
			@FormParam("day") String day) {
		if (parentService.updateUserData(parentId, name, surname, day, month, year))
			return Response.status(Response.Status.ACCEPTED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Path("students/{studentId}")
	public Student getStudent(@PathParam("id") String userId, @PathParam("studentId") String studentId) {
		return parentService.getParentStudent(userId, studentId);
	}

	@PUT
	@Path("students/{studentId}")
	public Response updateStudentPersonalData(@PathParam("id") String userId, @PathParam("studentId") String studentId,
			@FormParam("name") String name, @FormParam("surname") String surname, @FormParam("year") String year,
			@FormParam("month") String month, @FormParam("day") String day) {

		if (parentService.updateUserData(studentId, name, surname, day, month, year))
			return Response.status(Response.Status.ACCEPTED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Path("appointments/{appointmentId}")
	public Appointment getAppointments(@PathParam("id") String userId,
			@PathParam("appointmentId") String appointmentId) {
		return parentService.getParentAppointment(userId, appointmentId);
	}

	@PUT
	@Path("appointments/{appointmentId}")
	public Response updateAppointment(@PathParam("id") String userId, @PathParam("appointmentId") String appointmentId,
			@FormParam("year") String year, @FormParam("month") String month, @FormParam("day") String day) {

		if (parentService.updateParentAppointment(userId, appointmentId, day, month, year))
			return Response.status(Response.Status.ACCEPTED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@POST
	@Path("appointments")
	public Response addAppointment(@PathParam("id") String userId, @FormParam("year") String year,
			@FormParam("month") String month, @FormParam("day") String day, @FormParam("teacherId") String teacherId) {

		if (parentService.addParentAppointment(userId, teacherId, day, month, year,uriInfo.getBaseUri().toString()))
			return Response.status(Response.Status.ACCEPTED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@DELETE
	@Path("appointments/{appointmentId}")
	public Response deleteAppointment(@PathParam("id") String userId,
			@PathParam("appointmentId") String appointmentId) {
		if (parentService.deleteParentAppointment(userId, appointmentId))
			return Response.status(Response.Status.ACCEPTED).build();
		else
			return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Path("payments/{paymentId}")
	public Payment getPayments(@PathParam("id") String userId, @PathParam("paymentId") String paymentId) {
		return parentService.getParentPayment(userId, paymentId);
	}

	@PUT
	@Path("payments/{paymentId}")
	public Response payPayment(@PathParam("id") String userId, @PathParam("paymentId") String paymentId) {
		parentService.payParentPayments(paymentId);
		return Response.status(Response.Status.ACCEPTED).build();
	}

	@GET
	@Path("notifications/{notificationId}")
	public List<Notification> getNotifications(@PathParam("id") String userId,
			@PathParam("notificationId") String paymentId) {
		return parentService.getParentNotifications(userId);
	}

}
