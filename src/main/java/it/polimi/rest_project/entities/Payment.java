package it.polimi.rest_project.entities;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Payment {

	@Id
	private String paymentId;
	@Column
	private Integer amount;
	@Column
	private Date date;
	@Column
	private String reason;
	@JoinColumn
	private User user;
	@Column
	private boolean done;

	public Payment() {
		Integer random = new Random().nextInt();
		paymentId = random.toString();
	}

	public Date getDate() {
		return date;
	}

	public Integer getAmount() {
		return amount;
	}

	public String getReason() {
		return reason;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (paymentId == null) {
			if (other.paymentId != null)
				return false;
		} else if (!paymentId.equals(other.paymentId))
			return false;
		return true;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

}
