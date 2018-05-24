package it.polimi.rest_project.resources;

import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.internal.util.Base64;

import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.services.AppointmentService;
import it.polimi.rest_project.services.Back2School;

@Path("appointments")
public class AppointmentResource {

	private AppointmentService appointmentService;

	public AppointmentResource() {
		appointmentService = new AppointmentService();
	}
	
	@GET
	public List<Appointment> getAppointments(@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return appointmentService.getAppointments(userId);
	}

	@GET
	@Path("{appointmentId}")
	public Appointment getAppointment(@PathParam("appointmentId") String appointmentId,
			@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return appointmentService.getAppointment(userId, appointmentId);
	}

	private String getUserId(ContainerRequestContext requestContext) {
		List<String> headers = requestContext.getHeaders().get(Back2School.AUTHORIZATION_HEADER_KEY);
		String auth = headers.get(0);
		auth = auth.replaceFirst(Back2School.AUTHORIZATION_HEADER_PREFIX, "");
		auth = Base64.decodeAsString(auth);
		StringTokenizer tokenizer = new StringTokenizer(auth, ":");
		String userId = tokenizer.nextToken();
		return userId;
	}

	@PUT
	@Path("{appointmentId}")
	public Response updateAppointment(@PathParam("appointmentId") String appointmentId,
			@Context ContainerRequestContext requestContext, @FormParam("year") String year,
			@FormParam("month") String month, @FormParam("day") String day) {
		String userId = getUserId(requestContext);
		return appointmentService.updateAppointment(userId, appointmentId, day, month, year);
	}

	@POST
	public Response addAppointment(@Context UriInfo uriInfo,@Context ContainerRequestContext requestContext, @FormParam("year") String year,
			@FormParam("month") String month, @FormParam("day") String day, @FormParam("user2Id") String user2Id) {
		String userId = getUserId(requestContext);
		return appointmentService.createAppointment(userId, user2Id, day, month, year,uriInfo.getBaseUri().toString());
	}

}
