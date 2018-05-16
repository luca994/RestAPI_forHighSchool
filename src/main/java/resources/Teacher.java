package resources;

import java.util.List;
import java.util.Map;

public class Teacher extends User {

	private Map<Classroom, String> classInformations;
	private List<Notification> notifications;

	public Map<Classroom, String> getClassInformations() {
		return classInformations;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setClassInformations(Map<Classroom, String> classInformations) {
		this.classInformations = classInformations;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

}
