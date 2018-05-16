package resources;

import java.util.Date;

public class Notification {

	private String text;
	private Date date;
	private User user;

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}

	public User getUser() {
		return user;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
