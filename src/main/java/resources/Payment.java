package resources;

import java.util.Date;

public class Payment {

	private Date date;
	private Integer amount;
	private String reason;
	private String paymentId;

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

}
