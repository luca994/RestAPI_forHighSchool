package it.polimi.rest_project.resources;

import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.DELETE;
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

import it.polimi.rest_project.application.Back2School;
import it.polimi.rest_project.entities.Appointment;
import it.polimi.rest_project.services.AppointmentService;

@Path("appointments")
public class AppointmentResource {

	/** The appointment service used by the resource */
	private AppointmentService appointmentService;

	/**
	 * Default constructor that initializes the service used by the resource
	 */
	public AppointmentResource() {
		appointmentService = new AppointmentService();
	}

	/**
	 * Returns the list of appointments that can be viewed by the user
	 */
	@GET
	public List<Appointment> getAppointments(@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return appointmentService.getAppointments(userId);
	}

	/**
	 * Returns the appointment if it can be viewed by the user
	 */
	@GET
	@Path("{appointmentId}")
	public Appointment getAppointment(@PathParam("appointmentId") String appointmentId,
			@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return appointmentService.getAppointment(userId, appointmentId);
	}

	/**
	 * Gets the userId of the user who made the request
	 * 
	 * @param requestContext
	 *            the request context of this call
	 * @return the id of the user who made the request
	 */
	private String getUserId(ContainerRequestContext requestContext) {
		List<String> headers = requestContext.getHeaders().get(Back2School.AUTHORIZATION_HEADER_KEY);
		String auth = headers.get(0);
		auth = auth.replaceFirst(Back2School.AUTHORIZATION_HEADER_PREFIX, "");
		auth = Base64.decodeAsString(auth);
		StringTokenizer tokenizer = new StringTokenizer(auth, ":");
		String userId = tokenizer.nextToken();
		return userId;
	}

	/**
	 * Updates the resource with the parameters taken as input
	 */
	@PUT
	@Path("{appointmentId}")
	public Response updateAppointment(@PathParam("appointmentId") String appointmentId,
			@Context ContainerRequestContext requestContext, @FormParam("year") Integer year,
			@FormParam("month") Integer month, @FormParam("day") Integer day) {
		String userId = getUserId(requestContext);
		return appointmentService.updateAppointment(userId, appointmentId, day, month, year);
	}

	/**
	 * Creates a resource with the parameters taken as input, if the user who made the request is allowed to do it
	 */
	@POST
	public Response addAppointment(@Context UriInfo uriInfo, @Context ContainerRequestContext requestContext,
			@FormParam("year") Integer year, @FormParam("month") Integer month, @FormParam("day") Integer day,
			@FormParam("user2Id") String user2Id) {
		String userId = getUserId(requestContext);
		return appointmentService.createAppointment(userId, user2Id, day, month, year, uriInfo.getBaseUri().toString());
	}

	/**
	 * Deletes the resource with the id taken as input, if the user who made the request is allowed to do it
	 */
	@DELETE
	@Path("{appointmentId}")
	public Response deleteAppointment(@Context ContainerRequestContext requestContext,
			@PathParam("appointmentId") String appointmentId) {
		String userId = getUserId(requestContext);
		return appointmentService.deleteAppointment(userId, appointmentId);
	}

}
