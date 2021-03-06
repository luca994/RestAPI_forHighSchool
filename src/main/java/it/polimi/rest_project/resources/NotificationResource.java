package it.polimi.rest_project.resources;

import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.internal.util.Base64;

import it.polimi.rest_project.application.Back2School;
import it.polimi.rest_project.entities.Notification;
import it.polimi.rest_project.services.NotificationService;

@Path("notifications")
public class NotificationResource {

	/** The notification service used by the resource */
	private NotificationService notificationService;

	/**
	 * Default constructor that initializes the service used by the resource
	 */
	public NotificationResource() {
		notificationService = new NotificationService();
	}

	/**
	 * Returns the list of notifications that can be viewed by the user
	 */
	@GET
	public List<Notification> getNotifications(@Context ContainerRequestContext requestContext,
			@QueryParam("type") String type) {
		String userId = getUserId(requestContext);
		if (type != null) {
			if (type.toLowerCase().equals("general"))
				return notificationService.getGeneralNotifications(userId);
			if (type.toLowerCase().equals("specific"))
				return notificationService.getSpecificNotifications(userId);
			return null;
		}
		return notificationService.getNotifications(userId);
	}

	/**
	 * Returns the notification if it can be viewed by the user
	 */
	@GET
	@Path("{notificationId}")
	public Notification getNotification(@PathParam("notificationId") String notificationId,
			@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return notificationService.getNotification(userId, notificationId);
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
	 * if the id corresponds to a parent or a teacher, it creates a specific
	 * notification for that teacher/parent. If the id corresponds to a classroom it
	 * creates a general notification for all the parents which have a child
	 * enrolled in that class. If the id is null it creates a general notification
	 * for all the parents and all the teachers in the system
	 */
	@POST
	public Response addNotification(@Context UriInfo uriInfo, @Context ContainerRequestContext requestContext,
			@FormParam("id") String id, @FormParam("text") String text) {
		String userId = getUserId(requestContext);
		return notificationService.createNotification(userId, id, text, uriInfo.getBaseUri().toString());
	}

}
