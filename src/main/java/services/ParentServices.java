package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import resources.Appointment;
import resources.Parent;
import resources.PersonalData;
import resources.Teacher;

@Path("parents/{id}")
public class ParentServices {

	private List<Parent> parents;
	private List<Teacher> teachers;

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
	public Response setAppointments(@PathParam("id") String userId, @PathParam("appointmentId") String appointmentId,
			@FormParam("year") String year, @FormParam("month") String month, @FormParam("day") String day) {
		Appointment targetAppointment = null;
		for (Parent p : parents)
			if (p.getUserId().equals(userId))
				for (Appointment a : p.getAppointments())
					if (a.getAppointmentId().equals(appointmentId)) {
						targetAppointment = a;
						break;
					}
		try {
			targetAppointment.setDate(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
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
		Appointment targetAppointment = new Appointment();
		Parent targetParent = null;
		Teacher targetTeacher = null;
		for(Parent p:parents)
			if(p.getUserId().equals(userId)) {
				targetParent=p;
				break;
			}
		for(Teacher t:teachers)
			if(t.getUserId().equals(teacherId)) {
				targetTeacher=t;
				break;
			}
		if(targetParent == null || targetTeacher == null)
			return Response.noContent().build();
		targetAppointment.setParent(targetParent);
		targetAppointment.setTeacher(targetTeacher);
		targetAppointment.setAppointmentId(Integer.toString(new Random().nextInt()));
		try {
			targetAppointment.setDate(new SimpleDateFormat().parse(year + "-" + month + "-" + day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}

}
