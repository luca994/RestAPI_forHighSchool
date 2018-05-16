package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("parents")
public class ParentServices {

	@GET
	@Path("{id}")
	public String print(@PathParam("name") String id) {
		return "Hello "+id;
	}
	
}
