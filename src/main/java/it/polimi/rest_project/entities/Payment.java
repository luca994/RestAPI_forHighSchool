package it.polimi.rest_project.entities;

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
public class Payment {

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
	@JsonIgnoreProperties({ "userId", "dateOfBirth","students","resources" })
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

	public String getPaymentId() {
		return paymentId;
	}

	public Integer getAmount() {
		return amount;
	}

	public Calendar getDate() {
		return date;
	}

	public String getReason() {
		return reason;
	}

	public User getUser() {
		return user;
	}

	public boolean isDone() {
		return done;
	}

	public List<Link> getResources() {
		return resources;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public void setResources(List<Link> resources) {
		this.resources = resources;
	}

}
