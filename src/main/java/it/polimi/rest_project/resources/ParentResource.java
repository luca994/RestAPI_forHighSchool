package it.polimi.rest_project.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.entities.PersonalData;
import it.polimi.rest_project.services.ParentService;

@Path("parents/{id}")
public class ParentResource {

	private ParentService parentService;

	public ParentResource() {
		parentService = new ParentService();
	}

	@GET
	public PersonalData getPersonalData(@PathParam("id") String id) {
		return parentService.getPersonalData(id);
	}

	@PUT
	public Response setPersonalData(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") String year, @FormParam("month") String month,
			@FormParam("day") String day) {

		try {
			PersonalData newPersonalData = new PersonalData(name, surname,
					new SimpleDateFormat().parse(year + "-" + month + "-" + day));
			parentService.updatePersonalData(id, newPersonalData);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("appointments")
	public List<Appointment> getAppointments(@PathParam("id") String id) {
		return parentService.getParentAppointments(id);
	}

	@PUT
	@Path("appointments/{appointmentId}")
	public Response updateAppointment(@PathParam("id") String userId, @PathParam("appointmentId") String appointmentId,
			@FormParam("year") String year, @FormParam("month") String month, @FormParam("day") String day) {
		try {
			// We have to check that the userId who made the call is present in the
			// appointment selected
			Appointment newAppointment = new Appointment(appointmentId, null, null,
					new SimpleDateFormat().parse(year + "-" + month + "-" + day));
			parentService.updateParentAppointment(newAppointment);
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
	
	

}
