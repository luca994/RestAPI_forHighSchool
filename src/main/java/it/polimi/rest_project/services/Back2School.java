package it.polimi.rest_project.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import it.polimi.rest_project.security.SecurityManager;

@ApplicationPath("b2s")
public class Back2School extends ResourceConfig {

	private static final String persistence_unit = "b2sdb";
	private static final EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(persistence_unit);
	public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	public static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

	public Back2School() {
		packages("it.polimi.rest_project.resources");
		register(SecurityManager.class);
		UserService userService = new UserService();
		userService.createFirstAdmin();
	}

	public static EntityManager getEntityManager() {
		return emfactory.createEntityManager();
	}

}
