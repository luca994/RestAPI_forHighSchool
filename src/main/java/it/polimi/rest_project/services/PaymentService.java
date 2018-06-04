package it.polimi.rest_project.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.polimi.rest_project.application.Back2School;
import it.polimi.rest_project.entities.Link;
import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Payment;

public class PaymentService {

	private EntityManager entityManager;

	public PaymentService() {
		entityManager = Back2School.getEntityManager();
	}

	public Payment getPayment(String userId, String paymentId) {
		Payment targetPayment = entityManager.find(Payment.class, paymentId);
		if (targetPayment.getUser().getUserId().equals(userId))
			return targetPayment;
		return null;
	}

	public Response issuePayment(String userId, String user2Id, String amount, String reason, String baseUri) {
		UserService userService = new UserService();
		if (userService.isAdministrator(userId)) {
			Payment newPayment = new Payment();
			if (!userService.isParent(user2Id))
				return Response.status(Status.BAD_REQUEST).build();
			newPayment.setUser(entityManager.find(Parent.class, user2Id));
			newPayment.setAmount(Integer.parseInt(amount));
			newPayment.setReason(reason);
			addResources(newPayment, baseUri);
			entityManager.getTransaction().begin();
			entityManager.persist(newPayment);
			entityManager.getTransaction().commit();
			return Response.created(newPayment.getResources().get(0).getHref()).entity(newPayment).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	public Response payPayment(String userId, String paymentId) {
		Payment targetPayment = entityManager.find(Payment.class, paymentId);
		if (targetPayment.getUser().getUserId().equals(userId)) {
			targetPayment.setDone(true);
			entityManager.getTransaction().begin();
			entityManager.persist(targetPayment);
			entityManager.getTransaction().commit();
			return Response.status(Status.OK).entity(targetPayment).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}

	/**
	 * Adds the accessible resources to the entity
	 */
	private void addResources(Payment payment, String baseUri) {
		Link self = new Link(baseUri + "payments" + "/" + payment.getPaymentId(), "self");
		payment.getResources().add(self);
	}

	public List<Payment> getPayments(String userId) {
		UserService userService = new UserService();
		if (userService.isAdministrator(userId))
			return entityManager.createQuery("Select p from Payment p").getResultList();
		if (userService.isParent(userId)) {
			Query query = entityManager.createQuery("Select p from Payment p where p.user.userId=:userId");
			query.setParameter("userId", userId);
			return query.getResultList();
		}
		return null;
	}

	public List<Payment> getPaymentsMonthly(String userId, Integer month) {
		UserService userService = new UserService();
		if (userService.isAdministrator(userId) || userService.isParent(userId)) {
			Query query;
			if (userService.isAdministrator(userId)) {
				query = entityManager.createQuery("Select p from Payment p");
			} else {
				query = entityManager.createQuery("Select p from Payment p where p.user.userId=:userId");
				query.setParameter("userId", userId);
			}
			List<Payment> payments = query.getResultList();
			List<Payment> displayablePayments = new ArrayList<Payment>();
			for (Payment p : payments)
				if ((p.getDate().get(2) + 1) == month)
					displayablePayments.add(p);
			return displayablePayments;
		}
		return null;
	}
}
