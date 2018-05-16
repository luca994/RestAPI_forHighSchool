package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import resources.Appointment;
import resources.Parent;
import resources.PersonalData;

@Path("parents/{id}")
public class ParentServices {

	private List<Parent> parents;

	@GET
	public PersonalData getPersonalData(@PathParam("id") String id) {
		for (Parent p : parents)
			if (p.getUserId().equals(id))
				return p.getPersonalData();
		return null;
	}

	@PUT
	public Response setPersonalData(@PathParam("id") String id, @FormParam("name") String name,
			@FormParam("surname") String surname, @FormParam("year") String year, @FormParam("month") String month,
			@FormParam("day") String day) {
		Parent targetParent = null;
		for (Parent p : parents)
			if (p.getUserId().equals(id))
				targetParent = p;
		if (name != null)
			targetParent.getPersonalData().setName(name);
		if (surname != null)
			targetParent.getPersonalData().setSurname(surname);
		try {
			targetParent.getPersonalData().setDateOfBirth(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("appointments")
	public List<Appointment> getAppointments(@PathParam("id") String id) {
		for (Parent p : parents)
			if (p.getUserId().equals(id))
				return p.getAppointments();
		return null;
	}

	@PUT
	@Path("appointments/{appointmentId}")
	public Response setAppointments() {
		return Response.ok().build();
	}

}
