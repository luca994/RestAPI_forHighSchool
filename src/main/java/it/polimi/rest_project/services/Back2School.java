package it.polimi.rest_project.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("b2s")
public class Back2School extends ResourceConfig {

	private static EntityManagerFactory emfactory;
	private static final String persistence_unit = "b2sdb";

	public Back2School() {
		packages("it.polimi.rest_project.resources");
		emfactory = Persistence.createEntityManagerFactory(persistence_unit);
	}

	public static EntityManager getEntityManager() {
		EntityManager em = emfactory.createEntityManager();
		return em;
	}

}
