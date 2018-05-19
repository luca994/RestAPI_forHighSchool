package it.polimi.rest_project.services;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("a")
public class Back2School extends ResourceConfig {

	protected static final EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("back2schoolDB");

	public Back2School() {
		packages("it.polimi.services");
	}
}
