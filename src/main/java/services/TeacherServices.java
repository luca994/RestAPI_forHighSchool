package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import resources.Classroom;
import resources.PersonalData;
import resources.Teacher;

@Path("teacers/{id}")
public class TeacherServices {
	List<Teacher> teachers;
	List<Classroom> classrooms;
	
	@GET
	public PersonalData getPersonalData(@PathParam("id") String id) {
		for(Teacher t : teachers) {
			if(t.getUserId().equals(id)) {
				return t.getPersonalData();
			}
		}
		return null;
	}
	
	@PUT
	public Response setPersonalData(@PathParam("id") String id,@FormParam("name") String name,@FormParam("surname") String surname,@FormParam("year") String year,@FormParam("month")String month,@FormParam("day")String day) {
		Teacher targetTeacher=null;
		for (Teacher t : teachers)
			if (t.getUserId().equals(id))
				targetTeacher=t;
		if(name!=null)
			targetTeacher.getPersonalData().setName(name);
		if(surname!=null)
			targetTeacher.getPersonalData().setSurname(surname);
		try {
			targetTeacher.getPersonalData().setDateOfBirth(new SimpleDateFormat().parse(year+"-"+month+"-"+day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("classrooms")
	public List<Classroom> getClassrooms(){
		return classrooms;
	}
	
	
	
}
