package resources;

import java.util.Date;

public class Appointment {

	private Date date;
	private Teacher teacher;
	private Parent parent;
	private String appointmentId;

	public Date getDate() {
		return date;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public Parent getParent() {
		return parent;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		if (appointmentId == null) {
			if (other.appointmentId != null)
				return false;
		} else if (!appointmentId.equals(other.appointmentId))
			return false;
		return true;
	}
}
