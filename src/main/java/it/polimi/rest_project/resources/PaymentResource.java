package it.polimi.rest_project.resources;

import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.internal.util.Base64;

import it.polimi.rest_project.application.Back2School;
import it.polimi.rest_project.entities.Payment;
import it.polimi.rest_project.services.PaymentService;

@Path("payments")
public class PaymentResource {

	/** The payment service used by the resource */
	private PaymentService paymentService;

	/**
	 * Default constructor that initializes the service used by the resource
	 */
	public PaymentResource() {
		paymentService = new PaymentService();
	}

	/**
	 * Returns the list of payments that can be viewed by the user
	 */
	@GET
	public List<Payment> getPayments(@Context ContainerRequestContext requestContext,
			@QueryParam("month") Integer month) {
		String userId = getUserId(requestContext);
		if (month != null)
			return paymentService.getPaymentsMonthly(userId, month);
		return paymentService.getPayments(userId);
	}

	/**
	 * Returns the payment if it can be viewed by the user
	 */
	@GET
	@Path("{paymentId}")
	public Payment getPayment(@PathParam("paymentId") String paymentId,
			@Context ContainerRequestContext requestContext) {
		String userId = getUserId(requestContext);
		return paymentService.getPayment(userId, paymentId);
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
	 * Creates a resource with the parameters taken as input if the user who made the request is allowed to do it
	 */
	@POST
	public Response addPayment(@Context UriInfo uriInfo, @Context ContainerRequestContext requestContext,
			@FormParam("user2Id") String user2Id, @FormParam("amount") String amount,
			@FormParam("reason") String reason) {
		String userId = getUserId(requestContext);
		return paymentService.issuePayment(userId, user2Id, amount, reason, uriInfo.getBaseUri().toString());
	}

	@PUT
	@Path("{paymentId}")
	public Response payPayment(@Context ContainerRequestContext requestContext,
			@PathParam("paymentId") String paymentId) {
		String userId = getUserId(requestContext);
		return paymentService.payPayment(userId, paymentId);
	}

}
