package it.polimi.rest_project.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.polimi.rest_project.json.OptimizedDateSerializer;

@Entity
@Table(name = "Payments")
@JsonPropertyOrder({ "paymentId", "date", "amount", "reason", "done", "user", "resources" })
public class Payment implements Serializable {

	private static final long serialVersionUID = -7032322720137769992L;
	@Id
	private String paymentId;
	@Column
	private Integer amount;
	@Column
	@JsonSerialize(using = OptimizedDateSerializer.class)
	private Calendar date;
	@Column
	private String reason;
	@JoinColumn
	@JsonIgnoreProperties({ "userId", "dateOfBirth", "students", "resources" })
	private User user;
	@Column
	private boolean done;
	@JoinColumn
	private List<Link> resources;

	public Payment() {
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z')
				.filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS).build();
		this.paymentId = generator.generate(8);
		date = new GregorianCalendar();
		this.resources = new ArrayList<Link>();
		done = false;
	}

	/**
	 * @return the paymentId
	 */
	public String getPaymentId() {
		return paymentId;
	}

	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * @return the date
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * @return the resources
	 */
	public List<Link> getResources() {
		return resources;
	}

	/**
	 * @param paymentId
	 *            the paymentId to set
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param done
	 *            the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * @param resources
	 *            the resources to set
	 */
	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}
