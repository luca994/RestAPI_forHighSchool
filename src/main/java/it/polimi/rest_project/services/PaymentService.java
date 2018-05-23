package it.polimi.rest_project.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.polimi.rest_project.entities.Parent;
import it.polimi.rest_project.entities.Payment;

public class PaymentService {

	private EntityManager entityManager;

	public PaymentService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Payment> getPayments(){
		Query query = entityManager.createQuery("Select p from Payment p");
		return query.getResultList();
	}
	
	public List<Payment> getPaymentsFromUser(String userId) {
		Query query = entityManager.createQuery("Select p from Payment p where p.user=:param");
		query.setParameter("param", entityManager.find(Parent.class, userId));
		return query.getResultList();
	}

	public Payment getPayment(String userId, String paymentId) {
		Payment targetPayment = entityManager.find(Payment.class, paymentId);
		if (getPaymentsFromUser(userId).contains(targetPayment))
			return targetPayment;
		else
			return null;
	}

	public boolean payPayment(String paymentId) {
		Payment targetPayment;
		entityManager.getTransaction().begin();
		targetPayment = entityManager.find(Payment.class, paymentId);
		targetPayment.setDone(true);
		entityManager.persist(targetPayment);
		entityManager.getTransaction().commit();
		return true;
	}
}
